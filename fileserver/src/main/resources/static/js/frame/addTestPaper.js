var pageNumber = 10; // 每页显示多少条记录
var pageIndex = 0;//页码
var subId = 0;
var choicetype = 0;
var likeName = "";
var sort1 = "";
var sortName = "";
var abilityId=0;  //知识点
var  difficultyLevel=0; //难度
//分页元素
var total = 50; // 总共多少记录

//界面存在试题集合
choiceids=[];

$(function () {


    //判断
    function refaddbtn() {
        choiceids.splice(0);
        var choices = $(".choice");
        choices.each(function (i, choice) {
            choiceids.push($(choice).attr("choiceid"));
        });
        var choices1 = $(".choice1");
        choices1.each(function (i, choice) {
            if ($.inArray($(choice).attr("choiceid"), choiceids) >= 0) {
                $(choice).children(".choice-title").children(".addthis").hide();
            } else {
                $(choice).children(".choice-title").children(".addthis").show();
            }
        });
        if (choiceids.length < 1. && $("#testPaper").attr("testPaperId") == 0) {
            $("#subIdScreen").prop("disabled", false);
            $('#subIdScreen').selectpicker('refresh')
        } else if (choiceids.length > 0){
            $("#subIdScreen").prop("disabled", true);
        $('#subIdScreen').selectpicker('refresh');
    }

    }

    document.body.ondrop = function (event) {
        event.preventDefault();
        event.stopPropagation();
    };
//////////////////////////////////////////////////////////////////////悬浮窗口js
    //var oBtn = $('#show');
    var popWindow = $('.popWindow');

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

    function openWindw(x, y) {
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
            // positionLeft = browserWidth/2 - popWindowWidth/2+browserScrollLeft;
            // positionTop = browserHeight/2 - popWindowHeight/2+browserScrollTop;
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

///////////////////////////////////////////////拖拽排序
    var container = document.getElementById("choices");
    var sort = Sortable.create(container, {
        animation: 150, // ms，动画速度移动项目排序时，' 0 ' -没有动画
        handle: ".choice", // 限制排序开始单击/触摸指定的元素
        draggable: ".choice", // 指定元素中的哪些项应该排序
        onUpdate: function (evt/**Event*/) {
            var item = evt.item; // 当前拖动的HTMLElement
            // alert($(item).attr('choiceid'));
            sortRef();
        }
    });

//序号更新
    function sortRef() {
        var choices = $(".choice");
        $("#navigation").empty();
        choices.each(function (i, choice) {
            $(choice).attr("c_index", i);
            $(choice).children(".choice-title").children(".choice-index").html(i + 1);
            $("#navigation").append(' <lable class="anchor">'+(parseInt(i)+1)+'</lable>');
            //alert(i);
            anchorBind();
        });
    }
//快速锚点定位
    function anchorBind() {
        $(".anchor").bind("click", function () {
            var choices = $(".choice");
            var num = $(this).text();
            if (parseInt(num) > choices.length) return;
            $("html,body").animate({
                scrollTop: choices.eq(parseInt(num) - 1).offset().top - 40
            }, 200 /*scroll实现定位滚动*/); /*让整个页面可以滚动*/
        });
    }

    var testPaperid = $("#testPaper").attr("testPaperId");
    if (testPaperid!=0 && testPaperid!=null){
    init();
    }else{
        getsubtype();
    }




    $(".pack-up").bind("click", function () {
        var bi = $(this).parent().parent().children(".choice-details").css("display") == "none" ? "none" : "block";
        $(".choice-details").css("display", "none");
        $(this).parent().parent().children(".choice-details").css("display", bi == "none" ? "block" : "none");
        $(".pack-up").children("i").removeClass("icon-jiantoushang");
        $(".pack-up").children("i").addClass("icon-jiantouxia");
        if ($(this).parent().parent().children(".choice-details").css("display") == "block") {
            $(this).children("i").removeClass("icon-jiantouxia");
            $(this).children("i").addClass("icon-jiantoushang");
        }
    });

    $("#typeScreen").change(function () {
        choicetype = $("#typeScreen").val();
        binds(0);
    });

    $("#abilityId").change(function () {
        abilityId = $("#abilityId").val();
        binds(0);
    });

    $("#typeScreen2").change(function () {
        difficultyLevel = $("#typeScreen2").val();
        binds(0);
    });


    $(".querybtn").click(function () {
        likeName = $("#query").val();
        binds(0);
    });


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
            binds(0);
        }
    });

    function PageCallback(index, jq) { // 前一个参数表示当前点击的那个分页的页数索引值，后一个参数表示装载容器。
        pageIndex = index;
        binds(pageIndex);
    }

    //获取知识点

    function  Knowledge() {
        subId = $("#subIdScreen").val();
        $.ajax({
            url: "/knowledgePoint/selectBySubId",
            type: "post",
            data: {id:subId},
            dataType: "json",
            success: function (data) {
                $("#abilityId").empty();
                $("#abilityId").append('<option value="0">全部知识点</option>');
                for (let i = 0; i < data.length; i++) {
                    $("#abilityId").append('<option value="' + data[i].id + '">' + data[i].name + '</option>');
                }
                $('#abilityId').selectpicker('refresh');
                $('#abilityId').val(0).trigger("change");

            },
            error:function (data) {
                console.log("服务器异常");
            }

        })

    }


    function information(data) {
        if (data == null) {
            return false;
        } else {
            let difficul = "";

            for (let i = 0; i < data.choices.length; i++) {
                if (data.choices[i].choice.type == 1 || data.choices[i].choice.type == 2) {//单选题和多选
                    let opent = "";
                    for (let o = 0; o < data.choices[i].choice.optionNum; o++) {
                        let op = '';
                        switch (o) {
                            case 0:
                                op = "A." + data.choices[i].choice.optionA;
                                break;
                            case 1:
                                op = "B." + data.choices[i].choice.optionB;
                                break;
                            case 2:
                                op = "C." + data.choices[i].choice.optionB;
                                break;
                            case 3:
                                op = "D." + data.choices[i].choice.optionD;
                                break;
                            case 4:
                                op = "E." + data.choices[i].choice.optionE;
                                break;
                            default:
                                op = "F." + data.choices[i].choice.optionF;
                                break;
                        }
                        if (data.choices[i].choice.type == 1)
                            opent += '<div class="input-cho"><input class="single" type="radio" name="single1" />' + op + '</div>\n';
                        else
                            opent += '<div class="input-cho"><input class="single" type="checkbox" name="single1" />' + op + '</div>\n';
                    }

                    $("#choices").append(' <div class="choice" choiceid="' + data.choices[i].choice.id + '"  bindId="' + data.choices[i].testPaperBindChoice.id + '" c_index="' + data.choices[i].testPaperBindChoice.indexNum + '">\n' +
                        '            <div class="choice-title"><div class="choice-index">' + (data.choices[i].testPaperBindChoice.indexNum + 1) + '</div><div class="choice-name" >你所熟练的语言？</div>\n' +
                        '                <div class="choice-oper delete-choice"><i class="my-icon lsm-sidebar-icon icon-shanchu "></i></div>\n' +
                        '                <div class="choice-oper delete-choice"><input type="checkbox" name="TestPaper"/></div>\n' +
                        '            </div>\n' +
                        '            <div class="choice-details1">\n' +
                        '                <div class="make-choice">\n' +
                        opent +
                        '                </div>\n' +
                        '            </div>\n' +
                        '        </div>');
                } else if (data.choices[i].choice.type == 3) {//判断题
                    let correct = ""
                    if (data.choices[i].choice.correct == 1) correct = "正确"; else correct = "错误";
                    $("#choices").append('<div class="choice"  choiceid="' + data.choices[i].choice.id + '" bindId="' + data.choices[i].testPaperBindChoice.id + '" c_index="' + data.choices[i].testPaperBindChoice.indexNum + '">\n' +
                        ' <div class="choice-title"><div class="choice-index">' + (data.choices[i].testPaperBindChoice.indexNum + 1) + '</div><div class="choice-name" >' + data.choices[i].choice.topic + '</div>\n' +
                        ' <div class="choice-oper delete-choice"><i class="my-icon lsm-sidebar-icon icon-shanchu " ></i></div>\n' +
                        ' <div class="choice-oper "><input type="checkbox" name="TestPaper"/></div>\n' +
                        '\t\t\t</div>\n' +
                        '\t\t\t<div class="choice-details1">\n' +
                        '\t\t\t\t<div class="make-choice">\n' +
                        '\t\t\t\t\t<div class="input-cho"><input class="single" type="radio" name="single3" />正确</div>\n' +
                        '\t\t\t\t\t<div class="input-cho"><input class="single" type="radio" name="single3" />错误</div>\n' +
                        '\t\t\t\t</div>\n' +

                        '\t\t\t</div>\n' +
                        '\t\t</div>');
                } else if (data.choices[i].choice.type == 4) {//简答题
                    $("#choices").append('<div class="choice"  choiceid="' + data.choices[i].choice.id +  '" bindId="' + data.choices[i].testPaperBindChoice.id + '" c_index="' + data.choices[i].testPaperBindChoice.indexNum + '">\n' +
                        ' <div class="choice-title"><div class="choice-index">' + (data.choices[i].testPaperBindChoice.indexNum + 1) + '</div><div class="choice-name" >' + data.choices[i].choice.topic + '</div>\n' +
                        ' <div class="choice-oper delete-choice"><i class="my-icon lsm-sidebar-icon icon-shanchu " ></i></div>\n' +
                        ' <div class="choice-oper delete-choice"><input type="checkbox" name="TestPaper"/></i></div>\n' +
                        '\t\t\t</div>\n' +
                        '\t\t\t<div class="choice-details1">\n' +
                        '\t\t\t\t<div class="make-choice">\n' +
                        '\t\t\t\t\t<div class="text-cho"><textarea rows="3" cols="50" wrap="hard"  placeholder="请输入参考答案"></textarea></div>\n' +
                        '\t\t\t\t</div>\n' +

                        '\t\t\t</div>\n' +
                        '\t\t</div>');
                }
            }
            deleteChoiceBind();
            sortRef();
        }

    }

//获取学科
    function getsubtype() {
        $.ajax({
            url: "/subject/findAll",
            type: "post",
            async: false,
            data: {},
            dataType: "json",

            success: function (data) {
                $("#subIdScreen").empty();
                $("#subIdScreen").append('<option value="0">全部学科</option>');
                for (let i = 0; i < data.length; i++) {
                    $("#subIdScreen").append('<option value="' + data[i].id + '">' + data[i].name + '</option>');
                }
                $('#subIdScreen').selectpicker('refresh');
                $('#subIdScreen').val(0).trigger("change");
            },
            error: function (data) {

            }
        });
    }


    function init() {
        $.ajax({
            url: "/testPaper/read",
            type: "post",
            data: {testPaperId: $("#testPaper").attr("testPaperId")},
            dataType: "json",
            success: function (data) {
                console.log(data);
                if (data.testPaper != null) {
                    getsubtype();
                    $("#subIdScreen").val(data.testPaper.subId).trigger("change");
                    $("#subIdScreen").prop("disabled", true);
                    $('#subIdScreen').selectpicker('refresh');
                    $("#testPaperName").val(data.testPaper.name);
                }
                information(data);
            },
            error: function (data) {
                console.log("服务器异常");
            }
        })
    }


    /**
     * 添加试题弹出框
     */

    function binds(index) {
        pageIndex = index;
        subId = $("#subIdScreen").val();

        let ScreenChoice = {
            "pageNumber": pageNumber,
            "pageIndex": pageIndex,
            "subId": subId,
            "type": choicetype,
            "name": likeName,
            "sort": sort1,
            "sortName": sortName,
            "abilityIds":abilityId,
            "difficultyLevel":difficultyLevel
        }

        $.ajax({
            url: "/choice/findAll",
            contentType: "application/json;charset=UTF-8",
            type: "post",
            async: false,
            data: JSON.stringify(ScreenChoice),
            dataType: "json",
            success: function (data) {
                console.log(data);

                $(".allChoice").empty();
                let difficul = "";
                for (let i = 0; i < data.page.content.length; i++) {
                    difficul = "";
                    for (let k = 0; k < 5; k++) {
                        if (k < data.page.content[i].difficultyLevel)
                            difficul += '<label style="color: #85822b;font-size: 14px;">★</label>';
                        else
                            difficul += '<label style="color: #9d9ea3;font-size: 14px;">★</label>';
                    }
                    if (data.page.content[i].type == 1 || data.page.content[i].type == 2) {//单选题和多选
                        let type = "";
                        let opent = "";
                        if (data.page.content[i].type == 1) type = "单选题"; else type = "多选题"
                        for (let o = 0; o < data.page.content[i].optionNum; o++) {
                            let op = '';
                            switch (o) {
                                case 0:
                                    op = "A." + data.page.content[i].optionA;
                                    break;
                                case 1:
                                    op = "B." + data.page.content[i].optionB;
                                    break;
                                case 2:
                                    op = "C." + data.page.content[i].optionB;
                                    break;
                                case 3:
                                    op = "D." + data.page.content[i].optionD;
                                    break;
                                case 4:
                                    op = "E." + data.page.content[i].optionE;
                                    break;
                                default:
                                    op = "F." + data.page.content[i].optionF;
                                    break;
                            }
                            if (data.page.content[i].type == 1)
                                opent += '<div class="input-cho"><input class="single" type="radio" name="single1" />' + op + '</div>\n';
                            else
                                opent += '<div class="input-cho"><input class="single" type="checkbox" name="single1" />' + op + '</div>\n';
                        }

                        $(".allChoice").append('<div class="choice1" choiceid="' + data.page.content[i].id + '">\n' +
                            '<div class="choice-title"><div class="choice-type" data="' + data.page.content[i].type + '">' + type + '</div><div class="choice-name" >' + data.page.content[i].topic + '</div>\n' +
                            '<div class="choice-oper pack-up"><i class="my-icon lsm-sidebar-icon  icon-jiantouxia"></i></div>\n' +
                            '<div class="choice-oper addthis"><i class="my-icon lsm-sidebar-icon  icon-tianjia"></i></div>\n' +
                            '</div>\n' +
                            '<div class="choice-details">\n' +
                            '<div class="make-choice">' + opent + '</div>\n' +
                            '<div class="answer">标答：' + data.page.content[i].correct + '</div>\n' +
                            '<div class="difficulty">难度：' + difficul + '</div>\n' +
                            '<div class="analysis">解析：' + data.page.content[i].analysis + '</div>\n' +
                            '<div class="knowledge">知识点：</div>\n' +
                            '</div>\n' +
                            '</div>');
                    } else if (data.page.content[i].type == 3) {//判断题
                        let correct = ""
                        if (data.page.content[i].correct == 1) correct = "正确"; else correct = "错误";
                        $(".allChoice").append('<div class="choice1"  choiceid="' + data.page.content[i].id + '">\n' +
                            '\t\t\t<div class="choice-title"><div class="choice-type" data="' + data.page.content[i].type + '">判断题</div><div class="choice-name" >' + data.page.content[i].topic + '</div>\n' +
                            '\t\t\t\t<div class="choice-oper pack-up"><i class="my-icon lsm-sidebar-icon  icon-jiantouxia"></i></div>\n' +
                            '<div class="choice-oper addthis"><i class="my-icon lsm-sidebar-icon  icon-tianjia"></i></div>\n' +
                            '\t\t\t</div>\n' +
                            '\t\t\t<div class="choice-details">\n' +
                            '\t\t\t\t<div class="make-choice">\n' +
                            '\t\t\t\t\t<div class="input-cho"><input class="single" type="radio" name="single3" />正确</div>\n' +
                            '\t\t\t\t\t<div class="input-cho"><input class="single" type="radio" name="single3" />错误</div>\n' +
                            '\t\t\t\t</div>\n' +
                            '\t\t\t\t<div class="answer">标答：' + correct + '</div>\n' +
                            '\t\t\t\t<div class="difficulty">难度：' + difficul + '</div>\n' +
                            '\t\t\t\t<div class="analysis">解析：' + data.page.content[i].analysis + '</div>\n' +
                            '\t\t\t\t<div class="knowledge">知识点：</div>\n' +
                            '\t\t\t</div>\n' +
                            '\t\t</div>');
                    } else if (data.page.content[i].type == 4) {//简答题
                        $(".allChoice").append('<div class="choice1"  choiceid="' + data.page.content[i].id + '">\n' +
                            '\t\t\t<div class="choice-title"><div class="choice-type" data="' + data.page.content[i].type + '">简答题</div><div class="choice-name" >' + data.page.content[i].topic + '</div>\n' +
                            '\t\t\t\t<div class="choice-oper pack-up"><i class="my-icon lsm-sidebar-icon  icon-jiantouxia"></i></div>\n' +
                            '<div class="choice-oper addthis"><i class="my-icon lsm-sidebar-icon  icon-tianjia"></i></div>\n' +
                            '\t\t\t</div>\n' +
                            '\t\t\t<div class="choice-details">\n' +
                            '\t\t\t\t<div class="make-choice">\n' +
                            '\t\t\t\t\t<div class="text-cho"><textarea rows="3" cols="50" wrap="hard"  placeholder="请输入参考答案"></textarea></div>\n' +
                            '\t\t\t\t</div>\n' +
                            '\t\t\t\t<div class="answer">标答：' + data.page.content[i].correct + '</div>\n' +
                            '\t\t\t\t<div class="difficulty">难度：' + difficul + '</div>\n' +
                            '\t\t\t\t<div class="analysis">解析：' + data.page.content[i].analysis + '</div>\n' +
                            '\t\t\t\t<div class="knowledge">知识点：</div>\n' +
                            '\t\t\t</div>\n' +
                            '\t\t</div>');
                    }
                }
                choicebind();
                refaddbtn();
                total = data.page.totalElements;
                $(".totalmsg").html("【共" + total + "条记录，当前显示：" + (data.page.pageable.pageNumber * data.page.pageable.pageSize + 1) + "~" + (data.page.pageable.pageNumber * data.page.pageable.pageSize + data.page.numberOfElements) + "】");

            },
            error: function (data) {
                console.log("服务器异常");
            }
        });

    }

    $("#choices").empty();
    $("#addchoice").click(function (e) {
        if($("#subIdScreen option:selected").val()==0) {
            $.alert("请选择科目");
            return;
        }
        openWindw(e.pageX, e.pageY);
        Knowledge();
        binds(0);
    });

    function choicebind() {
        $(".addthis").bind("click", function (e) {
            let choicename=$(this).parent().children(".choice-name").html();
            let choiceid=$(this).parent().parent().attr("choiceid");
            let choicetype=$(this).parent().children(".choice-type").attr("data");
            var htmlss=$(this).parent().parent().children(".choice-details").children(".make-choice").html();

            $("#choices").append('<div class="choice"  choiceid="' + choiceid + '"  c_index=""  bindId="">\n' +
                ' <div class="choice-title"><div class="choice-index"></div><div class="choice-name" >' + choicename+ '</div>\n' +
                ' <div class="choice-oper delete-choice"><i class="my-icon lsm-sidebar-icon icon-shanchu "></i></div>\n' +
                '\t\t\t</div>\n' +
                '\t\t\t<div class="choice-details1">\n' +
                '\t\t\t\t<div class="make-choice">\n' +
                '\t\t\t\t\t<div class="text-cho">'+htmlss+'</div>\n' +
                '\t\t\t\t</div>\n' +

                '\t\t\t</div>\n' +
                '\t\t</div>');
            deleteChoiceBind();
            sortRef();
            refaddbtn();

        });
        $(".input-cho").bind("click", function () {
            $(this).children('input')[0].click();
        });
        $(".pack-up").bind("click", function () {
            var bi = $(this).parent().parent().children(".choice-details").css("display") == "none" ? "none" : "block";
            $(".choice-details").css("display", "none");
            $(this).parent().parent().children(".choice-details").css("display", bi == "none" ? "block" : "none");
            $(".pack-up").children("i").removeClass("icon-jiantoushang");
            $(".pack-up").children("i").addClass("icon-jiantouxia");
            if ($(this).parent().parent().children(".choice-details").css("display") == "block") {
                $(this).children("i").removeClass("icon-jiantouxia");
                $(this).children("i").addClass("icon-jiantoushang");
            }
        });
    }

    function deleteChoiceBind(){
        $(".delete-choice").bind("click",function () {
            let choice=$(this).parent().parent();
            $.confirm({
                confirmButtonClass: 'btn-info',
                cancelButtonClass: 'btn-danger',
                title: '提示',
                content: '确定要删除该试题吗？',
                confirm: function(){
                    if(choice.attr("bindid")==""){
                        console.log("我进来了");
                       $(choice).remove();
                        sortRef();
                        refaddbtn();
                    }else{
                        $.ajax({
                            url: "/TestPaperBindChoice/delete",
                            type: "post",
                            data: {id: $(choice).attr("bindid")},
                            dataType: "json",
                            success: function (data) {
                                $(choice).remove();
                                sortRef();
                                refaddbtn();
                            },
                            error: function (data) {
                                console.log("服务器异常");
                            }
                        })
                    }

                },
                cancel: function(){

                }
            });




        })
    }







});


//添加试卷
function save() {
    let name = $("#testPaperName").val();
    let subId = $("#subIdScreen option:selected").val();
    let testPaperid = $("#testPaper").attr("testPaperId");
    if(name==null || name==""){
        $.alert("试卷名称不能为空");
        return false;
    }

    let TestPaper = {
        "name": name,
        "subId": subId,
        "id":testPaperid
    }

    console.log(name);
    $.ajax({
        url: "/testPaper/save",
        contentType: "application/json;charset=UTF-8",
        type: "post",
        data: JSON.stringify(TestPaper),
        dataType: "json",
        success: function (data) {
            console.log(data);
            if(data!=undefined && data !=null){
                $("#testPaper").attr("testPaperId",data.id);
                saveTestPaperBindChoice();
            }
        },
        error: function (data) {
            console.log("服务器异常");
        }
    })
}


//试卷绑定试题
function saveTestPaperBindChoice() {
    var choices = $(".choice");
    let testPaperList=[];
    choices.each(function (i, choice) {
        let TestPaperBindChoice={
            "id":$(choice).attr("bindId"),
            "indexNum":$(choice).attr("c_index"),
            "choiceId":$(choice).attr("choiceid"),
            "testPaperId":$("#testPaper").attr("testPaperId"),
            "score":3.0
        }
        testPaperList.push(TestPaperBindChoice);
    });

    $.ajax({
        url: "/TestPaperBindChoice/save",
        contentType: "application/json;charset=UTF-8",
        type: "post",
        data: JSON.stringify(testPaperList),
        dataType: "json",
        success: function (data) {
            console.log(data);
        },
        error: function (data) {
            console.log("服务器异常");
        }
    })
}


