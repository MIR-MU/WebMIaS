$(document).ready(function() {
    MathJax.Hub.Queue(["Typeset",MathJax.Hub,"visualQuery"]);
    
    clickableExamples();
});

function clickableExamples() {
    $('.example').click(function() {
        var loc = $(location).attr('href');
        loc = loc.substring(0, loc.length-14);
        var eqs = $(this).find('.example-query-string');
        if (eqs) {
            loc = loc + "ps?" + eqs.text();
            console.log(loc);
            window.location = loc;
        }
    });
}
