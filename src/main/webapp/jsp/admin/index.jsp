<%--
  Created by IntelliJ IDEA.
  User: Sasha
  Date: 17.06.2015
  Time: 15:38
  To change this template use File | Settings | File Templates.
--%>
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

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 -->
    <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
    <script src="${pageContext.request.contextPath}/js/respond.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/lte-ie7.js"></script>



    <%--Статистика--%>
    <script>

        $(document).ready(function () {
            // Handler for .ready() called.
            showGroupStat();
        });


        function showGroupStat() {
            var xmlhttp = new XMLHttpRequest();
            xmlhttp.open('GET', '/admin?action=vkgroupstats', true);
            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState == 4) {
                    var response = JSON.parse(xmlhttp.responseText);
                    if (response.status == 'error')
                        toastrNotification(response.status, response.msg);
                    else {
                        drawChart(response);
                        $('#startDate').attr("max", response.max);
                        $('#startDate').attr("value", response.date_from);
                        $('#endDate').attr("max", response.max);
                        $('#endDate').attr("value", response.date_to);
                    }
                }
            };
            xmlhttp.send();
        }

        function redrawChart() {
            var dateFrom = document.getElementById("startDate").value;
            var dateTo = document.getElementById("endDate").value;
            var xmlhttp = new XMLHttpRequest();
            xmlhttp.open('GET', '/admin?action=vkgroupstats&date_from=' + dateFrom + "&date_to=" + dateTo, true);
            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState == 4) {
                    var response = JSON.parse(xmlhttp.responseText);
                    if (response.status == 'error')
                        toastrNotification(response.status, response.msg);
                    else
                        drawChart(response);
                }
            };
            xmlhttp.send();
        }

        function drawChart(response) {
            new Chart(document.getElementById("line").getContext("2d")).Line(response.line);
            new Chart(document.getElementById("bar").getContext("2d")).Bar(response.bar);
            new Chart(document.getElementById("pie").getContext("2d")).Pie(response.pie);
            $("#country_list").empty();
            for (var i = 0; i < response.pie.length; i++) {
                $("#country_list").append('<li class="list-group-item row"><div style="border-radius: 4px; width: 20px; height: 20px; background: ' + response.pie[i].color + '"><span style="margin-left: 30px">  ' + response.pie[i].name + '</span></div></li>');
            }
            $("#day1").html(response.day);
            $("#day2").html(response.day);
            $("#visitors").html(response.visitors);
            $("#dayVisitors").html(response.dayVisitors);
        }

    </script>

</head>

<body>

<jsp:include page="../pagecontent/header.jsp"/>
<jsp:include page="../pagecontent/sidebar.jsp"/>

<!-- container section start -->
<section id="container" class="">


    <section id="main-content">
        <section class="wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header"><i class="fa fa-table"></i> Admin</h3>
                    <ol class="breadcrumb">
                        <li><i class="fa fa-home"></i><a href="index.html">Home</a></li>
                        <li><i class="fa fa-table"></i>Charts</li>
                    </ol>
                </div>
            </div>

            <!-- page start-->



                <div class="modal-body">
                    <div class="row" >
                        <!-- chart morris start -->

                        <div class="col-lg-12">
                            <section class="panel">
                                <%--<header class="panel-heading" style="position: fixed; left: 300px; top:300px;">--%>
                                <%--<Char>General Chart</Char>--%>
                                <%--</header>--%>


                                <div style="margin-left: 50px">
                                    <label for="startDate" ><l:resource
                                            key="charts.from"/></label>
                                    <l:resource key="charts.startdate"><input id="startDate" name="date" type="date" style="width: 200px"
                                                                              class="form-control"
                                                                              ></l:resource>
                                    <label for="endDate" ><l:resource key="charts.to"/></label>
                                    <l:resource key="charts.enddate"><input id="endDate" name="date" type="date" style="width: 200px"
                                                                            class="form-control" ></l:resource>

                                    <a onclick="redrawChart()"  class="btn btn-default"><l:resource key="charts.update"/></a>
                                </div>

                                <div class="panel-body" style="margin-top: 50px;">
                                    <div class="tab-pane" id="chartjs">
                                        <div class="row">
                                            <!-- Line -->
                                            <div class="col-lg-6" >
                                                <section class="panel">
                                                    <header class="panel-heading" style="margin-left: 50px;">
                                                        Unique visitors and views
                                                    </header>
                                                    <div style="margin-left: 50px; width: 600px">
                                                        <l:resource key="charts.uniquevisitsdaily"/>
                                                        <span id="day1"></span> <l:resource key="charts.days"/>:
                                                        <span id="dayVisitors"></span><br>
                                                        <l:resource key="charts.uniquevisits"/>
                                                        <span id="day2"></span> <l:resource key="charts.days"/>:
                                                        <span id="visitors"></span>
                                                    </div>
                                                    <div class="panel-body text-center"  >
                                                        <canvas id="line" height="500" width="930" ></canvas>
                                                    </div>
                                                </section>
                                            </div>
                                        </div>
                                        <!-- Bar -->
                                        <div class="row">
                                            <div class="col-lg-6" >
                                                <section class="panel">
                                                    <header class="panel-heading" style="margin-left: 50px;">
                                                        <l:resource key="charts.age"/>
                                                    </header>
                                                    <div class="panel-body text-center">
                                                        <canvas id="bar" height="500" width="930"></canvas>
                                                    </div>
                                                </section>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <!-- Pie -->
                                            <div class="col-lg-6" >
                                                <section class="panel">
                                                    <header class="panel-heading">
                                                        <l:resource key="charts.geografy"/>
                                                    </header>
                                                    <div class="panel-body text-center">
                                                        <canvas id="pie" height="400" width="400"></canvas>
                                                    </div>
                                                </section>
                                            </div>
                                            <div class="col-lg-6" style="margin-left: 5px">
                                                <section class="panel" style="margin-top: 31px">
                                                    <ul class="list-group" id="country_list">
                                                    </ul>
                                                </section>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </section>
                        </div>
                    </div>
                </div>


            <!-- page end-->
        </section>
    </section>
</section>
<!-- container section end -->



</body>
</html>

