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
		<div id="sidebar">
			<ul class="side-menu">
				<li class="active">
					<a href="#" title="股海浮沉"><i class="fa fa-bar-chart"></i></a>
					<ul>
						<li class="sub-menu" target="/stock/position/mng"><a href="javascript:void(0);">当前持仓</a></li>
						<li class="sub-menu" target="/stock/hisLog/mng"><a href="javascript:void(0);">历史记录</a></li>
						<li class="sub-menu" target="/stock/stock/mng"><a href="javascript:void(0);">股票管理</a></li>
					</ul>
				</li>
			</ul>
		</div>
    		
		<div id="main"></div>

		<script src="/js/jquery.min.js"></script>
		<script src="/bootstrap-3.3.7/js/bootstrap.min.js"></script>
		<script src="/bootstrap-table/bootstrap-table.js"></script>
		<script src="/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
		<script src="/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
		<script src="/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
		<script src="/js/mylab.js"></script>

		<script>
		    $(function () {
		    	$("#sidebar").find("li.sub-menu").click(function(e) {
		    		let url = $(this).attr("target");
		    		$.loadPage(url, "main");
		    		e.preventDefault();
		    		return false;
		    	});
		    	$("#sidebar").find("li.sub-menu").first().trigger("click");
		    });
		</script>
	</body>
</html>