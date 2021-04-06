
$(function () {

    var  testPaperid=$("#testPaper").attr("testPaperId");
   // if (testPaperid!=0){
        init();
   // }
    function init() {
        $.ajax({
            url: "/testPaper/read",
            type: "post",
            data:{testPaperId: 1},
            dataType: "json",
            success: function(data) {
                console.log(data);
                information(data);
            },
            error: function(data){
            console.log("服务器异常");
        }
        })
    }

    $(".pack-up").bind("click",function(){
        var bi=$(this).parent().parent().children(".choice-details").css("display")=="none"?"none":"block";
        $(".choice-details").css("display","none");
        $(this).parent().parent().children(".choice-details").css("display",bi=="none"?"block":"none");
        $(".pack-up").children("i").removeClass("icon-jiantoushang");
        $(".pack-up").children("i").addClass("icon-jiantouxia");
        if($(this).parent().parent().children(".choice-details").css("display")=="block"){
            $(this).children("i").removeClass("icon-jiantouxia");
            $(this).children("i").addClass("icon-jiantoushang");
        }
    });

});

function   information(data) {
     if(data==null){
         return  false;
     }else {
         let difficul="";
         for(let i=0;i>data.length;i++){
             difficul="";
             for (let k=0;k<5;k++){
                 if (k<data[i].difficultyLevel)
                     difficul+='<label style="color: #85822b;font-size: 14px;">★</label>';
                 else
                     difficul+='<label style="color: #9d9ea3;font-size: 14px;">★</label>';
             }
             if(data[i].type==1||data[i].type==2){//单选题和多选
                 let type="";
                 let opent="";
                 if (data[i].type==1)type="单选题";else type="多选题"
                 for (let o=0;o<data[i].optionNum;o++){
                     let op='';
                     switch (o) {
                         case 0:op="A."+data[i].optionA;break;
                         case 1:op="B."+data[i].optionB;break;
                         case 2:op="C."+data[i].optionB;break;
                         case 3:op="D."+data[i].optionD;break;
                         case 4:op="E."+data[i].optionE;break;
                         default:op="F."+data[i].optionF;break;
                     }
                     if (data[i].type==1)
                         opent+='<div class="input-cho"><input class="single" type="radio" name="single1" />'+op+'</div>\n';
                     else
                         opent+='<div class="input-cho"><input class="single" type="checkbox" name="single1" />'+op+'</div>\n';
                 }

                 $(".allchoice").append('<div class="choice" choiceid="'+data[i].id+'">\n'+
                     '<div class="choice-title"><div class="choice-type" data="'+data[i].type+'">'+type+'</div><div class="choice-name" >'+data[i].topic+'</div>\n' +
                     '<div class="choice-oper pack-up"><i class="my-icon lsm-sidebar-icon  icon-jiantouxia"></i></div>\n' +
                     '<div class="choice-oper delete-choice"><i class="my-icon lsm-sidebar-icon icon-shanchu "></i></div>\n' +
                     '<div class="choice-oper edit-choice"><i class="my-icon lsm-sidebar-icon icon-bianji "></i></div>\n' +
                     '</div>\n' +
                     '<div class="choice-details">\n' +
                     '<div class="make-choice">'+opent+'</div>\n'+
                     '<div class="answer">标答：'+data[i].correct+'</div>\n' +
                     '<div class="difficulty">难度：'+difficul+'</div>\n' +
                     '<div class="analysis">解析：'+data[i].analysis+'</div>\n' +
                     '<div class="knowledge">知识点：</div>\n' +
                     '</div>\n' +
                     '</div>');
             }else if(data[i].type==3){//判断题
                 let correct=""
                 if (data[i].correct==1) correct="正确";else correct="错误";
                 $(".allchoice").append('<div class="choice"  choiceid="'+data.page.content[i].id+'">\n' +
                     '\t\t\t<div class="choice-title"><div class="choice-type" data="'+data.page.content[i].type+'">判断题</div><div class="choice-name" >'+data.page.content[i].topic+'</div>\n' +
                     '\t\t\t\t<div class="choice-oper pack-up"><i class="my-icon lsm-sidebar-icon  icon-jiantouxia"></i></div>\n' +
                     '\t\t\t\t<div class="choice-oper delete-choice"><i class="my-icon lsm-sidebar-icon icon-shanchu "></i></div>\n' +
                     '\t\t\t\t<div class="choice-oper edit-choice"><i class="my-icon lsm-sidebar-icon icon-bianji "></i></div>\n' +
                     '\t\t\t</div>\n' +
                     '\t\t\t<div class="choice-details">\n' +
                     '\t\t\t\t<div class="make-choice">\n' +
                     '\t\t\t\t\t<div class="input-cho"><input class="single" type="radio" name="single3" />正确</div>\n' +
                     '\t\t\t\t\t<div class="input-cho"><input class="single" type="radio" name="single3" />错误</div>\n' +
                     '\t\t\t\t</div>\n' +
                     '\t\t\t\t<div class="answer">标答：'+correct+'</div>\n' +
                     '\t\t\t\t<div class="difficulty">难度：'+difficul+'</div>\n' +
                     '\t\t\t\t<div class="analysis">解析：'+data[i].analysis+'</div>\n' +
                     '\t\t\t\t<div class="knowledge">知识点：</div>\n' +
                     '\t\t\t</div>\n' +
                     '\t\t</div>');
             }else if(data[i].type==4){//简答题
                 $(".allchoice").append('<div class="choice"  choiceid="'+data[i].id+'">\n' +
                     '\t\t\t<div class="choice-title"><div class="choice-type" data="'+data[i].type+'">简答题</div><div class="choice-name" >'+data[i].topic+'</div>\n' +
                     '\t\t\t\t<div class="choice-oper pack-up"><i class="my-icon lsm-sidebar-icon  icon-jiantouxia"></i></div>\n' +
                     '\t\t\t\t<div class="choice-oper delete-choice"><i class="my-icon lsm-sidebar-icon icon-shanchu "></i></div>\n' +
                     '\t\t\t\t<div class="choice-oper edit-choice"><i class="my-icon lsm-sidebar-icon icon-bianji "></i></div>\n' +
                     '\t\t\t</div>\n' +
                     '\t\t\t<div class="choice-details">\n' +
                     '\t\t\t\t<div class="make-choice">\n' +
                     '\t\t\t\t\t<div class="text-cho"><textarea rows="3" cols="50" wrap="hard"  placeholder="请输入参考答案"></textarea></div>\n' +
                     '\t\t\t\t</div>\n' +
                     '\t\t\t\t<div class="answer">标答：'+data[i].correct+'</div>\n' +
                     '\t\t\t\t<div class="difficulty">难度：'+difficul+'</div>\n' +
                     '\t\t\t\t<div class="analysis">解析：'+data[i].analysis+'</div>\n' +
                     '\t\t\t\t<div class="knowledge">知识点：</div>\n' +
                     '\t\t\t</div>\n' +
                     '\t\t</div>');
             }
         }

     }

}

function  getsubtype(){
    $.ajax({
        url:"/subject/findAll",
        type:"post",
        data:{},
        dataType:"json",
        success: function(data) {
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
        error: function(data) {

        }
    });
}





function  save() {
    let TestPaperBindChoice={
        "indexNum":pageNumber,
        "choiceId":pageIndex,
        "testPaperId":subId,
        "score":choicetype,
    }
    $.ajax({
        url: "/TestPaperBindChoice/save",
        contentType:"application/json;charset=UTF-8",
        type: "post",
        data:JSON.stringify(TestPaperBindChoice),
        dataType: "json",
        success: function(data) {
            console.log(data);

        },
        error: function(data){
            console.log("服务器异常");
        }
    })
}


function del(id) {
    $.ajax({
        url: "/TestPaperBindChoice/delete",
        type: "post",
        data:{id:id},
        dataType: "json",
        error: function(data){
            console.log("服务器异常");
        }
    })

}

function  saveTestPaper() {
    let  name=$("#testPaperName").val();
    let  subId=$("#subIdScreen option:selected").val();
    if(name !="" && name !=null){

    }else{
        console.log("试卷名不能为空");
        return false;
    }
    let TestPaper={
        "name":name,
        "subId":subId
    }
    $.ajax({
        url: "/testPaper/save",
        contentType:"application/json;charset=UTF-8",
        type: "post",
        data:JSON.stringify(TestPaper),
        dataType: "json",
        success: function(data) {
            console.log(data);
        },
        error: function(data){
            console.log("服务器异常");
        }
    })
}

function popup(){
    $("#myModalLabel").text("添加视题");
    $('#myModal').modal();
}
