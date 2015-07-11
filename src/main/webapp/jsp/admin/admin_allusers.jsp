<%--
  Created by IntelliJ IDEA.
  User: Sasha
  Date: 14.06.2015
  Time: 22:46
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

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/custom-datatable.js"></script>
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">

    <link href="${pageContext.request.contextPath}/css/toastr.css" rel="stylesheet" type="text/css"/>
    <script src="${pageContext.request.contextPath}/js/toastr.js"></script>

    <script>

        function changeImage(e) {
            var dataString;
            if (e.title.valueOf() == "activated".valueOf()) {
                e.src = "${pageContext.request.contextPath}/img/banned.jpg";
                e.title = "banned";
                dataString = "email=" + e.id + "&status=banned";

                $.ajax({
                    type: "POST",
                    url: "/admin/users?action=status",
                    data: dataString,
                    success: function (data) {
                        toastrNotification(data.status, data.msg)
                    }
                });
                return;
            }

            if (e.title.valueOf() == "banned".valueOf()) {
                e.src = "${pageContext.request.contextPath}/img/created.jpg";
                e.title = "created";
                dataString = "email=" + e.id + "&status=created";

                $.ajax({
                    type: "POST",
                    url: "/admin/users?action=status",
                    data: dataString,
                    success: function (data) {
                        toastrNotification(data.status, data.msg)
                    }
                });
                return;
            }

            if (e.title.valueOf() == "created".valueOf()) {
                e.src = "${pageContext.request.contextPath}/img/activated.jpg";
                e.title = "activated";
                dataString = "email=" + e.id + "&status=activated";

                $.ajax({
                    type: "POST",
                    url: "/admin/users?action=status",
                    data: dataString,
                    success: function (data) {
                        toastrNotification(data.status, data.msg)
                    }
                });
            }
        }

    </script>

</head>

<body>
<jsp:include page="../pagecontent/header.jsp"/>
<jsp:include page="../pagecontent/sidebar.jsp"/>

<section id="container" class="">

    <section id="main-content">
        <section class="wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header"><i class="fa fa-list-alt"></i> <l:resource key="userlist"/></h3>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <ol class="breadcrumb">
                        <li><i class="fa fa-home"></i><a href="/"><l:resource key="admin"/></a></li>
                        <li><i class="fa fa-desktop"></i><l:resource key="edit"/></li>
                        <li><i class="fa fa-list-alt"></i><l:resource key="userlist"/></li>
                    </ol>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <div class="pull-left"><l:resource key="userlist"/></div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="panel-body">
                            <table width="100%" border="0" margin="0" padding="0"
                                   class="row-border tableHeader" id="personTable">
                                <tbody>
                                <thead>
                                <tr>
                                    <th><l:resource key="owner.id"/></th>
                                    <th><l:resource key="reg.name"/></th>
                                    <th><l:resource key="reg.surname"/></th>
                                    <th><l:resource key="login.email"/></th>
                                    <th><l:resource key="state"/></th>
                                    <th><l:resource key="role"/></th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </section>
</section>
</body>
</html>

