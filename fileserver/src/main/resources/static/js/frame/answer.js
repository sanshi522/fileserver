var answerList = [];
var win = window;
$(function () {

    function addRespondents() {
        let Respondents = {
            "assessId": $("#assessId").attr("assessId"),
            "submit": 1,
            "correct": 1,
        }
        $.ajax({
            url: "Respondents/save",
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(Respondents),
            type: "Post",
            dataType: "json",
            success: function (data) {
                $("#respondentsId").val(data.id);
            },
            error: function (data) {
                console.log("服务器异常")
            }
        });
    }

    init();

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


//遍历试卷信息及试题
    function information(data) {
        if (data == null) {
            return;
        } else {
            $(".navigation_title").empty();
            $(".navigation_title").append('  <h5>考核名称：'+data.assess.name+'</h5>');
            let difficul = "";
            $("#choices").empty();
            let  score=0;
            for (let i = 0; i < data.choiceList.length; i++) {
                score+=data.choiceList[i].score;
                if (data.choiceList[i].choice.type == 1 || data.choiceList[i].choice.type == 2) {//单选题和多选
                    let opent = "";
                    for (let o = 0; o < data.choiceList[i].choice.optionNum; o++) {
                        let op = '';
                        switch (o) {
                            case 0:
                                op = "A." + data.choiceList[i].choice.optionA;
                                break;
                            case 1:
                                op = "B." + data.choiceList[i].choice.optionB;
                                break;
                            case 2:
                                op = "C." + data.choiceList[i].choice.optionC;
                                break;
                            case 3:
                                op = "D." + data.choiceList[i].choice.optionD;
                                break;
                            case 4:
                                op = "E." + data.choiceList[i].choice.optionE;
                                break;
                            default:
                                op = "F." + data.choiceList[i].choice.optionF;
                                break;
                        }
                        if (data.choiceList[i].choice.type == 1)
                            opent += '<div class="input-cho"><input class="single" type="radio" name="' + data.choiceList[i].choice.id + '" value="' + o + '" />' + op + '</div>\n';
                        else
                            opent += '<div class="input-cho"><input class="single" type="checkbox" name="' + data.choiceList[i].choice.id + '" value="' + o + '" />' + op + '</div>\n';
                    }
                    $("#choices").append('  <div class="choice" choicecheck="0" choice_type="' + data.choiceList[i].choice.type + '" choiceid="' + data.choiceList[i].choice.id + '"> <div class="choice-title"> <div class="choice-index"> ' + (i + 1) + ' \n' +
                        '</div><div class="choice-name" >' + data.choiceList[i].choice.topic + ' ('+data.choiceList[i].score+'分) </div>\n' +
                        ' <div class="choice-oper delete-choice"> </div>\n' +
                        '<div class="score_div"> </div><div class="choice-oper delete-choice"></div></div> <div class="choice-details1">  <div class="make-choice"> ' +
                        opent +
                        '                                       </div>\n' +
                        '                                   </div>\n' +
                        '                               </div>');
                } else if (data.choiceList[i].choice.type == 3) {//判断题
                    let correct = ""
                    if (data.choiceList[i].choice.correct == 1) correct = "正确"; else correct = "错误";
                    $("#choices").append('<div class="choice" choicecheck="0" choice_type="' + data.choiceList[i].choice.type + '"  choiceid="' + data.choiceList[i].choice.id + '" >\n' +
                        ' <div class="choice-title"><div class="choice-index">' + (i + 1) + '</div><div class="choice-name" >' + data.choiceList[i].choice.topic + ' ('+data.choiceList[i].score+'分)</div>\n' +
                        ' <div class="choice-oper delete-choice"></div>\n' +
                        '<div class="score_div">\n' +
                        '</div>' +
                        // ' <div class="choice-oper "><input type="checkbox" name="TestPaper"/></div>\n' +
                        '\t\t\t</div>\n' +
                        '\t\t\t<div class="choice-details1">\n' +
                        '\t\t\t\t<div class="make-choice">\n' +
                        '\t\t\t\t\t<div class="input-cho"><input class="single" type="radio" name="' + data.choiceList[i].choice.id + '" value="1" />正确</div>\n' +
                        '\t\t\t\t\t<div class="input-cho"><input class="single" type="radio" name="' + data.choiceList[i].choice.id + '" value="0" />错误</div>\n' +
                        '\t\t\t\t</div>\n' +

                        '\t\t\t</div>\n' +
                        '\t\t</div>');
                } else if (data.choiceList[i].choice.type == 4) {//简答题
                    $("#choices").append('<div class="choice" choicecheck="0" fileUrl=""  choice_type="' + data.choiceList[i].choice.type + '" choiceid="' + data.choiceList[i].choice.id + '">\n' +
                        ' <div class="choice-title"><div class="choice-index">' + (i + 1) + '</div><div class="choice-name" >' + data.choiceList[i].choice.topic + '('+data.choiceList[i].score+'分)</div>\n' +
                        ' <div class="choice-oper delete-choice"> </div>\n' +
                        '<div class="score_div">\n' +
                        '</div>' +
                        '\t\t\t</div>\n' +
                        '\t\t\t<div class="choice-details1">\n' +
                        '\t\t\t\t<div class="make-choice">\n' +
                        '\t\t\t\t\t<div class="text-cho"><textarea rows="3" cols="50" wrap="hard"  id="' + data.choiceList[i].choice.id + '"  placeholder="请输入参考答案"></textarea></div>\n' +
                        '<div style="float: left"> <a  class="btn btn-link" onclick="DownloadAttachment(' + data.choiceList[i].choice.id + ')" >附件</a></div>  ' +
                        '<div style="float: left"><input  type="file" class="selected-btn"  onchange="studentUp(this)" style="display: none;" /> <a class="btn btn-link" onclick="uploadFile(this)">上传附件</a> </div>' +
                        ' <div class="uploading-done pull-left">  </div> ' +
                        '\t\t\t\t</div>\n' +
                        '\t\t\t</div>\n' +
                        '\t\t</div>');
                }
            }
            sortRef();
            addRespondents();
            $(".navigation_title").append('  <h5>总分：'+score+'</h5>');

        }
    }

    function init() {
        $.ajax({
            url: "/Assess/studentChoice",
            type: "post",
            data: {assessId: $("#assessId").attr("assessId")},
            dataType: "json",
            success: function (data) {
                console.log(data);
                information(data);
            },
            error: function (data) {
                console.log("服务器异常");
            }
        })
    }


});

/**
 * 添加试题到弹出框
 */
function binds() {
    let choices = $(".choice");
    var assessId = $("#assessId").attr("assessId");
    var respondentsId = $("#respondentsId").val();
    let flag=true;
    choices.each(function (i, choice) {
        if(!flag) return;
        var type = $(choice).attr("choice_type")
        var id = $(choice).attr("choiceid");
        var value = "";
        var num = $(choice).attr("c_index");
        var fileIds = $(choice).attr("fileUrl");
        if(fileIds!="" && fileIds!=undefined){
            fileIds=fileIds.substring(0,fileIds.length-1);
        }else{
            fileIds="";
        }
        switch (type) {
            case "1":
                var radio = document.getElementsByName(id);
                for (var i = 0; i < radio.length; i++) {
                    if (radio[i].checked == true) {
                        value = toString(radio[i].value);
                        break;
                    }
                }
                value = substrings(value);
                break;
            case "2":
                var radio = document.getElementsByName(id);
                for (var i = 0; i < radio.length; i++) {
                    if (radio[i].checked == true) {
                        value += toString(radio[i].value);
                    }
                }
                value = substrings(value);
                break;
            case "3":
                var radio = document.getElementsByName(id);
                for (var i = 0; i < radio.length; i++) {
                    if (radio[i].checked == true) {
                        value = radio[i].value;
                        break;
                    }
                }
                break;
            case "4":
                value = document.getElementById(id).value;

                break;
        }
        if (value == "") {
            flag=false;
            if (confirm("还有题目未作答确定要提交吗")==true){
                flag=true;
                return true;
            }else{
                return false;
            }
            // $.confirm({
            //     confirmButtonClass: 'btn-info',
            //     cancelButtonClass: 'btn-danger',
            //     title: '提示',
            //     content: '还有题目未作答确定要提交吗',
            //     confirm: function () {
            //         flag=true;
            //     },
            //     cancel: function () {
            //         $("html,body").animate({
            //             scrollTop: choices.eq(parseInt(num)).offset().top - 40
            //         }, 200 /*scroll实现定位滚动*/); /*让整个页面可以滚动*/
            //          flag=false;
            //         return ;
            //     }
            // });
        }
        let Answer = {
            "choiceId": id,
            "respondentsId": respondentsId,
            "answer": value,
            "fileIds":fileIds
        }
        answerList.push(Answer);
    });
    if(!flag){
        return;
    }

     $.ajax({
        url:"Answer/save",
        type:"Post",
        contentType:"application/json;charset=UTF-8",
         sync:false,
       data:JSON.stringify(answerList),
         dataType: "json",
        success:function (data) {
             console.log(data);
            window.location.href="javascript:history.go(-1)";
         },
         error:function (data) {
            console.log("服务器异常");
         }
     })
}

function toString(a) {
    switch (a) {
        case "0":
            return "A,";
            break;
        case "1":
            return "B,";
            break;
        case "2":
            return "C,";
            break;
        case "3":
            return "D,";
            break;
        case "4":
            return "E,";
            break;
        case "5":
            return "F,";
            break;
        case "6":
            return "G,"
            break;

    }


}

function substrings(str) {

    return str.slice(0, str.length - 1);
}

function DownloadAttachment(id) {
    $.ajax({
        url: "choice/findOneById",
        type: "post",
        data: {"id": id},
        success: function (data) {
            let str = data.fileIds.split(",");
            for (let i = 0; i < str.length; i++) {
                if(str[i]!="" && str[i]!=null){
                    win.location.href = "/file/downloadShareFile?fileId=" + str[i];
                }else {
                    $.alert("没有附件");
                }

            }
        },
        error: function () {
            console.log("服务器异常");
        }

    })
}

var files = [];
var upBtnIndex = 0;

function studentUp(th) {
    let sindex = 0;
    sindex = $(th).parent().parent().find('.uploading-done').find('.line-box').length;
    var max = 100;
    var init = 0;
    var uploaded;
    var formData = new FormData();
    console.log($(th)[0].files[0]);
    formData.append("file", $(th)[0].files[0]);
    $.ajax({
        url: "/Answer/upload",
        type: 'POST',
        async: false,
        data: formData,
        // 告诉jQuery不要去处理发送的数据
        processData: false,
        // 告诉jQuery不要去设置Content-Type请求头
        contentType: false,
        beforeSend: function () {
            console.log("正在进行，请稍候");
        }, success: function (responseStr) {
            if (responseStr != "" && responseStr != "上传失败") {
                let value = $(th).parent().parent().parent().parent().attr("fileUrl");
                value = value + responseStr + ",";
                $(th).parent().parent().parent().parent().attr('fileUrl', value);
                $(th).parent().parent().find('.uploading-done').show();
                $(th).parent().parent().find('.uploading-done').append('<div class="line-box clearfix"  fileUrl="' + responseStr + '"  ><div class="documnet-icon pull-left"></div><div class="document-headline pull-left"><div class="documnet-text pull-left">' + $(th)[0].files[0].name + '</div><div class="delete-icon  pull-right" onclick="removeFile(this)">+</div><div class="progress-line-back"><div class="progress-line" style="width: ' + init + '%;"></div></div></div></div></div>')
                var test = setInterval(function () {
                    init += 5;
                    uploaded = init + '%';
                    $(th).parent().parent().find('.uploading-done').find('.line-box').eq(sindex).find('.progress-line').css({width: uploaded});
                    if (init === 100) {
                        clearInterval(test);
                        $(th).parent().parent().find('.uploading-done').find('.line-box').eq(sindex).find('.progress-line').hide();
                    }
                }, 100);

            } else {
                $.alert(responseStr);
            }


        }
    });

}

function uploadFile(fie) {
    $(fie).parent().children("input").trigger("click");
}

var upBtnIndex = 0;
$('.selected-btn').change(function () {

});
function removeFile(th) {
    let str1 = $(th).parent().parent().parent().parent().parent().parent().attr("fileUrl")
    str1=str1.substring(0,str1.length-1)
    let arr = str1.split(",");
    let str = $(th).parent().parent().attr("fileUrl");
    if (arr.indexOf(str) >= 0) {
        arr.splice(arr.indexOf(str), 1);
    }
    let value = "";
    for (let i = 0; i < arr.length; i++) {
        value += arr[i] + ",";
    }
    $(th).parent().parent().parent().parent().parent().parent().attr("fileUrl", value)
    $(th).parent().parent().remove();
}
