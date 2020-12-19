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
    <link rel="stylesheet" type="text/css" href="/css/jquery-pagination/pagination.css">
    <link rel="stylesheet" type="text/css" href="/fonts/iconfont1.css">
    <link rel="stylesheet" type="text/css" href="/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/bootstrap/bootstrap-select/bootstrap-select.min.css">

    <script src="/js/jquery/jquery-3.5.1.min.js"></script>
    <script src="/js/jquery/jquery-pagination/jquery.pagination.js"></script>
    <script src="/js/bootstrap/bootstrap.min.js"></script>
    <script src="/js/bootstrap/bootstrap-select/bootstrap-select.min.js"></script>
    <script src="/js/bootstrap/bootstrap-select/i18n/defaults-zh_CN.min.js"></script>
    <script src="/js/frame/share.js"></script>


</head>
<style>
    .bootstrap-select:not([class*=col-]):not([class*=form-control]):not(.input-group-btn) {
        width: 100px;
    }
    body{
        margin: 8px;
    }
</style>
<body>
<div id="contextWrap">
    <div class="mainWrap navslide">
        <div class="ui equal width left aligned padded grid stackable">
            <!--Site Content-->
<%--            <div class="row">--%>
                <div class="sixteen wide tablet sixteen wide computer column">
                    <div class="ui segments">
                        <div class="ui segment">
                            <h3 class="ui header">共享中心</h3>
                        </div>
                        <div class="ui segment fullheight">
                            <div id="queryLevels" class="blue ui buttons">
<%--                                <button class="ui button" data-filter="0">所有文件</button>--%>
<%--                                <button class="ui button" data-filter="1">学届共享</button>--%>
<%--                                <button class="ui button" data-filter="2">院系共享</button>--%>
<%--                                <button class="ui button" data-filter="3">班级共享</button>--%>
<%--                                <button class="ui button" data-filter="4">小组共享</button>--%>
<%--                                <button class="ui button" data-filter="5">学生共享</button>--%>
<%--                                <button class="ui button" data-filter="6">老师共享</button>--%>
<%--                                <button class="ui button" data-filter="6">管理员共享</button>--%>
                            </div>

                            <div class="blue ui buttons">
                                <button class="ui button greenli" data-shuffle>我的分享</button>
                                <button class="ui button redli" data-sortAsc>添加共享</button>
<%--                                <button class="ui button yellowli" data-sortDesc>降序</button>--%>
                            </div>

                            <!-- To choose the value by which you want to sort add -->
                            <div class="ui query">
                                <input class="query"  type="text" name="gender">
                                <i class="my-icon lsm-sidebar-icon icon-chazhao querybtn"></i>
                            </div>
<%--                            border: #982B2D 1px solid;--%>
                            <div id="screendiv" style="width:100%;float: left;height: 35px;display: none">
                                <div  id="yearScreenDiv" style="float: left;margin-top:5px;margin-left: 5px;width: 100px;display: none">
                                    <select name="channels" id="yearScreen" data-style="btn-info" class="selectpicker"  data-live-search="true" >
                                        <option value="0">全部学年</option>
                                        <option value="1">2021</option>
                                        <option value="2">2020</option>
                                        <option value="3">2019</option>
                                    </select>
                                </div>
                                <div  id="gradeScreenDiv" style="float: left;margin-top:5px;margin-left: 5px;width: 100px;display: none">
                                 <select name="channels" id="gradeScreen" data-style="btn-info" class="selectpicker"  data-live-search="true" >
                                     <option  value="0">全部院系</option>
                                     <option  value="1">1</option>
                                 </select>
                                </div>
                                <div  id="classScreenDiv" style="float: left;margin-top:5px;margin-left: 5px;width: 100px;display: none">
                                <select name="channels" id="classScreen" data-style="btn-info" class="selectpicker"  data-live-search="true" >
                                    <option  value="0">全部班级</option>
                                    <option  value="1">1</option>
                                </select>
                                </div>
                                <div  id="groupScreenDiv" style="float: left;margin-top:5px;margin-left: 5px;width: 100px;display: none">
                                <select name="channels" id="groupScreen" data-style="btn-info" class="selectpicker"  data-live-search="true" >
                                    <option  value="0">全部小组</option>
                                    <option  value="1">1</option>
                                </select>
                                </div>
                                <div  id="teacherScreenDiv" style="float: left;margin-top:5px;margin-left: 5px;width: 100px;display: none">
                                <select name="channels" id="teacherScreen" data-style="btn-info" class="selectpicker"  data-live-search="true" >
                                    <option  value="0">全部老师</option>
                                    <option  value="1">1</option>
                                </select>
                                </div>
                                <div  id="studentScreenDiv" style="float: left;margin-top:5px;margin-left: 5px;width: 100px;display: none">
                                <select name="channels" id="studentScreen" data-style="btn-info" class="selectpicker"  data-live-search="true" >
                                    <option  value="0">全部学生</option>
                                    <option  value="1">1</option>
                                </select>
                                </div>
                                <div  id="adminScreenDiv" style="float: left;margin-top:5px;margin-left: 5px;width: 100px;display: none">
                                <select name="channels" id="adminScreen" data-style="btn-info" class="selectpicker"  data-live-search="true" >
                                    <option  value="0">全部管理员</option>
                                    <option  value="1">1</option>
                                </select>
                                </div>
                            </div>
                            <div class="ui divider"></div>
                            <div class="filtr-container">
                                <div class="filtr-item" data-category="1, 2" data-sort="value">
                                    <img src="../../images/file_logo_png/1140150.png"/>
                                    <div class="sharefilename">采集文件</div>
                                    <div class="susdiv"></div>
                                </div>
                                <div class="filtr-item" data-category="2" data-sort="value">
                                    <img src="../../images/file_logo_png/1140192.png"/>
                                    <div class="sharefilename">中国地图</div>
                                    <div class="susdiv"></div>
                                </div>
                                <div class="filtr-item" data-category="1" data-sort="value">
                                    <img src="../../images/file_logo_png/1140192.png"/>
                                    <div class="sharefilename">中国地图</div>
                                    <div class="susdiv"></div>
                                </div>

                                <div class="filtr-item" data-category="1" data-sort="value">
                                    <img src="../../images/file_logo_png/1140192.png"/>
                                    <div class="sharefilename">中国地图</div>
                                    <div class="susdiv"></div>
                                </div>
                                <div class="filtr-item" data-category="2" data-sort="value">

                                    <img src="../../images/file_logo_png/1140192.png"/>
                                    <div class="sharefilename">中国地图</div>
                                    <div class="susdiv"></div>
                                </div>
                                <div class="filtr-item" data-category="1" data-sort="value">

                                    <img src="../../images/file_logo_png/1140192.png"/>
                                    <div class="sharefilename">中国地图</div>
                                    <div class="susdiv"></div>
                                </div>
                                <div class="filtr-item" data-category="3" data-sort="value">
                                    <img src="../../images/file_logo_png/1140192.png"/>
                                    <div class="sharefilename">中国地图</div>
                                    <div class="susdiv"></div>
                                </div>
                                <div class="filtr-item" data-category="2, 3" data-sort="value">
                                    <img src="../../images/file_logo_png/1140192.png"/>
                                    <div class="sharefilename">中国地图</div>
                                    <div class="susdiv"></div>
                                </div>
                                <div class="filtr-item" data-category="2" data-sort="value">
                                    <img src="../../images/file_logo_png/1140192.png"/>
                                    <div class="sharefilename">中国地图</div>
                                    <div class="susdiv"></div>
                                </div>
                                <div class="filtr-item" data-category="3" data-sort="value">

                                    <img src="../../images/file_logo_png/1140192.png"/>
                                    <div class="sharefilename">中国地图</div>
                                    <div class="susdiv"></div>
                                </div>
                                <div class="filtr-item" data-category="2, 3" data-sort="value">
                                    <img src="../../images/file_logo_png/1140192.png"/>
                                    <div class="sharefilename">中国地图</div>
                                    <div class="susdiv"></div>
                                </div>
                                <div class="filtr-item" data-category="3" data-sort="value">
                                    <img src="../../images/file_logo_png/1140192.png"/>
                                    <div class="sharefilename">中国地图</div>
                                    <div class="susdiv"></div>
                                </div>
                                <div class="filtr-item" data-category="2" data-sort="value">

                                    <img src="../../images/file_logo_png/1140192.png"/>
                                    <div class="sharefilename">中国地图</div>
                                    <div class="susdiv"></div>
                                </div>
                                <div class="filtr-item" data-category="2" data-sort="value">
                                    <img src="../../images/file_logo_png/1140192.png"/>
                                    <div class="sharefilename">中国地图</div>
                                    <div class="susdiv"></div>
                                </div>
                                <div class="filtr-item" data-category="2, 3" data-sort="value">
                                    <img src="../../images/file_logo_png/1140192.png"/>
                                    <div class="sharefilename">中国地图</div>
                                    <div class="susdiv"></div>
                                </div>
                                <div class="filtr-item" data-category="2, 4" data-sort="value">
                                    <img src="../../images/file_logo_png/1140192.png"/>
                                    <div class="sharefilename">中国地图</div>
                                    <div class="susdiv"></div>
                                </div>
                                <div class="filtr-item" data-category="1, 3" data-sort="value">
                                    <img src="../../images/file_logo_png/1140192.png"/>
                                    <div class="sharefilename">中国地图</div>
                                    <div class="susdiv"></div>
                                </div>
                                <div class="filtr-item" data-category="1, 2" data-sort="value">
                                    <img src="../../images/file_logo_png/1140192.png"/>
                                    <div class="sharefilename">中国地图</div>
                                    <div class="susdiv"></div>
                                </div>
                                <div class="filtr-item" data-category="1, 3" data-sort="value">
                                    <img src="../../images/file_logo_png/1140192.png"/>
                                    <div class="sharefilename">中国地图</div>
                                    <div class="susdiv"></div>
                                </div>
                                <div class="filtr-item" data-category="2" data-sort="value">
                                    <img src="../../images/file_logo_png/1140224.png"/>
                                    <div class="sharefilename">男儿当自强</div>
                                    <div class="susdiv"></div>
                                </div>
                                <div class="filtr-item" data-category="4" data-sort="value">
                                    <img src="../../images/file_logo_png/1140224.png"/>
                                    <div class="sharefilename">男儿当自强</div>
                                    <div class="susdiv"></div>
                                </div>
                                <div class="filtr-item" data-category="3" data-sort="value">
                                    <img src="../../images/file_logo_png/1140224.png"/>
                                    <div class="sharefilename">男儿当自强</div>
                                    <div class="susdiv"></div>
                                </div>
                                <div class="filtr-item" data-category="2, 4" data-sort="value">
                                    <img src="../../images/file_logo_png/1140224.png"/>
                                    <div class="sharefilename">男儿当自强</div>
                                    <div class="susdiv"></div>
                                </div>
                                <div class="filtr-item" data-category="2" data-sort="value">
                                    <img src="../../images/file_logo_png/1140224.png"/>
                                    <div class="sharefilename">男儿当自强</div>
                                    <div class="susdiv"></div>
                                </div>
                                <div class="filtr-item" data-category="2, 3" data-sort="value">
                                    <img src="../../images/file_logo_png/1140232.png"/>
                                    <div class="sharefilename">Orcal安装程序</div>
                                    <div class="susdiv"></div>
                                </div>
                                <div class="filtr-item" data-category="2, 4" data-sort="value">
                                    <img src="../../images/file_logo_png/1140232.png"/>
                                    <div class="sharefilename">Orcal安装程序</div>
                                    <div class="susdiv"></div>
                                </div>
                                <div class="filtr-item" data-category="2" data-sort="value">
                                    <img src="../../images/file_logo_png/1140232.png"/>
                                    <div class="sharefilename">Orcal安装程序</div>
                                    <div class="susdiv"></div>
                                </div>
                                <div class="filtr-item" data-category="2, 3" data-sort="value">
                                    <img src="../../images/file_logo_png/1140232.png"/>
                                    <div class="sharefilename">Orcal安装程序</div>
                                    <div class="susdiv"></div>
                                </div>
                                <div class="filtr-item" data-category="2" data-sort="value">
                                    <img src="../../images/file_logo_png/1140224.png"/>
                                    <div class="sharefilename">男儿当自强</div>
                                    <div class="susdiv"></div>
                                </div>
                                <div class="filtr-item" data-category="2" data-sort="value">
                                    <img src="../../images/file_logo_png/1140224.png"/>
                                    <div class="sharefilename">男儿当自强</div>
                                    <div class="susdiv"></div>
                                </div>
                            </div>
                            <div class="paginationParent"><div id="Pagination"></div></div>
                        </div>
                    </div>
                </div>
<%--            </div>--%>
            <!--Site Content-->
        </div>
    </div>
</div>
<!--jquery-->

</body>
</html>
