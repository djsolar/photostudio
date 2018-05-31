var serviceTable;
$(function () {
    extendDateFun();
    serviceTable = refreshServicePackage();
    init_modal_handler();
});

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

function init_modal_handler() {
    $("#addPackage").click(function () {
        $("#addServicePackageModal").modal("show");
    });
}

function refreshServicePackage() {
    var table = $('#servicePackageTable').DataTable({
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
        "processing": false,
        "serverSide": true,
        "fnCreatedRow": function( nRow, aData, iDataIndex ) {
            $(nRow).attr('id', aData.id);
        },
        "ajax": {
            "url": "/admin/service/list",
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
                "sTitle": "套系名称",
                "mDataProp": "name",
                "sClass": "dt-center",
                "bSortable": false,
                "sWidth": "12%"
            }, {
                "sTitle": "衣服套数",
                "mDataProp": "clothesNumber",
                "sClass": "dt-center",
                "bSortable": false,
                "sWidth": "4%"
            }, {
                "sTitle": "拍摄照片数",
                "mDataProp": "photoNumber",
                "sClass": "dt-center",
                "bSortable": false,
                "sWidth": "4%"
            },
            {
                "sTitle": "可选照片数",
                "mDataProp": "pickPhotoNumber",
                "sClass": "dt-center",
                "bSortable": false,
                "sWidth": "4%"
            },
            {
                "sTitle": "套系金额",
                "mDataProp": "price",
                "sClass": "dt-center",
                "bSortable": false,
                "sWidth": "6%"
            },
            {
                "sTitle": "备注",
                "mDataProp": "remark",
                "sClass": "dt-center",
                "bSortable": false,
                "sWidth": "10%"
            },
            {
                "sTitle": "更新时间",
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
        $("#servicePackageTable tbody tr").click(function () {
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