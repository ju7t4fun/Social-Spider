<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="grabbing-mode-group" class="form-group">
    <h4><l:resource key="select.grabbing.mode"/></h4>

    <div class="btn-group btn-group-vertical j4f-fix-full-width"
         data-toggle="buttons">
        <label class="btn btn-default ${grabbing_mode eq 'PER_GROUP'?'active':''}">
            <input type="radio" name="grabbing_mode" value="per_group" ${grabbing_mode eq 'PER_GROUP'?'checked':''}>

            <div class="show-when-jquery-unsupported">
                <l:resource key="grabbing.mode1"/>
            </div>
            <div class="show-when-jquery-supported" hidden>
                <l:resource key="grabbingModeStrategyPerGroupBegin"/>
                <span class="post-count-dat-info"></span>
                <l:resource key="grabbingModeStrategyPerGroupEnd"/>
                <l:resource key="grabbingModeSummaryCount"/>
                <%--<l:resource key="grabbingModeSummaryGrabbed"><span></span></l:resource><span>/</span>--%>
                <%--<l:resource key="grabbingModeSummaryPosted"><span></span></l:resource><span> : </span>--%>
                <span id="post-count-to-grabbing-mode-1"></span>
                <span>/</span>
                <span id="post-count-to-posting-mode-1"></span><span>.</span>
            </div>
        </label>
        <label class="btn btn-default ${grabbing_mode eq 'TOTAL'?'active':''}">
            <input type="radio" name="grabbing_mode" value="total" ${grabbing_mode eq 'TOTAL'?'checked':''}>
            <div class="show-when-jquery-unsupported">
                <l:resource key="grabbing.mode2"/>
            </div>
            <div class="show-when-jquery-supported" hidden>

                <l:resource key="grabbingModeStrategyTotalBegin"/>
                <span class="post-count-nom-info"></span>
                <l:resource key="grabbingModeStrategyTotalEnd"/>
                <l:resource key="grabbingModeSummaryCount"/>
                <%--<l:resource key="grabbingModeSummaryGrabbed"><span></span></l:resource><span>/</span>--%>
                <%--<l:resource key="grabbingModeSummaryPosted"><span></span></l:resource><span> : </span>--%>
                <span id="post-count-to-grabbing-mode-2"></span>
                <span>/</span>
                <span id="post-count-to-posting-mode-2"></span><span>.</span>
            </div>
        </label>
    </div>
</div>
<div id="grabbing-count-group" class="form-group row">
    <div id="post_count_number_group">
        <span><l:resource key="amount.of.posts"/>  </span>
        <input type="number" name="post_count"
               style="width:40px;border: none;-webkit-appearance: none; "
               value="${post_count}">
    </div>
    <div class="col-lg-offset-1 col-lg-10">
        <input type="text" id="post_count_slider" value="" name="interval"/>
    </div>
    <script type="text/javascript">
        var nmbPost = {}, postCountSliderData, newPrefix, nmbOne = {};
        nmbPost.nom = "${bundle.nmbPostNom}";
        nmbPost.dat = "${bundle.nmbPostDat}";
        nmbPost.gen = "${bundle.nmbPostGen}";
        nmbPost.plu = "${bundle.nmbPostPlu}";
        nmbOne.dat = "${bundle.nmbOneDat}";
        nmbOne.nom = "${bundle.nmbOneNom}";

        newPrefix = "${bundle.srcPostCountPrefix}";
        scriptLocaleStorage.set("nmbPostNom", nmbPost.nom);
        scriptLocaleStorage.set("nmbPostGen", nmbPost.gen);
        scriptLocaleStorage.set("nmbPostPlu", nmbPost.plu);
        scriptLocaleStorage.set("nmbPostDat", nmbPost.dat);
        scriptLocaleStorage.set("nmbOneDat", nmbOne.dat);
        scriptLocaleStorage.set("nmbOneNom", nmbOne.nom);
        scriptLocaleStorage.set("srcPostCountPrefix", newPrefix);

        $("#post_count_number_group").hide();
        var trackPostCount = function (data) {
            $("[name='post_count']").val(data.from);
            var count = {};
            if (data.from == 1) {
                count.nom = nmbOne.nom;
                count.dat = nmbOne.dat;
            } else {
                count.nom = data.from;
                count.dat = data.from;
            }
            $(".post-count-dat-info").text("" + count.dat + " " + units(data.from, {
                nom: nmbPost.dat,
                gen: nmbPost.gen,
                plu: nmbPost.plu
            }));
            $(".post-count-nom-info").text("" + count.nom + " " + units(data.from, {
                nom: nmbPost.nom,
                gen: nmbPost.gen,
                plu: nmbPost.plu
            }));
            recalculatePostCount(data.from);
            calculateCountWallCallBack = function () {
                recalculatePostCount(data.from);
            }
        };
        function recalculatePostCount(postCount) {
            var sourceCount = $("#tokenize_focus_source_walls > option[selected]").length;
            var onePost = sourceCount > 0 ? 1 : 0;
            var destCount = $("#tokenize_focus_destination_walls > option[selected]").length;
            $("#post-count-to-grabbing-mode-1").text(postCount * sourceCount);
            $("#post-count-to-posting-mode-1").text(postCount * sourceCount * destCount);

            $("#post-count-to-grabbing-mode-2").text(postCount * onePost);
            $("#post-count-to-posting-mode-2").text(postCount * onePost * destCount);
        }
        $("#post_count_slider").ionRangeSlider({
            hide_min_max: true,
            keyboard: true,
            min: 0,
            max: 10,
            from_min: 1,
            from_max: 2,
            from: parseInt("${post_count}"),
            step: 1,
            decorate_both: false,
            prefix: newPrefix,
            onStart: trackPostCount,
            onChange: trackPostCount,
            onFinish: trackPostCount,
            onUpdate: trackPostCount
        });


        scriptCallBack.push(function (map) {
            nmbPost.nom = map.get("nmbPostNom");
            nmbPost.dat = map.get("nmbPostDat");
            nmbPost.gen = map.get("nmbPostGen");
            nmbPost.plu = map.get("nmbPostPlu");
            nmbOne.dat = map.get("nmbOneDat");
            nmbOne.nom = map.get("nmbOneNom");
            newPrefix = map.get("srcPostCountPrefix");
            $("#post_count_slider").data("ionRangeSlider").update({
                prefix: newPrefix + " "
            });
        });
    </script>
</div>