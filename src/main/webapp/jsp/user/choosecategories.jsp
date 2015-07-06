<%--
  Created by IntelliJ IDEA.
  User: Орест
  Date: 7/4/2015
  Time: 1:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<html>
<head>
    <title></title>
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
    <%--<script src="${pageContext.request.contextPath}/js/jquery.tokenize.js"></script>--%>

    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">


    <!-- HTML5 shim and Respond.js IE8 support of HTML5 -->
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
    <script src="${pageContext.request.contextPath}/js/respond.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/lte-ie7.js"></script>
    <![endif]-->


</head>
<body>
<section id="container" class="">

    <jsp:include page="../pagecontent/header.jsp"/>
    <jsp:include page="../pagecontent/sidebar.jsp"/>

    <section id="main-content">
        <section class="wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header"><i class="fa fa-list-alt"></i> Created</h3>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <ol class="breadcrumb">
                        <li><i class="fa fa-home"></i><a href="/">Home</a></li>
                        <li><i class="fa fa-desktop"></i>Post</li>
                        <li><i class="fa fa-list-alt"></i>Created</li>
                    </ol>
                </div>
            </div>
            <div class="row">
                <table >
                    <c:set var="amm" value="${chosen.size()}"/>
                    <c:set var="perrow" value="4"/>
                    <tr>

                        <c:forEach items="${chosen}" var="chosenItem" varStatus="status">

                        <c:choose>
                        <c:when test="${( (status.count) % perrow  == 0) }">
                        <td >
                            <div class="background" onclick="changeIm(this)" id="${chosenItem.id}"
                                 style="background: url(${chosenItem.imageUrl})  no-repeat; background-size: 300px 200px "  >
                                <div class="imgtitle">${chosenItem.name}</div>
                                <div class="xbox" id="cover${chosenItem.id}" style="visibility: visible">
                                    <img src="${pageContext.request.contextPath}/img/OkChoose.jpg" width="300"
                                         height="200"/>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        </c:when>


                        <c:otherwise>
                            <td >
                                <div class="background" onclick="changeIm(this)" id="${chosenItem.id}"
                                     style="background: url(${chosenItem.imageUrl}) no-repeat; background-size: 300px 200px">
                                    <div class="imgtitle">${chosenItem.name}</div>
                                    <div class="xbox" id="cover${chosenItem.id}" style="visibility: visible">
                                        <img src="${pageContext.request.contextPath}/img/OkChoose.jpg" width="300"
                                             height="200"/>
                                    </div>
                                </div>
                            </td>
                        </c:otherwise>
                        </c:choose>

                        </c:forEach>


                        <c:forEach items="${nonchosen}" var="nonChosenItem" varStatus="status">

                        <c:choose>
                        <c:when test="${( (amm + status.count) % perrow == 0) && !status.last}">
                        <td  >
                            <div class="background" onclick="changeIm(this)" id="${nonChosenItem.id}"
                                 style="background: url('${nonChosenItem.imageUrl}') no-repeat; background-size: 300px 200px">
                                <div class="imgtitle">${nonChosenItem.name}</div>
                                <div class="xbox" id="cover${nonChosenItem.id}" style="visibility: hidden">
                                    <img src="${pageContext.request.contextPath}/img/OkChoose.jpg" width="300"
                                         height="200"/>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        </c:when>

                        <c:otherwise>
                            <td >
                                <div class="background" onclick="changeIm(this)" id="${nonChosenItem.id}"
                                     style="background: url('${nonChosenItem.imageUrl}') no-repeat; background-size: 300px 200px">
                                    <div class="imgtitle">${nonChosenItem.name}</div>
                                    <div class="xbox" id="cover${nonChosenItem.id}" style="visibility: hidden">
                                        <img src="${pageContext.request.contextPath}/img/OkChoose.jpg" width="300"
                                             height="200"/>
                                    </div>
                                </div>
                            </td>
                        </c:otherwise>
                        </c:choose>
                        </c:forEach>
                    </tr>
                </table>
            </div>
        </section>
    </section>

</section>
<script>
    function changeIm(el) {
        var element = document.getElementById("cover" + el.id);
        if (element.style.visibility === "hidden") {

            var xmlhttp = new XMLHttpRequest();
            xmlhttp.open('GET', '/userhascategories?action=addremove&id=' + el.id + "&tochosen=yes", true);
            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState == 4) {
                    var response = JSON.parse(xmlhttp.responseText);
                    if (response.status === 'success') {
                        element.style.visibility = "visible";
                    }
                }
            };
            xmlhttp.send(null);

        } else {
            var xmlhttp = new XMLHttpRequest();
            xmlhttp.open('GET', '/userhascategories?action=addremove&id=' + el.id + "&tochosen=no", true);
            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState == 4) {
                    var response = JSON.parse(xmlhttp.responseText);
                    if (response.status === 'success') {
                        element.style.visibility = "hidden";
                    }
                }
            };
            xmlhttp.send(null);
        }
    }
</script>
<style>
    div.background {
        width: 300px;
        height: 200px;
        background-size: 300px 200px;
    }

    div.xbox {
        width: 300px;
        height: 200px;
        background-color: #000000;
        opacity: 0.85;
    }

    div.imgtitle {
        width: 300px;
        height: 20px;
        background-color: #000000;
        opacity: 0.7;
        color: whitesmoke;
    }

    td {
        padding: 10px 10px 10px 10px;
    }


</style>
</body>
</html>
