<%--
  Created by IntelliJ IDEA.
  User: Орест
  Date: 15.06.2015
  Time: 4:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title></title>
</head>
<body>
<form  action="/forgotpassword?action=changepassword&email=${email}" method="post" >

  <div >
    email : ${email} <br>
    ${changeMessage}
  </div>

  <div >
    <input type="password"   name="password" placeholder=""  />
  </div>
  <div >
    <input type="password"   name="repeatPassword" placeholder=""  />
  </div>

  <button type="submit" > OK </button>
  <a href="/" >Main</a>
</form>
</body>
</html>
