<%--
  Created by IntelliJ IDEA.
  User: Sasha
  Date: 16.06.2015
  Time: 15:15
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

  <title>Homepage</title>

  <!-- Bootstrap CSS -->
  <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
  <!-- bootstrap theme -->
  <link href="${pageContext.request.contextPath}/css/bootstrap-theme.css" rel="stylesheet">
  <!--external css-->
  <!-- font icon -->
  <link href="${pageContext.request.contextPath}/css/elegant-icons-style.css" rel="stylesheet" />
  <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet" />
  <!-- Custom styles -->
  <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/style-responsive.css" rel="stylesheet" />
  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.tokenize.js"></script>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery.tokenize.css" />
  <!-- HTML5 shim and Respond.js IE8 support of HTML5 -->
  <!--[if lt IE 9]>
  <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
  <!--<script src="${pageContext.request.contextPath}/js/respond.min.js"></script>-->
  <script src="${pageContext.request.contextPath}/js/lte-ie7.js"></script>
  <![endif]-->
</head>

<body>

<!-- container section start -->
<section id="container" class="">

  <jsp:include page="../pagecontent/header.jsp" />
  <jsp:include page="../pagecontent/sidebar.jsp" />

  <!--main content start-->
  <section id="main-content">
    <section class="wrapper">
      <div class="row">
        <div class="col-lg-12">
          <h3 class="page-header"><i class="fa fa-home"></i> Home</h3>
        </div>
      </div>

      <div class="row">
        <div class="col-md-8 portlets">
          <div class="panel panel-default">
            <div class="panel-body">
              <ul style="margin-left:-30px;">
                <li>
                  <table width="100%" style="padding:0 50px;">
                    <tr><td style="text-align:left;padding-left:20px;"><img src="${pageContext.request.contextPath}/img/post.png" style="margin:15px;"><strong>GROUP NAME</strong></td></tr>
                    <tr><td style="text-align:justify;">Vivamus tincidunt eleifend congue. Sed lacinia ullamcorper fringilla. Suspendisse mi nisi, molestie eget varius quis, cursus nec felis. Quisque mi tortor, accumsan vitae tempus a, placerat et est. Mauris eu mauris urna. Quisque ipsum purus, iaculis nec libero vel, euismod lacinia est. Proin quis mi et velit scelerisque feugiat. Nunc porttitor, nunc et ultricies scelerisque, velit est sollicitudin tortor, ut condimentum dolor nulla id sem. In suscipit urna a nibh bibendum cursus. Sed ex orci, rhoncus at convallis quis, bibendum ac ex. Proin vulputate lacus tellus, nec sollicitudin nisl congue eget.</td></tr>
                    <tr><td style="text-align:left;padding-left:20px;">
                      <br>
                      <div style="color:blue;">#lorem #ipsum #dolor</div>
                    </td></tr>
                    <tr><td>
                      <img src="${pageContext.request.contextPath}/img/bg-1.jpg" width="600" style="margin:20px;">
                    </td></tr>
                    <tr><td style="text-align:right;">
                      <input type="image" src="${pageContext.request.contextPath}/img/like.png" style="margin:0 10px;" alt="Like">
                      45
                      <input type="image" src="${pageContext.request.contextPath}/img/speaker.png" style="margin:0 10px;" alt="Share">
                      14
                    </td></tr>
                  </table>
                </li>
                <hr>
                <li>
                  <table width="100%" style="padding:0 50px;">
                    <tr><td style="text-align:left;padding-left:20px;"><img src="${pageContext.request.contextPath}/img/post.png" style="margin:15px;"><strong>GROUP NAME</strong></td></tr>
                    <tr><td style="text-align:justify;">Vivamus tincidunt eleifend congue. Sed lacinia ullamcorper fringilla. Suspendisse mi nisi, molestie eget varius quis, cursus nec felis. Quisque mi tortor, accumsan vitae tempus a, placerat et est. Mauris eu mauris urna. Quisque ipsum purus, iaculis nec libero vel, euismod lacinia est. Proin quis mi et velit scelerisque feugiat. Nunc porttitor, nunc et ultricies scelerisque, velit est sollicitudin tortor, ut condimentum dolor nulla id sem. In suscipit urna a nibh bibendum cursus. Sed ex orci, rhoncus at convallis quis, bibendum ac ex. Proin vulputate lacus tellus, nec sollicitudin nisl congue eget.</td></tr>
                    <tr><td style="text-align:left;padding-left:20px;">
                      <br>
                      <div style="color:blue;">#lorem #ipsum #dolor</div>
                    </td></tr>
                    <tr><td>
                      <img src="${pageContext.request.contextPath}/img/sample-img-1.jpg" style="margin:20px;">
                      <img src="${pageContext.request.contextPath}/img/sample-img-2.png" style="margin:20px;">
                      <img src="${pageContext.request.contextPath}/img/sample-img-3.png" style="margin:20px;">
                    </td></tr>
                    <tr><td style="text-align:right;">
                      <input type="image" src="${pageContext.request.contextPath}/img/like.png" style="margin:0 10px;" alt="Like">
                      217
                      <input type="image" src="${pageContext.request.contextPath}/img/speaker.png" style="margin:0 10px;" alt="Share">
                      55
                    </td></tr>
                  </table>
                </li>
              </ul>
            </div>
          </div>

        </div>

        <div class="col-md-4 portlets" >
          <div class="panel panel-default" style="position:fixed;margin-left:-10px;">
            <div class="panel-body">
              <h3>FILTER BY</h3>
              <ul>
                <li>
                  <h4>Groups:</h4>
                  <select id="tokenize_focus" multiple="multiple" class="tokenize-sample" style="width:200px;">
                    <option value="1">Group 1</option>
                    <option value="2">Group 2</option>
                    <option value="3">Group 3</option>
                    <option value="4">Group 4</option>
                    <option value="5">Group 5</option>
                  </select>

                  <script type="text/javascript">
                    $('select#tokenize_focus').tokenize({displayDropdownOnFocus:true});
                  </script>
                </li>
                <li style="width:300px;">
                  <h4>Tags: </h4>
                  <input name="tagsinput" id="tagsinput" class="tagsinput"
                         value="Tag1, Tag2, Tag3, Tag4, Tag5"/>
                </li>
                <li>
                  <div class="col-lg-offset-2 col-lg-9" style="margin:20px auto;">
                    <button type="submit" class="btn btn-primary">Filter</button>
                    <button type="reset" class="btn btn-danger">Clear</button>
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </section>
  </section>
  <!--main content end-->
</section>
<!-- container section end -->

<!-- javascripts -->
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

