$(function () {
    refreshEvent();
});

function refreshEvent() {
    var table = $('#eventTable').DataTable({
        "pagingType": "simple_numbers",//设置分页控件的模式
        searching: true,//屏蔽datatales的查询框
        aLengthMenu: [10],//设置一页展示10条记录
        "bLengthChange": false,//屏蔽tables的一页展示多少条记录的下拉列表
        info: false,
        destroy: true,
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
        "fnCreatedRow": function (nRow, aData, iDataIndex) {
            $(nRow).attr('id', aData.id);
        },
        "ajax": {
            "url": "/admin/home/getevents",
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
                "sTitle": "先生姓名",
                "mDataProp": "manName",
                "sClass": "dt-center",
                "bSortable": false,
                "sWidth": "10%"
            }, {
                "sTitle": "小姐姓名",
                "mDataProp": "womenName",
                "sClass": "dt-center",
                "bSortable": false,
                "sWidth": "10%"
            }, {
                "sTitle": "电话号码",
                "mDataProp": "phone",
                "sClass": "dt-center",
                "bSortable": false,
                "sWidth": "10%"
            },{
                "sTitle": "事件",
                "mDataProp": "event",
                "sClass": "dt-center",
                "bSortable": false,
                "sWidth": "10%"
            }
        ]
    });
    return table;
}
