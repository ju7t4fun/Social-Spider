<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="">
<div id="post-delay-group" class="form-group">
    <h4><l:resource key="post.delay"/></h4>

    <div class="post_delay_number_group">
        <span>Between </span>
        <input type="number" name="post_delay_min"
               style="margin-left:15px;width:50px;border: none;-webkit-appearance: none;"
               value="${post_delay_min}"/>
        <span> and </span>
        <input type="number" name="post_delay_max"
               style="margin-left:15px;width:50px;border: none;-webkit-appearance: none;"
               value="${post_delay_max}"/>
        <span> seconds.</span>
    </div>
    <div class="col-lg-offset-1 col-lg-10">
        <input type="text" id="post_delay_slider" value="" name="interval"/>
    </div>
    <script type="text/javascript">
        j4fBundleMark("nmbSecondGen");
        j4fBundleMark("nmbSecondNom");
        j4fBundleMark("nmbSecondPlu");
        $(document).ready(function () {
            var $range = $("#post_delay_slider"),
                    $result_min = $("[name='post_delay_min']"),
                    $result_max = $("[name='post_delay_max']");
            $(".post_delay_number_group").hide();

            var track = function (data) {
                $result_min.val(data.from);
                $result_max.val(data.to);
            };

            var updateDataPostDelay = function(num){
                return unitsAuto(num,{nom:"nmbSecondNom",gen:"nmbSecondGen", plu:"nmbSecondPlu"});
            }

            $range.ionRangeSlider({
                force_edges: false,
                hide_min_max: true,
                keyboard: true,
                min: 0,
                max: parseInt("${delay_limit}"),
                from: parseInt("${post_delay_min}"),
                to: parseInt("${post_delay_max}"),
                from_min:30,
                from_max:50,
                to_min:60,
                to_max:100,
                type: 'double',
                step: 1,
                postfix: updateDataPostDelay,
                decorate_both: false,
                onStart: track,
                onChange: track,
                onFinish: track,
                onUpdate: track
            });

            scriptCallBack.push(function(){
                updateDataPostDelay = function(num){
                    return unitsAuto(num,{nom:"nmbSecondNom",gen:"nmbSecondGen", plu:"nmbSecondPlu"});
                };
                $range.data("ionRangeSlider").update({
                    postfix: updateDataPostDelay
                })
            })
        });
    </script>
</div>
</div>