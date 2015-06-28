<%--
  Created by IntelliJ IDEA.
  User: Sasha
  Date: 15.06.2015
  Time: 13:15
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
    <meta name="keyword"
          content="Creative, Dashboard, Admin, Template, Theme, Bootstrap, Responsive, Retina, Minimal">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/icons/favicon.png">

    <title><l:resource key="addnewpost" /></title>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/fileinput.css" media="all" rel="stylesheet"
          type="text/css"/>
    <%--<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>--%>
    <script src="${pageContext.request.contextPath}/js/fileinput.min.js" type="text/javascript"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"
            type="text/javascript"></script>
    <!-- Bootstrap CSS -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <!-- bootstrap theme -->
    <%--<link href="${pageContext.request.contextPath}/css/bootstrap-theme.css" rel="stylesheet">--%>
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
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.tokenize.js"></script>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery.tokenize.css"/>

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

    <!--main content start-->
    <section id="main-content">
        <section class="wrapper">
            <!--overview start-->
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header"><i class="fa fa-credit-card"></i><l:resource key="newpost.newpost" /></h3>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <ol class="breadcrumb">
                        <li><i class="fa fa-home"></i><a href="/"><l:resource key="home" /></a></li>
                        <li><i class="fa fa-laptop"></i><l:resource key="addnewpost" /></li>
                    </ol>
                </div>
            </div>

            <!-- project team & activity start -->
            <div class="row">

                <div class="col-md-6 portlets" style="width:100%;">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <div class="pull-left"><l:resource key="addnewpost" /></div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="panel-body">
                            <div class="padd">

                                <div class="form quick-post">
                                    <!-- Edit profile form (not working)-->
                                    <form id="post_form" class="form-horizontal"
                                          action="/post" method="GET">
                                        <input type="hidden" name="action" value="addPost">
                                        <!-- Title -->
                                        <div class="form-group">
                                            <label class="control-label col-lg-2" for="title"><l:resource key="newpost.title" /></label>

                                            <div class="col-lg-10">
                                                <input type="text" name="title" class="form-control" id="title">
                                            </div>
                                        </div>
                                        <!-- Content -->
                                        <div class="form-group">
                                            <label class="control-label col-lg-2" for="content"><l:resource key="newpost.content" /></label>

                                            <div class="col-lg-10">
                                                <textarea class="form-control" name="message" id="content"
                                                          style="height:250px;"></textarea>
                                            </div>
                                        </div>
                                        <!-- Tags -->

                                        <div class="form-group">
                                            <div style="width:1100px;">
                                            <label class="control-label col-lg-2" for="tagsinput" style="margin-right:20px;"><l:resource key="newpost.tags" /></label>


                                                <input name="tags" id="tagsinput" class="tagsinput"/>

                                        </div>
                                        </div>
                                        <!-- Add file -->

                                        <div class="form-group">
                                            <label class="control-label col-lg-2" for="title"><l:resource key="newpost.addfile" /></label>

                                            <div id="upload-from">
                                                <div class="btn-group">
                                                    <button type="button" class="btn btn-primary"><l:resource key="newpost.upload" /></button>
                                                    <button type="button" class="btn btn-primary dropdown-toggle"
                                                            data-toggle="dropdown" aria-haspopup="true"
                                                            aria-expanded="false">
                                                        <span class="caret"></span>
                                                        <span class="sr-only">Dropdown</span>
                                                    </button>
                                                    <ul class="dropdown-menu">
                                                        <li><a href="#"
                                                               onclick="uploadFromComputer();"><l:resource key="newpost.computer" />
                                                        </a></li>
                                                        <li><a href="#" onclick="uploadFromURL();"><l:resource key="newpost.url" /></a></li>
                                                    </ul>
                                                </div>
                                            </div>
                                            <div class="col-lg-offset-2 col-lg-9">
                                                <script>
                                                    function uploadFromComputer() {
                                                        $('#uriForm').hide();
                                                        $('#compForm').show();
                                                        $('html, body').animate({
                                                            scrollTop: $("#scrl").offset().top
                                                        }, 1000);
                                                    }
                                                    function uploadFromURL() {
                                                        $('#compForm').hide();
                                                        $('#uriForm').show();
                                                        $('#fl').hide();
                                                        $('#sc').hide();
                                                        $('html, body').animate({
                                                            scrollTop: $("#scrl").offset().top
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
                                                            uploadUrl: "http://localhost:8080/controller?action=upload",
                                                            maxFileCount: 10
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
                                                        } else {
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
                                            <div id="scrl" class="col-lg-offset-2 col-lg-9">
                                                <button id="publish" class="btn btn-primary"><l:resource key="newpost.save" /></button>
                                                <button class="btn btn-info" data-toggle="modal"
                                                        data-target="#myModal"><l:resource key="newpost.publish" />
                                                </button>
                                                <button type="reset" class="btn btn-default"><l:resource key="newpost.reset" /></button>
                                            </div>
                                        </div>
                                        <script>
                                            $("#publish").click(function () {
                                                $("#post_form").submit();
                                            });
                                        </script>
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

        </section>
    </section>
</section>

<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="myModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">x</button>
                <h4 class="modal-title"><l:resource key="newpost.dateandtime" /></h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <form id="modal_form" method="POST" action="/post?action=addPost" class="form-horizontal">
                            <input type="hidden" name="typePost" value="new">

                            <div style="position: relative; left: -130px; top:30px;">
                                <div class="form-group">
                                    <label class="col-md-4 control-label" for="date"><l:resource key="newpost.date" /></label>

                                    <div class="col-md-4">
                                        <l:resource key="newpost.postdate"><input id="date" name="date" type="date" min="${date}" value="${date}"
                                               placeholder="" class="form-control input-md"></l:resource>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 control-label" for="time"><l:resource key="newpost.time" /></label>

                                    <div class="col-md-4">
                                        <l:resource key="newpost.posttime"><input id="time" name="time" type="time" value="${time}" placeholder=""
                                               class="form-control input-md"></l:resource>
                                    </div>
                                </div>
                            </div>
                            <div style="position: relative; left: 250px; top:-109px;">
                                <div class="form-group" style="">
                                    <div class="col-lg-6">
                                        <h4><l:resource key="newpost.selectgroup" />:</h4>
                                        <select name="groups" id="tokenize_focus" multiple="multiple"
                                                class="tokenize-sample">
                                            <option value="1">GrabGroup 1</option>
                                            <option value="2">GrabGroup 2</option>
                                            <option value="3">GrabGroup 3</option>
                                            <option value="4">GrabGroup 4</option>
                                            <option value="5">GrabGroup 5</option>
                                            <option value="11">GrabGroup 11</option>
                                            <option value="12">GrabGroup 12</option>
                                            <option value="13">GrabGroup 13</option>
                                            <option value="14">GrabGroup 14</option>
                                            <option value="15">GrabGroup 15</option>
                                            <option value="21">GrabGroup 21</option>
                                            <option value="22">GrabGroup 22</option>
                                            <option value="23">GrabGroup 23</option>
                                            <option value="24">GrabGroup 24</option>
                                            <option value="25">GrabGroup 25</option>
                                        </select>

                                        <script type="text/javascript">
                                            $('select#tokenize_focus').tokenize({displayDropdownOnFocus: true});
                                        </script>
                                    </div>
                                </div>
                            </div>
                            <div style="position: relative; left:-58px; top:-100px;">
                                <div class="form-group">
                                    <label class="col-md-4 control-label" for="check"><l:resource key="newpost.removingdate" /></label>
                                    <input id="check" type="checkbox">
                                </div>
                            </div>
                            <div style="position: relative; left:54px; top:-90px; width: 600px;">
                                <div id="time3" class="col-md-4">
                                    <l:resource key="newpost.date"><input id="time1" name="date_delete" min="${date}" value="${del_date}" type="date"
                                           placeholder="" class="form-control input-md"></l:resource>
                                </div>

                            </div>
                            <div style="position: relative; left:-146px; top:-50px; width: 600px;">
                                <div id="time4" class="col-md-4">
                                    <l:resource key="newpost.time"><input id="time5" name="time_delete" type="time" placeholder="Time"
                                           class="form-control input-md" value="${del_time}"></l:resource>
                                </div>
                            </div>
                            <button id="submit_modal" type="button" style="margin-left: 455px;margin-top: -80px;"
                                    class="btn btn-primary"
                                    data-dismiss="modal">
                                <l:resource key="newpost.save" />
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        $("#time3, #time4").hide();
        $('#check').click(function () {
            $("#time3, #time4").toggle(this.checked);
        });
        $(document).ready(function () {
            $("#submit_modal").click(function () {
                $.post(
                        "/post?action=addPost",
                        {
                            typePost: "new",
                            title: $("#title").val(),
                            message: $("#content").val(),
                            tags: $("#tagsinput").val(),
                            date: $("#date").val(),
                            time: $("#time").val(),
                            date_delete: $("#time1").val(),
                            time_delete: $("#time5").val(),
                            groups: $("#tokenize_focus").val().toString()
                        },
                        onAjaxSuccess
                );
                function onAjaxSuccess(data) {
                    var responce = JSON.parse(data);
                    if (responce.status === 'success') {
                        location.href = "/post";
                    }
                }
            });
        });
    </script>
    <!-- container section start -->

    <!-- javascripts -->

    <%--<script src="${pageContext.request.contextPath}/js/jquery.js"></script>--%>
    <script src="${pageContext.request.contextPath}/js/jquery-ui-1.10.4.min.js"></script>
    <%--<script src="${pageContext.request.contextPath}/js/jquery-1.8.3.min.js"></script>--%>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/jquery-ui-1.9.2.custom.min.js"></script>
    <!-- bootstrap -->
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <!-- nice scroll -->
    <script src="${pageContext.request.contextPath}/js/jquery.scrollTo.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.nicescroll.js"
            type="text/javascript"></script>
    <!--script for this page only-->
    <script src="${pageContext.request.contextPath}/js/calendar-custom.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.rateit.min.js"></script>
    <!-- custom select -->
    <script src="${pageContext.request.contextPath}/js/jquery.customSelect.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/chart-master/Chart.js"></script>
    <script src="${pageContext.request.contextPath}/js/gritter.js" type="text/javascript"></script>


    <!--custome script for all page-->
    <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
    <!-- custom script for this page-->
    <script src="${pageContext.request.contextPath}/js/jquery.autosize.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.placeholder.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/gdp-data.js"></script>
    <script src="${pageContext.request.contextPath}/js/morris.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/sparklines.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.slimscroll.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/fileinput.min.js"
            type="text/javascript"></script>
    <!--custom tagsinput-->
    <script src="${pageContext.request.contextPath}/js/jquery.tagsinput.js"></script>
    <script src="${pageContext.request.contextPath}/js/form-component.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.tokenize.js"></script>


</body>
</html>