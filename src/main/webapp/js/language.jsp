<%@page contentType="text/javascript" %>

        var scriptLocaleStorage = new Map();
var scriptCallBack = [];
//var updateNeed = true, updating = false;
function update(){
    scriptCallBack.forEach(function(value) {
        value(scriptLocaleStorage);
    })
}
scriptLocaleStorage.set("x","2");
function j4fBundlePut(key,value){
    scriptLocaleStorage.set(key,value);
}
function j4fBundleMark(key){
    scriptLocaleStorage.set(key,key);
}
function j4fBundle(key){
    return scriptLocaleStorage.get(key);
}
        function j4fBundleCallBack(callBackFunction){
            return scriptCallBack.push(callBackFunction);
        }
function scriptStorageUpdate(){
    var names = [];
    scriptLocaleStorage.forEach(function(value, key) {
        names.push(key);
    });
    var myJsonString = JSON.stringify(names);
    $.post("${pageContext.request.contextPath}/controller?action=locale", {names: myJsonString})
        .done(function (data) {
            var map = data;
            scriptLocaleStorage.forEach(function(value, key, storageMap) {
                var tmp =  map[key];
                if(tmp!=null){
                    storageMap.set(key,tmp);
                }
            });
            update();
        });
}
$(document).ready(function () {
    scriptStorageUpdate();
});
$(document).ready(function () {
    $(".btn").click(function () {
        var lang = $(this).attr("change");
        var names = [];
        $(".loc-t, .loc-p").each(function () {
            names.push( $(this).attr("locres"));
        });
        scriptLocaleStorage.forEach(function(value, key) {
            names.push(key);
        })
        var myJsonString = JSON.stringify(names);
        $.post("${pageContext.request.contextPath}/controller?action=locale&lang=".concat(lang), {names: myJsonString})
            .done(function (data) {
                var map = data;
                $(".loc-t").each(function () {
                    $(this).text(map[$(this).attr("locres")]);
                });
                $(".loc-p").each(function () {
                    $(this).attr("placeholder", map[$(this).attr("locres")]);
                });
                scriptLocaleStorage.forEach(function(value, key, storageMap) {
                    var tmp =  map[key];
                    if(tmp!=null){
                        storageMap.set(key,tmp);
                    }
                });
                update();
            });
    })
});