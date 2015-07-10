<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
<script src="${pageContext.request.contextPath}/js/language.js"></script>

<aside>
    <div id="sidebar" class="nav-collapse ">
        <c:choose>
            <c:when test="${user.role == 'USER'}">
                <ul class="sidebar-menu">
                    <li class="sub-menu">
                        <a href="javascript:;" class="">
                            <i class="icon_genius"></i>
                            <span><l:resource key="post"/></span>
                            <span class="menu-arrow arrow_carrot-right"></span>
                        </a>
                        <ul class="sub">
                            <li><a class="" href="/post?action=created"><l:resource key="created"/></a></li>
                            <li><a class="" href="/post?action=queued"><l:resource key="queued"/></a></li>
                            <li><a class="" href="/post?action=posted"><l:resource key="posted"/></a></li>
                            <li><a class="" href="/post?action=add"><l:resource key="addnewpost"/></a></li>
                        </ul>
                    </li>
                    <li class="sub-menu">
                        <a href="javascript:;" class="">
                            <i class="icon_document_alt"></i>
                            <span><l:resource key="task"/></span>
                            <span class="menu-arrow arrow_carrot-right"></span>
                        </a>
                        <ul class="sub">
                            <li><a class="" href="${pageContext.request.contextPath}/task"><l:resource
                                    key="alltasks"/></a></li>
                            <li><a class="" href="${pageContext.request.contextPath}/task?action=create"><l:resource
                                    key="addnewtask"/></a></li>
                        </ul>
                    </li>
                    <li>
                        <a class="" href="/owner"> <i class="fa fa-users"></i>
                            <span><l:resource key="owner"/></span>
                        </a>
                    </li>
                    <li>
                        <a class="" href="/categories"> <i class="fa fa-bookmark"></i>
                            <span><l:resource key="category"/></span>
                        </a>
                    </li>

                </ul>
            </c:when>
            <c:when test="${user.role =='ADMIN'}">
                <ul class="sidebar-menu">
                    <li class="sub-menu">
                        <a href="${pageContext.request.contextPath}/admin/support" class="">
                            <i class="icon_genius"></i>
                            <span><l:resource key="support"/></span>
                        </a>
                    </li>
                    <li class="sub-menu">
                        <a href="javascript:;" class="">
                            <i class="icon_genius"></i>
                            <span><l:resource key="edit"/></span>
                            <span class="menu-arrow arrow_carrot-right"></span>
                        </a>
                        <ul class="sub">
                            <li>
                                <a class="" href="${pageContext.request.contextPath}/admin/users"><l:resource
                                        key="userslist"/></a>
                            </li>
                            <li>
                                <a class="" href="${pageContext.request.contextPath}/admin/categories"><l:resource
                                        key="category"/>
                                </a>
                            </li>
                            <li>
                                <a class="" href="${pageContext.request.contextPath}/admin/groups"><l:resource
                                        key="groups"/></a>
                            </li>
                        </ul>
                    </li>

                    <li class="sub-menu">
                        <a href="${pageContext.request.contextPath}/task?action=showtasksforadmin" class="">
                            <i class="icon_genius"></i>
                            <span>Tasks Binding</span>
                        </a>
                    </li>
                    <li class="sub-menu">
                        <a href="${pageContext.request.contextPath}/admin/addtask" class="">
                            <i class="icon_genius"></i>
                            <span>Add task</span>
                        </a>
                    </li>
                    <li class="sub-menu">
                        <a href="${pageContext.request.contextPath}/admin/owner" class="">
                            <i class="icon_genius"></i>
                            <span>Owner</span>
                        </a>
                    </li>
                </ul>
            </c:when>
        </c:choose>

        <div style="position:fixed; top: 510px; left: 10px;">
            <div class="btn-group" style="margin-right: 20px;">
                <a style="border: 1px; color: #030303;
                        border-radius: 20px;   border-bottom-right-radius: 0;
                             border-top-right-radius: 0; font-family: 'Open Sans', 'Helvetica Neue', Arial, sans-serif;
                         font-weight: 700;width: 30px;
                 height: 25px;" class="btn btn-default btn-sm" change="ua">UA</a>
                <a style="border: 1px;color: #030303;
                         border-radius: 20px; border-bottom-left-radius: 0;
                     border-top-left-radius: 0; margin-left: -1px; font-family: 'Open Sans', 'Helvetica Neue', Arial, sans-serif;
                     font-weight: 700;width: 30px;
                    height: 25px;" class="btn btn-default btn-sm" change="en"><span style="margin-left: -4px;">EN</span></a>
            </div>
            <div style="color:#fff;">
                <h6>Social-Spider Corp. </h6>
                <h6><l:resource key="address"/></h6>
                <h6>(032)555-9876</h6>

                <p><a href="mailto:example@gmail.com " style="color:#39afea"><h6>socialspider@gmail.com</h6></a></p>
            </div>
        </div>
    </div>

</aside>