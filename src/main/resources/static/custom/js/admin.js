
function init_menu_li() {
    $("li[url]").click(function () {
        var url = $(this).attr("url");
       $("div.right_col").load(url);
    });
}

$(document).ready(function () {
    init_menu_li();
});