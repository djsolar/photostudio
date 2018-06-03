$(function () {
    init_button_handler();
});

function init_button_handler() {
    $("#addPhoto").click(function () {
        $("#uploadPhoto").modal("show");
    });

    $("#addPhoto").click(function () {
        $.ajax({
            url: "/admin/photoAlbum/add",
            type: "post",
            dataType: "json",
            data: $("#photoAlbumForm").serialize(),
            success: function (data) {
                if (data.status) {
                    var newPhoto = "<div class=\"thumbnail\">\n" +
                        "                                <div class=\"image view view-first\">\n" +
                        "                                    <img style=\"width: 100%; display: block;\" src=\"images/media.jpg\" alt=\"image\"/>\n" +
                        "                                    <div class=\"mask no-caption\">\n" +
                        "                                        <div class=\"tools tools-bottom\">\n" +
                        "                                            <a href=\"#\"><i class=\"fa fa-link\"></i></a>\n" +
                        "                                            <a href=\"#\"><i class=\"fa fa-pencil\"></i></a>\n" +
                        "                                            <a href=\"#\"><i class=\"fa fa-times\"></i></a>\n" +
                        "                                        </div>\n" +
                        "                                    </div>\n" +
                        "                                </div>\n" +
                        "                                <div class=\"caption\">\n" +
                        "                                    <p><strong>Image Name</strong>\n" +
                        "                                    </p>\n" +
                        "                                    <p>Snow and Ice Incoming</p>\n" +
                        "                                </div>\n" +
                        "                            </div>"
                }
            }
        });
    });
}

function file_upload() {
    $('#fileupload').fileupload({
        dataType: 'json',
        done: function (e, data) {
            $("tr:has(td)").remove();
            $.each(data.result, function (index, file) {

                $("#uploaded-files").append(
                    $('<tr/>')
                        .append($('<td/>').text(file.fileName))
                        .append($('<td/>').text(file.fileSize))
                        .append($('<td/>').text(file.fileType))
                )
            });
        },

        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress .progress-bar').css(
                'width',
                progress + '%'
            ).text(progress + "%");
            console.log("progress: " + progress)
        }
    });
}