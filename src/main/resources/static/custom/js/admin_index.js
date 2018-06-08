$(function () {
    init_button_handler();
});

function init_button_handler() {
    $("#profile").click(function () {
        $("#UserProfile").modal("show");
    });
    
    $("#editProfile").click(function () {
        $("#editProfile").click(function () {
            $("#editProfileModal").modal("show");
        });
    });

    $("#cancelUser").click(function () {
        $("#editProfileModal").modal("hide");
    });

    $("#saveUser").click(function () {
        $("#userForm").ajaxSubmit({
            url: "/admin/account/update",
            type: "post",
            dataType: "json",
            success: function (data) {
                if (data.status) {
                    $("#editProfileModal").modal("hide");
                    window.location = "/admin/logout";
                }
            }
        });
    });
}