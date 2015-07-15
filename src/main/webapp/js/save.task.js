/**
 * Created by shell on 6/27/2015.
 */
$(document).ready(function () {
    $("#task-save").click(function () {
        var task_id = $("input[name=task_id]").val();
        var source = [];
        $("#tokenize_focus_source_walls > option[selected]").each(function () {
            source.push($(this).attr("value"));
        });

        if (source.length == 0) {
            toastrNotification("warning", "There are not source groups selected!");
            return
        }

        var destination = [];
        $("#tokenize_focus_destination_walls > option[selected]").each(function () {
            destination.push($(this).attr("value"));
        });

        if (destination.length == 0) {
            toastrNotification("warning", "There are not destination groups selected!");
            return
        }

        var options = new Object();
        options.grabbing_type = $("input[name=grabbing_type][type='radio']:checked").val();
        options.posting_type = $("input[name=posting_type][type='radio']:checked").val();
        options.repeat = $("input[name=repeat][type='radio']:checked").val();
        options.repeat_count = $("input[name=repeat_days][type='number']").val();
        options.start_time = $("input[name=start_time][type='radio']:checked").val();
        options.work_time = $("input[name=work_time][type='radio']:checked").val();

        var content_type = [];
        $("input[name=content_type]:checked").each(function () {
            content_type.push($(this).val());
        });
        options.actions = $("input[name=actions][type='radio']:checked").val();
        options.hashtags = $("input[name=wordsinput]").val();
        options.signature = $("textarea[name=addtext]").val();
        //group
        options.interval_min = $("input[name=interval_min][type='number']").val();
        options.interval_max = $("input[name=interval_max][type='number']").val();
        options.post_count = $("input[name=post_count][type='number']").val();
        options.post_delay_min = $("input[name=post_delay_min][type='number']").val();
        options.post_delay_max = $("input[name=post_delay_max][type='number']").val();
        options.grabbing_mode = $("input[name=grabbing_mode][type='radio']:checked").val();


        var filter = new Object();
        filter.likes = $("input[name=likes]").val();
        filter.reposts = $("input[name=reposts]").val();
        filter.comments = $("input[name=comments]").val();
        filter.min_time = 3600;
        filter.max_time = 7 * 24 * 3600;

        var myJsonString = JSON.stringify({
            taskId: task_id,
            source: source,
            destination: destination,
            options: options,
            content_type: content_type,
            filter: filter
        });
        $.post("task?action=save", {data: myJsonString})
            .done(function (data) {
                $("input[name=task_id]").val(data.newId);
                $("#task-id-loc").text("Task #" + data.newId);
                if (data.warning != null) {
                    setTimeout(function () {
                        toastrNotification("success", "Succeed Saved. But..." + data.warning);
                        setTimeout(function () {
                            location.href = "/task";
                        }, 2000);
                    }, 500);
                }
                else {
                    location.href = "/task";
                }
            })
            .fail(function (jqXHR) {
                if (jqXHR.status == 401) {
                    toastrNotification("error", "Death session. Please ReLogin.");
                } else {
                    toastrNotification("error", "Saved  error.");
                }
            });

    });
});