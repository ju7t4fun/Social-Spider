<%--
  Created by IntelliJ IDEA.
  User: Sasha
  Date: 15.06.2015
  Time: 13:15
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

  <title>Add Post</title>

  <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/fileinput.css" media="all" rel="stylesheet" type="text/css" />
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
  <script src="${pageContext.request.contextPath}/js/fileinput.min.js" type="text/javascript"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js" type="text/javascript"></script>
  <!-- Bootstrap CSS -->
  <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
  <!-- bootstrap theme -->
  <link href="${pageContext.request.contextPath}/css/bootstrap-theme.css" rel="stylesheet">
  <!-- font icon -->
  <link href="${pageContext.request.contextPath}/css/elegant-icons-style.css" rel="stylesheet" />
  <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet" />
  <!-- owl carousel -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/owl.carousel.css" type="text/css">
  <link href="${pageContext.request.contextPath}/css/jquery-jvectormap-1.2.2.css" rel="stylesheet">
  <!-- Custom styles -->
  <link href="${pageContext.request.contextPath}/css/fileinput.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/style-responsive.css" rel="stylesheet" />
  <link href="${pageContext.request.contextPath}/css/jquery-ui-1.10.4.min.css" rel="stylesheet">
</head>

<body>
<!-- container section start -->
<section id="container" class="">

  <jsp:include page="../pagecontent/header.jsp" />
  <jsp:include page="../pagecontent/sidebar.jsp" />

  <!--main content start-->
  <section id="main-content">
    <section class="wrapper">
      <!--overview start-->
      <div class="row">
        <div class="col-lg-12">
          <ol class="breadcrumb">
            <li><i class="fa fa-home"></i><a href="index.html">Home</a></li>
            <li><i class="fa fa-laptop"></i>Add New Post</li>
          </ol>
        </div>
      </div>

      <!-- project team & activity start -->
      <div class="row">

        <div class="col-md-6 portlets" style="width:100%;">
          <div class="panel panel-default">
            <div class="panel-heading">
              <div class="pull-left">Add New Post</div>
              <div class="clearfix"></div>
            </div>
            <div class="panel-body" style="padding:20px;">
              <div class="padd">

                <div class="form quick-post">
                  <!-- Edit profile form (not working)-->
                  <form class="form-horizontal">
                    <!-- Title -->
                    <div class="form-group">
                      <label class="control-label col-lg-2" for="title">Title</label>
                      <div class="col-lg-10">
                        <input type="text" class="form-control" id="title">
                      </div>
                    </div>
                    <!-- Content -->
                    <div class="form-group">
                      <label class="control-label col-lg-2" for="content">Content</label>
                      <div class="col-lg-10">
                        <textarea class="form-control" id="content" style="height:250px;"></textarea>
                      </div>
                    </div>
                    <!-- Tags -->
                    <div class="form-group" >
                      <label class="control-label col-lg-2" for="tagsinput">Tags</label>

                        <input name="tagsinput" id="tagsinput" class="tagsinput" value="Tag1, Tag2, Tag3, Tag4, Tag5" />

                    </div>
                    <!-- Add file -->
                    <div class="form-group">
                      <label class="control-label col-lg-2" for="title">Add file</label>
                      <div class="col-lg-offset-2 col-lg-9">

                        <div class="container kv-main" style="width:900px;">

                            <input id="input-dim-1" type="file" multiple class="file-loading" accept="image/*, audio/*, video/*">
                            <script>
                              $("#input-dim-1").fileinput({
                                uploadUrl: "http://localhost:8080/controller?action=upload",
                              });
                            </script>
                          </div>
                      </div>
                    </div>

                    <!-- Buttons -->
                    <div class="form-group">
                      <br>
                      <!-- Buttons -->
                      <div class="col-lg-offset-2 col-lg-9">
                        <button type="submit" class="btn btn-primary">Publish</button>
                        <button type="submit" class="btn btn-danger">Save Draft</button>
                        <button type="reset" class="btn btn-default">Reset</button>
                      </div>
                    </div>
                  </form>
                </div>


              </div>
              <div class="widget-foot">
                <!-- Footer goes here -->
              </div>
            </div>
          </div>

        </div>

      </div>
      <!-- project team & activity end -->

    </section>
  </section>
  <!--main content end-->


</section>

<!-- container section start -->

<!-- javascripts -->

<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-ui-1.10.4.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-1.9.2.custom.min.js"></script>
<!-- bootstrap -->
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<!-- nice scroll -->
<script src="${pageContext.request.contextPath}/js/jquery.scrollTo.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.nicescroll.js" type="text/javascript"></script>
<!--script for this page only-->
<script src="${pageContext.request.contextPath}/js/calendar-custom.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.rateit.min.js"></script>
<!-- custom select -->
<script src="${pageContext.request.contextPath}/js/jquery.customSelect.min.js" ></script>
<script src="${pageContext.request.contextPath}/assets/chart-master/Chart.js"></script>

<!--custome script for all page-->
<script src="${pageContext.request.contextPath}/js/scripts.js"></script>
<!-- custom script for this page-->
<script src="${pageContext.request.contextPath}/js/jquery.autosize.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.placeholder.min.js"></script>
<script src="${pageContext.request.contextPath}/js/gdp-data.js"></script>
<script src="${pageContext.request.contextPath}/js/morris.min.js"></script>
<script src="${pageContext.request.contextPath}/js/sparklines.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.slimscroll.min.js"></script>
<script src="${pageContext.request.contextPath}/js/fileinput.min.js" type="text/javascript"></script>
<!--custom tagsinput-->
<script src="${pageContext.request.contextPath}/js/jquery.tagsinput.js"></script>
<script src="${pageContext.request.contextPath}/js/form-component.js"></script>

</body>
</html>


