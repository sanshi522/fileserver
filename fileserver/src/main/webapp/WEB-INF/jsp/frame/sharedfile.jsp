<%--
  Created by IntelliJ IDEA.
  User: 致颜文磊
  Date: 2020/9/23
  Time: 10:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="/js/jquery/jquery-3.5.1.min.js"></script>
    <script src="/js/spark-md5.min.js"></script>
    <script src="/js/sharedfile.js"></script>
</head>
<body>
<input id="file" type="file"/>
<br/><br/>
<button id="upload">分片上传</button>
<p id="process">0<span>%</span></p>
<button id="try" style="display: none">重试</button>
</body>
</html>
