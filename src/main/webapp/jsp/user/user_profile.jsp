<%--
  Created by IntelliJ IDEA.
  User: Sasha
  Date: 19.06.2015
  Time: 12:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Creative - Bootstrap 3 Responsive Admin Template">
    <meta name="author" content="GeeksLabs">
    <meta name="keyword" content="Creative, Dashboard, Admin, Template, Theme, Bootstrap, Responsive, Retina, Minimal">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/icons/favicon.png">

    <title>My Profile</title>

    <!-- For editable -->
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/bootstrap-editable.css" rel="stylesheet">
    <script src="http://code.jquery.com/jquery-2.0.3.min.js"></script>

    <%--Conflicted js--%>
    <%--<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>--%>

    <script src="${pageContext.request.contextPath}/js/bootstrap-editable.min.js"></script>

    <!-- Bootstrap CSS -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <!-- bootstrap theme -->
    <link href="${pageContext.request.contextPath}/css/bootstrap-theme.css" rel="stylesheet">
    <!--external css-->

    <link href="${pageContext.request.contextPath}/css/fileinput.css" media="all" rel="stylesheet"
          type="text/css"/>
    <script src="${pageContext.request.contextPath}/js/fileinput.min.js" type="text/javascript"></script>

    <!-- font icon -->
    <link href="${pageContext.request.contextPath}/css/elegant-icons-style.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet"/>
    <!-- Custom styles -->
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style-responsive.css" rel="stylesheet"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.tokenize.js"></script>
    <link rel="stylesheet" type="text/css" href="css/jquery.tokenize.css"/>
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 -->
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
    <script src="${pageContext.request.contextPath}/js/respond.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/lte-ie7.js"></script>
    <![endif]-->

    <link href="${pageContext.request.contextPath}/css/toastr.css" rel="stylesheet" type="text/css"/>
    <script src="${pageContext.request.contextPath}/js/toastr.js"></script>
    <script type="text/javascript">

        // При завантаженні сторінки
        setTimeout(function () {
            if (${toastr_notification!=null}) {
                var args = "${toastr_notification}".split("|");
                toastrNotification(args[0], args[1]);
            }
        }, 500);

    </script>

</head>

<body>
<!-- container section start -->
<section id="container" class="">

    <jsp:include page="../pagecontent/header.jsp"/>
    <jsp:include page="../pagecontent/sidebar.jsp"/>

    <!--main content start-->
    <section id="main-content">
        <section class="wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header"><i class="fa fa-user-md"></i> PROFILE</h3>
                    <ol class="breadcrumb">
                        <li><i class="fa fa-home"></i><a href="/">HOME</a></li>
                        <li><i class="fa fa-user-md"></i>PROFILE</li>
                    </ol>
                </div>
            </div>
            <div class="row">
                <!-- profile-widget -->
                <div class="col-lg-12">
                    <div class="profile-widget profile-widget-info">
                        <div class="panel-body">
                            <div class="col-lg-2 col-sm-2">
                                <h4><span class="userfname">${user.name} </span><span> </span><span
                                        class="userlname">${user.surname}</span></h4>

                                <div class="follow-ava">
                                    <img class="avatar" src="${user.avatarURL}" alt="">
                                </div>
                                <h6>${user.role}</h6>
                            </div>
                            <div class="col-lg-4 col-sm-4 follow-info">

                                <p><i>Email: ${user.email}</i></p>
                                <h6>
                                    <span><i class="icon_calendar"></i>${user.createTime}</span>
                                </h6>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- page start-->
            <section class="panel">
                <div class="panel-body bio-graph-info">
                    <h1>Bio Graph</h1>

                    <div class="row">
                        <div class="bio-row">
                            <span>First Name: <span id="name"> ${user.name}</span></span>
                        </div>
                        <script>
                            $.fn.editable.defaults.mode = 'inline';
                            $('#name').editable({
                                type: 'text',
                                pk: 1,
                                url: 'http://localhost:8080/profile?action=edit',
                                title: 'Enter your First name',
                                success: function (response) {
                                    if (response.status == 'error') return response.msg;
                                }
                            });
                        </script>
                        <div class="bio-row">
                            <span>Last Name: <span id="surname"> ${user.surname} </span></span>
                        </div>
                        <script>
                            $.fn.editable.defaults.mode = 'inline';
                            $('#surname').editable({
                                type: 'text',
                                pk: 1,
                                url: 'http://localhost:8080/profile?action=edit',
                                title: 'Enter your Last name',
                                success: function (response) {
                                    if (response.status == 'error') return response.msg;
                                }
                            });
                        </script>
                        <div class="bio-row">
                            <span>Email: <span id="email"> ${user.email}</span></span>
                        </div>
                        <div class="bio-row">
                            <span>Change password: <span id="change_password"> </span>
                                <a href="/profile?action=change&email=${user.email}">Change password</a> </span>
                        </div>
                        <div class="bio-row">
                            <span>Change avatar:
                                <a href="#" onclick="showUpload();">Select avatar</a></span>
                            <script>
                                function showUpload() {
                                    if ($('#compForm').is(":visible")) {
                                        $('#compForm').hide();
                                    } else {
                                        $('#compForm').show();
                                    }
                                }
                                function uploadFromURL() {
                                    $('html, body').animate({
                                        scrollTop: $(".widget-foot").offset().top
                                    }, 1000);
                                }
                            </script>
                            <script>
                                $(document).ajaxComplete(function (event, xhr, settings) {
                                    if (settings.url === "http://localhost:8080/profile?action=changeAvatar") {
                                        var response = JSON.parse(xhr.responseText);
                                        $(".avatar").attr("src", response.success);
                                        toastrNotification(response.status, response.msg);
                                    }
                                });
                            </script>
                            <script>
                                $(document).ajaxComplete(function (event, xhr, settings) {
                                    if (settings.url === "http://localhost:8080/profile?action=edit") {
                                        var response = JSON.parse(xhr.responseText);
                                        $(".userfname").text(response.fname);
                                        $(".userlname").text(response.lname);
                                        toastrNotification(response.status, response.msg);
                                    }
                                });
                            </script>
                            <div id="compForm" class="container kv-main" style="width:800px; margin-top:20px;">
                                <input id="input-dim-2" type="file"
                                       multiple="true" method="post"
                                       enctype="multipart/form-data"
                                       accept="image/*">
                                <script>
                                    $("#input-dim-2").fileinput({
                                        uploadUrl: "http://localhost:8080/profile?action=changeAvatar",
                                        allowedFileExtensions: ['jpg', 'gif', 'png', 'jpeg'],
                                        maxFileCount: 1
                                    });
                                </script>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <section>
                <script>
                    $('#compForm').hide();
                </script>
                <!-- page end-->
            </section>
        </section>
        <!--main content end-->
    </section>
    <!-- container section end -->

    <!-- javascripts -->
    <script src="${pageContext.request.contextPath}/js/bootstrap-editable.min.js"></script>

    <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <!-- nice scroll -->
    <script src="${pageContext.request.contextPath}/js/jquery.scrollTo.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.nicescroll.js" type="text/javascript"></script>
    <!--custome script for all page-->
    <script src="${pageContext.request.contextPath}/js/scripts.js"></script>


</body>
</html>

