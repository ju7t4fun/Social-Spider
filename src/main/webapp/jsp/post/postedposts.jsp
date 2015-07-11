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

    <title>Posted Posts</title>

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
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/posts-datatable.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/fnStandingRedraw.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/fnSetFilteringDelay.js"></script>
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">

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
                    <h3 class="page-header"><i class="fa fa-list-alt"></i> <l:resource key="posted"/></h3>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <ol class="breadcrumb">
                        <li><i class="fa fa-home"></i><a href="/"><l:resource key="home"/></a></li>
                        <li><i class="fa fa-desktop"></i><l:resource key="post"/></li>
                        <li><i class="fa fa-list-alt"></i><l:resource key="posted"/></li>
                    </ol>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <div class="pull-left"><l:resource key="posted"/></div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="panel-body">
                            <table width="100%" border="0" margin="0" padding="0"
                                   class="row-border tableHeader" id="postsTable">
                                <tbody>
                                <thead>
                                <tr>
                                    <th><l:resource key="message"/></th>
                                    <th><l:resource key="owner.groupname"/></th>
                                    <th><l:resource key="attachment"/></th>
                                    <th><l:resource key="newpost.posttime"/></th>
                                    <th><l:resource key="owner.statistics"/></th>
                                    <th><l:resource key="owner.id"/></th>
                                    <th><l:resource key="delete"/></th>
                                </tr>
                                </thead>
                                </tbody>
                            </table>
                            <%--<input class="btn btn-primary" style="visibility: hidden" type="button" id="showAllBtnId"--%>
                                   <%--onclick="myFunc()" value="Show All"/>--%>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </section>
</section>
<%--Вікно статистики поста--%>
<div class="b-popup" id="popup_post_stats">
    <div class="b-popup-content" style="height: 340px;">
        <h4><l:resource key="poststats"/></h4>
        <br>
        <table id="stats_table" class="col-lg-12">
            <tr>
                <td>
                    <div align="center"><l:resource key="subscribers"/></div>
                    <div align="center">
                        <h1 id="reach_total">0</h1>

                        <h1>/</h1>

                        <h1 id="reach_subscribers">0</h1></div>
                    <div align="center">
                        <l:resource key="amountofusers"/>
                    </div>
                </td>
                <td>
                    <div align="center"><l:resource key="feedback"/></div>
                    <div>
                        <table>
                            <tr>
                                <td style="text-align: center">
                                    <h1><i style="color: #6c6c6c;" class="fa fa-heart"></i><span id="likes"></span></h1>
                                </td>
                                <td style="text-align: center">
                                    <h1><i style="color: #6c6c6c;" class="fa fa-bullhorn"></i><span id="reposts"></span>
                                    </h1>
                                </td>
                                <td style="text-align: center">
                                    <h1><i style="color: #6c6c6c;" class="fa fa-comment"></i><span id="comments"></span>
                                    </h1>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div align="center"><l:resource key="like.share.comment.amount"/></div>
                </td>
            </tr>
            <tr>
                <td><h4 id="to_group">0</h4> <l:resource key="community.visits"/></td>
                <td><h4 id="hide_post">0</h4> <l:resource key="hide.post"/></td>
            </tr>
            <tr>
                <td><h4 id="join_group">0</h4> <l:resource key="community.join"/></td>
                <td><h4 id="report">0</h4> <l:resource key="report"/></td>
            </tr>
            <tr>
                <td><h4 id="links">0</h4> <l:resource key="links.followed"/></td>
                <td><h4 id="unsubscribe">0</h4> <l:resource key="unsubscribe"/></td>
            </tr>
        </table>
        <br>

        <div align="right" style="margin-right: 20px">
            <button class="btn btn-primary" onclick="closePostStats()"><l:resource key="ok"/></button>
        </div>
    </div>
    <script>
        function openPostStats(postId) {
            $.post(
                    "http://localhost:8080/post?action=postStats",
                    {
                        post_id: postId
                    },
                    onAjaxSuccess
            );
            function onAjaxSuccess(data) {

                $('#reach_total').html(' ' + data.reach_total);
                $('#reach_subscribers').html(' ' + data.reach_subscribers)

                $('#likes').html(' ' + data.likes);
                $('#reposts').html(' ' + data.reposts)
                $('#comments').html(' ' + data.comments);

                $('#to_group').html(' ' + data.to_group);
                $('#hide_post').html(' ' + data.hide);
                $('#join_group').html(' ' + data.join_group);
                $('#report').html(' ' + data.report);
                $('#links').html(' ' + data.links);
                $('#unsubscribe').html(' ' + data.unsubscribe);

                $("#popup_post_stats").show();
            }
        }
        function closePostStats() {
            $("#popup_post_stats").hide();
        }
    </script>
</div>
<script>
    function myFunc() {
        var newUrl = path + "/post?action=getPosted";
        table.api().ajax.url(newUrl).load();
        document.getElementById("showAllBtnId").style.visibility = "hidden";
    }
</script>
<style>
    #stats_table td {
        vertical-align: top;
        text-align: left
    }

    #stats_table h1 {
        display: inline;
        font-weight: 900;
        color: #00a0df;
    }

    #stats_table h4 {
        margin-left: 25px;
        display: inline;
        font-weight: 600;
        color: #00a0df;
    }
</style>
</body>
</html>

