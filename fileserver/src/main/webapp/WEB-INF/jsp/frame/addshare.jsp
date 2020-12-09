<%--
  Created by IntelliJ IDEA.
  User: 致颜文磊
  Date: 2020/9/27
  Time: 17:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件共享</title>
    <link rel="stylesheet" type="text/css" href="/css/share/share.css" />
    <link rel="stylesheet" type="text/css" href="/css/share/addshare.css" />
    <link rel="stylesheet" type="text/css" href="/fonts/iconfont1.css">
    <script src="/js/jquery-3.5.1.min.js"></script>
    <script type="text/javascript" src="/js/frame/addshare.js"></script>
</head>
<body>
<div id="contextWrap">
    <div class="mainWrap navslide">
        <div class="ui equal width left aligned padded grid stackable">
            <!--Site Content-->
            <div class="row">
                <div class="sixteen wide tablet sixteen wide computer column">
                    <div class="ui segments">
                        <div class="ui segment">
                            <h3 class="ui header">添加共享</h3>
                        </div>
                        <div class="ui segment fullheight">
                            <div class="blue ui buttons">
                                <button class="ui button greenli" data-shuffle>共享中心</button>
                                <button class="ui button redli" data-sortAsc>我的共享</button>
                            </div>
                            <div class="ui divider"></div>
                            <div class="filtr-container">
                                <div class="ui filelist" style="">

                                </div>
                                <div class="ui filemesg" style="">

                                </div>
                            </div>
                        </div>
                        <div class="blue ui buttons commit-1">
                            <button class="ui button " data-shuffle>添加上传</button>
                        </div>
                        <div class="blue ui buttons rest-1">
                                <button class="ui button red" data-filter="all">重置</button>
                        </div>
                    </div>
                </div>
            </div>
            <!--Site Content-->
        </div>
    </div>
</div>
<!--jquery-->

</body>
</html>
