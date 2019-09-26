<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
    	<title>穷庐</title>
    	<link href="/bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet">
    	<link rel="stylesheet" href="/bootstrap-table/bootstrap-table.css">
    	<link rel="stylesheet" href="/bootstrap-datepicker/css/bootstrap-datepicker3.min.css">
    	<link rel="stylesheet" type="text/css" href="/css/jquery-accordion-menu.css"></link>
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
	        			<ul id="stockTab" class="nav nav-tabs">
						    <li>
						    	<a href="#logTab" tblId="#logTable" data-toggle="tab">当前持仓</a>
						    </li>
	    					<li><a href="#hisLogTab" tblId="#hisLogTable" data-toggle="tab">历史记录</a></li>
	    				</ul>
				    				
	    				<div id="stockTabContent" class="tab-content">
						    <div class="tab-pane active" id="logTab">
								<div id="toolbar" class="toolbar" style="height: 33px;">
									<a id="buyBtn" class="btn btn-primary" href="javascript:">买入</a>
									<a id="sellBtn" class="btn btn-primary" href="javascript:">卖出</a>
									<a id="addStockBtn" class="btn btn-primary" href="javascript:">添加股票</a>
									<a id="refreshBtn" class="btn btn-primary" href="javascript:">点击刷新</a>
									<span class="alert"></span>
								</div>
										
								<table id="logTable"
									data-toggle="table" 
						        	data-url="/stock/position/logLst?status=0"
						        	data-search='true'
									data-show-toggle='true'
									data-show-columns='true'
									data-height='528'
									data-show-footer='false'
									data-click-to-select='true'
									data-toolbar='#toolbar'
									data-striped="true"
									data-sort-stable='true'
									data-check-radio='true'>
						            <thead>
			                            <tr>
			                            	<th data-radio="true" data-width="30"></th>
			                            	 <th data-field="idx" data-width="40"
			                            	 	data-align="center"
			                            	 	data-formatter="idxFormatter"
			                            	 	>序号</th>
			                                <th data-field="stockNo" data-width="120">股票代码</th>
			                                <th data-field="stockNm" data-sortable="true">股票名称</th>
			                                <th data-field="transNumber">股票数</th>
			                                <th data-field="buyInDt">买入日期</th>
			                                <th data-field="buyInAt">买入价</th>
			                                <th data-field="currPrice"
			                                	data-formatter="currPriceFormatter">当前价</th>
			                                <th data-field="prepareSellOutAt"
			                                	data-formatter="prepareSellOutAtFormatter">计划卖出价</th>
			                                <th data-field="prepareBuyInAt"
			                                	data-formatter="prepareBuyInAtFormatter">计划补仓价</th>
			                                <th data-field="profit"
			                                	data-sortable="true"
			                                	data-formatter="profitFormatter">盈亏比</th>
			                            </tr>
		                            </thead>
						        </table>
						    </div>
						    
						    <div class="tab-pane" id="hisLogTab">
						        <table id="hisLogTable" 
						        	data-toggle="table"
						        	data-url="/stock/position/logLst?status=1"
						        	data-show-refresh='true'
						        	data-search='true'
									data-show-toggle='true'
									data-show-columns='true'
									data-height='528'
									data-show-footer='false'
									data-striped="true"
									data-sort-stable='true'>
						            <thead>
			                            <tr>
			                                <th data-field="stockNo" data-width="120">股票代码</th>
			                                <th data-field="stockNm">股票名称</th>
			                                <th data-field="transNumber">股票数</th>
			                                <th data-field="buyInDt">买入日期</th>
			                                <th data-field="buyInAt">买入价</th>
			                                <th data-field="sellOutDt">卖出日期</th>
			                                <th data-field="sellOutAt">卖出价</th>
			                                <th data-field="profit"
			                                	data-sortable="true"
			                                	data-formatter="profitFormatter">盈亏比</th>
			                                <th data-field="profitAt"
			                                	data-sortable="true"
			                                	data-formatter="profitAtFormatter">盈亏金额</th>
			                            </tr>
		                            </thead>
						        </table>
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
		<script src="/js/jquery-accordion-menu.js"></script>
		
		<script>
		    var $logTable = $('#logTable'),
		    	$hisLogTable = $("#hisLogTable"),
		        $stockModal = $('#stockModal').modal({show: false});
		        
		    $(function () {
		    	jQuery("#jquery-accordion-menu").jqueryAccordionMenu();
		    
		    	$('#stockTab li:eq(0) a').tab('show');
		    	$('#stockTab a').click(function (e) {
		    		e.preventDefault();
		    		$(this).tab('show');
		    		$($(this).attr("tblId")).bootstrapTable("resetView");
				});
		    	
		    	$("#buyBtn").click(function() {
		    		$stockModal.find('.modal-content').html("");
	    			$.get("/stock/buy", function(data) {
		        		$stockModal.find('.modal-content').html(data);
		        		$stockModal.modal('show');
		        	});
		    	});
		    	
		    	$("#sellBtn").click(function() {
		    		var rows = $logTable.bootstrapTable('getSelections');
		    		if (rows && rows.length == 1) {
		    			$stockModal.find('.modal-content').html("");
		    			$.get("/stock/sell?logId=" + rows[0].logId, function(data) {
			        		$stockModal.find('.modal-content').html(data);
			        		$stockModal.modal('show');
			        	});
		    		} else {
		    			alert("请选择一条记录");
		    		}
		    	});
		    	
		    	$("#refreshBtn").click(function() {
		    		$(this).html("刷新中...");
		    		var url = "/stock/position/logLst?status=0";
		    		$.get(url, function(data) {
		    			$logTable.bootstrapTable("load", data);
		    			$("#refreshBtn").html("点击刷新");
		    		});
		    	});
		    	
		    	$("#addStockBtn").click(function() {
	    			$stockModal.find('.modal-content').html("");
	    			$.get("/stock/addStock", function(data) {
		        		$stockModal.find('.modal-content').html(data);
		        		$stockModal.modal('show');
		        	});
		    	});
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