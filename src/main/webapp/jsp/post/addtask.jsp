<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Sasha
  Date: 16.06.2015
  Time: 15:15
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
  <!--external css-->
  <!-- font icon -->
  <link href="${pageContext.request.contextPath}/css/elegant-icons-style.css" rel="stylesheet" />
  <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet" />
  <!-- Custom styles -->
  <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/style-responsive.css" rel="stylesheet" />
  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.tokenize.task.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/save.task.js"></script>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery.tokenize.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/just4fun.fix.css" />
  <!-- HTML5 shim and Respond.js IE8 support of HTML5 -->
  <!--[if lt IE 9]>
  <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
  <!--<script src="${pageContext.request.contextPath}/js/respond.min.js"></script>-->
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
      <div class="row">
        <div class="col-lg-12">
          <ol class="breadcrumb">
            <li><i class="fa fa-home"></i><a href="index.html">Home</a></li>
            <li><i class="fa fa-desktop"></i>UI Fitures</li>
            <li><i class="fa fa-list-alt"></i>Components</li>
          </ol>
        </div>
      </div>

      <div class="row">
        <div class="col-lg-6">
          <!--collapse start-->
          <div class="panel-group m-bot20" id="accordion" style="width:1100px;">
            <div class="panel panel-default j4f-fix">
              <div class="panel-heading">
                <h4 class="panel-title">
                  <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
                    Step #1 | Basic Settings - Groups & Grabbing Type
                  </a>
                </h4>
              </div>
              <div id="collapseOne" class="panel-collapse collapse in">
                <div class="panel-body" style="height:330px;">
                  <div class="col-lg-6">
                    <h4>Select group from which you are grabbing posts:</h4>
                    <select id="tokenize_focus_source_walls" multiple="multiple" class="tokenize-sample" style="width:350px;">
                      <c:forEach items="${sourceWalls}" var="wall">
                        <option value="${wall.id}"><c:out value="${wall.text}"/></option>
                      </c:forEach>

                    </select>

                    <script type="text/javascript">
                      $('select#tokenize_focus_source_walls').tokenize({
                        datas: 'select',
                        newElements:'false',
                        displayDropdownOnFocus: true
                      });
                    </script>
                    <h4>Select group to post:</h4>
                    <select id="tokenize_focus_destination_walls" multiple="multiple" class="tokenize-sample" style="width:350px;">
                      <c:forEach items="${destinationWalls}" var="wall">
                        <option value="${wall.id}"><c:out value="${wall.text}"/></option>
                      </c:forEach>
                    </select>
                    <script type="text/javascript">
                      $('select#tokenize_focus_destination_walls').tokenize({
                        displayDropdownOnFocus:true,
                        newElements:'false'
                      });
                    </script>
                  </div>

                  <div class="col-lg-6">
                    <h4>Type of Grabbing: </h4>
                    <br>
                    <input type="radio" name="grabbing_type" value="1" checked> Grab posts from the start of the wall. <br>
                    <br>
                    <input type="radio" name="grabbing_type" value="2"> Grab posts from the end of the wall. <br>
                    <br>
                    <input type="radio" name="grabbing_type" value="3"> Grab random post. <br>
                    <br>
                    <input type="radio" name="grabbing_type" value="4"> Grab new posts only. <br>
                  </div>


                </div>
              </div>
            </div>
            <div class="panel panel-default">
              <div class="panel-heading">
                <h4 class="panel-title">
                  <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
                    Step #2 | Basic Settings - Posting Settings
                  </a>
                </h4>
              </div>
              <div id="collapseTwo" class="panel-collapse collapse">
                <div class="panel-body">
                  <div class="col-lg-6">
                    <h4>Posting type: </h4>
                    <input type="radio" name="posting_type" value="COPY" checked> Copying
                    <input type="radio" name="posting_type" value="REPOST" style="margin-left:15px;"> Reposting

                    <h4>Repeat: </h4>
                    <input type="radio" name="repeat" value="1" checked> Do not repeat posts. <br>
                    <input type="radio" name="repeat" value="2"> Post can be repeated every
                    <input type="number" name="repeat_days" style="width:40px;border: none;-webkit-appearance: none; "> days.

                  </div>
                  <div class="col-lg-6">
                    <h4>Start Time: </h4>
                    <input type="radio" name="start_time" value="INTERVAL" checked> Interval
                    <input type="radio" name="start_time" value="SCHEDULE" style="margin-left:15px;"> Schedule
                    <br>[INTERVAL_SLIDER]
                    <br>
                    <h4>Work Time: </h4>
                    <input type="radio" name="work_time" value="ROUND_DAILY" checked> Around the Clock
                    <input type="radio" name="work_time" value="DAY_PERIOD" style="margin-left:15px;"> Select Time
                    <br>[TIME_SELECT]
                  </div>
                </div>
              </div>
            </div>
            <div class="panel panel-default">
              <div class="panel-heading">
                <h4 class="panel-title">
                  <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
                    Step #3 | Advanced Settings - Content Type & Actions
                  </a>
                </h4>
              </div>
              <div id="collapseThree" class="panel-collapse collapse">
                <div class="panel-body">
                  <div class="col-lg-6">
                    <h4>Content Type</h4>
                    <div class="col-lg-3">
                      <input type="checkbox" name="content_type" value="text" checked> Text <br>
                      <input type="checkbox" name="content_type" value="photo" checked> Photo <br>
                      <input type="checkbox" name="content_type" value="audio" checked> Audio <br>
                      <input type="checkbox" name="content_type" value="video" checked> Video <br>
                    </div>

                    <div class="col-lg-3">
                      <input type="checkbox" name="content_type" value="repost"> Reposts <br>
                      <input type="checkbox" name="content_type" value="link"> Links <br>
                      <input type="checkbox" name="content_type" value="hashtag"> Hashtags <br>
                      <input type="checkbox" name="content_type" value="docs"> Documents <br>
                      <input type="checkbox" name="content_type" value="page"> Pages <br>
                    </div>

                  </div>
                  <div class="col-lg-3">
                    <h4>Actions After Posting</h4>
                    <input type="radio" name="actions" value="LIKE"> Like <br>
                    <input type="radio" name="actions" value="REPOST"> Repost <br>
                    <input type="radio" name="actions" value="DO_NOTHING" checked> Do nothing <br>
                  </div>

                </div>
              </div>
            </div>
            <div class="panel panel-default">
              <div class="panel-heading">
                <h4 class="panel-title">
                  <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#collapseFour">
                    Step #4 | Advanced Settings - Text Settings
                  </a>
                </h4>
              </div>
              <div id="collapseFour" class="panel-collapse collapse">
                <div class="panel-body">
                  <div class="col-lg-6">
                    <h4>Hash Tags</h4>

                    <input name="wordsinput" id="tagsinput" class="tagsinput" value="Tag1, Tag2, Tag3, Tag4, Tag5" />
                    <!--
                    <br>
                    <input type="radio" name="stop_words" value="1"> Skip
                    <input type="radio" name="stop_words" value="2" style="margin-left:15px;"> Delete
                    <input type="radio" name="stop_words" value="3" style="margin-left:15px;"> Delete sentence
                    -->
                  </div>
                  <div class="col-lg-3">
                    <h4>Add Text to Post</h4>
										<textarea class="form-control" name="addtext" style="width:500px;height:120px;">
										</textarea>

                  </div>

                </div>
              </div>
            </div>

          </div>
          <!--collapse end-->
          <div class="col-lg-offset-2 col-lg-9">
            <button id="task-save" type="submit" class="btn btn-primary">Save</button>
            <button type="reset" class="btn btn-default">Reset</button>
          </div>
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
<script src="${pageContext.request.contextPath}/js/jquery.tokenize.task.js"></script>
<!--custom tagsinput-->
<script src="${pageContext.request.contextPath}/js/jquery.tagsinput.js"></script>
<script src="${pageContext.request.contextPath}/js/form-component.js"></script>

</body>
</html>

