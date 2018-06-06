$(function () {
    init_button_handler();
    get_all_photoalbum();
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
                    clear_photo_form();
                    $("#uploadPhoto").modal("hide");
                    get_all_photoalbum();
                }
            }
        });
    });
}

function clear_photo_form() {
    $("#photoAlbumForm")[0].reset();
}

function get_all_photoalbum() {
    $("div.x_content").empty();
    $.ajax({
        url: "/admin/photoAlbum/list",
        type: "post",
        success: function (data) {
            if (data.status) {
                $("div.x_content").append(template("photoList", {"entity": data.entity}));
                photo_operation_bind();
            }
        }
    });
}

function photo_operation_bind() {
    $("a.delete").click(function () {
        var id = $(this).parent().attr("id");
        $.ajax({
            url: "/admin/photoAlbum/delete",
            type: "post",
            dataType: "json",
            data: {"id": id},
            success: function (data) {
                if (data.status) {
                    get_all_photoalbum();
                }
            }
        });
    });
    
    $("a.top").click(function () {
        var top = $(this).parent().attr("top");
        var id = $(this).parent().attr("id");
        if (top === "false") {
            top = true;
        } else {
            top = false;
        }
        $.ajax({
            url: '/admin/photoAlbum/top',
            type: "post",
            dataType: "json",
            data: {"id": id, "top": top},
            success: function (data) {
                if (data.status) {
                    get_all_photoalbum();
                }
            }
        });
    });
    $("a.eye").click(function () {
        var id = $(this).parent().attr("id");
        $.ajax({
            url: "/admin/photoAlbum/eye",
            type: "post",
            dataType: "json",
            data: {"id": id},
            success: function (data) {
                if (data.status) {
                    $("#eyePhotoContent").modal("show");
                    $("#photoDescription").text("相册描述：" + data.entity.description);
                    $("#photoContent").attr("src", "/" + data.entity.imageName);
                }
            }
        });
    });
}