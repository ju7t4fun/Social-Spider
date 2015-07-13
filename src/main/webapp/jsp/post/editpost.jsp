<%@ taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
<link href="${pageContext.request.contextPath}/css/toastr.css" rel="stylesheet" type="text/css"/>
<script src="${pageContext.request.contextPath}/js/toastr.js"></script>
<script src="${pageContext.request.contextPath}/js/language.js"></script>
<link href="${pageContext.request.contextPath}/css/fileinput.css" media="all" rel="stylesheet"
      type="text/css"/>

<script src="${pageContext.request.contextPath}/js/fileinput.min.js" type="text/javascript"></script>

<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="edit_post" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 840px; position: relative; left: -100px;">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">x</button>
                <h3 class="modal-title"><l:resource key="post"/></h3>
            </div>
            <div class="modal-body" style="position: relative; left: 70px; top: -21px;">
                <div class="row">
                    <div class="col-md-10">
                        <div class="form-group">
                            <div>
                                <input type="text" class="form-control" style="width: 10%; display: none;" id="id"
                                       disabled="disabled"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label><l:resource key="text"/></label>

                            <div>
                                    <textarea rows="15" cols="55" id="post_tarea" class="form-control"
                                              name="name"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label><l:resource key="attachment"/></label>
                        </div>
                        <div>
                            <table id="attachments" style="margin-left: 80px;"></table>
                        </div>
                        <!-- Add file -->
                        <br>

                        <div class="form-group">
                            <div id="upload-from">
                                <div class="btn-group" style="margin-left: -30px;">
                                    <button type="button" class="btn btn-primary"><l:resource
                                            key="newpost.upload"/></button>
                                    <button id="toggle_button" type="button" class="btn btn-primary dropdown-toggle"
                                            data-toggle="dropdown" aria-haspopup="true"
                                            aria-expanded="false">
                                        <span class="caret"></span>
                                        <span class="sr-only">Dropdown</span>
                                    </button>
                                    <ul class="dropdown-menu">
                                        <li><a href="#"
                                               onclick="uploadFromComputer();"><l:resource
                                                key="newpost.computer"/>
                                        </a></li>
                                        <li><a href="#" onclick="uploadFromURL();"><l:resource
                                                key="newpost.url"/></a></li>
                                    </ul>
                                </div>
                            </div>
                            <div class="col-lg-offset-2 col-lg-9">
                                <script>
                                    function uploadFromComputer() {
                                        $('#uriForm').hide();
                                        $('#compForm').show();
                                        $('html, body').animate({
                                            scrollTop: $("#scrl").offset().top
                                        }, 1000);
                                    }
                                    function uploadFromURL() {
                                        $('#compForm').hide();
                                        $('#uriForm').show();
                                        $('#fl').hide();
                                        $('#sc').hide();
                                    }
                                </script>
                                <div id="compForm" class="container kv-main" style="width:700px;
                                                margin-top:20px; margin-left: -141px;">
                                    <input id="input-dim-2" type="file"
                                           multiple="true" method="post"
                                           enctype="multipart/form-data"
                                           accept="audio/*,video/*,image/*" value="">
                                </div>
                                <div class="container kv-main" id="uriForm" style="width:600px;
                                                margin-top:20px; margin-left: -141px;">
                                    <form class="bs-example bs-example-form" role="form">
                                        <div class="row">
                                            <div class="col-lg-6">
                                                <div class="input-group">
                                                             <span class="input-group-btn">
                                                            <button id="button1" class="btn btn-default" type="button">
                                                                Upload
                                                            </button>
                                                             </span>
                                                    <input style="width:600px" id="text1" type="text"
                                                           placeholder="Enter url to you file"
                                                           class="form-control">
                                                </div>
                                            </div>
                                            <br>
                                        </div>
                                    </form>
                                    <br>

                                    <div id="sc" class="alert alert-success"
                                         role="alert"><span
                                            id="success"></span></div>
                                    <div id="fl" class="alert alert-danger"
                                         role="alert"><span id="fail"></span></div>
                                </div>
                            </div>
                        </div>

                        <script>
                            $("#button1").click(function () {
                                var text = $('#text1').val();
                                var dataString = "url=" + text;
                                $.ajax({
                                    type: "POST",
                                    url: 'http://localhost:8080/controller?action=uploadurl',
                                    data: dataString,
                                    dataType: "json",
                                    success: function (data) {
                                        if (data.success) {
                                            $('#fl').hide();
                                            $('#sc').show();
                                            $("#success").text(data.success);
                                            setTimeout(function () {
                                                $('#sc').hide();
                                            }, 4000);
                                        } else {
                                            $('#sc').hide();
                                            $('#fl').show();
                                            $("#fail").text(data.fail);
                                            setTimeout(function () {
                                                $('#fl').hide();
                                            }, 4000);
                                        }
                                    }
                                });
                            });
                        </script>
                        <script>
                            $('#uriForm').hide();
                            $('#compForm').hide();
                            $('#sc').hide();
                            $('#fl').hide();
                        </script>
                        <div class="form-group">
                            <div class="col-xs-5 col-xs-offset-3" style="margin-top: 3px; margin-left: -15px;">
                                <button onclick="saveEditedPost();" class="btn btn-default"><l:resource
                                        key="newpost.save"/></button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>

    function editPost(id) {
        var fileInputCount = 0;
        $("#remove_all").click();
        $('#compForm').hide();
        $('#uriForm').hide();
        $.post(
                "http://localhost:8080/post?action=getPostByIdWithoutHtml",
                {
                    post_id: id
                },
                onAjaxSuccess
        );
        function onAjaxSuccess(data) {
            var response = data;
            var attach = $("#attachments");
            attach.empty();
            $("#post_tarea").val(response.postText);
            $("#id").val(id);

            function checkType(i) {
                var atr;
                switch (response.attachments[i].type) {
                    case "photo":
                        atr = "img";
                        break;
                    case "youtube":
                        atr = "iframe";
                        break;
                    case "vk_video":
                        atr = "iframe";
                        break;
                    case "audio":
                        atr = "video controls";
                        break;
                    case "video":
                        atr = "video controls";
                        break;
                }
                return atr;
            }
            var attachmentLength = response.attachments.length;
            if (attachmentLength != 0) {
                if (attachmentLength >= 10) {
                    $('#toggle_button').attr('disabled', 'disabled');
                } else {
                    fileInputCount = 10 - response.attachments.length;
                }
                var row = $("<tr></tr>");

                for (var i = 0; i < response.attachments.length; i++) {
                    var atr = checkType(i);
                    row.append('<td id="' + response.attachID[i].id + '"><div style="margin: 0px 20px;"><' + atr +
                            ' width="240" height="200"  src="' + response.attachments[i].url + '"></' + atr +
                            '></div><td>');
                    if (i % 2 != 0) {
                        row = $('<tr></tr>');
                    }

                    attach.append(row);
                }
                $("td:empty").remove();
            } else {
                fileInputCount = 10;
            }

            $('#attachments td')
                    .on("mouseenter", function () {
                        $(this).addClass("delete-img");
                    })
                    .on("mouseleave", function () {
                        $(this).removeClass("delete-img");
                    });

            $('#attachments td').on("click", function () {
                var elemID = $(this).closest('td').attr('id');
                if (fileInputCount < 10 && $("#toggle_button").is(":disabled")) {
                    $('#toggle_button').removeAttr('disabled');
                }
                $.post(
                        "http://localhost:8080/post?action=deleteattach",
                        {
                            attachId: elemID
                        },
                        onAjaxSuccess
                );
                function onAjaxSuccess(data) {
                    $('#' + elemID).remove();
                    var response = data;
                    $('#postsTable').DataTable().draw(false);
                    toastrNotification(response.status, response.message);
                    $("#input-dim-2").empty();
                    fileInputCount = fileInputCount + 1;
                    fileInput(fileInputCount);
                }
            });
            fileInput(fileInputCount);
        }
    }
    function saveEditedPost() {
        var id = $("#id").val();
        var textPost = $("#post_tarea").val();
        $.post(
                "http://localhost:8080/post?action=editpost",
                {
                    post_id: id,
                    text: textPost
                },
                onAjaxSuccess
        );
        function onAjaxSuccess(data) {
            var response = data;
            $('#postsTable').DataTable().draw(false);
            $("#edit_post").modal('toggle');
            toastrNotification(response.status, response.message);
        }
    }
</script>
<script>
    function fileInput(fileCount) {
        $("#input-dim-2").fileinput({
            uploadUrl: "http://localhost:8080/controller?action=upload",
            maxFileCount: fileCount
        });
    }
</script>
<style>
    .delete-img {
        background-image: url('../../img/closeIcon.png');
        background-repeat: no-repeat;
    }
</style>