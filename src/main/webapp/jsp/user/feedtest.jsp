<%--
  Created by IntelliJ IDEA.
  User: maryan
  Date: 02.07.2015
  Time: 14:08
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>Google Plus Bootstrap Theme Demo</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <%--<link href="assets/css/bootstrap.css" rel="stylesheet">--%>
    <!-- Bootstrap CSS -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <!-- bootstrap theme -->
    <link href="${pageContext.request.contextPath}/css/bootstrap-theme.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <link href="${pageContext.request.contextPath}/css/google-plus.css" rel="stylesheet">

</head>

<body class="">
<section id="main-content">
    <section class="wrapper">
        <div class="col-lg-12">
            <h3>Post</h3>
        </div>
        <section class="wrapper" style="margin-left: 180px; margin-top: -30px;">
            <div class="row">
                <div class="col-md-8 portlets">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <ul style="margin-left:-30px;">
                                <li>
                                    <table width="100%" style="padding:0 50px;">
                                        <tr>
                                            <td style="text-align:left;padding-left:20px;"><img
                                                    src="${pageContext.request.contextPath}/img/post.png"
                                                    style="margin:15px;"><strong>GROUP NAME</strong></td>
                                        </tr>
                                        <tr>
                                            <td style="text-align:justify;">
                                                Vivamus tincidunt eleifend congue. Sed lacinia ullamcorper
                                                fringilla.
                                                Suspendisse mi nisi, molestie eget varius quis, cursus nec felis.
                                                Quisque mi tortor, accumsan vitae tempus a, placerat et est. Mauris
                                                eu
                                                mauris urna. Quisque ipsum purus, iaculis nec libero vel, euismod
                                                lacinia est. Proin quis mi et velit scelerisque feugiat. Nunc
                                                porttitor,
                                                nunc et ultricies scelerisque, velit est sollicitudin tortor, ut
                                                condimentum dolor nulla id sem. In suscipit urna a nibh bibendum
                                                cursus.
                                                Sed ex orci, rhoncus at convallis quis, bibendum ac ex. Proin
                                                vulputate
                                                lacus tellus, nec sollicitudin nisl congue eget.
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="text-align:left;padding-left:20px;">
                                                <br>

                                                <div style="color:blue;">#lorem #ipsum #dolor</div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <img src="${pageContext.request.contextPath}/img/bg-1.jpg"
                                                     width="600"
                                                     style="margin:20px;">
                                            </td>
                                        </tr>
                                    </table>
                                </li>

                            </ul>
                        </div>
                    </div>

                </div>
            </div>
        </section>
    </section>
</section>


<script>
    function onSuccess(data) {
        $("#post_text").html(response.postText);
        if (response.attachments.length != 0) {
            var div_gallery = $("#gallery");
            for (var i = 0; i < response.attachments.length; i++) {
                var image = $('<img>');
                if (response.attachments[i].payload.includes("image")) {
                    image.attr("src", "http://localhost:8080" + response.attachments[i].payload);
                    image.attr("data-image", "http://localhost:8080" + response.attachments[i].payload);
                    image.attr("data-description", response.attachments[i].payload);
                } else {
                    image.attr("src", "../img/poster.jpg");
                    image.attr("data-type", "html5video");
                    image.attr("data-image", "../img/poster.jpg");
                    image.attr("data-videomp4", "http://localhost:8080" + response.attachments[i].payload);
                    image.attr("data-description", response.attachments[i].payload);
                }
                div_gallery.append(image);
            }
        }
    }
</script>
<!-- javascripts -->
<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<%--<script type="text/javascript" src="assets/js/jquery.js"></script>--%>
<%--<script type="text/javascript" src="assets/js/bootstrap.js"></script>--%>


</body>
</html>
