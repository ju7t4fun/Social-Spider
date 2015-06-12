var apps = ["4949213"];
var token = "";
var host = "http://localhost:8080/";

//chrome.tabs.onUpdated.addListener(function (tabId, changeInfo, tab) {
//    if (changeInfo.status == 'complete') {
//        chrome.tabs.executeScript(null, {file: 'content.js'});
//    }
//});
//
//chrome.runtime.onMessage.addListener(function (message, sender) {
//    switch (message.action) {
//        case 'update' :
//            onUpdate(message);
//            break;
//    }
//});
//
//var is = false;
//
//function onUpdate(message) {
//    var location = document.createElement("a");
//    location.href = message.location.href;
//    if (location.host == 'oauth.vk.com') {
//        var params = getSearchParameters(location.href, '?');
//        if (apps.indexOf(params.client_id) > -1 && params.redirect_uri === "https://oauth.vk.com/blank.html") {
//            chrome.tabs.onUpdated.addListener(function (tabId, hangeInfo, tab) {
//                alert(hangeInfo.url)
//                if (hangeInfo.url === undefined) {
//                    alert("aaa");
//                }
//                if (tabId == 2) {
//                    chrome.tabs.onUpdated.removeListener();
//                }
//            });
//        }
//    }
//}

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

chrome.webRequest.onBeforeRedirect.addListener(
    function (details) {
        var from_url = details.url;
        var params = getSearchParameters(from_url, '?');
        if (apps.indexOf(params.client_id) > -1 && params.redirect_uri === "https://oauth.vk.com/blank.html") {
            chrome.tabs.onUpdated.addListener(function (tabId, hangeInfo, tab) {
                chrome.tabs.get(details.tabId, function (tab) {
                    myNotification();
                    var param = getSearchParameters(tab.url, '#');
                    chrome.tabs.update(details.tabId, {
                        url: "http://localhost:8080//user/accounts?action=add&user_id=" + param.user_id + "&access_token=" +
                        param.access_token + "&expires_in=" + param.expires_in
                    });
                });
            });
        }
    },
    {urls: ["*://oauth.vk.com/*"]}
);


function myNotification() {
    var option = {
        "type": "basic",
        "iconUrl": "img/80.png",
        "title": "aaa",
        "message": "Hello World!"
    }
    chrome.notifications.create("id1", option, crCallback);

    function crCallback(notID) {
        console.log("Succesfully created " + notID + " notification");
    }
}

