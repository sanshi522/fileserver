<%--
  Created by IntelliJ IDEA.
  User: 致颜文磊
  Date: 2020/12/8
  Time: 15:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件上传</title>
    <script src="/js/jquery-3.5.1.min.js"></script>
    <script type="text/javascript" src="/js/frame/upload.js"></script>
</head>
<body style="margin: 0;overflow:hidden">
<div class="viewimg" onclick="javascript:maxview()" style="width:40px;height:40px;border: 1px dashed red;position:relative">
    <div style="width:40px;height:15px;border: 1px dashed red;position:absolute;bottom: 0px;z-index: 10;text-align: center;font-size: 8px;">
        12
    </div>
</div>
<div class="upload-tile" style="width:100%;height:20px;border: 1px dashed #216AAF;background: #216AAF;color:#C2E3E8;display:none;">
    文件上传
    <input id="button" type="button" value="-" style="float: right" onclick="minview()">

</div>
<div class="talk" style="width:100%;height:100%;border: 1px dashed #216AAF;display:none;">

</div>
</body>
</html>
