<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
    	<span aria-hidden="true">&times;</span>
    </button>
    <h4 class="modal-title">买入</h4>
</div>
<div class="modal-body">
	<form class="form-horizontal" role="form">
		<div class="form-group">
			<label class="col-sm-2 control-label">选择股票:</label>
			<div class="col-sm-4">
				 <select name="stockNo" class="form-control">
		        	<option value="">--请选择--</option>
		        	<#list stockList as stock>
		        		<option value="${stock.stockNo}">${stock.stockNo} - ${stock.stockNm}</option>
		        	</#list>
		        </select>
			</div>
			<label class="col-sm-2 control-label">买入日期:</label>
			<div class="col-sm-4">
				 <div id="buyInDt" class="input-group date">
		        	<input name="buyInDt" type="text" class="form-control">
		        	<span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
				</div>
			</div>
	    </div>
	    <div class="form-group">
			<label class="col-sm-2 control-label">买入价格:</label>
			<div class="col-sm-4">
				 <input type="text" class="form-control" name="buyInAt">
			</div>
			<label class="col-sm-2 control-label">买入数量:</label>
			<div class="col-sm-4">
				 <input type="text" class="form-control" name="buyInNum">
			</div>
	    </div>
	</form>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
    <button id="buySubmitBtn" type="button" class="btn btn-primary submit">提交</button>
</div>

<script>
	$("#buySubmitBtn").click(function () {
		var stockNo = $stockModal.find('select[name="stockNo"]').val();
		var buyInDt = $stockModal.find('input[name="buyInDt"]').val();
		var buyInAt = $stockModal.find('input[name="buyInAt"]').val();
		var buyInNum = $stockModal.find('input[name="buyInNum"]').val();
		$.ajax({
	        url: "/stock/buy/submit",
	        type: 'post',
	        data: {
	        	stockNo : stockNo,
	        	buyInDt : buyInDt,
	        	buyInAt : buyInAt,
	        	buyInNum : buyInNum
	        },
	        success: function (data) {
	            $stockModal.modal('hide');
	            if (data == "succ") {
	            	alert('买入成功!');
	            	$logTable.bootstrapTable('refresh');
	            } else {
	            	alert(data);
	            }
	        },
	        error: function (data) {
	            $stockModal.modal('hide');
	            alert('买入失败!');
	        }
	    });
	});
	$("#buyInDt").datepicker({
	    "autoclose":true,"format":"yyyymmdd","language":"zh-CN"
	});
</script>