<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Creative - Bootstrap 3 Responsive Admin Template">
    <meta name="author" content="GeeksLabs">
    <meta name="keyword" content="Creative, Dashboard, Admin, Template, Theme, Bootstrap, Responsive, Retina, Minimal">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/icons/favicon.png">

    <title>Social Spider</title>

    <!-- Bootstrap CSS -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <!-- bootstrap theme -->
    <link href="${pageContext.request.contextPath}/css/bootstrap-theme.css" rel="stylesheet">
    <!--external css-->
    <!-- font icon -->
    <link href="${pageContext.request.contextPath}/css/elegant-icons-style.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet" />
    <!-- Custom styles -->
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style-responsive.css" rel="stylesheet" />

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}js/language.js"></script>

</head>
<body>
<section id="container" class="">
    <header class="header dark-bg">
        <div class="main">
            <a href="/" class="logo">Social <span class="lite">Spider</span></a>
            <a href="/login" style=""> <span style="color:#fff;line-height:65px;margin-left:600px;">SIGN IN</span> </a>
        </div>
    </header>

    <div class="poster" style="padding-top:130px;">
        <div class="main">
		        <span>
		            <h1>SOCIAL-SPIDER </h1>
                    <h2>make it easy</h2>
				</span>
        </div>
    </div>

    <div class="inf">
        <div class="main">
            <l:resource key="main.things.about.our.service"><h1>LOKI</h1></l:resource>
            <table>
                <tr>
                    <th style="width:350px;height:170px;"><img src="img/m1.png" width="100" height="100"></th>
                    <th style="width:350px;"><img src="img/m2.png" width="100" height="100"></th>
                    <th style="width:350px;"><img src="img/m3.png" width="100" height="100"></th>
                </tr>
                <tr>
                    <td><h2>Easy To Use</h2></td>
                    <td><h2>Powerful Features</h2></td>
                    <td><h2>You'll Love It</h2></td>
                </tr>
            </table>
        </div>
    </div>

    <div class="discover">
        <div class="main">
            <l:resource key="discover.the.features"><h1></h1></l:resource>
            <table>
                <tr> <th class="tableBorder"><h2>Post/Schedule</h2></th> <th class="tableBorder"><h2>Group Lists</h2></th> <th class="tableBorder"><h2>Saved Campaigns</h2></th> <th class="tableBorder"><h2>Posting Statistics</h2></th> </tr>
                <tr>
                    <td> <h4>With Autoposter can schedule or post unlimited groups!
                        You must just follow simple steps for posting
                        <ul>
                            <li>1) Choose Groups List</li>
                            <li>2) Choose Messages & Pictures/Videos</li>
                            <li>3) Choose Inverval & click POST</li>
                        </ul> </h4> </td>
                    <td><h4>With Autoposter all your groups is saved & categorized!
                        Never no check groups one by one & don't need worry about posting unwanted groups!
                        Can create unlimited categories & add them unlimited groups. </h4></td>
                    <td><h4>With Autoposter all your campaign contents is saved.
                        System save all your campaigns contents to drafts so its easy to use your pictures, messages, videos again.
                        You can anytime add more pictures, messages, videos & change them. </h4></td>
                    <td><h4>We collect your campaign clicks & display you statistics.
                        You can monitoring your campaigns & see how popular your posts are.
                        So you know how get best results. </h4></td>
                <tr>
            </table>
        </div>
    </div>

    <div id="testimonials">
        <div class="main">
            <p><h1><l:resource key="what.people.say.about.those.our.service"/></h1></p>

            <div id="speech1">Most usually cotton is employed, with varying levels of ply, with the lower numbers meaning finer quality.
                <div class="corner"><img src="img/corner.png" width="23" height="13"></div>
            </div>

            <div id="speech2">The new technology of the printing press was widely used to publish many arguments.
                <div class="corner"><img src="img/corner.png" width="23" height="13"></div>
            </div>

            <div id="speech3">The mass media face a number of pressures that can prevent them from accurately depicting competing scientific claims.
                <div class="corner"><img src="img/corner.png" width="23" height="13"></div>
            </div>

            <div id="person1">
                <h4>Jim Bianco</h4>
                <h5>Musician</h5>
            </div>

            <div id="person2">
                <h4>Rita MacNeil</h4>
                <h5>Designer</h5>
            </div>

            <div id="person3">
                <h4>Tom Simpson</h4>
                <h5>Developer</h5>
            </div>
        </div>
    </div>

    <div class="footer dark-bg" style="padding-top:30px;padding-bottom:30px;line-height:25px;border-bottom: 0;">
        <div class="main">
            <div style="margin-left:750px;">
                <button class="language-button-logic" change="ua" style="background-color:#fed189; border: 2px solid #fed189;border-radius:10px;">ua</button>
                <button class="language-button-logic" change="en" style="background-color:#00a0df; border: 2px solid #00a0df;border-radius:10px;">en</button>
            </div>
            <div style="margin-top:-20px;">
                <h4>Social-Spider Corp. </h4>
                <h4><p>7 X Evo Ave Rev3 - Victoria 70913 EU</p>
                    <p>(432)555-9876</p>
                </h4>
                <p><a href="mailto:example@gmail.com"><h4>socialspider@gmail.com</h4></a></p>
            </div>
        </div>
    </div>
</section>
</body>
</html>