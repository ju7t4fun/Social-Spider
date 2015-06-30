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

            <!-- task notificatoin start -->
            <li id="task_notificatoin_bar" class="dropdown">
                <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                    <span class="icon-task-l"></span>
                    <span class="badge bg-important">6</span>
                </a>
                <ul class="dropdown-menu extended tasks-bar">
                    <div class="notify-arrow notify-arrow-blue"></div>
                    <li>
                        <p class="blue">You have 6 pending letter</p>
                    </li>
                    <li>
                        <a href="#">
                            <div class="task-info">
                                <div class="desc">Design PSD</div>
                                <div class="percent">90%</div>
                            </div>
                            <div class="progress progress-striped">
                                <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="90"
                                     aria-valuemin="0" aria-valuemax="100" style="width: 90%">
                                    <span class="sr-only">90% Complete (success)</span>
                                </div>
                            </div>
                        </a>
                    </li>
                    <li>
                        <a href="#">
                            <div class="task-info">
                                <div class="desc">
                                    Project 1
                                </div>
                                <div class="percent">30%</div>
                            </div>
                            <div class="progress progress-striped">
                                <div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="30"
                                     aria-valuemin="0" aria-valuemax="100" style="width: 30%">
                                    <span class="sr-only">30% Complete (warning)</span>
                                </div>
                            </div>
                        </a>
                    </li>
                    <li>
                        <a href="#">
                            <div class="task-info">
                                <div class="desc">Digital Marketing</div>
                                <div class="percent">80%</div>
                            </div>
                            <div class="progress progress-striped">
                                <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="80"
                                     aria-valuemin="0" aria-valuemax="100" style="width: 80%">
                                    <span class="sr-only">80% Complete</span>
                                </div>
                            </div>
                        </a>
                    </li>
                    <li>
                        <a href="#">
                            <div class="task-info">
                                <div class="desc">Logo Designing</div>
                                <div class="percent">78%</div>
                            </div>
                            <div class="progress progress-striped">
                                <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="78"
                                     aria-valuemin="0" aria-valuemax="100" style="width: 78%">
                                    <span class="sr-only">78% Complete (danger)</span>
                                </div>
                            </div>
                        </a>
                    </li>
                    <li>
                        <a href="#">
                            <div class="task-info">
                                <div class="desc">Mobile App</div>
                                <div class="percent">50%</div>
                            </div>
                            <div class="progress progress-striped active">
                                <div class="progress-bar" role="progressbar" aria-valuenow="50" aria-valuemin="0"
                                     aria-valuemax="100" style="width: 50%">
                                    <span class="sr-only">50% Complete</span>
                                </div>
                            </div>

                        </a>
                    </li>
                    <li class="external">
                        <a href="#">See All Tasks</a>
                    </li>
                </ul>
            </li>
            <!-- task notificatoin end -->
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
                        <a href="/profile"><i class="icon_clock_alt"></i><l:resource key="profile" /></a>
                    </li>
                    <li>
                        <a href="/accounts"><i class="icon_profile"></i> <l:resource key="myaccounts" /></a>
                    </li>
                    <li>
                        <a href="/login?action=signOut"><i class="icon_key_alt"></i> <l:resource key="logout" /></a>
                    </li>
                </ul>
            </li>
            <!-- user login dropdown end -->
        </ul>
        <!-- notificatoin dropdown end-->
    </div>
</header>
<!--header end-->