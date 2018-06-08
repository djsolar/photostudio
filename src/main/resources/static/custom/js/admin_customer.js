var customerTable;
$(function () {
    extendDateFun();
    init_daterangepicker_single_call();
    init_modal_handler();
    customerTable = refreshCustomer();
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

function refreshCustomer() {
    var table = $('#customerTable').DataTable({
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
            "url": "/admin/customer/list",
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
            },
            {
                "sTitle": "地址",
                "mDataProp": "address",
                "sClass": "dt-center",
                "bSortable": false,
                "sWidth": "10%"
            },
            {
                "sTitle": "佳期",
                "mDataProp": "weedingDate",
                "sClass": "dt-center",
                "bSortable": false,
                "sWidth": "10%",
                render: function (data, type, row) {
                    return (new Date(data)).Format("yyyy-MM-dd");
                }
            },
            {
                "sTitle": "更新时间",
                "mDataProp": "updateTime",
                "sClass": "dt-center",
                "bSortable": false,
                "sWidth": "12%",
                render: function (data, type, row) {
                    return (new Date(data)).Format("yyyy-MM-dd hh:mm:ss");
                }
            },
            {
                "sTitle": "操作",
                "mDataProp": "order",
                "sClass": "dt-center",
                "bSortable": false,
                "sWidth": "10%",
                render: function (data, type, row) {
                    if (data == null) {
                        return "<button customerId=\"" + row.id + "\" type=\"button\" class=\"btn btn-primary btn-xs\">  添加订单 </button>";
                    } else {
                        return "<button orderId= \"" + data.id + "\" type=\"button\" class=\"btn btn-success btn-xs\"> 查看订单 </button>";

                    }
                }
            }
        ]
    });
    table.on('draw', function () {
        $("#customerTable tbody tr").click(function () {
            if ($(this).hasClass("selected")) {
                $(this).removeClass("selected")
            } else {
                table.$("tr.selected").removeClass("selected");
                $(this).addClass("selected");
            }
        });
        $("button[customerId]").click(function () {
            $("#addOrderModal").modal("show");
            var customerId = $(this).attr("customerId");
            $.ajax({
                url: "/admin/service/all",
                success: function (data) {
                    console.log(data);
                    if (data.status) {
                        clear_order_form();
                        $("#addOrderModal input[name='customerId']").val(customerId);
                        var $SELECT = $("select.select2_multiple");
                        $SELECT.empty();
                        for (var i = 0; i < data.entity.length; i++) {
                            var e = data.entity[i];
                            $SELECT.append($("<option></option>").text(e.name).attr("value", e.id).attr("price", e.price));
                        }
                    }
                }
            })
        });
        $("button[orderId]").click(function () {
            $("#addOrderModal").modal("show");
            clear_order_form();
            $.ajax({
                url: "/admin/order/get",
                type: "post",
                dataType: "json",
                data: {"orderId": $(this).attr("orderId")},
                success: function (data1) {
                    if (data1.status) {
                        var entity = data1.entity;
                        $.ajax({
                            url: "/admin/service/all",
                            success: function (data) {
                                if (data.status) {
                                    var $SELECT = $("select.select2_multiple");
                                    $SELECT.empty();
                                    for (var i = 0; i < data.entity.length; i++) {
                                        var e = data.entity[i];
                                        $SELECT.append($("<option></option>").text(e.name).attr("value", e.id).attr("price", e.price));
                                    }
                                    $("#addOrderModal input[name='takePhotoTime']").val(entity.takePhotoTime);
                                    $("#addOrderModal input[name='selectPhotoTime']").val(entity.selectPhotoTime);
                                    $("#addOrderModal input[name='pickPhotoTime']").val(entity.pickPhotoTime);
                                    $("#addOrderModal select.select2_multiple").val(entity.serviceId);
                                    $("#addOrderModal input[name='remark']").val(entity.remark);
                                    $("#addOrderModal input[name='price']").val(entity.price);
                                    $("#addOrderModal input[name='earnest']").val(entity.earnest);
                                    $("#addOrderModal input[name='customerId']").val(entity.customerId);
                                    $("#addOrderModal input[name='id']").val(entity.id);
                                    $("#addOrderModal textarea").text(entity.remark);
                                }
                            }
                        });
                    }
                }
            });
        });
    });
    return table;
}

function clear_custom_form() {
    $("#customerForm")[0].reset();
    $("#addCustomerModal input[name='id']").val("");
}

function clear_order_form() {
    $("#orderForm")[0].reset();
    $("#addOrderModal input[name='customerId']").val("");
    $("#addOrderModal input[name='orderId']").val("");
}

function init_modal_handler() {
    $("#addCustomer").click(function () {
        $("#myModalLabel").text("添加客户");
        clear_custom_form();
        $("#addCustomerModal").modal("show");
    });

    $("#deleteCustomer").click(function () {
        var rowData = customerTable.rows(".selected").data();
        if (rowData.length === 0)
            return;
        var data = rowData[0];
        $.ajax({
            url: "/admin/customer/delete",
            type: "post",
            dataType: "json",
            data: {"id": data.id},
            success: function (data) {
                if (data.status) {
                    customerTable.ajax.reload(null, false);
                }
            }
        })
    });

    $("#editCustomer").click(function () {
        var rowData = customerTable.rows(".selected").data();
        if (rowData.length === 0)
            return;
        var data = rowData[0];
        $("#myModalLabel").text("编辑客户");
        $("#addCustomerModal").modal("show");
        $("#addCustomerModal input[name='manName']").val(data.manName);
        $("#addCustomerModal input[name='womenName']").val(data.womenName);
        $("#addCustomerModal input[name='phone']").val(data.phone);
        $("#addCustomerModal input[name='address']").val(data.address);
        $("#addCustomerModal input[name='weedingDate']").val(data.weedingDate);
        $("#addCustomerModal input[name='id']").val(data.id);
    });

    $("#cancelAddCustomer").click(function () {
        $("#addCustomerModal").modal("hide")
    });

    $("#saveCustomer").click(function () {
        if ($("#customerForm").data('bootstrapValidator').validate().isValid()) {
            $.ajax({
                url: "/admin/customer/add",
                type: "post",
                dataType: "json",
                data: $("#customerForm").serialize(),
                success: function (data) {
                    if (data.status) {
                        $("#addCustomerModal").modal("hide");
                        clear_custom_form();
                        customerTable.ajax.reload(null, false);
                    }
                }
            });
        }
    });

    $("select.select2_multiple").change(function () {
        var options = $("select.select2_multiple option:selected");
        console.log(options.attr("price"));
        $("input[name='price']").val(options.attr("price"));
    });

    $("#cancelOrder").click(function () {
        $("#addOrderModal").modal("hide");
    });

    $("#saveOrder").click(function () {
        if ($("#orderForm").data('bootstrapValidator').validate().isValid()) {
            $.ajax({
                url: "/admin/order/add",
                type: "post",
                dataType: "json",
                data: $("#orderForm").serialize(),
                success: function (data) {
                    clear_order_form();
                    $("#addOrderModal").modal("hide");
                    customerTable.ajax.reload(null, false);
                }
            });
        }
    });

    $("#customerForm").bootstrapValidator({
        excluded: [':disabled', ':hidden', ':not(:visible)'],
        message: "验证失败",
        feedbackIcons: {//根据验证结果显示的各种图标
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            manName: {
                validators: {
                    notEmpty: {
                        message: '先生姓名不能为空'
                    }
                }
            },
            womenName: {
                validators: {
                    notEmpty: {
                        message: '小姐姓名不能为空'
                    }
                }
            },
            phone: {
                message: '手机号码不合法',
                validators: {
                    notEmpty: {
                        message: '手机号码不能为空'
                    },
                    stringLength: {
                        min: 11,
                        max: 11,
                        message: '请输入11位手机号码'
                    },
                    regexp: {
                        regexp: /^1[3|5|8]{1}[0-9]{9}$/,
                        message: '请输入正确的手机号码'
                    }
                }
            },
            address: {
                validators: {
                    notEmpty: {
                        message: '地址不能为空'
                    }
                }
            },
            weedingDate: {
                validators: {
                    notEmpty: {
                        message: ' 佳期不能为空'
                    }
                }
            }
        }
    });

    $("#orderForm").bootstrapValidator({
        excluded: [':disabled', ':hidden', ':not(:visible)'],
        message: "验证失败",
        feedbackIcons: {//根据验证结果显示的各种图标
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            takePhotoTime: {
                validators: {
                    notEmpty: {
                        message: '拍摄日期不能为空'
                    }
                }
            },
            selectPhotoTime: {
                validators: {
                    notEmpty: {
                        message: '选片日期不能为空'
                    }
                }
            },
            pickPhotoTime: {
                notEmpty: {
                    message: '选片日期不能为空'
                }
            },
            serviceId: {
                validators: {
                    notEmpty: {
                        message: '套系不能为空'
                    }
                }
            }
        }
    });
}

function init_daterangepicker_single_call() {

    if (typeof ($.fn.daterangepicker) === 'undefined') {
        return;
    }
    console.log('init_daterangepicker_single_call');

    var option = {
        "singleDatePicker": true,
        "autoApply": true,
        "locale": {
            "format": "YYYY-MM-DD",
            "separator": " - ",
            "applyLabel": "Apply",
            "cancelLabel": "Cancel",
            "fromLabel": "From",
            "toLabel": "To",
            "customRangeLabel": "Custom",
            "weekLabel": "W",
            "daysOfWeek": [
                "日",
                "一",
                "二",
                "三",
                "四",
                "五",
                "六"
            ],
            "monthNames": [
                "一月",
                "二月",
                "三月",
                "四月",
                "五月",
                "六月",
                "七月",
                "八月",
                "九月",
                "十月",
                "十一月",
                "十二月"
            ],
            "firstDay": 1
        },
        "opens": "right"
    }

    $("input[aria-describedby='inputSuccess2Status4']").daterangepicker(option, function (start, end, label) {
        console.log('New date range selected: ' + start.format('YYYY-MM-DD') + ' to ' + end.format('YYYY-MM-DD') + ' (predefined range: ' + label + ')');
    });
}