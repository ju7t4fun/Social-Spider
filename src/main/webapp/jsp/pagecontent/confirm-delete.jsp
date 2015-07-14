<%@taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
<link href="${pageContext.request.contextPath}/css/core.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/confirm-delete.js"></script>

<div class="mod mod-effect-blur" id="mod-delete">
    <div class="mod-content">
        <h3><l:resource key="confirm-delete"/></h3>

        <div>
            <button id="delete_butt"><l:resource key="delete"/></button>
            <button id="cancel_butt"><l:resource key="cancel"/></button>
        </div>
    </div>
</div>
<div class="mod-overlay"></div>
