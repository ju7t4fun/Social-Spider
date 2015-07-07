<link href="${pageContext.request.contextPath}/css/toastr.css" rel="stylesheet" type="text/css"/>
<script src="${pageContext.request.contextPath}/js/toastr.js"></script>
<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="edit_post" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 840px; position: relative; left: -100px;">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">x</button>
                <h3 class="modal-title">Post</h3>
            </div>
            <div class="modal-body" style="position: relative; left: 70px; top: -21px;">
                <div class="row">
                    <div class="col-md-10">
                        <div class="form-group">
                            <label>ID</label>

                            <div>
                                <input type="text" class="form-control" style="width: 10%;" id="id"
                                       disabled="disabled"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label>Text</label>

                            <div>
                                    <textarea rows="15" cols="55" id="post_tarea" class="form-control"
                                              name="name"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>Attachments</label>
                        </div>
                        <div>
                            <table id="attachments" style="margin-left: 80px;"></table>
                        </div>
                        <div class="form-group">
                            <div class="col-xs-5 col-xs-offset-3">
                                <button onclick="saveEditedPost();" class="btn btn-default">Save</button>
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
                    case "audio":
                        atr = "video controls";
                        break;
                    case "video":
                        atr = "video controls";
                        break;
                }
                return atr;
            }

            if (response.attachments.length != 0) {
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
                }
            });
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
<style>
    .delete-img {
        background-image: url('../../img/closeIcon.png');
        background-repeat: no-repeat;
    }
</style>