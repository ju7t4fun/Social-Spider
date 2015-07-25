<%@page contentType="text/javascript" %>
        <%@taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>

        $(document).ready(function () {
            var web_socket_user_limits = new WebSocket("ws://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/websocket/limits-info");
            web_socket_user_limits.onerror = function(evt) {
                toastrNotification("error", "Error with WebSocket. "+ evt.data);
            };
            web_socket_user_limits.onmessage = function (event) {
                if(typeof event.data == 'string'){
                    var response_json = JSON.parse(event.data);
                    var change = response_json.change;
                    var $change_object = $('.j4f-'+change+'-value');
                    if($change_object.length<=0){
                        return;
                    }
                    console.log({data:event.data});
                    var value = response_json.data.value;
                    var percent = response_json.data.percent;
                    console.log({change:change,value:value,percent:percent});
                    $change_object.text(value);
                    $change_object.parent('.progress-bar').attr('aria-valuenow',percent);
                    $change_object.parent('.progress-bar').attr('style','width: '+percent+'%');
                }else{
                    toastrNotification("error", 'Not equals data type. Please reload page!');
                    console.log('typeof event.data: '+typeof event.data);
                }
                //toastrNotification("success", "Data: "+ event.data);

            };
        });