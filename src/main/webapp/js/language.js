function language(lang) {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "controller?action=locale&lang=" + lang, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            location.reload();
        }
    };
    xhr.send();
}