<%--
  Created by IntelliJ IDEA.
  User: Sasha
  Date: 17.06.2015
  Time: 15:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
<%@ taglib prefix="lg" uri="http://lab.epam.com/spider/logger" %>
<%@ taglib prefix="v" uri="http://lab.epam.com/spider/views" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Creative - Bootstrap 3 Responsive Admin Template">
    <meta name="author" content="GeeksLabs">
    <meta name="keyword" content="Creative, Dashboard, Admin, Template, Theme, Bootstrap, Responsive, Retina, Minimal">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/icons/favicon.png">

    <title>Social Spider</title>

    <!-- Bootstrap CSS -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <!-- bootstrap theme -->
    <link href="${pageContext.request.contextPath}/css/bootstrap-theme.css" rel="stylesheet">
    <!--external css-->
    <!-- font icon -->
    <link href="${pageContext.request.contextPath}/css/elegant-icons-style.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet"/>
    <!-- Custom styles -->
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style-responsive.css" rel="stylesheet"/>
    <script src="${pageContext.request.contextPath}/js/jquery.js"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.tokenize.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery.tokenize.css"/>

    <link href="${pageContext.request.contextPath}/css/toastr.css" rel="stylesheet" type="text/css"/>
    <script src="${pageContext.request.contextPath}/js/toastr.js"></script>

</head>
<body>

<section id="container">

    <jsp:include page="../pagecontent/header.jsp"/>
    <jsp:include page="../pagecontent/sidebar.jsp"/>
    <jsp:include page="../post/viewpost.jsp"/>

    <v:views userId="${user.id}"/>

    <section id="main-content">
        <section class="wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header" style="position: fixed"><i class="fa fa-home"></i><l:resource key="home"/>
                    </h3>
                </div>
            </div>
            <section class="wrapper">
                <div class="row">
                    <div class="col-md-8 portlets" style="margin-left: 190px; margin-top: -50px">
                        <div class="panel panel-default">
                            <div style="margin-left: 15px; margin-right: 15px">
                                <div class="panel-body" id="feed">
                                    <div align="center">
                                        <p><l:resource key="dataloading"/></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </section>
    </section>
</section>

<script>

    var countInFeed = 0;

    // Вставка в початок стрічки
    function appendInBeginningFeed(args) {
        createFeedPost(args[1], args[2], true);
    }

    // Вставка в кінець
    function appendInEndFeed(args) {
        createFeedPost(args[1], args[2], false);
    }

    // Завантаження вмісту поста
    function createFeedPost(postId, categoties, isBegin) {
        countInFeed++;
        var feed = $("#feed");
        var post = "";
        $.getJSON("/post?action=getPostById&post_id=" + postId, function (response) {
            var text = showMoreText(response.postText, postId);
            post += '<ul style="margin-left:-40px;"><table width="100%" style="padding:0 50px;">';
            post += '<tr><td style="text-align:left;"><strong> POST #' + postId + ' ' + categoties + '</strong></td></tr>';
            post += '<tr><td style="text-align:justify;" id="showalltext' + postId + '"><br>' + text + ' </td></tr>';
            for (var i = 0; i < response.attachments.length; i++) {
                if (response.attachments[i].type == 'photo') {
                    post += '<tr><td><div width="600" height="450"><img src="' + response.attachments[i].url + '"  style="margin:25px;"></div></td> </tr>';
                    break;
                }
            }
            post += '<tr><td style="text-align:right;"><div class="btn-group"> <a class="btn btn-default" onclick="viewPost(' + postId + ');" data-toggle="modal" data-target="#myModal"><l:resource key="view" /></a> <a class="btn btn-default" onclick="openPublishWindows(' + postId + ');"  data-toggle="modal" data-target="#publish_modal"><l:resource key="newpost.publish" /></a> <a class="btn btn-default" onclick="savePost(' + postId + ');"><l:resource key="newpost.save" /></a></div></td></tr></table></ul>';
            post += '<div style="width: 90%; height: 3px;margin:25px auto 25px;border-radius: 4px;background:  lightslategray;"></div>';
            if (isBegin) {
                var pos = $(document).height() - $(window).scrollTop();
                feed.prepend(post);
                $(window).scrollTop($(document).height() - pos);
            } else
                feed.append(post);
        });
    }

    // Зберігаємо пост
    function savePost(postId) {
        $.post(
                "/post?action=savePostFromFeed",
                {
                    id: postId
                },
                onAjaxSuccess
        );
        function onAjaxSuccess(data) {
            var response = JSON.parse(data);
            toastrNotification(response.status, response.message);
        }
    }

</script>
<style>
    #scrollUp {
        background-image: url("${pageContext.request.contextPath}/img/icons/top.png");
        bottom: 20px;
        right: 20px;
        width: 38px;
        height: 38px;
    }

</style>

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
                                                   class="form-control input-md">
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
            xmlhttp.open('GET', '/owner?action=getOwnerWall&id=' + id, true);
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
            $("#time1").val($("#date").val())
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
                    if ($("#time5").val() < $("#time").val()) {
                        toastrNotification('warning', "Time error!");
                        return;
                    }
                if ($("#tokenize_focus").val() == null) {
                    toastrNotification('warning', "There are not groups selected!");
                    return;
                }
                $.post(
                        "/post?action=publishPostId",
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

<script>

    // Завантаження при прокрутці
    $(window).scroll(function () {
        if ($(window).scrollTop() + $(window).height() == $(document).height()) {
            feedWebSocket.send("on_scroll|" + ${user.id} +"|" + countInFeed + "|" + 5);
        }
    });

    function showMore(postId) {
        $.getJSON("/post?action=getPostById&post_id=" + postId, function (response) {
            $("#showalltext" + postId).html("<br>" + response.postText);
        });
    }

    function showMoreText(text, postId) {
        var showChar = 300;
        if (text.length <= showChar) {
            return text;
        } else {
            var c = text.substr(0, showChar);
            var html = c + ' <a style="color: blue" onclick="showMore(' + postId + ');"><l:resource key="feed.showalltext"/></a>';
            return html;
        }
    }

    $(function () {
        $.scrollUp({
            animation: 'fade',
            activeOverlay: '#00FFFF'
        });
    });

    var feedWebSocket = new WebSocket("ws://localhost:8080/websocket/feed");

    feedWebSocket.onopen = function (event) {
        console.log("FeedWebSocket OnOpen " + event.data);
    };

    // Опрацювання команд
    feedWebSocket.onmessage = function (event) {
        console.log("FeedWebSocket OnMessage " + event.data);
        var args = event.data.split("|");
        switch (args[0]) {
            case "new":
                appendInBeginningFeed(args);
                break;
            case "on_scroll":
                appendInEndFeed(args);
                break;
            case "history":
                if (countInFeed == 0)
                    $("#feed").empty();
                appendInEndFeed(args);
                break;
        }
    };

    feedWebSocket.onclose = function (event) {
        console.log("FeedWebSocket OnClose " + event.data);
    };

    $(document).ready(function () {

    });

</script>
<!-- javascripts -->
<%--<script src="${pageContext.request.contextPath}/js/jquery.js"></script>--%>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<!-- nice scroll -->
<script src="${pageContext.request.contextPath}/js/jquery.scrollTo.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.nicescroll.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/jquery.scrollUp.js"></script>
<!-- gritter -->

<!-- custom gritter script for this page only-->
<script src="${pageContext.request.contextPath}/js/gritter.js" type="text/javascript"></script>
<!--custome script for all page-->
<script src="${pageContext.request.contextPath}/js/scripts.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.tokenize.js"></script>
<!--custom tagsinput-->
<script src="${pageContext.request.contextPath}/js/jquery.tagsinput.js"></script>
<%--<script src="${pageContext.request.contextPath}/js/form-component.js"></script>--%>
<!--custom checkbox & radio-->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ga.js"></script>
<!--custom switch-->
<script src="${pageContext.request.contextPath}/js/bootstrap-switch.js"></script>
<!--custom tagsinput-->
<script src="${pageContext.request.contextPath}/js/jquery.tagsinput.js"></script>
<%--<script src="${pageContext.request.contextPath}/js/form-component.js"></script>--%>
<script src="${pageContext.request.contextPath}/js/jquery.tokenize.js"></script>

</body>
</html>

