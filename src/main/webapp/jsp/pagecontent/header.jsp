<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
<header class="header dark-bg" style="background: rgb(26, 39, 50)">
    <div class="toggle-nav">
        <div class="icon-reorder tooltips" data-original-title="Toggle Navigation" data-placement="bottom"></div>
    </div>
    <!--logo start-->
    <a href="/" class="logo">Social <span class="lite">Spider</span></a>
    <!--logo end-->

    <div class="top-nav notification-row">
        <!-- notificatoin dropdown start-->
        <ul class="nav pull-right top-menu">

            <c:choose>
                <c:when test="${user.role=='ADMIN'}">
                    <jsp:include page="support_admin.jsp"/>
                </c:when>
                <c:when test="${user.role == 'USER'}">
                    <jsp:include page="support_user.jsp"/>
                    <jsp:include page="notification.jsp"/>
                </c:when>
                <c:otherwise>

                </c:otherwise>
            </c:choose>
            <!-- user login dropdown start-->
            <li class="dropdown">
                <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                            <span class="profile-ava">
                                 <img class="avatar" width="40px" height="40px" src="${user.avatarURL}" alt="">
                            </span>
                    <span class="userfname">${user.name}</span>
                    <b class="caret"></b>
                </a>
                <ul class="dropdown-menu extended logout">
                    <div class="log-arrow-up"></div>
                    <li id="aaaa" class="eborder-top">
                        <a href="/profile"><i class="icon_profile"></i><l:resource key="profile"/></a>
                    </li>
                    <li>
                        <a href="/accounts"><i class="fa fa-vk"></i> <l:resource key="myaccounts"/></a>
                    </li>
                    <li>
                        <a href="/login?action=signOut"><i class="fa fa-sign-out"></i> <l:resource key="logout"/></a>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</header>