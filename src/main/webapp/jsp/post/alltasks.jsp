<%--
  Created by IntelliJ IDEA.
  User: Sasha
  Date: 17.06.2015
  Time: 15:38
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
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <!-- Tokenizer Script -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.tokenize.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery.tokenize.css"/>


    <link href="${pageContext.request.contextPath}/css/toastr.css" rel="stylesheet" type="text/css"/>
    <script src="${pageContext.request.contextPath}/js/toastr.js"></script>
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 -->
    <!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
    <script src="js/respond.min.js"></script>
    <script src="js/lte-ie7.js"></script>
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
                    <ol class="breadcrumb">
                        <li><i class="fa fa-home"></i><a href="index.html">Home</a></li>
                        <li><i class="fa fa-desktop"></i>Task</li>
                        <li><i class="fa fa-list-alt"></i>All Tasks</li>
                    </ol>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading tab-bg-primary ">
                            <ul class="nav nav-tabs">
                                <li class="">
                                    <a data-toggle="tab" href="#active">Active Tasks</a>
                                </li>
                                <li class="active">
                                    <a data-toggle="tab" href="#all">All Tasks</a>
                                </li>

                            </ul>
                        </header>
                        <div class="panel-body">
                            <div class="tab-content">
                                <div id="active" class="tab-pane">
                                    <div class="col-lg-12">
                                        <table class="table table-striped table-advance table-hover">
                                            <tr>
                                                <th>ID</th>
                                                <th>Grabbing</th>
                                                <th>Posting</th>
                                                <th>Account</th>
                                                <th>Type</th>
                                                <th>Content</th>
                                                <th>Interval</th>
                                                <th>On/Off</th>
                                            </tr>
                                            <tr>
                                                <td>456423</td>
                                                <td>testest</td>
                                                <td>testtest</td>
                                                <td>acc1</td>
                                                <td>typetype</td>
                                                <td>text, photo, links</td>
                                                <td>interval</td>
                                                <td>
                                                    <div class="switch switch-square"
                                                         data-on-label="<i class=' icon-ok'></i>"
                                                         data-off-label="<i class='icon-remove'></i>">
                                                        <input type="checkbox" checked="" />
                                                    </div>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                                <div id="all" class="tab-pane active">
                                    <div class="col-lg-12">
                                        <table class="table table-striped table-advance table-hover">
                                            <tr>
                                                <th>ID</th>
                                                <th>Grabbing</th>
                                                <th>Posting</th>
                                                <th>Type</th>
                                                <th>Content</th>
                                                <th>Run Time</th>
                                                <th>Limited</th>
                                                <th>On/Off</th>
                                            </tr>
                                            <c:forEach items="${tasks}" var="task">
                                            <tr>
                                                <td><c:out value="${task.id}"/></td>
                                                <td><c:out value="${task.source}"/></td>
                                                <td><c:out value="${task.destination}"/></td>
                                                <td><c:out value="${task.type}"/></td>
                                                <td><c:out value="${task.contenttype}"/></td>
                                                <td><c:out value="${task.time}"/></td>
                                                <td><c:out value="${task.workLimit}"/></td>
                                                <td>
                                                    <div change="${task.id}" class="task-switch switch switch-square"
                                                         data-on-label="<i class=' icon-ok'></i>"
                                                         data-off-label="<i class='icon-remove'></i>">
                                                        <input type="checkbox" ${task.active!=null?'checked':''} />
                                                    </div>
                                                </td>
                                            </tr>
                                            </c:forEach>
                                        </table>
                                        <script type="text/javascript">

                                            function delayResultPrint(result,type ){
                                                type = typeof type !== 'undefined' ? type : "success";
                                                setTimeout(function () {
                                                    toastrNotification(type, result);
                                                }, 500);
                                            }

                                            $(document).ready(function () {
                                                $(" div.task-switch").change(function () {
                                                    $(this).children(".switch-on").each(function () {
                                                        var row = $(this).parent().parent().parent();
//                                                        $(row).css("background-color", "yellow");
                                                        var id = $(this).parent().attr('change');
                                                        $.post("task?action=stateChange&toState=RUNNING&taskId=".concat(id))
                                                                .done(function (data) {
//                                                                    $(row).css("background-color", "green");
//                                                                    alert(data.alert + id);
                                                                    delayResultPrint(data.alert + " #" + id);
                                                                })
                                                                .fail(function (jqXHR, textStatus, errorThrown) {
//                                                                    $(row).css("background-color", "red");
//                                                                    alert(textStatus + id);
                                                                    delayResultPrint(textStatus + " #" + id, "error");


                                                                });
                                                    });
                                                    $(this).children(".switch-off").each(function () {
                                                        var row = $(this).parent().parent().parent();
//                                                        $(row).css("background-color", "yellow");
                                                        var id = $(this).parent().attr('change');
                                                        $.post("task?action=stateChange&toState=STOPPED&taskId=".concat(id))
                                                                .done(function (data) {
//                                                                    $(row).css("background-color", "green");
//                                                                    alert(data.alert + id);
                                                                    delayResultPrint(data.alert  + " #" + id);
                                                                })
                                                                .fail(function (jqXHR, textStatus, errorThrown) {
//                                                                    $(row).css("background-color", "red");
//                                                                    alert(textStatus + id);
                                                                    delayResultPrint(textStatus + " #" + id, "error");
                                                                });
                                                    });

                                                });
                                            });
                                        </script>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                </div>

            </div>
        </section>

    </section>
    <!--main content end-->
</section>
<!-- container section end -->

<!-- javascripts -->
<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<!-- nice scroll -->
<script src="${pageContext.request.contextPath}/js/jquery.scrollTo.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.nicescroll.js" type="text/javascript"></script>
<!-- gritter -->

<!-- custom gritter script for this page only-->
<script src="${pageContext.request.contextPath}/js/gritter.js" type="text/javascript"></script>
<!--custome script for all page-->
<script src="${pageContext.request.contextPath}/js/scripts.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.tokenize.js"></script>
<!--custom tagsinput-->
<script src="${pageContext.request.contextPath}/js/jquery.tagsinput.js"></script>
<%--<script src="${pageContext.request.contextPath}/js/form-component.js"></script>--%>
<!--custom checkbox & radio-->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ga.js"></script>
<!--custom switch-->
<script src="${pageContext.request.contextPath}/js/bootstrap-switch.js"></script>



</body>
</html>

