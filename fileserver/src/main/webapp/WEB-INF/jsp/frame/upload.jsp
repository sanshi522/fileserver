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
    <link rel="stylesheet" type="text/css" href="/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/frame/upload.css" />
    <link rel="stylesheet" type="text/css" href="/fonts/iconfont1.css">


    <script src="/js/jquery/jquery-3.5.1.min.js"></script>
    <script src="/js/spark-md5.min.js"></script>

    <script type="text/javascript" src="/js/frame/upload.js"></script>
<%--    <script type="text/javascript" src="/js/frame/uploadworker.js"></script>--%>
</head>
<body style="margin: 0;overflow:hidden">
<div class="viewimg" onclick="maxview()">
    <img class="uploadimg" src="./images/upload/uploadnotalk.png" />
    <div class="talknum" >
        12
    </div>
</div>
<div class="uptitle" >文件上传
    <div class="minbtn" style="" onclick="minview()">
        <i id='addfilebtn' class='my-icon lsm-sidebar-icon icon-tianjiazengjiajia'></i>
    </div>
</div>

<div class="talks" >
<%--    <div class="talk finishtalk">--%>
<%--        <div class="filename">文件名字.png</div>--%>
<%--        <div class="talkstate">已完成 - 361.60KB</div>--%>
<%--        <div class="deltalkdiv">--%>
<%--            <i class="my-icon lsm-sidebar-icon icon-shezhi deltalkbth"></i>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--    <div class="talk nowtalk">--%>
<%--        <div class="filename">文件名字.png</div>--%>
<%--        <div  class="progress progress-striped active" style="width:240px;float: left;">--%>
<%--            <div  class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" >12%--%>
<%--            </div>--%>
<%--        </div>--%>
<%--        <div class="talkstate"> - 560.10MB</div>--%>
<%--        <div class="pausetalkdiv">--%>
<%--            <i class="my-icon lsm-sidebar-icon icon-shezhi pausetalkbth"></i>--%>
<%--        </div>--%>
<%--    </div>--%>
</div>
<div class="talking">
    当前任务：0
</div>
</body>
</html>
