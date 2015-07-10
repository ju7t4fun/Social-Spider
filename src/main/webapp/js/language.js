$(document).ready(function () {
    $(".btn").click(function () {
        var lang = $(this).attr("change");
        var names = [];
        i = 0;
        $(".loc-t, .loc-p").each(function () {
            names[i++] = $(this).attr("locres")
        });

        var myJsonString = JSON.stringify(names);
        $.post("http://localhost:8080/controller?action=locale&lang=".concat(lang), {names: myJsonString})
            .done(function (data) {
                var map = data;
                $(".loc-t").each(function () {
                    $(this).text(map[$(this).attr("locres")]);
                });
                $(".loc-p").each(function () {
                    $(this).attr("placeholder", map[$(this).attr("locres")]);
                })
            });
    })
});