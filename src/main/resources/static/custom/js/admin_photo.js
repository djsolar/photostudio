$(function () {
    init_button_handler();
});

function init_button_handler() {
    $("#addPhoto").click(function () {
        $("#uploadPhoto").modal("show");
    });

    $("#savePhotoAlbum").click(function () {
        $("#photoAlbumForm").ajaxSubmit({
            url: "/admin/photoAlbum/upload",
            type: "post",
            success: function (data) {
                if (data.status) {

                }
            }
        });
    });
}