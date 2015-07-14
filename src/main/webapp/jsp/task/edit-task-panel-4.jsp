<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="panel panel-default" hidden>
    <div class="panel-heading">
        <h4 class="panel-title j4f-fix-title-background">
            <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion"
               href="#collapseFour">
            </a>
        </h4>
    </div>
    <div id="collapseFour" class="panel-collapse collapse">
        <div class="panel-body">
            <l:resource key="addtask.step4"/>
            <div class="col-lg-6">
                <h4><l:resource key="actions.after.posting"/></h4>
                <input type="radio" name="actions" value="LIKE"> <l:resource
                    key="like"/> <br>
                <input type="radio" name="actions" value="REPOST"> <l:resource
                    key="reposts"/> <br>
                <input type="radio" name="actions" value="DO_NOTHING" checked>
                <l:resource key="do.nothing"/> <br>
            </div>
        </div>
    </div>
</div>