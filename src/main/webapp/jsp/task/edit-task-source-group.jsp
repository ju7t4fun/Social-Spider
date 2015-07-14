<%--
  Created by IntelliJ IDEA.
  User: hell-engine
  Date: 7/13/2015
  Time: 7:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="source-group" class="form-group">
  <h4><l:resource key="select.group.to.grab"/></h4>
  <select id="tokenize_focus_source_walls" multiple="multiple"
          class="tokenize-sample"
          style="width:100%;">
    <c:forEach items="${sourceWalls}" var="wall">
      <%--<option value="${wall.id}" ${wall.selected eq 'true'?'selected="selected"':''}><c:out value="${wall.text}"/></option>--%>
      <c:choose>
        <c:when test="${wall.selected eq 'true'}">
          <option value="${wall.id}" selected="selected"><c:out value="${wall.text}"/></option>
        </c:when>
        <c:otherwise>
          <option value="${wall.id}"><c:out value="${wall.text}"/></option>
        </c:otherwise>
      </c:choose>
    </c:forEach>

  </select>

  <script type="text/javascript">
    var calculateCountWallCallBack;
    $('select#tokenize_focus_source_walls').tokenize({
      datas: 'select',
      newElements: false,
      displayDropdownOnFocus: true,
      onAddToken: function (value, text, e) {
        calculateCountWallCallBack();
      }
    });
  </script>
</div>