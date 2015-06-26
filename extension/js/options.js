var host = "http://localhost:8080/";

// Файл конфігурації default
var config = {
    user_id: '0',
    message: '',
    locale: "locales/ua.json",
    filter: {
        info: true,
        success: true,
        warning: true,
        error: true
    }
};

// Загрузка при відкриті сторінки
window.addEventListener('load', function () {
    // Загрузка конфігурації
    chrome.storage.local.get({'config': {}}, function (items) {
        if (items.config.user_id !== undefined)
            config = items.config;
        refreshPage();
    });
    onSignIn();
    onSignOut();
    onNotificationFilter();
    onLocale();
});

// Опрацювання кнопока локалізації
function onLocale() {
    $("#ua").click(function () {
        config.locale = "locales/ua.json";
        chrome.storage.local.set({'config': config});
        refreshPage();
    });
    $("#en").click(function () {
        config.locale = "locales/en.json";
        chrome.storage.local.set({'config': config});
        refreshPage();
    });
}

// Опрацювання авторизації
function onSignIn() {
    document.getElementById("signIn").onclick = function () {
        var message = $("#message");
        var email = document.getElementById("email").value;
        var password = document.getElementById("password").value;

        var request = new XMLHttpRequest();
        request.open("GET", host + "api/method/auth?email=" + email + "&password=" + password, true);
        request.onreadystatechange = function () {
            if (request.readyState == 4) {
                var response = JSON.parse(request.responseText);
                // Опрацювання успішної авторизації
                if (response.response === "success") {
                    message.attr("class", "alert alert-success");
                    $("#register_form").attr("hidden", "");
                    $("#signOut").removeAttr("hidden");
                    $("#notification").removeAttr("hidden");

                    config.user_id = response.user_id;
                    config.message = response.message;
                    updateConfiguration();
                    refreshPage();
                }
                if (response.response === "warning")
                    message.attr("class", "alert alert-warning");
                if (response.response === "error")
                    message.attr("class", "alert alert-danger");
                message.html(response.message);
                message.removeAttr("hidden")
            }
        };
        request.send();
    }
}

// Завершення поточної сесії
function onSignOut() {
    $("#signOutButton").click(function () {
        $("#register_form").removeAttr("hidden");
        $("#signOut").attr("hidden", "");
        $("#message").attr("hidden", "");
        $("#notification").attr("hidden", "");
        config.user_id = 0;
        chrome.runtime.sendMessage({'action': 'signOut'});
        chrome.storage.local.remove(['config']);
    });
}

// Опрацювання вибору фільтра
function onNotificationFilter() {
    $("#infoCheckBox").click(function () {
        config.filter.info = !config.filter.info;
        updateConfiguration();
    });
    $("#successCheckBox").click(function () {
        config.filter.success = !config.filter.success;
        updateConfiguration();
    });
    $("#warningCheckBox").click(function () {
        config.filter.warning = !config.filter.warning;
        updateConfiguration();
    });
    $("#errorCheckBox").click(function () {
        config.filter.error = !config.filter.error;
        updateConfiguration();
    });
}

// Оновлення конфігурації на сторінці
function refreshPage() {

    // Установка форми авторизації
    if (config.user_id > 0) {
        $("#register_form").attr("hidden", "");
        $("#signOut").removeAttr("hidden");
        var message = $("#message");
        message.attr("class", "alert alert-success");
        message.html(config.message);
        message.removeAttr("hidden");
    } else {
        $("#notification").attr("hidden", "");
    }

    // Установка фільтрів
    if (config.filter.info)
        $("#infoCheckBox").attr("checked", "");
    if (config.filter.success)
        $("#successCheckBox").attr("checked", "");
    if (config.filter.warning)
        $("#warningCheckBox").attr("checked", "");
    if (config.filter.error)
        $("#errorCheckBox").attr("checked", "");

    // Локалізація
    $.getJSON(config.locale, function (locale) {
        $("#email").attr("placeholder", locale.email);
        $("#password").attr("placeholder", locale.password);
        $("#signIn").attr("value", locale.signIn);
        $("#signOutButton").html(locale.signOut);
        $("#notificationFilter").html(locale.notificationFilter);
        $("#info").html(locale.info);
        $("#success").html(locale.success);
        $("#warning").html(locale.warning);
        $("#error").html(locale.error);
    });
}

// Зберігаєм та оновлюєм конфігурацію
function updateConfiguration() {
    chrome.storage.local.set({'config': config});
    chrome.runtime.sendMessage({'action': 'update'});
}
