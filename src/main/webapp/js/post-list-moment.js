/**
 * Created by hell-engine on 7/19/2015.
 */
var momentStorage = new Map();

function displayData(){
    var locale = 'en';
    var x =  document.cookie.split(";");
    {
        for(var k = 0; k< x.length; k++){
            var pair = x[k].split("=");
            if(pair.length == 2){
                if(pair[0].trim() == 'language'){
                    if(pair[1] == 'ua') locale = 'uk';
                    else if(pair[1] == 'en') locale = 'en';
                    break;
                }
            }
        }
        //console.log({language:locale})
    }
    momentStorage.forEach(function(value, key) {
        $(".wood-timer[time_id='"+key+"']").text(value.locale(locale).fromNow());
    });
}

$(document).ready(function(){
    displayData();
    setInterval(function() {
        displayData();
    },5000);
    scriptCallBack.push(displayData)
});

function addMoment(id, moment){
    $(".wood-timer[time_id='"+id+"']").text(moment.fromNow());
    momentStorage.set(id,moment);
}
