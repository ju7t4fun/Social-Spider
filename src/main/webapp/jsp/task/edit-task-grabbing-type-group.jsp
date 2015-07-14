<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="grabbing-type-group" class="form-group">
    <h4><l:resource key="grabbing.type"/></h4>

    <div class="btn-group btn-group-vertical j4f-fix-full-width"
         data-toggle="buttons">
        <label class="btn btn-default ${grabbing_type eq 'BEGIN'?'active':''}">
            <input type="radio" name="grabbing_type" value="begin" ${grabbing_type eq 'BEGIN'?'checked':''}>
            <l:resource key="grabbing.type1"/>
        </label>
        <label class="btn btn-default ${grabbing_type eq 'END'?'active':''}">
            <input type="radio" name="grabbing_type" value="end" ${grabbing_type eq 'END'?'checked':''}> <l:resource
                key="grabbing.type2"/>
        </label>
        <label class="btn btn-default ${grabbing_type eq 'RANDOM'?'active':''}">
            <input type="radio" name="grabbing_type" value="random" ${grabbing_type eq 'RANDOM'?'checked':''}> <l:resource
                key="grabbing.type3"/>
        </label>
        <label class="btn btn-default ${grabbing_type eq 'NEW'?'active':''}">
            <input type="radio" name="grabbing_type" value="new" ${grabbing_type eq 'NEW'?'checked':''}> <l:resource
                key="grabbing.type4"/>
        </label>
    </div>
</div>