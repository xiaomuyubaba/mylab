<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
    	<title>穷庐</title>
    	<link href="/bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet">
    	<link rel="stylesheet" href="/bootstrap-table/bootstrap-table.css">
    	<link rel="stylesheet" href="/bootstrap-datepicker/css/bootstrap-datepicker3.min.css">
    	<link rel="stylesheet" type="text/css" href="/font-awesome/css/font-awesome.min.css"></link>
		<link rel="stylesheet" type="text/css" href="/css/mylab.css"></link>
	</head>
	
	<body>
		<div>
    		<div class="sidebar">
    			<div id="jquery-accordion-menu" class="jquery-accordion-menu">
					<ul id="demo-list">
						<li class="active"><a href="#"><i class="fa fa-bar-chart"></i>股海浮沉</a></li>
					</ul>
				</div>
    		</div>
	    		
			<div class="main">
				<div class="page-header">
					<ol class="breadcrumb">
					    <li><a href="#">股海浮沉</a></li>
				    </ol>
				</div>

				<div class="page-content">
					<div class="tabbable">
	        			<ul id="stockTabs" class="nav nav-tabs">
						    <li>
						    	<a href="#logTab" tblId="#logTable" data-toggle="tab">当前持仓</a>
						    </li>
	    					<li><a href="#hisLogTab" tblId="#hisLogTable" data-toggle="tab">历史记录</a></li>
	    					<li><a href="#stockTab" tblId="#stockTable" data-toggle="tab">股票管理</a></li>
	    				</ul>
				    				
	    				<div id="stockTabContent" class="tab-content">
						    <div class="tab-pane active" id="logTab">
								<div id="toolbar1" class="toolbar" style="height: 33px;">
									<a id="buyBtn" class="btn btn-primary" href="javascript:">买入</a>
									<a id="refreshBtn1" class="btn btn-primary" href="javascript:">刷新</a>
								</div>
								<table id="logTable"></table>
						    </div>
						    
						    <div class="tab-pane" id="hisLogTab">
						    	<div id="toolbar2" class="toolbar" style="height: 33px;">
									<a id="refreshBtn2" class="btn btn-primary" href="javascript:">刷新</a>
								</div>
						        <table id="hisLogTable"></table>
						    </div>

						    <div class="tab-pane" id="stockTab">
                                <div id="toolbar2" class="toolbar" style="height: 33px;">
                                    <a id="addStockBtn" class="btn btn-primary" href="javascript:">新增</a>
                                    <a id="refreshBtn3" class="btn btn-primary" href="javascript:">刷新</a>
                                </div>
                                
                                <table id="stockTable"></table>
                            </div>
						</div>
	    			</div>
					    
				    <div id="stockModal" class="modal fade">
				        <div class="modal-dialog">
				            <div class="modal-content">
				            </div>
				        </div>
					</div>
				</div>
			</div>
		</div>
		
		<script src="/js/jquery.min.js"></script>
		<script src="/bootstrap-3.3.7/js/bootstrap.min.js"></script>
		<script src="/bootstrap-table/bootstrap-table.js"></script>
		<script src="/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
		<script src="/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
		<script src="/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
		<script src="/js/mylab.js"></script>

		<script>
		    var $logTable = $("#logTable"),
		    	$hisLogTable = $("#hisLogTable"),
		    	$stockTable = $("#stockTable"),
		        $stockModal = $('#stockModal').modal({show: false});
		        
		    $(function () {
		    	$('#stockTabs a').click(function (e) {
		    		e.preventDefault();
		    		$(this).tab('show');
		    		$($(this).attr("tblId")).bootstrapTable("resetView");
				});
				
				$("#logTable").mngTable({
					columns: [
						{
							field: "idx",
							title: "序号",
							width: 60,
							formatter: function(v, row, idx, fd) {
								return idx + 1;
							}
						},
						{
							field: "stockNo",
							title: "股票代码",
							width: 100,
							sortable: true
						},
						{
							field: "stockNm",
							title: "股票名称",
							width: 160
						},
						{
							field: "transNumber",
							title: "股票数",
							width: 100
						},
						{
							field: "buyInDt",
							title: "买入日期",
							width: 100,
						},
						{
							field: "buyInAt",
							title: "买入价",
							width: 90
						},
						{
							field: "currPrice",
							title: "当前价",
							width: 90,
							formatter: currPriceFormatter
						},
						{
							field: "prepareBuyInAt",
							title: "计划补仓价",
							width: 120,
							formatter: prepareBuyInAtFormatter
						},
						{
							field: "profit",
							title: "盈亏",
							sortable: true,
							formatter: profitFormatter
						},
						{
							field: "op",
							title: "操作",
							width: 120,
							formatter: function(v, row, idx, fd) {
								let btns = '<button type="button" class="tbl-btn sell-btn">卖出</button>';
								btns = btns + '<button type="button" class="tbl-btn del-btn">删除</button>';
								return btns;
							},
							events: {
								"click .sell-btn": function(e, val, row, idx) {
									$.showModal("/stock/sell?logId=" + row.logId, $stockModal);
								},
								"click .del-btn": function(e, val, row, idx) {
									if (confirm("确认删除?")) {
			                        	$.ajaxPost("/stock/delLog", {logId : row.logId}, 
			                        		function(ajaxResp) {
								    			alert('删除成功!');
			                                    $("#refreshBtn1").trigger("click");
								    	});
			                        }
								}
							}
						}
					]
				});
		    	
		    	$("#buyBtn").click(function() {
		    		$.showModal("/stock/buy", $stockModal);
		    	});
		    	
		    	$("#refreshBtn1").click(function() {
		    		$("#refreshBtn1").html("刷新中...");
		    		$.ajaxPost("/stock/position/logLst", {status:"0"}, function(respData) {
		    			$logTable.bootstrapTable("load", respData.logLst);
		    			$("#refreshBtn1").html("刷新");
		    		});
		    	});
		    	
		    	$("#hisLogTable").mngTable({
					columns: [
						{
							field: "idx",
							title: "序号",
							width: 60,
							formatter: function(v, row, idx, fd) {
								return idx + 1;
							}
						},
						{
							field: "stockNo",
							title: "股票代码",
							width: 100,
							sortable: true
						},
						{
							field: "stockNm",
							title: "股票名称",
							width: 160
						},
						{
							field: "transNumber",
							title: "股票数",
							width: 100
						},
						{
							field: "buyInDt",
							title: "买入日期",
							width: 100,
							sortable: true
						},
						{
							field: "buyInAt",
							title: "买入价",
							width: 90
						},
						{
							field: "sellOutDt",
							title: "卖出日期",
							width: 90,
							sortable: true
						},
						{
							field: "sellOutAt",
							title: "卖出价",
							width: 90
						},
						{
							field: "profit",
							title: "盈亏",
							sortable: true,
							formatter: profitFormatter
						},
						{
							field: "profitAt",
							title: "盈亏金额",
							width: 120,
							sortable: true,
							formatter: profitAtFormatter
						}
					]
				});
				
				$("#refreshBtn2").click(function() {
		    		$("#refreshBtn2").html("刷新中...");
		    		$.ajaxPost("/stock/position/logLst", {status:"1"}, function(respData) {
		    			$hisLogTable.bootstrapTable("load", respData.logLst);
		    			$("#refreshBtn2").html("刷新");
		    		});
		    	});
				
				$("#stockTable").mngTable({
					columns: [
						{
							field: "stockNo",
							title: "股票代码",
							width: 100,
							sortable: true
						},
						{
							field: "stockNm",
							title: "股票名称"
						},
						{
							field: "op",
							title: "操作",
							width: 120,
							formatter: function(v, row, idx, fd) {
								return '<button type="button" class="tbl-btn del-btn">删除</button>';
							},
							events: {
								"click .del-btn": function(e, val, row, idx) {
									if (confirm("确认删除?")) {
			                        	$.ajaxPost("/stock/delStock", {stockNo : row.stockNo}, 
			                        		function(ajaxResp) {
								    			alert('删除成功!');
			                                    $("#refreshBtn3").trigger("click");
								    	});
			                        }
								}
							}
						}
					]
				});
				
				$("#addStockBtn").click(function() {
		    		$.showModal("/stock/addStock", $stockModal);
		    	});
				
				$("#refreshBtn3").click(function() {
		    		$("#refreshBtn3").html("刷新中...");
		    		$.ajaxPost("/stock/stockLst", null, function(respData) {
		    			$stockTable.bootstrapTable("load", respData.stockLst);
		    			$("#refreshBtn3").html("刷新");
		    		});
		    	});
		    	
		    	$('#stockTabs li:eq(0) a').tab('show');
		    	$("#refreshBtn1").trigger("click");
		    	$("#refreshBtn2").trigger("click");
		    	$("#refreshBtn3").trigger("click");
		    });
		    
		    function profitFormatter(value) {
		    	var f = parseFloat(value);
		        if (f < 0) {
		        	return '<span style="color:green;font-weight:bold;">' + value + ' %</span>';
		        } else if (f > 0) {
		        	return '<span style="color:red;font-weight:bold;"> + ' + value + ' %</span>';
		        } else {
		        	return '<span>' + value + '%</span>';
		        }
		    }
		    function profitAtFormatter(value) {
		    	var f = parseFloat(value);
		        if (f < 0) {
		        	return '<span style="color:green;font-weight:bold;">' + value + '</span>';
		        } else if (f > 0) {
		        	return '<span style="color:red;font-weight:bold;">' + value + '</span>';
		        } else {
		        	return '<span>' + value + '</span>';
		        }
		    }
		    function currPriceFormatter(value, row, index) {
		    	let b = parseFloat(row.buyInAt);
		    	let c = parseFloat(row.currPrice);
		    	if (c > b) {
		        	return '<span style="color:red;font-weight:bold;">' + value + '</span>';
		        } else if (c < b) {
		        	return '<span style="color:green;font-weight:bold;">' + value + '</span>';
		        } else {
		        	return '<span>' + value + '</span>';
		        }
		    }
		    function prepareSellOutAtFormatter(value, row, index) {
		    	let f = parseFloat(value);
		    	let c = parseFloat(row.currPrice);
		        if (c >= f) {
		        	return '<span style="color:red;font-weight:bold;">' + value + '</span>';
		        } else {
		        	return '<span style="color:#999;font-weight:bold;">' + value + '</span>';
		        }
		    }
		    function prepareBuyInAtFormatter(value, row, index) {
		    	let f = parseFloat(value);
		    	let c = parseFloat(row.currPrice);
		        if (c <= f) {
		        	return '<span style="color:red;font-weight:bold;">' + value + '</span>';
		        } else {
		        	return '<span style="color:#999;font-weight:bold;">' + value + '</span>';
		        }
		    }
		    function idxFormatter(value, row, index) {
		    	return index+1;
		    }
		</script>
	</body>
</html>