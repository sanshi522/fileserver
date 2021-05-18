// JavaScript Document
var showtype = 0;
$(function () {
    var logintype;
    //根据登录身份初始化页面元素--------------------------------------
    $.ajax({
        url: "login/Islogin",
        type: "post",
        dataType: "json",
        success: function (data) {
            if (data.resoult) {
                //根据登陆账户的类型初始化导航栏
                if (data.logintype == 0) {//学生
                    logintype = data.logintype;
                    $("#queryLevels").append(' <button class="ui button queryLevel select" data-filter="0">所有文件</button>\n' +
                        '<button class="ui button queryLevel" data-filter="1">学届共享</button>\n' +
                        '<button class="ui button queryLevel" data-filter="2">院系共享</button>\n' +
                        '<button class="ui button queryLevel" data-filter="3">班级共享</button>\n' +
                        '<button class="ui button queryLevel" data-filter="4">小组共享</button>\n' +
                        '<button class="ui button queryLevel" data-filter="5">学生共享</button>\n' +
                        '<button class="ui button queryLevel" data-filter="6">老师共享</button>');
                } else if (data.user.teaIdentity == 1) {//老师
                    logintype = data.user.teaIdentity;
                    $("#queryLevels").append(' <button class="ui button queryLevel select" data-filter="0">所有文件</button>\n' +
                        '<button class="ui button queryLevel" data-filter="1">学届共享</button>\n' +
                        '<button class="ui button queryLevel" data-filter="2">院系共享</button>\n' +
                        '<button class="ui button queryLevel" data-filter="3">班级共享</button>\n' +
                        '<button class="ui button queryLevel" data-filter="4">小组共享</button>\n' +
                        '<button class="ui button queryLevel" data-filter="5">学生共享</button>\n' +
                        '<button class="ui button queryLevel" data-filter="6">老师共享</button>\n' +
                        '<button class="ui button queryLevel" data-filter="7">管理员共享</button>')
                } else if (data.user.teaIdentity == 2 || data.user.teaIdentity == 3) {//管理员老师 及 管理员
                    logintype = data.user.teaIdentity;
                    $("#queryLevels").append('<button class="ui button queryLevel select" data-filter="0">所有文件</button>\n' +
                        '<button class="ui button queryLevel" data-filter="1">学届共享</button>\n' +
                        '<button class="ui button queryLevel" data-filter="2">院系共享</button>\n' +
                        '<button class="ui button queryLevel" data-filter="3">班级共享</button>\n' +
                        '<button class="ui button queryLevel" data-filter="4">小组共享</button>\n' +
                        '<button class="ui button queryLevel" data-filter="5">学生共享</button>\n' +
                        '<button class="ui button queryLevel" data-filter="6">老师共享</button>\n' +
                        '<button class="ui button queryLevel" data-filter="7">管理员共享</button>')
                }
                $(".queryLevel").bind('click', function () {
                    screenLevel = 0; //筛选级别-所有
                    $(".queryLevel").removeClass("select");
                    $(this).addClass("select");
                    //切换则查询
                    if (queryLevel != $(this).attr("data-filter")) {
                        queryLevel = $(this).attr("data-filter");
                        //重置名字匹配框
                        $(".query").val("");
                        likeName = "";
                        Init(0);
                    }
                    //根据权限级别显示二级筛选框
                    if (logintype == 0 || queryLevel == 0) {//（登陆方式为学生/查询级别所有）
                        $("#screendiv").css("display", "none");
                        // $("#screendiv").html("");
                    } else {//老师/管理员：按顺序做出选择 通过身份获取
                        $("#screendiv").css("display", "block");
                        $("#yearScreenDiv").css("display", "block");
                        if (queryLevel == 7)
                            $("#yearScreenDiv").css("display", "none");
                        $("#gradeScreenDiv").css("display", "none");
                        $("#classScreenDiv").css("display", "none");
                        $("#groupScreenDiv").css("display", "none");
                        $("#teacherScreenDiv").css("display", "none");
                        $("#studentScreenDiv").css("display", "none");
                        $("#adminScreenDiv").css("display", "none");
                        if (queryLevel == 7) {
                            $("#adminScreenDiv").css("display", "block");
                        }
                        if (queryLevel != 7) {
                            //获取该身份下的学年
                            $.ajax({
                                url: "Grade/GetYear",
                                type: "post",
                                dataType: "json",
                                success: function (data) {
                                    if (data.resoult) {
                                        $("#yearScreen").children("option").remove();
                                        $("#yearScreen").append('<option value="0">全部学年</option>');
                                        for (let i = 0; i < data.years.length; i++) {
                                            $("#yearScreen").append('<option value="' + data.years[i] + '">' + data.years[i] + '</option>');
                                        }
                                        //$("#yearScreen").selectpicker('val','全部学年');
                                        $('#yearScreen').selectpicker('refresh');
                                        $('#yearScreen').val('0').trigger("change");
                                    }
                                },
                                error: function (data) {
                                    console.log("获取学年服务器错误")
                                }
                            });
                        } else {
                            //获取所有管理员
                            $.ajax({
                                url: "Teacher/GetAdmin",
                                type: "post",
                                dataType: "json",
                                success: function (data) {
                                    if (data.resoult) {
                                        $("#teacherScreen").children("option").remove();
                                        $("#teacherScreen").append('<option value="0">全部老师</option>');
                                        for (let i = 0; i < data.teachers.length; i++) {
                                            $("#teacherScreen").append('<option value="' + data.teachers[i].teaId + '">' + data.teachers[i].teaName + '</option>');
                                        }
                                        // $("#gradeScreen").selectpicker('val','0');
                                        $('#teacherScreen').selectpicker('refresh');
                                        $('#teacherScreen').val('0').trigger("change");
                                    }
                                },
                                error: function (data) {
                                    console.log("获取老师服务器错误")
                                }
                            });
                        }
                    }
                })
            } else {
                parent.location.href = "/";
            }
        },
        error: function (jqXHR) {
            alert("发生错误：" + jqXHR.status);
        }
    })
    //根据搜索条件展示筛选框
    $("#yearScreen").change(function () {
        issistId = $("#yearScreen").val();
        if (issistId == "0") {//筛选条件全部
            screenLevel = 0; //筛选级别-所有
            $("#gradeScreenDiv").css("display", "none")
            $("#classScreenDiv").css("display", "none");
            $("#groupScreenDiv").css("display", "none");
            $("#teacherScreenDiv").css("display", "none");
            $("#studentScreenDiv").css("display", "none");
        } else {//筛选条件为院校
            screenLevel = 1; //筛选级别-学年
            if (queryLevel != 1) {
                $("#gradeScreenDiv").css("display", "block");
                //查询学年下的学院
                $.ajax({
                    url: "Grade/GetGrade",
                    type: "post",
                    data: {yearNumber: issistId},
                    dataType: "json",
                    success: function (data) {
                        if (data.resoult) {
                            $("#gradeScreen").children("option").remove();
                            $("#gradeScreen").append('<option value="0">全部院系</option>');
                            for (let i = 0; i < data.grades.length; i++) {
                                $("#gradeScreen").append('<option value="' + data.grades[i].id + '">' + data.grades[i].name + '</option>');
                            }
                            // $("#gradeScreen").selectpicker('val','0');
                            $('#gradeScreen').selectpicker('refresh');
                            $('#gradeScreen').val('0').trigger("change");
                        }
                    },
                    error: function (data) {
                        console.log("获取院系服务器错误")
                    }
                });
            }
        }
    })
    $("#gradeScreen").change(function () {
        issistId = $("#gradeScreen").val();
        if (issistId == "0") {
            //筛选级别-学年
            screenLevel = 1;
            issistId = $("#yearScreen").val();
            $("#classScreenDiv").css("display", "none")
            $("#groupScreenDiv").css("display", "none");
            $("#teacherScreenDiv").css("display", "none");
            $("#studentScreenDiv").css("display", "none");
        } else {
            //筛选级别-院系
            screenLevel = 2;
            if (queryLevel != 2) {
                $("#classScreenDiv").css("display", "block");
                //查询学院下的班级
                $.ajax({
                    url: "Class/GetClass",
                    type: "post",
                    data: {GradeId: issistId},
                    dataType: "json",
                    success: function (data) {
                        if (data.resoult) {
                            $("#classScreen").children("option").remove();
                            $("#classScreen").append('<option value="0">全部班级</option>');
                            for (let i = 0; i < data.clases.length; i++) {
                                $("#classScreen").append('<option value="' + data.clases[i].id + '">' + data.clases[i].name + '</option>');
                            }
                            // $("#gradeScreen").selectpicker('val','0');
                            $('#classScreen').selectpicker('refresh');
                            $('#classScreen').val('0').trigger("change");
                        }
                    },
                    error: function (data) {
                        console.log("获取班级服务器错误")
                    }
                });
            }
        }
    })
    $("#classScreen").change(function () {
        issistId = $("#classScreen").val();
        if (issistId == "0") {//筛选条件全部
            //筛选级别-院系
            screenLevel = 2;
            issistId = $("#gradeScreen").val();
            $("#groupScreenDiv").css("display", "none")
            $("#teacherScreenDiv").css("display", "none");
            $("#studentScreenDiv").css("display", "none");
        } else {//筛选条件为
            //筛选级别-班级
            screenLevel = 3;
            if (queryLevel != 3 && queryLevel != 6) {
                $("#groupScreenDiv").css("display", "block");
                //查询班级下的小组
                $.ajax({
                    url: "Group/GetGroup",
                    type: "post",
                    data: {CclassId: issistId},
                    dataType: "json",
                    success: function (data) {
                        if (data.resoult) {
                            $("#groupScreen").children("option").remove();
                            $("#groupScreen").append('<option value="0">全部小组</option>');
                            for (let i = 0; i < data.Groups.length; i++) {
                                $("#groupScreen").append('<option value="' + data.Groups[i].id + '">' + data.Groups[i].name + '</option>');
                            }
                            // $("#gradeScreen").selectpicker('val','0');
                            $('#groupScreen').selectpicker('refresh');
                            $('#groupScreen').val('0').trigger("change");
                        }
                    },
                    error: function (data) {
                        console.log("获取小组服务器错误")
                    }
                });
            }
            if (queryLevel == 6) {
                $("#teacherScreenDiv").css("display", "block");
                //查询班级下的老师
                $.ajax({
                    url: "Teacher/GetTeacher",
                    type: "post",
                    data: {classId: issistId},
                    dataType: "json",
                    success: function (data) {
                        if (data.resoult) {
                            $("#teacherScreen").children("option").remove();
                            $("#teacherScreen").append('<option value="0">全部老师</option>');
                            for (let i = 0; i < data.teachers.length; i++) {
                                $("#teacherScreen").append('<option value="' + data.teachers[i].teaId + '">' + data.teachers[i].teaName + '</option>');
                            }
                            // $("#gradeScreen").selectpicker('val','0');
                            $('#teacherScreen').selectpicker('refresh');
                            $('#teacherScreen').val('0').trigger("change");
                        }
                    },
                    error: function (data) {
                        console.log("获取老师服务器错误")
                    }
                });
            }
        }
    })
    $("#groupScreen").change(function () {
        issistId = $("#groupScreen").val();
        if (issistId == "0") {
            //筛选级别-班级
            screenLevel = 3;
            issistId = $("#classScreen").val();
            $("#studentScreenDiv").css("display", "none")
        } else {
            //筛选级别-小组
            screenLevel = 4;
            if (queryLevel != 4) {
                $("#studentScreenDiv").css("display", "block");
                //查询小组下的学生
                $.ajax({
                    url: "Student/GetStudent",
                    type: "post",
                    data: {StuGroup: issistId},
                    dataType: "json",
                    success: function (data) {
                        if (data.resoult) {
                            $("#studentScreen").children("option").remove();
                            $("#studentScreen").append('<option value="0">全部组员</option>');
                            for (let i = 0; i < data.students.length; i++) {
                                $("#studentScreen").append('<option value="' + data.students[i].stuId + '">' + data.students[i].stuName + '</option>');
                            }
                            // $("#gradeScreen").selectpicker('val','0');
                            $('#studentScreen').selectpicker('refresh');
                            $('#studentScreen').val('0').trigger("change");
                        }
                    },
                    error: function (data) {
                        console.log("获取学生服务器错误")
                    }
                });
            }
        }
    })
    $("#studentScreen").change(function () {
        issistId = $("#studentScreen").val();
        if (issistId == "0") {
            //筛选级别-小组
            screenLevel = 4;
            issistId = $("#groupScreen").val();
        } else {
            //筛选级别-学生
            screenLevel = 5;
        }
    })
    $("#teacherScreen").change(function () {
        issistId = $("#studentScreen").val();
        if (issistId == "0") {
            //筛选级别-班级
            screenLevel = 3;
            issistId = $("#classScreen").val();
        } else {
            //筛选级别-老师
            screenLevel = 6;
        }
    })
    $("#admintScreen").change(function () {
        issistId = $("#admintScreen").val();
        if (issistId == "0") {
            //筛选级别-所有管理员
            screenLevel = 0;
        } else {
            //筛选级别-管理员
            screenLevel = 1;

        }
    });
    //初始化单页显示条数
    $("#showNumber").val('10').trigger("change")
    $("#showNumber").change(function () {
        if (pageNumber != $("#showNumber").val()) {
            pageNumber = $("#showNumber").val();
            $("#Pagination").pagination(total, {
                callback: PageCallback,
                prev_text: '上一页',
                next_text: '下一页',
                items_per_page: pageNumber,
                num_display_entries: 4, // 连续分页主体部分显示的分页条目数
                num_edge_entries: 1, // 两侧显示的首尾分页的条目数
                jump: true,
            });
            Init(0);
        }
    });
    //请求条件
    var pageNumber = 10; // 每页显示多少条记录
    var pageIndex = 0;//页码
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
    var sort = "";
    var sortName = "";
    //分页元素
    var total = 0; // 总共多少记录
    let nu=0;
    //分页请求
    Init(0);

    function refresh() {
        nu=1;
        pageNumber = $("#showNumber").val();
        $("#Pagination").pagination(total, {
            callback: PageCallback,
            prev_text: '上一页',
            next_text: '下一页',
            items_per_page: pageNumber,
            num_display_entries: 4, // 连续分页主体部分显示的分页条目数
            num_edge_entries: 1, // 两侧显示的首尾分页的条目数
            jump: true,
        });
    }

    function PageCallback(index, jq) { // 前一个参数表示当前点击的那个分页的页数索引值，后一个参数表示装载容器。
        pageIndex = index;
        Init(pageIndex);
    }

    function Init(Index) { // 参数就是点击的那个分页的页数索引值
        let ScreenShareFile = {
            "pageNumber": pageNumber,
            "pageIndex": pageIndex,
            "queryLevel": queryLevel,
            "screenLevel": screenLevel,
            "issistId": issistId,
            "likeName": likeName,
            "sort": sort,
            "sortName": sortName
        }
        let me = this.showtype;
        var win = window;
        $.ajax({
            url: "/file/getAllMyShareFile",
            contentType: "application/json;charset=UTF-8",
            type: "post",
            async: false,
            data: JSON.stringify(ScreenShareFile),
            dataType: "json",
            success: function (data) {
                $(".filtr-container").empty();
                for (let i = 0; i < data.page.content.length; i++) {
                    if (me == 0) {
                        $(".filtr-container").append('<div class="filtr-item chunk" fileid=' + data.page.content[i].id + '>\n' +
                            '<img src="../../images/file_logo_png/1140224.png"/>\n' +
                            '<div class="sharefilename">' + data.page.content[i].name + '</div>\n' +
                            '<div class="sharefilesize">' + getKbMbGb(data.page.content[i].size) + '</div>\n' +
                            '<div class="susdiv">\n' +
                            '<div class="operation download"><i class="my-icon lsm-sidebar-icon icon-xiazai querybtn"></i></div>\n' +
                            '<div class="operation compile"><i class="my-icon lsm-sidebar-icon icon-bianji querybtn"></i></div>\n' +
                            '<div class="operation delete"><i class="my-icon lsm-sidebar-icon icon-shanchu querybtn"></i></div>\n' +
                            '</div>\n' +
                            '</div>');
                    } else {
                        $(".filtr-container").append('<div class="filtr-item line" fileid=' + data.page.content[i].id + '>\n' +
                            '<img src="../../images/file_logo_png/1140224.png"/>\n' +
                            '<div class="sharefilename">' + data.page.content[i].name + '</div>\n' +
                            '<div class="sharefilesize">' + getKbMbGb(data.page.content[i].size) + '</div>\n' +
                            '<div class="susdiv">\n' +
                            '<div class="operation download"><i class="my-icon lsm-sidebar-icon icon-xiazai querybtn"></i></div>\n' +
                            '<div class="operation compile"><i class="my-icon lsm-sidebar-icon icon-bianji querybtn"></i></div>\n' +
                            '<div class="operation delete"><i class="my-icon lsm-sidebar-icon icon-shanchu querybtn"></i></div>\n' +
                            '</div>\n' +
                            '</div>');
                    }
                }
                $(".download").bind("click", function () {
                    win.location.href = "/file/downloadShareFile?fileId=" + $(this).parent().parent().attr("fileid");
                });
                total = data.page.totalElements;
                $(".totalmsg").html("【共" + total + "条记录，当前显示：" + (data.page.pageable.pageNumber * data.page.pageable.pageSize + 1) + "~" + (data.page.pageable.pageNumber * data.page.pageable.pageSize + data.page.numberOfElements) + "】");
                //$(".filtr-container").html(JSON.stringify(data));
                if(nu==0){
                    refresh();
                }
            },
            error: function (data) {
                console.log("服务器异常");
            }
        });
    }

  //搜索
    $(".querybtn").click(function () {
        likeName = $("#query").val();
        nu=0;
       Init(0);
    });

    function getKbMbGb(limit) {
        var size = "";
        if (limit < 0.1 * 1024) {                            //小于0.1KB，则转化成B
            size = limit.toFixed(2) + "B"
        } else if (limit < 0.1 * 1024 * 1024) {            //小于0.1MB，则转化成KB
            size = (limit / 1024).toFixed(2) + "KB"
        } else if (limit < 0.1 * 1024 * 1024 * 1024) {        //小于0.1GB，则转化成MB
            size = (limit / (1024 * 1024)).toFixed(2) + "MB"
        } else {                                            //其他转化成GB
            size = (limit / (1024 * 1024 * 1024)).toFixed(2) + "GB"
        }

        var sizeStr = size + "";                        //转成字符串
        var index = sizeStr.indexOf(".");                    //获取小数点处的索引
        var dou = sizeStr.substr(index + 1, 2)            //获取小数点后两位的值
        if (dou == "00") {                                //判断后两位是否为00，如果是则删除00
            return sizeStr.substring(0, index) + sizeStr.substr(index + 3, 2)
        }
        return size;
    }
});


