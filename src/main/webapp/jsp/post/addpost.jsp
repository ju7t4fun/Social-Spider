<%--
  Created by IntelliJ IDEA.
  User: Sasha
  Date: 15.06.2015
  Time: 13:15
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: Sasha
  Date: 13.06.2015
  Time: 12:15
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

  <!-- Bootstrap CSS -->
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js" type="text/javascript"></script>
  <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
  <!-- bootstrap theme -->
  <link href="${pageContext.request.contextPath}/css/bootstrap-theme.css" rel="stylesheet">
  <!--external css-->
  <!-- font icon -->
  <link href="${pageContext.request.contextPath}/css/elegant-icons-style.css" rel="stylesheet" />
  <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet" />
  <!-- full calendar css-->
  <link href="${pageContext.request.contextPath}/assets/fullcalendar/fullcalendar/bootstrap-fullcalendar.css" rel="stylesheet" />
  <link href="${pageContext.request.contextPath}/assets/fullcalendar/fullcalendar/fullcalendar.css" rel="stylesheet" />
  <!-- easy pie chart-->
  <link href="${pageContext.request.contextPath}/assets/jquery-easy-pie-chart/jquery.easy-pie-chart.css" rel="stylesheet" type="text/css" media="screen"/>
  <!-- owl carousel -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/owl.carousel.css" type="text/css">
  <link href="${pageContext.request.contextPath}/css/jquery-jvectormap-1.2.2.css" rel="stylesheet">
  <!-- Custom styles -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/fullcalendar.css">
  <link href="${pageContext.request.contextPath}/css/widgets.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/style-responsive.css" rel="stylesheet" />
  <link href="${pageContext.request.contextPath}/css/xcharts.min.css" rel=" stylesheet">
  <link href="${pageContext.request.contextPath}/css/jquery-ui-1.10.4.min.css" rel="stylesheet">
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
            <div class="panel-body">
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
                    <div class="form-group">
                      <label class="control-label col-lg-2" for="tags">Tags</label>
                      <div class="col-lg-10">
                        <input type="text" class="form-control" id="tags">
                      </div>
                    </div>
                    <!-- Add file -->
                    <div class="form-group">
                      <label class="control-label col-lg-2" for="title">Add file</label>
                      <div class="col-lg-offset-2 col-lg-9">

                        <div class="container kv-main">

                          <form enctype="multipart/form-data">
                            <input id="file-0a" class="file" type="file" multiple data-min-file-count="1">
                          </form>
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
<script>
  $('#file-fr').fileinput({
    language: 'fr',
    uploadUrl: '#',
    allowedFileExtensions : ['jpg', 'png','gif'],
  });
  $('#file-es').fileinput({
    language: 'es',
    uploadUrl: '#',
    allowedFileExtensions : ['jpg', 'png','gif'],
  });
  $("#file-0").fileinput({
    'allowedFileExtensions' : ['jpg', 'png','gif'],
  });
  $("#file-1").fileinput({
    uploadUrl: '#', // you must set a valid URL here else you will get an error
    allowedFileExtensions : ['jpg', 'png','gif'],
    overwriteInitial: false,
    maxFileSize: 1000,
    maxFilesNum: 10,
    //allowedFileTypes: ['image', 'video', 'flash'],
    slugCallback: function(filename) {
      return filename.replace('(', '_').replace(']', '_');
    }
  });
  /*
   $(".file").on('fileselect', function(event, n, l) {
   alert('File Selected. Name: ' + l + ', Num: ' + n);
   });
   */
  $("#file-3").fileinput({
    showUpload: false,
    showCaption: false,
    browseClass: "btn btn-primary btn-lg",
    fileType: "any",
    previewFileIcon: "<i class='glyphicon glyphicon-king'></i>"
  });
  $("#file-4").fileinput({
    uploadExtraData: {kvId: '10'}
  });
  $(".btn-warning").on('click', function() {
    if ($('#file-4').attr('disabled')) {
      $('#file-4').fileinput('enable');
    } else {
      $('#file-4').fileinput('disable');
    }
  });
  $(".btn-info").on('click', function() {
    $('#file-4').fileinput('refresh', {previewClass:'bg-info'});
  });
  /*
   $('#file-4').on('fileselectnone', function() {
   alert('Huh! You selected no files.');
   });
   $('#file-4').on('filebrowse', function() {
   alert('File browse clicked for #file-4');
   });
   */
  $(document).ready(function() {
    $("#test-upload").fileinput({
      'showPreview' : false,
      'allowedFileExtensions' : ['jpg', 'png','gif'],
      'elErrorContainer': '#errorBlock'
    });
    /*
     $("#test-upload").on('fileloaded', function(event, file, previewId, index) {
     alert('i = ' + index + ', id = ' + previewId + ', file = ' + file.name);
     });
     */
  });
</script>
<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-ui-1.10.4.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-1.9.2.custom.min.js"></script>
<!-- bootstrap -->
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<!-- nice scroll -->
<script src="${pageContext.request.contextPath}/js/jquery.scrollTo.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.nicescroll.js" type="text/javascript"></script>
<!-- charts scripts -->
<script src="${pageContext.request.contextPath}/assets/jquery-knob/js/jquery.knob.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.sparkline.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/jquery-easy-pie-chart/jquery.easy-pie-chart.js"></script>
<script src="${pageContext.request.contextPath}/js/owl.carousel.js" ></script>
<!-- jQuery full calendar -->
<<script src="${pageContext.request.contextPath}/js/fullcalendar.min.js"></script> <!-- Full Google Calendar - Calendar -->
<script src="${pageContext.request.contextPath}/assets/fullcalendar/fullcalendar/fullcalendar.js"></script>
<!--script for this page only-->
<script src="${pageContext.request.contextPath}/js/calendar-custom.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.rateit.min.js"></script>
<!-- custom select -->
<script src="${pageContext.request.contextPath}/js/jquery.customSelect.min.js" ></script>
<script src="${pageContext.request.contextPath}/assets/chart-master/Chart.js"></script>

<!--custome script for all page-->
<script src="${pageContext.request.contextPath}/js/scripts.js"></script>
<!-- custom script for this page-->
<script src="${pageContext.request.contextPath}/js/sparkline-chart.js"></script>
<script src="${pageContext.request.contextPath}/js/easy-pie-chart.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-jvectormap-1.2.2.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-jvectormap-world-mill-en.js"></script>
<script src="${pageContext.request.contextPath}/js/xcharts.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.autosize.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.placeholder.min.js"></script>
<script src="${pageContext.request.contextPath}/js/gdp-data.js"></script>
<script src="${pageContext.request.contextPath}/js/morris.min.js"></script>
<script src="${pageContext.request.contextPath}/js/sparklines.js"></script>
<script src="${pageContext.request.contextPath}/js/charts.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.slimscroll.min.js"></script>
<script src="${pageContext.request.contextPath}/js/fileinput.min.js" type="text/javascript"></script>

</body>
</html>


