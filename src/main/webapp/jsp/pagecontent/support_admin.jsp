<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">

    var webSocket = new WebSocket("ws://localhost:8080/support");

    webSocket.onopen = function (event) {
    };

    // Опрацювання команд
    webSocket.onmessage = function (event) {
        var args = event.data.split("|");
        switch (args[0]) {
            case "count":
                changeCountUnRead(args[1]);
                break;
            case "new":
                addNew(args);
        }
    };

    webSocket.onclose = function (event) {
    };

    function changeCountUnRead(count) {
        document.getElementById("count_unread_messages").innerHTML = count;
    }

    function addNew(args) {
        var body = document.getElementById("message").outerHTML
                .replace("{user_id}", args[1])
                .replace("{user_name}", args[2])
                .replace("{user_date}", args[3]);
        var children = document.getElementById("message_chat").children;
        for (var i = 0; i < children.length && i < 5; i++) {
            body = body + children[i].outerHTML;
        }
        document.getElementById("message_chat").innerHTML = body;
    }

</script>

<div hidden>
    <li id="message">
        <a href="/admin/inbox?id={user_id}">
            <span class="label label-primary"><i class="icon_profile"></i></span>{user_name}
            <span class="small italic pull-right">{user_date}</span>
        </a>
    </li>
</div>

<li id="mail_notificatoin_bar" class="dropdown">
    <a data-toggle="dropdown" class="dropdown-toggle" href="#" onclick="onOpenInbox()">
        <i class="icon-envelope-l"></i>
        <span id="count_unread_messages" class="badge bg-important">0</span>
    </a>
    <ul class="dropdown-menu extended notification">
        <div class="notify-arrow notify-arrow-blue"></div>
        <li>
            <p class="blue">See all messages</p>
        </li>
        <div id="message_chat">

        </div>
        <li>
            <a href="/admin/inbox">See all messages</a>
        </li>
    </ul>
</li>