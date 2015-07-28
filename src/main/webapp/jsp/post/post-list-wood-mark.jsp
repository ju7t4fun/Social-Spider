<%--
  Created by IntelliJ IDEA.
  User: hell-engine
  Date: 7/18/2015
  Time: 3:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
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


  <link href="${pageContext.request.contextPath}/css/toastr.css" rel="stylesheet" type="text/css"/>
  <script src="${pageContext.request.contextPath}/js/toastr.js"></script>



  <!-- CSS Reset -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/bower_components/normalize.css/normalize.css">
  <!-- Global CSS for the page and tiles -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/wood-mark/css/main.css">
  <!-- Custom CSS Wood mark -->
  <link href="${pageContext.request.contextPath}/css/j4f-wood-box.css" rel="stylesheet"/>

  <script type="text/javascript">
    // При завантаженні сторінки
    setTimeout(function () {
      if (${toastr_notification!=null}) {
        var args = "${toastr_notification}".split("|");
        toastrNotification(args[0], args[1]);
      }
    }, 500);
  </script>
    <script src="${pageContext.request.contextPath}/assets/bower/bower_components/moment/min/moment-with-locales.js"></script>
    <script src="${pageContext.request.contextPath}/js/post-list-moment.js"></script>
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
            <li><i class="fa fa-home"></i><a href="${pageContext.request.contextPath}/"><l:resource key="home"/></a></li>
            <li><i class="fa fa-desktop"></i><l:resource key="post"/></li>
            <li><i class="fa fa-list-alt"></i><l:resource key="posted"/></li>
          </ol>
        </div>
      </div>



      <div class="row">
        <div class="col-lg-12">
          <ul id="wood-mark-container" class="tiles-wrap animated">
            <!-- These are our grid blocks -->
              <c:forEach items="${posts}" var="post">
                  <li>
                      <div class="wood-message wood-box" onclick="viewPost('${post.mid}')" data-toggle="modal"
                           data-target="#myModal"><c:out value="${post.message}" />
                      </div>
                      <c:if test="${post.no_att != 'true'}">
                          <div class="wood-box wood-attachment" style="font-size: 13px" onclick="viewPost('${post.mid}')"
                               data-toggle="modal" data-target="#myModal">
                              <c:if test="${post.photo != 0}">
                                <span><i class="fa fa-picture-o"></i> <span>${post.photo}</span></span>
                              </c:if>
                              <c:if test="${post.audio != 0}">
                                  <span><i class="fa fa-music"></i> <span>${post.audio}</span></span>
                              </c:if>
                              <c:if test="${post.video != 0}">
                                  <span><i class="fa fa-film"></i> <span>${post.video}</span></span>
                              </c:if>
                              <c:if test="${post.docs != 0}">
                                  <span><i class="fa fa-file-text-o"></i> <span>${post.docs}</span></span>
                              </c:if>
                              <c:if test="${post.other != 0}">
                                  <span><i class="fa fa-file-text-o"></i> <span>${post.other}</span></span>
                              </c:if>
                          </div>
                      </c:if>
                      <div class="wood-box wood-info-box">
                          <div class="wood-2-col">
                              <div><l:resource key="wood.box.to"/> <a target="_blank" href="${post.to_link}">${post.to_name}</a></div>
                              <div><l:resource key="wood.box.by"/> <a target="_blank" href="${post.by_link}">${post.by_name}</a></div>
                          </div>
                          <div class="wood-2-col">
                              <div class="wood-status">
                                  <span class="wood-post-state ${post.state == 'posting'?'run':''}">
                                      <c:choose>
                                          <c:when test="${post.state == 'posted'}">
                                              <l:resource key="wood.box.posted"/>
                                          </c:when>
                                          <c:when test="${post.state == 'posting'}">
                                              <l:resource key="wood.box.posting"/>
                                          </c:when>
                                          <c:when test="${post.state == 'queered'}">
                                              <l:resource key="wood.box.queered"/>
                                          </c:when>
                                      </c:choose>
                                  </span>
                                  <span class="wood-timer" time_id="${post.pid}"></span>
                              </div>
                              <script>
                                  addMoment(${post.pid}, moment(parseInt('${post.unix_time_ms}')));
                              </script>
                          </div>
                      </div>
                      <c:if test="${post.state == 'posted'}">
                          <div class="wood-box wood-stat">
                              <a href="javascript:openPostStats('${post.pid}')" onclick="openPostStats('${post.pid}')">
                                  <i class="fa fa-heart"></i> <span>0</span>
                                  <i class="fa fa-bullhorn"></i> <span>0</span>
                                  <i class="fa fa-comment"></i> <span>0</span>
                              </a>
                          </div>
                      </c:if>
                  </li>
              </c:forEach>
            <!-- End of grid blocks -->
          </ul>
        </div>

        <!-- include jQuery -->
        <%--<script src="${pageContext.request.contextPath}/assets/bower_components/jquery/dist/jquery.min.js"></script>--%>

        <!-- Include the imagesLoaded plug-in -->
        <script src="${pageContext.request.contextPath}/assets/bower_components/imagesloaded/imagesloaded.pkgd.min.js"></script>

        <!-- Include the plug-in -->
        <script src="${pageContext.request.contextPath}/assets/wood-mark/wookmark.js"></script>

        <!-- Once the page is loaded, initalize the plug-in. -->
        <script type="text/javascript">
            var elements_not_end = true, sync_ajax = true, count = 30;
          (function ($) {
            // Instantiate wookmark after all images have been loaded
            var wookmark,
                    container = '#wood-mark-container',
                    $container = $(container),
                    $window = $(window),
                    $document = $(document);

            imagesLoaded(container, function () {
              wookmark = new Wookmark(container, {
                offset: 10, // Optional, the distance between grid items
                itemWidth: 260 // Optional, the width of a grid item

              });
            });

            /**
             * When scrolled all the way to the bottom, add more tiles
             */
            function onScroll() {
              // Check if we're within 100 pixels of the bottom edge of the broser window.
              var winHeight = window.innerHeight ? window.innerHeight : $window.height(), // iphone fix
                      closeToBottom = ($window.scrollTop() + winHeight > $document.height() - 100);

                if (closeToBottom && sync_ajax && elements_not_end) {
                    sync_ajax = false;
                    $.get("${pageContext.request.contextPath}/post?action=get_board&offset=" + count)
                            .done(function (data) {
                                count += 30;
                                console.log(data.tiles);
                                var firstTen =  [];
                                for(var i=0;i<data.tiles.length; i++) {
                                    var post = data.tiles[i];
                                    var $li = $("<li></li>");
                                    {
                                        var div_text = '<div class="wood-message wood-box" onclick="viewPost(' + post.mid + ')" data-toggle="modal"' +
                                                'data-target="#myModal">' + post.message + '</div>';
                                        $li.append(div_text);
                                    }
                                    if (post.no_att != 'true') {
                                        var div_att = ' <div class="wood-box wood-attachment" style="font-size: 13px" onclick="viewPost(' + post.mid + ')"' +
                                            'data-toggle="modal" data-target="#myModal"></div>';

                                        var span_photo, span_audio, span_video, span_docs, span_other;
                                        //console.log([typeof  span_audio, typeof  span_photo, typeof span_other]);
                                        if (parseInt(post.photo) != 0)
                                            span_photo = '<span><i class="fa fa-picture-o"></i> <span> ' + post.photo + '</span></span>';
                                        if (parseInt(post.audio) != 0)
                                            span_audio = '<span><i class="fa fa-music"></i> <span> ' + post.audio + '</span></span>';
                                        if (parseInt(post.video) != 0)
                                            span_video = '<span><i class="fa fa-film"></i> <span> ' + post.video + '</span></span>';
                                        if (parseInt(post.docs) != 0)
                                            span_docs = '<span><i class="fa fa-file-text-o"></i> <span> ' + post.docs + '</span></span>';
                                        if (parseInt(post.other) != 0)
                                            span_other = '<span><i class="fa fa-file-text-o"></i> <span> ' + post.other + '</span></span>';
                                        //console.log([typeof  span_audio, typeof  span_photo, typeof span_other]);
                                        var $div_att = $(div_att);
                                        if(typeof span_photo != 'undefined')$div_att.append(span_photo);
                                        if(typeof span_audio != 'undefined')$div_att.append(span_audio);
                                        if(typeof span_video != 'undefined')$div_att.append(span_video);
                                        if(typeof span_docs != 'undefined')$div_att.append(span_docs);
                                        if(typeof span_other != 'undefined')$div_att.append(span_other);

                                        $li.append($div_att);
                                    }
                                    {
                                        var $div_info = $('<div class="wood-box wood-info-box"></div>');
                                        var div_to, div_by;
                                        div_to = '<div><l:resource key="wood.box.to"/> <a target="_blank" href="' + post.to_link + '">' + post.to_name + '</a></div>';
                                        div_by = '<div><l:resource key="wood.box.by"/> <a target="_blank" href="' + post.by_link + '">' + post.by_name + '</a></div>';


                                        var state_text;
                                        if (post.state == 'posted')state_text = '<l:resource key="wood.box.posted"/> ';
                                        else if (post.state == 'posting')state_text = '<l:resource key="wood.box.posting"/> ';
                                        else if (post.state == 'queered')state_text = '<l:resource key="wood.box.queered"/> ';

                                        var $span_state = $('<span class="wood-post-state"></span> '),
                                                $span_timer = $('<span class="wood-timer"></span>');
                                        if (post.state == 'posting')$span_state.addClass('run');
                                        $span_state.append(state_text);

                                        $span_timer.attr("time_id", post.pid);
                                        addMoment(post.pid, moment(parseInt(post.unix_time_ms)));

                                        $div_info.append('<div class="wood-2-col">' + div_to + div_by + '</div>');
                                        $div_info.append($('<div class="wood-2-col"></div>').append(
                                                $('<div class="wood-status"></div>').append($span_state).append($span_timer)
                                        ));
                                        $li.append($div_info);
                                    }
                                    if(post.state == 'posted'){
                                        var div_stat = '<div class="wood-box wood-stat">'+
                                            '<a href="javascript:openPostStats('+post.pid+')" onclick="openPostStats('+post.pid+')">'+
                                            '<i class="fa fa-heart"></i> <span>0</span> ' +
                                            '<i class="fa fa-bullhorn"></i> <span>0</span> ' +
                                            '<i class="fa fa-comment"></i> <span>0</span>' +
                                            '</a>' +
                                            '</div>';
                                        $li.append(div_stat);
                                    }
                                    console.log($div_info);

                                    firstTen.push($li)
                                }
                                // Get the first then items from the grid, clone them, and add them to the bottom of the grid
//                                var $items = $('li', $container),$firstTen = $items.slice(0, 10).clone().css('opacity', 0);
                                $container.append(firstTen);

                                wookmark.initItems();
                                wookmark.layout(true, function () {
                                    // Fade in items after layout
                                    setTimeout(function () {
                                        firstTen.css('opacity', 1);
                                    }, 300);
                                });
                                sync_ajax = true;
                            })
                            .fail(function (jqXHR) {
                                if (jqXHR.status == 401) {
                                    toastrNotification("error", "Death session. Please ReLogin.");
                                } else {
                                    toastrNotification("error", "Query  error.");
                                }
                            });


                }
            }
              // Capture scroll event.
            $window.bind('scroll.wookmark', onScroll);
          })(jQuery);
        </script>
      </div>
    </section>
  </section>
</section>
<%--Вікно статистики поста--%>
<jsp:include page="../post/modal_stat_info.jsp"/>

<%--Вікно публікації--%>
<jsp:include page="../post/modal_post_info.jsp"/>

</body>
</html>
