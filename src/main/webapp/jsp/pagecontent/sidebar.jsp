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
                            <span>Post</span>
                            <span class="menu-arrow arrow_carrot-right"></span>
                        </a>
                        <ul class="sub">
                            <li><a class="" href="/post?action=created">Created</a></li>
                            <li><a class="" href="/post?action=queued">Queued</a></li>
                            <li><a class="" href="/post?action=posted">Posted</a></li>
                            <li><a class="" href="/post?action=add"><l:resource key="addnewpost"/></a></li>
                        </ul>
                    </li>
                    <li class="sub-menu">
                        <a href="javascript:;" class="">
                            <i class="icon_document_alt"></i>
                            <span>Task</span>
                            <span class="menu-arrow arrow_carrot-right"></span>
                        </a>
                        <ul class="sub">
                            <li><a class="" href="${pageContext.request.contextPath}/task">All
                                Tasks</a></li>
                            <li><a class="" href="${pageContext.request.contextPath}/task?action=create">Add New
                                Task</a></li>
                        </ul>
                    </li>
                    <li>
                        <a class="" href="/owner"> <i class="icon_piechart"></i>
                            <span>Owner</span>
                        </a>
                    </li>
                </ul>
            </c:when>
            <c:when test="${user.role =='ADMIN'}">
                <ul class="sidebar-menu">
                    <li class="sub-menu">
                        <a href="${pageContext.request.contextPath}/admin/support" class="">
                            <i class="icon_genius"></i>
                            <span>Support</span>
                        </a>
                    </li>
                    <li class="sub-menu">
                        <a href="javascript:;" class="">
                            <i class="icon_genius"></i>
                            <span>Edit</span>
                            <span class="menu-arrow arrow_carrot-right"></span>
                        </a>
                        <ul class="sub">
                            <li>
                                <a class="" href="${pageContext.request.contextPath}/admin/users">Users
                                    list</a>
                            </li>
                            <li>
                                <a class="" href="${pageContext.request.contextPath}/admin/categories">Categories
                                </a>
                            </li>
                        </ul>
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