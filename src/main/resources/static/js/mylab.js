;$(function() {
	$.loadPage = function(url, target) {
		$.get(url, function(data) {
			$("#" + target).empty().html(data);
		});
	}
	
	$.showModal = function(url, md) {
		md.find('.modal-content').html("");
		$.get(url, function(data) {
			md.find('.modal-content').html(data);
			md.modal('show');
    	});
	}
	
	$.hideModal = function(md) {
		md.modal("hide");
		md.find(".modal-content").empty();
	}
	
	$.ajaxPost = function(url, postData, succCallBack, failCallBack, errCallBack) {
		$.ajax({
	        url: url,
	        type: "post",
	        dataType:"json",
	        data: postData,
	        success: function(ajaxResult) {
	        	$.processAjaxResult(ajaxResult, succCallBack, failCallBack);
	        },
	        error: function(ajaxResult) {
	        	if (typeof errCallBack === "function") {
	        		errCallBack(ajaxResult);
	        	} else {
	        		alert("请求异常");
	        	}
	        }
	    });
	}
	
	$.encodeUrl = function(url) {
		return encodeURIComponent(url);
	}
	
	$.processAjaxResult = function(r, succCallBack, failCallBack) {
		var code = r.respCode;
		if ("00" == code) {
			if (typeof succCallBack == "function") {
				succCallBack(r.respData)
			}
		} else {
			if (typeof failCallBack == "function") {
				failCallBack(r)
			} else {
				alert(r.respMsg);
			}
		}
	}
	
	$.autoCompleteOff = function() {
		$("input:not([autocomplete])").attr("autocomplete", "off");
	}
	
	$.logObj = function(obj) {
		console.log(JSON.stringify(obj));
	}
	
	$.fn.extend({
		mngTable : function(opts) {
			opts = $.extend({
				striped: false,
                pagination: false,
                height: 520,
                showColumns: false,
                showToggle: false,
                clickToSelect: true
			}, opts);
			$(this).bootstrapTable(opts);
			return $(this);
		},
		mngModal : function(opts) {
			$(this).append('<div class="modal-dialog"><div class="modal-content"></div></div>');
			$(this).modal({show: false});
			return $(this);
		},
		qryFrm : function(opts) {
			opts = $.extend({
				autoQry: true
			}, opts);
			
			let $qryForm = $(this);
		    let $qryTbl = $(this).find(".qry-rslt-tbl");
		    let $qryBtn = $(this).find(".qry-btn");
	    	let $mngModal = $(this).find(".mng-modal");
		    let $pagination = $(this).find(".qry-pagination");
		    
		    $qryForm.validate({
		        submitHandler: function(form) {
		            $qryBtn.attr("disabled", "disabled");
		            $(form).ajaxSubmit({
		                type: "post",
		                dataType: "json",
		                success: function(resp) {
		                    $.dealAjaxResp(resp, function(data) {
		                        $qryBtn.removeAttr("disabled");
		                        if ($pagination.length > 0) {
		                        	$qryTbl.bootstrapTable("load", data.pager.rslt);
			                        $pagination.mngPagination($qryForm, data.pager);
		                        } else {
		                        	$qryTbl.bootstrapTable("load", data.lst);
		                        }
		                    });
		                }
		            });
		        },
		        errorPlacement: function(error, element) {
		            error.appendTo(element.parent());
		        }
		    });
		    
		    $qryTbl.mngTable(opts.tblOtps);
		    
		    $qryBtn.click(function() {
		    	$qryForm.find('input[name="pageNum"]').val("1");
		        $qryForm.submit();
		    });
		    
		    $qryForm.find(".search-cancle-btn").click(function() {
		    	$qryForm.find(".fixed-table-toolbar .search input").val("");
		    	$qryForm.find(".fixed-table-toolbar .search select").val("");
		        $qryBtn.trigger("click");
		    });
		    
		    $qryForm.find(".add-btn").click(function() {
		    	$.showModal(opts.baseUrl + "/add", $mngModal);
		    });
		    
		    if (opts.autoQry) {
		    	$qryForm.submit();
		    }
		    
		    return $.extend($(this), {
		    	qryTbl: $qryTbl,
		    	refresh: function() {
		    		$mngModal.modal("hide");
		    		$mngModal.find(".modal-content").empty();
		    		$qryForm.submit();
		    	},
		    	showMngModal: function(url) {
		    		$.showModal(url, $mngModal);
		    	},
		    	hideMngModal: function() {
		    		$mngModal.modal("hide");
		    		$mngModal.find(".modal-content").empty();
		    	},
		    	getSelections: function() {
		    		return $qryTbl.bootstrapTable("getSelections");
		    	}
		    });
		}
	});
});