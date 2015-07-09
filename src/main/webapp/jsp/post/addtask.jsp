<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
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
  <%--<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>--%>

  <!-- javascripts -->
  <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.tokenize.task.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/save.task.js"></script>
  <script src="${pageContext.request.contextPath}/assets/ionRangeSlider/js/ion-rangeSlider/ion.rangeSlider.min.js"></script>


  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery.tokenize.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/just4fun.fix.css" />

  <!-- Range Slider styles -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/ionRangeSlider/css/normalize.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/ionRangeSlider/css/ion.rangeSlider.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/ionRangeSlider/css/ion.rangeSlider.skinFlat.css" />
  <!-- HTML5 shim and Respond.js IE8 support of HTML5 -->
  <!--[if lt IE 9]>
  <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
  <!--<script src="${pageContext.request.contextPath}/js/respond.min.js"></script>-->
  <script src="${pageContext.request.contextPath}/js/lte-ie7.js"></script>
  <![endif]-->


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

  <jsp:include page="../pagecontent/header.jsp" />
  <jsp:include page="../pagecontent/sidebar.jsp" />

  <!--main content start-->
  <section id="main-content">
    <section class="wrapper">
      <div class="row">
        <div class="col-lg-12">
          <ol class="breadcrumb">
            <li><i class="fa fa-home"></i><a href="index.html"><l:resource key="home"/></a></li>
            <li><i class="fa fa-desktop"></i><l:resource key="task"/></li>
            <li><i class="fa fa-list-alt"></i><l:resource key="addnewtask"/></li>
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
                    <l:resource key="addtask.step1"/>
                  </a>
                </h4>
              </div>
              <div id="collapseOne" class="panel-collapse collapse in">
                <div class="panel-body">
                  <div class="col-lg-6">
                    <h4><l:resource key="select.group.to.grab"/></h4>
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
                    <h4><l:resource key="select.group.to.post"/></h4>
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

                    <h4><l:resource key="select.grabbing.mode"/></h4>
                    <br>
                    <input type="radio" name="grabbing_mode" value="total" checked> <l:resource key="grabbing.mode1"/> <br>
                    <input type="radio" name="grabbing_mode" value="per_group"> <l:resource key="grabbing.mode2"/> <br>
                    <div class="post_count_number_group">
                    <span><l:resource key="amount.of.posts"/>  </span>
                    <input type="number" name="post_count" style="width:40px;border: none;-webkit-appearance: none; " value="1">
                      </div>
                    <div>
                      <input type="text" id="post_count_slider" value="" name="interval" />
                    </div>
                    <script type="text/javascript">
                      $(document).ready(function () {
                        var slider;
                        var $range_post_count = $("#post_count_slider"),
                                $post_count_input = $("[name='post_count']");
                        $(".post_count_number_group").hide();

                        var oldFrom = 0;
                        var oldFromPostfix = " post.";
                        var track = function (data) {
                          $post_count_input.val(data.from);

//                          var from = data.from;
//                          var needChange = false;
//                          if(from != oldFrom){
//                            oldFrom = from;
//                            needChange = true;
//                          }
//
//                          if(needChange){
//                            var newPostfix;
//                            if(from > 1){
//                              newPostfix = "  posts.";
//                            }
//                            else{
//                              newPostfix = "  post.";
//                            }
//                            if(oldFromPostfix != newPostfix){
//                              oldFromPostfix = newPostfix;
//                              slider.update({
//                                postfix: newPostfix
//                              });
//                            }
//
//                          }
                        };


                        $range_post_count.ionRangeSlider({
                          hide_min_max: true,
                          keyboard: true,
                          min: 0,
                          max: 10,
                          from_min: 1,
                          from_max: 8,
                          from: 1,
                          step: 1,
                          decorate_both: false,
                          onStart: track,
                          onChange: track,
                          onFinish: track,
                          onUpdate: track
                        });
                        slider = $range_post_count.data("ionRangeSlider");
                      });
                    </script>


                  </div>

                  <div class="col-lg-6">
                    <h4><l:resource key="grabbing.type"/> </h4>
                    <br>
                    <input type="radio" name="grabbing_type" value="begin" checked> <l:resource key="grabbing.type1"/> <br>

                    <input type="radio" name="grabbing_type" value="end"> <l:resource key="grabbing.type2"/> <br>

                    <input type="radio" name="grabbing_type" value="random"> <l:resource key="grabbing.type3"/> <br>

                    <input type="radio" name="grabbing_type" value="new"> <l:resource key="grabbing.type4"/> <br>
                    <br>
                    <h4><l:resource key="filter"/> </h4>
                    <br>
                    <span style="width:80px;display: inline-block"><l:resource key="likes"/> </span>
                    <input type="number" name="likes" style="margin-left:15px;width:50px;border: none;-webkit-appearance: none;" value="60" />
                    <br>
                    <span style="width:80px;display: inline-block"> <l:resource key="reposts"/> </span>
                    <input type="number" name="reposts" style="margin-left:15px;width:50px;border: none;-webkit-appearance: none;" value="10" />
                    <br>
                    <span style="width:80px;display: inline-block"> <l:resource key="comments"/> </span>
                    <input type="number" name="comments" style="margin-left:15px;width:50px;border: none;-webkit-appearance: none;" value="0" />
                    <br>
                  </div>


                </div>
              </div>
            </div>
            <div class="panel panel-default">
              <div class="panel-heading">
                <h4 class="panel-title">
                  <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
                    <l:resource key="addtask.step2"/>
                  </a>
                </h4>
              </div>
              <div id="collapseTwo" class="panel-collapse collapse">
                <div class="panel-body">
                  <div class="col-lg-6">
                    <h4> <l:resource key="posting.type"/> </h4>
                    <input type="radio" name="posting_type" value="COPY" checked> <l:resource key="copying"/>
                    <input type="radio" name="posting_type" value="REPOST" style="margin-left:15px;"> <l:resource key="reposts"/>

                    <h4><l:resource key="repeat"/> </h4>
                    <input type="radio" name="repeat" value="REPEAT_DISABLE" checked> <l:resource key="do.not.repeat"/> <br>
                    <input type="radio" name="repeat" value="REPEAT_ON_TIME"> <l:resource key="repeat.every"/>
                    <input type="number" name="repeat_days" style="width:40px;border: none;-webkit-appearance: none; "> <l:resource key="days"/>

                  </div>
                  <div class="col-lg-6">
                    <h4><l:resource key="start.time"/> </h4>
                    <input type="radio" name="start_time" value="INTERVAL" checked> <l:resource key="interval"/>
                    <input type="radio" name="start_time" value="SCHEDULE" style="margin-left:15px;" disabled > <l:resource key="schedule"/>
                    <div class="interval_number_group">
                      <br>
                      <br>
                    <%--[INTERVAL_SLIDER]--%>
                      <span>Between </span>
                      <input type="number" name="interval_min" style="margin-left:15px;width:50px;border: none;-webkit-appearance: none;" value="3" />
                      <span> and </span>
                      <input type="number" name="interval_max" style="margin-left:15px;width:50px;border: none;-webkit-appearance: none;" value="10" />
                      <span> minutes.</span>
                    </div>
                    <div>
                      <input type="text" id="interval_slider" value="" name="interval" />
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
                    <h4><l:resource key="work.time"/> </h4>
                    <input type="radio" name="work_time" value="ROUND_DAILY" checked> <l:resource key="around.the.clock"/>
                    <input type="radio" name="work_time" value="DAY_PERIOD" style="margin-left:15px;" disabled> <l:resource key="select.time"/>

                    <br>
                    <br>
                    <%--[TIME_SELECT]--%>
                    <h4><l:resource key="post.delay"/> </h4>
                    <div class="post_delay_number_group">
                      <span>Between </span>
                      <input type="number" name="post_delay_min" style="margin-left:15px;width:50px;border: none;-webkit-appearance: none;" value="10" />
                      <span> and </span>
                      <input type="number" name="post_delay_max" style="margin-left:15px;width:50px;border: none;-webkit-appearance: none;" value="25" />
                      <span> seconds.</span>
                    </div>
                    <div>
                      <input type="text" id="post_delay_slider" value="" name="interval" />
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
                <h4 class="panel-title">
                  <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
                    <l:resource key="addtask.step3"/>
                  </a>
                </h4>
              </div>
              <div id="collapseThree" class="panel-collapse collapse">
                <div class="panel-body">
                  <div class="col-lg-6">
                    <h4><l:resource key="content.type"/></h4>
                    <div class="col-lg-4">
                      <input type="checkbox" name="content_type" value="text" checked> <l:resource key="text"/> <br>
                      <input type="checkbox" name="content_type" value="photo" checked> <l:resource key="photo"/> <br>
                      <input type="checkbox" name="content_type" value="audio" checked> <l:resource key="audio"/> <br>
                      <input type="checkbox" name="content_type" value="video" checked> <l:resource key="video"/> <br>
                    </div>

                    <div class="col-lg-4">
                      <input type="checkbox" name="content_type" value="repost" checked> <l:resource key="deep.copy"/> <br>
                      <input type="checkbox" name="content_type" value="link"> <l:resource key="links"/> <br>
                      <input type="checkbox" name="content_type" value="hashtag"> <l:resource key="hashtags"/> <br>
                      <input type="checkbox" name="content_type" value="docs" checked> <l:resource key="documents"/> <br>
                      <input type="checkbox" name="content_type" value="page"> <l:resource key="pages"/> <br>
                    </div>

                  </div>
                  <div class="col-lg-3">
                    <h4><l:resource key="actions.after.posting"/></h4>
                    <input type="radio" name="actions" value="LIKE"> <l:resource key="like"/> <br>
                    <input type="radio" name="actions" value="REPOST"> <l:resource key="reposts"/> <br>
                    <input type="radio" name="actions" value="DO_NOTHING" checked> <l:resource key="do.nothing"/> <br>
                  </div>

                </div>
              </div>
            </div>
            <div class="panel panel-default">
              <div class="panel-heading">
                <h4 class="panel-title">
                  <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#collapseFour">
                    <l:resource key="addtask.step4"/>
                  </a>
                </h4>
              </div>
              <div id="collapseFour" class="panel-collapse collapse">
                <div class="panel-body">
                  <div class="col-lg-6">
                    <h4><l:resource key="hashtags"/></h4>

                    <input name="wordsinput" id="tagsinput" class="tagsinput" value="socialspider, posting" />
                    <!--
                    <br>
                    <input type="radio" name="stop_words" value="1"> Skip
                    <input type="radio" name="stop_words" value="2" style="margin-left:15px;"> Delete
                    <input type="radio" name="stop_words" value="3" style="margin-left:15px;"> Delete sentence
                    -->
                  </div>
                  <div class="col-lg-3">
                    <h4><l:resource key="add.text.to.post"/></h4>
										<textarea class="form-control" name="addtext" style="width:500px;height:120px;">
										</textarea>

                  </div>

                </div>
              </div>
            </div>

          </div>
          <!--collapse end-->
          <div class="col-lg-offset-2 col-lg-9">
            <button id="task-save" type="submit" class="btn btn-primary"><l:resource key="newpost.save"/></button>
            <button type="reset" class="btn btn-default"><l:resource key="newpost.reset"/></button>
          </div>
        </div>

      </div>
    </section>
  </section>
  <!--main content end-->
</section>
<!-- container section end -->

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
<script src="${pageContext.request.contextPath}/js/jquery.tokenize.task.js"></script>
<!--custom tagsinput-->
<script src="${pageContext.request.contextPath}/js/jquery.tagsinput.js"></script>
<script src="${pageContext.request.contextPath}/js/form-component.js"></script>

</body>
</html>

