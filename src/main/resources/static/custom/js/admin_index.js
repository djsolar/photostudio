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
        $.ajax({
            url: "/admin/account/update",
            type: "post",
            dataType: "json",
            data: $("#userForm").serialize(),
            success: function (data) {
                
            }
        });
    });
}