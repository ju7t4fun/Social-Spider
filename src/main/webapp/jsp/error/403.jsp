<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<html>
<head>
    <title>Forbidden</title>
    <link href="${pageContext.request.contextPath}/css/error.css" rel="stylesheet" type="text/css" media="all">
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" type="text/css" media="all">
</head>
<body>
<div class="wrapper row2">
    <div id="container" class="clear">
        <section id="fof" class="clear">
            <div class="hgroup"><br><br><br><br></div>
            <img src="${pageContext.request.contextPath}/img/error/403.png" alt="">

            <p><a href="javascript:history.go(-1)">&laquo; Go Back</a> / <a href="${pageContext.request.contextPath}/">Go Home &raquo;</a></p>
        </section>
    </div>
</div>
</body>
</html>
