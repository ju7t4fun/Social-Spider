<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
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

    <title>Login Page</title>

    <!-- Bootstrap CSS -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <!-- bootstrap theme -->
    <link href="${pageContext.request.contextPath}/css/bootstrap-theme.css" rel="stylesheet">
    <!--external css-->
    <!-- Plugin CSS -->
    <!--<link rel="stylesheet" href="${pageContext.request.contextPath}/css/animate.min.css" type="text/css">-->

    <!-- Custom CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/creative.css" type="text/css">
    <!-- font icon -->
    <link href="${pageContext.request.contextPath}/css/elegant-icons-style.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet"/>
    <!-- Custom styles -->
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style-responsive.css" rel="stylesheet"/>


    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/language.jsp"></script>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 -->
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
    <script src="${pageContext.request.contextPath}/js/respond.min.js"></script>
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

<body class="login-img3-body">
<nav id="mainNav" class="navbar navbar-default navbar-fixed-top">

    <header class="header dark-bg" style="background: rgb(26, 39, 50)">
        <div class="main">
            <a href="${pageContext.request.contextPath}/" class="logo">Social <span class="lite">Spider</span></a>
        </div>

    </header>
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand page-scroll" href="${pageContext.request.contextPath}/">Socail spider</a>
        </div>
    </div>
</nav>
<header class="back-header">
    <div class="container">

        <form class="login-form" action="${pageContext.request.contextPath}/login?action=signIn" method="post" id="login_form">
            <div class="login-wrap">
                <p class="login-img"><i class="icon_lock_alt"></i></p>

                <div class="form-group">
                    <!--<span class="input-group-addon"><i class="icon_mail_alt"></i></span>-->
                    <l:resource key="login.email"><input type="email" value="${login}" name="email" id="email"
                                                         class="form-control" placeholder=""
                                                         style="border-color:#ffffff;"
                                                         required/></l:resource>
                </div>
                <div class="form-group">
                    <!--<span class="input-group-addon"><i class="icon_key_alt"></i></span>-->
                    <l:resource key="login.password"><input type="password" name="password" id="password"
                                                            class="form-control" placeholder="" required/></l:resource>
                </div>
                <label class="checkbox">
                    <span class="pull-right"> <a href="${pageContext.request.contextPath}/forgot_password"> <l:resource key="login.forgotpw"/> </a></span>
                </label>
                <button class="btn btn-primary btn-lg btn-block" type="submit"
                        style="margin-bottom:20px;margin-right:10px;"><l:resource key="login.signin"/>
                </button>
                <span class="pull-right"> <a href="${pageContext.request.contextPath}/register"> <l:resource key="login.signup"/> </a> </span>
                <span><l:resource key="login.signinwith"/> <a href="${pageContext.request.contextPath}/login?action=vkAuth"><img
                        src="${pageContext.request.contextPath}/img/vk.png"></a></span>
                <span> <l:resource key="login.or"/> <a href="${pageContext.request.contextPath}/login?action=fbAuth"><img
                        src="${pageContext.request.contextPath}/img/fb.png"></a> </span>
            </div>
        </form>
    </div>
</header>
<script>
    $(document).ready(function () {
        $.vegas('slideshow', {
            backgrounds: [
                {src: '${pageContext.request.contextPath}/img/header.jpg', fade: 2000, delay: 9000},
                {src: '${pageContext.request.contextPath}/img/2.jpg', fade: 2000, delay: 9000},
                {src: '${pageContext.request.contextPath}/img/4.jpg', fade: 2000, delay: 9000},
            ]
        });
    });
</script>
<script>
    var Script = function () {
        $().ready(function () {
            $("#login_form").validate({
                rules: {
                    password: {
                        required: true,
                        minlength: 5
                    },
                    email: {
                        required: true,
                        email: true
                    }
                },
                messages: {
                    password: {
                        required: "Please provide a password.",
                        minlength: "Your password must be at least 5 characters long."
                    },
                    email: "Please enter a valid email address.",
                    agree: "Please accept our terms & condition."
                },
                errorPlacement: function (error, element) {
                    element.attr('title', error.text());
                    $(".error").tooltip(
                            {
                                tooltipClass: "mytooltip",
                                placement: 'top',
                                html: true
                            });
                }
            });
        });
    }();
</script>
<jsp:include page="../pagecontent/simple_footer.jsp"/>

<script src="js/bootstrap.min.js"></script>
<!-- nice scroll -->
<script src="${pageContext.request.contextPath}/js/jquery.scrollTo.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.nicescroll.js" type="text/javascript"></script>
<!-- jquery validate js -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
<!--custome script for all page-->
<script src="${pageContext.request.contextPath}/js/scripts.js"></script>
<!-- EASING SCROLL SCRIPTS PLUGIN -->
<script src="${pageContext.request.contextPath}/js/jquery.vegas.min.js"></script>
<!-- VEGAS SLIDESHOW SCRIPTS -->
<script src="${pageContext.request.contextPath}/js/jquery.easing.min.js"></script>

</body>

</html>