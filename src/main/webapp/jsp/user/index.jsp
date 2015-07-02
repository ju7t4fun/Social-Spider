<%--
  Created by IntelliJ IDEA.
  User: Sasha
  Date: 17.06.2015
  Time: 15:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.tokenize.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery.tokenize.css"/>

    <script>
        var feedWebSocket = new WebSocket("ws://localhost:8080/websocket/feed");

        feedWebSocket.onopen = function (event) {
//            alert("onOpen");
        };

        // Опрацювання команд
        feedWebSocket.onmessage = function (event) {
//            alert("onMessage " + event.data);
            var args = event.data.split("|");
            createFeed(args[1]);

        };

        feedWebSocket.onclose = function (event) {
//            alert('onClose');
        };

    </script>


</head>
<body>

<section id="container" class="">

    <jsp:include page="../pagecontent/header.jsp"/>
    <jsp:include page="../pagecontent/sidebar.jsp"/>

    <!--main content start-->
    <section id="main-content">
        <section class="wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header"><i class="fa fa-home"></i> Home</h3>
                </div>
            </div>
            <div style="position: fixed; top: 127px; left: 195px;">
                <p>
                    <img src="${pageContext.request.contextPath}/img/filter.png" data-toggle="modal"
                         data-target="#myModal"
                         width="30px"
                         height="30px">
                </p>
                <input type="button" value="Click" onclick="createFeed(42);">
            </div>
            <section class="wrapper" style="margin-left: 180px; margin-top: -30px;">
                <div class="row">
                    <div class="col-md-8 portlets">
                        <div class="panel panel-default">
                            <div class="panel-body" id="feed">
                                <%--<ul style="margin-left:-30px;">--%>
                                    <%--<table width="100%" style="padding:0 50px;">--%>
                                        <%--<tr>--%>
                                            <%--<td style="text-align:left;"><strong>POST #1</strong></td>--%>
                                        <%--</tr>--%>
                                        <%--<tr>--%>
                                            <%--<td style="text-align:justify;">--%>
                                                <%--<br>--%>
                                                <%--Vivamus tincidunt eleifend congue. Sed lacinia ullamcorper--%>
                                                <%--fringilla.--%>
                                                <%--Suspendisse mi nisi, molestie eget varius quis, cursus nec felis.--%>
                                                <%--Quisque mi tortor, accumsan vitae tempus a, placerat et est. Mauris--%>
                                                <%--eu--%>
                                                <%--mauris urna.--%>
                                            <%--</td>--%>
                                        <%--</tr>--%>
                                        <%--<tr>--%>
                                            <%--<td style="text-align:left;padding-left:20px;">--%>
                                                <%--<br>--%>

                                                <%--<div style="color:blue;">#lorem #ipsum #dolor</div>--%>
                                            <%--</td>--%>
                                        <%--</tr>--%>
                                    <%--</table>--%>
                                    <%--<div class="btn-group" style="margin-left: 450px;">--%>
                                        <%--<a class="btn btn-default" href="">View</a>--%>
                                        <%--<a class="btn btn-default" href="">Publish</a>--%>
                                        <%--<a class="btn btn-default" href="">Save</a>--%>
                                    <%--</div>--%>
                                <%--</ul>--%>

                                <%--<div style="width: 90%; height: 3px;margin:25px auto 25px;border-radius: 4px;--%>
                                       <%--background:  lightslategray;"></div>--%>

                                <%--<ul style="margin-left:-30px;">--%>
                                    <%--<table width="100%" style="padding:0 50px;">--%>
                                        <%--<tr>--%>
                                            <%--<td style="text-align:left;"><strong>POST #2</strong></td>--%>
                                        <%--</tr>--%>

                                        <%--<tr>--%>
                                            <%--<td style="text-align:justify;">--%>
                                                <%--<br>--%>
                                                <%--Vivamus tincidunt eleifend congue. Sed lacinia ullamcorper--%>
                                                <%--fringilla.--%>
                                                <%--Suspendisse mi nisi, molestie eget varius quis, cursus nec felis.--%>
                                                <%--Quisque mi tortor, accumsan vitae tempus a, placerat et est..--%>
                                            <%--</td>--%>
                                        <%--</tr>--%>
                                        <%--<tr>--%>
                                            <%--<td style="text-align:left;padding-left:20px;">--%>
                                                <%--<br>--%>

                                                <%--<div style="color:blue;">#lorem #ipsum #dolor</div>--%>
                                            <%--</td>--%>
                                        <%--</tr>--%>
                                        <%--<tr>--%>
                                            <%--<td>--%>
                                                <%--<img src="${pageContext.request.contextPath}/img/bg-1.jpg"--%>
                                                     <%--width="600"--%>
                                                     <%--style="margin:20px;">--%>
                                            <%--</td>--%>
                                        <%--</tr>--%>
                                    <%--</table>--%>
                                    <%--<div class="btn-group" style="margin-left: 450px;">--%>
                                        <%--<a class="btn btn-default" href="">View</a>--%>
                                        <%--<a class="btn btn-default" href="">Publish</a>--%>
                                        <%--<a class="btn btn-default" href="">Save</a>--%>
                                    <%--</div>--%>
                                <%--</ul>--%>
                            </div>
                        </div>

                    </div>
                </div>
            </section>
        </section>
    </section>
    <!--main content end-->
</section>
<!-- container section end -->
<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="myModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">x</button>
                <h3 class="modal-title">Filter by</h3>
            </div>
            <div class="modal-body" style="position: relative; left: 70px; top: -21px;">
                <div class="row">
                    <div class="col-md-12">
                        <div class="panel-body">
                            <h3 style="margin-left: 125px;">Categories:</h3>
                            <ul>
                                <li>
                                    <select id="tokenize_focus" name="filter_select" multiple="multiple"
                                            class="tokenize-sample"
                                            style="width:250px; height: 250px;">
                                    </select>

                                    <script type="text/javascript">
                                        $('select#tokenize_focus').tokenize({displayDropdownOnFocus: true});
                                    </script>
                                </li>
                                <li>
                                    <div class="col-lg-offset-2 col-lg-9" style="margin:20px auto;">
                                        <button type="submit" style="margin-left: 330px; margin-bottom: -50px;"
                                                class="btn btn-primary">
                                            Filter
                                        </button>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $.getJSON("/controller?action=categories", function (data) {
        $.each(data, function (key, val) {
            $('#tokenize_focus').append($('<option>', {
                value: val.id,
                text: val.name
            }));
        });
    });
</script>

<script>
    var postCounter = 1;
    function createFeed(postID) {
        var feed = $("#feed");
        var post = "";
        var imgsrc;
        $.getJSON("/controller?action=getpostbyid&post_id=" + postID, function (jsonResponse) {
            post += '<ul style="margin-left:-30px;"> <table width="100%" style="padding:0 50px;">';
            post += '<tr><td style="text-align:left;"><strong>'+'POST #'+ (postCounter++) +'</strong></td> </tr>';
            post += '<tr><td style="text-align:justify;"> <br>'+ jsonResponse.postText +' </td> </tr>';
            for (var i = 0; i < jsonResponse.attachments.length; i++) {
                if (jsonResponse.attachments[i].payload.includes("jpg")) {
                    imgsrc = jsonResponse.attachments[i].payload;
                    break;
                }
            }
            if (imgsrc != null) {
                 post += '<tr><td><img src="' + imgsrc +'" width="600"style="margin:25px;"> </td> </tr>';
            }
            post += '</table><div class="btn-group" style="margin-left: 450px;"> <a class="btn btn-default" href="">View</a> <a class="btn btn-default" href="">Publish</a> <a class="btn btn-default" href="">Save</a></div></ul>';
            post += '<div style="width: 90%; height: 3px;margin:25px auto 25px;border-radius: 4px;background:  lightslategray;"></div>';
            feed.prepend(post); // .prepend(post); - to begin
            $('html, body').css({
                'overflow-y': 'auto',
                'height': 'auto'
            });
        });

    }
</script>
<!-- javascripts -->
<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<!-- nice scroll -->
<script src="${pageContext.request.contextPath}/js/jquery.scrollTo.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.nicescroll.js" type="text/javascript"></script>
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

</body>
</html>

