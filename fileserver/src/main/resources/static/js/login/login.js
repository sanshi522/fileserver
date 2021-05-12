$(function () {
    // $('#login').click(function(){
    //     const format = new FormData();
    //     format.append("slumber", $("#user_number").val());
    //     format.append("pass", $("#password").val());
    //     format.append("identity", 0);
    //     $.ajax({
    //         url:"login",
    //         type:"POST",
    //         data:format,
    //         cache: false,
    //         processData: false,
    //         contentType: false,
    //         success:function(data){
    //             let obj2 = eval(data);
    //             if (obj2.code==1){
    //                 location.href = "/";
    //             }else{
    //                 $("#error_msg").val(obj2.msg);
    //             }
    //         }
    //     });
    // });
    // $('#login1').click(function(){
    //     const format = new FormData();
    //     format.append("slumber", $("#user_number1").val());
    //     format.append("pass", $("#password1").val());
    //     format.append("identity", 1);
    //     $.ajax({
    //         url:"login",
    //         type:"POST",
    //         data:format,
    //         cache: false,
    //         processData: false,
    //         contentType: false,
    //         success:function(data){
    //             let obj2 = eval(data);
    //             if (obj2.code==1)
    //                 //跳转页面
    //                 // location.assign(window.location.host);
    //                 location.href = "/";
    //             else
    //                 $("#error_msg").val(obj2.msg);
    //         }
    //     });
    // });
    $("input").focus(function () {
        $("#error_msg").val(null);
    })
});

function sub() {
    if (!verisy(0))
        return;
    const format = new FormData();
    format.append("slumber", $("#user_number").val());
    format.append("pass", $("#password").val());
    format.append("identity", 0);
    $.ajax({
        url: "login",
        type: "POST",
        data: format,
        cache: false,
        processData: false,
        contentType: false,
        success: function (data) {
            let obj2 = eval(data);
            if (obj2.code == 1) {
                location.href = "/";
            } else {
                $("#error_msg").val(obj2.msg);
            }
        }
    });
}

function sub1() {
    if (!verisy(1))
        return;
    const format = new FormData();
    format.append("slumber", $("#user_number1").val());
    format.append("pass", $("#password1").val());
    format.append("identity", 1);
    $.ajax({
        url: "login",
        type: "POST",
        data: format,
        cache: false,
        processData: false,
        contentType: false,
        success: function (data) {
            let obj2 = eval(data);
            if (obj2.code == 1)
                //跳转页面
                // location.assign(window.location.host);
                location.href = "/";
            else
                $("#error_msg").val(obj2.msg);
        }
    });
}

function StuClick() {
    $('.toggle_btn1').css("background", "#fff");
    $('.toggle_btn2').css("background", "#C9C2C2");
    $('.toggle_btn1').css("color", "#0d71bb");
    $('.toggle_btn2').css("color", "#fff");
    $('.from2').css("display", "none");
    $('.from1').css("display", "block");
}

function TeaClick() {
    $('.toggle_btn1').css("background", "#C9C2C2");
    $('.toggle_btn2').css("background", "#fff");
    $('.toggle_btn1').css("color", "#fff");
    $('.toggle_btn2').css("color", "#0d71bb");
    $('.from1').css("display", "none");
    $('.from2').css("display", "block");
}

function verisy(a) {
    if (a == 0) {
        if ($("#user_number").val().length > 0 && $("#password").val().length > 0) {
            return true;
        } else {
            if (!$("#user_number").val().length > 0) {
                $("#error_msg").val("请输入学号！");
                return false;
            } else {
                $("#error_msg").val("请输入密码！");
                return false;
            }
        }
    } else {
        if ($("#user_number1").val().length > 0 && $("#password1").val().length > 0) {
            return true;
        } else {
            if (!$("#user_number1").val().length > 0) {
                $("#error_msg").val("请输入姓名！");
                return false;
            } else {
                $("#error_msg").val("请输入密码！");
                return false;
            }
        }
    }
}

function onkey(e) {
    var keyCode = null;
    if (e.which)
        keyCode = e.which;
    else if (e.keyCode)
        keyCode = e.keyCode;
    if (keyCode == 13)
        sub();
}

function onkey1(e) {
    var keyCode = null;
    if (e.which)
        keyCode = e.which;
    else if (e.keyCode)
        keyCode = e.keyCode;
    if (keyCode == 13)
        sub1();
}