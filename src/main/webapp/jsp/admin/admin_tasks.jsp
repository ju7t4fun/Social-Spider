<%--
  Created by IntelliJ IDEA.
  User: Орест
  Date: 7/8/2015
  Time: 12:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Creative - Bootstrap 3 Responsive Admin Template">
    <meta name="author" content="GeeksLabs">
    <meta name="keyword" content="Creative, Dashboard, Admin, Template, Theme, Bootstrap, Responsive, Retina, Minimal">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/icons/favicon.png">

    <title>Admin | All Users</title>

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
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <!-- nice scroll -->
    <script src="${pageContext.request.contextPath}/js/jquery.scrollTo.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.nicescroll.js" type="text/javascript"></script>
    <!-- gritter -->

    <%--<!-- custom gritter script for this page only-->--%>
    <script src="${pageContext.request.contextPath}/js/gritter.js" type="text/javascript"></script>
    <%--<!--custome script for all page-->--%>
    <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.tokenize.js"></script>

    <%--for table--%>
    <link href="http://cdn.datatables.net/1.10.3/css/jquery.dataTables.css" rel="stylesheet" type="text/css">
    <link href="http://datatables.net/release-datatables/extensions/ColVis/css/dataTables.colVis.css" rel="stylesheet"
          type="text/css">
    <script src="http://cdn.datatables.net/1.10.3/js/jquery.dataTables.min.js"></script>
    <script src="http://datatables.net/release-datatables/extensions/ColVis/js/dataTables.colVis.js"></script>
    <script src="http://jquery-datatables-column-filter.googlecode.com/svn/trunk/media/js/jquery.dataTables.columnFilter.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/fnStandingRedraw.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/fnSetFilteringDelay.js"></script>

    <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>


    <!-- HTML5 shim and Respond.js IE8 support of HTML5 -->
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
    <script src="${pageContext.request.contextPath}/js/respond.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/lte-ie7.js"></script>
    <![endif]-->

    <link href="${pageContext.request.contextPath}/css/toastr.css" rel="stylesheet" type="text/css"/>
    <script src="${pageContext.request.contextPath}/js/toastr.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/custom-datatable.js"></script>




</head>

<body>
<jsp:include page="../pagecontent/header.jsp"/>
<jsp:include page="../pagecontent/sidebar.jsp"/>


<!-- container section start -->
<%--<c:set var="mysrc" value="${pageContext.request.contextPath}/img/deleted.png" />--%>



<section id="container" class="">
    <!--main content start-->
    <!--main content start-->


    <section id="main-content">
        <section class="wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header"><i class="fa fa-list-alt"></i> Task Binding</h3>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <ol class="breadcrumb">
                        <li><i class="fa fa-home"></i><a href="/">Admin</a></li>
                        <li><i class="fa fa-desktop"></i>Task Binding</li>
                    </ol>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div id="active" class="tab-pane active">
                                <div class="col-lg-12">
                                    <table id="tasksTable">
                                        <thead>
                                        <tr style="align-content: center">
                                            <th>Grabbing</th>
                                            <th>Binding</th>
                                            <th>Type</th>
                                            <th>Content</th>
                                            <th>On/Off</th>
                                            <th>Delete</th>
                                        </tr>
                                        </thead>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </section>

</section>



<!-- container section end -->



<script>

    var table;

    jQuery(document).ready(function () {
        table = $('#tasksTable').dataTable({

            "bFilter": false,
            "bLengthChange": false,
            "bSort": false,
            "bPaginate": true,
            "paging": true,
            "bInfo": false,
            "iDisplayStart": 0,
            "bProcessing": true,
            'iDisplayLength': 10,
            "bServerSide": true,
            "sAjaxSource": "http://localhost:8080/task?action=gettasks&taskType=forAdmin",
            colVis: {
                "align": "right",
                "buttonText": "columns <img src=\"/img/caaret.png\"/>",
            },

            "columnDefs": [
                {
                    "aTargets": [4], "createdCell": function (td, cellData, rowData, row, col) {

                    var codeChecked =
                            '<div class="onoffswitch"> <input type="checkbox" name="onoffswitch" class="onoffswitch-checkbox" id="myonoffswitch' + rowData[5] + '" onchange = "change(this,\'' + rowData[5] + '\' )"  checked >' +
                            '<label class="onoffswitch-label" for="myonoffswitch' + rowData[5] + '" > <span class="onoffswitch-inner"></span> <span class="onoffswitch-switch"></span> </label> </div>';

                    var codeNonChecked =
                            '<div class="onoffswitch"> <input type="checkbox" name="onoffswitch" class="onoffswitch-checkbox" id="myonoffswitch' + rowData[5] + '" onchange = "change(this,\'' + rowData[5] + '\' )"   >' +
                            '<label class="onoffswitch-label" for="myonoffswitch' + rowData[5] + '" > <span class="onoffswitch-inner"></span> <span class="onoffswitch-switch"></span> </label> </div>';


                    if (cellData.valueOf() == 'running'.valueOf()) {

                        $(td).html(codeChecked);
                    } else {
                        $(td).html(codeNonChecked);
                    }

                }

                },

                {
                    "aTargets": [5], "createdCell": function (td, cellData, rowData, row, col) {
                    $(td).html('<div class="btn-group"><a class="btn btn-danger" onclick="deleteTask(' + cellData + ')"><i class="icon_close_alt2"></i></a></div>');
                }
                },

                {
                    "aTargets": [1], "createdCell": function (td, cellData, rowData, row, col) {
                    $(td).html('<a class="btn btn-default" onclick="PopUpShow(' + rowData[5] + ')"><span class="fa fa-users"></span></a>');
                }
                },

                {
                    "aTargets": [3], "createdCell": function (td, cellData, rowData, row, col) {
                    $(td).html(parseAttachment(cellData));
                }
                },

                {
                    "width": "30%", "targets": [0, 3]
                },

                {
                    "class": "dt-body-left", "targets": [0]
                },

                {
                    "class": "dt-body-center", "targets": [1,4, 5]
                }

            ]

        });

    });


    function PopUpShow(taskId) {
        alert("Task ID: " + taskId);
    }

    function change(obj, id) {


        var toState = 'STOPPED';
        if (obj.checked == true) {
            toState = 'running';
        }

        var xmlhttp = new XMLHttpRequest();
        xmlhttp.open('GET', '/task?action=stateChange&taskId=' + id + '&toState=' + toState, true);
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4) {
                if (response.msg === 'error') {
                    table.fnStandingRedraw();
                }
            }

        };
        xmlhttp.send();
    }

    var dataTables_filter_input = $(".dataTables_filter").find("input");
    dataTables_filter_input.attr("class", "form-control");
    dataTables_filter_input.attr("style", "width: 500px")
    $(".dataTables_filter").attr("hidden", "");
    $(".dataTables_length").attr("hidden", "");



    function deleteTask(id) {
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.open('GET', '/task?action=deletetask&taskId=' + id, true);
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4) {
                table.fnStandingRedraw();
            }

        };
        xmlhttp.send();
    }

    function parseAttachment(arg) {
        var args = arg.split(",");
        var cell = "";
        for (var i = 0; i < args.length; i++) {
            cell = cell + " " + parseDoc(args[i].toUpperCase());
        }
        return cell;
    };

    function parseDoc(arg) {
        var arg = arg.trim();
        switch (arg) {
            case "PHOTO":
                return '<img src=\"/img/icons/jpg-icon.png" style="width: 30px; height: 30px">';
            case "VIDEO":
                return '<img src=\"/img/icons/mpg-icon.png" style="width: 30px; height: 30px">';
            case "AUDIO":
                return '<img src=\"/img/icons/mp3-icon.png" style="width: 30px; height: 30px">';
            case "TEXT":
                return '<img src=\"/img/icons/txt-icon.png" style="width: 30px; height: 30px">';
            case "DOC" :
                return '<img src=\"/img/restIcos/doc.jpg" style="width: 30px; height: 30px">';
            case "HASH TAG" :
                return '<img src=\"/img/restIcos/hashtag.jpg" style="width: 30px; height: 30px">';
            case "LINKS" :
                return '<img src=\"/img/restIcos/links.jpg" style="width: 30px; height: 30px">';
            case "PAGES" :
                return '<img src=\"/img/restIcos/pages.jpg" style="width: 30px; height: 30px">';
            case "REPOST" :
                return '<img src=\"/img/restIcos/repost.jpg" style="width: 30px; height: 30px">';
        }
        return "";
    };
</script>

<style>
    .onoffswitch {
        position: relative;
        width: 90px;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
    }

    .onoffswitch-checkbox {
        display: none;
    }

    .onoffswitch-label {
        display: block;
        overflow: hidden;
        cursor: pointer;
        border: 2px solid #999999;
        border-radius: 20px;
    }

    .onoffswitch-inner {
        display: block;
        width: 200%;
        margin-left: -100%;
        -moz-transition: margin 0.3s ease-in 0s;
        -webkit-transition: margin 0.3s ease-in 0s;
        -o-transition: margin 0.3s ease-in 0s;
        transition: margin 0.3s ease-in 0s;
    }

    .onoffswitch-inner:before, .onoffswitch-inner:after {
        display: block;
        float: left;
        width: 50%;
        height: 30px;
        padding: 0;
        line-height: 30px;
        font-size: 14px;
        color: white;
        font-family: Trebuchet, Arial, sans-serif;
        font-weight: bold;
        -moz-box-sizing: border-box;
        -webkit-box-sizing: border-box;
        box-sizing: border-box;
    }

    .onoffswitch-inner:before {
        content: "ON";
        padding-left: 10px;
        background-color: #34C171;
        color: #FFFFFF;
    }

    .onoffswitch-inner:after {
        content: "OFF";
        padding-right: 10px;
        background-color: #EEEEEE;
        color: #999999;
        text-align: right;
    }

    .onoffswitch-switch {
        display: block;
        width: 18px;
        margin: 6px;
        background: #FFFFFF;
        border: 2px solid #999999;
        border-radius: 20px;
        position: absolute;
        top: 0;
        bottom: 0;
        right: 56px;
        -moz-transition: all 0.3s ease-in 0s;
        -webkit-transition: all 0.3s ease-in 0s;
        -o-transition: all 0.3s ease-in 0s;
        transition: all 0.3s ease-in 0s;
    }

    .onoffswitch-checkbox:checked + .onoffswitch-label .onoffswitch-inner {
        margin-left: 0;
    }

    .onoffswitch-checkbox:checked + .onoffswitch-label .onoffswitch-switch {
        right: 0px;
    }
</style>

</body>
</html>