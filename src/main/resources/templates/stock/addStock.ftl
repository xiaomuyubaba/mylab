<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
    	<span aria-hidden="true">&times;</span>
    </button>
    <h4 class="modal-title">添加股票</h4>
</div>
<div class="modal-body">
	<form class="form-horizontal" role="form">
		<div class="form-group">
			<label class="col-sm-2 control-label">股票号码:</label>
			<div class="col-sm-4">
				 <input type="text" class="form-control" name="stockNo">
			</div>
			<label class="col-sm-2 control-label">股票名称:</label>
			<div class="col-sm-4">
				 <input type="text" class="form-control" name="stockNm">
			</div>
	    </div>
	</form>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
    <button id="addStockSubmitBtn" type="button" class="btn btn-primary submit">提交</button>
</div>

<script>
	$("#addStockSubmitBtn").click(function () {
		var stockNo = $stockModal.find('input[name="stockNo"]').val();
		var stockNm = $stockModal.find('input[name="stockNm"]').val();
		$.ajax({
	        url: "/stock/addStock/submit",
	        type: 'post',
	        data: {
	        	stockNo : stockNo,
	        	stockNm : stockNm
	        },
	        success: function (data) {
	            $stockModal.modal('hide');
	            if (data == "succ") {
	            	alert('添加股票成功!');
	            } else {
	            	alert(data);
	            }
	        },
	        error: function (data) {
	            $stockModal.modal('hide');
	            alert('添加股票失败!');
	        }
	    });
	});
</script>