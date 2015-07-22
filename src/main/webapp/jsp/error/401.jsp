<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<html>
<head>
    <title>Page Not Found</title>
    <link href="${pageContext.request.contextPath}/css/error.css" rel="stylesheet" type="text/css" media="all">
    <link href=".${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" type="text/css" media="all">
</head>
<body>
<div class="wrapper row2">
    <div id="container" class="clear">
        <section id="fof" class="clear">
            <div class="hgroup"><br><br><br><br></div>
            <img src="${pageContext.request.contextPath}/img/error/401.png" alt="">

            <p>Unauthorized. Your Credentials Do Not Match Those Required To Log On.</p>

            <p><a href="javascript:history.go(-1)">&laquo; Go Back</a> / <a href="${pageContext.request.contextPath}/login">Go Login</a> / <a href="/">Go
                Home &raquo;</a></p>
        </section>
    </div>
</div>
</body>
</html>
