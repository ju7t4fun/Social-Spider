<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

    <title>Login Page</title>

    <!-- Bootstrap CSS -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <!-- bootstrap theme -->
    <link href="${pageContext.request.contextPath}/css/bootstrap-theme.css" rel="stylesheet">
    <!--external css-->
    <!-- font icon -->
    <link href="${pageContext.request.contextPath}/css/elegant-icons-style.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet" />
    <!-- Custom styles -->
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style-responsive.css" rel="stylesheet" />

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 -->
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
    <script src="${pageContext.request.contextPath}/js/respond.min.js"></script>
    <![endif]-->
</head>

<body class="login-img3-body">

<div class="container">

    <form class="login-form" action="/login?action=signIn" method="post">
        <div class="login-wrap">
            <p class="login-img"><i class="icon_lock_alt"></i></p>
            ${loginMessage}
            <div class="input-group">
                <span class="input-group-addon"><i class="icon_mail_alt"></i></span>
                <input type="email" value="${login}" name="email" class="form-control" placeholder="<l:resource key="login.email"/>" style="border-color:#ffffff;" autofocus>
            </div>
            <div class="input-group">
                <span class="input-group-addon"><i class="icon_key_alt"></i></span>
                <input type="password" name="password" class="form-control" placeholder="<l:resource key="login.password"/>">
            </div>
            <label class="checkbox">
                <input type="checkbox" value="remember-me"> <l:resource key="login.remember"/>
                <span class="pull-right"> <a href="#"> <l:resource key="login.forgotpw"/> </a></span>
            </label>
            <button class="btn btn-primary btn-lg btn-block" type="submit" style="margin-bottom:20px;margin-right:10px;"> <l:resource key="login.signin"/> </button>


            <span class="pull-right"> <a href="${pageContext.request.contextPath}/jsp/user/registration.jsp"> <l:resource key="login.signup"/> </a> </span>

            <span> <l:resource key="login.signinwith"/> <a href="/login?action=vkAuth"><img src="${pageContext.request.contextPath}/img/vk.png"></a> </span>
            <span> <l:resource key="login.or"/> <a href="/login?action=fbAuth"><img src="${pageContext.request.contextPath}/img/fb.png"></a> </span>


        </div>
    </form>

</div>


</body>
</html>