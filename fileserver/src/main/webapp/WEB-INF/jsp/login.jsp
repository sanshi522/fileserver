<%--
  Created by IntelliJ IDEA.
  User: 致颜文磊
  Date: 2020/9/22
  Time: 12:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link rel="stylesheet" href="/css/bootstrap.css">
<link  rel="stylesheet" href="/css/login.css" type="text/css">

<script src="/js/jquery-3.5.1.min.js"></script>
<script src="/js/login/login.js"></script>
<head>
    <title>WaiS文件管理</title>
</head>
<body>
<div class="overlay">
    <div class="row1">
        <div class="title_a">Welcome to</div>
        <div class="title_b">某某某某某某<br>系统</div>
        <div class="title_c">version number：1.0</div>
        <div class="login_div">
            <div class="toggle_div">
                <input type="button" onclick="StuClick()"  class="toggle_btn1"  value="学生">
                <input type="button" onclick="TeaClick()"  class="toggle_btn2" style="background:#C9C2C2" value="老师">
            </div>
            <div class="from1">
                    <div class="col-md-12">
                        <%--@declare id="username"--%><label for="username">学号</label>
                        <input type="text" class="form-control"  onkeypress="onkey(event)" id="user_number" placeholder="请输入学号">
                    </div>
                    <div class="col-md-12">
                        <label for="password">密码</label>
                        <input type="password" class="form-control"  onkeypress="onkey(event)" id="password" placeholder="请输入密码">
                    </div>
                    <div class="col-md-12 login_btn">
                        <input id="login" type="submit" onclick="sub()" class="btn btn-primary" value="登录">
                        <a style="float: right;"   href="/findpass/stu">修改密码</a>
                    </div>
            </div>
            <div class="from2">
                    <div class="col-md-12">
                        <label for="username">姓名</label>
                        <input type="text" class="form-control"  onkeypress="onkey1(event)" id="user_number1" placeholder="请输入姓名">
                    </div>

                    <div class="col-md-12">
                        <label for="password">密码</label>
                        <input type="password" class="form-control"  onkeypress="onkey1(event)" id="password1" placeholder="请输入密码">
                    </div>

                    <div class="col-md-12 login_btn">
                        <input id="login1"  type="submit" onclick="sub1()" class="btn btn-primary" value="登录">
                        <a style="float: right;" href="/findpass/tea">修改密码</a>
                    </div>
            </div>
            <input type="text" disabled=＂disabled＂ class="error_msg" id="error_msg">
        </div>
    </div>
</div>
</body>
</html>
