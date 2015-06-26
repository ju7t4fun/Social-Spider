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

    <link href="${pageContext.request.contextPath}/css/multi-select.css" media="screen" rel="stylesheet" type="text/css"/>
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
                                            <select id="tokenize_read" multiple="multiple"   >
                                                <%--class="tokenize-sample"--%>
                                                    <%--style="width: 350px; margin: 0px; padding: 0px; border: 0px; display: none;">--%>
                                            </select>
                                            <%--<script type="text/javascript">--%>
                                                <%--$('select#tokenize_read').tokenize({displayDropdownOnFocus: true});--%>
                                            <%--</script>--%>
                                        </td>
                                        <td>
                                            <select id="tokenize_write" multiple="multiple" >
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
                $('#tokenize_read').multiSelect('addOption', { value: ''.concat(selectedProfilesRead[i]),
                    text: 'Profile ID : '.concat(selectedProfilesRead[i]), index: 0 });
                $('#tokenize_read').multiSelect('select', ''.concat(selectedProfilesRead[i]));
            }
        }
        if (selectedProfilesWrite != undefined) {
            for (i = 0; i < selectedProfilesWrite.length; i++) {
                $('#tokenize_write').multiSelect('addOption', { value: ''.concat(selectedProfilesWrite[i]),
                    text: 'Profile ID : '.concat(selectedProfilesWrite[i]), index: 0 });
                $('#tokenize_write').multiSelect('select', ''.concat(selectedProfilesWrite[i]));
            }
        }

        if (profilesRead != undefined) {
            for (var i = 0; i < profilesRead.length - 1; i++) {
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

        var jsonRes = {"ownerVkId" : index,
                        "Read" :  readResult ,
                        "Write" : writeResult
                                            };

        var xmlhttp = new XMLHttpRequest();
        xmlhttp.open("POST", "/jsonhandler");
        xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
        xmlhttp.send(JSON.stringify(jsonRes));
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



