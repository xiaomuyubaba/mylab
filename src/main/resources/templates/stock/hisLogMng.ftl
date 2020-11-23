<div id="main-top">
	<ul id="breadcrumb">
		<li><a href="#"><i class="fa fa-home"></i></a></li>
	    <li><a href="#">股海浮沉</a></li>
	    <li><a href="#">历史记录</a></li>
    </ul>
</div>

<div id="main-content">
    <div class="mng-pane">
        <div class="toolbar" style="height: 33px;">
            <a id="refresh-btn" class="btn btn-primary" href="javascript:">刷新</a>
        </div>
        <table id="his-log-tbl"></table>
    </div>
</div>

<script>
    var $hisLogTable = $("#his-log-tbl");
    $(function () {
        $hisLogTable.mngTable({
            columns: [
                {
                    field: "idx",
                    title: "序号",
                    width: 60,
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
                    sortable: true
                },
                {
                    field: "buyInAt",
                    title: "买入价",
                    width: 90
                },
                {
                    field: "sellOutDt",
                    title: "卖出日期",
                    width: 90,
                    sortable: true
                },
                {
                    field: "sellOutAt",
                    title: "卖出价",
                    width: 90
                },
                {
                    field: "profit",
                    title: "盈亏",
                    sortable: true,
                    formatter: profitFormatter
                },
                {
                    field: "profitAt",
                    title: "盈亏金额",
                    width: 120,
                    sortable: true,
                    formatter: profitAtFormatter
                }
            ]
        });
        $("#refresh-btn").click(function() {
            $.ajaxPost("/stock/hisLog/qry", {}, function(respData) {
                $hisLogTable.bootstrapTable("load", respData.logLst);
            });
        });
        $("#refresh-btn").trigger("click");
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