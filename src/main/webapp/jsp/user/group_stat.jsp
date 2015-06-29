<%--
  Created by IntelliJ IDEA.
  User: maryan
  Date: 29.06.2015
  Time: 18:38
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

    <title>Task | All Tasks</title>

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
    <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
    <script src="${pageContext.request.contextPath}/js/respond.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/lte-ie7.js"></script>
</head>

<body>
<button value="Button" class="btn btn-info" data-toggle="modal"
        data-target="#myModal"></button>

<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="myModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 800px; margin-left: -100px;">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">x</button>
                <h4 class="modal-title">Charts</h4>
            </div>
            <div class="modal-body">
                <div class="row" style="margin-left: 110px;">
                    <!-- chart morris start -->
                    <div class="col-lg-12">
                        <section class="panel">
                            <header class="panel-heading">
                                <Char>General Chart</Char>
                            </header>
                            <div class="panel-body">
                                <div class="tab-pane" id="chartjs">
                                    <div class="row">
                                        <!-- Line -->
                                        <div class="col-lg-6">
                                            <section class="panel">
                                                <header class="panel-heading">
                                                    Line
                                                </header>
                                                <div class="panel-body text-center">
                                                    <canvas id="line" height="300" width="450"></canvas>
                                                </div>
                                            </section>
                                        </div>
                                    </div>
                                    <!-- Bar -->
                                    <div class="row">
                                        <div class="col-lg-6">
                                            <section class="panel">
                                                <header class="panel-heading">
                                                    Bar
                                                </header>
                                                <div class="panel-body text-center">
                                                    <canvas id="bar" height="300" width="500"></canvas>
                                                </div>
                                            </section>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <!-- Pie -->
                                        <div class="col-lg-6">
                                            <section class="panel">
                                                <header class="panel-heading">
                                                    Pie
                                                </header>
                                                <div class="panel-body text-center">
                                                    <canvas id="pie" height="300" width="400"></canvas>
                                                </div>
                                            </section>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </section>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- javascripts -->
<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-1.8.3.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<!-- nice scroll -->
<script src="${pageContext.request.contextPath}/js/jquery.scrollTo.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.nicescroll.js" type="text/javascript"></script>
<!-- chartjs -->
<script src="${pageContext.request.contextPath}/assets/chart-master/Chart.js"></script>
<!-- custom chart script for this page only-->
<script src="${pageContext.request.contextPath}/js/chartjs-custom.js"></script>
<!--custome script for all page-->
<script src="${pageContext.request.contextPath}/js/scripts.js"></script>

</body>
</html>

