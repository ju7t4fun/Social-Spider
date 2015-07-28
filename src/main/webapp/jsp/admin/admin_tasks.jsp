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
    <script src="${pageContext.request.contextPath}/js/jquery.multi-select.js" type="text/javascript"></script>
    <link href="${pageContext.request.contextPath}/css/multi-select.css" media="screen" rel="stylesheet">

    <%--for table--%>
    <link href="http://cdn.datatables.net/1.10.3/css/jquery.dataTables.css" rel="stylesheet" type="text/css">
    <link href="http://datatables.net/release-datatables/extensions/ColVis/css/dataTables.colVis.css" rel="stylesheet"
          type="text/css">
    <script src="http://cdn.datatables.net/1.10.3/js/jquery.dataTables.min.js"></script>
    <script src="http://datatables.net/release-datatables/extensions/ColVis/js/dataTables.colVis.js"></script>
    <script src="http://jquery-datatables-column-filter.googlecode.com/svn/trunk/media/js/jquery.dataTables.columnFilter.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/fnStandingRedraw.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/fnSetFilteringDelay.js"></script>
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">

    <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

    <link href="${pageContext.request.contextPath}/css/toastr.css" rel="stylesheet" type="text/css"/>
    <script src="${pageContext.request.contextPath}/js/toastr.js"></script>

</head>

<body>
<jsp:include page="../pagecontent/header.jsp"/>
<jsp:include page="../pagecontent/sidebar.jsp"/>

<jsp:include page="../pagecontent/confirm-delete.jsp"/>

<section id="container" class="">

    <section id="main-content">
        <section class="wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header"><i class="fa fa-list-alt"></i> <l:resource key="task"/></h3>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <ol class="breadcrumb">
                        <li><i class="fa fa-home"></i><a href="/"><l:resource key="admin"/></a></li>
                        <li><i class="fa fa-desktop"></i><l:resource key="task"/></li>
                    </ol>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">


                        <div class="b-popup" id="popup_bind">
                            <div class="b-popup-content" style="height: 340px; width: 500px; margin-left: 470px;">
                                <h4>
                                    <div style="margin-left: 53px;"><l:resource key="task.categoriesbinding"/></div>
                                </h4>
                                <table style="margin-left: 55px; margin-top: 30px">
                                    <tr>
                                        <td>
                                            <select id="tokenize_category" multiple="multiple">
                                            </select>
                                        </td>
                                    </tr>
                                </table>
                                <br>

                                <div style="float: right">
                                    <a href="javascript:PopUpHide()">
                                        <button class="btn btn-info"><l:resource
                                                key="newpost.save"/></button>
                                    </a>
                                    <a href="javascript:PopUpHideS()">
                                        <button class="btn btn-danger"><l:resource
                                                key="cancel"/></button>
                                    </a>
                                </div>

                            </div>
                        </div>


                        <div class="panel-body">
                            <div id="active" class="tab-pane active">
                                <div class="col-lg-12">
                                    <table id="tasksTable">
                                        <thead>
                                        <tr style="align-content: center">
                                            <th><l:resource key="grabbing"/></th>
                                            <th><l:resource key="binding"/></th>
                                            <th><l:resource key="notification.type"/></th>
                                            <th><l:resource key="newpost.content"/></th>
                                            <th><l:resource key="on.off"/></th>
                                            <th><l:resource key="delete"/></th>
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
            "sAjaxSource": "${pageContext.request.contextPath}/task?action=gettasks&taskType=forAdmin",
            colVis: {
                "align": "right",
                "buttonText": "columns <img src=\"/img/caaret.png\"/>",
            },

            "columnDefs": [{
                "bVisible": false, "aTargets": [2]
            }, {
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
            }, {
                "aTargets": [5], "createdCell": function (td, cellData, rowData, row, col) {
                    $(td).html('<div class="btn-group"><a class="btn btn-danger" onclick="deleteTask(' + cellData + ')"><i class="icon_close_alt2"></i></a></div>');
                }
            }, {
                "aTargets": [1], "createdCell": function (td, cellData, rowData, row, col) {
                    $(td).html('<a class="btn btn-default" onclick="PopUpShow(' + rowData[5] + ')"><span class="fa fa-users"></span></a>');
                }
            }, {
                "aTargets": [3], "createdCell": function (td, cellData, rowData, row, col) {
                    $(td).html(parseAttachment(cellData));
                }
            }, {
                "width": "30%", "targets": [0, 3]
            }, {
                "width": "5px", "targets": [5]
            }, {
                "class": "dt-body-left", "targets": [0]
            }, {
                "class": "dt-body-center", "targets": [1, 4]
            }]
        });
    });

    var taskToSendId;

    function PopUpShow(id) {
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.open('GET', '/task?action=optionFilling&id=' + id, true);
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4) {
                var response = JSON.parse(xmlhttp.responseText);
                setOption(response);
                taskToSendId = id;
                $("#popup_bind").show();
            }
        };
        xmlhttp.send();
    }

    function setOption(response) {
        $('#tokenize_category').empty();
        $('#tokenize_category').multiSelect('refresh');

        for (var i = 0; i < response.categories.length; i++) {
            $('#tokenize_category').multiSelect('addOption', {
                value: '' + response.categories[i].id,
                text: response.categories[i].name, index: 0,
            });
            if (response.categories[i].select == true) {
                $('#tokenize_category').multiSelect('select', '' + response.categories[i].id);
            }
        }
    }

    function PopUpHide() {
        sendSelectedField(taskToSendId);
        $("#popup_bind").hide();
    }

    function PopUpHideS() {
        $("#popup_bind").hide();
    }

    function sendSelectedField(id) {
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.open("POST", "/task?action=bind&id=" + id);
        var selectionCategory = document.getElementById("tokenize_category").options;

        var categories = [];
        for (var i = 0; i < selectionCategory.length; i++) {
            if (selectionCategory[i].selected)
                categories.push(selectionCategory[i].value);
        }

        var request = {
            categories: categories,
        };
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4) {
                var response = JSON.parse(xmlhttp.responseText);
                for (var i = 0; i < response.length; i++) {
                    toastrNotification(response[i].status, response[i].msg);
                }
            }
        };
        xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
        xmlhttp.send(JSON.stringify(request));
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
    dataTables_filter_input.attr("style", "width: 500px");
    //    $(".dataTables_filter").attr("hidden", "");
    //    $(".dataTables_length").attr("hidden", "");

    function deleteTask(id) {
        deleteConfirmTask(id);
    }

    function parseAttachment(arg) {
        var args = arg.split(",");
        var cell = "";
        for (var i = 0; i < args.length; i++) {
            cell = cell + " " + parseDoc(args[i].toUpperCase());
        }
        return cell;
    }

    function parseDoc(arg) {
        switch (arg.trim()) {
            case "PHOTO":
                return '<img src=\"/img/icons/jpg-icon.png" style="width: 30px; height: 30px">';
            case "VIDEO":
                return '<img src=\"/img/icons/mpg-icon.png" style="width: 30px; height: 30px">';
            case "AUDIO":
                return '<img src=\"/img/icons/mp3-icon.png" style="width: 30px; height: 30px">';
            case "TEXT":
                return '<img src=\"/img/icons/txt-icon.png" style="width: 30px; height: 30px">';
            case "DOC" :
                return '<img src=\"/img/icons/doc-icon.png" style="width: 30px; height: 30px">';
            case "HASH TAG" :
                return '<img src=\"/img/icons/hash-icon.png" style="width: 30px; height: 30px">';
            case "LINKS" :
                return '<img src=\"/img/icons/link-icon.png" style="width: 30px; height: 30px">';
            case "PAGES" :
                return '<img src=\"/img/icons/page-icon.png" style="width: 30px; height: 30px">';
            case "REPOST" :
                return '<img src=\"/img/icons/repost-icon.png" style="width: 30px; height: 30px">';
        }
        return "";
    }

</script>

<style>
    .onoffswitch {
        margin-left: 53px;
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
        font-size: 13px;
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