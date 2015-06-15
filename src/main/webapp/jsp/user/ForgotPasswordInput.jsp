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
<form  action="/register?action=register" method="post" >

    <div >
      ${email}
    </div>

    <div >
     <input type="password"   name="password" placeholder=""  />
    </div>
  <div >
    <input type="password"   name="repeatPassword" placeholder=""  />
  </div>

<button type="submit" > OK </button>

</form>
</body>
</html>
