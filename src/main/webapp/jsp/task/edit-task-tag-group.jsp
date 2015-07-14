<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="tag-group" class="form-group">
    <h4><l:resource key="hashtags"/></h4>
    <input name="wordsinput" id="tagsinput" class="tagsinput"
           value="${hashtags}"/>
</div>