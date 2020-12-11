<%--
  Created by IntelliJ IDEA.
  User: 致颜文磊
  Date: 2020/9/25
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>WaiS平台</title>
    <link rel="stylesheet" type="text/css" href="/css/left-side-menu.css">
    <link rel="stylesheet" type="text/css" href="/css/index.css">
    <link rel="stylesheet" type="text/css" href="/fonts/iconfont.css">
    <link rel="stylesheet" type="text/css" href="/fonts/iconfont1.css">
    <script type="text/javascript" src="/js/jquery/jquery-3.5.1.min.js"></script>
    <script type="text/javascript" src="/js/jquery/jquery.slimscroll.min.js"></script>
    <script type="text/javascript" src="/js/left-side-menu.js"></script>
    <script type="text/javascript" src="/js/index.js"></script>
</head>
<body>
<div class ="top"  style="width: 100%;height: 60px;background-color:#303030;">
    <div class="home_logodiv">
    </div>
    <div class="close_div">
    </div>
    <div class="user_div">
        <img id="user_head" src="" style="float: left;">
        <div class="user_name" style="float: left;">
            张三
        </div>
    </div>
    <div class="msg_div">
    </div>

</div>

<div class="left-side-menu" >
    <div class="lsm-expand-btn">
        <div class="lsm-mini-btn"  style="float: left;">
            <label>
                <input type="checkbox" checked="checked">
                <svg viewBox="0 0 100 100" xmlns="http://www.w3.org/2000/svg">
                    <circle cx="50" cy="50" r="30" />
                    <path class="line--1" d="M0 40h62c18 0 18-20-17 5L31 55" />
                    <path class="line--2" d="M0 50h80" />
                    <path class="line--3" d="M0 60h62c18 0 18 20-17-5L31 45" />
                </svg>
            </label>
        </div>
        <div class="lsm-expand-text">
            菜单
        </div>
        <!-- <input type="text">-->
    </div>
    <div class="lsm-container">
        <div class="lsm-scroll" >
            <div class="lsm-sidebar">
                <ul>
                      <li class="lsm-sidebar-item stu-show tea-show man-show">
                          <a class="active" href="javascript:;"><i class="my-icon lsm-sidebar-icon icon-shouye"></i><span>首页</span>
                              <i class="my-icon lsm-sidebar-more"></i>
                          </a>
                      </li>
                      <li class="lsm-sidebar-item tea-show">
                          <a href="javascript:;"><i class="my-icon lsm-sidebar-icon icon-banjiguanli"></i><span>班级管理</span><i class="my-icon lsm-sidebar-more"></i></a>
                          <ul>
                              <li><a href="javascript:;"><span>我的班级</span></a></li>
                              <li><a href="javascript:;"><i class="my-icon lsm-sidebar-icon icon-xiaozuguanli"></i><span>小组管理</span></a></li>
                              <li class="lsm-sidebar-item">
                                  <a  class="active" href="javascript:;"><i class="my-icon lsm-sidebar-icon icon-xuesheng"></i><span>学生管理</span><i class="my-icon lsm-sidebar-more"></i></a>
                                  <ul>
                                      <li><a href="javascript:;"><span>查找学生</span></a></li>
                                      <li><a href="javascript:;"><span>修改学生信息</span></a></li>
                                      <li><a href="javascript:;"><span>批量导入</span></a></li>
                                      <li><a href="javascript:;"><span>批量导出</span></a></li>
                                  </ul>
                              </li>
                          </ul>
                      </li>
                      <li class="lsm-sidebar-item stu-show tea-show">
                          <a href="javascript:;"><i class="my-icon lsm-sidebar-icon icon-shishiboxingjiankongtu"></i><span>样本库</span><i class="my-icon lsm-sidebar-more"></i></a>
                          <ul>
                              <li><a href="javascript:;"><span>地爆天星1</span></a></li>
                              <li><a href="javascript:;"><span>神罗天征1</span></a></li>
                              <li><a href="javascript:;"><span>八门遁甲1</span></a></li>
                          </ul>
                      </li>
                      <li class="lsm-sidebar-item tea-show">
                          <a href="javascript:;"><i class="my-icon lsm-sidebar-icon icon-ceshi"></i><span>训练管理</span><i class="my-icon lsm-sidebar-more"></i></a>
                          <ul>
                              <li><a href="javascript:;"><span>火之国2</span></a></li>
                              <li><a href="javascript:;"><span>沙之国3</span></a></li>
                              <li><a href="javascript:;"><span>火影忍者3</span></a></li>
                          </ul>
                      </li>
                    <li class="lsm-sidebar-item stu-show">
                        <a href="javascript:;"><i class="my-icon lsm-sidebar-icon icon-ceshi"></i><span>训练中心</span><i class="my-icon lsm-sidebar-more"></i></a>
                        <ul>
                            <li><a href="javascript:;"><span>火之国2</span></a></li>
                            <li><a href="javascript:;"><span>沙之国3</span></a></li>
                            <li><a href="javascript:;"><span>火影忍者3</span></a></li>
                        </ul>
                    </li>
                      <li class="lsm-sidebar-item tea-show">
                          <a href="javascript:;"><i class="my-icon lsm-sidebar-icon icon-kaoshifenxi"></i><span>考核管理</span><i class="my-icon lsm-sidebar-more"></i></a>
                          <ul>
                              <li><a href="javascript:;"><span>火之国4</span></a></li>
                              <li><a href="javascript:;"><span>沙之国4</span></a></li>
                              <li><a href="javascript:;"><span>火影忍者4</span></a></li>
                          </ul>
                      </li>
                    <li class="lsm-sidebar-item stu-show">
                        <a href="javascript:;"><i class="my-icon lsm-sidebar-icon icon-kaoshifenxi"></i><span>考核中心</span><i class="my-icon lsm-sidebar-more"></i></a>
                        <ul>
                            <li><a href="javascript:;"><span>火之国4</span></a></li>
                            <li><a href="javascript:;"><span>沙之国4</span></a></li>
                            <li><a href="javascript:;"><span>火影忍者4</span></a></li>
                        </ul>
                    </li>
                      <li class="lsm-sidebar-item stu-show tea-show">
                          <a href="javascript:;"><i class="my-icon lsm-sidebar-icon icon-gongxiangtubiaozhuangtaileicaozuolei23"></i><span>共享站</span><i class="my-icon lsm-sidebar-more"></i></a>
                          <ul>
                              <li><a href="javascript:openshare();"><span>共享中心</span></a></li>
                              <li><a href="javascript:openmyshare();"><span>我的共享</span></a></li>
                              <li><a href="javascript:openaddshare();"><span>添加共享</span></a></li>
                          </ul>
                      </li>
                      <li class="lsm-sidebar-item stu-show tea-show">
                          <a href="javascript:;"><i class="my-icon lsm-sidebar-icon icon-gerenzhongxin"></i><span>个人中心</span><i class="my-icon lsm-sidebar-more"></i></a>
                          <ul>
                              <li><a href="javascript:;"><span>我的资料</span></a></li>
                              <li><a href="javascript:;"><span>编辑资料</span></a></li>
                              <li><a href="javascript:;"><span>消息中心</span></a></li>
                          </ul>
                      </li>
                      <li class="lsm-sidebar-item man-show tea-show">
                          <a href="javascript:;"><i class="my-icon lsm-sidebar-icon icon-shezhi"></i><span>设置</span><i class="my-icon lsm-sidebar-more"></i></a>
                          <ul>
                              <li><a href="javascript:;"><span>火之国</span></a></li>
                              <li><a href="javascript:;"><span>最后一条子菜单</span></a></li>
                          </ul>
                      </li>
                  </ul>
              </div>
          </div>
      </div>
  </div>
<iframe name="viewiframe" class="frame1 min_frame" id="frameId" src="" frameborder="0" background="#fff" style="float:right;height:calc(100% - 60px);position:absolute;reght:0;"></iframe>
<iframe name="uploadiframe" class="frame2 min-upload-frame"  src="/uploadview" frameborder="0" background="#fff" style="padding: 0px;"></iframe>
<div style="background: #A67172; border: 2px #ff0000 solid; width: 400px;height: 500px;position: fixed;z-index: 10;right: 0px;bottom: 0px;display: none;"></div>
  <div class ="center"  style="background-color: #397bc5;width: 100%;"></div>
  </body>
  </html>
