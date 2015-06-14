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


    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}js/language.js"></script>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 -->
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
    <script src="${pageContext.request.contextPath}/js/respond.min.js"></script>
    <![endif]-->
</head>

<body class="login-img3-body">

<div class="container">

    <form class="login-form" action="/login?action=signIn" method="post" id="register_form">
        <div class="login-wrap">
            <p class="login-img"><i class="icon_lock_alt"></i></p>
            ${loginMessage}
            <div class="input-group">
                <span class="input-group-addon"><i class="icon_mail_alt"></i></span>
                <l:resource key="login.email"><input type="email" value="${login}" name="email" class="form-control" placeholder="" style="border-color:#ffffff;" required/></l:resource>
            </div>
            <div class="input-group">
                <span class="input-group-addon"><i class="icon_key_alt"></i></span>
                <l:resource key="login.password"><input type="password" name="password" class="form-control" placeholder="" pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$" required/></l:resource>
            </div>
            <label class="checkbox">
                <input type="checkbox" value="remember-me"> <l:resource key="login.remember"/>
                <span class="pull-right"> <a href="#"> <l:resource key="login.forgotpw"/> </a></span>
            </label>
            <button class="btn btn-primary btn-lg btn-block" type="submit" style="margin-bottom:20px;margin-right:10px;"> <l:resource key="login.signin"/>
            </button>

            <span class="pull-right"> <a href="/register"> <l:resource key="login.signup"/> </a> </span>
            <span> <l:resource key="login.signinwith"/> <a href="/login?action=vkAuth"><img src="${pageContext.request.contextPath}/img/vk.png"></a></span>
            <span> <l:resource key="login.or"/> <a href="/login?action=fbAuth"><img src="${pageContext.request.contextPath}/img/fb.png"></a> </span>


        </div>
    </form>

    <div class="footer dark-bg" style="padding-top:30px;padding-bottom:30px;line-height:25px;border-bottom: 0;">
        <div class="main">
            <div style="margin-left:750px;">
                <button class="language-button-logic" change="ua" style="background-color:#fed189; border: 2px solid #fed189;border-radius:10px;">ua</button>
                <button class="language-button-logic" change="en" style="background-color:#00a0df; border: 2px solid #00a0df;border-radius:10px;">en</button>
            </div>
            <div style="margin-top:-20px;">
                <h4>Social-Spider Corp. </h4>
                <h4><p>7 X Evo Ave Rev3 - Victoria 70913 EU</p>
                    <p>(432)555-9876</p>
                </h4>
                <p><a href="mailto:example@gmail.com"><h4>socialspider@gmail.com</h4></a></p>
            </div>
        </div>
    </div>

</div>

<!-- javascripts -->
<script src="js/jquery.js"></script>
<script src="js/bootstrap.min.js"></script>
<!-- nice scroll -->
<script src="js/jquery.scrollTo.min.js"></script>
<script src="js/jquery.nicescroll.js" type="text/javascript"></script>
<!-- jquery validate js -->
<script type="text/javascript" src="js/jquery.validate.min.js"></script>

<!-- custom form validation script for this page-->
<script src="js/form-validation-script.js"></script>
<!--custome script for all page-->
<script src="js/scripts.js"></script>

</body>
</html>