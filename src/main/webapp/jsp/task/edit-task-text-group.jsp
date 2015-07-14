<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="text-group" class="form-group">
    <h4><l:resource key="add.text.to.post"/></h4>
    <textarea class="form-control" name="addtext" rows="2"><c:out value="${signature}"/></textarea>
</div>