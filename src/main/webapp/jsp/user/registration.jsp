<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="Creative - Bootstrap 3 Responsive Admin Template">
  <meta name="author" content="GeeksLabs">
  <meta name="keyword" content="Creative, Dashboard, Admin, Template, Theme, Bootstrap, Responsive, Retina, Minimal">
  <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/icons/favicon.png">

  <title>Sign Up</title>

  <!-- Bootstrap CSS -->
  <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
  <!-- bootstrap theme -->
  <link href="${pageContext.request.contextPath}/css/bootstrap-theme.css" rel="stylesheet">
  <!--external css-->
  <!-- font icon -->
  <link href="${pageContext.request.contextPath}/css/elegant-icons-style.css" rel="stylesheet" />
  <link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet" />
  <!-- Custom styles -->
  <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/style-responsive.css" rel="stylesheet" />

  <!-- HTML5 shim and Respond.js IE8 support of HTML5 -->
  <!--[if lt IE 9]>
  <script src="js/html5shiv.js"></script>
  <script src="js/respond.min.js"></script>
  <![endif]-->
</head>

<body class="login-img3-body">

<div class="container">

  <form class="login-form" action="index.html">
    <div class="login-wrap">
      <p class="login-img"><i class="icon_house_alt"></i></p>
      <div class="input-group">
        <span class="input-group-addon"><i class="icon_profile"></i></span>
        <input type="text" class="form-control" placeholder="<l:resource key="reg.username"/>">
      </div>
      <div class="input-group">
        <span class="input-group-addon"><i class="icon_mail_alt"></i></span>
        <input type="email" class="form-control" placeholder="<l:resource key="login.email"/>" style="border-color:#ffffff;" autofocus>
      </div>
      <div class="input-group">
        <span class="input-group-addon"><i class="icon_key_alt"></i></span>
        <input type="password" class="form-control" placeholder="<l:resource key="login.password"/>">
      </div>

      <button class="btn btn-primary btn-lg btn-block" type="submit" style="margin-bottom:20px;margin-right:10px;"> <l:resource key="login.signup"/> </button>

    </div>
  </form>

</div>


</body>
</html>

