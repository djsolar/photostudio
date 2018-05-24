
function init_menu_li() {
    $("li[url]").click(function () {
        var url = $(this).attr("url");
       $("div.right_col").load(url);
    });
}

function init_add_customer_handler() {
    $("#cancelAddCustomer").click(function () {
        $("#addCustomerModal").modal("hi")
    });
    
    $("#saveCustomer").click(function () {

    });
}

$(document).ready(function () {
    init_menu_li();
    init_add_customer_handler();
});