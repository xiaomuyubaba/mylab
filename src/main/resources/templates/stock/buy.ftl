<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
    	<span aria-hidden="true">&times;</span>
    </button>
    <h4 class="modal-title">买入</h4>
</div>
<div class="modal-body">
	<form id="add-stock-frm" class="form-horizontal" role="form" method="post" action="/stock/position/buy/submit">
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
		        	<input name="buyInDt" type="text" class="form-control" value="${today}">
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
    <button id="buy-submit-btn" type="button" class="btn btn-primary submit">提交</button>
</div>

<script>
    ;$(function() {
        $("#buy-submit-btn").click(function () {
            $("#add-stock-frm").ajaxSubmit({
                type: "post",
                dataType: "json",
                success: function(resp) {
                    $.processAjaxResult(resp, function(respData) {
                        alert('买入成功!');
                        $positionMngModal.modal('hide');
                        $("#qry-btn").trigger("click");
                    });
                }
            });
        });
        $("#buyInDt").datepicker({
            "autoclose":true,
            "format":"yyyymmdd",
            "language":"zh-CN"
        });
    });
</script>