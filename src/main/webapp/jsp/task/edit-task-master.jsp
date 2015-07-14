<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
<%--
  Created by IntelliJ IDEA.
  User: Sasha
  Date: 16.06.2015
  Time: 15:15
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

    <title>Task | Edit Task</title>

    <!-- Bootstrap CSS -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <!-- bootstrap theme -->
    <link href="${pageContext.request.contextPath}/css/bootstrap-theme.css" rel="stylesheet">
    <!--external css-->
    <!-- font icon -->
    <link href="${pageContext.request.contextPath}/css/elegant-icons-style.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet"/>
    <!-- Tokenize styles -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery.tokenize.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/just4fun.fix.css"/>
    <!-- Range Slider styles -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/ionRangeSlider/css/normalize.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/ionRangeSlider/css/ion.rangeSlider.css"/>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/ionRangeSlider/css/ion.rangeSlider.skinHTML5.css"/>
    <!-- radios-for-buttons -->
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
    <!-- Custom styles -->
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style-responsive.css" rel="stylesheet"/>
    <%--<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>--%>

    <!-- javascripts -->
    <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.tokenize.task.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/save.task.js"></script>
    <script src="${pageContext.request.contextPath}/assets/ionRangeSlider/js/ion-rangeSlider/ion.rangeSlider.js"></script>
    <script src="${pageContext.request.contextPath}/js/j4f-number-cases.js"></script>


    <!-- HTML5 shim and Respond.js IE8 support of HTML5 -->
    <!--[if lt IE 9]>
  <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
  <!--<script src="${pageContext.request.contextPath}/js/respond.min.js"></script>-->
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
                    <ol class="breadcrumb">
                        <li><i class="fa fa-home"></i><a href="index.html"><l:resource key="home"/></a></li>
                        <li><i class="fa fa-desktop"></i><l:resource key="task"/></li>
                        <li><i class="fa fa-list-alt"></i><span id="task-id-loc">
                        <c:choose>
                            <c:when test="${task_id != '0'}">
                                Task #${task_id}
                            </c:when>
                            <c:otherwise>
                                <l:resource key="addnewtask"></l:resource>
                            </c:otherwise>
                        </c:choose></span></li>
                    </ol>
                </div>
            </div>
            <input type="hidden" name="task_id" value="${task_id}">
            <div class="row">
                <div class="col-lg-6">
                    <!--collapse start-->
                    <div class="panel-group m-bot20" id="accordion" style="width:1100px;">
                        <div class="panel panel-default j4f-fix">
                            <div class="panel-heading">
                                <h4 class="panel-title j4f-fix-title-background">
                                    <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion"
                                       href="#collapseOne">
                                        <l:resource key="addtask.step1"/>
                                    </a>
                                </h4>
                            </div>
                            <div id="collapseOne" class="panel-collapse collapse in">
                                <div class="panel-body">
                                    <div class="col-lg-6">
                                        <jsp:include page="../task/edit-task-source-group.jsp"/>
                                        <jsp:include page="../task/edit-task-destination-group.jsp"/>
                                        <jsp:include page="../task/edit-task-grabbing-mode-group.jsp"/>
                                    </div>
                                    <div class="col-lg-6">
                                        <jsp:include page="../task/edit-task-grabbing-type-group.jsp"/>
                                        <jsp:include page="../task/edit-task-filter-group.jsp"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title j4f-fix-title-background">
                                    <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion"
                                       href="#collapseTwo">
                                        <l:resource key="addtask.step2"/>
                                    </a>
                                </h4>
                            </div>
                            <div id="collapseTwo" class="panel-collapse collapse">
                                <div class="panel-body">
                                    <div class="col-lg-6">
                                        <jsp:include page="../task/edit-task-posting-type-group.jsp"/>
                                        <jsp:include page="../task/edit-task-repeat-group.jsp"/>
                                    </div>
                                    <div class="col-lg-6">
                                        <jsp:include page="../task/edit-task-post-delay-group.jsp"/>
                                    </div>
                                    <div class="col-lg-6">
                                        <jsp:include page="../task/edit-task-start-time-group.jsp"/>
                                        <jsp:include page="../task/edit-task-work-time-group.jsp"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title j4f-fix-title-background">
                                    <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion"
                                       href="#collapseThree">
                                        <l:resource key="addtask.step3"/>
                                    </a>
                                </h4>
                            </div>
                            <div id="collapseThree" class="panel-collapse collapse">
                                <div class="panel-body">
                                    <div class="col-lg-6">
                                        <jsp:include page="../task/edit-task-content-type-group.jsp"/>
                                    </div>
                                    <div class="col-lg-6">

                                        <jsp:include page="../task/edit-task-tag-group.jsp"/>
                                        <jsp:include page="../task/edit-task-text-group.jsp"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <jsp:include page="../task/edit-task-panel-4.jsp"/>

                    </div>
                    <!--collapse end-->
                    <div class="col-lg-offset-2 col-lg-9">
                        <button id="task-save" type="submit" class="btn btn-primary"><l:resource
                                key="newpost.save"/></button>
                        <button type="reset" class="btn btn-default"><l:resource key="newpost.reset"/></button>
                    </div>
                </div>

            </div>
            </div>
        </section>
        <!--main content end-->
    </section>
    <!-- container section end -->
    <script src="${pageContext.request.contextPath}/js/bootstrap-number-input.js"></script>

    <script type="text/javascript">
        j4fBundlePut('x', "${bundle.x}")
        $(document).ready(function () {
            $(".show-when-jquery-unsupported").hide();
            $(".show-when-jquery-supported").show();
        });
    </script>
    <!-- javascripts -->
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <!-- nice scroll -->
    <script src="${pageContext.request.contextPath}/js/jquery.scrollTo.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.nicescroll.js" type="text/javascript"></script>
    <!-- gritter -->

    <!-- custom gritter script for this page only-->
    <script src="${pageContext.request.contextPath}/js/gritter.js" type="text/javascript"></script>
    <!--custome script for all page-->
    <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.tokenize.task.js"></script>
    <!--custom tagsinput-->
    <script src="${pageContext.request.contextPath}/js/jquery.tagsinput.js"></script>
    <script src="${pageContext.request.contextPath}/js/form-component.js"></script>
    //switch unjquery mode
</body>
</html>

