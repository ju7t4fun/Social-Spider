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

    <script type="text/javascript">
        function selectItem(id) {
            location.href = "support?id=" + id;
        }
    </script>

</head>
<body>

<div hidden>
    <input id="current_user_id" type="hidden" value="${current_user.id}">
    // Повідомлення від користувача
    <li id="me-message_a" class="by-me">
        <div class="avatar pull-left">
            <img src="${current_user.avatarURL}" alt="" width="40" height="40"/>
        </div>
        <div class="chat-content">
            <div class="chat-meta">{user_name}<span class="pull-right">{date}</span></div>
            <div class="chat-meta" style="color:#4c4c4c;font-size:14px;">{message}</div>
            <div class="clearfix"></div>
        </div>
    </li>
    // Повідомлення від адміна
    <li id="admin-message_a" class="by-other">
        <div class="avatar pull-right">
            <img src="${pageContext.request.contextPath}/img/admin.jpg" alt="" width="40" height="40"/>
        </div>
        <div class="chat-content">
            <div class="chat-meta">{date}<span class="pull-right"> Admin </span>
            </div>
            <div class="chat-meta" style="color:#4c4c4c;font-size:14px;">{message}</div>
            <div class="clearfix"></div>
        </div>
    </li>
</div>


<section id="container" class="">

    <jsp:include page="../pagecontent/header.jsp"/>
    <jsp:include page="../pagecontent/sidebar.jsp"/>

    <section id="main-content">
        <section class="wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <ol class="breadcrumb">
                        <li><i class="fa fa-home"></i><a href="/">HOME</a></li>
                        <li><i class="fa fa-user"></i></i><a href="/">ADMIN</a></li>
                        <li><i class="fa fa-list-alt"></i>INBOX</li>
                    </ol>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-4">
                    <section class="panel" style="overflow: scroll;  height: 73%; padding: 5px;
                    overflow-x:hidden">
                        <ul class="nav nav-tabs-justified">
                            <c:forEach var="user" items="${users}">
                                <li class="active" onclick="selectItem(${user.key.id})">
                                    <a data-toggle="tab" href="/admin/support?id=${user.key.id}">
                                            ${user.key.name} ${user.key.surname}
                                                <span id="list-${user.key.id}">
                                                    <c:if test="${user.value>0}">
                                                        <span class="small italic pull-right badge bg-warning">${user.value}</span>
                                                    </c:if>
                                                </span>
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                    </section>
                </div>
                <div class="col-lg-8">
                    <section class="panel" style="padding:15px; height: 73%">
                        <ul id="support_inbox_a" class="chats" style="overflow: scroll; width:100%; height: 88%; padding:
                        5px; overflow-x:hidden">
                            <c:forEach var="msg" items="${messages}">
                                <c:if test="${msg.type == 'TO_ADMIN'}">
                                    <li id="me-message" class="by-me">
                                        <div class="avatar pull-left">
                                            <img src="${current_user.avatarURL}" alt="" width="40" height="40"/>
                                        </div>
                                        <div class="chat-content">
                                            <div class="chat-meta">${current_user.name} ${current_user.surname}
                                                <span class="pull-right"> ${msg.formatData}</span>
                                            </div>
                                            <div class="chat-meta" style="color:#4c4c4c;font-size:14px;">
                                                    ${msg.text}
                                            </div>
                                            <div class="clearfix"></div>
                                        </div>
                                    </li>
                                </c:if>
                                <c:if test="${msg.type == 'TO_USER'}">
                                    <li id="admin-message" class="by-other">
                                        <div class="avatar pull-right">
                                            <img src="${pageContext.request.contextPath}/img/admin.jpg" alt=""
                                                 width="40" height="40"/>
                                        </div>
                                        <div class="chat-content">
                                            <div class="chat-meta">${msg.formatData}<span class="pull-right">
                                                Admin </span>
                                            </div>
                                            <div class="chat-meta" style="color:#4c4c4c;font-size:14px;">
                                                    ${msg.text}
                                            </div>
                                            <div class="clearfix"></div>
                                        </div>
                                    </li>
                                </c:if>
                            </c:forEach>
                        </ul>
                        <br>

                        <div class="widget-foot">
                            <form class="form-inline">
                                <div class="form-group" style="width: 80%">
                                    <input id="message_text" style="width: 100%" id="message_text" type="text"
                                           class="form-control" placeholder="Type your message here...">
                                </div>
                                <input onclick="send(${current_user.id})" type="button" class="btn btn-info"
                                       style="margin-left:5%"
                                       value="Надіслати">
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
