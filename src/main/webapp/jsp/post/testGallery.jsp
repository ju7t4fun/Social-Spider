<%--
  Created by IntelliJ IDEA.
  User: maryan
  Date: 01.07.2015
  Time: 15:24
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>Unite Gallery - Default Theme - All Options</title>

  <script type='text/javascript' src='${pageContext.request.contextPath}/js/jquery-11.0.min.js'></script>
  <script type='text/javascript' src='${pageContext.request.contextPath}/js/testunitegallery.min.js'></script>

  <link rel='stylesheet' href='${pageContext.request.contextPath}/css/unite-gallery.css' type='text/css' />

  <script type='text/javascript' src='${pageContext.request.contextPath}/js/ug-theme-default.js'></script>
  <link rel='stylesheet' 		  href='${pageContext.request.contextPath}/css/ug-theme-default.css' type='text/css' />


</head>

<body>

<h2>Default Theme - All Options (watch the page source)</h2>

<div id="gallery" style="display:none;">

  <img alt="Preview Image 1"
       src="${pageContext.request.contextPath}/upload/images/go1dfd7movk5jpv5qu0k3tvbkf.jpeg"
       data-image="${pageContext.request.contextPath}/upload/images/go1dfd7movk5jpv5qu0k3tvbkf.jpeg"
       data-description="Preview Image 1 Description">

  <img alt="Preview Image 2"
       src="https://pp.vk.me/c627725/v627725365/718f/waKgxw_Dkrc.jpg"
       data-image="https://pp.vk.me/c627725/v627725365/718f/waKgxw_Dkrc.jpg"
       data-description="Preview Image 2 Description">

  <img alt="Youtube Video"
       data-type="youtube"
       data-videoid="A3PDXmYoF5U"
       data-description="You can include youtube videos easily!">

  <img alt="Html5 Video"
       src="thumbs/html5_video_thumb.png"
       data-type="html5video"
       data-image="thumbs/html5_video_image.png"
       data-videomp4="${pageContext.request.contextPath}/upload/videos/4bntjaf20mrek53dkcv63ser8i.mp4"
       data-description="Html5 Video Description">

  <img alt="Html5 Video"
       src="thumbs/html5_video_thumb.png"
       data-type="html5video"
       data-image="thumbs/html5_video_image.png"
       data-videomp4="${pageContext.request.contextPath}/upload/musics/d5dddii6bdni25k0779bi5omir.mp3"
       data-description="Html5 Video Description">

    <img alt="VK"
         data-type="VKONTAKTE"
         data-videoid="http://vk.com/video_ext.php?oid=16697463&id=171361804&hash=0089d6a9aa4c5a4a&api_hash=1436283388b9bd4c6cdc40850774"
         data-description="VK VIDEO">
</div>

<script type="text/javascript">

  jQuery(document).ready(function(){

    jQuery("#gallery").unitegallery({

      theme_enable_fullscreen_button: true,	//show, hide the theme fullscreen button. The position in the theme is constant
      theme_enable_play_button: true,			//show, hide the theme play button. The position in the theme is constant
      theme_enable_hidepanel_button: true,	//show, hide the hidepanel button
      theme_enable_text_panel: true,			//enable the panel text panel.

      theme_text_padding_left: 20,			//left padding of the text in the textpanel
      theme_text_padding_right: 5,			//right paddin of the text in the textpanel
      theme_text_align: "left",				//left, center, right - the align of the text in the textpanel
      theme_text_type: "title",				//title, description - text that will be shown on the text panel, title or description

      theme_hide_panel_under_width: 480		//hide panel under certain browser width, if null, don't hide

    });

  });

</script>


</body>
</html>
