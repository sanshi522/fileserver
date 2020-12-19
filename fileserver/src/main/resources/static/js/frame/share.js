// JavaScript Document
$(function() {
    var logintype;
    //根据登录身份初始化页面元素--------------------------------------
    $.ajax({
        url:"login/Islogin",
        type:"post",
        dataType:"json",
        success:function(data) {
            if (data.resoult){
                //根据登陆账户的类型初始化导航栏
                if (data.logintype == 0) {//学生
                    logintype=data.logintype;
                    $("#queryLevels").append(' <button class="ui button queryLevel select" data-filter="0">所有文件</button>\n' +
                        '                                <button class="ui button queryLevel" data-filter="1">学届共享</button>\n' +
                        '                                <button class="ui button queryLevel" data-filter="2">院系共享</button>\n' +
                        '                                <button class="ui button queryLevel" data-filter="3">班级共享</button>\n' +
                        '                                <button class="ui button queryLevel" data-filter="4">小组共享</button>\n' +
                        '                                <button class="ui button queryLevel" data-filter="5">学生共享</button>');
                } else if (data.user.teaIdentity == 1) {//老师
                    logintype=data.user.teaIdentity;
                    $("#queryLevels").append(' <button class="ui button queryLevel select" data-filter="0">所有文件</button>\n' +
                        '                                <button class="ui button queryLevel" data-filter="1">学届共享</button>\n' +
                        '                                <button class="ui button queryLevel" data-filter="2">院系共享</button>\n' +
                        '                                <button class="ui button queryLevel" data-filter="3">班级共享</button>\n' +
                        '                                <button class="ui button queryLevel" data-filter="4">小组共享</button>\n' +
                        '                                <button class="ui button queryLevel" data-filter="5">学生共享</button>\n' +
                        '                                <button class="ui button queryLevel" data-filter="6">老师共享</button>')
                } else if (data.user.teaIdentity == 2 || data.user.teaIdentity == 3) {//管理员老师 及 管理员
                    logintype=data.user.teaIdentity;
                    $("#queryLevels").append('<button class="ui button queryLevel select" data-filter="0">所有文件</button>\n' +
                        '                                <button class="ui button queryLevel" data-filter="1">学届共享</button>\n' +
                        '                                <button class="ui button queryLevel" data-filter="2">院系共享</button>\n' +
                        '                                <button class="ui button queryLevel" data-filter="3">班级共享</button>\n' +
                        '                                <button class="ui button queryLevel" data-filter="4">小组共享</button>\n' +
                        '                                <button class="ui button queryLevel" data-filter="5">学生共享</button>\n' +
                        '                                <button class="ui button queryLevel" data-filter="6">老师共享</button>\n' +
                        '                                <button class="ui button queryLevel" data-filter="7">管理员共享</button>')
                }
                $(".queryLevel").bind('click',function () {
                    $(".queryLevel").removeClass("select");
                    $(this).addClass("select");
                    //切换则查询
                    if(queryLevel!=$(this).attr("data-filter")){
                        queryLevel=$(this).attr("data-filter");
                        //重置名字匹配框
                        $(".query").val("");
                        likeName="";
                        Init(0);
                    }
                    //根据权限级别显示二级筛选框
                    if(logintype==0 || queryLevel==0){//（登陆方式为学生/查询级别所有）
                        $("#screendiv").css("display","none");
                       // $("#screendiv").html("");
                    }else {//老师/管理员：按顺序做出选择 通过身份获取
                        $("#screendiv").css("display","block");
                        $("#yearScreenDiv").css("display","block");
                        if (queryLevel==7)
                            $("#yearScreenDiv").css("display","none");
                        $("#gradeScreenDiv").css("display","none");
                        $("#classScreenDiv").css("display","none");
                        $("#groupScreenDiv").css("display","none");
                        $("#teacherScreenDiv").css("display","none");
                        $("#studentScreenDiv").css("display","none");
                        $("#adminScreenDiv").css("display","none");
                        if (queryLevel==7)
                            $("#adminScreenDiv").css("display","block");
                        if (queryLevel!=7){
                            //获取该身份下的学年
                        }else{
                            //获取所有管理员
                        }
                    }
                })
            }else {
                parent.location.href = "/";
            }
        },
        error:function(jqXHR){
            alert("发生错误："+ jqXHR.status);
        }
    })
    //根据搜索条件展示筛选框
    $("#yearScreen").change(function () {
        issistId=$("#yearScreen").val();
        if (issistId=="0"){//筛选条件全部
            screenLevel=0; //筛选级别-所有
            $("#gradeScreenDiv").css("display","none")
        }else {//筛选条件为院校
            screenLevel = 1; //筛选级别-学年
            if (queryLevel!=1){
                $("#gradeScreenDiv").css("display","block");
                //查询学年下的学院

            }
        }
    })
    $("#gradeScreen").change(function () {
        issistId=$("#gradeScreen").val();
        if (issistId=="0"){
            //筛选级别-学年
            screenLevel=1;
            issistId=$("#yearScreen").val();
            $("#classScreenDiv").css("display","none")
        }else {
            //筛选级别-院系
            screenLevel = 2;
            if (queryLevel!=2){
                $("#classScreenDiv").css("display","block");
                //查询学院下的班级

            }
        }
    })
    $("#classScreen").change(function () {
        issistId=$("#classScreen").val();
        if (issistId=="0"){//筛选条件全部
            //筛选级别-院系
            screenLevel=2;
            issistId=$("#gradeScreen").val();
            $("#groupScreenDiv").css("display","none")
            $("#teacherScreenDiv").css("display","none")
        }else {//筛选条件为
            //筛选级别-班级
            screenLevel = 3;
            if (queryLevel!=3 && queryLevel!=6){
                $("#groupScreenDiv").css("display","block");
                //查询班级下的小组

            }
            if (queryLevel==6){
                $("#teacherScreenDiv").css("display","block");
                //查询班级下的老师
            }
        }
    })
    $("#groupScreen").change(function () {
        issistId=$("#groupScreen").val();
        if (issistId=="0"){
            //筛选级别-班级
            screenLevel=3;
            issistId=$("#classScreen").val();
            $("#studentScreenDiv").css("display","none")
        }else {
            //筛选级别-小组
            screenLevel = 4;
            if (queryLevel!=4){
                $("#studentScreenDiv").css("display","block");
                //查询学院下的班级

            }
        }
    })
    $("#studentScreen").change(function () {
        issistId=$("#studentScreen").val();
        if (issistId=="0"){
            //筛选级别-小组
            screenLevel=4;
            issistId=$("#groupScreen").val();
        }else {
            //筛选级别-学生
            screenLevel = 5;

        }
    })
    $("#studentScreen").change(function () {
        issistId=$("#studentScreen").val();
        if (issistId=="0"){
            //筛选级别-班级
            screenLevel=3;
            issistId=$("#classScreen").val();
        }else {
            //筛选级别-老师
            screenLevel = 6;

        }
    })
    $("#admintScreen").change(function () {
        issistId=$("#admintScreen").val();
        if (issistId=="0"){
            //筛选级别-所有管理员
            screenLevel=0;
        }else {
            //筛选级别-管理员
            screenLevel = 1;

        }
    })
    //请求条件
    var pageNumber = 10; // 每页显示多少条记录
    var pageIndex= 0 ;//页码
    /**
     * //查询级别(0所有；1学年；2院系；3班级；4小组；5学生；6老师；7管理员)
     * @type {number}
     */
    var queryLevel = 0;
    /**
     * //筛选级别(0所有；1学年；2院系；3班级；4小组；5学生；6老师；7管理员)
     * @type {number}
     */
    var screenLevel = 0;
    /**
     * 筛选id
     * @type {number}
     */
    var issistId = 0;
    var likeName = "";
    var sort="";
    var sortName="";
    //分页元素
    var total=0; // 总共多少记录
    //分页请求
    Init(0);

    $("#Pagination").pagination(total, {
        callback : PageCallback,
        prev_text : '上一页',
        next_text : '下一页',
        items_per_page : 6,
        items_per_page : pageNumber,
        num_display_entries : 4, // 连续分页主体部分显示的分页条目数
        num_edge_entries : 1, // 两侧显示的首尾分页的条目数
        jump:true,
    });
    function PageCallback(index, jq) { // 前一个参数表示当前点击的那个分页的页数索引值，后一个参数表示装载容器。
        pageIndex=index;
        Init(pageIndex);
    }
    function Init(Index) { // 参数就是点击的那个分页的页数索引值
        let ScreenShareFile={
            "pageNumber":pageNumber,
            "pageIndex":pageIndex,
            "queryLevel":queryLevel,
            "screenLevel":screenLevel,
            "issistId":issistId,
            "likeName":likeName,
            "sort":sort,
            "sortName":sortName
        }
        $.ajax({
            url: "/file/getAllShareFile",
            contentType:"application/json;charset=UTF-8",
            type: "post",
            async: false,
            data:JSON.stringify(ScreenShareFile),
            dataType: "json",
            success: function(data) {
                total=1000;
                $(".filtr-container").html(JSON.stringify(data));
               // $(".filtr-container").append()
            },
            error: function(data){
                console.log("服务器异常");
            }
            });
    }
});