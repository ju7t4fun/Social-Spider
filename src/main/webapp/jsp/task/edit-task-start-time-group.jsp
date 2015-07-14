<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="start-time-group" class="form-group">
    <h4><l:resource key="start.time"/></h4>

    <div class="btn-group btn-group-justified" data-toggle="buttons">
        <a class="btn btn-default active" aria-expanded="true"
           href="#start-time-interval-pills" data-toggle="tab">
            <input type="radio" name="start_time" value="INTERVAL" checked>
            <l:resource key="interval"/>
        </a>
        <a class="btn btn-default" aria-expanded="true"
           href="#start-time-sch-pills" data-toggle="tab" disabled>
            <input type="radio" name="start_time" value="SCHEDULE"
                   style="margin-left:15px;" disabled>
            <l:resource key="schedule"/>
        </a>
    </div>
</div>
<div class="tab-content">
    <div class="tab-pane fade active in row" id="start-time-interval-pills">
        <div class="interval_number_group">
            <%--[INTERVAL_SLIDER]--%>
            <span>Between </span>
            <input type="number" name="interval_min"
                   style="margin-left:15px;width:50px;border: none;-webkit-appearance: none;"
                   value="${interval_min}"/>
            <span> and </span>
            <input type="number" name="interval_max"
                   style="margin-left:15px;width:50px;border: none;-webkit-appearance: none;"
                   value="${interval_max}"/>
            <span> minutes.</span>
        </div>
        <div class="col-lg-offset-1 col-lg-10">
            <input type="text" id="interval_slider" value=""
                   name="interval"/>
        </div>
        <script type="text/javascript">
            j4fBundleMark("nmbMinuteGen");
            j4fBundleMark("nmbMinuteNom");
            j4fBundleMark("nmbMinutePlu");
            $(document).ready(function () {
                var $range = $("#interval_slider"),
                        $result_min = $("[name='interval_min']"),
                        $result_max = $("[name='interval_max']");
                $(".interval_number_group").hide();

                var track = function (data) {
                    $result_min.val(data.from);
                    $result_max.val(data.to);
                };


                var updateDataStartTime = function(num){
                    return unitsAuto(num,{gen:"nmbMinuteGen",nom:"nmbMinuteNom", plu:"nmbMinutePlu"});
                }

                $range.ionRangeSlider({
                    hide_min_max: true,
                    keyboard: true,
                    min: 1,
                    max: parseInt("${interval_limit}"),
                    from: parseInt("${interval_min}"),
                    to: parseInt("${interval_max}"),
                    type: 'double',
                    step: 1,
                    postfix: function(num){
                        return unitsAuto(num,{gen:"nmbMinuteGen",nom:"nmbMinuteNom", plu:"nmbMinutePlu"});
                    },
                    decorate_both: false,
                    onStart: track,
                    onChange: track,
                    onFinish: track,
                    onUpdate: track
                });



                scriptCallBack.push(function(){
                    updateDataStartTime = function(num){
                        return unitsAuto(num,{gen:"nmbMinuteGen",nom:"nmbMinuteNom", plu:"nmbMinutePlu"});
                    };
                    $range.data("ionRangeSlider").update({
                        postfix: updateDataStartTime
                    })
                })
            });
        </script>
    </div>
    <div class="tab-pane fade " id="start-time-sch-pills"></div>
</div>