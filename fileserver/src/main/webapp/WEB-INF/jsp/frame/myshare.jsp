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
                            <h3 class="ui header">我的分享</h3>
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
