<%--
  Created by LZHR.
  User: lizhongren1
  Date: 2018/2/9
  Time: 上午9:29
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
首页!!!

<form action="/uploadPic" method="POST" enctype="multipart/form-data">
    <input type="text" name="pid">
    文件1: <input type="file" name="myfiles"/><br/>
    <input type="submit" value="上传"/>
</form>


</body>
</html>
