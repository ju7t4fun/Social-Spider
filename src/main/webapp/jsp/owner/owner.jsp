<%--
  Created by IntelliJ IDEA.
  User: Sasha
  Date: 19.06.2015
  Time: 15:09
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

    <title>All Posts</title>

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
    <%--<script src="${pageContext.request.contextPath}/js/jquery.tokenize.js"></script>--%>

    <%--for table--%>
    <link href="http://cdn.datatables.net/1.10.3/css/jquery.dataTables.css" rel="stylesheet" type="text/css">
    <link href="http://datatables.net/release-datatables/extensions/ColVis/css/dataTables.colVis.css" rel="stylesheet"
          type="text/css">
    <script src="http://cdn.datatables.net/1.10.3/js/jquery.dataTables.min.js"></script>
    <script src="http://datatables.net/release-datatables/extensions/ColVis/js/dataTables.colVis.js"></script>
    <script src="http://jquery-datatables-column-filter.googlecode.com/svn/trunk/media/js/jquery.dataTables.columnFilter.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/fnStandingRedraw.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/plugin/fnSetFilteringDelay.js"></script>

    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">

    <script src="${pageContext.request.contextPath}/js/jquery.multi-select.js" type="text/javascript"></script>
    <link href="${pageContext.request.contextPath}/css/multi-select.css" media="screen" rel="stylesheet">

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 -->
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
    <script src="${pageContext.request.contextPath}/js/respond.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/lte-ie7.js"></script>
    <![endif]-->

    <link href="${pageContext.request.contextPath}/css/toastr.css" rel="stylesheet" type="text/css"/>
    <script src="${pageContext.request.contextPath}/js/toastr.js"></script>
    <script type="text/javascript">

        // При завантаженні сторінки
        setTimeout(function () {
            if (${toastr_notification!=null}) {
                var args = "${toastr_notification}".split("|");
                toastrNotification(args[0], args[1]);
            }
        }, 500);

        function removeOwner(id, elm) {
            var xmlhttp = new XMLHttpRequest();
            xmlhttp.open('GET', '/owner?action=remove&id=' + id, true);
            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState == 4) {
                    var response = JSON.parse(xmlhttp.responseText);
                    toastrNotification(response.status, response.msg);
                    if (response.status === 'success') {
                        $('#ownersTable').DataTable().row($(this).parents('tr'))
                                .remove().draw();
                    }
                }
            };
            xmlhttp.send(null);
        }
    </script>

    <script type="text/javascript">
        var table;

        jQuery(document).ready(function () {
            table = $('#ownersTable').dataTable({

                "bSort": false,
                "bPaginate": true,
                "paging": true,
                "bInfo": false,
                "iDisplayStart": 0,
                "bProcessing": true,
                'iDisplayLength': 10,
                "bServerSide": true,
                "sAjaxSource": "http://localhost:8080/owner?action=get",
                colVis: {
                    "align": "right",
                    "buttonText": "columns <img src=\"/img/caaret.png\"/>",
                },

                "columnDefs": [{
                    "aTargets": [0], "createdCell": function (td, cellData, rowData, row, col) {
                        if (cellData > 0) {
                            $(td).html('<i class="fa fa-user"> ' + Math.abs(cellData) + '</i>');
                        } else {
                            $(td).html('<i class="fa fa-users"> ' + Math.abs(cellData) + '</i>');
                        }
                    }
                }, {
                    "aTargets": [1], "createdCell": function (td, cellData, rowData, row, col) {
                        if (rowData[0] > 0) {
                            $(td).html('<a href="http://vk.com/id' + rowData[0] + '" target="_blank">' + cellData + '</a>');
                        } else {
                            $(td).html('<a href="http://vk.com/public' + Math.abs(rowData[0]) + '" target="_blank">' +
                                    cellData + '</a>');
                        }
                    }
                }, {
                    "aTargets": [2], "createdCell": function (td, cellData, rowData, row, col) {
                        $(td).html('<a class="btn btn-default" onclick="PopUpShow(' + cellData + ')"><span class="fa fa-users"></span></a>');
                    }
                }, {
                    "aTargets": [3], "createdCell": function (td, cellData, rowData, row, col) {
                        $(td).html('<a class="btn btn-primary" href=""><span class="fa fa-bar-chart"></span></a>');
                    }
                }, {
                    "aTargets": [4], "createdCell": function (td, cellData, rowData, row, col) {
                        $(td).html('<div class="btn-group"><a class="btn btn-success" href="#"><i class="icon_pencil-edit"></i></a><a class="btn btn-danger" onclick="removeOwner(' + cellData + ',this)"><i class="icon_close_alt2"></i></a></div>');
                    }
                }, {
                    "width": "60%", "targets": 1
                }, {
                    "class": "dt-body-left", "targets": [1]
                }, {
                    "width": "2%", "targets": [2, 3, 4]
                }]

            });

//            $(".dataTables_filter").attr("hidden", "");
            $(".dataTables_length").attr("hidden", "");
            var dataTables_filter_input = $(".dataTables_filter").find("input");
            dataTables_filter_input.attr("class", "form-control");
            dataTables_filter_input.attr("style", "width: 500px")

        })
    </script>

</head>
<body>

<!-- container section start -->
<section id="container" class="">

    <jsp:include page="../pagecontent/header.jsp"/>
    <jsp:include page="../pagecontent/sidebar.jsp"/>

    <jsp:include page="../post/viewpost.jsp"/>

    <section id="main-content">
        <section class="wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header" style="width: 80%"><i class="fa fa-list-alt"></i> Created</h3>
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

                        <div class="b-popup" id="popup1">
                            <div class="b-popup-content">
                                <table>
                                    <tr>
                                        <th style="width:400px;">Read</th>
                                        <th style="width:400px;">Write</th>
                                    </tr>
                                    <tr>
                                        <td>
                                            <select id="tokenize_read" multiple="multiple">
                                            </select>
                                        </td>
                                        <td>
                                            <select id="tokenize_write" multiple="multiple">
                                            </select>
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

                        <div class="panel-body">
                            <div id="active" class="tab-pane active">
                                <div class="col-lg-12">
                                    <table width="100%" border="0" margin="0" padding="0"
                                           class="row-border tableHeader" id="ownersTable">
                                        <thead>
                                        <tr style="align-content: center">
                                            <th>id</th>
                                            <th>Name</th>
                                            <th>Binding account</th>
                                            <th>Statistics</th>
                                            <th>Action</th>
                                        </tr>
                                        </thead>
                                    </table>
                                    <a class="btn btn-primary" href="">Add group</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </section>
</section>
</body>
</html>

<script>

    var rowID;
    var selectedProfilesRead;
    var selectedProfilesWrite;
    var profilesWrite;
    var profilesRead;


    $(document).ready(function () {

        //start of add row
//       table.makeEditable({
//            sUpdateURL: "UpdateData.php",
//            sAddURL: "AddData.php",
//            sDeleteURL: "DeleteData.php"
//        })


        $("#btnClose").click(function (e) {
            HideDialog();
            e.preventDefault();
        });

        $("#btnSubmit").click(function (e) {

            var xmlhttp = new XMLHttpRequest();
            xmlhttp.open("POST", "/AddNewOwnerServlet", true);
            xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xmlhttp.send("publicUrl=" + document.getElementById("publicUrl").value);

            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState == 4) {
                    var parsed = JSON.parse(xmlhttp.responseText.replace(/\u0000/g, ""));
//                    alert(parsed.SelectedProfilesRead);
                    table.fnStandingRedraw();
                }
            }

            HideDialog();
            e.preventDefault();
        });


        //end of popup adding row


        $('#tokenize_read').multiSelect(
                {
                    selectableHeader: "<div class='custom-header'>Non Selected Profiles</div>",
                    selectionHeader: "<div class='custom-header'>Selected Profiles</div>"
                }
        );

        $('#tokenize_write').multiSelect(
                {
                    selectableHeader: "<div class='custom-header'>Non Selected Profiles</div>",
                    selectionHeader: "<div class='custom-header'>Selected Profiles</div>"
                }
        );


        $("#popup1").hide();
        selectedProfilesRead = [];
        selectedProfilesWrite = [];
        profilesWrite = [];
        profilesRead = [];
    });

    function ShowDialog(modal) {
        $("#overlay").show();
        $("#dialog").fadeIn(300);

        if (modal) {
            $("#overlay").unbind("click");
        }
        else {
            $("#overlay").click(function (e) {
                HideDialog();
            });
        }
    }

    function HideDialog() {
        $("#overlay").hide();
        $("#dialog").fadeOut(300);
    }

    function initializeArraysFromRequest(jsFile) {

        var arr = [];
        arr = jsFile.SelectedProfilesRead;
        var i;
        if (arr != undefined) {
            for (i = 0; i < arr.length; i++) {
                selectedProfilesRead.push(arr[i].id);
            }
        }
        arr = jsFile.SelectedProfilesWrite;
        if (arr != undefined) {
            for (i = 0; i < arr.length; i++) {
                selectedProfilesWrite.push(arr[i].id);
            }
        }
        arr = jsFile.ProfilesWrite;
        if (arr != undefined) {
            for (i = 0; i < arr.length; i++) {
                profilesWrite.push(arr[i].id);
            }
        }
        arr = jsFile.ProfilesRead;
        if (arr != undefined) {

            for (i = 0; i < arr.length; i++) {

                profilesRead.push(arr[i].id);
            }
        }
    }
    function setOptions(jsFile) {

        selectedProfilesRead = [];
        selectedProfilesWrite = [];
        profilesWrite = [];
        profilesRead = [];


        $('#tokenize_read').multiSelect('refresh');
        $('#tokenize_write').multiSelect('refresh');

        initializeArraysFromRequest(jsFile);

        if (selectedProfilesRead != undefined) {
            for (i = 0; i < selectedProfilesRead.length; i++) {
                $('#tokenize_read').multiSelect('addOption', {
                    value: ''.concat(selectedProfilesRead[i]),
                    text: 'Profile ID : '.concat(selectedProfilesRead[i]), index: 0
                });
                $('#tokenize_read').multiSelect('select', ''.concat(selectedProfilesRead[i]));
            }
        }
        if (selectedProfilesWrite != undefined) {
            for (i = 0; i < selectedProfilesWrite.length; i++) {
                $('#tokenize_write').multiSelect('addOption', {
                    value: ''.concat(selectedProfilesWrite[i]),
                    text: 'Profile ID : '.concat(selectedProfilesWrite[i]), index: 0
                });
                $('#tokenize_write').multiSelect('select', ''.concat(selectedProfilesWrite[i]));
            }
        }

        if (profilesRead != undefined) {
            for (var i = 0; i < profilesRead.length; i++) {
                $('#tokenize_read').multiSelect('addOption', {
                    value: ''.concat(profilesRead[i]),
                    text: 'Profile ID : '.concat(profilesRead[i]), index: 0
                });
            }
        }
        if (profilesWrite != undefined) {
            for (var i = 0; i < profilesWrite.length; i++) {
                $('#tokenize_write').multiSelect('addOption', {
                    value: ''.concat(profilesWrite[i]),
                    text: 'Profile ID : '.concat(profilesWrite[i]), index: 0
                });
            }
        }
    }


    function PopUpShow(i) {
        document.getElementById("tokenize_read").options.length = 0;
        document.getElementById("tokenize_write").options.length = 0;
        rowID = i;
        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4) {
                alert(xhr.responseText);
                var parsed = JSON.parse(xhr.responseText.replace(/\u0000/g, ""));
                setOptions(parsed);
            }
        };
        var url = '/OptionFillingServlet?';
        var param = "ownerID=".concat(i);
        alert(url.concat(param));
        xhr.open('GET', url.concat(param), false);
        xhr.send();

        $("#popup1").show();
    }
    function PopUpHide() {
        sendRequest(rowID);
        $("#popup1").hide();

    }

    function sendRequest(index) {

        var afterSelectionRead = document.getElementById("tokenize_read").options;
        var afterSelectionWrite = document.getElementById("tokenize_write").options;
        var readResult = [];
        var writeResult = [];
        for (var i = 0; i < afterSelectionRead.length; i++) {
            if (afterSelectionRead[i].selected)
                readResult.push(afterSelectionRead[i].value);
        }
        for (var i = 0; i < afterSelectionWrite.length; i++) {
            if (afterSelectionWrite[i].selected)
                writeResult.push(afterSelectionWrite[i].value);
        }

        var jsonRes = {
            "ownerVkId": index,
            "Read": readResult,
            "Write": writeResult
        };

        var xmlhttp = new XMLHttpRequest();
        xmlhttp.open("POST", "/jsonhandler");
        xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
        xmlhttp.send(JSON.stringify(jsonRes));
    }


</script>
