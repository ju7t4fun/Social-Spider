<%--
  Created by IntelliJ IDEA.
  User: Sasha
  Date: 19.06.2015
  Time: 14:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
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

    <!-- javascripts -->
    <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <!-- nice scroll -->
    <script src="${pageContext.request.contextPath}/js/jquery.scrollTo.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.nicescroll.js" type="text/javascript"></script>
    <!-- gritter -->

    <%--<!-- custom gritter script for this page only-->--%>
    <script src="${pageContext.request.contextPath}/js/gritter.js" type="text/javascript"></script>
    <%--<!--custome script for all page-->--%>
    <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.tokenize.js"></script>

    <%--for table--%>
    <link href="http://cdn.datatables.net/1.10.3/css/jquery.dataTables.css" rel="stylesheet" type="text/css">
    <link href="http://datatables.net/release-datatables/extensions/ColVis/css/dataTables.colVis.css" rel="stylesheet"
          type="text/css">
    <script src="http://cdn.datatables.net/1.10.3/js/jquery.dataTables.min.js"></script>
    <script src="http://datatables.net/release-datatables/extensions/ColVis/js/dataTables.colVis.js"></script>
    <script src="http://jquery-datatables-column-filter.googlecode.com/svn/trunk/media/js/jquery.dataTables.columnFilter.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/fnStandingRedraw.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/fnSetFilteringDelay.js"></script>

    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">

    <link href="${pageContext.request.contextPath}/css/toastr.css" rel="stylesheet" type="text/css"/>
    <script src="${pageContext.request.contextPath}/js/toastr.js"></script>

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
                    <h3 class="page-header"><i class="fa fa-envelope-o"></i> <l:resource key="support"/></h3>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <ol class="breadcrumb">
                        <li><i class="fa fa-home"></i><a href="/"><l:resource key="admin"/></a></li>
                        <li><i class="fa fa-envelope-o"></i><l:resource key="support"/></li>
                    </ol>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-4">
                    <section class="panel" style="overflow: scroll;  height: 63%; padding: 5px;
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
                    <section class="panel" style="padding:15px; height: 63%">
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
                        <c:choose>
                            <c:when test="${current_user != null}">
                                <div class="widget-foot">
                                    <div class="form-inline">
                                        <div class="form-group" style="width: 80%">
                                            <l:resource key="header.typemessagehere"><input id="message_text"
                                                                                            style="width: 100%"
                                                                                            type="text"
                                                                                            class="form-control"
                                                                                            placeholder=""></l:resource>
                                        </div>
                                        <a onclick="send(${current_user.id})" class="btn btn-info"
                                           style="margin-left:5%"><l:resource key="header.send"/></a>
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="widget-foot">
                                    <form class="form-inline">
                                        <div class="form-group" style="width: 80%">
                                            <l:resource key="header.typemessagehere"><input id="message_text1"
                                                                                            style="width: 100%;"
                                                                                            type="text"
                                                                                            class="form-control"
                                                                                            placeholder=""
                                                                                            disabled></l:resource>
                                        </div>
                                        <a onclick="send(${current_user.id})" class="btn btn-info"
                                           style="margin-left:5%" disabled><l:resource
                                                key="header.send"/></a>
                                    </form>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </section>
                </div>
            </div>
        </section>
    </section>
</section>
<script>
    $(document).ready(function () {
        $("#support_inbox_a").scrollTop(10000);
    })
</script>
</body>
</html>
