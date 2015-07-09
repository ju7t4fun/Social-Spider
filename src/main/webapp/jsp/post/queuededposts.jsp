<%--
  Created by IntelliJ IDEA.
  User: Sasha
  Date: 19.06.2015
  Time: 15:09
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

    <title>Queued Posts</title>

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

    <script>
        var path = '${pageContext.request.contextPath}';
    </script>
    <link href="http://cdn.datatables.net/1.10.3/css/jquery.dataTables.css" rel="stylesheet" type="text/css">
    <link href="http://datatables.net/release-datatables/extensions/ColVis/css/dataTables.colVis.css" rel="stylesheet"
          type="text/css">
    <script src="http://cdn.datatables.net/1.10.3/js/jquery.dataTables.min.js"></script>
    <script src="http://datatables.net/release-datatables/extensions/ColVis/js/dataTables.colVis.js"></script>
    <script src="http://jquery-datatables-column-filter.googlecode.com/svn/trunk/media/js/jquery.dataTables.columnFilter.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/fnStandingRedraw.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/fnSetFilteringDelay.js"></script>


    <script type="text/javascript" src="${pageContext.request.contextPath}/js/queuedposts.js"></script>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 -->
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
    <script src="${pageContext.request.contextPath}/js/respond.min.js"></script>
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

    <jsp:include page="../post/viewpost.jsp"/>
    <%--for confirm delete modal window(include script and css)--%>
    <jsp:include page="../pagecontent/confirm-delete.jsp"/>

    <section id="main-content">
        <section class="wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header"><i class="fa fa-list-alt"></i> <l:resource key="queued"/></h3>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <ol class="breadcrumb">
                        <li><i class="fa fa-home"></i><a href="/"><l:resource key="home"/></a></li>
                        <li><i class="fa fa-desktop"></i><l:resource key="post"/></li>
                        <li><i class="fa fa-list-alt"></i><l:resource key="queued"/></li>
                    </ol>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <div class="pull-left"><l:resource key="queued"/></div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="panel-body">
                            <div id="active" class="tab-pane active">
                                <div class="col-lg-12">


                                    <table width="100%" border="0" margin="0" padding="0"
                                           class="row-border tableHeader" id="queuedTable">
                                        <tbody>
                                        <thead>
                                        <tr>
                                            <th><l:resource key="message"/></th>
                                            <th><l:resource key="owner.groupname"/></th>
                                            <th><l:resource key="attachment"/></th>
                                            <th><l:resource key="newpost.posttime"/></th>
                                            <th><l:resource key="owner.id"/></th>
                                            <th><l:resource key="delete"/></th>

                                        </tr>
                                        </thead>

                                        </tbody>
                                    </table>
                                    <l:resource key="showall"><input class="btn btn-primary" style="visibility: hidden" type="button" id="showAllBtnId" onclick="myFunc()" value=""/></l:resource>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </section>
    <!--main content end-->
</section>
<!-- container section end -->
<script>
    function myFunc() {
        var newUrl = path + "/post?action=fillqueuededposts";
        table.api().ajax.url(newUrl).load();
        document.getElementById("showAllBtnId").style.visibility = "hidden";
    };
</script>
</body>
</html>

