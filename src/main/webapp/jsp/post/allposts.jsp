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
  <link href="${pageContext.request.contextPath}/css/elegant-icons-style.css" rel="stylesheet" />
  <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet" />
  <!-- Custom styles -->
  <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/style-responsive.css" rel="stylesheet" />
  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.tokenize.js"></script>
  <link rel="stylesheet" type="text/css" href="css/jquery.tokenize.css" />
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
                    <table class="table table-striped table-advance table-hover">
                      <tr><th>Post</th><th>Time</th><th>Statistics</th></tr>
                      <tr>
                        <td style="width:700px;text-align:left;"> <img src="${pageContext.request.contextPath}/img/post.png" width="50" height="45" style="margin:0 20px;"> POST_TITLE</td>
                        <td>15.06.2015 18:33</td>
                        <td>
                          <ul>
                            <li>
                              <img src="${pageContext.request.contextPath}/img/like.png" width="23" height="23" style="margin-right:15px;">
                              213
                            </li>
                            <li>
                              <img src="${pageContext.request.contextPath}/img/speaker.png" width="23" height="23" style="margin-right:15px;">
                              56
                            </li>
                            <li>
                              <img src="${pageContext.request.contextPath}/img/comment.png" width="23" height="23" style="margin-right:15px;">
                              41
                            </li>
                          </ul>
                        </td>
                      </tr>
                      <tr>
                        <td style="width:700px;text-align:left;"> <img src="${pageContext.request.contextPath}/img/post.png" width="50" height="45" style="margin:0 20px;"> POST_TITLE</td>
                        <td>14.06.2015 08:11</td>
                        <td>
                          <ul>
                            <li>
                              <img src="${pageContext.request.contextPath}/img/like.png" width="23" height="23" style="margin-right:15px;">
                              213
                            </li>
                            <li>
                              <img src="${pageContext.request.contextPath}/img/speaker.png" width="23" height="23" style="margin-right:15px;">
                              56
                            </li>
                            <li>
                              <img src="${pageContext.request.contextPath}/img/comment.png" width="23" height="23" style="margin-right:15px;">
                              41
                            </li>
                          </ul>
                      </tr>
                      <tr>
                        <td style="width:700px;text-align:left;"> <img src="${pageContext.request.contextPath}/img/post.png" width="50" height="45" style="margin:0 20px;"> POST_TITLE</td>
                        <td>14.06.2015 07:59</td>
                        <td>
                          <ul>
                            <li>
                              <img src="${pageContext.request.contextPath}/img/like.png" width="23" height="23" style="margin-right:15px;">
                              213
                            </li>
                            <li>
                              <img src="${pageContext.request.contextPath}/img/speaker.png" width="23" height="23" style="margin-right:15px;">
                              56
                            </li>
                            <li>
                              <img src="${pageContext.request.contextPath}/img/comment.png" width="23" height="23" style="margin-right:15px;">
                              41
                            </li>
                          </ul>
                      </tr>
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

</body>
</html>

