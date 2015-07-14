<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="content-type-group" class="form-group">
    <h4><l:resource key="content.type"/></h4>

    <div class="col-lg-4">
        <div class="checkbox">
            <label>
                <input type="checkbox" name="content_type" value="text" ${TEXT eq 'true'?'checked':''}>
                <l:resource key="text"/>
            </label>
        </div>
        <div class="checkbox">
            <label>
                <input type="checkbox" name="content_type" value="photo" ${PHOTO eq 'true'?'checked':''}>
                <l:resource key="photo"/>
            </label>
        </div>
        <div class="checkbox">
            <label>
                <input type="checkbox" name="content_type" value="audio" ${AUDIO eq 'true'?'checked':''}>
                <l:resource key="audio"/>
            </label>
        </div>
        <div class="checkbox">
            <label>
                <input type="checkbox" name="content_type" value="video" ${VIDEO eq 'true'?'checked':''}>
                <l:resource key="video"/>
            </label>
        </div>
        <div class="checkbox">
            <label>
                <input type="checkbox" name="content_type" value="docs" ${DOCUMENTS eq 'true'?'checked':''}>
                <l:resource key="documents"/>
            </label>
        </div>
    </div>

    <div class="col-lg-8">
        <div class="checkbox">
            <label>
                <input type="checkbox" name="content_type" value="repost"  ${REPOSTS eq 'true'?'checked':''}>
                <l:resource key="deep.copy"/>
            </label>
        </div>
        <div class="checkbox j4f-disable-at-admin">
            <label>
                <input type="checkbox" name="content_type" value="title" ${SIMPLE_TITLE eq 'true'?'checked':''}>
                <l:resource key="content.simple.title"/>
            </label>
        </div>
        <div class="checkbox j4f-disable-at-admin">
            <label>
                <input type="checkbox" name="content_type" value="tex_title" ${TEXT_TITLE eq 'true'?'checked':''}>
                <l:resource key="content.text.title"/>
            </label>
        </div>


        <div class="checkbox">
            <label>
                <input type="checkbox" name="content_type" value="link" ${LINKS eq 'true'?'checked':''}>
                <l:resource key="links"/>
            </label>
        </div>
        <div class="checkbox">
            <label>
                <input type="checkbox" name="content_type" value="page" ${PAGES eq 'true'?'checked':''}>
                <l:resource key="pages"/>
            </label>
        </div>
        <%--<div class="checkbox">--%>
        <%--<label>--%>
        <%--<input type="checkbox" name="content_type" value="hashtag">--%>
        <%--<l:resource key="hashtags"/>--%>
        <%--</label>--%>
        <%--</div>--%>
    </div>
</div>