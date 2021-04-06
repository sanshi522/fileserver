
$(function () {
    var  testPaperid=$("#testPaper").attr("testPaperId");
    if (testPaperid!=0){
        init();
    }
    function init() {
        $.ajax({
            url: "/choice/findOneById",
            type: "post",
            data:{id: 3},
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

});

function   information(data) {
     if(data==null){
         return  false;
     }else {
         let difficul="";
         for(let i=0;i>data.length;i++){
             difficul="";
             for (let k=0;k<5;k++){
                 if (k<data.page.content[i].difficultyLevel)
                     difficul+='<label style="color: #85822b;font-size: 14px;">★</label>';
                 else
                     difficul+='<label style="color: #9d9ea3;font-size: 14px;">★</label>';
             }
             if(data.page.content[i].type==1||data.page.content[i].type==2){//单选题和多选
                 let type="";
                 let opent="";
                 if (data.page.content[i].type==1)type="单选题";else type="多选题"
                 for (let o=0;o<data.page.content[i].optionNum;o++){
                     let op='';
                     switch (o) {
                         case 0:op="A."+data.page.content[i].optionA;break;
                         case 1:op="B."+data.page.content[i].optionB;break;
                         case 2:op="C."+data.page.content[i].optionB;break;
                         case 3:op="D."+data.page.content[i].optionD;break;
                         case 4:op="E."+data.page.content[i].optionE;break;
                         default:op="F."+data.page.content[i].optionF;break;
                     }
                     if (data.page.content[i].type==1)
                         opent+='<div class="input-cho"><input class="single" type="radio" name="single1" />'+op+'</div>\n';
                     else
                         opent+='<div class="input-cho"><input class="single" type="checkbox" name="single1" />'+op+'</div>\n';
                 }

                 $(".allchoice").append('<div class="choice" choiceid="'+data.page.content[i].id+'">\n'+
                     '<div class="choice-title"><div class="choice-type" data="'+data.page.content[i].type+'">'+type+'</div><div class="choice-name" >'+data.page.content[i].topic+'</div>\n' +
                     '<div class="choice-oper pack-up"><i class="my-icon lsm-sidebar-icon  icon-jiantouxia"></i></div>\n' +
                     '<div class="choice-oper delete-choice"><i class="my-icon lsm-sidebar-icon icon-shanchu "></i></div>\n' +
                     '<div class="choice-oper edit-choice"><i class="my-icon lsm-sidebar-icon icon-bianji "></i></div>\n' +
                     '</div>\n' +
                     '<div class="choice-details">\n' +
                     '<div class="make-choice">'+opent+'</div>\n'+
                     '<div class="answer">标答：'+data.page.content[i].correct+'</div>\n' +
                     '<div class="difficulty">难度：'+difficul+'</div>\n' +
                     '<div class="analysis">解析：'+data.page.content[i].analysis+'</div>\n' +
                     '<div class="knowledge">知识点：</div>\n' +
                     '</div>\n' +
                     '</div>');
             }else if(data.page.content[i].type==3){//判断题
                 let correct=""
                 if (data.page.content[i].correct==1) correct="正确";else correct="错误";
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
                     '\t\t\t\t<div class="analysis">解析：'+data.page.content[i].analysis+'</div>\n' +
                     '\t\t\t\t<div class="knowledge">知识点：</div>\n' +
                     '\t\t\t</div>\n' +
                     '\t\t</div>');
             }else if(data.page.content[i].type==4){//简答题
                 $(".allchoice").append('<div class="choice"  choiceid="'+data.page.content[i].id+'">\n' +
                     '\t\t\t<div class="choice-title"><div class="choice-type" data="'+data.page.content[i].type+'">简答题</div><div class="choice-name" >'+data.page.content[i].topic+'</div>\n' +
                     '\t\t\t\t<div class="choice-oper pack-up"><i class="my-icon lsm-sidebar-icon  icon-jiantouxia"></i></div>\n' +
                     '\t\t\t\t<div class="choice-oper delete-choice"><i class="my-icon lsm-sidebar-icon icon-shanchu "></i></div>\n' +
                     '\t\t\t\t<div class="choice-oper edit-choice"><i class="my-icon lsm-sidebar-icon icon-bianji "></i></div>\n' +
                     '\t\t\t</div>\n' +
                     '\t\t\t<div class="choice-details">\n' +
                     '\t\t\t\t<div class="make-choice">\n' +
                     '\t\t\t\t\t<div class="text-cho"><textarea rows="3" cols="50" wrap="hard"  placeholder="请输入参考答案"></textarea></div>\n' +
                     '\t\t\t\t</div>\n' +
                     '\t\t\t\t<div class="answer">标答：'+data.page.content[i].correct+'</div>\n' +
                     '\t\t\t\t<div class="difficulty">难度：'+difficul+'</div>\n' +
                     '\t\t\t\t<div class="analysis">解析：'+data.page.content[i].analysis+'</div>\n' +
                     '\t\t\t\t<div class="knowledge">知识点：</div>\n' +
                     '\t\t\t</div>\n' +
                     '\t\t</div>');
             }


         }

     }



}
