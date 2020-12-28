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
    <link rel="stylesheet" type="text/css" href="/css/frame/share.css" />
    <link rel="stylesheet" type="text/css" href="/css/frame/addshare.css" />
    <link rel="stylesheet" type="text/css" href="/fonts/iconfont1.css">
    <link rel="stylesheet" type="text/css" href="/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/bootstrap/bootstrap-select/bootstrap-select.min.css">

    <script src="/js/jquery/jquery-3.5.1.min.js"></script>
    <script src="/js/bootstrap/bootstrap.min.js"></script>
    <script src="/js/bootstrap/bootstrap-select/bootstrap-select.min.js"></script>
    <script src="/js/bootstrap/bootstrap-select/i18n/defaults-zh_CN.min.js"></script>
    <script type="text/javascript" src="/js/frame/addshare.js"></script>
</head>
<style>
    body{
        margin: 8px;
    }
</style>
<body>
<div id="contextWrap">
    <div class="mainWrap navslide">
        <div class="ui equal width left aligned padded grid stackable">
            <!--Site Content-->
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
                                <div class="smailtitel">选择文件</div>
                                <div class="ui filelist">
                                    <!--
                                                                            <div class="file" filename="文件1" filepath="路径1">
                                                                                <img class="filetypeimg" src="img/fileicon/png/1140192.png"/>
                                                                                <div class="sharefilename">中国地图撒大苏打实打实1</div>
                                                                                <div class="delfile" >
                                                                                    <i class="my-icon lsm-sidebar-icon icon-shezhi" style="font-size: 30px;"></i>
                                                                                </div>
                                                                            </div>
                                    -->


                                    <!--
                                                                            <div id="addfile" class="addfile">
                                                                                <input type="file" id="inp_file" class="inp_file" style="display: none;" multiple="multiple">
                                                                                <i id="addfilebtn" class="my-icon lsm-sidebar-icon icon-shezhi"></i>
                                                                            </div>
                                    -->
                                </div>
                                <div class="smailtitel">共享设置</div>
                                <div class="ui filemesg" style="">
                                    <div class="user-title" style="width:100%;height:45px;">
                                        <div id="channels11" style="float: left;margin-top:5px;margin-left: 5px;width: auto;">
                                            <select name="channels" id="queryLevels" title="选择共享级别" data-style="btn-primary" class="selectpicker">

                                            </select>
                                        </div>
                                    </div>

                                    <table class="table" style="width:100%;border: 1px solid rgba(34, 36, 38, .15); ">
                                        <thead>
                                        <tr><th><input id="checkAll" type="checkbox"/></th><th>
                                            <div style="float: left;line-height: 30px;">目标</div>
                                            <div  id="yearScreenDiv" >
                                                <select name="channels" id="yearScreen" data-style="btn-info" class="selectpicker"  data-live-search="true" >
                                                    <option value="0">全部学年</option>

                                                </select>
                                            </div>
                                            <div  id="gradeScreenDiv" >
                                                <select name="channels" id="gradeScreen" data-style="btn-info" class="selectpicker"  data-live-search="true" >
                                                    <option  value="0">全部院系</option>

                                                </select>
                                            </div>
                                            <div  id="classScreenDiv" >
                                                <select name="channels" id="classScreen" data-style="btn-info" class="selectpicker"  data-live-search="true" >
                                                    <option  value="0">全部班级</option>
                                                </select>
                                            </div>
                                            <div  id="groupScreenDiv" >
                                                <select name="channels" id="groupScreen" data-style="btn-info" class="selectpicker"  data-live-search="true" >
                                                    <option  value="0">全部小组</option>
                                                </select>
                                            </div>
                                        </th></tr>
                                        </thead>
                                        <tbody id="selecttarget">
                                        <tr class="target" ><td><input data_id=1 name="checkItem" type="checkbox" /></td><td>郭靖</td></tr>
                                        <tr class="target" ><td><input data_id=2 name="checkItem" type="checkbox" /></td><td>黄蓉</td></tr>
                                        <tr class="target" ><td><input data_id=3 name="checkItem" type="checkbox" /></td><td>杨过</td></tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="blue ui buttons commit-1">
                                <button class="ui button " id="submit" data-shuffle>上传</button>
                            </div>
                            <div class="blue ui buttons reset-1">
                                <button class="ui button red" id="reset" data-filter="all">重置</button>
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
