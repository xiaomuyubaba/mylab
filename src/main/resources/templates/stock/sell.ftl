<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
    	<span aria-hidden="true">&times;</span>
    </button>
    <h4 class="modal-title">卖出</h4>
</div>
<div class="modal-body">
	<form id="position-sell-frm" class="form-horizontal" role="form" method="post" action="/stock/position/sell/submit">
		<input type="hidden" name="logId" value="${logInfo["logId"]}" />
		<div class="form-group">
	    	<label class="col-sm-2 control-label">股票:</label>
	    	<label class="col-sm-4 control-label">${logInfo["stockNo"]} - ${logInfo['stockNm']}</label>
	    	<label class="col-sm-2 control-label">数量:</label>
	    	<label class="col-sm-4 control-label">${logInfo["transNumber"]}</label>
	    </div>
	    <div class="form-group">
	    	<label class="col-sm-2 control-label">买入日期:</label>
	    	<label class="col-sm-4 control-label">${logInfo["buyInDt"]}</label>
	    	<label class="col-sm-2 control-label">买入价格:</label>
	    	<label class="col-sm-4 control-label">${logInfo["buyInAt"]} (元)</label>
	    </div>
	    <div class="form-group">
	        <label class="col-sm-2 control-label">卖出日期</label>
	        <div class="col-sm-4">
	        	<div id="sellOutDt" class="input-group date">
		        	<input name="sellOutDt" type="text" class="form-control" value="${today}" />
		        	<span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
		        </div>
			</div>
			<label class="col-sm-2 control-label">卖出价格</label>
			<div class="col-sm-4">
				 <input type="text" class="form-control" name="sellOutAt">
			</div>
	    </div>
	</form>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
    <button id="sellSubmitBtn" type="button" class="btn btn-primary submit">提交</button>
</div>

<script>
	$("#sellSubmitBtn").click(function() {
		var logId = $positionMngModal.find('input[name="logId"]').val();
		var sellOutDt = $positionMngModal.find('input[name="sellOutDt"]').val();
		var sellOutAt = $positionMngModal.find('input[name="sellOutAt"]').val();
		$.ajaxPost("/stock/position/delLog",
		    {
                logId : logId,
                sellOutDt : sellOutDt,
                sellOutAt : sellOutAt
            },
            function(ajaxResp) {
                alert('卖出成功!');
                $positionMngModal.modal('hide');
                $positionMngTbl.bootstrapTable('refresh');
        });

        $("#sellOutDt").datepicker({
            "autoclose":true,"format":"yyyymmdd","language":"zh-CN"
        });
	});
</script>