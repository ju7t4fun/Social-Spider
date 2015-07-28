<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="repeat-group" class="form-group">
    <h4><l:resource key="repeat_h4_text"/></h4>

    <div class="btn-group btn-group-vertical j4f-fix-full-width"
         data-toggle="buttons">
        <a class="btn btn-default ${repeat eq 'REPEAT_DISABLE'?'active':''}" aria-expanded="true"
           href="#repeat-no-repeat-pills" data-toggle="tab">

            <input type="radio" name="repeat" value="REPEAT_DISABLE" ${repeat eq 'REPEAT_DISABLE'?'checked':''}>
            <l:resource key="do.not.repeat"/>
        </a>
        <a class="btn btn-default ${repeat eq 'REPEAT_ON_TIME'?'active':''}" aria-expanded="true"
           href="#repeat-day-repeat-pills" data-toggle="tab">
            <input type="radio" name="repeat" value="REPEAT_ON_TIME" ${repeat eq 'REPEAT_ON_TIME'?'checked':''}>
            <l:resource key="repeat.every"/>
            <span id="repeat_days_text"></span>
            <%--<l:resource key="days"/>--%>
        </a>
    </div>
</div>
<div class="tab-content">
    <div class="tab-pane fade  ${repeat eq 'REPEAT_DISABLE'?'active in':''}" id="repeat-no-repeat-pills"></div>
    <div class="tab-pane fade row ${repeat eq 'REPEAT_ON_TIME'?'active in':''}" id="repeat-day-repeat-pills">
        <div class="repeat_count_group">
            <input type="number" name="repeat_days"
                   style="width:40px;border: none;-webkit-appearance: none; "
                   value="${repeat_count}"/>
        </div>
        <div class="col-lg-offset-1 col-lg-10">
            <input type="text" id="repeat_count_slider"/>
        </div>
        <script type="text/javascript">
            j4fBundleMark("nmbDayGen");
            j4fBundleMark("nmbDayNom");
            j4fBundleMark("nmbDayPlu");
            var updateRepeatValue = parseInt("${repeat_count}");
            $(document).ready(function () {
                var $repeat_count_slider = $("#repeat_count_slider"),
                        $range_repeat_count1 = $("[name='repeat_days']"),
                        $repeat_count_text = $("#repeat_days_text");
                $(".repeat_count_group").hide();
                var decoreFunction = function(num){
                    return unitsAuto(num,{gen:"nmbDayGen",nom:"nmbDayNom", plu:"nmbDayPlu"});
                };

                var track = function (data) {
                    $range_repeat_count1.val(data.from);
                    updateRepeatValue = data.from;
                    $repeat_count_text.text(data.from+" "+decoreFunction(data.from));
                };

                scriptCallBack.push(function(){
                    $repeat_count_text.text(updateRepeatValue+" "+decoreFunction(updateRepeatValue));
                });

                $repeat_count_slider.ionRangeSlider({
                    hide_min_max: true,
                    keyboard: true,
                    min: 0,
                    max: 150,
                    from_min: 3,
                    from: parseInt("${repeat_count}"),
                    step: 1,
                    postfix:decoreFunction ,
                    decorate_both: false,
                    onStart: track,
                    onChange: track,
                    onFinish: track,
                    onUpdate: track
                });
            });
        </script>
    </div>
</div>
