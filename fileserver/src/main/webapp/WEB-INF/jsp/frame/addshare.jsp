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
                                <button class="ui button" data-filter="all">所有文件</button>
                                <button class="ui button" data-filter="1">班级共享</button>
                                <button class="ui button" data-filter="2">小组共享</button>
                                <button class="ui button" data-filter="3">个人共享</button>
                            </div>

                            <div class="blue ui buttons">
                                <button class="ui button greenli" data-shuffle>共享中心</button>
                                <button class="ui button redli" data-sortAsc>添加共享</button>
<%--                                <button class="ui button yellowli" data-sortDesc>降序</button>--%>
                            </div>

                            <!-- To choose the value by which you want to sort add -->
                            <div class="ui query">
                                <input class="query"  type="text" name="gender">
                                <i class="my-icon lsm-sidebar-icon icon-chazhao querybtn"></i>
                            </div>
                            <div class="ui divider"></div>
                            <div class="filtr-container">
                                萨顶顶
                                <input id="button" type="button" value="调用parent.html中的say()函数" onclick="callParent()"/>
                            </div>
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
