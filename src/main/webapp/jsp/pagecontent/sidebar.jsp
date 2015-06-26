<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<aside>
    <div id="sidebar" class="nav-collapse ">
        <c:choose>
            <c:when test="${user.role == 'USER'}">
                <ul class="sidebar-menu">
                    <li class="sub-menu">
                        <a href="javascript:;" class="">
                            <i class="icon_genius"></i>
                            <span>Post</span>
                            <span class="menu-arrow arrow_carrot-right"></span>
                        </a>
                        <ul class="sub">
                            <li><a class="" href="${pageContext.request.contextPath}/jsp/post/allposts.jsp">All
                                Posts</a></li>
                            <li><a class="" href="${pageContext.request.contextPath}/jsp/post/addpost.jsp">Add New
                                Post</a></li>
                        </ul>
                    </li>
                    <li class="sub-menu">
                        <a href="javascript:;" class="">
                            <i class="icon_document_alt"></i>
                            <span>Task</span>
                            <span class="menu-arrow arrow_carrot-right"></span>
                        </a>
                        <ul class="sub">
                            <li><a class="" href="${pageContext.request.contextPath}/jsp/post/alltasks.jsp">All
                                Tasks</a></li>
                            <li><a class="" href="${pageContext.request.contextPath}/jsp/post/addtask.jsp">Add New
                                Task</a></li>
                            <li><a class="" href="${pageContext.request.contextPath}/jsp/post/bindaccount.jsp">Bind
                                Accounts</a>
                            </li>
                            <li><a class="" href="${pageContext.request.contextPath}/jsp/post/schedule.jsp">Schedule</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a class="" href="chart-chartjs.html">
                            <i class="icon_piechart"></i>
                            <span>Function 3</span>
                        </a>
                    </li>
                </ul>
            </c:when>
            <c:when test="${user.role =='ADMIN'}">
                <ul class="sidebar-menu">
                    <li class="sub-menu">
                        <a href="javascript:;" class="">
                            <i class="icon_genius"></i>
                            <span>Post</span>
                            <span class="menu-arrow arrow_carrot-right"></span>
                        </a>
                        <ul class="sub">
                            <li><a class="" href="${pageContext.request.contextPath}/jsp/post/allposts.jsp">All
                                Posts</a></li>
                            <li><a class="" href="${pageContext.request.contextPath}/jsp/post/addpost.jsp">Add New
                                Post</a></li>
                        </ul>
                    </li>
                </ul>
            </c:when>
        </c:choose>
    </div>
</aside>