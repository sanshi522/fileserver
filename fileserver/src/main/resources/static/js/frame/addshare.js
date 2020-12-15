// JavaScript Document
/* exported addfile */
$(function(){
    initadd();
    $("#channels21").css("display","none");
    $("#checkAll").bind("click", function () {
        var checkbox = document.getElementsByName("checkItem");
        if ($("#checkAll").is(':checked')) {//全选
            if(checkbox.length != 0)  //判断条件是当选择的个数不为0时
            {
                for(var i = 0; i < checkbox.length; i ++){
                    checkbox[i].checked=true;
                }
            }
        }
        else
        {//全不选
            if(checkbox.length != 0)  //判断条件是当选择的个数不为0时
            {
                for( i = 0; i < checkbox.length; i ++){
                    checkbox[i].checked=false;
                }
            }
        }
    });
    //共享目标选择按钮
    $('input:checkbox[name="checkItem"]').bind("click", function () {
        var checkbox = document.getElementsByName("checkItem");
        var exist=true;
        for(var i = 0; i < checkbox.length; i ++){
            if(checkbox[i].checked==false){
                exist=false;
            }
        }
        if(exist){
            $("#checkAll").prop("checked",true);
        }
        else{
            $("#checkAll").prop("checked",false);
        }

    });
    //上传按钮
    $('#submit').bind("click", function () {
        if($(".file").length==0) alert("请添加文件");
        if($("#channels1").val()==""){
            alert("请选择共享目标");
        }
        else if($("#channels1").val()=="2"){
            if($("#channels2").val()==""){
                alert("请选择共享目标");
            }
        }
        $(".file").each(function(){
            var file=$(this).children(".inp_file")[0].files[0];
           // alert($(this).html());
          parent.frames["uploadiframe"].addtalk(file);//返回文件id
           // var id=parent.frames["uploadiframe"].addtalk(file);//返回文件id
           // $(".target").each(function(){
            //循环入库共享授权表（排除授权重复的id相同，授权人相同，目标相同，文件相同）
               // alert("文件名："+file.name+"大小："+file.size+"分享到:"+$("#channels1").val()+"的"+$(this).attr("data_id"));
           // });
            this.remove();
        });
    });
    //第一级共享目标下拉框change事件
    $('#channels1').change(function(){
        if($("#channels1").val()=="2"){
            $("#channels21").css("display","block");
        }else{
            $("#channels21").css("display","none");
        }
    });

});
//初始化绑定事件按钮
function initadd(){
    $(".ui.filelist").append("<div  class='addfile'>"+
        "<input type='file' class='inp_file' class='inp_file' style='display: none;'>"+
        "<i id='addfilebtn' class='my-icon lsm-sidebar-icon icon-tianjiazengjiajia'></i>"+
        "</div>");
    //添加文件
    $("#addfilebtn").bind("click",function(){
        $("#addfilebtn").parent().children("input").trigger("click");
        //var sum=$("#inp_file").files.length;
    });
    $(".inp_file").bind("change",function(){
        if(this.files.length < 1) {
            return;
        }else{
            //新文件
            $(this).parent().children("i").remove();
            $(this).parent().removeClass("addfile");
            $(this).parent().addClass("file");
            $(this).parent().append("<img class='filetypeimg' src='images/file_logo_png/1140192.png'/>"+
                "<div class='sharefilename'>"+this.files[0].name+"</div>"+
                "<div class='delfile' >"+
                "<i class='my-icon lsm-sidebar-icon icon-shanchu' style='font-size: 20px;color:#B4DD33;''></i>"+
                "</div>");

            $(".delfile").bind("click",function(){
                $(this).parent().remove();
                //var sum=$("#inp_file").files.length;
            });
            //添加文件按钮并绑定事件
            initadd();
        }
    })
}


