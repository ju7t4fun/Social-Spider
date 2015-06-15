<%--
  Created by IntelliJ IDEA.
  User: Sasha
  Date: 13.06.2015
  Time: 12:44
  To change this template use File | Settings | File Templates.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${not empty user}">
  <c:choose>
    <c:when test="${user.getRole() == '1'}">
      <!--sidebar start-->
      <aside>
        <div id="sidebar"  class="nav-collapse ">
          <!-- sidebar menu start-->
          <ul class="sidebar-menu">
            <li class="active">
              <a class="" href="${pageContext.request.contextPath}/jsp/admin/admin_page.jsp">
                <i class="icon_house_alt"></i>
                <span>Dashboard</span>
              </a>
            </li>

            <li>
              <a class="" href="${pageContext.request.contextPath}/jsp/admin/admin_allusers.jsp">
                <i class="icon_genius"></i>
                <span>Users</span>
              </a>
            </li>
            <li>
              <a class="" href="${pageContext.request.contextPath}/jsp/admin/admin_charts.jsp">
                <i class="icon_piechart"></i>
                <span>Charts</span>
              </a>

            </li>
            <li>
              <a class="" href="chart-chartjs.html">
                <i class="icon_piechart"></i>
                <span>Function 3</span>
              </a>
            </li>
            <li>
              <a class="" href="chart-chartjs.html">
                <i class="icon_piechart"></i>
                <span>Function 4</span>
              </a>
            </li>
            <li>
              <a class="" href="chart-chartjs.html">
                <i class="icon_piechart"></i>
                <span>Function 5</span>
              </a>
            </li>
          </ul>
          <!-- sidebar menu end-->
        </div>
      </aside>
      <!--sidebar end-->
    </c:when>
    <c:otherwise>
      --%>


      <!--sidebar start-->
      <aside>
        <div id="sidebar"  class="nav-collapse ">
          <!-- sidebar menu start-->
          <ul class="sidebar-menu">
            <li class="active">
              <a class="" href="index.html">
                <i class="icon_house_alt"></i>
                <span>Dashboard</span>
              </a>
            </li>

            <li>
              <a class="" href="${pageContext.request.contextPath}/jsp/post/addpost.jsp">
                <i class="icon_genius"></i>
                <span>Add New Post</span>
              </a>
            </li>
            <li>
              <a class="" href="chart-chartjs.html">
                <i class="icon_piechart"></i>
                <span>Function 2</span>

              </a>

            </li>
            <li>
              <a class="" href="chart-chartjs.html">
                <i class="icon_piechart"></i>
                <span>Function 3</span>
              </a>
            </li>
          </ul>
          <!-- sidebar menu end-->
        </div>
      </aside>
      <!--sidebar end-->


 <%--

    </c:otherwise>
  </c:choose>
</c:if>

--%>