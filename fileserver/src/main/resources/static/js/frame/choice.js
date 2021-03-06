// JavaScript Document
var checkAll = [];
var samplerCheckAll = [];
var knowledgeList=[];
$(function () {

    $("#upload").on("click", function () {
        $("#myModal").modal("show");
    });

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
                $("#subIdScreen").append('<option value="0">全部学科</option>');
                for (let i = 0; i < data.length; i++) {
                    $("#subjectId").append('<option value="' + data[i].id + '">' + data[i].name + '</option>');
                    $("#subIdScreen").append('<option value="' + data[i].id + '">' + data[i].name + '</option>');
                }
                $('#subjectId').selectpicker('refresh');
                $('#subIdScreen').selectpicker('refresh');
                $('#subjectId').val(data[0].id).trigger("change");
                $('#subIdScreen').val(0).trigger("change");
            },
            error: function (data) {

            }
        });
    }

    choicebind();
    getsubtype();
    var pageNumber = 10; // 每页显示多少条记录
    var pageIndex = 0;//页码
    var total2 = 10;
    var pageNumber2 = 5; // 每页显示多少条记录
    var pageIndex2 = 0;//页码
    var subId = 0;
    var choicetype = 0;
    var likeName = "";
    var likeName2 = "";
    var sort = "";
    var sortName = "";
    let nu=0;
    let nu2=0;
    //分页元素
    var total = 50; // 总共多少记录
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

    function refresh2() {
        nu2=1;
        pageNumber2 = $("#showNumber2").val();
        $("#Pagination2").pagination(total2, {
            callback: PageCallback2,
            prev_text: '上一页',
            next_text: '下一页',
            items_per_page: pageNumber2,
            num_display_entries: 4, // 连续分页主体部分显示的分页条目数
            num_edge_entries: 1, // 两侧显示的首尾分页的条目数
            jump: true,
        });
    }


    $("#showNumber2").val('5').trigger("change")
    $("#showNumber2").change(function () {
        if (pageNumber2 != $("#showNumber2").val()) {
            pageNumber2 = $("#showNumber2").val();
            $("#Pagination2").pagination(total2, {
                callback: PageCallback2,
                prev_text: '上一页',
                next_text: '下一页',
                items_per_page: pageNumber2,
                num_display_entries: 4, // 连续分页主体部分显示的分页条目数
                num_edge_entries: 1, // 两侧显示的首尾分页的条目数
                jump: true,
            });
            getaccessory(0);
        }
    });





    function PageCallback(index, jq) { // 前一个参数表示当前点击的那个分页的页数索引值，后一个参数表示装载容器。
        pageIndex = index;
        Init(pageIndex);
    }

    function PageCallback2(index, jq) { // 前一个参数表示当前点击的那个分页的页数索引值，后一个参数表示装载容器。
        pageIndex2 = index;
        getaccessory(pageIndex2);
    }

    $("#subIdScreen").change(function () {
        subId = $("#subIdScreen").val();
        nu=0;
        Init(0);
    });
    $("#typeScreen").change(function () {
        choicetype = $("#typeScreen").val();
        nu=0;
        Init(0);
    });

    $(".querybtn").click(function () {
        likeName = $("#query").val();
        nu=0;
        Init(0);
    });

    function Init(index) { // 参数就是点击的那个分页的页数索引值
        pageIndex = index;
        let ScreenChoice = {
            "pageNumber": pageNumber,
            "pageIndex": pageIndex,
            "subId": subId,
            "type": choicetype,
            "name": likeName,
            "sort": sort,
            "sortName": sortName,
            "abilityIds": null,
            "difficultyLevel": null
        }
        var win = window;
        $.ajax({
            url: "/choice/findAll",
            contentType: "application/json;charset=UTF-8",
            type: "post",
            async: true,
            data: JSON.stringify(ScreenChoice),
            dataType: "json",
            success: function (data) {
                $(".allchoice").empty();
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
                                    op = "C." + data.page.content[i].optionC;
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

                        $(".allchoice").append('<div class="choice" choiceid="' + data.page.content[i].id + '">\n' +
                            '<div class="choice-title"><div class="choice-type" data="' + data.page.content[i].type + '">' + type + '</div><div class="choice-name" >' + data.page.content[i].topic + '</div>\n' +
                            '<div class="choice-oper pack-up"><i class="my-icon lsm-sidebar-icon  icon-jiantouxia"></i></div>\n' +
                            '<div class="choice-oper delete-choice"><i class="my-icon lsm-sidebar-icon icon-shanchu "></i></div>\n' +
                            '<div class="choice-oper edit-choice"><i class="my-icon lsm-sidebar-icon icon-bianji "></i></div>\n' +
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
                        $(".allchoice").append('<div class="choice"  choiceid="' + data.page.content[i].id + '">\n' +
                            '\t\t\t<div class="choice-title"><div class="choice-type" data="' + data.page.content[i].type + '">判断题</div><div class="choice-name" >' + data.page.content[i].topic + '</div>\n' +
                            '\t\t\t\t<div class="choice-oper pack-up"><i class="my-icon lsm-sidebar-icon  icon-jiantouxia"></i></div>\n' +
                            '\t\t\t\t<div class="choice-oper delete-choice"><i class="my-icon lsm-sidebar-icon icon-shanchu "></i></div>\n' +
                            '\t\t\t\t<div class="choice-oper edit-choice"><i class="my-icon lsm-sidebar-icon icon-bianji "></i></div>\n' +
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
                        $(".allchoice").append('<div class="choice"  choiceid="' + data.page.content[i].id + '">\n' +
                            '\t\t\t<div class="choice-title"><div class="choice-type" data="' + data.page.content[i].type + '">简答题</div><div class="choice-name" >' + data.page.content[i].topic + '</div>\n' +
                            '\t\t\t\t<div class="choice-oper pack-up"><i class="my-icon lsm-sidebar-icon  icon-jiantouxia"></i></div>\n' +
                            '\t\t\t\t<div class="choice-oper delete-choice"><i class="my-icon lsm-sidebar-icon icon-shanchu "></i></div>\n' +
                            '\t\t\t\t<div class="choice-oper edit-choice"><i class="my-icon lsm-sidebar-icon icon-bianji "></i></div>\n' +
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
                        $(".allchoice").append('<div class="choice"  choiceid="' + data.page.content[i].id + '">\n' +
                            '\t\t\t<div class="choice-title"><div class="choice-type" data="' + data.page.content[i].type + '">填空题</div><div class="choice-name" >' + ChioceName + '</div>\n' +
                            '\t\t\t\t<div class="choice-oper pack-up"><i class="my-icon lsm-sidebar-icon  icon-jiantouxia"></i></div>\n' +
                            '\t\t\t\t<div class="choice-oper delete-choice"><i class="my-icon lsm-sidebar-icon icon-shanchu "></i></div>\n' +
                            '\t\t\t\t<div class="choice-oper edit-choice"><i class="my-icon lsm-sidebar-icon icon-bianji "></i></div>\n' +
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
                total = data.page.totalElements;
                $(".totalmsg").html("【共" + total + "条记录，当前显示：" + (data.page.pageable.pageNumber * data.page.pageable.pageSize + 1) + "~" + (data.page.pageable.pageNumber * data.page.pageable.pageSize + data.page.numberOfElements) + "】");
                if(nu==0){
                    refresh()
                }
            },
            error: function (data) {
                console.log("服务器异常");
            }
        });
    }

    $("#addchoice").click(function (e) {
        //清空savchoice内容
        $(".choice_table").attr("choice_id", "");
        knowledgeList=[];
        openWindw(e.pageX, e.pageY, 0);
    });
//////////////////////////////////////////////////////////////////////悬浮窗口js
    //var oBtn = $('#show');
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

    function openWindw(x, y, id) {
        ref();
        $("#sp1").hide();
        $(".addtext").hide();
        $(".popWindow").css("min-width", '300px');
        $(window).resize();
        $("#resetchoice").click();
        popinit(id);

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
    //收起
    oClose.click(function () {
        $(this).hide();
        $(".addtext").hide();
        $(".popWindow").css("min-width", '300px');
        $(window).resize();
    });
    //切换学科
    $("#subjectId").change(function () {
        $("#query1").empty();
        $(".knowledges").empty();
        if ($(".addtext").is(':visible')) {
            getknowleged();
        }
    });
    //切换题目类型
    $(".choice_judje_tr").hide();
    $(".awser_tr2").hide();
    $("#choicetype").change(function () {
        if ($("#choicetype").val() == 1) {
            $(".choice_judje_tr").hide();
            $(".choice_option_tr").show();
            $("#choicenum").change();
            $(".choice_option").attr("type", "radio");
            $(".awser_tr1").show();
            $(".awser_tr2").hide();
            $(".accessory_tr").hide();
            $("#scaleofmarks").val(0).trigger("change");


        } else if ($("#choicetype").val() == 2) {
            $(".choice_judje_tr").hide();
            $(".choice_option_tr").show();
            $("#choicenum").change();
            $(".choice_option").attr("type", "checkbox");
            $(".awser_tr1").show();
            $(".awser_tr2").hide();
            $(".accessory_tr").hide();
            $("#scaleofmarks").val(1).trigger("change");
        } else if ($("#choicetype").val() == 3) {
            $(".choice_option_tr").hide();
            $(".choice_judje_tr").show();
            $(".awser_tr1").show();
            $(".awser_tr2").hide();
            $(".accessory_tr").hide();
            $("#scaleofmarks").val(0).trigger("change");
        } else  if($("#choicetype").val() == 5){
            $(".choice_option_tr").hide();
            $(".choice_judje_tr").hide();
            $(".awser_tr2").show();
            $(".awser_tr1").hide();
            $(".accessory_tr").hide();
            $("#scaleofmarks").val(2).trigger("change");
        } else {
            $(".choice_option_tr").hide();
            $(".choice_judje_tr").hide();
            $(".awser_tr2").show();
            $(".awser_tr1").hide();
            $(".accessory_tr").show();
            $("#scaleofmarks").val(2).trigger("change");
        }
        checkedno();
    });
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
    //点击对错按钮
    $(".choice_judje").click(function () {
        if ($(this).val() == 1)
            $("#anwser").val("正确");
        else
            $("#anwser").val("错误");
    });

    //全部不选中状态
    function checkedno() {
        var obj = document.getElementsByName('opt');
        for (var i = 0; i < obj.length; i++) {
            obj[i].checked = false;
        }
        var obj2 = document.getElementsByName('judje');
        for (var j = 0; j < obj2.length; j++) {
            obj2[j].checked = false;
        }
        $("#anwser").val('');
    }

    //搜索
    var querident;
    $("#query101").click(function () {

        if (querident == 1)
            getknowleged();
        else
            nu2=0;
            getaccessory(0);
    });
    //点击知识点
    $("#knowledge_open").click(function () {
        querident = 1;
        $(".addtext").show();
        $(".library_div").hide();
        $('.popWindow h3 span').show();
        $(window).resize();
        $("#query1").val("");
        $(".popWindow").css("min-width", '710px');
        $(".addtitel").html("知识点");
        $(".addcontext").empty();
        $("#paginationParent2").hide();
        getknowleged();
        total2=0;
        refresh2();
    });
    //点击附件
    $("#accessory_open").click(function () {
        $("#paginationParent2").show();
        querident = 2;
        $(".addtext").show();
        $(".library_div").show();
        $('.popWindow h3 span').show();
        $(window).resize();
        $("#query1").val("");
        $(".popWindow").css("min-width", '710px');
        $(".addtitel").html("附件");
        $(".addcontext").empty();
        nu2=0;
        getaccessory(0);
    });
 //附件文件类型切换
    $("#library").change(function () {
        nu2=0;
        getaccessory(0);
    });

    //初始化数据
    function popinit(id) {
        if (id != 0) {
            getchoice(id);

        } else {

            return;
        }
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

    //获取某一试题信息
    function getchoice(id) {
        knowledgeList=[];
        $.ajax({
            url: "/choice/findOneById",
            type: "post",
            data: {id: id},
            dataType: "json",
            success: function (data) {
                $(".choice_table").attr("choice_id", data.id);
                $("#choicetype").val(data.type).trigger("change");
                $("#subjectId").val(data.subId).trigger("change");
                $("#choice_name").val(data.topic);
                if (data.type == 1 || data.type == 2) {
                    $("#choicenum").val(data.optionNum).trigger("change");
                    $("#optionA").val(data.optionA != null ? data.optionA : "");
                    $("#optionB").val(data.optionB != null ? data.optionB : "");
                    $("#optionC").val(data.optionC != null ? data.optionC : "");
                    $("#optionD").val(data.optionD != null ? data.optionD : "");
                    $("#optionE").val(data.optionE != null ? data.optionE : "");
                    $("#optionF").val(data.optionF != null ? data.optionF : "");
                    $("#anwser").val(data.correct);
                    let s = data.correct.toArray();
                    for (let l in s) {
                        if (s[l] == "A") $(".optionA").children("td").children(".choice_option").prop("checked", true);
                        if (s[l] == "B") $(".optionB").children("td").children(".choice_option").prop("checked", true);
                        if (s[l] == "C") $(".optionC").children("td").children(".choice_option").prop("checked", true);
                        if (s[l] == "D") $(".optionD").children("td").children(".choice_option").prop("checked", true);
                        if (s[l] == "E") $(".optionE").children("td").children(".choice_option").prop("checked", true);
                        if (s[l] == "F") $(".optionF").children("td").children(".choice_option").prop("checked", true);
                    }
                } else if (data.type == 3) {

                    if (data.correct == 1) {
                        $(".cor1").prop("checked", true);
                        $("#anwser").val("正确");
                    } else {
                        $(".cor2").prop("checked", true);
                        $("#anwser").val("错误");
                    }
                } else if (data.type == 4 || data.type == 5) {
                    $("#answer2").val(data.correct);
                }
                if (data.difficultyLevel == 5) $("#star5").prop("checked", true);
                if (data.difficultyLevel == 4) $("#star4").prop("checked", true);
                if (data.difficultyLevel == 3) $("#star3").prop("checked", true);
                if (data.difficultyLevel == 2) $("#star2").prop("checked", true);
                if (data.difficultyLevel == 1) $("#star1").prop("checked", true);

                $("#choice_analysis").val(data.analysis);
                $("#scaleofmarks").val(data.scaleRule).trigger("change");
                console.log(data);
                checkAll = [];
                samplerCheckAll = [];
                $.ajax({
                    url:"knowledgePoint/selectByNam",
                    type: "post",
                    data:{"arr":data.abilityIds},
                    sync: false,
                    dataType: "json",
                    success:function (data3) {
                        console.log(data3)
                        for(let i=0;i<data3.length;i++){
    $(".knowledges").append('<span class="knowledge_a" knowid="' + data3[i].id + '" knowname="' + data3[i].name+ '">' +  data3[i].name + '</span>')
                            knowledgeList.push(data3[i].id);
                        }
                        $(".knowledge_a").bind("click", function () {
                            let  index= knowledgeList.indexOf(parseInt($(this).attr("knowid")));
                            if (index > -1) {
                                knowledgeList.splice(index, 1);
                            }
                            $(this).remove();
                        })

                    }
                })

                attachment();

            },
            error: function (data) {
                $.alert("服务器异常");
            }
        });
    }

    //获取知识点
    function getknowleged() {
        $.ajax({
            url: "/knowledgePoint/findAllByNameLike",
            type: "post",
            data: {subid: $('#subjectId').val(), name: $("#query1").val()},
            dataType: "json",
            success: function (data) {
                $(".addcontext").empty();
                for (let i = 0; i < data.length; i++) {
                    //$(".addcontext").append(data[i].subId+";"+data[i].name+"<br>")
                    $(".addcontext").append('<div class="knowledge_b" knowid="' + data[i].id + '" knowname="' + data[i].name + '" >\n' +
                        '<span class="knowledge_bp1" >' + data[i].name + '</span>\n' +
                        '<span class="knowledge_bp2" >\n' +
                        '<i class="my-icon lsm-sidebar-icon icon-tianjia know_add"></i>\n' +
                        '</span>\n' +
                        '</div>')
                }
                $(".know_add").bind("click", function () {
                    if(knowledgeList.indexOf(parseInt($(this).parent().parent().attr("knowid")))>-1){
                        return ;
                    }
                    $(".knowledges").append('<span class="knowledge_a" knowid="' + $(this).parent().parent().attr("knowid") + '" knowname="' + $(this).parent().parent().attr("knowname") + '">' + $(this).parent().parent().attr("knowname") + '</span>')
                    knowledgeList.push(parseInt($(this).parent().parent().attr("knowid")));

                    $(".knowledge_a").bind("click", function () {
                       let  index= knowledgeList.indexOf(parseInt($(this).attr("knowid")));
                        if (index > -1) {
                            knowledgeList.splice(index, 1);
                        }

                        $(this).remove();
                    })
                });

            },
            error: function (data) {
                $.alert("服务器异常");
            }
        });
    }

//获取勾选附件


//单位转换
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

    //获取附件
    function getaccessory(index) {
         if($("#library").val()==0){
        pageIndex2 = index;
        let ScreenShareFile = {
            "pageNumber": pageNumber2,
            "pageIndex": pageIndex2,
            "queryLevel": 0,
            "screenLevel": 0,
            "issistId": 0,
            "likeName": $("#query1").val(),
            "sort": sort,
            "sortName": sortName
        }
        $.ajax({
            url: "/file/choiceShareFile",
            contentType: "application/json;charset=UTF-8",
            type: "post",
            async: false,
            data: JSON.stringify(ScreenShareFile),
            dataType: "json",
            success: function (data) {
                let me = 0;
                $(".addcontext").empty();
                for (let i = 0; i < data.page.content.length; i++) {
                    if (me == 0) {
                        let check = "";
                        for (let j = 0; j < checkAll.length; j++) {
                            if (checkAll[j] == data.page.content[i].id) {
                                check = "checked=checked";
                            }
                        }
                        $(".addcontext").append('<div class="filtr-item chunk1" fileid=' + data.page.content[i].id + '>\n' +
                            '<img src="../../images/file_logo_png/1140224.png"/>\n' +
                            '<input type="checkbox" name="ch" class="checkbox" ' + check + ' onchange="attachmentCheck(this)"/> \n' +
                            '<div class="sharefilename">' + data.page.content[i].name + '</div>\n' +
                            '<div class="sharefilesize">' + getKbMbGb(data.page.content[i].size) + '</div>\n' +
                            '</div>');
                    } else {
                        $(".addcontext").append('' +
                            '<div class="filtr-item line1" fileid=' + data.page.content[i].id + '>\n' +
                            '<img src="../../images/file_logo_png/1140224.png"/>\n' +
                            '<div class="sharefilename">' + data.page.content[i].name + '</div>\n' +
                            '<div class="sharefilesize">' + getKbMbGb(data.page.content[i].size) + '</div>\n' +
                            '<div class="susdiv">\n' +
                            '<div class="operation download"><i class="my-icon lsm-sidebar-icon  ">选择</i></div>\n' +
                            '<div class="operation compile"><i class="my-icon lsm-sidebar-icon icon-chazhao "></i></div>\n' +
                            '<div class="operation delete"><i class="my-icon lsm-sidebar-icon icon-chazhao "></i></div>\n' +
                            '</div>\n' +
                            '</div>');
                    }
                }
                total2 = data.page.totalElements;
                if(nu2==0){
                    refresh2();
                }

            },
            error: function (data) {
                console.log("服务器异常");
            }
        });
        	}else{
                pageIndex2 = index+1;
                let Page={
                    "pageIndex":pageIndex2,
                    "pageNumber":pageNumber2,
                    "likeName": $("#query1").val()
                }
                $.ajax({
                    url: "/Sample/findAll",
                    type: "post",
                    contentType: "application/json;charset=UTF-8",
                    data: JSON.stringify(Page),
                    dataType: "json",
                    success: function (data) {
                        $(".addcontext").empty();
                        for (let i=0;i<data.size;i++) {
                            let check = "";
                            for (let j = 0; j < samplerCheckAll.length; j++) {
                                if (samplerCheckAll[j] == data.list[i].id) {
                                    check = "checked=checked";
                                }
                            }
                            $(".addcontext").append('<div class="filtr-item chunk1" fileid=' + data.list[i].id + '>\n' +
                                '<img src="../../images/file_logo_png/1140224.png"/>\n' +
                                '<input type="checkbox" name="sampleCh" class="checkbox" ' + check + '  onchange="attachmentChecks(this)"/> \n' +
                                '<div class="sharefilename">' + data.list[i].fileName + '</div>\n' +
                                '<div class="sharefilesize">' + getKbMbGb(data.list[i].fileSize) + '</div>\n' +
                                '</div>');
                        }
                        total2 = data.total;
                        if(nu2==0){
                            refresh2();
                        }

                    }, error: function (data) {

                    }
                });

        	}



    }

    //重置
    $("#resetchoice").click(function () {
        $("#choice_name").val("");
        $(".operation1").val("");
        checkedno();
        $("#anwser").val("");
        $("#answer2").val("");
        $(".knowledges").empty();//知识点
        $(".accessorys").empty();//附件
        $("#choice_analysis").val("");//解析
    });
    //提交
    $("#savechoice").click(function () {
        let id;
        let subId;//学科id
        let topic;//题目
        let type;//类型（单选，多选，判断题，简答题）
        let optionNum = null;//选项个数
        let optionA = null;//选项A
        let optionB = null;
        let optionC = null;
        let optionD = null;
        let optionE = null;
        let optionF = null;
        let correct;//正确选项
        let analysis;//解析
        let abilityIds = "";//知识点id集合
        let difficultyLevel;//难易程度
        let scaleRule;//评分标准
        let createTime = null;
        let uapdateTime = null;
        if ($(".choice_table").attr("choice_id") == "")
            id = null;
        else
        id = $(".choice_table").attr("choice_id");
        subId = $("#subjectId").val();
        topic = $("#choice_name").val();
        if (topic == "" || topic.length == 0) {
            $.alert("请添加题目");
            return;
        }
        type = $("#choicetype").val();
        //附件集合-待添加
        //for ()
        optionNum = $("#choicenum").val();
        optionA = $("#optionA").val();
        optionB = $("#optionB").val();
        optionC = $("#optionC").val();
        optionD = $("#optionD").val();
        if (optionNum == 5) {
            optionE = $("#optionE").val();
        }
        if (optionNum == 6) {
            optionE = $("#optionE").val();
            optionF = $("#optionF").val();
        }
        if (type == 1 || type == 2) {
            if (optionA == "" || optionB == "" || optionC == "" || optionD == "" || optionE == "" || optionF == "") {
                $.alert("请完善选项");
                return;
            }
            correct = $("#anwser").val();
            if (correct == "" || correct.length == 0) {
                $.alert("请勾选正确选项");
                return;
            }
        }
        if (type == 3) {
            correct = $("input[name='judje']:checked").val();
            if (correct == null) {
                $.alert("请勾选正确选项");
                return;
            }
        }
        if (type == 4) {
            correct = $("#answer2").val();
            if (correct == null || correct.length == 0) {
                $.alert("请输入参考答案");
                return;
            }
        }
        if (type == 5) {
            correct = $("#answer2").val();
            if (correct == null || correct.length == 0) {
                $.alert("请输入参考答案");
                return;
            }
        }
        analysis = $("#choice_analysis").val();
        //知识点集合
        abilityIds = "";
      for (let i=0;i<knowledgeList.length;i++){
          abilityIds+=knowledgeList[i]+","
      }

        difficultyLevel = $("input[name='star']:checked").val();
        if (difficultyLevel == null) {
            $.alert("请选择难易程度");
            return;
        }
        scaleRule = $("#scaleofmarks").val();
        let Choice = {
            "id": id,
            "subId": subId,
            "topic": topic,
            "type": type,
            "fileIds": "",
            "optionNum": optionNum,
            "optionA": optionA,
            "optionB": optionB,
            "optionC": optionC,
            "optionD": optionD,
            "optionE": optionE,
            "optionF": optionF,
            "correct": correct,
            "analysis": analysis,
            "abilityIds": abilityIds,
            "difficultyLevel": difficultyLevel,
            "scaleRule": scaleRule,
            "createTime": createTime,
            "uapdateTime": uapdateTime
        }


        let  choiceFileList=[];
        for (let i = 0; i < checkAll.length; i++) {
            //fileIds +=  + ","
            let  ChoiceFile={
                "choiceId":id,
                "type":0,
                "fileId":checkAll[i]
            }
            choiceFileList.push(ChoiceFile);
        }

        for (let i = 0; i < samplerCheckAll.length; i++) {
            //fileIds +=  + ","
            let  ChoiceFile={
                "choiceId":id,
                "type":1,
                "fileId":samplerCheckAll[i]
            }
            choiceFileList.push(ChoiceFile);
        }

        let  ChoiceUtil={
        "choice":Choice,
        "choiceFileList":choiceFileList
        }

        $.ajax({
            url: "/choice/save",
            contentType: "application/json;charset=UTF-8",
            type: "post",
            async: false,
            data: JSON.stringify(ChoiceUtil),
            dataType: "json",
            success: function (data) {
                $("#resetchoice").click();
                $(".addtext").hide();
                $("#cancel").click();
                Init(pageIndex);

            },
            error: function (data) {
                $.alert("服务器异常,添加失败！");
            }
        });
    })

//////////////////////////////////////////////////////////////////////悬浮窗口js


    function choicebind() {
        $(".edit-choice").bind("click", function (e) {
            openWindw(e.pageX, e.pageY, $(this).parent().parent().attr("choiceid"));


        });
        $(".delete-choice").bind("click", function (e) {
            var thi = $(this);
            $.confirm({
                confirmButtonClass: 'btn-info',
                cancelButtonClass: 'btn-danger',
                title: '提示',
                content: '确定要删除该试题吗？',
                autoClose: 'confirm|3000',
                confirm: function () {
                    $.ajax({
                        url: "/choice/deleteById",
                        //contentType:"application/json;charset=UTF-8",
                        type: "post",
                        async: false,
                        data: {id: thi.parent().parent().attr("choiceid")},
                        dataType: "json",
                        success: function (data) {
                            if (!data.resoult) {
                                $.confirm({
                                    title: data.msg,
                                    cancelButton: false,
                                    content: data.testPaperNames,
                                    autoClose: 'confirm|10000',
                                });
                            } else {
                                $.alert("删除成功");
                            }
                            Init(pageIndex);
                        },
                        error: function (data) {
                            $.alert("服务器异常,删除失败！");
                        }
                    });
                },
                cancel: function () {

                }
            });
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


    $(".btn-primary").on('click', function () {
        var formData = new FormData();
        formData.append("file", $("#f_upload")[0].files[0]);
        $.ajax({
            url: "/exportExcel/importExcels",
            type: 'POST',
            async: false,
            data: formData,
            // 告诉jQuery不要去处理发送的数据
            processData: false,
            // 告诉jQuery不要去设置Content-Type请求头
            contentType: false,
            beforeSend: function () {
                console.log("正在进行，请稍候");
            },
            success: function (responseStr) {
                if (responseStr == "导入成功") {
                    $.alert("导入成功");
                    nu=0;
                    Init(pageIndex);
                } else {
                    alert("导入失败");
                }
            }
        });
    });

    function choiceFile() {


    }

});

//搜索框回车
function query1(e) {
    var keyCode = null;
    if (e.which)
        keyCode = e.which;
    else if (e.keyCode)
        keyCode = e.keyCode;
    if (keyCode == 13)
        $("#query101").click();
}

function exportchoice() {
    window.location.href = "exportExcel/choiceexportExcel";
}

function attachmentCheck(checker) {
    let fileId = $(checker).parent().attr("fileid");
    if ($(checker).prop("checked")) {
        checkAll.push(fileId);
    } else {
        let index = checkAll.indexOf(fileId);
        checkAll.splice(index, 1)

    }

}

function attachmentChecks(checker) {
    let fileId = $(checker).parent().attr("fileid");
    if ($(checker).prop("checked")) {
        samplerCheckAll.push(fileId);
    } else {
        let index = checkAll.indexOf(fileId);
        samplerCheckAll.splice(index, 1)
    }

}

function attachmentCheck2() {
    $(":checkbox[name='ch']:checked").each(function () {
        let fileId = $(this).parent().attr("fileid");
        checkAll.push(fileId);
    });
    
    



}

function attachment() {
    if($(".choice_table").attr("choice_id") != "" && $(".choice_table").attr("choice_id") != null && $(".choice_table").attr("choice_id") != undefined  ) {
        $.ajax({
            url: "ChoiceFile/findByChoiceId",
            type: "post",
            data: {"choiceId": $(".choice_table").attr("choice_id")},
            dataType:"json",
            sync:false,
            success:function (data) {
                console.log(data);
               for(let i=0;i<data.length;i++){
                   if(data[i].type==0){
                    checkAll.push(data[i].fileId)
                   }else{
                       samplerCheckAll.push(data[i].fileId)
                   }
               }
            },
            error:function (data) {

            }
        })
    }

}





