<%@page contentType="text/javascript" %>
var webSocket = new WebSocket("ws://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/websocket/support");

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
            newUser(args);
            break;
        case "me":
            me(args);
            break;
        case "updateCountList":
            updateCountList(args);
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

function send(id) {
    var edit = document.getElementById("message_text");
    if (edit.value !== "") {
        webSocket.send("to_user|" + id + "|" + edit.value);
        edit.value = "";
    }
}

// Повертає усі повідомлення
function inboxBody() {
    var body = "";
    var inbox = document.getElementById("support_inbox_a");
    var node = inbox.children;
    for (var i = 0; i < inbox.childElementCount; i++) {
        body = body + node[i].outerHTML;
    }
    return body;
}

function me(args) {
    document.getElementById("support_inbox_a").innerHTML =
        inboxBody() + document.getElementById("admin-message_a").outerHTML
            .replace("{message}", args[1])
            .replace("{date}", args[2]);
    document.getElementById("support_inbox_a").scrollTop = document.getElementById("support_inbox_a").scrollHeight;
}

function newUser(args) {
    if (document.getElementById("current_user_id").value === args[1]) {
        document.getElementById("support_inbox_a").innerHTML =
            inboxBody() + document.getElementById("me-message_a").outerHTML
                .replace("{user_name}", args[2])
                .replace("{message}", args[4])
                .replace("{date}", args[3]);
        document.getElementById("support_inbox_a").scrollTop = document.getElementById("support_inbox_a").scrollHeight;
    }
    //document.getElementById("list-" + args[1]).innerHTML =
    //    '<span class="small italic pull-right badge bg-warning">new</span>';
    document.getElementById("support_inbox_a").scrollTop = document.getElementById("support_inbox_a").scrollHeight;
}

function updateCountList(args) {
    document.getElementById("list-" + args[1]).innerHTML =
        '<span class="small italic pull-right badge bg-warning">new</span>';
}