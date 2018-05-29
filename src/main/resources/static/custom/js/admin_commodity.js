
function init_add_commodity_handler() {

    $("#addCommodity").click(function () {
        $("#addCommodityModal").modal("show");
    });

    $("#deleteCommodity").click(function () {
        var rowData = commodityTable.rows('.selected').data();
        if (rowData.length === 0) {
            return;
        }
        $.ajax({
            url: "/admin/commodity/delete",
            type: "post",
            dataType: "json",
            data: {"id": $("#commodityTable tbody tr.selected").attr("id")},
            success: function (data) {
                if (data.status) {
                    commodityTable.ajax.reload(null, false);
                }
            }
        });
    });

    $("#editCommodity").click(function () {
        var rowData = commodityTable.rows('.selected').data();
        if (rowData.length === 0) {
            return;
        }
        var data = rowData[0];
        $("#addCommodityModal").modal("show");
        $("#addCommodityModal input[name='name']").val(data.name);
        $("#addCommodityModal input[name='type']").val(data.type);
        $("#addCommodityModal input[name='price']").val(data.price);
        $("#addCommodityModal input[name='unit']").val(data.unit);
        $("#addCommodityModal input[name='id']").val(data.id);
    });

    $("#saveCommodity").click(function () {
        $.ajax({
            url: "/admin/commodity/add",
            type: "POST",
            dataType: "json",
            data: $("#commodityForm").serialize(),
            success: function (data) {
                if (data.status) {
                    $("#addCommodityModal").modal("hide");
                    $("#commodityForm")[0].reset();
                    commodityTable.ajax.reload(null, false);
                } else {

                }
            }
        });
    });
}

function init_modal_handler() {
    $("body").on("hidden.bs.modal", '.modal', function () {
        $("body").css("padding-right", 0);
        $("#addCommodityModal").removeData('bs.modal');
    })
}

function refreshCommodity() {
    var table = $('#commodityTable').DataTable({
        "pagingType": "simple_numbers",//设置分页控件的模式
        searching: true,//屏蔽datatales的查询框
        aLengthMenu: [10],//设置一页展示10条记录
        "bLengthChange": false,//屏蔽tables的一页展示多少条记录的下拉列表
        info: false,
        "oLanguage": {  //对表格国际化
            "sLengthMenu": "每页显示 _MENU_条",
            "sZeroRecords": "没有找到符合条件的数据",
            //  "sProcessing": "&lt;img src=’./loading.gif’ /&gt;",
            "sInfo": "当前第 _START_ - _END_ 条　共计 _TOTAL_ 条",
            "sInfoEmpty": "没有记录",
            "sInfoFiltered": "(从 _MAX_ 条记录中过滤)",
            "sSearch": "搜索：",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "前一页",
                "sNext": "后一页",
                "sLast": "尾页"

            }
        },
        "fnDrawCallback": function () {
            this.api().column(0).nodes().each(function (cell, i) {
                cell.innerHTML = i + 1;
            });
        },
        order: [[0, "desc"]],
        "processing": false,
        "serverSide": true,
        "fnCreatedRow": function( nRow, aData, iDataIndex ) {
            $(nRow).attr('id', aData.id);
        },
        "ajax": {
            "url": "/admin/commodity/list",
            "dataSrc": "aaData"
        }, "aoColumns": [
            {
                "sTitle": "序号",
                "sClass": "dt-center",
                "bSortable": false,
                "sWidth": "4%",
                "data": null,
                "targets": 0
            },
            {
                "sTitle": "名称",
                "mDataProp": "name",
                "sClass": "dt-center",
                "bSortable": false,
                "sWidth": "12%"
            }, {
                "sTitle": "类型",
                "mDataProp": "type",
                "sClass": "dt-center",
                "bSortable": false,
                "sWidth": "12%"
            }, {
                "sTitle": "价格",
                "mDataProp": "price",
                "sClass": "dt-center",
                "bSortable": false,
                "sWidth": "10%"
            },
            {
                "sTitle": "单位",
                "mDataProp": "unit",
                "sClass": "dt-center",
                "bSortable": false,
                "sWidth": "10%"
            },
            {
                "sTitle": "更新日期",
                "mDataProp": "updateTime",
                "sClass": "dt-center",
                "bSortable": false,
                "sWidth": "10%",
                render: function (data, type, row) {
                    return (new Date(data)).Format("yyyy-MM-dd hh:mm:ss");
                }
            }
        ]
    });
    table.on( 'draw', function () {
        $("#commodityTable tbody tr").click(function () {
            if ($(this).hasClass("selected")) {
                $(this).removeClass("selected")
            } else {
                table.$("tr.selected").removeClass("selected");
                $(this).addClass("selected");
            }
        });
    } );
    return table;
}

function extendDateFun() {
    Date.prototype.Format = function (fmt) { //author: meizz
        var o = {
            "M+": this.getMonth() + 1,
            //月份
            "d+": this.getDate(),
            //日
            "h+": this.getHours(),
            //小时
            "m+": this.getMinutes(),
            //分
            "s+": this.getSeconds(),
            //秒
            "q+": Math.floor((this.getMonth() + 3) / 3),
            //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        }
        for (var k in o) {
            if (new RegExp("(" + k + ")").test(fmt)) {
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            }
        }
        return fmt;
    };
}
var commodityTable;
$(document).ready(function () {
    init_add_commodity_handler();
    init_modal_handler();
    commodityTable = refreshCommodity();
    extendDateFun();
});