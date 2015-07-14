<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="posting-type-group" class="form-group">
    <h4><l:resource key="posting.type"/></h4>

    <div class="btn-group btn-group-justified" data-toggle="buttons">
        <label class="btn btn-default ${posting_type eq 'COPY'?'active':''}">
            <input type="radio" name="posting_type" value="COPY" ${posting_type eq 'COPY'?'checked':''}>
            <l:resource key="copying"/>
        </label>
        <label class="btn btn-default ${posting_type eq 'REPOST'?'active':''}">
            <input type="radio" name="posting_type" value="REPOST" ${posting_type eq 'REPOST'?'checked':''}>
            <l:resource key="reposts"/>
        </label>
    </div>
</div>