<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
<script src="${pageContext.request.contextPath}/js/language.jsp"></script>

<aside>
    <div id="sidebar" class="nav-collapse ">
        <c:choose>
            <c:when test="${user.role == 'USER'}">
                <ul class="sidebar-menu">
                    <c:choose>
                        <c:when test="true">
                            <li>
                                <a class="" href="${pageContext.request.contextPath}/post"> <i class="fa fa-credit-card"></i>
                                    <span><l:resource key="post"/></span>
                                </a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="sub-menu">
                                <a href="javascript:" class="">
                                    <i class="fa fa-credit-card"></i>
                                    <span><l:resource key="post"/></span>
                                    <span class="menu-arrow arrow_carrot-right"></span>
                                </a>
                                <ul class="sub">
                                    <li><a class="" href="${pageContext.request.contextPath}/post?action=created"><i class="fa fa-list"></i>
                                        <l:resource key="created"/></a></li>
                                    <li><a class="" href="${pageContext.request.contextPath}/post?action=queued"><i class="fa fa-spinner"></i>
                                        <l:resource key="queued"/></a></li>
                                    <li><a class="" href="${pageContext.request.contextPath}/post?action=posted"><i class="fa fa-check-square-o"></i>
                                        <l:resource key="posted"/></a></li>
                                    <li><a class="" href="${pageContext.request.contextPath}/post?action=add"><i class="fa fa-plus-circle"></i>
                                        <l:resource key="addnewpost"/></a></li>
                                </ul>
                            </li>
                        </c:otherwise>
                    </c:choose>


                    <li class="sub-menu">
                        <a href="javascript:" class="">
                            <i class="fa fa-tasks"></i>
                            <span><l:resource key="task"/></span>
                            <span class="menu-arrow arrow_carrot-right"></span>
                        </a>
                        <ul class="sub">
                            <li><a class="" href="${pageContext.request.contextPath}/task">
                                <i class="fa fa-list"></i><l:resource key="alltasks"/></a></li>
                            <li><a class="" href="${pageContext.request.contextPath}/task?action=create">
                                <i class="fa fa-plus-circle"></i><l:resource key="addnewtask"/></a></li>
                        </ul>
                    </li>
                    <li>
                        <a class="" href="${pageContext.request.contextPath}/owner"> <i class="fa fa-users"></i>
                            <span><l:resource key="owner"/></span>
                        </a>
                    </li>
                    <li>
                        <a class="" href="${pageContext.request.contextPath}/categories"> <i class="fa fa-bookmark"></i>
                            <span><l:resource key="category"/></span>
                        </a>
                    </li>

                </ul>
            </c:when>
            <c:when test="${user.role =='ADMIN'}">
                <ul class="sidebar-menu">
                    <li class="sub-menu">
                        <a href="javascript:" class="">
                            <i class="fa fa-rss"></i>
                            <span><l:resource key="feed"/></span>
                            <span class="menu-arrow arrow_carrot-right"></span>
                        </a>
                        <ul class="sub">
                            <li>
                                <a href="${pageContext.request.contextPath}/task?action=showtasksforadmin" class="">
                                    <i class="fa fa-tasks"></i>
                                    <span><l:resource key="task"/></span>
                                </a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/admin/addtask" class="">
                                    <i class="fa fa-plus-circle"></i>
                                    <span><l:resource key="addnewtask"/></span>
                                </a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/admin/owner" class="">
                                    <i class="fa fa-users"></i>
                                    <span><l:resource key="owner"/></span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li class="sub-menu">
                        <a href="javascript:" class="">
                            <i class="fa fa-pencil-square-o"></i>
                            <span><l:resource key="edit"/></span>
                            <span class="menu-arrow arrow_carrot-right"></span>
                        </a>
                        <ul class="sub">
                            <li>
                                <a class="" href="${pageContext.request.contextPath}/admin/users">
                                    <i class="fa fa-user-plus"></i><l:resource key="userlist"/></a>
                            </li>
                            <li>
                                <a class="" href="${pageContext.request.contextPath}/admin/categories">
                                    <i class="fa fa-star"></i><l:resource key="category"/>
                                </a>
                            </li>
                            <li>
                                <a class="" href="${pageContext.request.contextPath}/admin/groups">
                                    <i class="fa fa-users "></i><l:resource key="groups"/></a>
                            </li>
                        </ul>
                    </li>
                    <li class="sub-menu">
                        <a href="${pageContext.request.contextPath}/admin/support" class="">
                            <i class="fa fa-envelope-o"></i>
                            <span><l:resource key="support"/></span>
                        </a>
                    </li>
                </ul>
            </c:when>
        </c:choose>

        <div style=" position: absolute; bottom: 10px; left: 6px;">
            <div class="btn-group" style="margin-right: 20px;">
                <a style="border: 1px; color: #3c454f; border-radius: 20px; border-bottom-right-radius: 0;
                border-top-right-radius: 0; font-family: 'Open Sans', 'Helvetica Neue', Arial, sans-serif;
                font-weight: 600; width: 25px; font-size: 8pt; height: 18px;" class="btn btn-default btn-sm btn-xs"
                   change="ua">UA</a>
                <a style="border: 1px;color: #3c454f; border-radius: 20px; border-bottom-left-radius: 0;
                border-top-left-radius: 0; font-family: 'Open Sans', 'Helvetica Neue', Arial, sans-serif;
                font-weight: 600; width: 25px; font-size: 8pt; height: 18px;" class="btn btn-default btn-sm btn-xs"
                   change="en"><span style="margin-left: -4px;">EN
                </span></a>
            </div>
            <div style="color:#fff;">
                <h6>Social Spider Demo </h6>
                <%--<h6><l:resource key="address"/></h6>--%>
                <p><a href="skype:sm_1423?chat"  style="color:#fff;" ><i class="fa fa-skype"></i> <span>Chat</span></a></p>
                <p><a href="mailto:kovalik.yura@aol.com" style="color:#39afea" target="_blank"><h6>kovalik.yura@aol.com</h6></a></p>
            </div>
        </div>
    </div>
</aside>