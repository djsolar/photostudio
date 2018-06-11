$(function () {
    init_daterangepicker_single_call();
    init_button_bind();
    get_all_activity();
});

function init_button_bind() {
    $("#addActivity").click(function () {
        clear_activity_form();
        $("#addActivityModal h4.modal-title").text("添加活动");
        $("#addActivityModal").modal("show");
    });
    $("#cancelActivity").click(function () {
        $("#addActivityModal").modal("hide");
    });

    $("#saveActivity").click(function () {
        $("#activityForm").ajaxSubmit({
            url: "/admin/activity/add",
            type: "post",
            success: function (data) {
                if (data.status) {
                    clear_activity_form();
                    $("#addActivityModal").modal("hide");
                    get_all_activity();
                }
            }
        });
    });
}

function get_all_activity() {
    $("div.x_content").empty();
    $.ajax({
        url: "/admin/activity/list",
        type: "post",
        dataType: "json",
        success: function (data) {
            if (data.status) {
                $("div.x_content").append(template("activityList", {"entity": data.entity}));
                init_activity_operation();
            }
        }
    });
}

function clear_activity_form() {
    $("#activityForm")[0].reset();
    $("#activityForm input[name='id']").val("");
}

function init_activity_operation() {
    $("a.delete").click(function () {
        var id = $(this).parent().attr("id");
        $.ajax({
            url: "/admin/activity/delete",
            type: "post",
            dataType: "json",
            data: {"id": id},
            success: function (data) {
                if (data.status) {
                    get_all_activity();
                }
            }
        });
    });

    $("a.edit").click(function () {
        var id = $(this).parent().attr("id");
        $.ajax({
            url: "/admin/activity/getOne",
            type: "post",
            dataType: "json",
            data: {"id": id},
            success: function (data) {
                var entity = data.entity;
                console.log(entity);
                $("#addActivityModal h4.modal-title").text("编辑活动");
                $("#addActivityModal").modal("show");
                $("#activityForm input[name='caption']").val(entity.caption);
                $("#activityForm input[name='description']").val(entity.description);
                $("#activityForm input[name='startDate']").val(entity.startDate);
                $("#activityForm input[name='endDate']").val(entity.endDate);
                $("#activityForm input[name='id']").val(entity.id);
            }

        });
    });
    $("a.eye").click(function () {
        var id = $(this).parent().attr("id");
        $.ajax({
            url: "/admin/activity/eye",
            type: "post",
            dataType: "json",
            data: {"id": id},
            success: function (data) {
                if (data.status) {
                    $("#eyeActivityContent").modal("show");
                    $("#activityDescription").text("活动描述：" + data.entity.description);
                    $("#activityContent").attr("src", "/photo/" + data.entity.imageName);
                }
            }
        });
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