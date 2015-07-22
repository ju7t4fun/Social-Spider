<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/admin-inbox.jsp"></script>
<div hidden>
    <li id="message">
        <a href="${pageContext.request.contextPath}/admin/support?id={user_id}">
            <span class="label label-primary"><i class="icon_profile"></i></span>  {user_name}
            <span class="small italic pull-right">{user_date}</span>
        </a>
    </li>
</div>

<li id="mail_notificatoin_bar" class="dropdown">
    <a data-toggle="dropdown" class="dropdown-toggle" href="#" onclick="onOpenInbox()">
        <i class="icon-envelope-l"></i>
        <span id="count_unread_messages" class="badge bg-important">0</span>
    </a>
    <ul class="dropdown-menu extended notification">
        <div class="notify-arrow notify-arrow-blue"></div>
        <li>
            <p class="blue"><l:resource key="header.seeallmessages" /></p>
        </li>
        <div id="message_chat">

        </div>
        <li>
            <a href="${pageContext.request.contextPath}/admin/support"><l:resource key="header.seeallmessages" /></a>
        </li>
    </ul>
</li>