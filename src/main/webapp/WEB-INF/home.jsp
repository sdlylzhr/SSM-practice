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

<%--<form action="/uploadPic" method="POST" enctype="multipart/form-data">--%>
    <%--<input type="text" name="pid">--%>
    <%--文件1: <input type="file" name="myfiles"/><br/>--%>
    <%--<input type="submit" value="上传"/>--%>
<%--</form>--%>

<form action="/addnew" method="post">
    学生姓名:<input type="text" name="name"><br>
    学生爱好: <input type="text" name="hobby"><br>
    <input type="submit" value="添加新学生">
</form>

<a href="/searchpage">查询页面</a>


</body>
</html>
