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


    <!--[if lt IE 9]>
    <![endif]-->
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
                    <section class="panel">
                        <header class="panel-heading">
                            Account binding
                        </header>
                        <table class="table table-striped table-advance table-hover">
                            <tr>
                                <th>Group ID</th>
                                <th>Group Name</th>
                                <th>Account</th>
                            </tr>
                            <tr>
                                <td>111111</td>
                                <td>Group 1</td>

                                <td>
                                    <a href="javascript:PopUpShow1()">
                                        <button style="border-radius: 4px;border-color:#424D5F;background-color:#424D5F;color:#ffffff;margin:5px;padding:10px;">
                                            Bind Accounts...
                                        </button>
                                    </a>

                                    <div class="b-popup" id="popup1">
                                        <div class="b-popup-content">

                                            <table>
                                                <tr>
                                                    <th style="width:400px;">Read</th>
                                                    <th style="width:400px;">Write</th>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <select id="tokenize_focus11" multiple="multiple"
                                                                class="tokenize-sample" style="width:350px;">
                                                            <option value="1">Account 1</option>
                                                            <option value="2">Account 2</option>
                                                            <option value="3">Account 3</option>
                                                            <option value="4">Account 4</option>
                                                            <option value="5">Account 5</option>
                                                        </select>

                                                        <script type="text/javascript">
                                                            $('select#tokenize_focus11').tokenize({displayDropdownOnFocus: true});
                                                        </script>
                                                    </td>
                                                    <td>
                                                        <select id="tokenize_focus12" multiple="multiple"
                                                                class="tokenize-sample" style="width:350px;">
                                                            <option value="1">Account 1</option>
                                                            <option value="2">Account 2</option>
                                                            <option value="3">Account 3</option>
                                                            <option value="4">Account 4</option>
                                                            <option value="5">Account 5</option>
                                                        </select>

                                                        <script type="text/javascript">
                                                            $('select#tokenize_focus12').tokenize({displayDropdownOnFocus: true});
                                                        </script>
                                                    </td>
                                                </tr>

                                            </table>
                                            <a href="javascript:PopUpHide1()">
                                                <button style="border-radius: 4px;border-color:#424D5F;background-color:#424D5F;color:#ffffff;margin:15px;padding:10px;width:150px;">
                                                    Save
                                                </button>
                                            </a>
                                        </div>
                                    </div>

                                </td>
                            </tr>
                            <tr>
                                <td>222222</td>
                                <td>Group 2</td>
                                <td>
                                    <a href="javascript:PopUpShow2()">
                                        <button style="border-radius: 4px;border-color:#424D5F;background-color:#424D5F;color:#ffffff;margin:5px;padding:10px;">
                                            Bind Accounts...
                                        </button>
                                    </a>

                                    <div class="b-popup" id="popup2">
                                        <div class="b-popup-content">
                                            <table>
                                                <tr>
                                                    <th style="width:400px;">Read</th>
                                                    <th style="width:400px;">Write</th>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <select id="tokenize_focus21" multiple="multiple"
                                                                class="tokenize-sample" style="width:350px;">
                                                            <option value="1">Account 1</option>
                                                            <option value="2">Account 2</option>
                                                            <option value="3">Account 3</option>
                                                            <option value="4">Account 4</option>
                                                            <option value="5">Account 5</option>
                                                        </select>

                                                        <script type="text/javascript">
                                                            $('select#tokenize_focus21').tokenize({displayDropdownOnFocus: true});
                                                        </script>
                                                    </td>
                                                    <td>
                                                        <select id="tokenize_focus22" multiple="multiple"
                                                                class="tokenize-sample" style="width:350px;">
                                                            <option value="1">Account 1</option>
                                                            <option value="2">Account 2</option>
                                                            <option value="3">Account 3</option>
                                                            <option value="4">Account 4</option>
                                                            <option value="5">Account 5</option>
                                                        </select>

                                                        <script type="text/javascript">
                                                            $('select#tokenize_focus22').tokenize({displayDropdownOnFocus: true});
                                                        </script>
                                                    </td>
                                                </tr>

                                            </table>
                                            <a href="javascript:PopUpHide2()">
                                                <button style="border-radius: 4px;border-color:#424D5F;background-color:#424D5F;color:#ffffff;margin:15px;padding:10px;width:150px;">
                                                    Save
                                                </button>
                                            </a>
                                        </div>
                                    </div>

                                </td>
                            </tr>
                            <tr>
                                <td>333333</td>
                                <td>Group 3</td>
                                <td>
                                    <a href="javascript:PopUpShow3()">
                                        <button style="border-radius: 4px;border-color:#424D5F;background-color:#424D5F;color:#ffffff;margin:5px;padding:10px;">
                                            Bind Accounts...
                                        </button>
                                    </a>

                                    <div class="b-popup" id="popup3">
                                        <div class="b-popup-content">
                                            <table>
                                                <tr>
                                                    <th style="width:400px;">Read</th>
                                                    <th style="width:400px;">Write</th>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <select id="tokenize_focus31" multiple="multiple"
                                                                class="tokenize-sample" style="width:350px;">
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
                                                        <select id="tokenize_focus32" multiple="multiple"
                                                                class="tokenize-sample" style="width:350px;">
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
                                            <a href="javascript:PopUpHide3()">
                                                <button style="border-radius: 4px;border-color:#424D5F;background-color:#424D5F;color:#ffffff;margin:15px;padding:10px;width:150px;">
                                                    Save
                                                </button>
                                            </a>
                                        </div>
                                    </div>

                                </td>
                            </tr>
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
        PopUpHide1();
        PopUpHide2();
        PopUpHide3();
    });
    function PopUpShow1() {
        $("#popup1").show();
    }
    function PopUpHide1() {
        $("#popup1").hide();
    }
    function PopUpShow2() {
        $("#popup2").show();
    }
    function PopUpHide2() {
        $("#popup2").hide();
    }
    function PopUpShow3() {
        $("#popup3").show();
    }
    function PopUpHide3() {
        $("#popup3").hide();
    }
</script>
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


</body>
</html>


