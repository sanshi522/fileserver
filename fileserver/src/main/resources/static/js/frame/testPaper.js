// JavaScript Document

$(function () {
    //获取学科
    function getsubtype() {
        $.ajax({
            url: "/subject/findAll",
            type: "post",
            data: {},
            dataType: "json",
            success: function (data) {
                $("#subjectId").empty();
                $("#subIdScreen").empty();
                $("#subIdScreen2").empty();

                $("#subIdScreen").append('<option value="0">全部学科</option>');
                for (let i = 0; i < data.length; i++) {
                    $("#subjectId").append('<option value="' + data[i].id + '">' + data[i].name + '</option>');
                    $("#subIdScreen").append('<option value="' + data[i].id + '">' + data[i].name + '</option>');
                    $("#subIdScreen2").append('<option value="' + data[i].id + '">' + data[i].name + '</option>');
                }
                $('#subjectId').selectpicker('refresh');
                $('#subIdScreen').selectpicker('refresh');
                $('#subjectId').val(data[0].id).trigger("change");
                $('#subIdScreen').val(0).trigger("change");
                $('#subIdScreen2').val(data[0].id).trigger("change");
                $('#subIdScreen2').val(0).trigger("change");
            },
            error: function (data) {

            }
        });
    }


    var pageNumber = 10; // 每页显示多少条记录
    var pageIndex = 0;//页码
    var subId = 0;
    var likeName = "";
    var total = 50; // 总共多少记录
    let nu=0;
    getsubtype();
    //分页元素

    //初始化单页显示条数

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

    function PageCallback(index, jq) { // 前一个参数表示当前点击的那个分页的页数索引值，后一个参数表示装载容器。
        pageIndex = index;
        Init(pageIndex);
    }

    function Init(index) { // 参数就是点击的那个分页的页数索引值
        pageIndex = index;
        let val = {
            "pageNumber": pageNumber,
            "pageIndex": pageIndex,
            "issistId": subId,
            "likeName": likeName == "" ? null : likeName
        }
        var win = window;
        $.ajax({
            url: "/testPaper/findAll",
            //contentType:"application/json;charset=UTF-8",
            type: "post",
            async: true,
            data: val,
            dataType: "json",
            success: function (data) {
                if (data.resoult) {
                    $(".allTestPaper").empty();
                    for (let i = 0; i < data.page.content.length; i++) {
                        $.ajax({
                            url: "/testPaper/findMsg",
                            //contentType:"application/json;charset=UTF-8",
                            type: "post",
                            async: false,
                            data: {id: data.page.content[i].id},
                            dataType: "json",
                            success: function (data2) {
                                $(".allTestPaper").append('<div class="testPaper" style="">\n' +
                                    '        <table width="100%">\n' +
                                    '            <tr >\n' +
                                    '                <th colspan="2" style="text-align:center;">' + data.page.content[i].name + '</th>\n' +
                                    '            </tr>\n' +
                                    '            <tr>\n' +
                                    '                <td>学科：</td>\n' +
                                    '                <td>' + data2.subName + '</td>\n' +
                                    '            </tr>\n' +
                                    '            <tr>\n' +
                                    '                <td>试卷总分：</td>\n' +
                                    '                <td>' + data2.choiceScoreNum + '</td>\n' +
                                    '            </tr>\n' +
                                    '\n' +
                                    '            <tr>\n' +
                                    '                <td>题目数量：</td>\n' +
                                    '                <td>' + data2.choiceNum + '</td>\n' +
                                    '            </tr>\n' +
                                    '            <tr>\n' +
                                    '                <td>创建时间：</td>\n' +
                                    '                <td>' + data.page.content[i].createTime + '</td>\n' +
                                    '            </tr>\n' +
                                    '            <tr>\n' +
                                    '                <td>创建人：</td>\n' +
                                    '                <td>' + data2.creatUserName + '</td>\n' +
                                    '            </tr>\n' +
                                    '            <tr>\n' +
                                    '                <td>编辑操作：</td>\n' +
                                    '                <td style="font-size: 20px;">\n' +
                                    '                    <label style="margin-right: 10px;cursor: pointer;"><i onclick="javascript:parent.open(\'addTestPaper?id=' + data.page.content[i].id + '\')" class="my-icon lsm-sidebar-icon icon-bianji del"></i></label>\n' +
                                    '                    <label><i class="my-icon lsm-sidebar-icon icon-shanchu"  data_id=' + data.page.content[i].id + '  style="cursor: pointer;"></i></label>\n' +
                                    '                </td>\n' +
                                    '            </tr>\n' +
                                    '\n' +
                                    '\n' +
                                    '        </table>\n' +
                                    '    </div>')
                            }, error(data2) {
                            }
                        });

                    }
                    delTestPaper();

                    total = data.page.totalElements;
                    $(".totalmsg").html("【共" + total + "条记录，当前显示：" + (data.page.pageable.pageNumber * data.page.pageable.pageSize + 1) + "~" + (data.page.pageable.pageNumber * data.page.pageable.pageSize + data.page.numberOfElements) + "】");
                    if(nu==0){
                        refresh()
                    }
                }

            }, error: function (data) {
                console.log("服务器异常");
            }
        });
    }




    $("#subIdScreen").change(function () {
        subId = $("#subIdScreen").val();
        nu=0;
        Init(0);
    });

    $(".querybtn").click(function () {
        likeName = $("#query").val();
        nu=0;
        Init(0);
    });
    //删除试卷
    function delTestPaper() {
        $(".icon-shanchu").bind("click", function () {
            var testPaperId = $(this).attr("data_id");
            $.confirm({
                confirmButtonClass: 'btn-info',
                cancelButtonClass: 'btn-danger',
                title: '提示',
                content: '确定要删除该试卷吗？',
                confirm: function () {
                    $.ajax({
                        url: "testPaper/delete",
                        type: "post",
                        data: {"testPaperId": testPaperId},
                        async: false,
                        dataType: "json",
                        success: function (data) {
                            $.alert(data.data);
                            Init(pageIndex);
                        },
                        error: function (data) {

                        }

                    });
                },
                cancel: function () {

                }
            });
        });

    }


//////////////////////////////////////////////////////////////////////悬浮窗口js
    var popWindow = $('.popWindow');
    var oClose = $('.popWindow h3 span');
    //浏览器可视区域的宽度
    var browserWidth = $(window).width();
    //浏览器可视区域的高度
    var browserHeight = $(window).height();
    //浏览器纵向滚动条距离上边界的值
    var browserScrollTop = $(window).scrollTop();
    //浏览器横向滚动条距离左边界的值
    var browserScrollLeft = $(window).scrollLeft();
    //弹出窗口的宽度
    var popWindowWidth = popWindow.outerWidth(true);
    //弹出窗口的高度
    var popWindowHeight = popWindow.outerHeight(true);
    //left的值＝浏览器可视区域的宽度／2－弹出窗口的宽度／2+浏览器横向滚动条距离左边界的值
    var positionLeft = browserWidth / 2 - popWindowWidth / 2 + browserScrollLeft;
    //top的值＝浏览器可视区域的高度／2－弹出窗口的高度／2+浏览器纵向滚动条距离上边界的值
    var positionTop = browserHeight / 2 - popWindowHeight / 2 + browserScrollTop;
    var oMask = '<div class="mask"></div>'
    //遮照层的宽度
    var maskWidth = $(document).width();
    //遮照层的高度
    var maskHeight = $(document).height();

    //刷新
    function ref() {
        maskWidth = $(document).width();
        maskHeight = $(document).height();
        $(popWindow).css("max-width", maskWidth / 2 + 'px');
        $(popWindow).css("max-height", maskHeight + 'px');
        $(".wContent").css("max-height", maskHeight / 3 * 2 + 'px');
        $(".addcontext").css("height", $(".wContent").height());
//	popWindowWidth = popWindow.outerWidth(true);
//	popWindowHeight = popWindow.outerHeight(true);
        popWindowWidth = $(popWindow).width();
        popWindowHeight = $(popWindow).height();
        browserWidth = $(window).width();
        browserHeight = $(window).height();
        positionLeft = browserWidth / 2 - popWindowWidth / 2 + browserScrollLeft;
        positionTop = browserHeight / 2 - popWindowHeight / 2 + browserScrollTop;


    }

    /** 弹出框**/
    function openWindw(x, y) {
        $(".popWindow").css("width", "320px");
        $("#addUser_div").hide();
        $("#screenTest_div").hide();
        ref();
        $(popWindow).css("left", x - popWindowWidth + 'px');
        $(popWindow).css("top", y + 'px');
        popWindow.show().animate({
            'left': positionLeft + 'px',
            'top': positionTop + 'px',
        }, 500);
        $('body').append(oMask);
        $('.mask').width(maskWidth).height(maskHeight);
        popWindow.removeClass("hide");
    };
    $(window).resize(function () {
        ref();
        $('.mask').width(maskWidth).height(maskHeight);
        if (popWindow.is(':visible')) {
            popWindow.animate({
                'left': positionLeft + 'px',
                'top': positionTop + 'px',
            }, 100);
        }
    });
    $(window).scroll(function () {
        if (popWindow.is(':visible')) {
            browserScrollTop = $(window).scrollTop();
            browserScrollLeft = $(window).scrollLeft();
            //positionLeft = browserWidth/2 - popWindowWidth/2+browserScrollLeft;
            //positionTop = browserHeight/2 - popWindowHeight/2+browserScrollTop;
            positionLeft = browserWidth / 2 - popWindowWidth / 2;
            positionTop = browserHeight / 2 - popWindowHeight / 2;
            popWindow.animate({
                'left': positionLeft + 'px',
                'top': positionTop + 'px'
            }, 100).dequeue();
        }
    });
    $("#cancel").click(function () {
        popWindow.hide();
        $('.mask').remove();
    });
    oClose.click(function () {
        popWindow.hide();
        $('.mask').remove();
    });
    //切换题目类型
    $("#choicetype").change(function () {
        if ($("#choicetype").val() == 1) {
            $(".choice_option_tr").show();
            $("#choicenum").change();
            $(".choice_option").attr("type", "radio");
        } else if ($("#choicetype").val() == 2) {
            $(".choice_option_tr").show();
            $("#choicenum").change();
            $(".choice_option").attr("type", "checkbox");
        } else if ($("#choicetype").val() == 3) {
            $(".choice_option_tr").hide();
        } else {
            $(".choice_option_tr").hide();
        }
    });
    $("#choicetype").change();
//设置选项个数
    $(".optionE").hide();
    $(".optionF").hide();
    $("#choicenum").change(function () {
        if ($("#choicenum").val() == 4) {
            $(".choice_option_tr").show();
            $(".optionE").hide();
            $(".optionF").hide();
            checkedno();
        } else if ($("#choicenum").val() == 5) {
            $(".choice_option_tr").show();
            $(".optionF").hide();
            checkedno();
        } else {
            $(".choice_option_tr").show();
        }
    });
//点击选项
    $(".choice_option").click(function () {
        var obj = document.getElementsByName('opt');
        var s = '';
        for (var i = 0; i < obj.length; i++) {
            if (obj[i].checked) {
                if (s == '')
                    s += obj[i].value;
                else
                    s += ',' + obj[i].value;
            }

        }
        $("#anwser").val(s);
    });

    //全部不选中状态
    function checkedno() {
        var obj = document.getElementsByName('opt');
        for (var i = 0; i < obj.length; i++) {
            obj[i].checked = false;
            $("#anwser").val('');
        }
    }

    //点击知识点
    $("#knowledge").click(function () {
        $(".addtext").show();
        $(".popWindow").css("min-width", '710px');
        $(".addtitel").html("添加知识点");
        $(".addcontext").empty();
        $(window).resize();
    });
    //点击附件
    $("#accessory").click(function () {
        $(".addtext").show();
        $(".popWindow").css("min-width", '710px');
        $(".addtitel").html("添加附件");
        $(".addcontext").empty();
        $(window).resize();
    });

    //获取某一试题信息
    function getchoice() {

    }

    var cassname = "";

    //获取知识点
    function getknowleged() {
        $.ajax({
            url: "http://localhost/subject/findAll",
            type: "post",
            data: {name: cassname},
            dataType: "json",
            success: function (data) {
                alert(JSON.stringify(data));
            },
            error: function (data) {

            }
        });
    }

    $("#addAssess").click(function (e) {
        openWindw(e.pageX, e.pageY);
    });

    //获取附件
    function getaccessory() {
        $.ajax({
            url: "http://localhost/subject/findAll",
            type: "post",
            data: {name: cassname},
            dataType: "json",
            success: function (data) {
                alert(JSON.stringify(data));
            },
            error: function (data) {

            }
        });
    }

    $("#save_ok").bind("click", function () {

        if ($("#TestPaperName").val() == "" || $("#TestPaperName").val() == null) {
            $.alert("试卷名称不能为空");
        }

        if ($("#TestPaperName").val() == "" || $("#TestPaperName").val() == null) {
            $.alert("试卷名称不能为空");
        }
        var re = /^[0-9]+.?[0-9]*/;
        if (!re.test($("#rodSum").val())) {
            $.alert("单选题数量只能为数字");
            return;
        }
        if (!re.test($("#checkSum").val())) {
            $.alert("多选题数量只能为数字");
            return;
        }
        if (!re.test($("#judgeSum").val())) {
            $.alert("判断题只能为数字");
            return;
        }
        if (!re.test($("#answerSum").val())) {
            $.alert("简答题只能为数字");
            return;
        }

        let TestPaperUtils = {
            "testPaperName": $("#TestPaperName").val(),
            "subId": $("#subIdScreen2").val(),
            "difficulty": $("#difficultyLevel").val(),
            "rodSum": $("#rodSum").val(),
            "checkSum": $("#checkSum").val(),
            "judgeSum": $("#judgeSum").val(),
            "answerSum": $("#answerSum").val(),
            "rodScore": $("#rodScore").val(),
            "checkScore": $("#checkScore").val(),
            "judgeScore": $("#judgeScore").val(),
            "answerScore": $("#answerScore").val(),
        }
        $.ajax({
            url: "testPaper/generateTestPaper",
            contentType: "application/json;charset=UTF-8",
            type: "post",
            data: JSON.stringify(TestPaperUtils),
            dataType: "json",
            success: function (data) {
                $.alert(data.data);
            },
            error: function (dta) {
                $.alert("服务器异常");
            }
        })


    })

});


//删除试卷
// function delTestPaper(testPaperId) {
//
//         $.confirm({
//             confirmButtonClass: 'btn-info',
//             cancelButtonClass: 'btn-danger',
//             title: '提示',
//             content: '确定要删除该试卷吗？',
//             confirm: function () {
//                 $.ajax({
//                     url: "testPaper/delete",
//                     type: "post",
//                     data: {"testPaperId": testPaperId},
//                     async: false,
//                     dataType: "json",
//                     success: function (data) {
//                         $.alert(data.data);
//                     },
//                     error: function (data) {
//
//                     }
//
//                 });
//             },
//             cancel: function () {
//
//             }
//         });
//
//
// }