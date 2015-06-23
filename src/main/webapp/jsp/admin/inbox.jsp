<%--
  Created by IntelliJ IDEA.
  User: Sasha
  Date: 19.06.2015
  Time: 14:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Creative - Bootstrap 3 Responsive Admin Template">
    <meta name="author" content="GeeksLabs">
    <meta name="keyword" content="Creative, Dashboard, Admin, Template, Theme, Bootstrap, Responsive, Retina, Minimal">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/icons/favicon.png">

    <title>My Inbox</title>

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

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 -->
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
    <script src="${pageContext.request.contextPath}/js/respond.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/lte-ie7.js"></script>
    <![endif]-->
</head>
<body>

<section id="container" class="">

    <jsp:include page="../pagecontent/header.jsp"/>
    <jsp:include page="../pagecontent/sidebar.jsp"/>

    <section id="main-content">
        <section class="wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <ol class="breadcrumb">
                        <li><i class="fa fa-home"></i><a href="index.html">Home</a></li>
                        <li><i class="fa fa-list-alt"></i>Inbox</li>
                    </ol>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-4">
                    <section class="panel" style="overflow: scroll;  height: 73%; padding: 5px;
                    overflow-x:hidden">
                        <ul class="nav nav-tabs-justified">
                            <c:forEach var="user" items="${users}">
                                <li class="active">
                                    <a data-toggle="tab" href="#recent-activity">
                                            ${user.key.name} ${user.key.surname}
                                        <c:if test="${user.value>0}">
                                            <span class="small italic pull-right badge bg-warning">${user.value}</span>
                                        </c:if>
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                    </section>
                </div>
                <div class="col-lg-8">
                    <section class="panel" style="padding:15px;">
                        <ul class="chats">
                            <li class="by-me">
                                <div class="avatar pull-left">
                                    <img src="${pageContext.request.contextPath}/img/user.jpg" alt=""/>
                                </div>

                                <div class="chat-content">
                                    <div class="chat-meta">John Smith <span class="pull-right">3 hours ago</span></div>
                                    <div class="chat-meta" style="color:#4c4c4c;font-size:14px;">Vivamus diam elit diam,
                                        consectetur dapibus adipiscing elit.
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </li>

                            <li class="by-other">
                                <div class="avatar pull-right">
                                    <img src="${pageContext.request.contextPath}/img/user22.png" alt=""/>
                                </div>

                                <div class="chat-content">
                                    <div class="chat-meta">3 hours ago <span class="pull-right">Jenifer Smith</span>
                                    </div>
                                    <div class="chat-meta" style="color:#4c4c4c;font-size:14px;">Vivamus diam elit diam,
                                        consectetur fconsectetur dapibus adipiscing elit.
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </li>

                            <li class="by-me">
                                <div class="avatar pull-left">
                                    <img src="${pageContext.request.contextPath}/img/user.jpg" alt=""/>
                                </div>

                                <div class="chat-content">
                                    <div class="chat-meta">John Smith <span class="pull-right">4 hours ago</span></div>
                                    <div class="chat-meta" style="color:#4c4c4c;font-size:14px;">Vivamus diam elit diam,
                                        consectetur fermentum sed dapibus eget, Vivamus consectetur dapibus adipiscing
                                        elit.
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </li>

                            <li class="by-other">
                                <!-- Use the class "pull-right" in avatar -->
                                <div class="avatar pull-right">
                                    <img src="${pageContext.request.contextPath}/img/user22.png" alt=""/>
                                </div>

                                <div class="chat-content">
                                    <!-- In the chat meta, first include "time" then "name" -->
                                    <div class="chat-meta">3 hours ago <span class="pull-right">Jenifer Smith</span>
                                    </div>
                                    <div class="chat-meta" style="color:#4c4c4c;font-size:14px;">Vivamus diam elit diam,
                                        consectetur fermentum sed dapibus eget, Vivamus consectetur dapibus adipiscing
                                        elit.
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </li>

                        </ul>

                        <div class="widget-foot">

                            <form class="form-inline">
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="Type your message here..."
                                           style="width:450px;">
                                </div>
                                <button type="submit" class="btn btn-info" style="margin-left:30px;">Send</button>
                            </form>
                        </div>

                    </section>
                </div>
            </div>
        </section>
    </section>

</section>
<!-- container section end -->
<!-- javascripts -->
<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<!-- nice scroll -->
<script src="${pageContext.request.contextPath}/js/jquery.scrollTo.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.nicescroll.js" type="text/javascript"></script>
<!-- jquery knob -->
<script src="${pageContext.request.contextPath}/assets/jquery-knob/js/jquery.knob.js"></script>
<!--custome script for all page-->
<script src="${pageContext.request.contextPath}/js/scripts.js"></script>

</body>
</html>
