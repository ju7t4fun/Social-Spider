<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="destination-group" class="form-group">
    <h4><l:resource key="select.group.to.post"/></h4>
    <select id="tokenize_focus_destination_walls"multiple="multiple"
            class="tokenize-sample"
            style="width:100%;">
        <c:forEach items="${destinationWalls}" var="wall">
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
        $('select#tokenize_focus_destination_walls').tokenize({
            datas: 'select',
            displayDropdownOnFocus: true,
            newElements: false,
            onAddToken: function (value, text, e) {
                calculateCountWallCallBack();
            }
        });
    </script>
</div>