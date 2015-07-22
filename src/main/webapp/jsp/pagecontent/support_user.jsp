<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
<script type="text/javascript">

    var webSocket = new WebSocket("ws://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/websocket/support");

    webSocket.onopen = function (event) {
    };

    // Опрацювання команд
    webSocket.onmessage = function (event) {
        var args = event.data.split("|");
        switch (args[0]) {
            case "count":
                changeUnReadCount(args[1]);
                break;
            case "me":
                me(args);
                break;
            case "admin":
                newMessage(args);
                break;
            case "previous":
                previousMessage(args);
                break;
            case "new":
                var audio = new Audio();
                audio.src = '../../sound/message.mp3';
                audio.autoplay = true;
        }
    };

    webSocket.onclose = function (event) {
    };

    // Змінюєм кількість не прочитаних повідомлень
    function changeUnReadCount(count) {
        document.getElementById("count_unread_messages").innerHTML = count;
    }

    // Надсилаєм (не пусті) повідомлення
    function send() {
        var edit = document.getElementById("message_text");
        if (edit.value !== "") {
            webSocket.send("to_admin|" + edit.value);
            edit.value = "";
        }
    }

    // Повертає усі повідомлення
    function inboxBody() {
        var body = "";
        var inbox = document.getElementById("support_inbox");
        var node = inbox.children;
        for (var i = 0; i < inbox.childElementCount; i++) {
            body = body + node[i].outerHTML;
        }
        return body;
    }

    // Відображення мого повідомлення
    function me(args) {
        document.getElementById("support_inbox").innerHTML =
                inboxBody() + document.getElementById("me-message").outerHTML
                        .replace("{message}", args[1])
                        .replace("{date}", args[2]);
        document.getElementById("support_inbox").scrollTop = document.getElementById("support_inbox").scrollHeight;
    }

    // Відображення нового повідомлення
    function newMessage(args) {
        document.getElementById("support_inbox").innerHTML =
                inboxBody() + document.getElementById("admin-message").outerHTML
                        .replace("{message}", args[1])
                        .replace("{date}", args[2]);
        document.getElementById("support_inbox").scrollTop = document.getElementById("support_inbox").scrollHeight
    }

    // Опрацювання прокрутки
    function onScroll() {
        var inbox = document.getElementById("support_inbox");
        if (inbox.scrollTop === 0) {
            webSocket.send("scroll|" + inbox.childElementCount + "|1");
            inbox.scrollTop = 1;
        }
    }

    // Завантаження попередніх повідомлень
    function previousMessage(args) {
        var inbox = document.getElementById("support_inbox");
        switch (args[1]) {
            case "TO_ADMIN":
                inbox.innerHTML = document.getElementById("me-message").outerHTML
                                .replace("{message}", args[2])
                                .replace("{date}", args[3]) + inboxBody();
                break;
            case "TO_USER":
                inbox.innerHTML = document.getElementById("admin-message").outerHTML
                                .replace("{message}", args[2])
                                .replace("{date}", args[3]) + inboxBody();
                break;
        }
    }

    function onOpenInbox() {
        webSocket.send("read|");
        changeUnReadCount(0);
    }

</script>

<div hidden>
    // Повідомлення від користувача
    <li id="me-message" class="by-me">
        <div class="avatar pull-left">
            <img src="${user.avatarURL}" alt="" width="40" height="40"/>
        </div>
        <div class="chat-content">
            <div class="chat-meta">${user.name} ${user.surname}<span class="pull-right">{date}</span></div>
            <div class="chat-meta" style="color:#4c4c4c;font-size:14px;">{message}</div>
            <div class="clearfix"></div>
        </div>
    </li>
    // Повідомлення від адміна
    <li id="admin-message" class="by-other">
        <div class="avatar pull-right">
            <img src="${pageContext.request.contextPath}/img/admin.jpg" alt="" width="40" height="40"/>
        </div>
        <div class="chat-content">
            <div class="chat-meta">{date}<span class="pull-right"> <l:resource key="header.admin"/> </span>
            </div>
            <div class="chat-meta" style="color:#4c4c4c;font-size:14px;">{message}</div>
            <div class="clearfix"></div>
        </div>
    </li>
</div>

<li id="mail_notificatoin_bar" class="dropdown">
    <a data-toggle="dropdown" class="dropdown-toggle" href="#" onclick="onOpenInbox()">
        <i class="icon-envelope-l"></i>
        <span id="count_unread_messages" class="badge bg-important"> </span>
    </a>
    <ul class="dropdown-menu extended inbox" style="opacity: 1">
        <div class="notify-arrow notify-arrow-blue"></div>
        <li style="width:370px;">
            <p class="blue"><l:resource key="header.seeallmessages"/></p>
        </li>

        <div class="col-md-4 portlets" style="width:400px; margin-left:-15px;height:200px;">
            <div class="panel panel-default">
                <div class="panel-body">
                    <div class="padd sscroll">
                        <ul id="support_inbox" onscroll="onScroll()" class="chats" style="overflow: scroll; width:
                        339px; height: 400px; padding: 5px; overflow-x:hidden">
                            // Вміст
                        </ul>
                    </div>
                    <br>

                    <div class="widget-foot">
                        <form class="form-inline">
                            <div class="form-group" style="width: 65%">
                                <l:resource key="header.typemessagehere"> <input style="width: 100%" id="message_text"
                                                                                 type="text" class="form-control"
                                                                                 placeholder=""></l:resource>
                            </div>
                            <a onclick="send()" class="btn btn-info" style="margin-left:5%"><l:resource
                                    key="header.send"/></a>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </ul>
</li>