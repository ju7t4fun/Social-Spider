var apps = ["4949213"];
var host = "/social-copybot.rhcloud.com";

// Файл конфігурації default
var config = {
    user_id: '0',
    message: '',
    filter: {
        info: false,
        success: false,
        warning: false,
        error: false
    }
};

// Виконується при запуску
window.addEventListener('load', function () {
    chrome.runtime.sendMessage({'action': 'update'});
});

// Слухаєм повідомлення про оновлення конфігурації
chrome.runtime.onMessage.addListener(function (message, sender) {
    switch (message.action) {
        case "update":
            chrome.storage.local.get({'config': {}}, function (items) {
                if (items.config.user_id !== undefined) {
                    config = items.config;
                    if (config.user_id > 0)
                        createSocket();
                }
            });
            break;
        case "signOut":
            config.user_id = 0;
            webSocket.close();
            break;
    }
});

var webSocket;

// Створення нового WebSocket-у
function createSocket() {
    if (config.user_id > 0) {
        webSocket = new WebSocket("ws://localhost:8080/spider/websocket/event/" + config.user_id);

        webSocket.onopen = function (event) {
            //alert("onOpen " + config.user_id);
        };

        webSocket.onmessage = function (event) {
            //alert("onMessage "  + config.user_id);
            createNotification(JSON.parse(event.data), -1);
        };

        webSocket.onclose = function (event) {
            //alert("onClose " + config.user_id)
            setTimeout(createSocket(), 25000);
        };
    }
}

// Виконується при оновленні сторінки
chrome.tabs.onUpdated.addListener(function (tabId, changeInfo, tab) {
    if (changeInfo.status === 'complete') {
        onAuth(tabId, tab.url);
    }
});

// Виконується при перенаправленні
chrome.webRequest.onBeforeRedirect.addListener(
    function (details) {
        onAuth(details.tabId, details.url);
    },
    {urls: ["*://oauth.vk.com/*"]}
);

// Опрацювання вікна авторизації
function onAuth(tabId, from_url) {

    var params = getSearchParameters(from_url, '?');
    if (apps.indexOf(params.client_id) > -1 && params.redirect_uri === "https://oauth.vk.com/blank.html") {
        chrome.tabs.onUpdated.addListener(parseAccessToken);
    }

    // Парсимо URL перенаправленої Вк сторінки
    function parseAccessToken(tabId, hangeInfo, tab) {
        if (hangeInfo.status === 'complete') {
            var param = getSearchParameters(tab.url, '#');
            chrome.tabs.update(tabId, {
                url: host + "accounts?action=add&user_id=" + param.user_id + "&access_token=" + param.access_token
                + "&expires_in=" + param.expires_in
            });
            chrome.tabs.onUpdated.removeListener(parseAccessToken);
        }
    }
}

// Розбиваємо URL по параметрам
function transformToAssocArray(prmstr, sep) {
    var params = {};
    var prmarr = prmstr.split(sep);
    prmarr = prmarr[1].split("&");
    for (var i = 0; i < prmarr.length; i++) {
        var tmparr = prmarr[i].split("=");
        params[tmparr[0]] = tmparr[1];
    }
    return params;
}

function getSearchParameters(url, sep) {
    return url != null && url != "" ? transformToAssocArray(url, sep) : {};
}

// Створення повідомлення
function createNotification(options, millisecond) {

    // Створення нового повідомлення якщо відповідають фільтру
    if (matchesFilter(options))
        chrome.notifications.create(options, crCallback);

    // Встановлення зображення відносно типу та перевірка відповідності фільтру
    function matchesFilter(msg) {
        var isShow;
        switch (msg.typeNotification) {
            case "SUCCESS":
                msg.iconUrl = "icon/spider_success.png";
                isShow = config.filter.success;
                break;
            case "INFO":
                msg.iconUrl = "icon/spider_info.png";
                isShow = config.filter.info;
                break;
            case "WARN":
                msg.iconUrl = "icon/spider_warning.png";
                isShow = config.filter.warning;
                break;
            case "ERROR" :
                msg.iconUrl = "icon/spider_error.png";
                isShow = config.filter.error;
                break;
        }
        delete msg.typeNotification;
        return isShow;
    }

    // Закриття після n - мілісекунд
    function crCallback(notId) {
        if (millisecond >= 0) {
            setTimeout(function () {
                chrome.notifications.clear(notId);
            }, millisecond);
        }
    }
}
