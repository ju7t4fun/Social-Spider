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
    <meta name="keyword"
          content="Creative, Dashboard, Admin, Template, Theme, Bootstrap, Responsive, Retina, Minimal">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/icons/favicon.png">

    <title>Add Post</title>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/fileinput.css" media="all" rel="stylesheet"
          type="text/css"/>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/fileinput.min.js" type="text/javascript"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"
            type="text/javascript"></script>
    <!-- Bootstrap CSS -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <!-- bootstrap theme -->
    <link href="${pageContext.request.contextPath}/css/bootstrap-theme.css" rel="stylesheet">
    <!-- font icon -->
    <link href="${pageContext.request.contextPath}/css/elegant-icons-style.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet"/>
    <!-- owl carousel -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/owl.carousel.css" type="text/css">
    <link href="${pageContext.request.contextPath}/css/jquery-jvectormap-1.2.2.css" rel="stylesheet">
    <!-- Custom styles -->
    <link href="${pageContext.request.contextPath}/css/fileinput.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style-responsive.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/jquery-ui-1.10.4.min.css" rel="stylesheet">


</head>

<body>
<!-- container section start -->

<section id="container" class="">

    <jsp:include page="../pagecontent/header.jsp"/>
    <jsp:include page="../pagecontent/sidebar.jsp"/>

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
                                            <label class="control-label col-lg-2"
                                                   for="content">Content</label>

                                            <div class="col-lg-10">
                                                <textarea class="form-control" id="content"
                                                          style="height:250px;"></textarea>
                                            </div>
                                        </div>
                                        <!-- Tags -->
                                        <div class="form-group">
                                            <label class="control-label col-lg-2" for="tagsinput">Tags</label>

                                            <div class="panel-body">
                                                <input name="tagsinput" id="tagsinput" class="tagsinput"
                                                       value="Tag1, Tag2, Tag3, Tag4, Tag5"/>
                                            </div>
                                        </div>

                                        <!-- Add file -->


                                        <div class="form-group">
                                            <label class="control-label col-lg-2" for="title">Add file</label>

                                            <div id="upload-from">
                                                <div class="btn-group">
                                                    <button type="button" class="btn btn-primary">Upload
                                                        from
                                                    </button>
                                                    <button type="button" class="btn btn-primary dropdown-toggle"
                                                            data-toggle="dropdown" aria-haspopup="true"
                                                            aria-expanded="false">
                                                        <span class="caret"></span>
                                                        <span class="sr-only">Dropdown</span>
                                                    </button>
                                                    <ul class="dropdown-menu">
                                                        <li><a href="#"
                                                               onclick="uploadFromComputer();">Computer
                                                        </a></li>
                                                        <li><a href="#" onclick="uploadFromURL();">URL</a></li>
                                                    </ul>
                                                </div>
                                            </div>
                                            <div class="col-lg-offset-2 col-lg-9">
                                                <script>
                                                    function uploadFromComputer() {
                                                        $('#uriForm').hide();
                                                        $('#compForm').show();
                                                        $('html, body').animate({
                                                            scrollTop: $(".widget-foot").offset().top
                                                        }, 1000);
                                                    }
                                                    function uploadFromURL() {
                                                        $('#compForm').hide();
                                                        $('#uriForm').show();
                                                        $('#fl').hide();
                                                        $('#sc').hide();
                                                        $('html, body').animate({
                                                            scrollTop: $(".widget-foot").offset().top
                                                        }, 1000);
                                                    }
                                                </script>
                                                <div id="compForm" class="container kv-main" style="width:800px;
                                                margin-top:20px;">
                                                    <input id="input-dim-2" type="file"
                                                           multiple="true" method="post"
                                                           enctype="multipart/form-data"
                                                           accept="audio/*,video/*,image/*" value="">
                                                    <script>
                                                        $("#input-dim-2").fileinput({
                                                            uploadUrl: "http://localhost:8080/controller?action=upload"
                                                        });
                                                    </script>
                                                </div>
                                                <div class="container kv-main" id="uriForm" style="width:800px;
                                                margin-top:20px;">
                                                    <form class="bs-example bs-example-form" role="form">
                                                        <div class="row">
                                                            <div class="col-lg-6">
                                                                <div class="input-group">
                                                             <span class="input-group-btn">
                                                            <button id="button1" class="btn btn-default" type="button">
                                                                Upload
                                                            </button>
                                                             </span>
                                                                    <input style="width:700px" id="text1" type="text"
                                                                           placeholder="Enter url to you file"
                                                                           class="form-control">
                                                                </div>
                                                            </div>
                                                            <br>
                                                        </div>
                                                    </form>
                                                    <br>

                                                    <div id="sc" class="alert alert-success"
                                                         role="alert"><span
                                                            id="success"></span></div>
                                                    <div id="fl" class="alert alert-danger"
                                                         role="alert"><span id="fail"></span></div>
                                                </div>
                                            </div>
                                        </div>

                                        <script>
                                            $("#button1").click(function () {
                                                var text = $('#text1').val();
                                                var dataString = "url=" + text;
                                                $.ajax({
                                                    type: "POST",
                                                    url: 'http://localhost:8080/controller?action=uploadurl',
                                                    data: dataString,
                                                    dataType: "json",
                                                    success: function (data) {
                                                        if (data.success) {
                                                            $('#fl').hide();
                                                            $('#sc').show();
                                                            $("#success").text(data.success);
                                                            setTimeout(function () {
                                                                $('#sc').hide();
                                                            }, 4000);
                                                        }
                                                        else {
                                                            $('#sc').hide();
                                                            $('#fl').show();
                                                            $("#fail").text(data.fail);
                                                            setTimeout(function () {
                                                                $('#fl').hide();
                                                            }, 4000);


                                                        }
                                                    }
                                                });
                                            });
                                        </script>
                                        <script>
                                            $('#uriForm').hide();
                                            $('#compForm').hide();
                                            $('#sc').hide();
                                            $('#fl').hide();
                                        </script>
                                        <!-- Buttons -->
                                        <div class="form-group">
                                            <br>
                                            <!-- Buttons -->
                                            <div class="col-lg-offset-2 col-lg-9">
                                                <button type="submit" class="btn btn-primary">Publish</button>
                                                <button type="submit" class="btn btn-danger">Save Draft
                                                </button>
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
<script type="text/javascript"
        src="${pageContext.request.contextPath}/js/jquery-ui-1.9.2.custom.min.js"></script>
<!-- bootstrap -->
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<!-- nice scroll -->
<script src="${pageContext.request.contextPath}/js/jquery.scrollTo.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.nicescroll.js" type="text/javascript"></script>
<!--script for this page only-->
<script src="${pageContext.request.contextPath}/js/calendar-custom.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.rateit.min.js"></script>
<!-- custom select -->
<script src="${pageContext.request.contextPath}/js/jquery.customSelect.min.js"></script>
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