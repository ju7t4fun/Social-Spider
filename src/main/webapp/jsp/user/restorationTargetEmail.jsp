<%--
  Created by IntelliJ IDEA.
  User: Орест
  Date: 15.06.2015
  Time: 4:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title></title>
</head>
<body>
<form  action="/forgotpassword" method="post" >
  ${restoreMessage}
  <div >
    <input type="hidden" name="action" value="sendmail">
    <input type="email"   name="email" placeholder=""  />
  </div>

  <button type="submit" > OK </button>

</form>
<a href="/" > Main </a>
</body>
</html>
