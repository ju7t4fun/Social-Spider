<%--
  Created by IntelliJ IDEA.
  User: Sasha
  Date: 13.06.2015
  Time: 12:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

    <title>Accounts</title>


    <!-- Bootstrap CSS -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <!-- bootstrap theme -->
    <link href="${pageContext.request.contextPath}/css/bootstrap-theme.css" rel="stylesheet">
    <!--external css-->
    <link href="${pageContext.request.contextPath}/css/core.css" rel="stylesheet">

    <!-- font icon -->
    <link href="${pageContext.request.contextPath}/css/elegant-icons-style.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet"/>
    <!-- Custom styles -->
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style-responsive.css" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery.tokenize.css"/>


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


    <!-- HTML5 shim and Respond.js IE8 support of HTML5 -->
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
    <script src="${pageContext.request.contextPath}/js/respond.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/lte-ie7.js"></script>
    <![endif]-->

    <link href="${pageContext.request.contextPath}/css/toastr.css" rel="stylesheet" type="text/css"/>
    <script src="${pageContext.request.contextPath}/js/toastr.js"></script>
    <!--custom tagsinput-->
    <script src="${pageContext.request.contextPath}/js/jquery.tagsinput.js"></script>

    <script type="text/javascript">

        // При завантаженні сторінки
        setTimeout(function () {
            if (${toastr_notification!=null}) {
                var args = "${toastr_notification}".split("|");
                toastrNotification(args[0], args[1]);
            }
        }, 500);

    </script>

    <script type="text/javascript">
        function removeAccount(id) {
            deleteConfirmProfile(id);
        }
    </script>

    <%--Наповнення таблиці--%>
    <script type="text/javascript">
        var table;

        jQuery(document).ready(function () {
            table = $('#accountsTable').dataTable({

                "bSort": false,
                "bPaginate": true,
                "paging": true,
                "bInfo": false,
                "iDisplayStart": 0,
                "bProcessing": true,
                'iDisplayLength': 10,
                "bServerSide": true,
                "sAjaxSource": "http://localhost:8080/accounts?action=get",
                colVis: {
                    "align": "right",
                    "buttonText": "columns <img src=\"/img/caaret.png\"/>",
                },

                "columnDefs": [{
                    "aTargets": [2], "createdCell": function (td, cellData, rowData, row, col) {
                        $(td).html('<a target="_blank" href="' + cellData + '">' + cellData + '</a>');
                    }
                }, {
                    "aTargets": [3], "createdCell": function (td, cellData, rowData, row, col) {
                        var button = '<div class="btn-group">';
                        if (rowData[4])
                            button = button + '<a class="btn btn-success';
                        else
                            button = button + '<a class="btn btn-warning';
                        button = button + '" href="/accounts?action=refresh"><i class="icon_refresh"></i></a>' +
                                '<a class="btn btn-danger" href="javascript:removeAccount(' + cellData + ')"><i class="icon_close_alt2"></i></a></div>'
                        $(td).html(button);
                    }
                }, {
                    "bVisible": false, "aTargets": [4]
                }]

            });

            $(".dataTables_filter").attr("hidden", "");
            $(".dataTables_length").attr("hidden", "");
            var dataTables_filter_input = $(".dataTables_filter").find("input");
            dataTables_filter_input.attr("class", "form-control");
            dataTables_filter_input.attr("style", "width: 500px")

        })
    </script>

</head>

<body>
<section id="container" class="">

    <jsp:include page="../pagecontent/header.jsp"/>
    <jsp:include page="../pagecontent/sidebar.jsp"/>

    <jsp:include page="../pagecontent/confirm-delete.jsp"/>

    <section id="main-content">
        <section class="wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header"><i class="fa fa-list"></i><l:resource key="vkaccounts"/></h3>
                    <ol class="breadcrumb">
                        <li><i class="fa fa-home"></i><a href="/"><l:resource key="home"/></a></li>
                        <li><i class="fa fa-th-list"></i><l:resource key="vkaccounts"/></li>
                    </ol>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <div class="pull-left"><l:resource key="vkaccounts"/></div>
                            <div class="clearfix"></div>
                        </div>

                        <div class="panel-body">
                            <div id="active" class="tab-pane active">
                                <div class="col-lg-12">
                                    <table width="100%" border="0" margin="0" padding="0"
                                           class="row-border tableHeader" id="accountsTable">
                                        <thead>
                                        <tr>
                                            <th><i class="icon_id-2_alt"></i> <l:resource key="vkaccounts.id"/></th>
                                            <th><i class="icon_profile"></i><l:resource key="vkaccounts.fullname"/></th>
                                            <th><i class="icon_link_alt"></i> <l:resource key="vkaccounts.url"/></th>
                                            <th><i class="icon_cogs"></i> <l:resource key="vkaccounts.action"/></th>
                                        </tr>
                                        </thead>
                                    </table>
                                    <div class="btn-group">
                                        <a href="/accounts?action=add" class="btn btn-primary"><i
                                                class="fa fa-vk"></i> <l:resource key="vkaccounts.add"/></a>
                                        <a class="btn btn-primary dropdown-toggle" data-toggle="dropdown" href=""><span
                                                class="caret"></span></a>
                                        <ul class="dropdown-menu">
                                            <li><a href="javascript:openNoExtension()"><l:resource key="no.extension"/></a></li>
                                        </ul>
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
<div class="b-popup" id="add_no_extension">
    <div class="b-popup-content" style="height: 380px;margin-top: 150px;">
        <h4><l:resource key="add.account.manually"/></h4>

        <div class="col-lg-12">
            <p> <l:resource key="access.text1"/> </p>
            <a href="https://oauth.vk.com/authorize?client_id=4949213&scope=wall,groups,photos,audio,video,docs,stats&redirect_uri=https://oauth.vk.com/blank.html&display=page&v=5.27&response_type=token&revoke=1"
               target="_blank" class="btn btn-info" type="button"
               style="margin-left: 38%"><i class="fa fa-vk"></i> <l:resource key="get.access"/></a>

            <p><l:resource key="access.text2"/></p>
            <blockquote>
                <small>Пожалуйста, <strong>не копируйте</strong> данные из адресной строки для сторонних сайтов. Таким
                    образом Вы можете <strong>потерять доступ</strong> к Вашему аккаунту.
                </small>
            </blockquote>
            <p><l:resource key="access.text3"/></p>
            <l:resource key="token"><input id="token_form_id" class="form-control" type="text" placeholder=""></l:resource>
            <br>

            <div style="float: right">
                <button class="btn btn-primary" onclick="addManually()"><l:resource key="add"/></button>
                <button class="btn btn-default" onclick="closeNoExtension()"><l:resource key="cancel"/></button>
            </div>
        </div>
    </div>
</div>
<script>
    function openNoExtension() {
        $("#add_no_extension").show();
    }
    function addManually() {
        var href = $("#token_form_id").val();
        $.post(
                "http://localhost:8080/accounts?action=addManually",
                {
                    href: href
                },
                onAjaxSuccess
        );
        function onAjaxSuccess(data) {
            toastrNotification(data.status, data.msg);
            if (data.status == 'success') {
                $('#accountsTable').DataTable().draw();
                $("#add_no_extension").hide();
            }
        }
    }
    function closeNoExtension() {
        $("#add_no_extension").hide();
    }
</script>
</body>
</html>

