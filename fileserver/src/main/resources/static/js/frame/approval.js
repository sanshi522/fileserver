var answerList = [];
var win = window;
var pageNumber = 5; // 每页显示多少条记录
var pageIndex = 0;//页码
var total = 50; // 总共多少记录
var  sampleUrl="";
$(function () {
    //获取样本服务器的访问地址
    $.ajax({
        url: "Sample/sampleUrl",
        contentType: "application/json;charset=UTF-8",
        type: "post",
        success: function (data) {
            sampleUrl = data;
        },
        error: function (data) {
            console.log("服务器异常")

        }
    })
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


    Init(0);

    function Init(index) {
        $.ajax({
            url: "Answer/approval",
            type: "Post",
            data: {"respondentId": $("#respondentId").attr("respondentId")},
            success: function (data) {
                let difficul = "";
                for (let i = 0; i < data.length; i++) {
                    if (data[i].choice.type == 1 || data[i].choice.type == 2) {//单选题和多选
                        let opent = "";
                        for (let o = 0; o < data[i].choice.optionNum; o++) {
                            let op = '';
                            switch (o) {
                                case 0:
                                    op = "A." + data[i].choice.optionA;
                                    break;
                                case 1:
                                    op = "B." + data[i].choice.optionB;
                                    break;
                                case 2:
                                    op = "C." + data[i].choice.optionC;
                                    break;
                                case 3:
                                    op = "D." + data[i].choice.optionD;
                                    break;
                                case 4:
                                    op = "E." + data[i].choice.optionE;
                                    break;
                                default:
                                    op = "F." + data[i].choice.optionF;
                                    break;
                            }
                            if (data[i].choice.type == 1) {
                                if (data[i].answer != null && data[i].answer.answer != null && transition(data[i].answer.answer) == (o + 1)) {
                                    opent += '<div class="input-cho"><input class="single" type="radio" checked="checked" name="' + data[i].choice.id + '" />' + op + '</div>\n';
                                } else {
                                    opent += '<div class="input-cho"><input class="single" type="radio" name="' + data[i].choice.id + '" />' + op + '</div>\n';
                                }
                            } else {
                                if (data[i].answer != null && data[i].answer.answer != null) {
                                    var array = data[i].answer.answer.split(",");

                                    for (var j = 0; j < array.length; j++) {
                                        if (transition(array[j]) == (o + 1)) {
                                            opent += '<div class="input-cho"><input class="single" checked="checked" type="checkbox" name="' + data[i].choice.id + '" />' + op + '</div>\n';
                                            break;
                                        }
                                        if (j == (array.length - 1)) {
                                            opent += '<div class="input-cho"><input class="single" type="checkbox" name="' + data[i].choice.id + '" />' + op + '</div>\n';
                                        }
                                    }
                                } else {
                                    opent += '<div class="input-cho"><input class="single" type="checkbox" name="' + data[i].choice.id + '" />' + op + '</div>\n';
                                }
                            }
                        }
                        $("#choices").append(' <div class="choice" choicecheck="0" choiceid="' + data[i].choice.id + '" answerId="' + data[i].answer.id + '"  choice_type="' + data[i].choice.type + '" max_score="' + data[i].score + '"  >\n' +
                            '            <div class="choice-title"><div class="choice-index"></div><div class="choice-name" >' + data[i].choice.topic + '(' + data[i].score + '分) </div>\n' +
                            '<input class="choice-box"  name="checkItem" type="checkbox" />' +
                            '                <div class="choice-oper delete-choice"></div>\n' +
                            '<div class="score_div">\n' +
                            '<input class="form-control score1" type="number" placeholder="分值" value="0" min="0" max="' + data[i].score + '">\n' +
                            '</div>' +
                            // '                <div class="choice-oper delete-choice"><input type="checkbox" name="TestPaper"/></div>\n' +
                            '            </div>\n' +
                            '            <div class="choice-details1">\n' +
                            '                <div class="make-choice">\n' +
                            opent +
                            '                </div>\n' +
                            '            </div>\n' +
                            '        </div>');
                    } else if (data[i].choice.type == 3) {//判断题
                        let correct = ""
                        if (data[i].choice.correct == 1) correct = "正确"; else correct = "错误";
                        var checker = "";
                        var checker1 = "";
                        if (data[i].answer != null) {
                            if (data[i].answer.answer == 1) {
                                checker = "checked=checked ";
                            } else {
                                checker1 = "checked=checked ";
                            }
                        }
                        $("#choices").append('<div class="choice" choicecheck="0"  choiceid="' + data[i].choice.id + '" answerId="' + data[i].answer.id + '"  choice_type="' + data[i].choice.type + '" max_score="' + data[i].score + '"     >\n' +
                            ' <div class="choice-title"><div class="choice-index"></div><div class="choice-name" >' + data[i].choice.topic + '(' + data[i].score + '分) </div>\n' +
                            '<input class="choice-box"  name="checkItem" type="checkbox" />' +
                            ' <div class="choice-oper delete-choice"></div>\n' +
                            '<div class="score_div">\n' +
                            '<input class="form-control score1" type="number" placeholder="分值" value="0"  min="0" max="' + data[i].score + '">\n' +
                            '</div>' +
                            // ' <div class="choice-oper "><input type="checkbox" name="TestPaper"/></div>\n' +
                            '\t\t\t</div>\n' +
                            '\t\t\t<div class="choice-details1">\n' +
                            '\t\t\t\t<div class="make-choice">\n' +
                            '\t\t\t\t\t<div class="input-cho"><input class="single" type="radio" name="' + data[i].choice.id + '" ' + checker + ' />正确</div>\n' +
                            '\t\t\t\t\t<div class="input-cho"><input class="single" type="radio" name="' + data[i].choice.id + '" ' + checker1 + ' />错误</div>\n' +
                            '\t\t\t\t</div>\n' +

                            '\t\t\t</div>\n' +
                            '\t\t</div>');
                    } else if (data[i].choice.type == 4) {//简答题
                        let name = "";
                        if (data[i].answer != null && data[i].answer.answer != null) {
                            name = data[i].answer.answer;
                        }
                        $("#choices").append('<div class="choice" choicecheck="0"  choiceid="' + data[i].choice.id + '" answerId="' + data[i].answer.id + '"  choice_type="' + data[i].choice.type + '"  max_score="' + data[i].score + '"   >\n' +
                            ' <div class="choice-title"><div class="choice-index"></div><div class="choice-name" >' + data[i].choice.topic + '(' + data[i].score + '分) </div>\n' +
                            '<input class="choice-box"  name="checkItem" type="checkbox" />' +
                            ' <div class="choice-oper delete-choice"></div>\n' +
                            '<div class="score_div">\n' +
                            '<input class="form-control score1" type="number" placeholder="分值" value="0" min="0" max="' + data[i].score + '">\n' +
                            '</div>' +
                            // ' <div class="choice-oper delete-choice"><input type="checkbox" name="TestPaper"/></i></div>\n' +
                            '\t\t\t</div>\n' +
                            '\t\t\t<div class="choice-details1">\n' +
                            '\t\t\t\t<div class="make-choice">\n' +
                            '\t\t\t\t\t<div class="text-cho"><textarea rows="3" cols="50" wrap="hard" id="' + data[i].choice.id + '" placeholder="请输入参考答案" >' + name + ' </textarea></div>\n' +
                            '\t\t\t\t</div>\n' +
                            '<div style="float: left"> <a  class="btn btn-link" onclick="DownloadFile(' + data[i].choice.id + ')" >试题附件</a></div>  ' +
                            '<div style="float: left"> <a  class="btn btn-link" onclick="DownloadAttachment(' + data[i].answer.id + ')" >学生上传附件</a></div>  ' +
                            '\t\t\t</div>\n' +
                            '\t\t</div>');
                    }else if (data[i].choice.type == 5) {//填空题
                        let name = "";
                        let  ChioceName="";
                        if (data[i].answer != null && data[i].answer.answer != null) {
                            name = data[i].answer.answer;
                        }

                        if(name!="" && name!="#"){
                            let  answers  =name.split("#");
                            let  str=data[i].choice.topic;
                            let    s=   str.toArray();
                            let s1=0;

                            for (let x=0;x<s.length;x++){
                                if(s[x]=="_"){
                                    ChioceName+= '<input style=" border:none; border-bottom: 1px solid #000;" type="text" value='+answers[s1]+' />';
                                    s1++;
                                }else{
                                    ChioceName+=s[x];
                                }

                            }

                        }else {
                            let  str=data[i].choice.topic;
                            let    s=   str.toArray();
                            for (let x=0;x<s.length;x++){
                                if(s[x]=="_"){
                                    ChioceName+= '<input style=" border:none; border-bottom: 1px solid #000;" type="text"/>';
                                }else{
                                    ChioceName+=s[x];
                                }
                            }

                        }

                        $("#choices").append('<div class="choice" choicecheck="0"  choiceid="' + data[i].choice.id + '" answerId="' + data[i].answer.id + '"  choice_type="' + data[i].choice.type + '"  max_score="' + data[i].score + '"   >\n' +
                            ' <div class="choice-title"><div class="choice-index"></div><div class="choice-name" >' + ChioceName + '(' + data[i].score + '分) </div>\n' +
                            '<input class="choice-box"  name="checkItem" type="checkbox" />' +
                            ' <div class="choice-oper delete-choice"></div>\n' +
                            '<div class="score_div">\n' +
                            '<input class="form-control score1" type="number" placeholder="分值" value="0" min="0" max="' + data[i].score + '">\n' +
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
                sortRef();
            },
            error: function (data) {
            }
        });
    }

    function transition(br) {
        switch (br) {
            case "A":
                return 1;
                break
            case "B":
                return 2;
                break
            case "C":
                return 3;
                break
            case "D":
                return 4;
                break
            case "E":
                return 5;
                break
            case "F":
                return 6;
                break
        }
    }

});

function save() {
    let respondentId = $("#respondentId").attr("respondentId");
    let choices = $(".choice");
    answerList=[];
    if (choices.length > 0) {
        let flag = true;
        for (let i = 0; i < choices.length; i++) {
            let choice = choices[i];
            if (!flag) return;
            var id = $(choice).attr("choiceid");
            var type = $(choice).attr("choice_type");
            var answerid = $(choice).attr("answerId");
            var num = $(choice).attr("c_index");
            let maxscore = $(choice).attr("max_score");
            var score = $(choice).children(".choice-title").children(".score_div").children(".score1").val();


            var regPos = /^\d+(\.\d+)?$/; //非负浮点数
            if (!regPos.test(score)) {
                $.alert("分数不合法");

                $("html,body").animate({
                    scrollTop: choices.eq(parseInt(num)).offset().top - 40
                }, 200 /*scroll实现定位滚动*/); /*让整个页面可以滚动*/
                flag = false;

            }

            if (parseFloat(score) > parseFloat(maxscore)) {
                $.alert("分数不等大于总分");

                $("html,body").animate({
                    scrollTop: choices.eq(parseInt(num)).offset().top - 40
                }, 200 /*scroll实现定位滚动*/); /*让整个页面可以滚动*/
                flag = false;

            }
            if (score == undefined || score == "") {
                score = 0;
            }
            let Answer = {
                "id": answerid,
                "choiceId": id,
                "respondentsId": respondentId,
                "score": score
            }
            answerList.push(Answer);
        }
        if (!flag) {
            return;
        }

        $.ajax({
             url: "Answer/teachRead",
            type: "Post",
            sync: false,
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(answerList),
            dataType: "json",
            success: function (data) {
                $.alert("成功");
                window.location.href = "javascript:history.go(-1)";
            },
            error: function (data) {
                console.log("服务器异常");
            }
        })

    } else {
        $.ajax({
            url: "Respondents/Read",
            type: "Post",
            sync: false,
            data: {"respondentsId": respondentId},
            success: function (data) {
                $.alert("成功");
                window.location.href = "javascript:history.go(-1)";
            },
            error: function (data) {
                console.log("服务器异常");
            }
        });

    }

}


function DownloadAttachment(id) {
    const SPEED = 500;
    $.ajax({
        url: "Answer/findById",
        type: "post",
        data: {"id": id},
        success: function (data) {
            let str = data.fileIds.split(",");
            for (let i = 0; i < str.length; i++) {
                if (str[i] != "" && str[i] != null) {
                    setTimeout(() => {
                        win.location.href = "/file/downloadShareFile2?filename=" + str[i];
                    }, SPEED * i)
                } else {
                    $.alert("没有附件");
                }
            }
        },
        error: function () {
            console.log("服务器异常");
        }

    })
}


function DownloadFile(id) {
    $.ajax({
        url: "ChoiceFile/findFileName2",
        type: "post",
        data: {"choiceId": id},
        success: function (data) {
            const  SPEED=500;
            if(data.length>0){
                console.log(data);
                for (let i = 0; i < data.length; i++) {
                    if(data[i].type==0){
                        setTimeout(()=>{
                            win.location.href = "Sample/downloadShareFile?filename="+data[i].name+"&names="+data[i].fileName;
                        },SPEED*i)
                    }else {
                        setTimeout(()=>{
                            var newhref = sampleUrl+"Sample/downloadShareFile?filename="+data[i].name+"&names="+data[i].fileName;
                            win.location.href=newhref;
                        },SPEED*i)
                    }
                }
            }else {
                $.alert("没有附件");
            }
        },
        error: function () {
            console.log("服务器异常");
        }

    })

}


