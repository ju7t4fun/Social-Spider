<%--
  Created by IntelliJ IDEA.
  User: Sasha
  Date: 13.06.2015
  Time: 12:43
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

    <title>Accounts</title>

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
<!-- container section start -->
<section id="container" class="">

    <jsp:include page="../pagecontent/header.jsp"/>
    <jsp:include page="../pagecontent/sidebar.jsp"/>

    <!--main content start-->
    <section id="main-content">
        <section class="wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header"><i class="fa fa-list"></i> VK_ACCOUNTS</h3>
                    <ol class="breadcrumb">
                        <li><i class="fa fa-home"></i><a href="/">HOME</a></li>
                        <li><i class="fa fa-user"></i><a href="/user">PROFILE</a></li>
                        <li><i class="fa fa-th-list"></i>VK_ACCOUNTS</li>
                    </ol>
                </div>
            </div>
            <!-- page start-->
            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading">
                            Accounts
                        </header>

                        <table class="table table-striped table-advance table-hover">
                            <tbody>
                            <tr>
                                <th><i class="icon_id-2_alt"></i> Id</th>
                                <th><i class="icon_profile"></i> Full Name</th>
                                <th><i class="icon_link_alt"></i> Url</th>
                                <th><i class="icon_cogs"></i> Action</th>
                            </tr>
                            <c:set var="i" value="0"/>
                            <c:forEach var="profile" items="${profiles}">
                                <tr>
                                    <td>${profile.vkId}</td>
                                    <td>
                                            ${fullNames[i]}
                                    </td>
                                    <td><a target="_blank" href="http://vk.com/id${profile.vkId}">http://vk
                                        .com/id${profile.vkId}</a>
                                    </td>
                                    <td>
                                        <div class="btn-group">
                                                <%--<a class="btn btn-primary" href="#"><i class="icon_plus_alt2"></i></a>--%>
                                            <c:if test="${profile.isExpired()}">
                                                <a class="btn btn-success" href="/user/accounts?action=refresh"><i
                                                        class="icon_refresh"></i></a>
                                            </c:if>
                                            <c:if test="${!profile.isExpired()}">
                                                <a class="btn btn-warning" href="/user/accounts?action=refresh"><i
                                                        class="icon_refresh"></i></a>
                                            </c:if>
                                            <a class="btn btn-danger" href="#"><i class="icon_close_alt2"></i></a>
                                        </div>
                                    </td>
                                </tr>
                                <c:set var="i" value="${i+1}"/>
                            </c:forEach>
                            </tbody>
                        </table>
                    </section>
                    <a href="/user/accounts?action=add" class="btn btn-primary">Add Account</a>
                </div>
            </div>
            <!-- page end-->
        </section>
    </section>
    <!--main content end-->
</section>
<!-- container section end -->
<!-- javascripts -->
<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<!-- nicescroll -->
<script src="${pageContext.request.contextPath}/js/jquery.scrollTo.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.nicescroll.js" type="text/javascript"></script>
<!--custome script for all page-->
<script src="${pageContext.request.contextPath}/js/scripts.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap-switch.js"></script>
</body>
</html>

