<%--
  Created by IntelliJ IDEA.
  User: Sasha
  Date: 19.06.2015
  Time: 15:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Creative - Bootstrap 3 Responsive Admin Template">
    <meta name="author" content="GeeksLabs">
    <meta name="keyword" content="Creative, Dashboard, Admin, Template, Theme, Bootstrap, Responsive, Retina, Minimal">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/icons/favicon.png">

    <title>All Posts</title>

    <!-- Bootstrap CSS -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <!-- bootstrap theme -->
    <link href="${pageContext.request.contextPath}/css/bootstrap-theme.css" rel="stylesheet">
    <!--external css-->
    <link href="${pageContext.request.contextPath}/css/core.css" rel="stylesheet">

    <!-- font icon -->
    <link href="${pageContext.request.contextPath}/css/elegant-icons-style.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet"/>
    <!-- Custom styles -->
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style-responsive.css" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery.tokenize.css"/>


    <!-- javascripts -->
    <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <!-- nice scroll -->
    <script src="${pageContext.request.contextPath}/js/jquery.scrollTo.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.nicescroll.js" type="text/javascript"></script>
    <!-- gritter -->

    <%--<!-- custom gritter script for this page only-->--%>
    <script src="${pageContext.request.contextPath}/js/gritter.js" type="text/javascript"></script>
    <%--<!--custome script for all page-->--%>
    <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.tokenize.js"></script>

    <%--for table--%>
    <link href="http://cdn.datatables.net/1.10.3/css/jquery.dataTables.css" rel="stylesheet" type="text/css">
    <link href="http://datatables.net/release-datatables/extensions/ColVis/css/dataTables.colVis.css" rel="stylesheet"
          type="text/css">
    <script src="http://cdn.datatables.net/1.10.3/js/jquery.dataTables.min.js"></script>
    <script src="http://datatables.net/release-datatables/extensions/ColVis/js/dataTables.colVis.js"></script>
    <script src="http://jquery-datatables-column-filter.googlecode.com/svn/trunk/media/js/jquery.dataTables.columnFilter.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/fnStandingRedraw.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/fnSetFilteringDelay.js"></script>


    <!-- HTML5 shim and Respond.js IE8 support of HTML5 -->
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
    <script src="${pageContext.request.contextPath}/js/respond.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/lte-ie7.js"></script>
    <![endif]-->

    <link href="${pageContext.request.contextPath}/css/toastr.css" rel="stylesheet" type="text/css"/>
    <script src="${pageContext.request.contextPath}/js/toastr.js"></script>
    <!--custom tagsinput-->
    <script src="${pageContext.request.contextPath}/js/jquery.tagsinput.js"></script>

    <script type="text/javascript">
        // При завантаженні сторінки
        setTimeout(function () {
            if (${toastr_notification!=null}) {
                var args = "${toastr_notification}".split("|");
                toastrNotification(args[0], args[1]);
            }
        }, 500);

        function removePost(id, elm) {
            deleteConfirmCreatedPost(id);
        }
    </script>

    <script type="text/javascript">
        var table;

        jQuery(document).ready(function () {
            table = $('#postsTable').dataTable({

                "bSort": false,
                "bPaginate": true,
                "paging": true,
                "bInfo": false,
                "iDisplayStart": 0,
                "bProcessing": true,
                'iDisplayLength': 10,
                "bServerSide": true,
                "sAjaxSource": "http://localhost:8080/post?action=getCreatedPost",
                colVis: {
                    "align": "right",
                    "buttonText": "columns <img src=\"/img/caaret.png\"/>",
                },

                "columnDefs": [{
                    "bVisible": false, "aTargets": [0]
                }, {
                    "aTargets": [1], "createdCell": function (td, cellData, rowData, row, col) {
                        $(td).html('<a href="#" onclick="viewPost(' + rowData[0] + ')" data-toggle="modal" data-target="#myModal">' +
                                cellData + '</a>');
                    }
                }, {
                    "aTargets": [2], "createdCell": function (td, cellData, rowData, row, col) {
                        $(td).html(parseAttachment(cellData));
                    }
                }, {
                    "aTargets": [3], "createdCell": function (td, cellData, rowData, row, col) {
                        $(td).html('<div class="btn-group"><a class="btn btn-primary" data-toggle="modal" data-target="#publish_modal" onclick="openPublishWindows(' + cellData + ')" ><i class="icon_plus_alt2"></i></a><a class="btn btn-danger" onclick="removePost(' + cellData + ',this)"><i class="icon_close_alt2"></i></a></div>');
                    }
                }, {
                    "width": "60%", "targets": 1
                }, {
                    "class": "dt-body-left", "targets": 1
                }, {
                    "class": "dt-body-right", "targets": 3
                }]

            });


            $(".dataTables_length").attr("hidden", "");
            var dataTables_filter_input = $(".dataTables_filter").find("input");
            dataTables_filter_input.attr("class", "form-control");
            dataTables_filter_input.attr("style", "width: 500px")
//            $(".dataTables_filter").attr("hidden", "");
            $(".dataTables_length").attr("hidden", "");


            $('#refreshbtn').click(function () {
                table.fnStandingRedraw();
            });

            function parseAttachment(arg) {
                var args = arg.split("!");
                var cell = "";
                for (var i = 0; i < args.length; i++) {
                    cell = cell + " " + parseDoc(args[i]);
                }
                return cell;
            }

            function parseDoc(arg) {
                var args = arg.split("|");
                switch (args[0]) {
                    case "PHOTO":
                        return '<img src="${pageContext.request.contextPath}/img/icons/jpg-icon.png" style="width: 30px; height: 30px"><span class="badge bg-important">{count}</span>'.replace("{count}", args[1]);
                    case "VIDEO":
                        return '<img src="${pageContext.request.contextPath}/img/icons/mpg-icon.png" style="width: 30px; height: 30px"><span class="badge bg-important">{count}</span>'.replace("{count}", args[1]);
                    case "AUDIO":
                        return '<img src="${pageContext.request.contextPath}/img/icons/mp3-icon.png" style="width: 30px; height: 30px"><span class="badge bg-important">{count}</span>'.replace("{count}", args[1]);
                    case "DOC":
                        return '<img src="${pageContext.request.contextPath}/img/icons/txt-icon.png" style="width: 30px; height: 30px"><span class="badge bg-important">{count}</span>'.replace("{count}", args[1]);
                }
                return "";
            }
        })
    </script>
    <script>
        var postId;

        function PopUpShow(id) {
            postId = id;
            var xmlhttp = new XMLHttpRequest();
            xmlhttp.open('GET', '/owner?action=getOwnerWall&id=' + id, true);
            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState == 4) {
                    var response = JSON.parse(xmlhttp.responseText);
                    var list = $("#tokenize_focus");
                    for (var i = 0; i < response.owner.length; i++) {
                        list.append('<option value="' + response.owner[i].id + '">' + response.owner[i].name + '</option>');
                    }
                    $("#create_dialog1").show();
                }

            };
            xmlhttp.send();
        }

    </script>
</head>
<body>

<!-- container section start -->
<section id="container" class="">

    <jsp:include page="../pagecontent/header.jsp"/>
    <jsp:include page="../pagecontent/sidebar.jsp"/>

    <jsp:include page="../post/viewpost.jsp"/>

    <%--for confirm delete modal window(include script and css)--%>
    <jsp:include page="../pagecontent/confirm-delete.jsp"/>

    <section id="main-content">
        <section class="wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header"><i class="fa fa-list-alt"></i> Created</h3>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <ol class="breadcrumb">
                        <li><i class="fa fa-home"></i><a href="/">Home</a></li>
                        <li><i class="fa fa-desktop"></i>Post</li>
                        <li><i class="fa fa-list-alt"></i>Created</li>
                    </ol>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <div class="pull-left">Created</div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="panel-body">
                            <div id="active" class="tab-pane active">
                                <div class="col-lg-12">
                                    <table width="100%" border="0" margin="0" padding="0"
                                           class="row-border tableHeader" id="postsTable">
                                        <thead>
                                        <tr style="align-content: center">
                                            <th>id</th>
                                            <th>Message</th>
                                            <th>Attachment</th>
                                            <th></th>
                                        </tr>
                                        </thead>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </section>
</section>
</section>

<%--Вікно публікації--%>
<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="publish_modal"
     class="modal fade">
    <div class="modal-dialog" style="margin-left: 35%; margin-top: 110px">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">x</button>
                <h4 class="modal-title"><l:resource key="newpost.dateandtime"/></h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <form id="modal_form" class="form-horizontal">
                            <%--Дата і час--%>
                            <div style="position: relative; left: -130px; top:4px;">
                                <div class="form-group">
                                    <label class="col-md-4 control-label" for="date"><l:resource
                                            key="newpost.date"/></label>

                                    <div class="col-md-4">
                                        <l:resource key="newpost.postdate">
                                            <input id="date" name="date" type="date"
                                                   min="${date}" value="${date}"
                                                   class="form-control input-md">
                                        </l:resource>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 control-label" for="time" style="margin-top: -24px">
                                        <l:resource key="newpost.time"/></label>

                                    <div class="col-md-4" style="margin-top: -24px">
                                        <l:resource key="newpost.posttime">
                                            <input id="time" name="time" type="time" value="${time}"
                                                   class="form-control input-md">
                                        </l:resource>
                                    </div>
                                </div>
                            </div>
                            <%--Вибір груп--%>
                            <div style="position: relative; left: 250px; top:-109px;">
                                <div class="form-group" style="">
                                    <div class="col-lg-6">
                                        <h4><l:resource key="newpost.selectgroup"/>:</h4>
                                        <select name="groups" id="tokenize_focus" multiple="multiple"
                                                class="tokenize-sample">
                                            <c:forEach items="${owners}" var="owner">
                                                <option value="${owner.wallId}">${owner.name}</option>
                                            </c:forEach>
                                        </select>
                                        <script type="text/javascript">
                                            $('select#tokenize_focus').tokenize({displayDropdownOnFocus: true});
                                        </script>
                                    </div>
                                </div>
                            </div>
                            <%--Включення автовидалення поста--%>
                            <div style="position: relative; left:-67px; top:-100px;">
                                <div class="form-group">
                                    <label class="col-md-4 control-label" for="check"><l:resource
                                            key="newpost.removingdate"/></label>
                                    <input id="check" type="checkbox" style="margin-top: 11px">
                                </div>
                            </div>
                            <%--Дата видалення--%>
                            <div style="position: relative; left:48px; top:-90px; width: 600px;">
                                <div id="time3" class="col-md-4">
                                    <l:resource key="newpost.date">
                                        <input id="time1" name="date_delete" style="width:162px;" type="date"
                                               class="form-control input-md" value="${del_date}">
                                    </l:resource>
                                </div>
                            </div>
                            <%--Час видалення--%>
                            <div style="position: relative; left:-152px; top:-50px; width: 600px;">
                                <div id="time4" class="col-md-4">
                                    <l:resource key="newpost.time">
                                        <input id="time5" name="time_delete" type="time" style="width:162px;"
                                               class="form-control input-md" value="${del_time}">
                                    </l:resource>
                                </div>
                            </div>
                            <%--Кнопка публікування--%>
                            <button id="submit_modal" type="button" style="float:right; margin-top: -51px;"
                                    class="btn btn-primary" <%--data-dismiss="modal" --%>>
                                <l:resource key="newpost.save"/>
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>

        var publishPostId = 0;

        // Завантажуємо дані для вікна
        function openPublishWindows(id) {
            publishPostId = id;
            var xmlhttp = new XMLHttpRequest();
            xmlhttp.open('GET', '/owner?action=getOwnerWall&id=' + id, true);
            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState == 4) {
                    var response = JSON.parse(xmlhttp.responseText);
                    var list = $("#tokenize_focus");
                    list.empty();
                    list.data('tokenize').clear();
                    for (var i = 0; i < response.owner.length; i++) {
                        list.append('<option value="' + response.owner[i].id + '">' + response.owner[i].name + '</option>');
                    }
                    $("#date").val(response.date);
                    $("#time").val(response.time);
                    $("#time1").val(response.del_date);
                    $("#time5").val(response.del_time);
                }
            };
            xmlhttp.send();
        }

        // Скриваємо видалення
        $("#time3, #time4").hide();
        $('#check').click(function () {
            $("#time3, #time4").toggle(this.checked);
        });

        // Опрацювання публікування поста
        $(document).ready(function () {
            $("#submit_modal").click(function () {
                if ($("#tokenize_focus").val() == null) {
                    toastrNotification('warning', "Не вибрано груп");
                    return;
                }
                $.post(
                        "/post?action=publishPostId",
                        {
                            postId: publishPostId,
                            date: $("#date").val(),
                            time: $("#time").val(),
                            date_delete: $("#time1").val(),
                            time_delete: $("#time5").val(),
                            checked: $("#check").prop('checked'),
                            groups: $("#tokenize_focus").val().toString()
                        },
                        onAjaxSuccess
                );
                function onAjaxSuccess(response) {
                    for (var i = 0; i < response.length; i++)
                        toastrNotification(response[i].status, response[i].msg);
                    $("#publish_modal").modal('toggle');
                }
            });
        });

    </script>

</div>
</body>
</html>

