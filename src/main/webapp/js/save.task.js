/**
 * Created by shell on 6/27/2015.
 */
$(document).ready(function () {
    $("#task-save").click(function () {
        var source = [];
        $("#tokenize_focus_source_walls > option[selected]").each(function () {
            source.push( $(this).attr("value"));
        });

        var destination = [];
        $("#tokenize_focus_destination_walls > option[selected]").each(function () {
            destination.push( $(this).attr("value"));
        });

        var options = new Object() ;
        options.grabbing_type = $("input[name=grabbing_type][type='radio']:checked").val();
        options.posting_type = $("input[name=posting_type][type='radio']:checked").val();
        options.repeat = $("input[name=repeat][type='radio']:checked").val();
        options.repeat_count = $("input[name=repeat_days][type='number']").val();
        options.start_time = $("input[name=start_time][type='radio']:checked").val();
        options.work_time = $("input[name=work_time][type='radio']:checked").val();

        var content_type = [];
        $("input[name=content_type]:checked").each(function () {
            content_type.push( $(this).val());
        });
        options.actions = $("input[name=actions][type='radio']:checked").val();
        options.hashtags = $("input[name=wordsinput]").val();
        options.signature = $("textarea[name=addtext]").val();


        options.interval_min = $("input[name=interval_min][type='number']").val();
        options.interval_max = $("input[name=interval_max][type='number']").val();
        options.post_count = 1;
        options.post_delay_min = $("input[name=post_delay_min][type='number']").val();
        options.post_delay_max = $("input[name=post_delay_max][type='number']").val();
        options.grabbing_mode = 'per_group';


        var filter = new Object() ;
        filter.likes = $("input[name=likes][type='number']").val();
        filter.reposts = $("input[name=reposts][type='number']").val();
        filter.comments = $("input[name=comments][type='number']").val();
        filter.min_time = 3600;
        filter.max_time = 7*24*3600;



        var myJsonString = JSON.stringify({source:source,destination:destination,options:options,content_type:content_type,filter:filter});



        $.post("task?action=save", {data: myJsonString})
            .done(function(data) {
                if(data.warning!=null){
                    alert(data.warning);
                }
                else
                alert("done");
            })
            .fail(function() {
                alert( "error" );
            });

    });
});