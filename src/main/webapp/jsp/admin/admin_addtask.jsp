<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
<%--
  Created by IntelliJ IDEA.
  User: maryan
  Date: 09.07.2015
  Time: 14:44
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

    <title>Task | Add New Task</title>

    <!-- Bootstrap CSS -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <!-- bootstrap theme -->
    <link href="${pageContext.request.contextPath}/css/bootstrap-theme.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery.tokenize.js"></script>

    <!--external css-->
    <!-- font icon -->
    <link href="${pageContext.request.contextPath}/css/elegant-icons-style.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet"/>
    <!-- Custom styles -->
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style-responsive.css" rel="stylesheet"/>
    <%--<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>--%>

    <!-- javascripts -->
    <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.tokenize.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery.tokenize.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/save.admintask.js"></script>
    <script src="${pageContext.request.contextPath}/assets/ionRangeSlider/js/ion-rangeSlider/ion.rangeSlider.min.js"></script>


    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery.tokenize.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/just4fun.fix.css"/>

    <!-- Range Slider styles -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/ionRangeSlider/css/normalize.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/ionRangeSlider/css/ion.rangeSlider.css"/>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/ionRangeSlider/css/ion.rangeSlider.skinHTML5.css"/>

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

    <!--main content start-->
    <section id="main-content">
        <section class="wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header"><i class="fa fa-tasks"></i> Task</h3>
                    <ol class="breadcrumb">
                        <li><i class="fa fa-home"></i><a href="/"><l:resource key="home"/></a></li>
                        <li><i class="fa fa-rss"></i>Feed</li>
                        <li><i class="fa fa-plus-circle"></i>Add</li>
                    </ol>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-6">
                    <!--collapse start-->
                    <div class="panel-group m-bot20" id="accordion" style="width:1100px;">
                        <div class="panel panel-default j4f-fix">
                            <div class="panel-heading">
                                <h4 class="panel-title j4f-fix-title-background">
                                    <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion"
                                       href="#collapseOne">
                                        Step #1 | Basic settings
                                    </a>
                                </h4>
                            </div>
                            <div id="collapseOne" class="panel-collapse collapse in">
                                <div class="panel-body">
                                    <div class="col-lg-6">
                                        <h4><l:resource key="newpost.selectgroup"/>:</h4>
                                        <select name="groups" id="tokenize_focus" multiple="multiple"
                                                class="tokenize-sample">
                                            <c:forEach items="${owners}" var="owner">
                                                <option value="${owner.wallId}">${owner.name}</option>
                                            </c:forEach>
                                        </select>
                                        <script type="text/javascript">
                                            $('select#tokenize_focus').tokenize({displayDropdownOnFocus: true});
                                        </script>
                                    </div>

                                    <div class="col-lg-6">
                                        <h4><l:resource key="filter"/></h4>
                                        <br>
                                        <table class="col-lg-6">
                                            <tr>
                                                <td style="text-align: left"><span style="width:80px;
                                                    display:inline-block">
                                                <l:resource key="likes"/></span></td>
                                                <td><input id="likes" class="form-control" type="number" name="likes"
                                                           min="0"
                                                           max="1000" style="margin-left:15px;width:100px;"
                                                           value="60"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="text-align: left"><span style="width:80px;
                                                        display: inline-block; margin-top: 5px">
                                                    <l:resource key="reposts"/> </span></td>
                                                <td><input class="form-control" type="number" name="reposts" min="0"
                                                           max="1000"
                                                           style="margin-left:15px;width:100px; margin-top: 5px"
                                                           value="10"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="text-align: left"><span style="width:80px;
                                                        display: inline-block; margin-top: 5px">
                                                    <l:resource key="comments"/> </span></td>
                                                <td><input class="form-control" type="number" name="comments" min="0"
                                                           max="1000"
                                                           style="margin-left:15px;width:100px; margin-top: 5px"
                                                           value="0"/></td>
                                            </tr>
                                        </table>
                                    </div>

                                    <div class="col-lg-7">
                                        <h4><l:resource key="start.time"/></h4>

                                        <div class="interval_number_group">
                                            <br>
                                            <br>
                                            <%--[INTERVAL_SLIDER]--%>
                                            <span>Between </span>
                                            <input type="number" name="interval_min"
                                                   style="margin-left:15px;width:50px;border: none;-webkit-appearance: none;"
                                                   value="3"/>
                                            <span> and </span>
                                            <input type="number" name="interval_max"
                                                   style="margin-left:15px;width:50px;border: none;-webkit-appearance: none;"
                                                   value="10"/>
                                            <span> minutes.</span>
                                        </div>
                                        <div>
                                            <input type="text" id="interval_slider" value="" name="interval"/>
                                        </div>
                                        <script type="text/javascript">
                                            $(document).ready(function () {
                                                var $range = $("#interval_slider"),
                                                        $result_min = $("[name='interval_min']"),
                                                        $result_max = $("[name='interval_max']");
                                                $(".interval_number_group").hide();
//                        $result_max.hide();

                                                var track = function (data) {
                                                    $result_min.val(data.from);
                                                    $result_max.val(data.to);
                                                };

                                                $range.ionRangeSlider({
                                                    hide_min_max: true,
                                                    keyboard: true,
                                                    min: 0,
                                                    max: 120,
                                                    from: 5,
                                                    to: 15,
                                                    type: 'double',
                                                    step: 1,
//                          grid: true,
                                                    postfix: " min",
                                                    decorate_both: false,
                                                    onStart: track,
                                                    onChange: track,
                                                    onFinish: track,
                                                    onUpdate: track
                                                });
                                            });
                                        </script>

                                        <br>
                                        <%--[TIME_SELECT]--%>
                                        <h4><l:resource key="post.delay"/></h4>

                                        <div class="post_delay_number_group">
                                            <span>Between </span>
                                            <input type="number" name="post_delay_min"
                                                   style="margin-left:15px;width:50px;border: none;-webkit-appearance: none;"
                                                   value="10"/>
                                            <span> and </span>
                                            <input type="number" name="post_delay_max"
                                                   style="margin-left:15px;width:50px;border: none;-webkit-appearance: none;"
                                                   value="25"/>
                                            <span> seconds.</span>
                                        </div>
                                        <div>
                                            <input type="text" id="post_delay_slider" value="" name="interval"/>
                                        </div>
                                        <script type="text/javascript">
                                            $(document).ready(function () {
                                                var $range = $("#post_delay_slider"),
                                                        $result_min = $("[name='post_delay_min']"),
                                                        $result_max = $("[name='post_delay_max']");
                                                $(".post_delay_number_group").hide();

                                                var track = function (data) {
                                                    $result_min.val(data.from);
                                                    $result_max.val(data.to);
                                                };

                                                $range.ionRangeSlider({
                                                    hide_min_max: true,
                                                    keyboard: true,
                                                    min: 0,
                                                    max: 120,
                                                    from: 5,
                                                    to: 15,
                                                    type: 'double',
                                                    step: 1,
//                          grid: true,
                                                    postfix: " sec",
                                                    decorate_both: false,
                                                    onStart: track,
                                                    onChange: track,
                                                    onFinish: track,
                                                    onUpdate: track
                                                });
                                            });
                                        </script>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title j4f-fix-title-background">
                                    <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion"
                                       href="#collapseTwo">
                                        Step #2 | Advanced settings
                                    </a>
                                </h4>
                            </div>
                            <div id="collapseTwo" class="panel-collapse collapse">
                                <div class="panel-body">
                                    <div class="col-md-8">
                                        <h4><l:resource key="content.type"/></h4>

                                        <div class="col-md-6">
                                            <input type="checkbox" name="content_type" value="text" checked> <l:resource
                                                key="text"/> <br>
                                            <input type="checkbox" name="content_type" value="photo" checked>
                                            <l:resource key="photo"/> <br>
                                            <input type="checkbox" name="content_type" value="audio" checked>
                                            <l:resource key="audio"/> <br>
                                            <input type="checkbox" name="content_type" value="video" checked>
                                            <l:resource key="video"/> <br>
                                        </div>

                                        <div class="col-md-6">
                                            <input type="checkbox" name="content_type" value="repost" checked>
                                            <l:resource key="deep.copy"/> <br>
                                            <input type="checkbox" name="content_type" value="link"> <l:resource
                                                key="links"/> <br>
                                            <input type="checkbox" name="content_type" value="hashtag"> <l:resource
                                                key="hashtags"/> <br>
                                            <input type="checkbox" name="content_type" value="docs" checked> <l:resource
                                                key="documents"/> <br>
                                            <input type="checkbox" name="content_type" value="page"> <l:resource
                                                key="pages"/> <br>
                                        </div>
                                        <div class="col-md-10">
                                            <h4><l:resource key="hashtags"/></h4>
                                            <input type="text" name="wordsinput" id="tagsinput"
                                                   class="tagsinput"
                                                   value="socialspider"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--collapse end-->
                    <div class="col-lg-offset-2 col-lg-9">
                        <button id="task-save" type="submit" class="btn btn-primary"><l:resource
                                key="newpost.save"/></button>
                        <button type="reset" class="btn btn-default"><l:resource key="newpost.reset"/></button>
                    </div>
                </div>

            </div>
        </section>
    </section>
</section>
<script>

    $("#likes").keyup(function(data) {
        alert(data.relatedTarget)
    });

    $(document).ready(function () {
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.open('GET', '/owner?action=getOwnerWall', true);
        xmlhttp.onreadystatechange = function () {

            if (xmlhttp.readyState == 4) {
                var response = JSON.parse(xmlhttp.responseText);
                var list = $("#tokenize_focus");
                list.empty();
                list.data('tokenize').clear();
                for (var i = 0; i < response.owner.length; i++) {
                    list.append('<option value="' + response.owner[i].id + '">' + response.owner[i].name + '</option>');
                }
            }
        };
        xmlhttp.send();
    });
</script>
<!-- javascripts -->
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<!-- nice scroll -->
<script src="${pageContext.request.contextPath}/js/jquery.scrollTo.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.nicescroll.js" type="text/javascript"></script>
<!-- gritter -->

<!-- custom gritter script for this page only-->
<script src="${pageContext.request.contextPath}/js/gritter.js" type="text/javascript"></script>
<!--custome script for all page-->
<script src="${pageContext.request.contextPath}/js/scripts.js"></script>

<!--custom tagsinput-->
<script src="${pageContext.request.contextPath}/js/jquery.tagsinput.js"></script>
<script src="${pageContext.request.contextPath}/js/form-component.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.tokenize.js"></script>

</body>
</html>