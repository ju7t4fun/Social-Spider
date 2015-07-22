<html>
<head>
  <title>File Uploading Form</title>
</head>
<body>
<h3>File Upload:</h3>
Select a file to upload: <br />
<form action="${pageContext.request.contextPath}/controller?action=upload&type=images" method="post"
      enctype="multipart/form-data">
  <input type="file" name="file" enctype="multipart/form-data"/>
  <br />
  <input type="submit" value="Upload File" />
</form>
</body>
</html>