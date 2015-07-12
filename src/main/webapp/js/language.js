var scriptLocaleStorage = new Map();
var scriptCallBack = [];
function update(){
    scriptCallBack.forEach(function(value) {
        value(scriptLocaleStorage);
    })
}
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
        $.post("http://localhost:8080/controller?action=locale&lang=".concat(lang), {names: myJsonString})
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