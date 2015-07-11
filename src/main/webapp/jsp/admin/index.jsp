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

    <title>Admin | Index</title>

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
    <!--custome script for all page-->
    <script src="${pageContext.request.contextPath}/js/scripts.js"></script>

    <%--for charts--%>
    <%--line diagram lib--%>
    <script src="${pageContext.request.contextPath}/js/highstock.js"></script>
    <script src="${pageContext.request.contextPath}/js/exporting.js"></script>
    <%--gender and pie diagram lib--%>
    <script src="${pageContext.request.contextPath}/js/highcharts.js"></script>
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">

</head>
<script>
    $(document).ready(function () {
        document.body.scrollTop = 132;
    })
</script>
<body>

<jsp:include page="../pagecontent/header.jsp"/>
<jsp:include page="../pagecontent/sidebar.jsp"/>

<!-- container section start -->
<section id="container1" class="">
    <section id="main-content">
        <section class="wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header"><i class="fa fa-pie-chart"></i> Admin</h3>
                    <ol class="breadcrumb">
                        <li><i class="fa fa-home"></i><a href="/">Home</a></li>
                        <li><i class="fa fa-pie-chart"></i>Charts</li>
                    </ol>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-group m-bot20" id="accordion">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h4 class="panel-title">
                                        <a class="accordion-toggle collapsed" data-toggle="collapse"
                                           data-parent="#accordion" href="#collapseOne">
                                            Item #1 Visits Diagrams
                                        </a>
                                    </h4>
                                </div>
                                <div id="collapseOne" class="panel-collapse in" style="height: auto;">
                                    <div class="panel-body">
                                        <div class="row" style="margin-left: 270px">
                                            <div class="col-lg-3">
                                                <input id="fromDate" class="form-control" type="date">
                                            </div>
                                            <div class="col-lg-3">
                                                <input id="toDate" class="form-control" type="date">
                                            </div>
                                            <input class="btn btn-default" type="button" onclick="redrawChart()"
                                                   value="Show">
                                        </div>
                                        <ul class="nav nav-tabs" role="tablist">
                                            <li role="presentation" class="active">
                                                <a href="#home" aria-controls="home" role="tab" data-toggle="tab">Visitors</a>
                                            </li>
                                            <li role="presentation">
                                                <a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">Sex
                                                    / Age</a>
                                            </li>
                                            <li role="presentation">
                                                <a href="#messages" aria-controls="messages" role="tab"
                                                   data-toggle="tab">Geo</a>
                                            </li>
                                        </ul>

                                        <div class="tab-content">
                                            <div role="tabpanel" class="tab-pane active" id="home"
                                                 style="height: 450px">
                                                <div class="modal-body">
                                                    <div class="row">
                                                        <div id="line-diagram"
                                                             style="height: 450px; min-width: 1100px"></div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div role="tabpanel" class="tab-pane" id="profile" style="height: 490px">
                                                <div class="modal-body">
                                                    <div class="row">
                                                        <div id="gender-diagram" style="min-width: 700px; max-width:
                                                        700px; height: 450px; margin: auto 230px"></div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div role="tabpanel" class="tab-pane" id="messages" style="height: 490px">
                                                <div class="modal-body">
                                                    <div class="row">
                                                        <div id="pie-diagram"
                                                             style="min-width: 480px; height: 400px; max-width: 480px;"></div>
                                                    </div>
                                                </div>
                                                <div class="modal-body">
                                                    <div class="row">
                                                        <div id="city-diagram"
                                                             style="min-width: 480px; height: 400px;
                                                             max-width: 480px; position: relative; left: 480px;  top: -426px"></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h4 class="panel-title">
                                        <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion"
                                           href="#collapseTwo">
                                            Item #2 Activity Diagrams
                                        </a>
                                    </h4>
                                </div>
                                <div id="collapseTwo" class="panel-collapse collapse" style="height: 0;">
                                    <div class="panel-body">
                                        <div class="row">
                                            <div class="col-lg-3">
                                                <input id="date" class="form-control" type="date"
                                                       onchange="redrawChartStats()">
                                            </div>
                                        </div>
                                        <br>
                                        <ul class="nav nav-tabs" role="tablist">
                                            <li role="presentation" class="active">
                                                <a href="#task" aria-controls="task" role="tab"
                                                   data-toggle="tab">Task</a></li>
                                            <li role="presentation">
                                                <a href="#post" aria-controls="post" role="tab"
                                                   data-toggle="tab">Post</a>
                                            </li>
                                            <li role="presentation">
                                                <a href="#error" aria-controls="error" role="tab" data-toggle="tab">Error</a>
                                            </li>
                                        </ul>

                                        <div class="tab-content">
                                            <div role="tabpanel" class="tab-pane active" id="task"
                                                 style="height: 450px">
                                                <div class="modal-body">
                                                    <div class="row">
                                                        <div id="task-line-diagram"
                                                             style="height: 450px; min-width: 1100px"></div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div role="tabpanel" class="tab-pane" id="post" style="height: 490px">
                                                <div class="modal-body">
                                                    <div class="row">
                                                        <div id="post-line-diagram"
                                                             style="height: 450px; min-width: 1100px"></div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div role="tabpanel" class="tab-pane" id="error" style="height: 490px">
                                                <div class="modal-body">
                                                    <div class="row">
                                                        <div id="error-line-diagram"
                                                             style="height: 450px; min-width: 1100px"></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </section>
</section>

<%--Побудова графіків постингу, грабінгу та помилок--%>
<script>
    function drawTaskDiagram(data) {
        $('#task-line-diagram').highcharts('StockChart', {
            rangeSelector: {
                inputEnabled: false,
                enabled: false
            },

            chart: {
                alignTicks: false
            },

            series: [{
                type: 'column',
                name: 'Task',
                data: data
            }]
        });
    }

    function drawPostDiagram(data) {
        $('#post-line-diagram').highcharts('StockChart', {
            rangeSelector: {
                inputEnabled: false,
                enabled: false
            },

            chart: {
                alignTicks: false
            },

            series: [{
                type: 'column',
                name: 'Task',
                data: data
            }]
        });
    }

    function drawErrorDiagram(data) {
        $('#error-line-diagram').highcharts('StockChart', {
            rangeSelector: {
                inputEnabled: false,
                enabled: false
            },

            chart: {
                alignTicks: false
            },

            series: [{
                type: 'column',
                name: 'Error',
                color: '#FF0000',
                data: data
            }]
        });
    }

    function redrawChartStats() {
        var date = document.getElementById("date").value;
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.open('GET', '/admin?action=statsService&date=' + date, true);
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4) {
                var response = JSON.parse(xmlhttp.responseText);
                drawTaskDiagram(response.task);
                drawPostDiagram(response.posted);
                drawErrorDiagram(response.errors);
                $("#date").val(response.date);
            }
        };
        xmlhttp.send();
    }

    $(document).ready(function () {
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.open('GET', '/admin?action=statsService', true);
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4) {
                var response = JSON.parse(xmlhttp.responseText);
                drawTaskDiagram(response.task);
                drawPostDiagram(response.posted);
                drawErrorDiagram(response.errors);
                $("#date").val(response.date);
            }
        };
        xmlhttp.send();
    })
</script>
<%--Опрацювання діаграм відвідування--%>
<script>

    function drawLineDiagram(data) {
        $('#line-diagram').highcharts('StockChart', {
            rangeSelector: {
                inputEnabled: false,
                buttons: [{
                    type: 'week',
                    count: 1,
                    text: 'week'
                }, {
                    type: 'month',
                    count: 1,
                    text: '1m'
                }, {
                    type: 'month',
                    count: 3,
                    text: '3m'
                }, {
                    type: 'month',
                    count: 6,
                    text: '6m'
                }, {
                    type: 'year',
                    count: 1,
                    text: '1y'
                }, {
                    type: 'all',
                    text: 'All'
                }],
                selected: 1
            },

            series: [{
                name: 'Views',
                data: data.views
            }, {
                name: 'Visitors',
                data: data.visitors
            }]
        });
    }

    function drawGenderDiagram(data) {
        var categories = ['12-18', '18-21', '21-24', '24-27',
            '27-30', '30-35', '35-45', ' 45-100'];
        $('#gender-diagram').highcharts({
            chart: {
                type: 'bar'
            },

            title: {
                text: 'Gender'
            },

            xAxis: [{
                categories: categories,
                reversed: false,
                labels: {
                    step: 1
                }
            }, { // mirror axis on right side
                opposite: true,
                reversed: false,
                categories: categories,
                linkedTo: 0,
                labels: {
                    step: 1
                }
            }],
            yAxis: {
                title: {
                    text: null
                },
                labels: {
                    formatter: function () {
                        return Math.abs(this.value) + '%';
                    }
                }
            },

            plotOptions: {
                series: {
                    stacking: 'normal'
                }
            },

            series: [{
                name: 'Male',
                data: data.male
            }, {
                name: 'Female',
                data: data.female
            }]
        });
    }

    function drawCountryDiagram(data) {
        $('#pie-diagram').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
                type: 'pie'
            },
            title: {
                text: 'Country'
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: false
                    },
                    showInLegend: true
                }
            },
            series: [{
                name: "Brands",
                colorByPoint: true,
                data: data
            }]
        });
    }

    function drawCityDiagram(data) {
        $('#city-diagram').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
                type: 'pie'
            },
            title: {
                text: 'City'
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: false
                    },
                    showInLegend: true
                }
            },
            series: [{
                name: "Brands",
                colorByPoint: true,
                data: data
            }]
        });
    }

    $(document).ready(function () {
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.open('GET', '/admin?action=stats', true);
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4) {
                var response = JSON.parse(xmlhttp.responseText);
                if (response.status == 'error')
                    toastrNotification(response.status, response.msg);
                else {
                    drawChart(response);
                    $('#fromDate').attr("max", response.max);
                    $('#fromDate').attr("value", response.date_from);
                    $('#toDate').attr("max", response.max);
                    $('#toDate').attr("value", response.date_to);
                }
            }
        };
        xmlhttp.send();
    });

    function redrawChart() {
        var dateFrom = document.getElementById("fromDate").value;
        var dateTo = document.getElementById("toDate").value;
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.open('GET', '/owner?action=stat&id=' + ownerId + '&date_from=' + dateFrom + "&date_to=" + dateTo, true);
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
        drawLineDiagram(response.line);
        drawGenderDiagram(response.bar);
        drawCountryDiagram(response.country);
        drawCityDiagram(response.city);
    }

</script>

</body>
</html>

