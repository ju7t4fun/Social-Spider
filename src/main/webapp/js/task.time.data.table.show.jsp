<%@page contentType="text/javascript" %>
<%@taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
        j4fBundleMark("task.stage.run");
        j4fBundleMark("task.stage.queered");
$(document).ready(function () {
    var web_socket_task_info = new WebSocket("ws://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/websocket/task-info");
    web_socket_task_info.onerror = function(evt) {
        toastrNotification("error", "Error with WebSocket. "+ evt.data);
    };
    web_socket_task_info.onmessage = function (event) {
        if(typeof event.data == 'string'){
            var response_json = JSON.parse(event.data);
            if(response_json.state == 'success') {
                var array_response = response_json.data;
                for (var i = 0; i < array_response.length; i++) {
                    var element = array_response[i];
                    //console.log({id: element.id, state: element.state, time: element.unix_time_ms});
                    var $target = $('.run-time-info[tifid="'+element.id+'"]');
                    if(element.state == 'runnable'){
                        var unix_time_ms = element.unix_time_ms;
                        if(typeof unix_time_ms == 'string'){
                            unix_time_ms = parseInt(element.unix_time_ms);
                        }
                        var pretty_moment = moment(unix_time_ms).fromNow();
                        $target.empty();
                        var $auto_moment = $("<span class='auto-moment-time'></span>");
                        $auto_moment.attr('time', unix_time_ms);
                        $auto_moment.text( pretty_moment);
                        $target.append('<span  class="loc-t" locres="task.stage.run">'+j4fBundle("task.stage.run")+' '+'</span>').append($auto_moment);
                    }else  if(element.state == 'running'){
                        $target.empty();
                        $target.append('<span  class="loc-t" locres="task.stage.queered">'+j4fBundle("task.stage.queered")+'</span>');
                    }else{
                        $target.empty();
                    }
                }
            }else if(response_json.state == 'error'){
                toastrNotification("error", 'Bad request! Please reload page! '+response_json.error);
                console.log('Bad request! Please reload page! '+response_json.error);
            }else{
                console.log('UNKNOWN ERROR!');
                toastrNotification("error", 'UNKNOWN ERROR!');
            }
        }else{
            toastrNotification("error", 'Not equals data type. Please reload page!');
            console.log('typeof event.data: '+typeof event.data);
        }
        //toastrNotification("success", "Data: "+ event.data);
        //console.log({data:event.data})
    };
    var processTaskInfoUpdate = function (){
        $(".tab-pane.active .onoffswitch .onoffswitch-checkbox:checked").each(function(index, element){
            var array_d = [];
            var id = $(this).attr('id').replace('myonoffswitch','');
            if(!$(this).parent().parent().parent().has('.run-time-info').length){
                //console.log("success");
                $(this).parent().parent().attr("style","display:inline-block;vertical-align: middle;");
                var $param_run_time_info = $('<div class="run-time-info" style="display:inline-block;vertical-align: middle; margin: 0 8px;"></div>');
                $param_run_time_info.attr('tifid',id);
                $(this).parent().parent().parent().append($param_run_time_info);
                $(this).parent().parent().parent().attr('style','text-align: left;');
                $(this).parent().parent().parent().append('<div style="clear: both;"></div>');
                array_d.push(id);
                <%--console.log({id:id, index:index, element:element});--%>
            }
            var taskInfoJsonString = JSON.stringify(array_d);
            web_socket_task_info.send(taskInfoJsonString);
        });

    };

    setTimeout(function() {
        processTaskInfoUpdate();
    },1000);
    setInterval(function() {
        processTaskInfoUpdate();
    },2500);

    setInterval(function() {
        $('.auto-moment-time').each(function(){
            var unix_time_ms = $(this).attr('time');
            if(typeof unix_time_ms == 'string'){
                unix_time_ms = parseInt(unix_time_ms);
            }
            var pretty_moment = moment(unix_time_ms).fromNow();
            $(this).text(pretty_moment);
        })
    },1000);
});