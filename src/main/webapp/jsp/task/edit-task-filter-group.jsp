<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="filter-slider-visible-group" class="form-group">
    <h4><l:resource key="filter"/></h4>

    <div class="btn-group btn-group-justified" data-toggle="buttons">
        <a class="btn btn-default active" aria-expanded="true"
           href="#filter-no-slider-pills"
           data-toggle="tab">

            <input type="radio" name="slider" value="SLIDER_DISABLE" checked>
            <l:resource key="filterSliderDisable"/>
        </a>
        <a class="btn btn-default" aria-expanded="true"
           href="#filter-with-slider-pills"
           data-toggle="tab">
            <input type="radio" name="slider" value="SLIDER_ENABLE">
            <l:resource key="filterSliderEnable"/>
        </a>
    </div>
</div>

<div id="filter-group" class="form-group">
    <div class="row">
        <div class="form-group col-sm-4">
            <label class="control-label"><l:resource key="likes"/></label>
            <input class="filter-number-spinner form-control j4f-fix-spinner-un-script"
                   type="number"
                   name="likes" value="${likes}" min="0" max="${likes_max}"/>
        </div>
        <div class="form-group col-sm-4">
            <label class="control-label"><l:resource key="reposts"/></label>
            <input class="filter-number-spinner form-control j4f-fix-spinner-un-script"
                   type="number"
                   name="reposts" value="${reposts}" min="0" max="${reposts_max}"/>
        </div>
        <div class="form-group col-sm-4">
            <label class="control-label"><l:resource key="comments"/></label>
            <input class="filter-number-spinner form-control j4f-fix-spinner-un-script"
                   type="number"
                   name="comments" value="${comments}" min="0" max="${comments_max}"/>
        </div>
    </div>

    <%--<div style="clear:both;">--%>
    <%--<!-- флоат? ЯКИЙ НАФ1Г ФЛОАТ В КОЛОНКАХ БУТСТРАПУ!!!!?? --></div>--%>

    <script type="text/javascript">
        $(document).ready(function () {

        });
    </script>
</div>
<div class="tab-content">
    <div class="tab-pane fade active in" id="filter-no-slider-pills"></div>
    <div class="tab-pane fade row" id="filter-with-slider-pills">
        <div class="col-lg-offset-1 col-lg-10">
            <input type="text" id="filter_likes_slider"/>
            <input type="text" id="filter_repost_slider"/>
            <input type="text" id="filter_comment_slider"/>
        </div>
        <script type="text/javascript">
            var filterValue = {}, newFilterPrefix = {}, newFilterPostfix = {};
            j4fBundleMark("srcFilterLikes");
            j4fBundleMark("srcFilterReposts");
            j4fBundleMark("srcFilterComments");
            j4fBundleMark("nmbFilterMultiGen");
            j4fBundleMark("nmbFilterMultiNom");
            j4fBundleMark("nmbFilterMultiPlu");
            j4fBundleMark("srcFilterLikesDisable");
            j4fBundleMark("srcFilterRepostsDisable");
            j4fBundleMark("srcFilterCommentsDisable");
            var getPostfix;
            $(document).ready(function () {
                var $filter_likes_slider = $("#filter_likes_slider"),
                        $filter_repost_slider = $("#filter_repost_slider"),
                        $filter_comment_slider = $("#filter_comment_slider");

                $filter_likes_slider.ionRangeSlider({
                    hide_min_max: true,
                    keyboard: true,
                    min: 0,
                    max: parseInt("${likes_max}"),
                    from: parseInt("${likes}"),
                    step: 1,
                    decorate_both: false,
                    decorated_function: function (num) {
                        return unitAutoDecorate(num, "srcFilterLikes", {
                            nom: "nmbFilterMultiNom",
                            gen: "nmbFilterMultiGen",
                            plu: "nmbFilterMultiPlu"
                        }, "srcFilterLikesDisable")
                    }
                });
                $filter_repost_slider.ionRangeSlider({
                    hide_min_max: true,
                    keyboard: true,
                    min: 0,
                    max: parseInt("${reposts_max}"),
                    from: parseInt("${reposts}"),
                    step: 1,
                    decorate_both: false,
                    decorated_function: function (num) {
                        return unitAutoDecorate(num, "srcFilterReposts", {
                            nom: "nmbFilterMultiNom",
                            gen: "nmbFilterMultiGen",
                            plu: "nmbFilterMultiPlu"
                        }, "srcFilterRepostsDisable")
                    }
                });
                $filter_comment_slider.ionRangeSlider({
                    hide_min_max: true,
                    keyboard: true,
                    min: 0,
                    max: parseInt("${comments_max}"),
                    from: parseInt("${comments}"),
                    step: 1,
                    decorate_both: false,
                    decorated_function: function (num) {
                        return unitAutoDecorate(num, "srcFilterComments", {
                            nom: "nmbFilterMultiNom",
                            gen: "nmbFilterMultiGen",
                            plu: "nmbFilterMultiPlu"
                        }, "srcFilterCommentsDisable")
                    }
                });
                var filterSliderLikesData = $filter_likes_slider.data("ionRangeSlider"),
                        filterSliderRepostsData = $filter_repost_slider.data("ionRangeSlider"),
                        filterSliderCommentsData = $filter_comment_slider.data("ionRangeSlider");

                var backTrackLikes = function (value) {
                    if (filterValue.likes != value) {
                        filterSliderLikesData.update({
                            from: value
                        });
                        filterValue.likes = value;
                    }
                };
                var backTrackRepost = function (value) {
                    if (filterValue.reposts != value) {
                        filterSliderRepostsData.update({
                            from: value
                        });
                        filterValue.reposts = value;
                    }
                };

                var backTrackComments = function (value) {
                    if (filterValue.comments != value) {
                        filterSliderCommentsData.update({
                            from: value
                        });
                        filterValue.comments = value;
                    }
                };


                $(".filter-number-spinner").removeClass("j4f-fix-spinner-un-script");
                $('.filter-number-spinner[name="likes"]').bootstrapNumber({
                    upClass: 'success',
                    downClass: 'danger',
                    upIcon: 'fa fa-thumbs-up',
                    downIcon: 'fa fa-thumbs-down',
                    onUpdate: backTrackLikes
                });
                $('.filter-number-spinner[name="reposts"]').bootstrapNumber({
                    upClass: 'success',
                    downClass: 'danger',
                    upIcon: 'glyphicon glyphicon-share-alt',
                    onUpdate: backTrackRepost

                });
                $('.filter-number-spinner[name="comments"]').bootstrapNumber({
                    upClass: 'success',
                    downClass: 'danger',
                    upIcon: 'fa fa-comment',
                    onUpdate: backTrackComments
                });



                var $likes_count_input = $("[name='likes']"),
                        $reposts_count_input = $("[name='reposts']"),
                        $comments_count_input = $("[name='comments']");

                $likes_count_input.on("change paste keyup", function(){
                    backTrackLikes($likes_count_input.val());
                });
                $reposts_count_input.on("change paste keyup", function(){
                    backTrackRepost($reposts_count_input.val());
                });

                $comments_count_input.on("change paste keyup", function(){
                    backTrackComments($comments_count_input.val());
                });
                var trackLikes = function (data) {
                    if (filterValue.likes != data.from) {
                        //alert(data.from);
                        $likes_count_input.val(data.from);
                        filterValue.likes = data.from;
                    }
                };
                var trackPeposts = function (data) {
                    if (filterValue.reposts != data.from) {
                        $reposts_count_input.val(data.from);
                        filterValue.reposts = data.from;
                    }
                };
                var trackComments = function (data) {
                    if (filterValue.comments != data.from) {
                        $comments_count_input.val(data.from);
                        filterValue.comments = data.from;
                    }
                };
                filterSliderLikesData.update({
                    onStart: trackLikes,
                    onChange: trackLikes,
                    onFinish: trackLikes,
                    onUpdate: trackLikes
                });
                filterSliderRepostsData.update({
                    onStart: trackPeposts,
                    onChange: trackPeposts,
                    onFinish: trackPeposts,
                    onUpdate: trackPeposts
                });
                filterSliderCommentsData.update({
                    onStart: trackComments,
                    onChange: trackComments,
                    onFinish: trackComments,
                    onUpdate: trackComments
                });

                scriptCallBack.push(function (map) {
                    newFilterPrefix.likes = map.get("srcFilterLikes");
                    newFilterPrefix.reposts = map.get("srcFilterReposts");
                    newFilterPrefix.comments = map.get("srcFilterComments");
                    newFilterPostfix.nom = map.get("nmbFilterMultiNom");
                    newFilterPostfix.gen = map.get("nmbFilterMultiGen");
                    newFilterPostfix.plu = map.get("nmbFilterMultiPlu");
                    getPostfix = function (num) {
                        return units(num, {
                            nom: newFilterPostfix.nom,
                            gen: newFilterPostfix.gen,
                            plu: newFilterPostfix.plu
                        });
                    };
                    //alert(typeof getPostfix == 'function')
                    filterSliderLikesData.update({
                        prefix: newFilterPrefix.likes + " ",
                        postfix: getPostfix
                    });
                    filterSliderRepostsData.update({
                        prefix: newFilterPrefix.reposts + " "
                    });
                    filterSliderCommentsData.update({
                        prefix: newFilterPrefix.comments + " "
                    });
                });
            });
        </script>
    </div>
</div>