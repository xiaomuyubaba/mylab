<div id="main-top">
	<ul id="breadcrumb">
		<li><a href="#"><i class="fa fa-home"></i></a></li>
	    <li><a href="#">股海浮沉</a></li>
	    <li><a href="#">当前持仓</a></li>
    </ul>
</div>

<div id="main-content">
	<div class="mng-pane">
		<div class="toolbar" style="height: 33px;">
		    <select id="stockNoSel" style="width: 150px; height: 28px; margin-right: 10px;">
                <option value="">--全部--</option>
                <#list stockList as stock>
                    <option value="${stock.stockNo}">${stock.stockNo} - ${stock.stockNm}</option>
                </#list>
            </select>
            <a id="qry-btn" class="btn btn-primary" href="javascript:">查询</a>
			<a id="buy-btn" class="btn btn-warning" href="javascript:">买入</a>
		</div>
		<table id="position-mng-tbl"></table>
	</div>
	
	<div id="position-mng-modal" class="modal"></div>
</div>

<script>
    var $positionMngModal = $("#position-mng-modal").mngModal();
    var $positionMngTbl;
    $(function () {
		$positionMngTbl = $("#position-mng-tbl").mngTable({
			columns: [
				{
					field: "idx",
					title: "序号",
					width: 50,
					align: "center",
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
					formatter: function(v, row, idx, fd) {
						let f = parseFloat(v);
				        if (f < 0) {
				        	return '<span style="color:green;font-weight:bold;">' + v + ' %</span>';
				        } else if (f > 0) {
				        	return '<span style="color:red;font-weight:bold;"> + ' + v + ' %</span>';
				        } else {
				        	return '<span>' + v + '%</span>';
				        }
					}
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
							$.showModal("/stock/position/sell?logId=" + row.logId, $positionMngModal);
						},
						"click .del-btn": function(e, val, row, idx) {
							if (confirm("确认删除?")) {
	                        	$.ajaxPost("/stock/delPositionLog", {logId : row.logId},
	                        		function(ajaxResp) {
						    			alert('删除成功!');
	                                    $("#qry-btn").trigger("click");
						    	});
	                        }
						}
					}
				}
			]
		});
    	
    	$("#buy-btn").click(function() {
    		$.showModal("/stock/position/buy", $positionMngModal);
    	});
    	
    	$("#qry-btn").click(function() {
    		$positionMngTbl.bootstrapTable("resetView");
    		$.ajaxPost("/stock/position/qry",
    		    {
    		        _QRY_stockNo: $("#stockNoSel").val()
    		    },
    			function(respData) {
    				$positionMngTbl.bootstrapTable("load", respData.logLst);
    		});
    	});
    	
    	$("#qry-btn").trigger("click");
    });
    
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
</script>
