<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
<%@ taglib prefix="lg" uri="http://lab.epam.com/spider/logger" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Social spider</title>

    <!-- Bootstrap Core CSS -->
    <link href="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css">
    <script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
    <!-- Custom styles -->
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style-responsive.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/style-blue.css" rel="stylesheet"/>
    <!-- Custom Fonts -->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800'
          rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,400italic,700,700italic,900,900italic'
          rel='stylesheet' type='text/css'>
    <%--<link rel="stylesheet" href="${pageContext.request.contextPath}/font-awesome/css/font-awesome.min.css"--%>
    <%--type="text/css">--%>

    <!-- Plugin CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/animate.min.css" type="text/css">

    <!-- Custom CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/creative.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/team.style.css" type="text/css">
    <!-- FONT ICON -->
    <link href="${pageContext.request.contextPath}/css/elegant-icons-style.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>

    <![endif]-->

    <script src="${pageContext.request.contextPath}/js/language.jsp"></script>
</head>

<body id="page-top">

<lg:log level="info">INFO MESSAGE FROM INDEX.JSP</lg:log>

<nav id="mainNav" class="navbar navbar-default navbar-fixed-top">

    <c:choose>
        <c:when test="${user.role == 'ADMIN'}">
            <jsp:forward page="/jsp/admin/index.jsp"/>
        </c:when>
        <c:when test="${user.role == 'USER'}">
            <jsp:forward page="/jsp/user/feed.jsp"/>
        </c:when>
        <c:otherwise>
            <header class="header dark-bg" style="background: rgb(26, 39, 50)">
                <div class="main">
                    <a href="#page-top" class="logo">Social <span class="lite">Spider</span></a>

                    <div class="commands">
                        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                            <ul class="nav navbar-nav navbar-right">
                                <li>
                                    <a class="page-scroll" href="#about"><l:resource key="index.about"/></a>
                                </li>
                                <li>
                                    <a class="page-scroll" href="#services"><l:resource key="index.opportunities"/></a>
                                </li>
                                <li>
                                    <a class="page-scroll" href="#team"><l:resource key="index.team"/></a>
                                </li>
                                <li>
                                    <a class="page-scroll" href="#contact"><l:resource key="index.contact"/></a>
                                </li>
                                <li>
                                    <a class="page-scroll" href="${pageContext.request.contextPath}/login"><l:resource key="index.signin"/></a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>

            </header>
        </c:otherwise>
    </c:choose>
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <a class="navbar-brand page-scroll" href="#page-top">Social spider</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->

        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container-fluid -->
</nav>

<header class="back-header">
    <div class="header-content">
        <div class="header-content-inner">
            <h1>SOCIAL SPIDER</h1>
            <hr>
            <p>make it easy</p>
            <a href="${pageContext.request.contextPath}/login" class="btn btn-primary btn-xl page-scroll"><l:resource key="index.signin"/></a>
        </div>
    </div>
</header>

<section class="bg-primary" id="about">
    <div class="container">
        <div class="row">
            <div class="col-lg-8 col-lg-offset-2 text-center">
                <h2 class="section-heading"><l:resource key="index.whatyouneed"/></h2>
                <hr class="light">
                <p class="text-faded"><l:resource key="index.whatyouneedtext"/></p>
                <l:resource key="index.demo.info"><p class="text-faded text-primary"></p></l:resource>
            </div>
        </div>
    </div>
</section>

<div class="services">
    <section id="services">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2 class="section-heading"><l:resource key="index.serviceopports"/></h2>
                    <hr class="primary">
                </div>
            </div>
        </div>
        <div class="container">
            <div class="row">
                <div class="col-lg-3 col-md-6 text-center">
                    <div class="service-box">
                        <i class="fa fa-4x fa-diamond wow bounceIn text-primary"></i>

                        <h3><l:resource key="index.op1_title"/></h3>

                        <p class="text-muted"><l:resource key="index.op1"/></p>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6 text-center">
                    <div class="service-box">
                        <i class="fa fa-4x fa-paper-plane wow bounceIn text-primary" data-wow-delay=".1s"></i>

                        <h3><l:resource key="index.op2_title"/></h3>

                        <p class="text-muted"><l:resource key="index.op2"/></p>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6 text-center">
                    <div class="service-box">
                        <i class="fa fa-4x fa-newspaper-o wow bounceIn text-primary" data-wow-delay=".2s"></i>

                        <h3><l:resource key="index.op3_title"/></h3>

                        <p class="text-muted"><l:resource key="index.op3"/></p>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6 text-center">
                    <div class="service-box">
                        <i class="fa fa-4x fa-heart wow bounceIn text-primary" data-wow-delay=".3s"></i>

                        <h3><l:resource key="index.op4_title"/></h3>

                        <p class="text-muted"><l:resource key="index.op4"/></p>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>

<div class="team">

    <!--TEAM SECTION START-->
    <section id="team">
        <div class="team-members">
            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                <h3 id="largefont"><l:resource key="index.teammembers"/></h3>
                <hr/>
            </div>
        </div>
        <div class="container">
            <div class="row text-center header animate-in" data-anim-type="fade-in-up">

            </div>


            <div class="row animate-in" data-anim-type="fade-in-up">
                <div id="team-yura" class="col-sm-4">
                    <div class="team-member team-wrapper">
                        <img src="${pageContext.request.contextPath}/img/team/yura.png" class="img-responsive " alt="">
                        <h4>Yura Kovalik</h4>
                        <p class="text-muted">Developer & Admin</p>
                        <ul class="list-inline social-buttons">
                            <li><a href="https://ua.linkedin.com/pub/yura-kovalik/61/581/818" target="_blank"><i class="fa fa-linkedin"></i></a></li>
                            <li><a href="skype:sm_1423?chat"><i class="fa fa-skype"></i></a></li>
                            <li><a  href="mailto:kovalik.yura@aol.com" target="_blank"><i class="fa fa-envelope-o "></i></a></li>
                        </ul>
                    </div>
                </div>
                <div id="team-marian" class="col-sm-4">
                    <div class="team-member team-wrapper">
                        <img src="${pageContext.request.contextPath}/img/team/maryan.png" class="img-responsive" alt="">
                        <h4>Marian Voronovskyi</h4>
                        <p class="text-muted">Developer</p>
                        <ul class="list-inline social-buttons">
                            <li><a href="https://ua.linkedin.com/in/marianvoronovskyi" target="_blank"><i class="fa fa-linkedin"></i></a></li>
                            <li><a href="skype:marik431?chat"><i class="fa fa-skype"></i></a></li>
                            <li><a  href="mailto:marian.voronovskyi@gmail.com" target="_blank"><i class="fa fa-envelope-o "></i></a></li>
                        </ul>
                    </div>
                </div>

                <div id="team-orest" class="col-sm-4">
                    <div class="team-member team-wrapper">
                        <img src="${pageContext.request.contextPath}/img/team/orest.png" class="img-responsive" alt="">
                        <h4>Dzyuba Orest</h4>
                        <p class="text-muted">Developer</p>
                        <ul class="list-inline social-buttons">
                            <li><a href="https://ua.linkedin.com/pub/orest-dzyuba/101/b61/267" target="_blank"><i class="fa fa-linkedin"></i></a></li>
                            <li><a href="skype:nevermorepls?chat"><i class="fa fa-skype"></i></a></li>
                            <li><a  href="mailto:dzyubaorest@gmail.com" target="_blank"><i class="fa fa-envelope-o "></i></a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="row animate-in" data-anim-type="fade-in-up">
                <div class="col-sm-4">
                    <div id="team-oleksandra" class="team-member team-wrapper">
                        <img class="img-responsive " src="${pageContext.request.contextPath}/img/team/sasha.png" alt="">
                        <h4>Oleksandra Lobanok</h4>
                        <p class="text-muted">Developer</p>
                    </div>
                </div>
                <div id="team-oles" class="col-sm-4">
                    <div class="team-member team-wrapper">
                        <img src="${pageContext.request.contextPath}/img/team/oles.png" class="img-responsive " alt="">
                        <h4>Oles Vushko</h4>
                        <p class="text-muted">Developer</p>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="team-member team-wrapper">
                        <img class="img-responsive team-photo-unborder" src="${pageContext.request.contextPath}/img/team/personne.png" alt="">
                        <%--<h4>Vitaliy Boyarsky</h4>--%>
                        <h4>Anonymous Person</h4>
                        <p class="text-muted">Ex Team Lead & Developer</p>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!--TEAM SECTION END-->

</div>


<div class="services">
    <section id="contact">
        <div class="container">
            <div class="row">

                <div class="col-lg-8 col-lg-offset-2 text-center">
                    <h2 class="section-heading">Social Spider Demo</h2>
                    <hr class="primary">
                    <l:resource key="index.contactus"><p></p></l:resource>
                    <l:resource key="index.contactus.unphone"><p class="text-primary"></p></l:resource>

                </div>
                <div class="col-lg-4 col-lg-offset-2 text-center">
                    <i class="fa fa-phone fa-3x wow bounceIn"></i>

                    <p>+38(055)555-55-55</p>
                </div>
                <div class="col-lg-4 text-center">
                    <i class="fa fa-envelope-o fa-3x wow bounceIn" data-wow-delay=".1s"></i>

                    <p><a href="mailto:kovalik.yura@aol.com" target="_blank">kovalik.yura@aol.com</a></p>
                </div>
                <div class="btn-group" style="margin-left: 211px">
                    <a class="btn btn-default btn-sm" change="ua">UA</a>
                    <a class="btn btn-default btn-sm" change="en">EN</a>
                </div>
            </div>
        </div>
    </section>
</div>
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
<!-- jQuery -->
<script src="${pageContext.request.contextPath}/js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

<!-- Plugin JavaScript -->
<script src="${pageContext.request.contextPath}/js/jquery.easing.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.fittext.js"></script>
<script src="${pageContext.request.contextPath}/js/wow.min.js"></script>

<!-- Custom Theme JavaScript -->
<script src="${pageContext.request.contextPath}/js/creative.js"></script>

<!-- EASING SCROLL SCRIPTS PLUGIN -->
<script src="${pageContext.request.contextPath}/js/jquery.vegas.min.js"></script>
<!-- VEGAS SLIDESHOW SCRIPTS -->
<script src="${pageContext.request.contextPath}/js/jquery.easing.min.js"></script>
<script src="${pageContext.request.contextPath}/js/language.jsp"></script>


</body>

</html>
