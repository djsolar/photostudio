
function init_menu_li() {
    $("li[url]").click(function () {
        var url = $(this).attr("url");
       $("div.right_col").load(url);
    });
}

function init_add_customer_handler() {

    $("#addCommodity").click(function () {
        $("#addCommodityModal").modal("show");
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

$(document).ready(function () {
    init_menu_li();
    init_add_customer_handler();
    init_modal_handler();
});