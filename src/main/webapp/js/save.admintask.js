$(document).ready(function () {
    $("#task-save").click(function () {
        var source = [];
        $("#tokenize_focus > option[selected]").each(function () {
            source.push($(this).attr("value"));
        });

        var task_id = $("input[name=task_id]").val();

        var destination = [];
        //destination.push(-1);

        var options = new Object();
        options.grabbing_type = 'new';

        options.posting_type = 'FAVORITE';//
        options.repeat = 'REPEAT_DISABLE';//
        options.repeat_count = 10;//

        options.start_time = 'INTERVAL';
        options.work_time = 'ROUND_DAILY';

        var content_type = [];
        $("input[name=content_type]:checked").each(function () {
            content_type.push($(this).val());
        });

        options.actions = 'DO_NOTHING';//

        options.hashtags = $("input[name=wordsinput]").val();

        options.signature = '';//

        //group
        options.interval_min = $("input[name=interval_min][type='number']").val();
        options.interval_max = $("input[name=interval_max][type='number']").val();
        options.post_count = 1;
        options.post_delay_min = $("input[name=post_delay_min][type='number']").val();
        options.post_delay_max = $("input[name=post_delay_max][type='number']").val();
        options.grabbing_mode = 'total';


        var filter = new Object();
        filter.likes = $("input[name=likes]").val();
        filter.reposts = $("input[name=reposts]").val();
        filter.comments = $("input[name=comments]").val();
        filter.min_time = 3600;
        filter.max_time = 7 * 24 * 3600;

        var myJsonString = JSON.stringify({
            source: source,
            options: options,
            content_type: content_type,
            destination: destination,
            filter: filter
        });
        //alert(myJsonString);
        $.post("/task?action=save", {data: myJsonString})
            .done(function (data) {
                $("input[name=task_id]").val(data.newId);
                $("#task-id-loc").text("Task #"+data.newId);
                if (data.warning != null) {
                    setTimeout(function () {
                        toastrNotification("success", "Succeed Saved. But..." + data.warning);
                    }, 500);
                }
                else {
                    setTimeout(function () {
                        toastrNotification("success", "Succeed Saved. All Saved Correctly!");
                    }, 500);
                }
            })
            .fail(function () {
                toastrNotification("error", "Saved  error.");
            });

    });
});