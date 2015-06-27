<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">

    var webSocketN = new WebSocket("ws://localhost:8080/websocket/notification");

    webSocketN.onopen = function (event) {
    };

    // Опрацювання команд
    webSocketN.onmessage = function (event) {
        var args = event.data.split("|");
        switch (args[0]) {
            case "count":
                changeCount(args[1]);
                break;
            case "notf":
                addNewNotification(args);

        }
    };

    webSocketN.onclose = function (event) {
    };

    function changeCount(count) {
        document.getElementById("count-notification").innerHTML = count;
        document.getElementById("count_notification_a").innerHTML = count;
    }

    function addNewNotification(args) {
        var body;
        switch (args[1]) {
            case "INFO":
                body = document.getElementById("notification-item").outerHTML
                        .replace("{title}", args[2])
                        .replace("{time}", args[3])
                        .replace("{type}", "label label-info")
                        .replace("{icon}", " icon_info");
                break;
            case "SUCCESS":
                body = document.getElementById("notification-item").outerHTML
                        .replace("{title}", args[2])
                        .replace("{time}", args[3])
                        .replace("{type}", "label label-success")
                        .replace("{icon}", " icon_check");
                break;
            case "WARN":
                body = document.getElementById("notification-item").outerHTML
                        .replace("{title}", args[2])
                        .replace("{time}", args[3])
                        .replace("{type}", "label label-warning")
                        .replace("{icon}", " icon_pin");
                break;
            case "ERROR":
                body = document.getElementById("notification-item").outerHTML
                        .replace("{title}", args[2])
                        .replace("{time}", args[3])
                        .replace("{type}", "label label-danger")
                        .replace("{icon}", "icon_error-triangle");
                break;
        }
        var children = document.getElementById("notf-body").children;
        for (var i = 0; i < children.length && i < 5; i++) {
            body = body + children[i].outerHTML;
        }
        document.getElementById("notf-body").innerHTML = body;
    }

</script>

<div hidden>
    <li id="notification-item">
        <a href="#">
            <span class="{type}">
                <i class="{icon}"></i></span>
            {title}
            <span class="small italic pull-right"> {time} </span>
        </a>
    </li>
</div>
<li id="alert_notificatoin_bar" class="dropdown">
    <a data-toggle="dropdown" class="dropdown-toggle" href="#">

        <i class="icon-bell-l"></i>
        <span id="count-notification" class="badge bg-important">0</span>
    </a>
    <ul class="dropdown-menu extended notification">
        <div class="notify-arrow notify-arrow-blue"></div>
        <li>
            <p class="blue">You have <span id="count_notification_a">4</span> new notifications</p>
        </li>
        <div id="notf-body">

        </div>

        <li>
            <a href="#">See all notifications</a>
        </li>
    </ul>
</li>