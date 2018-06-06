var userTable;
$(function () {
    extendDateFun();
    userTable = refreshUser();
    init_button_handler();
});

function init_button_handler() {
    $("#addUser").click(function () {
        clear_user_form();
        $("#addUserModal").modal("show");
        $.ajax({
            url: "/admin/role/all",
            type: "post",
            dataType: "json",
            success: function (data) {
                if (data.status) {
                    $("#userForm select").empty();
                    for(var i = 0; i < data.entity.length; i++) {
                        var entity = data.entity[i];
                        var optionHtml = $("<option></option>").attr("value", entity.id).text(entity.name);
                        $("#userForm select").append(optionHtml);
                    }
                }
            }
        });
    });

    $("#cancelUser").click(function () {
        $("#addUserModal").modal("hide");
    });

    $("#saveUser").click(function () {
        $.ajax({
            url: "/admin/account/add",
            type: "post",
            dataType: "json",
            data: $("#userForm").serialize(),
            success: function (data) {
                if (data.status) {
                    $("#addUserModal").modal("hide");
                    clear_user_form();
                    userTable.ajax.reload(null, false);
                }
            }
        });
    });

    $("#deleteUser").click(function () {
        var rowData = userTable.rows(".selected").data();
        if (rowData.length === 0)
            return;
        var id = rowData[0].id;
        $.ajax({
            url: "/admin/account/delete",
            type: "post",
            dataType: 'json',
            data: {"id": id},
            success: function (data) {
                if (data.status) {
                    userTable.ajax.reload(null, false);
                }
            }
        });
    });
}

function clear_user_form() {
    $("#userForm")[0].reset();
    $("#userForm input[name='id']").val("");
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

function refreshUser() {
    var table = $('#userTable').DataTable({
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
        "fnCreatedRow": function( nRow, aData, iDataIndex ) {
            $(nRow).attr('id', aData.id);
        },
        "ajax": {
            "url": "/admin/account/list",
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
                "sTitle": "昵称",
                "mDataProp": "nickName",
                "sClass": "dt-center",
                "bSortable": false,
                "sWidth": "10%"
            },
            {
                "sTitle": "用户名",
                "mDataProp": "username",
                "sClass": "dt-center",
                "bSortable": false,
                "sWidth": "10%"
            }
        ]
    });
    table.on( 'draw', function () {
        $("#userTable tbody tr").click(function () {
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
