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

    <%--<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.tokenize.js"></script>--%>
    <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery.tokenize.css"/>--%>


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
    <!--custom tagsinput-->
    <script src="${pageContext.request.contextPath}/js/jquery.tagsinput.js"></script>
    <script src="${pageContext.request.contextPath}/js/form-component.js"></script>

    <script src="${pageContext.request.contextPath}/js/jquery-1.8.3.min.js"></script>

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
    <link href="${pageContext.request.contextPath}/css/multi-select.css" media="screen" rel="stylesheet"
          type="text/css"/>
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
                                                <%--class="tokenize-sample"--%>
                                                <%--style="width: 350px; margin: 0px; padding: 0px; border: 0px; display: none;">--%>
                                            </select>
                                            <%--<script type="text/javascript">--%>
                                            <%--$('select#tokenize_read').tokenize({displayDropdownOnFocus: true});--%>
                                            <%--</script>--%>
                                        </td>
                                        <td>
                                            <select id="tokenize_write" multiple="multiple">
                                                <%--class="tokenize-sample"--%>
                                                <%--style="width: 350px; margin: 0px; padding: 0px; border: 0px; display: none;">--%>

                                            </select>

                                            <%--<script type="text/javascript">--%>
                                            <%--$('select#tokenize_write').tokenize({displayDropdownOnFocus: true});--%>
                                            <%--</script>--%>
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


                        <input type="button" value="add" id="addbtn"/>


                        <div id="overlay" class="web_dialog_overlay"></div>


                        <div id="dialog" class="web_dialog">
                            <table style="width: 100%; border: 0px;" cellpadding="3" cellspacing="0">
                                <tr>
                                    <td class="web_dialog_title">Adding Public</td>
                                    <td class="web_dialog_title align_right">
                                        <a href="#" id="btnClose">Close</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                </tr>
                                <tr>
                                    <td colspan="2" style="padding-left: 15px;">
                                        <b>Paste public url, pls </b>
                                    </td>
                                </tr>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                </tr>
                                <tr>
                                    <td>
                                        <input type="text" id="publicUrl"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                </tr>
                                <tr>
                                    <td colspan="2" style="text-align: center;">
                                        <input id="btnSubmit" type="button" value="Submit"/>
                                    </td>
                                </tr>
                            </table>
                        </div>


                    </div>
                </div>
            </div>


        </section>


    </section>
</section>
<!--main content end-->
</section>
<!-- container section end -->

<!-- javascripts -->

<script src="${pageContext.request.contextPath}/js/jquery.multi-select.js" type="text/javascript"></script>

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
                var parsed = JSON.parse(xhr.responseText.replace(/\u0000/g, ""));
                setOptions(parsed);
            }
        }
        var url = '/OptionFillingServlet?';
        var param = "ownerID=".concat(i);
        alert(url.concat(param));
        xhr.open('GET', url.concat(param), false);
        xhr.send(null);

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

<style type="text/css">

    .web_dialog_overlay {
        position: fixed;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
        height: 100%;
        width: 100%;
        margin: 0;
        padding: 0;
        background: #000000;
        opacity: .15;
        filter: alpha(opacity=15);
        -moz-opacity: .15;
        z-index: 101;
        display: none;
    }

    .web_dialog {
        display: none;
        position: fixed;
        width: 380px;
        height: 200px;
        top: 50%;
        left: 50%;
        margin-left: -190px;
        margin-top: -100px;
        background-color: #ffffff;
        border: 2px solid #336699;
        padding: 0px;
        z-index: 102;
        font-family: Verdana;
        font-size: 10pt;
    }

    .web_dialog_title {
        border-bottom: solid 2px #336699;
        background-color: #336699;
        padding: 4px;
        color: White;
        font-weight: bold;
    }

    .web_dialog_title a {
        color: White;
        text-decoration: none;
    }

    .align_right {
        text-align: right;
    }

</style>

</body>
</html>



