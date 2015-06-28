<%--
  Created by IntelliJ IDEA.
  User: Sasha
  Date: 19.06.2015
  Time: 15:09
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

    <title>All Posts</title>

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
    <%--<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>--%>
    <%--<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.tokenize.js"></script>--%>
    <%--<link rel="stylesheet" type="text/css" href="css/jquery.tokenize.css" />--%>


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

    <script type="text/javascript">
        var path = '${pageContext.request.contextPath}';
    </script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
    <link href="http://cdn.datatables.net/1.10.3/css/jquery.dataTables.css" rel="stylesheet" type="text/css">
    <link href="http://datatables.net/release-datatables/extensions/ColVis/css/dataTables.colVis.css" rel="stylesheet"
          type="text/css">
    <%--<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>--%>
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

    <script type="text/javascript">
        var table;

        jQuery(document).ready(function () {
            table = $('#postsTable').dataTable({

                "bSort": false,
                "bPaginate": true,
                "paging": true,
                "bInfo": false,
                "iDisplayStart": 0,
                "bProcessing": true,
                'iDisplayLength': 10,
                "bServerSide": true,
                "sAjaxSource": path + "/post?action=getCreatedPost",
//                "dom": 'C<"clear">lfrtip',
                colVis: {
                    "align": "right",
//                    restore: "Restore",
//                    showAll: "Show all",
//                    showNone: "Show none",
//                    order: 'alpha',
                    "buttonText": "columns <img src=\"/img/caaret.png\"/>",
                },

//                "language": {
//                    "infoFiltered": ""
//                }
//                ,
//                "dom": 'Cf<"toolbar"">rtip',//, "columnDefs": [ { "targets": 2,"orderable": false }, { "targets": 3,"orderable": false } ]//,

                "columnDefs": [{
                    "bVisible": false, "aTargets": [0]
                }, {
                    "aTargets": [1], "createdCell": function (td, cellData, rowData, row, col) {
                        $(td).html("<a href='#' onclick='showPost(" + rowData[0] + ")'>" + cellData + "</a>");
                    }
                }, {
                    "aTargets": [2], "createdCell": function (td, cellData, rowData, row, col) {
                        $(td).html(parseAttachment(cellData));
                    }
                }]

            })
//                    .columnFilter({
//                        aoColumns: [],
//                        bUseColVis: true
//                    }).fnSetFilteringDelay();


//            $(".dataTables_filter").css({"position": "auto"});
//            $(".dataTables_filter").css({"left": "0px"});
//            $(".dataTables_filter").css({"right": "30%"});
//            $(".dataTables_filter").css({"top": "-10px"});
//            $(".dataTables_filter").css({"width": "350px"});
//            $(".dataTables_filter").attr("hidden", "");
//            $("div.toolbar").append($("dataTables_filter")).append('<div class="btn-group" style="padding:5px "><button class="btn btn-default" id="refreshbtn" style="background:none;border:1px solid #ccc;height:30px" type="button"><span class="glyphicon glyphicon-refresh" style="padding:3px"></span></button></div>');
//            $("div.toolbar").css("float", "right").css({"position": "relative"});


            $('#refreshbtn').click(function () {
                table.fnStandingRedraw();
            });

            function parseAttachment(arg) {
                var args = arg.split("!");
                var cell = "";
                for (var i = 0; i < args.length; i++) {
                    cell = cell + " " + parseDoc(args[i]);
                }
                return cell;
            }

            function parseDoc(arg) {
                var args = arg.split("|");
                switch (args[0]) {
                    case "PHOTO":
                        return '<img src="${pageContext.request.contextPath}/img/icons/jpg-icon.png" style="width: 25px; height: 25px"><span class="badge bg-important">{count}</span>'.replace("{count}", args[1]);
                    case "VIDEO":
                        return '<img src="${pageContext.request.contextPath}/img/icons/mpg-icon.png" style="width: 25px; height: 25px"><span class="badge bg-important">{count}</span>'.replace("{count}", args[1]);
                    case "AUDIO":
                        return '<img src="${pageContext.request.contextPath}/img/icons/mp3-icon.png" style="width: 25px; height: 25px"><span class="badge bg-important">{count}</span>'.replace("{count}", args[1]);
                    case "DOC":
                        return '<img src="${pageContext.request.contextPath}/img/icons/txt-icon.png" style="width: 25px; height: 25px"><span class="badge bg-important">{count}</span>'.replace("{count}", args[1]);
                }
                return "";
            }
        })
    </script>
</head>
<body>

<!-- container section start -->
<section id="container" class="">

    <jsp:include page="../pagecontent/header.jsp"/>
    <jsp:include page="../pagecontent/sidebar.jsp"/>

    <a href="" onclick="showPost()"></a>


    <!--main content start-->
    <section id="main-content">
        <section class="wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <ol class="breadcrumb">
                        <li><i class="fa fa-home"></i><a href="/">Home</a></li>
                        <li><i class="fa fa-desktop"></i>Post</li>
                        <li><i class="fa fa-list-alt"></i>Created</li>
                    </ol>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-12">

                    <div class="panel">
                        <div class="panel-body">
                            <div class="tab-content">
                                <div id="active" class="tab-pane active">
                                    <div class="col-lg-12">

                                        <table width="100%" border="0" margin="0" padding="0"
                                               class="row-border tableHeader" id="postsTable">
                                            <tbody>
                                            <thead>
                                            <tr>
                                                <th>id</th>
                                                <th>Message</th>
                                                <th>Attachment</th>
                                            </tr>
                                            </thead>
                                            </tbody>
                                        </table>

                                    </div>
                                </div>
                            </div>
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
</body>
</html>

