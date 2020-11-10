<div id="main-top">
	<ul id="breadcrumb">
		<li><a href="#"><i class="fa fa-home"></i></a></li>
	    <li><a href="#">股海浮沉</a></li>
	    <li><a href="#">股票管理</a></li>
    </ul>
</div>

<div id="main-content">
	<div class="mng-pane">
		<div class="toolbar" style="height: 33px;">
			<a id="add-btn" class="btn btn-primary" href="javascript:">新增</a>
	        <a id="refresh-btn" class="btn btn-primary" href="javascript:">刷新</a>
	    </div>
	    
	    <table id="stockTable"></table>
	</div>
	
    <div id="stock-mng-modal" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
            </div>
        </div>
	</div>
</div>

<script>
    $(function () {
    	var $stockMngModal = $('#stockModal').modal({show: false});
		var $stockMngTable = $("#stockTable").mngTable({
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
	                        	$.ajaxPost("/stock/stock/del", {stockNo : row.stockNo}, 
	                        		function(ajaxResp) {
						    			alert('删除成功!');
	                                    $("#refresh-btn").trigger("click");
						    	});
	                        }
						}
					}
				}
			]
		});
		
		$("#add-btn").click(function() {
    		$.showModal("/stock/stock/addStock", $stockMngModal);
    	});
		
		$("#refresh-btn").click(function() {
    		$("#refresh-btn").html("刷新中...");
    		$.ajaxPost("/stock/stock/qry", null, function(respData) {
    			$stockMngTable.bootstrapTable("load", respData.stockLst);
    			$("#refresh-btn").html("刷新");
    		});
    	});
    	
    	$("#refresh-btn").trigger("click");
    });
</script>
