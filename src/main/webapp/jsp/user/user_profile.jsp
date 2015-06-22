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
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
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

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 -->
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
    <script src="${pageContext.request.contextPath}/js/respond.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/lte-ie7.js"></script>
    <![endif]-->
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
                    <h3 class="page-header"><i class="fa fa-user-md"></i> Profile</h3>
                    <ol class="breadcrumb">
                        <li><i class="fa fa-home"></i><a href="index.html">Home</a></li>
                        <li><i class="fa fa-user-md"></i>Profile</li>
                    </ol>
                </div>
            </div>
            <div class="row">
                <!-- profile-widget -->
                <div class="col-lg-12">
                    <div class="profile-widget profile-widget-info">
                        <div class="panel-body">
                            <div class="col-lg-2 col-sm-2">
                                <h4>USER NAME</h4>

                                <div class="follow-ava">
                                    <img src="${pageContext.request.contextPath}/img/profile-widget-avatar.jpg" alt="">
                                </div>
                                <h6>USER ROLE (User)</h6>
                            </div>
                            <div class="col-lg-4 col-sm-4 follow-info">
                                <p>Hello I’m User, and this is general information about me.</p>

                                <p><i class="fa fa-twitter">USER_TWITTER_ACCOUNT</i></p>
                                <h6>
                                    <span><i class="icon_clock_alt"></i>11:05 AM</span>
                                    <span><i class="icon_calendar"></i>25.10.13</span>
                                    <span><i class="icon_pin_alt"></i>NY</span>
                                </h6>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- page start-->
            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading tab-bg-info">
                            <ul class="nav nav-tabs" style="margin-left:700px;">
                                <li class="active">
                                    <a data-toggle="tab" href="#recent-activity">
                                        <i class="icon-home"></i>
                                        Chat
                                    </a>
                                </li>
                                <li>
                                    <a data-toggle="tab" href="#profile">
                                        <i class="icon-user"></i>
                                        Profile
                                    </a>
                                </li>
                            </ul>
                        </header>
                        <div class="panel-body">
                            <div class="tab-content">
                                <div id="recent-activity" class="tab-pane active">
                                    <div class="profile-activity">
                                        <div class="col-md-4 portlets">
                                            <!-- Widget -->
                                            <div class="panel panel-default" style="width:1100px;">
                                                <div class="panel-heading">
                                                    <div class="pull-left">Message</div>
                                                    <div class="clearfix"></div>
                                                </div>

                                                <div class="panel-body">
                                                    <!-- Widget content -->
                                                    <div class="padd sscroll">

                                                        <ul class="chats">

                                                            <!-- Chat by us. Use the class "by-me". -->
                                                            <li class="by-me">
                                                                <!-- Use the class "pull-left" in avatar -->
                                                                <div class="avatar pull-left">
                                                                    <img src="${pageContext.request.contextPath}/img/user.jpg"
                                                                         alt=""/>
                                                                </div>

                                                                <div class="chat-content">
                                                                    <!-- In meta area, first include "name" and then "time" -->
                                                                    <div class="chat-meta">John Smith <span
                                                                            class="pull-right">3 hours ago</span></div>
                                                                    Vivamus diam elit diam, consectetur dapibus
                                                                    adipiscing elit.
                                                                    <div class="clearfix"></div>
                                                                </div>
                                                            </li>

                                                            <!-- Chat by other. Use the class "by-other". -->
                                                            <li class="by-me">
                                                                <!-- Use the class "pull-right" in avatar -->
                                                                <div class="avatar pull-left">
                                                                    <img src="${pageContext.request.contextPath}/img/user22.png"
                                                                         alt=""/>
                                                                </div>

                                                                <div class="chat-content">
                                                                    <!-- In the chat meta, first include "time" then "name" -->
                                                                    <div class="chat-meta">Jenifer Smith <span
                                                                            class="pull-right">3 hours ago</span></div>
                                                                    Vivamus diam elit diam, consectetur fconsectetur
                                                                    dapibus adipiscing elit.
                                                                    <div class="clearfix"></div>
                                                                </div>
                                                            </li>

                                                            <li class="by-me">
                                                                <div class="avatar pull-left">
                                                                    <img src="${pageContext.request.contextPath}/img/user.jpg"
                                                                         alt=""/>
                                                                </div>

                                                                <div class="chat-content">
                                                                    <div class="chat-meta">John Smith <span
                                                                            class="pull-right">4 hours ago</span></div>
                                                                    Vivamus diam elit diam, consectetur fermentum sed
                                                                    dapibus eget, Vivamus consectetur dapibus adipiscing
                                                                    elit.
                                                                    <div class="clearfix"></div>
                                                                </div>
                                                            </li>

                                                            <li class="by-me">
                                                                <!-- Use the class "pull-right" in avatar -->
                                                                <div class="avatar pull-left">
                                                                    <img src="${pageContext.request.contextPath}/img/user22.png"
                                                                         alt=""/>
                                                                </div>

                                                                <div class="chat-content">
                                                                    <!-- In the chat meta, first include "time" then "name" -->
                                                                    <div class="chat-meta">Jenifer Smith<span
                                                                            class="pull-right">3 hours ago </span></div>
                                                                    Vivamus diam elit diam, consectetur fermentum sed
                                                                    dapibus eget, Vivamus consectetur dapibus adipiscing
                                                                    elit.
                                                                    <div class="clearfix"></div>
                                                                </div>
                                                            </li>

                                                        </ul>

                                                    </div>
                                                    <!-- Widget footer -->
                                                    <div class="widget-foot">

                                                        <form class="form-inline">
                                                            <div class="form-group">
                                                                <input type="text" class="form-control"
                                                                       placeholder="Type your message here..."
                                                                       style="width:700px;">
                                                            </div>
                                                            <button type="submit" class="btn btn-info">Send</button>
                                                        </form>


                                                    </div>
                                                </div>


                                            </div>
                                        </div>

                                    </div>
                                </div>
                                <!-- profile -->
                                <div id="profile" class="tab-pane">
                                    <section class="panel">
                                        <div class="panel-body bio-graph-info">
                                            <h1>Bio Graph</h1>

                                            <div class="row">
                                                <div class="bio-row">
                                                    <span>First Name: <span id="name"> USER_FIRST_NAME</span></span>
                                                </div>
                                                <script>
                                                    $.fn.editable.defaults.mode = 'inline';
                                                    $('#name').editable({
                                                        type: 'text',
                                                        pk: 1,
                                                        url: 'http://localhost:8080/controller?action=editprofile',
                                                        title: 'Enter your First name',
                                                        success: function (response) {
                                                            if (response.status == 'error') return response.msg; //msg will be shown in editable form
                                                        }
                                                    });
                                                </script>
                                                <div class="bio-row">
                                                    <span>Last Name: <span id="surname"> USER_LAST_NAME</span></span>
                                                </div>
                                                <script>
                                                    $.fn.editable.defaults.mode = 'inline';
                                                    $('#surname').editable({
                                                        type: 'text',
                                                        pk: 1,
                                                        url: 'http://localhost:8080/controller?action=editprofile',
                                                        title: 'Enter your Last name',
                                                        success: function (response) {
                                                            if (response.status == 'error') return response.msg; //msg will be shown in editable form
                                                        }
                                                    });
                                                </script>
                                                <div class="bio-row">
                                                    <span>Email: <span id="email"> EMAIL</span></span>
                                                </div>
                                                <div class="bio-row">
                                                    <span>Change password: <span id="change_password"> </span>Change password</span>
                                                </div>
                                                <div class="bio-row">
                                                    <span>Change avatar:
                                                        <a href="#" onclick="showUpload();">Select
                                                            avatar</a></span>
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
                                                    <div id="compForm" class="container kv-main" style="width:800px;
                                                      margin-top:20px;">
                                                        <input id="input-dim-2" type="file"
                                                               multiple="true" method="post"
                                                               enctype="multipart/form-data"
                                                               accept="image/*">
                                                        <script>
                                                            $("#input-dim-2").fileinput({
                                                                uploadUrl:
                                                                        "http://localhost:8080/controller?action=editprofile",
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
                                        <div class="row">
                                        </div>
                                    </section>
                                </div>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
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
<!-- jquery knob -->
<script src="${pageContext.request.contextPath}/assets/jquery-knob/js/jquery.knob.js"></script>
<!--custome script for all page-->
<script src="${pageContext.request.contextPath}/js/scripts.js"></script>

<script>

    //knob
    $(".knob").knob();

</script>


</body>
</html>

