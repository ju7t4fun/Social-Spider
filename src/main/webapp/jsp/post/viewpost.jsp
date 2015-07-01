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
                                                <td style="text-align:left;padding-left:-300px;"><img
                                                        src="${pageContext.request.contextPath}/img/post.png"
                                                        style="margin:0px;"><strong
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
                "http://localhost:8080/controller?action=getpostbyid",
                {
                    post_id: id
                },
                onAjaxSuccess
        );
        function onAjaxSuccess(data) {
            var response = data;
            $("#post_text").html(response.postText);
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
            var table = $("#attachments_table");
            table.empty();
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
                        row.append('<td><' + atr + ' width="320" height="240" src="' +
                                response.attachments[i].payload +
                                '"></' + atr + '><td>');
                        if (i % 2 != 0) {
                            table.append(row);
                            row = $('<tr></tr>');
                        }
                    }
                }
                table.append(row);
            }
        }
    }
</script>