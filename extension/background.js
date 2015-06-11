var apps = ["4949213"];
var token = "";
var host = "http://localhost:8080/";

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

