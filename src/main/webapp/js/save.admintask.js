$(document).ready(function () {
    $("#task-save").click(function () {
        var source = [];
        $("#tokenize_focus > option[selected]").each(function () {
            source.push($(this).attr("value"));
        });
        if (source.length == 0) {
            toastrNotification("warning", "НЕ вибрано груп");
            return
        }

        var destination = [];
        //destination.push(-1);

        var options = new Object();
        options.grabbing_type = 'new';

        options.posting_type = 'FAVORITE';//
        options.repeat = 'REPEAT_DISABLE';//
        options.repeat_count = 2;//

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
        filter.likes = $("input[name=likes][type='number']").val();
        filter.reposts = $("input[name=reposts][type='number']").val();
        filter.comments = $("input[name=comments][type='number']").val();
        filter.min_time = 3600;
        filter.max_time = 7 * 24 * 3600;

        var myJsonString = JSON.stringify({
            source: source,
            options: options,
            content_type: content_type,
            destination: destination,
            filter: filter
        });
        console.log(myJsonString);
        $.post("/task?action=save", {data: myJsonString})
            .done(function (data) {
                if (data.warning != null) {
                    toastrNotification("success", "Succeed Saved. But..." + data.warning);
                    location.href = "/task?action=showtasksforadmin";
                }
                else {
                    toastrNotification("success", "Succeed Saved. All Saved Correctly!");
                    location.href = "/task?action=showtasksforadmin";
                }
            })
            .fail(function () {
                toastrNotification("error", "Saved  error.");
            });

    });
});