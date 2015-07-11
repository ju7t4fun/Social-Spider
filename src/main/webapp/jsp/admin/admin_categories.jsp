<%--
  Created by IntelliJ IDEA.
  User: maryan
  Date: 29.06.2015
  Time: 17:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
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

    <title>Admin | Categories</title>

    <%--Додав--%>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
    <link href="${pageContext.request.contextPath}/css/bootstrap.3.3.4.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/fileinput.css" media="all" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/fileinput.min.js" type="text/javascript"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js" type="text/javascript"></script>
    <link href="${pageContext.request.contextPath}/css/elegant-icons-style.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/owl.carousel.css" type="text/css">
    <link href="${pageContext.request.contextPath}/css/jquery-jvectormap-1.2.2.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/fileinput.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style-responsive.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/jquery-ui-1.10.4.min.css" rel="stylesheet">

    <%----%>
    <%--<!-- Bootstrap CSS -->--%>
    <%--<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">--%>
    <%--<!-- bootstrap theme -->--%>
    <%--<link href="${pageContext.request.contextPath}/css/bootstrap-theme.css" rel="stylesheet">--%>
    <%--<!--external css-->--%>
    <%--<!-- font icon -->--%>
    <%--<link href="${pageContext.request.contextPath}/css/elegant-icons-style.css" rel="stylesheet"/>--%>
    <%--<link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet"/>--%>
    <%--<!-- Custom styles -->--%>
    <%--<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">--%>
    <%--<link href="${pageContext.request.contextPath}/css/style-responsive.css" rel="stylesheet"/>--%>

    <%--<!-- javascripts -->--%>
    <%--<script src="${pageContext.request.contextPath}/js/jquery.js"></script>--%>
    <%--<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>--%>
    <%--<!-- nice scroll -->--%>
    <%--<script src="${pageContext.request.contextPath}/js/jquery.scrollTo.min.js"></script>--%>
    <%--<script src="${pageContext.request.contextPath}/js/jquery.nicescroll.js" type="text/javascript"></script>--%>
    <%--<!-- gritter -->--%>

    <%--&lt;%&ndash;<!-- custom gritter script for this page only-->&ndash;%&gt;--%>
    <%--<script src="${pageContext.request.contextPath}/js/gritter.js" type="text/javascript"></script>--%>
    <%--&lt;%&ndash;<!--custome script for all page-->&ndash;%&gt;--%>
    <%--<script src="${pageContext.request.contextPath}/js/scripts.js"></script>--%>
    <%--&lt;%&ndash;<script src="${pageContext.request.contextPath}/js/jquery.tokenize.js"></script>&ndash;%&gt;--%>

    <%--<link href="${pageContext.request.contextPath}/css/fileinput.css" media="all" rel="stylesheet"--%>
    <%--type="text/css"/>--%>
    <%--<script src="${pageContext.request.contextPath}/js/fileinput.min.js" type="text/javascript"></script>--%>

    <%--Таблиця--%>
    <link href="http://cdn.datatables.net/1.10.3/css/jquery.dataTables.css" rel="stylesheet" type="text/css">
    <link href="http://datatables.net/release-datatables/extensions/ColVis/css/dataTables.colVis.css" rel="stylesheet"
          type="text/css">
    <script src="http://cdn.datatables.net/1.10.3/js/jquery.dataTables.min.js"></script>
    <script src="http://datatables.net/release-datatables/extensions/ColVis/js/dataTables.colVis.js"></script>
    <script src="http://jquery-datatables-column-filter.googlecode.com/svn/trunk/media/js/jquery.dataTables.columnFilter.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/fnStandingRedraw.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/fnSetFilteringDelay.js"></script>
    <%--Таблиця--%>

    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">


    <link href="${pageContext.request.contextPath}/css/toastr.css" rel="stylesheet" type="text/css"/>
    <script src="${pageContext.request.contextPath}/js/toastr.js"></script>

    <%--for confirm delete modal window(include script and css)--%>
    <jsp:include page="../pagecontent/confirm-delete.jsp"/>

    <script type="text/javascript">

        var taskToSendId;
        var table;

        jQuery(document).ready(function () {

            table = $('#categoryTable').dataTable({
                language: {
                    url:   "/controller?action=getLangJSON"
                },
                "initComplete": function () {
//                    $(".dataTables_length").attr("hidden", "");
                    var dataTables_filter_input = $(".dataTables_filter").find("input");
                    dataTables_filter_input.attr("class", "form-control");
                    dataTables_filter_input.attr("style", "width: 500px")

                },
                "bSort": true,
                aaSorting: [],
                "bPaginate": true,
                "paging": true,
                "bInfo": false,
                "iDisplayStart": 0,
                "bProcessing": true,
                'iDisplayLength': 10,
                "bServerSide": true,
                "sAjaxSource": "http://localhost:8080/admin/categories?action=getcategory",
                colVis: {
                    "align": "right",
                    "buttonText": "columns <img src=\"/img/caaret.png\"/>",
                },

                "sDom": '<"top"<"toolbar">f>t<"bottom"lp><"clear">',

                "columnDefs": [{
                    "targets": [0, 1, 2], "orderable": false
                }, {
                    "aTargets": [1], "createdCell": function (td, cellData, rowData, row, col) {

                        var parts = cellData.split("|");

                        $(td).html('<a  href="#" title="" >' + parts[0] + '</a>')
                                .tooltip(
                                {content: '<img src="' + parts[1] + '" width="300" height="200" width="300" />'},
                                {tooltipClass: "i1"});
                    }
                }, {
                    "aTargets": [2], "createdCell": function (td, cellData, rowData, row, col) {
                        $(td).html('<div class="btn-group"><a class="btn btn-success" data-toggle="modal" data-target="#modal_categoryEdit" onclick="setId(' + rowData[0] + ', \'' + cellData + '\'' + ')"><i class="icon_pencil-edit"></i></a><a class="btn btn-danger" onclick="removeCategory(' + rowData[0] + ')"><i class="icon_close_alt2"></i></a></div>');
                    }
                }]
            });


        });

        function setId(id, name) {
            document.getElementById('catIdEdit').value = id;
            document.getElementById('catNameEdit').value = name;
        }

        // Видалення
        function removeCategory(id) {
            deleteConfirmCategory(id);
        }

    </script>

</head>

<jsp:include page="../pagecontent/header.jsp"/>
<jsp:include page="../pagecontent/sidebar.jsp"/>

<section id="container" class="">

    <section id="main-content">
        <section class="wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header"><i class="fa fa-list-alt"></i> <l:resource key="category"/></h3>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <ol class="breadcrumb">
                        <li><i class="fa fa-home"></i><a href="/">Admin</a></li>
                        <li><i class="fa fa-edit"></i>Edit</li>
                        <li><i class="fa fa-list-alt"></i>Categories</li>
                    </ol>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div id="active" class="tab-pane active">
                                <div class="col-lg-12">

                                    <table width="100%" border="0" class="row-border tableHeader" id="categoryTable">
                                        <thead>
                                        <tr style="align-content: center">
                                            <th>id</th>
                                            <th>Category name</th>
                                            <th>Delete</th>
                                        </tr>
                                        </thead>
                                    </table>
                                    <a class="btn btn-primary" data-toggle="modal"
                                       data-target="#modal_category" href="#">Add category</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </section>
</section>

<%--Вікно додавання--%>
<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="modal_category"
     class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content" style="height: auto; width: 820px">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">x</button>
                <h4 class="modal-title">Add category</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <form id="modal_form" class="form-horizontal">
                            <input type="text" name="category" class="form-control" id="catName" style="width:770px; margin-left: 15px"
                                   placeholder="Category name">

                            <div id="compForm" class="container kv-main" style="width:800px;  margin-top:20px;">
                                <input id="input-dim-2" type="file" multiple="true" method="post"
                                       enctype="multipart/form-data" value="" accept="image/*">
                                <script>
                                    $("#input-dim-2").fileinput({
                                        uploadUrl: "/admin/categories?action=upCat",
                                        allowedFileExtensions: ['jpg', 'gif', 'png', 'jpeg'],
                                        maxFileCount: 1
                                    });
                                </script>
                            </div>
                        </form>
                        <div style="float: right; margin-top: 10px">
                            <a id="add_category_btn" class="btn btn-primary">Add</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%--Вікно редагування--%>
<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="modal_categoryEdit"
     class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content" style="height: auto; width: 820px">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">x</button>
                <h4 class="modal-title">Edit category</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <form id="modal_formEdit" class="form-horizontal">
                            <div hidden>
                                <input type="text" name="categoryId" class="form-control" id="catIdEdit" readonly
                                       placeholder="Category name" >
                            </div>
                            <div>
                                <input type="text" name="category" class="form-control" id="catNameEdit"
                                       placeholder="Category name" style="width:770px; margin-left: 15px">
                            </div>

                            <div id="compFormEdit" class="container kv-main" style="width:800px;  margin-top:20px;">
                                <input id="input-dim-2Edit" type="file" multiple="true" method="post"
                                       enctype="multipart/form-data" value="" accept="image/*">
                                <script>
                                    $("#input-dim-2Edit").fileinput({
                                        uploadUrl: "/admin/categories?action=upCat",
                                        allowedFileExtensions: ['jpg', 'gif', 'png', 'jpeg'],
                                        maxFileCount: 1
                                    });
                                </script>
                            </div>

                            <div style="float: right; margin-top: 10px">
                                <a id="edit_category_btn" class="btn btn-primary">Confirm</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<style>
    .i1 {
        position: fixed;
        background: red;
        font-size: 12px;
        height: 250px;
        width: 350px;
        padding: 20px;
        color: #fff;
        z-index: 99;
        border: 2px solid white;

    }
</style>

<script>
    // Локалізація

    $(".btn").click(function () {
        var lang = $(this).attr("change");
        var names = [];
        i = 0;
        $(".loc-t, .loc-p").each(function () {
            names[i++] = $(this).attr("locres")
        });

        var myJsonString = JSON.stringify(names);
        $.post("/controller?action=locale&lang=".concat(lang), {names: myJsonString})
                .done(function (data) {
                    var map = data;
                    $(".loc-t").each(function () {
                        $(this).text(map[$(this).attr("locres")]);
                    });
                    $(".loc-p").each(function () {
                        $(this).attr("placeholder", map[$(this).attr("locres")]);
                    })

                    table.fnStandingRedraw();
                });
    })
</script>
<script>
    // Додавання категорії
    $("#add_category_btn").click(function () {
        var name = $("#catName").val();
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.open("GET", "http://localhost:8080/admin/categories?action=addcategory&category=" + encodeURIComponent(name), true);
        xmlhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded; charset=UTF-8');
        xmlhttp.send();
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4) {
                var response = JSON.parse(xmlhttp.responseText);
                toastrNotification(response.status, response.msg);
                if (response.status === 'success') {
                    $("#modal_category").modal('toggle');
                }
                table.fnStandingRedraw();
            }
        }
    });

    // Редагування
    $("#edit_category_btn").click(function () {
        var name = $("#catNameEdit").val();
        var id = $("#catIdEdit").val();
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.open("GET", "http://localhost:8080/admin/categories?action=editCat&category=" + encodeURIComponent(name) + "&catIdEdit=" + id, true);
        xmlhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded; charset=UTF-8');
        xmlhttp.send();
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4) {
                var response = JSON.parse(xmlhttp.responseText);
                toastrNotification(response.status, response.msg);
                if (response.status === 'success') {
                    $("#modal_categoryEdit").modal('toggle');
                }
                table.fnStandingRedraw();
            }
        }
    });

</script>
<%--<script src="${pageContext.request.contextPath}/js/jquery.js"></script>--%>
<script src="${pageContext.request.contextPath}/js/jquery-ui-1.10.4.min.js"></script>
<%--<script src="${pageContext.request.contextPath}/js/jquery-1.8.3.min.js"></script>--%>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/js/jquery-ui-1.9.2.custom.min.js"></script>
<!-- bootstrap -->
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<!-- nice scroll -->
<script src="${pageContext.request.contextPath}/js/jquery.scrollTo.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.nicescroll.js"
<%--type="text/javascript"></script>--%>
<!--script for this page only-->
<script src="${pageContext.request.contextPath}/js/calendar-custom.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.rateit.min.js"></script>
<!-- custom select -->
<script src="${pageContext.request.contextPath}/js/jquery.customSelect.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/chart-master/Chart.js"></script>
<script src="${pageContext.request.contextPath}/js/gritter.js" type="text/javascript"></script>


<!--custome script for all page-->
<script src="${pageContext.request.contextPath}/js/scripts.js"></script>
<!-- custom script for this page-->
<script src="${pageContext.request.contextPath}/js/jquery.autosize.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.placeholder.min.js"></script>
<script src="${pageContext.request.contextPath}/js/gdp-data.js"></script>
<script src="${pageContext.request.contextPath}/js/morris.min.js"></script>
<script src="${pageContext.request.contextPath}/js/sparklines.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.slimscroll.min.js"></script>
<script src="${pageContext.request.contextPath}/js/fileinput.min.js"
        type="text/javascript"></script>
<!--custom tagsinput-->
<script src="${pageContext.request.contextPath}/js/jquery.tagsinput.js"></script>
<script src="${pageContext.request.contextPath}/js/form-component.js"></script>

<%--for tooltip--%>
<script src="${pageContext.request.contextPath}/js/jquery-ui-1.9.2.custom.min.js"></script>

</body>
</html>

