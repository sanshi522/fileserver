

$(function () {

    var pageNumber = 10; // 每页显示多少条记录
    var pageIndex = 0;//页码
    var subId = 0;
    var choicetype = 0;
    var likeName = "";
    var sort1 = "";
    var sortName = "";
    var abilityId = 0;  //知识点
    var difficultyLevel = 0; //难度
//分页元素
    var total = 50; // 总共多少记录

    let quan = 0;//是否批量操作标识符
//界面存在试题集合
   var choiceids = [];
    //比对现有试题与添加试题弹出框试题
    function refaddbtn() {
        choiceids.splice(0);
        var choices = $(".choice");
        choices.each(function (i, choice) {
            choiceids.push($(choice).attr("choiceid"));
        });
        var choices1 = $(".choice1");
        choices1.each(function (i, choice) {
            if ($.inArray($(choice).attr("choiceid"), choiceids) >= 0)
                $(choice).children(".choice-title").children(".addthis").hide();
            else
                $(choice).children(".choice-title").children(".addthis").show();
        });
        if (choiceids.length < 1. && $("#testPaper").attr("testPaperId") == 0) {
            $("#subIdScreen").prop("disabled", false);
            $('#subIdScreen').selectpicker('refresh')
        } else if (choiceids.length > 0) {
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
    let nu=0;

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
            $("#navigation").append(' <lable class="anchor">' + (parseInt(i) + 1) + '</lable>');
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
    if (testPaperid != 0 && testPaperid != null) {
        init();
    } else {
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
        nu=0;
        binds(0);
    });

    $("#abilityId").change(function () {
        abilityId = $("#abilityId").val();
        nu=0;
        binds(0);
    });

    $("#typeScreen2").change(function () {
        difficultyLevel = $("#typeScreen2").val();
        nu=0;
        binds(0);
    });


    $(".querybtn").click(function () {
        nu=0;
        likeName = $("#query").val();
        binds(0);
    });


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
            binds(0);
        }
    });

    function PageCallback(index, jq) { // 前一个参数表示当前点击的那个分页的页数索引值，后一个参数表示装载容器。
        pageIndex = index;
        binds(pageIndex);
    }

    //获取知识点

    function Knowledge() {
        subId = $("#subIdScreen").val();
        $.ajax({
            url: "/knowledgePoint/selectBySubId",
            type: "post",
            data: {id: subId},
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
            error: function (data) {
                console.log("服务器异常");
            }

        })


    }

//遍历试卷信息及试题
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

                    $("#choices").append(' <div class="choice" choicecheck="0" choiceid="' + data.choices[i].choice.id + '"  bindId="' + data.choices[i].testPaperBindChoice.id + '" c_index="' + data.choices[i].testPaperBindChoice.indexNum + '">\n' +
                        '            <div class="choice-title"><div class="choice-index">' + (data.choices[i].testPaperBindChoice.indexNum + 1) + '</div><div class="choice-name" >' + data.choices[i].choice.topic + '</div>\n' +
                        '<input class="choice-box"  name="checkItem" type="checkbox" />' +
                        '                <div class="choice-oper delete-choice"><i class="my-icon lsm-sidebar-icon icon-shanchu "></i></div>\n' +
                        '<div class="score_div">\n' +
                        '<input class="form-control score1" type="text" placeholder="分值" value="' + data.choices[i].testPaperBindChoice.score + '" >\n' +
                        '</div>' +
                        // '                <div class="choice-oper delete-choice"><input type="checkbox" name="TestPaper"/></div>\n' +
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
                    $("#choices").append('<div class="choice" choicecheck="0"  choiceid="' + data.choices[i].choice.id + '" bindId="' + data.choices[i].testPaperBindChoice.id + '" c_index="' + data.choices[i].testPaperBindChoice.indexNum + '">\n' +
                        ' <div class="choice-title"><div class="choice-index">' + (data.choices[i].testPaperBindChoice.indexNum + 1) + '</div><div class="choice-name" >' + data.choices[i].choice.topic + '</div>\n' +
                        '<input class="choice-box"  name="checkItem" type="checkbox" />' +
                        ' <div class="choice-oper delete-choice"><i class="my-icon lsm-sidebar-icon icon-shanchu " ></i></div>\n' +
                        '<div class="score_div">\n' +
                        '<input class="form-control score1" type="text" placeholder="分值" value="' + data.choices[i].testPaperBindChoice.score + '">\n' +
                        '</div>' +
                        // ' <div class="choice-oper "><input type="checkbox" name="TestPaper"/></div>\n' +
                        '\t\t\t</div>\n' +
                        '\t\t\t<div class="choice-details1">\n' +
                        '\t\t\t\t<div class="make-choice">\n' +
                        '\t\t\t\t\t<div class="input-cho"><input class="single" type="radio" name="single3" />正确</div>\n' +
                        '\t\t\t\t\t<div class="input-cho"><input class="single" type="radio" name="single3" />错误</div>\n' +
                        '\t\t\t\t</div>\n' +

                        '\t\t\t</div>\n' +
                        '\t\t</div>');
                } else if (data.choices[i].choice.type == 4) {//简答题
                    $("#choices").append('<div class="choice" choicecheck="0"  choiceid="' + data.choices[i].choice.id + '" bindId="' + data.choices[i].testPaperBindChoice.id + '" c_index="' + data.choices[i].testPaperBindChoice.indexNum + '">\n' +
                        ' <div class="choice-title"><div class="choice-index">' + (data.choices[i].testPaperBindChoice.indexNum + 1) + '</div><div class="choice-name" >' + data.choices[i].choice.topic + '</div>\n' +
                        '<input class="choice-box"  name="checkItem" type="checkbox" />' +
                        ' <div class="choice-oper delete-choice"><i class="my-icon lsm-sidebar-icon icon-shanchu " ></i></div>\n' +
                        '<div class="score_div">\n' +
                        '<input class="form-control score1" type="text" placeholder="分值" value="' + data.choices[i].testPaperBindChoice.score + '">\n' +
                        '</div>' +
                        // ' <div class="choice-oper delete-choice"><input type="checkbox" name="TestPaper"/></i></div>\n' +
                        '\t\t\t</div>\n' +
                        '\t\t\t<div class="choice-details1">\n' +
                        '\t\t\t\t<div class="make-choice">\n' +
                        '\t\t\t\t\t<div class="text-cho"><textarea rows="3" cols="50" wrap="hard"  placeholder="请输入参考答案"></textarea></div>\n' +
                        '\t\t\t\t</div>\n' +

                        '\t\t\t</div>\n' +
                        '\t\t</div>');
                }   else if(data.choices[i].choice.type == 5){
                    let  str=data.choices[i].choice.topic;
                    let    s=   str.toArray();
                    let  ChioceName="";
                    for (let x=0;x<s.length;x++){
                        if(s[x]=="_"){
                            ChioceName+= '<input style=" border:none; border-bottom: 1px solid #000;width: 50px" type="text"/>';
                        }else{
                            ChioceName+=s[x];
                        }

                    }
                    $("#choices").append('<div class="choice" choicecheck="0"  choiceid="' + data.choices[i].choice.id + '" bindId="' + data.choices[i].testPaperBindChoice.id + '" c_index="' + data.choices[i].testPaperBindChoice.indexNum + '">\n' +
                        ' <div class="choice-title"><div class="choice-index">' + (data.choices[i].testPaperBindChoice.indexNum + 1) + '</div><div class="choice-name" >' +ChioceName + '</div>\n' +
                        '<input class="choice-box"  name="checkItem" type="checkbox" />' +
                        ' <div class="choice-oper delete-choice"><i class="my-icon lsm-sidebar-icon icon-shanchu " ></i></div>\n' +
                        '<div class="score_div">\n' +
                        '<input class="form-control score1" type="text" placeholder="分值" value="' + data.choices[i].testPaperBindChoice.score + '">\n' +
                        '</div>' +
                        // ' <div class="choice-oper delete-choice"><input type="checkbox" name="TestPaper"/></i></div>\n' +
                        '\t\t\t</div>\n' +
                        '\t\t\t<div class="choice-details1">\n' +
                        '\t\t\t\t<div class="make-choice">\n' +
                        '\t\t\t\t</div>\n' +
                        '\t\t\t</div>\n' +
                        '\t\t</div>');

                }


            }
            deleteChoiceBind();
            sortRef();
            score_Num();
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


    String.prototype.toArray = function () {  //把字符串转换为数组
        var p = this.length;
        a = [];  //获取当前字符串长度，并定义空数组
        if (p) {  //如果存在则执行循环操作，预防空字符串
            for (var i = 0; i < p; i++) {  //遍历字符串，枚举每个字符
                a.push(this.charAt(i));  //把每个字符按顺序装入数组
            }
        }
        return a;  //返回数组
    }

    /**
     * 添加试题到弹出框
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
            "abilityIds": abilityId,
            "difficultyLevel": difficultyLevel
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
                            '<div class="knowledge">知识点：'+data.page.content[i].abilityIds+'</div>\n' +
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
                            '\t\t\t\t<div class="knowledge">知识点：'+data.page.content[i].abilityIds+'</div>\n' +
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
                            '\t\t\t\t<div class="knowledge">知识点：'+data.page.content[i].abilityIds+'</div>\n' +
                            '\t\t\t</div>\n' +
                            '\t\t</div>');
                    }
                    else if(data.page.content[i].type == 5){
                        let  str=data.page.content[i].topic;
                        let    s=   str.toArray();
                        let  ChioceName="";
                        for (let x=0;x<s.length;x++){
                            if(s[x]=="_"){
                                ChioceName+= '<input style=" border:none; border-bottom: 1px solid #000;width: 50px" type="text"/>';
                            }else{
                                ChioceName+=s[x];
                            }

                        }
                        $(".allChoice").append('<div class="choice1"  choiceid="' + data.page.content[i].id + '">\n' +
                            '\t\t\t<div class="choice-title"><div class="choice-type" data="' + data.page.content[i].type + '">填空题</div><div class="choice-name" >' + ChioceName + '</div>\n' +
                            '\t\t\t\t<div class="choice-oper pack-up"><i class="my-icon lsm-sidebar-icon  icon-jiantouxia"></i></div>\n' +
                            '<div class="choice-oper addthis"><i class="my-icon lsm-sidebar-icon  icon-tianjia"></i></div>\n' +
                            '\t\t\t</div>\n' +
                            '\t\t\t<div class="choice-details">\n' +
                            '\t\t\t\t<div class="make-choice">\n' +
                            '\t\t\t\t</div>\n' +
                            '\t\t\t\t<div class="answer">标答：' + data.page.content[i].correct + '</div>\n' +
                            '\t\t\t\t<div class="difficulty">难度：' + difficul + '</div>\n' +
                            '\t\t\t\t<div class="analysis">解析：' + data.page.content[i].analysis + '</div>\n' +
                            '\t\t\t\t<div class="knowledge">知识点：'+data.page.content[i].abilityIds+'</div>\n' +
                            '\t\t\t</div>\n' +
                            '\t\t</div>');
                    }
                }
                choicebind();
                refaddbtn();
                total = data.page.totalElements;
                $(".totalmsg").html("【共" + total + "条记录】");
                if(nu==0){
                    refresh();
                }
            },
            error: function (data) {
                console.log("服务器异常");
            }
        });

    }

    $("#choices").empty();
    $("#addchoice").click(function (e) {
        if ($("#subIdScreen option:selected").val() == 0) {
            $.alert("请选择科目");
            return;
        }
        openWindw(e.pageX, e.pageY);
        binds(0);
        Knowledge();
    });

    function choicebind() {
        //添加试题到试卷
        $(".addthis").bind("click", function (e) {
            let choicename = $(this).parent().children(".choice-name").html();
            let choiceid = $(this).parent().parent().attr("choiceid");
            let choicetype = $(this).parent().children(".choice-type").attr("data");
            var htmlss = $(this).parent().parent().children(".choice-details").children(".make-choice").html();

            $("#choices").append('<div class="choice" choicecheck="0"  choiceid="' + choiceid + '"  c_index=""  bindId="">\n' +
                ' <div class="choice-title"><div class="choice-index"></div><div class="choice-name" >' + choicename + '</div>\n' +
                '<input class="choice-box"  name="checkItem" type="checkbox" />' +
                ' <div class="choice-oper delete-choice"><i class="my-icon lsm-sidebar-icon icon-shanchu "></i></div>\n' +
                '<div class="score_div">\n' +
                '<input class="form-control score1" type="text" placeholder="分值" value="0">\n' +
                '</div>' +
                '\t\t\t</div>\n' +
                '\t\t\t<div class="choice-details1">\n' +
                '\t\t\t\t<div class="make-choice">\n' +
                '\t\t\t\t\t<div class="text-cho">' + htmlss + '</div>\n' +
                '\t\t\t\t</div>\n' +
                '\t\t\t</div>\n' +
                '\t\t</div>');
            deleteChoiceBind();
            sortRef();
            refaddbtn();
            score_Num();
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

    //多选操作
    $("#batchop").click(function () {
        if (quan == 0) {
            quan = 1;
            $(this).text("完成");
            $(this).css("background", "#924343");
            $(".choice-box").css("display", "block");
            $("#addchoice").parent().hide();
        } else {
            quan = 0;
            $(".choice").removeClass("choice-check");
            $(this).text("多选");
            $(this).css("background", "#848484");
            $(".choice-box").css("display", "none");
            $(".choice-box").prop("checked", false);
            $("#addchoice").parent().show();
        }
    })
    //全选框点击事件
    $("#checkAll").bind("click", function () {
        var checkbox = document.getElementsByName("checkItem");
        if ($("#checkAll").is(':checked')) {//全选
            if (checkbox.length != 0)  //判断条件是当选择的个数不为0时
            {
                for (var i = 0; i < checkbox.length; i++) {
                    $(checkbox[i]).parent().parent().addClass("choice-check");
                    $(checkbox[i]).parent().parent().attr("choicecheck", 1);
                    checkbox[i].checked = true;
                }
            }
        } else {//全不选
            if (checkbox.length != 0)  //判断条件是当选择的个数不为0时
            {
                for (i = 0; i < checkbox.length; i++) {
                    $(checkbox[i]).parent().parent().removeClass("choice-check");
                    $(checkbox[i]).parent().parent().attr("choicecheck", 0);
                    checkbox[i].checked = false;
                }
            }
        }
    });

//试卷内题目绑定事件
    function deleteChoiceBind() {
        //勾选试题
        $('input:checkbox[name="checkItem"]').bind("click", function () {
            var checkbox = document.getElementsByName("checkItem");
            var exist = true;
            for (var i = 0; i < checkbox.length; i++) {
                if (checkbox[i].checked == false) {
                    $(checkbox[i]).parent().parent().removeClass("choice-check");
                    $(checkbox[i]).parent().parent().attr("choicecheck", 0);
                    exist = false;
                } else {
                    $(checkbox[i]).parent().parent().addClass("choice-check");
                    $(checkbox[i]).parent().parent().attr("choicecheck", 1);
                }
            }
            if (exist) {
                $("#checkAll").prop("checked", true);
            } else {
                $("#checkAll").prop("checked", false);
            }

        });
        //删除试题
        //$(".delete-choice").bind("click", function (e) {
        $(".delete-choice").unbind("click").click(function () {
            // e.stopPropagation();
            // e.preventDefault()
            let delbtn = $(this);
            if (quan == 1 && $(delbtn).parent().parent().attr("choicecheck") == 1) {
                let choices = $(".choice-check");
                if (choices.length < 0) return;
                $.confirm({
                    confirmButtonClass: 'btn-info',
                    cancelButtonClass: 'btn-danger',
                    title: '提示',
                    content: '确定要删除选中的试题吗？',
                    autoClose: 'confirm|3000',
                    confirm: function () {
                        choices.each(function (i, choice) {
                            if ($(choice).attr("bindid") == "") {
                                $(choice).remove();
                                sortRef();
                                refaddbtn();
                            } else {
                                $.ajax({
                                    url: "/TestPaperBindChoice/delete",
                                    type: "post",
                                    data: {id: $(choice).attr("bindid")},
                                    dataType: "json",
                                    async: false,
                                    success: function (data) {
                                        $(choice).remove();
                                        sortRef();
                                        refaddbtn();
                                        score_Num();
                                    },
                                    error: function (data) {
                                        console.log("服务器异常");
                                        $.alert("删除失败！");
                                        return;
                                    }
                                })
                            }
                        });
                    },
                    cancel: function () {

                    }
                });
            } else {
                $.confirm({
                    confirmButtonClass: 'btn-info',
                    cancelButtonClass: 'btn-danger',
                    title: '提示',
                    content: '确定要删除该试题吗？',
                    autoClose: 'confirm|3000',
                    confirm: function () {
                        let choice = $(delbtn).parent().parent();
                        if ($(choice).attr("bindid") == "") {
                            $(delbtn).parent().parent().remove();
                            sortRef();
                            refaddbtn();
                        } else {
                            $.ajax({
                                url: "/TestPaperBindChoice/delete",
                                type: "post",
                                data: {id: $(choice).attr("bindid")},
                                dataType: "json",
                                async: false,
                                success: function (data) {
                                    $(choice).remove();
                                    sortRef();
                                    refaddbtn();
                                    score_Num();
                                },
                                error: function (data) {
                                    console.log("服务器异常");
                                    $.alert("删除失败！");
                                    return;
                                }
                            })
                        }
                    },
                    cancel: function () {

                    }
                });
            }
        })

        // $(".choice").bind("click",function(e){
        //     e.stopPropagation();
        //    if (quan==1){
        //        if($(this).attr("choicecheck")==0){
        //            $(this).attr("choicecheck",1);
        //            $(this).addClass("choice-check");
        //        }else{
        //            $(this).attr("choicecheck",0);
        //            $(this).removeClass("choice-check");
        //        }
        //    }
        // });
        $(".score1").bind("click", function (e) {
            e.stopPropagation();
        });

        // 格式化限制数字文本框输入，只能数字或者两位小数
        function input_num(obj) {
            // 清除"数字"和"."以外的字符
            obj.value = obj.value.replace(/[^\d.]/g, "");
            // 验证第一个字符是数字
            obj.value = obj.value.replace(/^\./g, "");
            // 只保留第一个, 清除多余的
            obj.value = obj.value.replace(/\.{2,}/g, ".");
            obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
            // 只能输入两个小数
            obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3');

            //如果有小数点，不能为类似 1.10的金额
            if (obj.value.indexOf(".") > 0 && obj.value.indexOf("0") > 2) {
                obj.value = parseFloat(obj.value);
            }

            //如果有小数点，不能为类似 0.00的金额
            if (obj.value.indexOf(".") > 0 && obj.value.lastIndexOf("0") > 2) {
                obj.value = parseFloat(obj.value);
            }
            //以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额
            if (obj.value.indexOf(".") <= 0 && obj.value != "") {
                obj.value = parseFloat(obj.value);
            }
            if (obj.value == null) obj.value = 0;
        }

        //失去焦点校验
        $(".score1").bind("blur", function (e) {
            input_num(this);
            if (quan == 1 && $(this).parent().parent().parent().attr("choicecheck") == 1) {
                let sco = $(this).val();
                let choices = $(".choice-check");
                choices.each(function (i, choice) {
                    $(choice).children(".choice-title").children(".score_div").children(".score1").val(sco)
                });
            }
            score_Num();
        });
        //批量操作时，被选中的分数同步
        $(".score1").bind("keyup", function (e) {
            var curKey = e.which;
            if (curKey != 13) return;
            input_num(this);
            if (quan == 1 && $(this).parent().parent().parent().attr("choicecheck") == 1) {
                let sco = $(this).val();
                let choices = $(".choice-check");
                choices.each(function (i, choice) {
                    $(choice).children(".choice-title").children(".score_div").children(".score1").val(sco)
                });
            }
            score_Num();
        });
    }

//计算总分
    function score_Num() {
        let choices = $(".choice");
        let scoreNums = 0;
        choices.each(function (i, choice) {
            scoreNums += parseFloat($(choice).children(".choice-title").children(".score_div").children(".score1").val());
        });
        $(".scoreNum").text("总分:(" + scoreNums + "分)")
    }

    $("#saveTestPaper").click(function () {
        let name = $("#testPaperName").val();
        let subId = $("#subIdScreen option:selected").val();
        let testPaperid = $("#testPaper").attr("testPaperId");
        if (name == null || name == "") {
            $.alert("试卷名称不能为空");
            return false;
        }
       if(subId==0){
           $.alert("学科不能为空");
           return false;
       }

        let TestPaper = {
            "name": name,
            "subId": subId,
            "id": testPaperid
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
                if (data != undefined && data != null) {
                    $("#testPaper").attr("testPaperId", data.id);
                    saveTestPaperBindChoice();
                }
            },
            error: function (data) {
                console.log("服务器异常");
            }
        })
    });

    function saveTestPaperBindChoice() {
        var choices = $(".choice");
        let testPaperList = [];
        choices.each(function (i, choice) {
            let TestPaperBindChoice = {
                "id": $(choice).attr("bindId"),
                "indexNum": $(choice).attr("c_index"),
                "choiceId": $(choice).attr("choiceid"),
                "testPaperId": $("#testPaper").attr("testPaperId"),
                "score": $(choice).children(".choice-title").children(".score_div").children(".score1").val()
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
                $.alert("试题保存成功");

            },
            error: function (data) {
                console.log("服务器异常");
            }
        })
        this.parent.open("testPaper");

    }


});








