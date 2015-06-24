<%--
  Created by IntelliJ IDEA.
  User: Sasha
  Date: 16.06.2015
  Time: 15:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>


    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Creative - Bootstrap 3 Responsive Admin Template">
    <meta name="author" content="GeeksLabs">
    <meta name="keyword" content="Creative, Dashboard, Admin, Template, Theme, Bootstrap, Responsive, Retina, Minimal">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/icons/favicon.png">

    <title>Task | Account Binding</title>

    <!-- Bootstrap CSS -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <!-- bootstrap theme -->
    <link href="${pageContext.request.contextPath}/css/bootstrap-theme.css" rel="stylesheet">
    <!--external css-->
    <!-- font icon -->
    <link href="${pageContext.request.contextPath}/css/elegant-icons-style.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet"/>
    <!-- Custom styles -->
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style-responsive.css" rel="stylesheet"/>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.tokenize.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery.tokenize.css"/>


    <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <!-- nice scroll -->
    <script src="${pageContext.request.contextPath}/js/jquery.scrollTo.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.nicescroll.js" type="text/javascript"></script>
    <!-- gritter -->

    <!-- custom gritter script for this page only-->
    <script src="${pageContext.request.contextPath}/js/gritter.js" type="text/javascript"></script>
    <!--custome script for all page-->
    <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.tokenize.js"></script>
    <!--custom tagsinput-->
    <script src="${pageContext.request.contextPath}/js/jquery.tagsinput.js"></script>
    <script src="${pageContext.request.contextPath}/js/form-component.js"></script>


    <!--[if lt IE 9]>
    <![endif]-->

    <%--for table--%>

    <script type="text/javascript">
        var path = '${pageContext.request.contextPath}';
    </script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
    <link href="http://cdn.datatables.net/1.10.3/css/jquery.dataTables.css" rel="stylesheet" type="text/css">
    <link href="http://datatables.net/release-datatables/extensions/ColVis/css/dataTables.colVis.css" rel="stylesheet"
          type="text/css">
    <script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="http://cdn.datatables.net/1.10.3/js/jquery.dataTables.min.js"></script>
    <script src="http://datatables.net/release-datatables/extensions/ColVis/js/dataTables.colVis.js"></script>
    <script src="http://jquery-datatables-column-filter.googlecode.com/svn/trunk/media/js/jquery.dataTables.columnFilter.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/postbinding-datatable.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/fnStandingRedraw.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/fnSetFilteringDelay.js"></script>

        <script type="text/javascript" >
            (function($, tokenize){

                // Keycodes
                var KEYS = {
                    BACKSPACE: 8,
                    TAB: 9,
                    ENTER: 13,
                    ESCAPE: 27,
                    ARROW_UP: 38,
                    ARROW_DOWN: 40,
                    COMMA: 188
                };

                // Debounce timeout
                var debounce_timeout = null;

                // Data storage constant
                var DATA = 'tokenize';

                $.tokenize = function(opts){

                    if(opts == undefined){
                        opts = $.fn.tokenize.defaults;
                    }

                    this.options = opts;
                };

                $.extend($.tokenize.prototype, {

                    init: function(el){

                        var $this = this;
                        this.select = el.attr('multiple', 'multiple').css({margin: 0, padding: 0, border: 0}).hide();

                        this.container = $('<div />')
                                .attr('class', this.select.attr('class'))
                                .addClass('Tokenize');

                        if(this.options.maxElements == 1){
                            this.container.addClass('OnlyOne');
                        }

                        this.dropdown = $('<ul />')
                                .addClass('Dropdown');

                        this.tokensContainer = $('<ul />')
                                .addClass('TokensContainer');

                        this.searchToken = $('<li />')
                                .addClass('TokenSearch')
                                .appendTo(this.tokensContainer);

                        this.searchInput = $('<input />')
                                .appendTo(this.searchToken);

                        if(this.options.searchMaxLength > 0){
                            this.searchInput.attr('maxlength', this.options.searchMaxLength)
                        }

                        if(this.select.prop('disabled')){
                            this.disable();
                        }

                        if(this.options.sortable){
                            if (typeof $.ui != 'undefined'){
                                this.tokensContainer.sortable({
                                    items: 'li.Token',
                                    cursor: 'move',
                                    placeholder: 'Token MovingShadow',
                                    forcePlaceholderSize: true,
                                    update: function(){
                                        $this.updateOrder();
                                    },
                                    start: function(){
                                        $this.searchToken.hide();
                                    },
                                    stop: function(){
                                        $this.searchToken.show();
                                    }
                                }).disableSelection();
                            } else {
                                this.options.sortable = false;
                                console.log('jQuery UI is not loaded, sortable option has been disabled');
                            }
                        }

                        this.container
                                .append(this.tokensContainer)
                                .append(this.dropdown)
                                .insertAfter(this.select);

                        this.tokensContainer.on('click', function(e){
                            e.stopImmediatePropagation();
                            $this.searchInput.get(0).focus();
                            $this.updatePlaceholder();
                            if($this.dropdown.is(':hidden') && $this.searchInput.val() != ''){
                                $this.search();
                            }
                        });

                        this.searchInput.on('focus click', function(){
                            if($this.options.displayDropdownOnFocus && $this.options.datas == 'select'){
                                $this.search();
                            }
                        });

                        this.searchInput.on('keydown', function(e){
                            $this.resizeSearchInput();
                            $this.keydown(e);
                        });

                        this.searchInput.on('keyup', function(e){
                            $this.keyup(e);
                        });

                        this.searchInput.on('paste', function(){
                            setTimeout(function(){ $this.resizeSearchInput(); }, 10);
                            setTimeout(function(){
                                var paste_elements = $this.searchInput.val().split(',');
                                if(paste_elements.length > 1){
                                    $.each(paste_elements, function(_, value){
                                        $this.tokenAdd(value.trim(), '');
                                    });
                                }
                            }, 20);
                        });

                        $(document).on('click', function(){
                            $this.dropdownHide();
                            if($this.options.maxElements == 1){
                                if($this.searchInput.val()){
                                    $this.tokenAdd($this.searchInput.val(), '');
                                }
                            }
                        });

                        this.resizeSearchInput();

                        $('option:selected', this.select).each(function(){
                            $this.tokenAdd($(this).attr('value'), $(this).html(), true);
                        });

                        this.updatePlaceholder();

                    },

                    updateOrder: function(){

                        var previous, current, $this = this;
                        $.each(this.tokensContainer.sortable('toArray', {attribute: 'data-value'}), function(k, v){
                            current = $('option[value="' + v + '"]', $this.select);
                            if(previous == undefined){
                                current.prependTo($this.select);
                            } else {
                                previous.after(current);
                            }
                            previous = current;
                        });
                        this.options.onReorder(this);

                    },

                    updatePlaceholder: function(){

                        if(this.options.placeholder != false){
                            if(this.placeholder == undefined){
                                this.placeholder = $('<li />').addClass('Placeholder').html(this.options.placeholder);
                                this.placeholder.insertBefore($('li:first-child', this.tokensContainer));
                            }

                            if(this.searchInput.val().length == 0 && $('li.Token', this.tokensContainer).length == 0){
                                this.placeholder.show();
                            } else {
                                this.placeholder.hide();
                            }
                        }

                    },

                    dropdownShow: function(){

                        this.dropdown.show();

                    },

                    dropdownPrev: function(){

                        if($('li.Hover', this.dropdown).length > 0){
                            if(!$('li.Hover', this.dropdown).is('li:first-child')){
                                $('li.Hover', this.dropdown).removeClass('Hover').prev().addClass('Hover');
                            } else {
                                $('li.Hover', this.dropdown).removeClass('Hover');
                                $('li:last-child', this.dropdown).addClass('Hover');
                            }
                        } else {
                            $('li:first', this.dropdown).addClass('Hover');
                        }

                    },

                    dropdownNext: function(){

                        if($('li.Hover', this.dropdown).length > 0){
                            if(!$('li.Hover', this.dropdown).is('li:last-child')){
                                $('li.Hover', this.dropdown).removeClass('Hover').next().addClass('Hover');
                            } else {
                                $('li.Hover', this.dropdown).removeClass('Hover');
                                $('li:first-child', this.dropdown).addClass('Hover');
                            }
                        } else {
                            $('li:first', this.dropdown).addClass('Hover');
                        }

                    },

                    dropdownAddItem: function(value, text, html){

                        if(html == undefined){
                            html = text;
                        }

                        if($('li[data-value="' + value + '"]', this.tokensContainer).length){
                            return false;
                        }

                        var $this = this;
                        var item = $('<li />')
                                .attr('data-value', value)
                                .attr('data-text', text)
                                .html(html)
                                .on('click', function(e){
                                    e.stopImmediatePropagation();
                                    $this.tokenAdd($(this).attr('data-value'), $(this).attr('data-text'));
                                }).on('mouseover', function(){
                                    $(this).addClass('Hover');
                                }).on('mouseout', function(){
                                    $('li', $this.dropdown).removeClass('Hover');
                                });

                        this.dropdown.append(item);
                        this.options.onDropdownAddItem(value, text, html, this);
                        return true;

                    },

                    dropdownHide: function(){

                        this.dropdownReset();
                        this.dropdown.hide();

                    },

                    dropdownReset: function(){

                        this.dropdown.html('');

                    },

                    resizeSearchInput: function(){

                        this.searchInput.attr('size', (this.searchInput.val().length > 1 ? this.searchInput.val().length : 5));
                        this.updatePlaceholder();

                    },

                    resetSearchInput: function(){

                        this.searchInput.val("");
                        this.resizeSearchInput();

                    },

                    resetPendingTokens: function(){

                        $('li.PendingDelete', this.tokensContainer).removeClass('PendingDelete');

                    },

                    keydown: function(e){

                        if(e.keyCode == KEYS.COMMA){
                            e.preventDefault();
                            this.tokenAdd(this.searchInput.val(), '');
                        } else {
                            switch(e.keyCode){
                                case KEYS.BACKSPACE:
                                    if(this.searchInput.val().length == 0){
                                        e.preventDefault();
                                        if($('li.Token.PendingDelete', this.tokensContainer).length){
                                            this.tokenRemove($('li.Token.PendingDelete').attr('data-value'));
                                        } else {
                                            $('li.Token:last', this.tokensContainer).addClass('PendingDelete');
                                        }
                                        this.dropdownHide();
                                    }
                                    break;

                                case KEYS.TAB:
                                case KEYS.ENTER:
                                    if($('li.Hover', this.dropdown).length){
                                        var element = $('li.Hover', this.dropdown);
                                        e.preventDefault();
                                        this.tokenAdd(element.attr('data-value'), element.attr('data-text'));
                                    } else {
                                        if(this.searchInput.val()){
                                            e.preventDefault();
                                            this.tokenAdd(this.searchInput.val(), '');
                                        }
                                    }
                                    this.resetPendingTokens();
                                    break;

                                case KEYS.ESCAPE:
                                    this.resetSearchInput();
                                    this.dropdownHide();
                                    this.resetPendingTokens();
                                    break;

                                case KEYS.ARROW_UP:
                                    e.preventDefault();
                                    this.dropdownPrev();
                                    break;

                                case KEYS.ARROW_DOWN:
                                    e.preventDefault();
                                    this.dropdownNext();
                                    break;

                                default:
                                    this.resetPendingTokens();
                                    break;
                            }
                        }

                    },

                    keyup: function(e){

                        this.updatePlaceholder();
                        switch(e.keyCode){
                            case KEYS.TAB:
                            case KEYS.ENTER:
                            case KEYS.ESCAPE:
                            case KEYS.ARROW_UP:
                            case KEYS.ARROW_DOWN:
                                break;

                            case KEYS.BACKSPACE:
                                if(this.searchInput.val()){
                                    this.search();
                                } else {
                                    this.dropdownHide();
                                }
                                break;
                            default:
                                if(this.searchInput.val()){
                                    this.search();
                                }
                                break;
                        }

                    },

                    search: function(){

                        var $this = this;
                        var count = 1;

                        if(this.options.maxElements > 0 && $('li.Token', this.tokensContainer).length >= this.options.maxElements){
                            return false;
                        }

                        if(this.options.datas == 'select'){

                            var found = false, regexp = new RegExp(this.searchInput.val().replace(/[-[\]{}()*+?.,\\^$|#\s]/g, "\\$&"), 'i');
                            this.dropdownReset();

                            $('option', this.select).not(':selected').each(function(){
                                if(count <= $this.options.nbDropdownElements){
                                    if(regexp.test($(this).html())){
                                        $this.dropdownAddItem($(this).attr('value'), $(this).html());
                                        found = true;
                                        count++;
                                    }
                                } else {
                                    return false;
                                }
                            });

                            if(found){
                                $('li:first', this.dropdown).addClass('Hover');
                                this.dropdownShow();
                            } else {
                                this.dropdownHide();
                            }

                        } else {

                            this.debounce(function(){
                                $.ajax({
                                    url: $this.options.datas,
                                    data: $this.options.searchParam + "=" + $this.searchInput.val(),
                                    dataType: $this.options.dataType,
                                    success: function(data){
                                        if(data){
                                            $this.dropdownReset();
                                            $.each(data, function(key, val){
                                                if(count <= $this.options.nbDropdownElements){
                                                    var html = undefined;
                                                    if(val[$this.options.htmlField]){
                                                        html = val[$this.options.htmlField];
                                                    }
                                                    $this.dropdownAddItem(val[$this.options.valueField], val[$this.options.textField], html);
                                                    count++;
                                                } else {
                                                    return false;
                                                }
                                            });
                                            if($('li', $this.dropdown).length){
                                                $('li:first', $this.dropdown).addClass('Hover');
                                                $this.dropdownShow();
                                                return true;
                                            }
                                        }
                                        $this.dropdownHide();
                                    },
                                    error: function(XHR, textStatus) {
                                        console.log("Error : " + textStatus);
                                    }
                                });
                            }, this.options.debounce);

                        }

                    },

                    debounce: function(func, threshold){

                        var obj = this, args = arguments;
                        var delayed = function(){
                            func.apply(obj, args);
                            debounce_timeout = null;
                        };
                        if(debounce_timeout){
                            clearTimeout(debounce_timeout);
                        }
                        debounce_timeout = setTimeout(delayed, threshold || this.options.debounce);

                    },

                    tokenAdd: function(value, text, first){

                        value = this.escape(value);

                        if(value == undefined || value == ''){
                            return false;
                        }

                        if(text == undefined || text == ''){
                            text = value;
                        }

                        if(first == undefined){
                            first = false;
                        }

                        if(this.options.maxElements > 0 && $('li.Token', this.tokensContainer).length >= this.options.maxElements){
                            this.resetSearchInput();
                            return false;
                        }

                        var $this = this;
                        var close_btn = $('<a />')
                                .addClass('Close')
                                .html("&#215;")
                                .on('click', function(e){
                                    e.stopImmediatePropagation();
                                    $this.tokenRemove(value);
                                });

                        if($('option[value="' + value + '"]', this.select).length){
                            $('option[value="' + value + '"]', this.select).attr('selected', 'selected');
                        } else if(this.options.newElements || (!this.options.newElements && $('li[data-value="' + value + '"]', this.dropdown).length > 0)) {
                            var option = $('<option />')
                                    .attr('selected', 'selected')
                                    .attr('value', value)
                                    .attr('data-type', 'custom')
                                    .html(text);

                            this.select.append(option);
                        } else {
                            this.resetSearchInput();
                            return false;
                        }

                        if($('li.Token[data-value="' + value + '"]', this.tokensContainer).length > 0) {
                            return false;
                        }

                        $('<li />')
                                .addClass('Token')
                                .attr('data-value', value)
                                .append('<span>' + text + '</span>')
                                .prepend(close_btn)
                                .insertBefore(this.searchToken);

                        if(!first){
                            this.options.onAddToken(value, text, this);
                        }

                        this.resetSearchInput();
                        this.dropdownHide();

                        return true;

                    },

                    tokenRemove: function(value){

                        var option = $('option[value="' + value + '"]', this.select);

                        if(option.attr('data-type') == 'custom'){
                            option.remove();
                        } else {
                            option.removeAttr('selected');
                        }

                        $('li.Token[data-value="' + value + '"]', this.tokensContainer).remove();

                        this.options.onRemoveToken(value, this);
                        this.resizeSearchInput();
                        this.dropdownHide();

                    },

                    clear: function(){

                        var $this = this;
                        $('li.Token', this.tokensContainer).each(function(){
                            $this.tokenRemove($(this).attr('data-value'));
                        });

                        this.options.onClear(this);

                    },

                    disable: function(){

                        this.select.prop('disabled', true);
                        this.searchInput.prop('disabled', true);
                        this.container.addClass('Disabled');
                        if(this.options.sortable){
                            this.tokensContainer.sortable('disable')
                        }

                    },

                    enable: function(){

                        this.select.prop('disabled', false);
                        this.searchInput.prop('disabled', false);
                        this.container.removeClass('Disabled');
                        if(this.options.sortable){
                            this.tokensContainer.sortable('enable')
                        }

                    },

                    escape: function(string){

                        return String(string).replace(/["]/g, function(){
                            return '';
                        });

                    }

                });

                $.fn.tokenize = function(options){

                    if(options == undefined){
                        options = {};
                    }

                    this.each(function(){
                        var obj = new $.tokenize($.extend({}, $.fn.tokenize.defaults, options));
                        obj.init($(this));
                        $(this).data(DATA, obj);
                    });

                    return this;

                };

                $.fn.tokenize.defaults = {

                    datas: 'select',
                    placeholder: false,
                    searchParam: 'search',
                    searchMaxLength: 0,
                    debounce: 0,
                    newElements: true,
                    nbDropdownElements: 10,
                    displayDropdownOnFocus: false,
                    maxElements: 0,
                    sortable: false,
                    dataType: 'json',
                    valueField: 'value',
                    textField: 'text',
                    htmlField: 'html',

                    onAddToken: function(value, text, e){},
                    onRemoveToken: function(value, e){},
                    onClear: function(e){},
                    onReorder: function(e){},
                    onDropdownAddItem: function(value, text, html, e){}

                };

            })(jQuery, 'tokenize');

        </script>

</head>

<body>

<!-- container section start -->
<section id="container" class="">

    <jsp:include page="../pagecontent/header.jsp"/>
    <jsp:include page="../pagecontent/sidebar.jsp"/>

    <!--main content start-->
    <section id="main-content">
        <section class="wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <ol class="breadcrumb">
                        <li><i class="fa fa-home"></i><a href="index.html">Home</a></li>
                        <li><i class="fa fa-desktop"></i>Task</li>
                        <li><i class="fa fa-list-alt"></i>Account Binding</li>
                    </ol>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-12">
                    <div class="panel">
                        <header class="panel-heading">
                            Account binding
                        </header>
                        <%--<table class="table table-striped table-advance table-hover">--%>
                        <%--<tr><th>Group ID</th><th>Group Name</th><th>Account</th></tr>--%>
                        <%--<tr>--%>
                        <%--<td>111111</td>--%>
                        <%--<td>Group 1</td>--%>


                        <div class="b-popup" id="popup1">
                            <div class="b-popup-content">

                                <table>
                                    <tr>
                                        <th style="width:400px;">Read</th>
                                        <th style="width:400px;">Write</th>
                                    </tr>

                                    <tr>
                                        <td>
                                            <select id="tokenize_focus31" multiple="multiple" class="tokenize-sample"
                                                    style="width: 350px; margin: 0px; padding: 0px; border: 0px; display: none;"
                                                    >
                                                <option value="1">Account 1</option>
                                                <option value="2">Account 2</option>
                                                <option value="3">Account 3</option>
                                                <option value="4">Account 4</option>
                                                <option value="5">Account 5</option>
                                            </select>

                                            <script type="text/javascript">
                                                $('select#tokenize_focus31').tokenize({displayDropdownOnFocus: true});
                                            </script>
                                        </td>
                                        <td>
                                            <select id="tokenize_focus32" multiple="multiple" class="tokenize-sample"
                                                    style="width: 350px; margin: 0px; padding: 0px; border: 0px; display: none;">
                                                <option value="1">Account 1</option>
                                                <option value="2">Account 2</option>
                                                <option value="3">Account 3</option>
                                                <option value="4">Account 4</option>
                                                <option value="5">Account 5</option>
                                            </select>

                                            <script type="text/javascript">
                                                $('select#tokenize_focus32').tokenize({displayDropdownOnFocus: true});
                                            </script>
                                        </td>
                                    </tr>

                                </table>
                                <a href="javascript:PopUpHide()">
                                    <button style="border-radius: 4px;border-color:#424D5F;background-color:#424D5F;color:#ffffff;margin:15px;padding:10px;width:150px;">
                                        Save
                                    </button>
                                </a>
                            </div>
                        </div>


                        <%--<div class="b-popup" id="popup1">--%>
                        <%--<div class="b-popup-content">--%>

                        <%--<table>--%>
                        <%--<tr>--%>
                        <%--<th style="width:400px;">Read</th>--%>
                        <%--<th style="width:400px;">Write</th>--%>
                        <%--</tr>--%>
                        <%--<tr>--%>
                        <%--<td>--%>
                        <%--<select id="tokenize_focus11" multiple="multiple"--%>
                        <%--class="tokenize-sample" style="width:350px;" onclick="sel1(this)">--%>
                        <%--<option value="1">Account 1</option>--%>
                        <%--<option value="2">Account 2</option>--%>
                        <%--<option value="3">Account 3</option>--%>
                        <%--<option value="4">Account 4</option>--%>
                        <%--<option value="5">Account 5</option>--%>
                        <%--</select>--%>

                        <%--&lt;%&ndash;<script type="text/javascript">&ndash;%&gt;--%>
                        <%--&lt;%&ndash;$('select#tokenize_focus12').tokenize({displayDropdownOnFocus: true});&ndash;%&gt;--%>
                        <%--&lt;%&ndash;</script>&ndash;%&gt;--%>
                        <%--</td>--%>
                        <%--<td>--%>
                        <%--<select id="tokenize_focus12" multiple="multiple"--%>
                        <%--class="tokenize-sample" style="width:350px;">--%>
                        <%--<option value="1">Account 1</option>--%>
                        <%--<option value="2">Account 2</option>--%>
                        <%--<option value="3">Account 3</option>--%>
                        <%--<option value="4">Account 4</option>--%>
                        <%--<option value="5">Account 5</option>--%>
                        <%--</select>--%>

                        <%--<script type="text/javascript">--%>
                        <%--$('select#tokenize_focus12').tokenize({displayDropdownOnFocus: true});--%>
                        <%--</script>--%>
                        <%--</td>--%>
                        <%--</tr>--%>

                        <%--</table>--%>
                        <%--<a href="javascript:PopUpHide()">--%>
                        <%--<button style="border-radius: 4px;border-color:#424D5F;background-color:#424D5F;color:#ffffff;margin:15px;padding:10px;width:150px;">--%>
                        <%--Save--%>
                        <%--</button>--%>
                        <%--</a>--%>
                        <%--</div>--%>
                        <%--</div>--%>

                        <table id="postBindingTable">
                            <tbody>
                            <thead>
                            <tr>
                                <th>Group ID</th>
                                <th>Group Name</th>
                                <th class="datatable-nosort">Bind Account</th>
                                <th class="datatable-nosort">Statistic</th>

                            </tr>
                            </thead>
                            <tfoot>
                            <tr>
                                <th>Group ID</th>
                                <th>Group Name</th>
                                <th class="datatable-nosort">Bind Account</th>
                                <th class="datatable-nosort">Statistic</th>
                            </tr>
                            </tfoot>

                            </tbody>
                        </table>


        </section>
        </div>


        </div>
    </section>
</section>
<!--main content end-->
</section>
<!-- container section end -->

<!-- javascripts -->

<script>
    $(document).ready(function () {

        $("#popup").hide();
    });
    function PopUpShow(i) {
        alert(i + " was passed");
        $("#popup1").show();
    }
    function PopUpHide() {
        $("#popup1").hide();
    }

</script>

<style>
    tfoot input {
        width: 100%;
        padding: 3px;
        box-sizing: border-box;
    }

    .tableHeader {
        text-align: left;
    }

    tfoot {
        display: table-header-group;
    }

    .dataTables_length {
        position: absolute;
        top: 10px;
        left: 220px;
    }

    .dataTables_info {
        position: absolute;
        top: 0px;
        left: 5px;
    }

    .ColVis {
        padding-right: 10px;
        padding-top: 5px;

    }

    .dataTables_filter {
        position: absolute;
        top: 10px;
        left: 200px;
        font-size: 15px;
    }

    .dataTables_filter input {
        height: 22px;
        margin-right: 10px;
        width: 150px
    }

    input {
        -moz-border-radius: 15px;
        border-radius: 3px;
        border: solid 1px #c7c7c7;
        padding: 1px;
    }

    table.dataTable tbody td {
        padding: 7px;
        padding-left: 20px;
    }


</style>

</body>
</html>



