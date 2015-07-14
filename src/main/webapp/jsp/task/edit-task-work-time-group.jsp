<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="work-time-group" class="form-group">
    <l:resource key="work.time"><h4></h4></l:resource>
    <div class="btn-group btn-group-justified" data-toggle="buttons">
        <a class="btn btn-default active" aria-expanded="true"
           href="#work-time-all-day-pills" data-toggle="tab">
            <input type="radio" name="work_time" value="ROUND_DAILY"
                   checked> <l:resource
                key="around.the.clock"/>
        </a>
        <a class="btn btn-default" disabled aria-expanded="true"
           href="#work-time-period-pills" data-toggle="tab">
            <input type="radio" name="work_time" value="DAY_PERIOD"
                   style="margin-left:15px;" disabled>
            <l:resource key="select.time"/>
        </a>
    </div>
</div>
<div class="tab-content">
    <div class="tab-pane fade active in" id="work-time-all-day-pills"></div>
    <div class="tab-pane fade " id="work-time-period-pills"></div>
</div>