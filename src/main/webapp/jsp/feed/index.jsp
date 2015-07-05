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

    <section id="main-content">
        <section class="wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header" style="position: fixed"><i class="fa fa-home"></i> Home</h3>
                </div>
            </div>
            <div style="position: fixed; top: 127px; left: 195px;">
                <input type="button" value="Click" onclick="createFeedPost(196, false);">
            </div>
            <section class="wrapper">
                <div class="row">
                    <div class="col-md-8 portlets" style="margin-left: 190px; margin-top: -50px">
                        <div class="panel panel-default">
                            <div class="panel-body" id="feed">
                                <div align="center">
                                    <p>Триває завантаження даних ...</p>
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
            var text = showMoreText(response.postText);
            post += '<ul style="margin-left:-30px;"><table width="100%" style="padding:0 50px;">';
            post += '<tr><td style="text-align:left;"><strong> POST #' + postId + ' ' + categoties + '</strong></td></tr>';
            post += '<tr><td style="text-align:justify;" class="smore"><br>' + text + ' </td></tr>';
            for (var i = 0; i < response.attachments.length; i++) {
                if (response.attachments[i].type == 'photo') {
                    post += '<tr><td><div width="600" height="450"><img src="' + response.attachments[i].url +
                            '"  style="margin:25px;"></div></td> </tr>';
                    break;
                }
            }
            post += '</table><div class="btn-group" style="margin-left: 450px;"> <a class="btn btn-default" onclick="viewPost(' + postId + ');" data-toggle="modal" data-target="#myModal">View</a> <a class="btn btn-default" data-toggle="modal" data-target="#publish_modal">Publish</a> <a class="btn btn-default" onclick="savePost(' + postId + ');">Save</a></div></ul>';
            post += '<div style="width: 90%; height: 3px;margin:25px auto 25px;border-radius: 4px;background:  lightslategray;"></div>';
            if (isBegin) {
                var pos = $(document).height() - $(window).scrollTop();
                feed.prepend(post);
                $(window).scrollTop($(document).height() - pos);
            } else
                feed.append(post);
        });
    }

</script>

<script>
    function savePost(postID) {
        $.post(
                "/post?action=savePostFromFeed",
                {
                    id: postID
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
    /* Image style */
    #scrollUp {
        background-image: url("${pageContext.request.contextPath}/img/icons/top.png");
        bottom: 20px;
        right: 20px;
        width: 38px;    /* Width of image */
        height: 38px;   /* Height of image */
    }

</style>
<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="publish_modal"
     class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">x</button>
                <h4 class="modal-title"><l:resource key="newpost.dateandtime"/></h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <form id="modal_form" method="POST" action="/post?action=addPost" class="form-horizontal">
                            <input type="hidden" name="typePost" value="new">

                            <div style="position: relative; left: -130px; top:30px;">
                                <div class="form-group">
                                    <label class="col-md-4 control-label" for="date"><l:resource
                                            key="newpost.date"/></label>

                                    <div class="col-md-4">
                                        <l:resource key="newpost.postdate"><input id="date" name="date" type="date"
                                                                                  min="${date}" value="${date}"
                                                                                  placeholder=""
                                                                                  class="form-control input-md"></l:resource>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 control-label" for="time"><l:resource
                                            key="newpost.time"/></label>

                                    <div class="col-md-4">
                                        <l:resource key="newpost.posttime"><input id="time" name="time" type="time"
                                                                                  value="${time}" placeholder=""
                                                                                  class="form-control input-md"></l:resource>
                                    </div>
                                </div>
                            </div>
                            <div style="position: relative; left: 250px; top:-109px;">
                                <div class="form-group" style="">
                                    <div class="col-lg-6">
                                        <h4><l:resource key="newpost.selectgroup"/>:</h4>
                                        <select name="groups" id="tokenize_focus" multiple="multiple"
                                                class="tokenize-sample">

                                        </select>

                                        <script type="text/javascript">
                                            $('select#tokenize_focus').tokenize({displayDropdownOnFocus: true});
                                        </script>
                                    </div>
                                </div>
                            </div>
                            <div style="position: relative; left:-58px; top:-100px;">
                                <div class="form-group">
                                    <label class="col-md-4 control-label" for="check"><l:resource
                                            key="newpost.removingdate"/></label>
                                    <input id="check" type="checkbox">
                                </div>
                            </div>
                            <div style="position: relative; left:54px; top:-90px; width: 600px;">
                                <div id="time3" class="col-md-4">
                                    <l:resource key="newpost.date"><input id="time1" name="date_delete" min="${date}"
                                                                          value="${del_date}" type="date"
                                                                          placeholder=""
                                                                          class="form-control input-md"></l:resource>
                                </div>

                            </div>
                            <div style="position: relative; left:-146px; top:-50px; width: 600px;">
                                <div id="time4" class="col-md-4">
                                    <l:resource key="newpost.time"><input id="time5" name="time_delete" type="time"
                                                                          placeholder="Time"
                                                                          class="form-control input-md"
                                                                          value="${del_time}"></l:resource>
                                </div>
                            </div>
                            <button id="submit_modal3" type="button" style="margin-left: 455px;margin-top: -80px;"
                                    class="btn btn-primary"
                                    data-dismiss="modal">
                                <l:resource key="newpost.save"/>
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        //        load feed while scroll bottom
        $(window).scroll(function () {
            if ($(window).scrollTop() + $(window).height() == $(document).height()) {
//                createFeed(196);
                feedWebSocket.send("on_scroll|" + ${user.id} +"|" + countInFeed + "|" + 5);
            }
        });
        //        end
        $("#time3, #time4").hide();
        $('#check').click(function () {
            $("#time3, #time4").toggle(this.checked);
        });
        $(document).ready(function () {
            $("#submit_modal3").click(function () {
                $.post(
                        "/post?action=addPost",
                        {
                            typePost: "new",
                            title: $("#title").val(),
                            message: $("#content").val(),
                            tags: $("#tagsinput").val(),
                            date: $("#date").val(),
                            time: $("#time").val(),
                            date_delete: $("#time1").val(),
                            time_delete: $("#time5").val(),
                            checked: $("#check").prop('checked'),
                            groups: $("#tokenize_focus").val().toString()
                        },
                        onAjaxSuccess
                );
                function onAjaxSuccess(data) {
                    var responce = JSON.parse(data);
                    if (responce.status === 'success') {
                        location.href = "/post?action=queued";
                    }
                }
            });
        });
    </script>
    <span id="temp" style="display: none;"></span>
    <script>
        function showMore() {
            $('.smore').click(function () {
                $(this).html($("#temp").text());
            });
        }
        function showMoreText(text) {
            var showChar = 300;
            if (text.length <= showChar) {
                return text;
            } else {
                var c = text.substr(0, showChar);
                var html = c + '<a style="color: blue" onclick="showMore();">...show all text</a>';
                $("#temp").text(text);
                return html;
            }
        }

        $(function () {
            $.scrollUp({
                animation: 'fade',
                activeOverlay: '#00FFFF'
            });
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
    <script src="${pageContext.request.contextPath}/js/form-component.js"></script>
    <!--custom checkbox & radio-->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ga.js"></script>
    <!--custom switch-->
    <script src="${pageContext.request.contextPath}/js/bootstrap-switch.js"></script>
    <!--custom tagsinput-->
    <script src="${pageContext.request.contextPath}/js/jquery.tagsinput.js"></script>
    <script src="${pageContext.request.contextPath}/js/form-component.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.tokenize.js"></script>

    <script>
        var feedWebSocket = new WebSocket("ws://localhost:8080/websocket/feed");

        feedWebSocket.onopen = function (event) {
//            alert("onOpen");
        };

        // Опрацювання команд
        feedWebSocket.onmessage = function (event) {
//            alert("onMessage " + event.data);
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
//            alert('onClose');
        };

    </script>
</body>
</html>

