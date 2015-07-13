/**
 * Возвращает единицу измерения с правильным окончанием
 *
 * @param {Number} num      Число
 * @param {Object} cases    Варианты слова {nom: 'час', gen: 'часа', plu: 'часов'}
 * @return {String}
 */
function units3x(num, cases) {
    num = Math.abs(num);

    var word = '';

    if (num.toString().indexOf('.') > -1) {
        word = cases.gen;
    } else {
        word = (
            num % 10 == 1 && num % 100 != 11
                ? cases.nom
                : num % 10 >= 2 && num % 10 <= 4 && (num % 100 < 10 || num % 100 >= 20)
                ? cases.gen
                : cases.plu
        );
    }
    return word;
}
function units2x(num, cases) {
    num = Math.abs(num);
    if(num == 1){
        return cases.nom;
    }else return cases.plu;
}
function units(num, cases, x){
    if (typeof(x)==='undefined') x = j4fBundle("x");
    if(x == "2"){
        return units2x(num, cases);
    }else{
        return units3x(num, cases);
    }
}

function unitsAuto(num, cases){
    var nom = j4fBundle(cases.nom),
        gen = j4fBundle(cases.gen),
        plu = j4fBundle(cases.plu);
    return units(num, {nom: nom,gen: gen,plu: plu});
}

function unitAutoDecorate(num, prefix, postfix, zero){
    var nom = j4fBundle(postfix.nom),
        gen = j4fBundle(postfix.gen),
        plu = j4fBundle(postfix.plu);
    if(num == 0) return j4fBundle(zero);
    var msg = '';
    msg += j4fBundle(prefix)+" "+num + " ";
    msg += units(num, {nom: nom,gen: gen,plu: plu});
    return msg;
}
