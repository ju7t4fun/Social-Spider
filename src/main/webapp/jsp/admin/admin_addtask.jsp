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
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style-responsive.css" rel="stylesheet"/>
    <%--<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>--%>

    <!-- javascripts -->
    <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.tokenize.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery.tokenize.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/save.admintask.js"></script>
    <script src="${pageContext.request.contextPath}/assets/ionRangeSlider/js/ion-rangeSlider/ion.rangeSlider.js"></script>
    <script src="${pageContext.request.contextPath}/js/j4f-number-cases.js"></script>

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
                    <h3 class="page-header"><i class="fa fa-tasks"></i> <l:resource key="addnewtask"/></h3>
                    <ol class="breadcrumb">
                        <li><i class="fa fa-home"></i><a href="/"><l:resource key="home"/></a></li>
                        <li><i class="fa fa-rss"></i><l:resource key="feed"/></li>
                        <li><i class="fa fa-plus-circle"></i><span id="task-id-loc">
                        <c:choose>
                            <c:when test="${task_id != '0'}">
                                Task #${task_id}
                            </c:when>
                            <c:otherwise>
                                <l:resource key="addnewtask"/>
                            </c:otherwise>
                        </c:choose></span></li>
                    </ol>
                </div>
            </div>
            <input type="hidden" name="task_id" value="${task_id}">
            <div class="row">
                <div class="col-lg-6">
                    <!--collapse start-->
                    <div class="panel-group m-bot20" id="accordion" style="width:1100px;">
                        <div class="panel panel-default j4f-fix">
                            <div class="panel-heading">
                                <h4 class="panel-title j4f-fix-title-background">
                                    <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion"
                                       href="#collapseOne">
                                        <l:resource key="basic.settings"/>
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
                                                <%--<option value="${owner.wallId}">${owner.name}</option>--%>
                                                <c:choose>
                                                    <c:when test="${wall.selected eq 'true'}">
                                                        <option value="${owner.wallId}" selected="selected">${owner.name}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${owner.wallId}">${owner.name}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                        <script type="text/javascript">
                                            $('select#tokenize_focus').tokenize({displayDropdownOnFocus: true});
                                        </script>
                                        <jsp:include page="../task/edit-task-start-time-group.jsp"/>
                                        <jsp:include page="../task/edit-task-post-delay-group.jsp"/>
                                    </div>
                                    <div class="col-lg-6">
                                        <jsp:include page="../task/edit-task-filter-group.jsp"/>
                                    </div>


                                </div>
                            </div>
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title j4f-fix-title-background">
                                    <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion"
                                       href="#collapseTwo">
                                        <l:resource key="advanced.settings"/>
                                    </a>
                                </h4>
                            </div>
                            <div id="collapseTwo" class="panel-collapse collapse">
                                <div class="panel-body">
                                    <div class="col-lg-6">
                                        <jsp:include page="../task/edit-task-content-type-group.jsp"/>
                                        </div>
                                        <div class="col-lg-6">
                                            <jsp:include page="../task/edit-task-tag-group.jsp"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--collapse end-->
                    <div class="col-lg-offset-2 col-lg-9">
                        <button style="margin-left: -183px;" id="task-save" type="submit" class="btn
                        btn-primary"><l:resource
                                key="newpost.save"/></button>

                    </div>
                </div>
            </div>
        </section>
    </section>
</section>
<script>

    $("#likes").keyup(function (data) {
        var v = this.value;
        if ($.isNumeric(v) === false) {
            this.value = this.value.slice(0, -1);
        }
    });
    $("#reposts").keyup(function (data) {
        var v = this.value;
        if ($.isNumeric(v) === false) {
            this.value = this.value.slice(0, -1);
        }
    });
    $("#comments").keyup(function (data) {
        var v = this.value;
        if ($.isNumeric(v) === false) {
            this.value = this.value.slice(0, -1);
        }
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

<script src="${pageContext.request.contextPath}/js/bootstrap-number-input.js"></script>

<script type="text/javascript">
    j4fBundlePut('x', "${bundle.x}")
    $(document).ready(function () {
        $(".j4f-disable-at-admin").hide();
        $(".show-when-jquery-unsupported").hide();
        $(".show-when-jquery-supported").show();
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