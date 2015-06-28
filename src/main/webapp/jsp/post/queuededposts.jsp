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
  <%--<link href="${pageContext.request.contextPath}/css/style-responsive.css" rel="stylesheet" />--%>
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
  <%--<script src="${pageContext.request.contextPath}/js/gritter.js" type="text/javascript"></script>--%>
  <%--<!--custome script for all page-->--%>
  <%--<script src="${pageContext.request.contextPath}/js/scripts.js"></script>--%>
  <%--<script src="${pageContext.request.contextPath}/js/jquery.tokenize.js"></script>--%>


  <%--for table--%>

  <script type="text/javascript">
    var path = '${pageContext.request.contextPath}';
  </script>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
  <link href="http://cdn.datatables.net/1.10.3/css/jquery.dataTables.css" rel="stylesheet" type="text/css">
  <link href="http://datatables.net/release-datatables/extensions/ColVis/css/dataTables.colVis.css" rel="stylesheet"
        type="text/css">
  <script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
  <script src="http://cdn.datatables.net/1.10.3/js/jquery.dataTables.min.js"></script>
  <script src="http://datatables.net/release-datatables/extensions/ColVis/js/dataTables.colVis.js"></script>
  <script src="http://jquery-datatables-column-filter.googlecode.com/svn/trunk/media/js/jquery.dataTables.columnFilter.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/queuedposts.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/fnStandingRedraw.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/fnSetFilteringDelay.js"></script>


  <!-- HTML5 shim and Respond.js IE8 support of HTML5 -->
  <!--[if lt IE 9]>
  <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
  <script src="${pageContext.request.contextPath}/js/respond.min.js"></script>
  <script src="${pageContext.request.contextPath}/js/lte-ie7.js"></script>
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
            <li><i class="fa fa-desktop"></i>Post</li>
            <li><i class="fa fa-list-alt"></i>All Posts</li>
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
                    <%--<table class="table table-striped table-advance table-hover">--%>
                    <%--<tr><th>Post</th><th>Time</th><th>Statistics</th></tr>--%>
                    <%--<tr>--%>
                    <%--<td style="width:600px;text-align:left;"> <img src="${pageContext.request.contextPath}/img/post.png" width="50" height="45" style="margin:0 20px;"> POST_TITLE</td>--%>
                    <%--<td>15.06.2015 18:33</td>--%>
                    <%--<td>--%>
                    <%--<table>--%>
                    <%--<tr>--%>
                    <%--<td style="text-align:left;border:none;"><img src="${pageContext.request.contextPath}/img/like.png" width="23" height="23"></td>--%>
                    <%--<td style="border:none;">68</td>--%>
                    <%--<td style="text-align:left;border:none;"><img src="${pageContext.request.contextPath}/img/speaker.png" width="23" height="23"></td>--%>
                    <%--<td style="border:none;">21</td>--%>
                    <%--<td style="text-align:left;border:none;"><img src="${pageContext.request.contextPath}/img/comment.png" width="23" height="23"></td>--%>
                    <%--<td style="border:none;">4</td>--%>
                    <%--</tr>--%>
                    <%--</table>--%>
                    <%--</td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                    <%--<td style="width:600px;text-align:left;"> <img src="${pageContext.request.contextPath}/img/post.png" width="50" height="45" style="margin:0 20px;"> POST_TITLE</td>--%>
                    <%--<td>14.06.2015 08:11</td>--%>
                    <%--<td>--%>
                    <%--<table>--%>
                    <%--<tr>--%>
                    <%--<td style="text-align:left;border:none;"><img src="${pageContext.request.contextPath}/img/like.png" width="23" height="23"></td>--%>
                    <%--<td style="border:none;">213</td>--%>
                    <%--<td style="text-align:left;border:none;"><img src="${pageContext.request.contextPath}/img/speaker.png" width="23" height="23"></td>--%>
                    <%--<td style="border:none;">56</td>--%>
                    <%--<td style="text-align:left;border:none;"><img src="${pageContext.request.contextPath}/img/comment.png" width="23" height="23"></td>--%>
                    <%--<td style="border:none;">41</td>--%>
                    <%--</tr>--%>
                    <%--</table>--%>
                    <%--</td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                    <%--<td style="width:600px;text-align:left;"> <img src="${pageContext.request.contextPath}/img/post.png" width="50" height="45" style="margin:0 20px;"> POST_TITLE</td>--%>
                    <%--<td>14.06.2015 07:59</td>--%>
                    <%--<td>--%>
                    <%--<table>--%>
                    <%--<tr>--%>
                    <%--<td style="text-align:left;border:none;"><img src="${pageContext.request.contextPath}/img/like.png" width="23" height="23"></td>--%>
                    <%--<td style="border:none;">566</td>--%>
                    <%--<td style="text-align:left;border:none;"><img src="${pageContext.request.contextPath}/img/speaker.png" width="23" height="23"></td>--%>
                    <%--<td style="border:none;">347</td>--%>
                    <%--<td style="text-align:left;border:none;"><img src="${pageContext.request.contextPath}/img/comment.png" width="23" height="23"></td>--%>
                    <%--<td style="border:none;">114</td>--%>
                    <%--</tr>--%>
                    <%--</table>--%>
                    <%--</td>--%>
                    <%--</tr>--%>
                    <%--</table>--%>


                    <table width="100%" border="0" margin="0" padding="0"
                           class="row-border tableHeader" id="queuedTable">
                      <tbody>
                      <thead>
                      <tr>
                        <th>Message</th>
                        <th>Group Name</th>
                        <th>Post Time</th>
                        <th>Id</th>

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
<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="myModal" class="modal fade">
  <div class="modal-dialog">
    <div class="modal-content" style="width: 840px; position: relative; left: -120px;">
      <div class="modal-header">
        <button aria-hidden="true" data-dismiss="modal" class="close" type="button">x</button>
        <h3 class="modal-title">Post</h3>
      </div>
      <div class="modal-body" style="position: relative; left: 70px; top: -21px;">
        <div class="row">
          <div class="col-md-8 portlets">
            <div class="panel panel-default">
              <div class="panel-body" style="width: 700px; margin-left: -15px;">
                <ul style="margin-left:-30px;">
                  <li>
                    <table width="100%" style="padding:0 50px;">
                      <tr>
                        <td style="text-align:left;padding-left:20px;"><img
                                src="${pageContext.request.contextPath}/img/post.png"
                                style="margin:15px;"><strong>GROUP NAME</strong></td>
                      </tr>
                      <tr>
                        <td style="text-align:justify;">
                          Vivamus tincidunt eleifend congue. Sed lacinia ullamcorper
                          fringilla.
                          Suspendisse mi nisi, molestie eget varius quis, cursus nec felis.
                          Quisque mi tortor, accumsan vitae tempus a, placerat et est. Mauris
                          eu
                          mauris urna. Quisque ipsum purus, iaculis nec libero vel, euismod
                          lacinia est. Proin quis mi et velit scelerisque feugiat. Nunc
                          porttitor,
                          nunc et ultricies scelerisque, velit est sollicitudin tortor, ut
                          condimentum dolor nulla id sem. In suscipit urna a nibh bibendum
                          cursus.
                          Sed ex orci, rhoncus at convallis quis, bibendum ac ex. Proin
                          vulputate
                          lacus tellus, nec sollicitudin nisl congue eget.
                        </td>
                      </tr>
                      <tr>
                        <td style="text-align:left;padding-left:20px;">
                          <br>

                          <div style="color:blue;">#lorem #ipsum #dolor</div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <img src="${pageContext.request.contextPath}/img/bg-1.jpg"
                               width="600"
                               style="margin:20px;">
                        </td>
                      </tr>
                    </table>
                  </li>
                  <hr>
                  <li>
                    <table width="100%" style="padding:0 50px;">
                      <tr>
                        <td style="text-align:left;padding-left:20px;"><img
                                src="${pageContext.request.contextPath}/img/post.png"
                                style="margin:15px;"><strong>GROUP NAME</strong></td>
                      </tr>
                      <tr>
                        <td style="text-align:justify;">
                          Vivamus tincidunt eleifend congue. Sed lacinia ullamcorper
                          fringilla.
                          Suspendisse mi nisi, molestie eget varius quis, cursus nec felis.
                          Quisque mi tortor, accumsan vitae tempus a, placerat et est. Mauris
                          eu
                          mauris urna. Quisque ipsum purus, iaculis nec libero vel, euismod
                          lacinia est. Proin quis mi et velit scelerisque feugiat. Nunc
                          porttitor,
                          nunc et ultricies scelerisque, velit est sollicitudin tortor, ut
                          condimentum dolor nulla id sem. In suscipit urna a nibh bibendum
                          cursus.
                          Sed ex orci, rhoncus at convallis quis, bibendum ac ex. Proin
                          vulputate
                          lacus tellus, nec sollicitudin nisl congue eget.
                        </td>
                      </tr>
                      <tr>
                        <td style="text-align:left;padding-left:20px;">
                          <br>

                          <div style="color:#ffbbf1;">#lorem #ipsum #dolor</div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <img src="${pageContext.request.contextPath}/img/sample-img-1.jpg"
                               style="margin:20px;">
                          <img src="${pageContext.request.contextPath}/img/sample-img-2.png"
                               style="margin:20px;">
                          <img src="${pageContext.request.contextPath}/img/sample-img-3.png"
                               style="margin:20px;">
                        </td>
                      </tr>
                    </table>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<script>
  function ShowDialog(i) {
    alert(i);
  }
</script>



</body>
</html>

