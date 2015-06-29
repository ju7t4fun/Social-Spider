<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="myModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 840px; position: relative; left: -100px;">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">x</button>
                <h3 class="modal-title">Post</h3>
            </div>
            <div class="modal-body" style="position: relative; left: 70px; top: -21px;">
                <div class="row">
                    <div class="col-md-8 portlets">
                        <div class="">
                            <div class="panel-body" style="width: 700px; margin-left: -15px;">
                                <ul style="margin-left:-30px;">
                                    <li>
                                        <br>
                                        <table width="100%" style="padding:0 50px;">
                                            <tr>
                                                <td style="text-align:center;padding-left:20px;"><img
                                                        src="${pageContext.request.contextPath}/img/post.png"
                                                        style="margin:15px;"><strong
                                                        id="group_name"></strong>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="text-align:justify;">
                                                    <span id="post_text"></span>
                                                </td>
                                            </tr>
                                        </table>
                                        <br>
                                        <table width="100%" style="padding:0 50px;" id="attachments_table"></table>

                                    </li>
                                    <hr>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    function viewPost(id) {
        $.post(
                "/controller?action=getpostbyid",
                {
                    post_id: id
                },
                onAjaxSuccess
        );
        function onAjaxSuccess(data) {
            var response = data;
            $("#post_text").text(response.postText);
            var table = $("#attachments_table");
            table.empty();
            if (response.attachments.length != 0) {
                var row = $("<tr></tr>");
                for (var i = 0; i < response.attachments.length; i++) {
                    var atr;
                    if (response.attachments[i].payload.includes("video")) {
                        atr = "video controls";
                    } else if (response.attachments[i].payload.includes("music")) {
                        atr = "video controls";
                    } else if (response.attachments[i].payload.includes("image")) {
                        atr = "img";
                    }
                    row.append('<td><' + atr + ' width="320" height="240" src="' +
                            response.attachments[i].payload +
                            '"></' + atr + '><td>');
                    if (i % 2 != 0) {
                        table.append(row);
                        row = $('<tr></tr>');
                    }
                }
                table.append(row);
            }
        }
    }
</script>