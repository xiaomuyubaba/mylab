;$(function() {
	$.loadPage = function(url, target) {
		$.get(url, function(data) {
			let cnt = "<div></div>";
			$("#" + target).empty().html(cnt.append(data));
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
		}
	});
});