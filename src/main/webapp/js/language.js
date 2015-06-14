$( document ).ready(function() {
    $(".language-button-logic").click(function(){
        var lang = $(this).attr("change");
        var names =[];i=0;
        $(".loc-t").each(function(){
            names[i++]=$(this).attr("locres")
        });

        var myJsonString = JSON.stringify(names);
        $.post( "controller?action=locale&lang=".concat(lang),{ names:myJsonString})
            .done(function( data ) {
                var map = data;
                $(".loc-t").each(function(){
                    $(this).text(map[$(this).attr("locres")]);
                })
            });
    })
});