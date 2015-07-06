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
                                <input type="text" class="form-control" style="width: 5%;" id="id"
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
            var post = "";
            $("#post_tarea").val(response.postText);
            $("#id").val(id);

            function checkType(i) {
                var atr;
                if (response.attachments[i].payload.includes("video")) {
                    atr = "video controls";
                } else if (response.attachments[i].payload.includes("music")) {
                    atr = "video controls";
                } else if (response.attachments[i].payload.includes("image")) {
                    atr = "img";
                }
                return atr;
            }

            if (response.attachments.length != 0) {
                var row = $("<tr></tr>");
                if (response.attachments.length == 1) {
                    var atr = checkType(0);
                    row.append('<td><' + atr + ' width="620" height="400" src="' +
                            response.attachments[0].payload +
                            '"></' + atr + '></td>');
                } else {
                    for (var i = 0; i < response.attachments.length; i++) {
                        var atr = checkType(i);
                        row.append('<td><div style="margin: 0px 20px;"><' + atr +
                                ' width="240" height="200" src="' +
                                response.attachments[i].payload +
                                '"></' + atr + '></div><td>');
                        if (i % 2 != 0) {
                            attach.append(row);
                            row = $('<tr></tr>');
                        }
                    }
                }
                $( "td:empty").remove();
            }
//            $(function() {
//                $('td').click(function() {
//                    alert(this);
////                    $(this).remove();
//                })
//            })
            $(function () {
                $('#attachments td')
                        .on("mouseenter", function () {
                            $(this).addClass("delete-img");
                        })
                        .on("mouseleave", function () {
                            $(this).removeClass("delete-img");
//                            $(this).remove();
                        });

            })
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