$(document).ready(function() {
    prefillClauses();
    renderQueryMath();
    $('#mathQuery').unbind('keyup');
    $('#mathQuery').keyup(function() {
        renderQueryMath();
    });    
    $('.field-input').keyup(function(event) {
        if (event.which < 37 || event.which > 40) {
            suggest($(this));
        }
    });
    $("#mathQuery").tooltip({position: { my: "left-100 center", at: "right center" }});
    createTextQueryTooltip();
});

function createTextQueryTooltip() {    
    $(".field-input").tooltip({position: { my: "left-100 center", at: "right center" }, content: "Start typing your text query to see common mathematical collocations"});
    $(".field-select").tooltip({position: {my: "center bottom", at: "right top" }, content: "Select document field for this query"});
}

function checkForm(form) {
    if (form.query.value == "") {
        return false;
    } else {
        return true;
    }
}

function buildQuery() {    
    var operatorSelect = $('#operator').val();
    var operator = "";
    if (operatorSelect == 'all') {
        operator = "AND";
    }
    var queryClauses = [];
    var clauses = $('.query-clause');
    var query = "";
    clauses.each(function(index) {
        var field = $(this).find('.field-select').val();
        var val = $(this).find('.field-input').val();
        if (val) {
            var result = val;
            if (field != 'any') {
                result = field + ":\"" + val + "\"";
            }
            queryClauses.push({'field': field, 'value': val});
            query += result + " ";
            if (operator != '' && index != clauses.size() - 1) {
                query += operator + " ";
            }
        }
    });
    queryClauses.push({'field': 'math', 'value': $('#mathQuery').val()});
    queryClauses.push({'field': 'operator', 'value': operatorSelect});
    query += " "+$('#mathQuery').val();
//    console.log(query);
//    console.log(JSON.stringify(queryClauses));
    $('input[name=query]').val(query);
    $('input[name=qc]').val(JSON.stringify(queryClauses));
}

function addClause() {
    $('.query-clauses').append('<div class="query-clause"><select class="field-select" title=""><option value="any">Any field</option><option value="title">Title</option><option value="content">Content</option></select>\n\
                        <input type="text" class="field-input" title=""/><datalist class="suggestions"></datalist>\n\
                        <a href="#" onclick="removeClause($(this))" >remove</a></div>');
    $('.field-input').last().keyup(function() {        
        if (event.which < 37 || event.which > 40) {
            suggest($(this));
        }
    });
    createTextQueryTooltip();
}
function removeClause($element) {
    $element.closest('div').remove();
}

function prefillClauses() {
    var qc = $('input[name=qc]').val();
//    console.log(qc);
    if (qc) {
        var clauses = JSON.parse(qc);
        clauses = $(clauses);
        var mathClause;
        var clausesToRemove = [];
        clauses.each(function(index) {
            if (index<clauses.size()-3) {
                addClause();
            }
            if (this['field']=='math') {
                mathClause = this;
                clausesToRemove.push(index);
            }
            if (this['field']=='operator') {
                $('#operator').val(this['value']);
                clausesToRemove.push(index);
            }
        });
        clauses.splice(clausesToRemove[1], 1);
        clauses.splice(clausesToRemove[0], 1);
        var domclauses = $('.query-clause');
        if (clauses.length > 0) {
            domclauses.each(function(index) {
                $(this).find('.field-select').val(clauses[index]['field']);    
                $(this).find('.field-input').val(clauses[index]['value']);        
            });
        }
        $('#mathQuery').val(mathClause['value']);
    }
}

function renderQueryMath() {
    MathJax.Hub.Config({
    tex2jax: {
      inlineMath: [ ['$','$'], ["\\(","\\)"] ],
      processEscapes: true
    }
  });
    var $visualQuery = $('#visualQuery');
    var query=$('#mathQuery').val();
    $.get('input',{'query':query}, function(responseText) {
        if (responseText) {
            if (responseText == "parse_error") {
                $('#mathQuery').addClass("error-highlight");
            } else {
                $('#mathQuery').removeClass("error-highlight");
                $visualQuery.empty();
                responseText = safe_tags_replace(responseText);
                $(responseText).appendTo($visualQuery);
                MathJax.Hub.Queue(["Typeset",MathJax.Hub,"visualQuery"]);
            } 
        } else {
            $visualQuery.empty();
        }
    });
    
}

function suggest($input) {
    var query=$input.val();
    if ($.trim(query)!= '') {
    $.get('suggest',{'query':query}, function(responseJson) {
            if (responseJson) {
                var responseList = JSON.parse(responseJson);
                $input.autocomplete({
                    source: responseList,
                    minLength: 1
                });
                $input.autocomplete('search');
            }
        });
    }
}

var tagsToReplace = {
    '&': '&amp;'
};

function replaceTag(tag) {
    return tagsToReplace[tag] || tag;
}

function safe_tags_replace(str) {
    return str.replace(/[&<>]/g, replaceTag);
}

function writeConsole(content) {
    var cached = window.open('', '_blank');
    cached.document.writeln(content);
}
