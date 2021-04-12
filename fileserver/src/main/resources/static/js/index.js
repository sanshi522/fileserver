$(function(){
    $.ajax({
        url:"login/Islogin",
        type:"post",
        dataType:"json",
        success:function(data) {
            if (data.resoult)
                document.getElementById("frameId").src = "/home";
            //根据登陆账户的类型初始化导航栏
            if (data.logintype == 0) {//学生
                $(".user_name").html(data.user.stuName);
                $(".stu-show").css("display", "block");
            } else {
                $(".user_name").html(data.user.teaName);
                if (data.user.teaIdentity == 1) {//老师
                    $(".tea-show").css("display", "block");
                } else if (data.user.teaIdentity == 2) {//管理员老师
                    //$(".tea-show").css("display", "block");
                    $(".man-show").css("display", "block");
                } else if (data.user.teaIdentity == 3) {//管理员
                    $(".man-show").css("display", "block");
                } else {
                    location.href = "/";
                }
            }
        },
        error:function(jqXHR){
            alert("发生错误："+ jqXHR.status);
        }
    })

    $(".close_div").click(function(){
        if (confirm("退出账号!")==true)
        {
            location.href = "exit";
        }else
            return;
    });
});
function  openshare(){
    $.ajax({
        url:"login/Islogin",
        type:"post",
        dataType:"json",
        success:function(data){
            if(data.resoult)
                document.getElementById("frameId").src="/share";
            else{
                location.href = "/";
            }
        },
        error:function(jqXHR){
            alert("发生错误："+ jqXHR.status);
        }
    })
}
function  openmyshare(){
        $.ajax({
            url:"login/Islogin",
            type:"post",
            dataType:"json",
            success:function(data){
                if(data.resoult)
                    document.getElementById("frameId").src="/myshare";
                else{
                    location.href = "/";
                }
            },
            error:function(jqXHR){
                alert("发生错误："+ jqXHR.status);
            }
        })
}
function  openaddshare(){
            $.ajax({
                url:"login/Islogin",
                type:"post",
                dataType:"json",
                success:function(data){
                    if(data.resoult)
                        document.getElementById("frameId").src="/addshare";
                    else{
                        location.href = "/";
                    }
                },
                error:function(jqXHR){
                    alert("发生错误："+ jqXHR.status);
                }
            })
}
function open(lrc){
    $.ajax({
        url:"login/Islogin",
        type:"post",
        dataType:"json",
        success:function(data){
            if(data.resoult){
                document.getElementById("frameId").src="/"+lrc;
            } else{
                location.href = "/";
            }
        },
        error:function(jqXHR){
            alert("发生错误："+ jqXHR.status);
        }
    })
}
//最小化上传窗口
function upload_minview(){
    $('.frame2').addClass("min-upload-frame");
    $('.frame2').removeClass("max-upload-frame");
}
//展开上传窗口
function upload_maxview() {
    $('.frame2').addClass("max-upload-frame");
    $('.frame2').removeClass("min-upload-frame");
}
//初始化导航栏

function say(){
    alert("parent.html");
}

