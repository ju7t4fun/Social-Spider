<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
<%--Вікно публікації--%>
<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="publish_modal"
     class="modal fade">
    <div class="modal-dialog" style="margin-left: 35%; margin-top: 110px">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">x</button>
                <h4 class="modal-title"><l:resource key="newpost.dateandtime"/></h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <form id="modal_form" class="form-horizontal">
                            <%--Дата і час--%>
                            <div style="position: relative; left: -130px; top:4px;">
                                <div class="form-group">
                                    <label class="col-md-4 control-label" for="date"><l:resource
                                            key="newpost.date"/></label>

                                    <div class="col-md-4">
                                        <l:resource key="newpost.postdate">
                                            <input id="date" name="date" type="date"
                                                   min="${date}" value="${date}"
                                                   class="form-control input-md" onchange="changeData()">
                                        </l:resource>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 control-label" for="time" style="margin-top: -24px">
                                        <l:resource key="newpost.time"/></label>

                                    <div class="col-md-4" style="margin-top: -24px">
                                        <l:resource key="newpost.posttime">
                                            <input id="time" name="time" type="time" value="${time}"
                                                   class="form-control input-md" onchange="changeData()">
                                        </l:resource>
                                    </div>
                                </div>
                            </div>
                            <%--Вибір груп--%>
                            <div style="position: relative; left: 250px; top:-109px;">
                                <div class="form-group" style="">
                                    <div class="col-lg-6">
                                        <h4><l:resource key="newpost.selectgroup"/>:</h4>
                                        <select name="groups" id="tokenize_focus" multiple="multiple"
                                                class="tokenize-sample">
                                            <c:forEach items="${owners}" var="owner">
                                                <option value="${owner.wallId}">${owner.name}</option>
                                            </c:forEach>
                                        </select>
                                        <script type="text/javascript">
                                            $('select#tokenize_focus').tokenize({displayDropdownOnFocus: true});
                                        </script>
                                    </div>
                                </div>
                            </div>
                            <%--Включення автовидалення поста--%>
                            <div style="position: relative; left:-67px; top:-100px;">
                                <div class="form-group">
                                    <label class="col-md-4 control-label" for="check"><l:resource
                                            key="newpost.removingdate"/></label>
                                    <input id="check" type="checkbox" style="margin-top: 11px">
                                </div>
                            </div>
                            <%--Дата видалення--%>
                            <div style="position: relative; left:48px; top:-90px; width: 600px;">
                                <div id="time3" class="col-md-4">
                                    <l:resource key="newpost.date">
                                        <input id="time1" name="date_delete" style="width:162px;" type="date"
                                               class="form-control input-md" value="${del_date}">
                                    </l:resource>
                                </div>
                            </div>
                            <%--Час видалення--%>
                            <div style="position: relative; left:-152px; top:-50px; width: 600px;">
                                <div id="time4" class="col-md-4">
                                    <l:resource key="newpost.time">
                                        <input id="time5" name="time_delete" type="time" style="width:162px;"
                                               class="form-control input-md" value="${del_time}">
                                    </l:resource>
                                </div>
                            </div>
                            <%--Кнопка публікування--%>
                            <button id="submit_modal" type="button" style="float:right; margin-top: -51px;"
                                    class="btn btn-primary" <%--data-dismiss="modal" --%>>
                                <l:resource key="newpost.save"/>
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>

        var publishPostId = 0;

        // Завантажуємо дані для вікна
        function openPublishWindows(id) {
            publishPostId = id;
            var xmlhttp = new XMLHttpRequest();
            xmlhttp.open('GET', '${pageContext.request.contextPath}/owner?action=getOwnerWall&id=' + id, true);
            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState == 4) {
                    var response = JSON.parse(xmlhttp.responseText);
                    var list = $("#tokenize_focus");
                    list.empty();
                    list.data('tokenize').clear();
                    for (var i = 0; i < response.owner.length; i++) {
                        list.append('<option value="' + response.owner[i].id + '">' + response.owner[i].name + '</option>');
                    }
                    $("#date").attr("min", response.date);
                    $("#date").val(response.date);
                    $("#time").val(response.time);
                    $("#time1").attr("min", response.del_date);
                    $("#time1").val(response.del_date);
                    $("#time5").val(response.del_time);
                }
            };
            xmlhttp.send();
        }

        function changeData() {
            $("#time1").val($("#date").val());
            $("#time1").attr("min", $("#date").val());
        }

        // Скриваємо видалення
        $("#time3, #time4").hide();
        $('#check').click(function () {
            $("#time3, #time4").toggle(this.checked);
        });

        // Опрацювання публікування поста
        $(document).ready(function () {
            $("#submit_modal").click(function () {
                if ($("#check").prop('checked'))
                    if ($("#date").val() === $("#time1").val())
                        if ($("#time5").val() < $("#time").val()) {
                            toastrNotification('warning', "Time error!");
                            return;
                        }
                if ($("#tokenize_focus").val() == null) {
                    toastrNotification('warning', "There are not groups selected!");
                    return;
                }
                $.post(
                        "${pageContext.request.contextPath}/post?action=publishPostId",
                        {
                            postId: publishPostId,
                            date: $("#date").val(),
                            time: $("#time").val(),
                            date_delete: $("#time1").val(),
                            time_delete: $("#time5").val(),
                            checked: $("#check").prop('checked'),
                            groups: $("#tokenize_focus").val().toString()
                        },
                        onAjaxSuccess
                );
                function onAjaxSuccess(response) {
                    for (var i = 0; i < response.length; i++)
                        toastrNotification(response[i].status, response[i].msg);
                    $("#publish_modal").modal('toggle');
                }
            });
        });

    </script>

</div>