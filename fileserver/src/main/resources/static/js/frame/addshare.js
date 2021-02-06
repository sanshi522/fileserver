// JavaScript Document
/* exported addfile */
$(function(){
    //获取用户身份
    var logintype;
    //上传共享级别
    var upident;
    //根据登录身份初始化页面元素--------------------------------------
    $.ajax({
        url:"login/Islogin",
        type:"post",
        dataType:"json",
        success:function(data) {
            if (data.resoult){
                //根据登陆账户的类型上传文件权限级别
                $("#queryLevels").empty();
                if (data.logintype == 0) {//学生
                    logintype=data.logintype;
                    $("#queryLevels").append(' ' +
                        '<option value=1 >学年</option>\n' +
                        '<option value=2 >学院</option>\n' +
                        '<option value=3 >班级</option>\n' +
                        '<option value=4 >小组</option>\n' +
                        '<option value=5 >同学</option>\n' +
                        '<option value=6 >老师</option>');
                } else if (data.user.teaIdentity == 1) {//老师
                    logintype=data.user.teaIdentity;
                    $("#queryLevels").append('  ' +
                        '<option value=1 >学年</option>\n' +
                        '<option value=2 >学院</option>\n' +
                        '<option value=3 >班级</option>\n' +
                        '<option value=4 >小组</option>\n' +
                        '<option value=5 >学生</option>\n' +
                        '<option value=6 >老师</option>\n' +
                        '<option value=7 >管理员</option>')
                } else if (data.user.teaIdentity == 2 || data.user.teaIdentity == 3) {//管理员老师 及 管理员
                    logintype=data.user.teaIdentity;
                    $("#queryLevels").append('  ' +
                        '<option value=1 >学年</option>\n' +
                        '<option value=2 >学院</option>\n' +
                        '<option value=3 >班级</option>\n' +
                        '<option value=4 >小组</option>\n' +
                        '<option value=5 >学生</option>\n' +
                        '<option value=6 >老师</option>\n' +
                        '<option value=7 >管理员</option>')
                }
                $('#queryLevels').selectpicker('refresh');
                $('#queryLevels').val(1).trigger("change");
            }else {
                parent.location.href = "/";
            }
        },
        error:function(jqXHR){
            alert("发生错误："+ jqXHR.status);
        }
    });
    $("#queryLevels").change(function() {
        $("#checkAll").prop("checked",false);//全选按钮取消全选
        upident = $("#queryLevels").val();
        //切换则查询
        //根据权限级别显示二级筛选框
        $("#selecttarget").empty();
        if (logintype == 0) {//（登陆方式为学生/查询级别所有）
            $("#yearScreenDiv").css("display", "none");
            if (upident != '1')
                $("#yearScreenDiv").css("display", "block");
            $("#gradeScreenDiv").css("display", "none");
            $("#classScreenDiv").css("display", "none");
            $("#groupScreenDiv").css("display", "none");
        } else {//老师/管理员：按顺序做出选择 通过身份获取
            if (upident != '1')
                $("#yearScreenDiv").css("display", "block");
            if (upident == '7' || upident == '1')
                $("#yearScreenDiv").css("display", "none");
            $("#gradeScreenDiv").css("display", "none");
            $("#classScreenDiv").css("display", "none");
            $("#groupScreenDiv").css("display", "none");
        }
        if (upident != 7) {
            //获取该身份下的学年
            $.ajax({
                url: "Grade/GetYear",
                type: "post",
                dataType: "json",
                success: function (data) {
                    if (data.resoult) {
                        if (upident != 1) {
                            $("#yearScreen").empty();
                            if (logintype == 0) {
                                $("#yearScreen").append('<option value="' + data.year.year + '">' + data.year.year + '</option>');
                                $('#yearScreen').selectpicker('refresh');
                                $('#yearScreen').val(data.year.year).trigger("change");
                            } else {
                                for (let i = 0; i < data.years.length; i++) {
                                    $("#yearScreen").append('<option value="' + data.years[i] + '">' + data.years[i] + '</option>');
                                }
                                $('#yearScreen').selectpicker('refresh');
                                $('#yearScreen').val(data.years[0]).trigger("change");
                            }
                        } else {
                            $("#selecttarget").empty();
                            if (logintype == 0) {
                                $("#selecttarget").append('<tr class="target" ><td><input data_id=' + data.year.year +' name="checkItem" type="checkbox" /></td><td>' + data.year.year + '</td></tr>');
                            } else {
                                $("#selecttarget").empty();
                                for (let i = 0; i < data.years.length; i++) {
                                    $("#selecttarget").append('<tr class="target" ><td><input data_id=' + data.years[i] +' name="checkItem" type="checkbox" /></td><td>' + data.years[i] + '</td></tr>');
                                }
                            }
                            checkItemAddBindClick();
                        }
                    }
                },
                error: function (data) {
                    console.log("获取学年服务器错误")
                }
            });
        } else {
            //获取所有管理员
            $.ajax({
                url: "Teacher/GetAdmin",
                type: "post",
                dataType: "json",
                success: function (data) {
                    if (data.resoult) {
                        $("#selecttarget").empty();
                        for (let i = 0; i < data.teachers.length; i++) {
                            $("#selecttarget").append('<tr class="target" ><td><input data_id=' + data.teachers[i].teaId +' name="checkItem" type="checkbox" /></td><td>' + data.teachers[i].teaName + '</td></tr>');
                        }
                        checkItemAddBindClick();
                    }
                },
                error: function (data) {
                    console.log("获取管理员服务器错误")
                }
            });
        }
    });
    //根据上传授权条件展示筛选框
    var paretid;
    $("#yearScreen").change(function () {
        paretid=$("#yearScreen").val();
        $("#gradeScreenDiv").css("display","none")
        $("#classScreenDiv").css("display","none");
        $("#groupScreenDiv").css("display","none");
        if (upident!=2)
            $("#gradeScreenDiv").css("display","block")
        //查询学年下的学院
        $.ajax({
            url:"Grade/GetGrade",
            type:"post",
            data: {yearNumber: paretid},
            dataType:"json",
            success:function(data) {
                if (data.resoult){
                    if (upident!=2){
                        $("#gradeScreen").empty();
                        if (logintype==0){
                            $("#gradeScreen").append('<option value="'+data.grade.id+'">'+data.grade.name+'</option>');
                            $('#gradeScreen').selectpicker('refresh');
                            $('#gradeScreen').val(data.grade.id).trigger("change");
                        }else{
                            for (let i=0;i<data.grades.length;i++){
                                $("#gradeScreen").append('<option value="'+data.grades[i].id+'">'+data.grades[i].name+'</option>');
                            }
                            $('#gradeScreen').selectpicker('refresh');
                            $('#gradeScreen').val(data.grades[0].id).trigger("change");
                        }
                    }else{
                        $("#selecttarget").empty();
                        if (logintype==0){
                            $("#selecttarget").append('<tr class="target" ><td><input data_id='+ data.grade.id+' name="checkItem" type="checkbox" /></td><td>'+data.grade.name+'</td></tr>');
                        }else{
                            $("#selecttarget").empty();
                            for (let i=0;i<data.grades.length;i++){
                                $("#selecttarget").append('<tr class="target" ><td><input data_id='+data.grades[i].id+' name="checkItem" type="checkbox" /></td><td>'+data.grades[i].name+'</td></tr>');
                            }
                        }
                        checkItemAddBindClick();
                    }
                }
            },
            error:function(data){
                console.log("获取院系服务器错误")
            }
        });
    });
    $("#gradeScreen").change(function () {
        paretid=$("#gradeScreen").val();
        $("#classScreenDiv").css("display","none");
        $("#groupScreenDiv").css("display","none");
        if (upident!=3)
            $("#classScreenDiv").css("display","block");
        $.ajax({
            url:"Class/GetClass",
            type:"post",
            data: {GradeId: paretid},
            dataType:"json",
            success:function(data) {
                if (data.resoult){
                    if (upident!=3){
                        $("#classScreen").empty();
                        if (logintype==0){
                            $("#classScreen").append('<option value="'+data.cclass.id+'">'+data.cclass.name+'</option>');
                            $('#classScreen').selectpicker('refresh');
                            $('#classScreen').val(data.cclass.id).trigger("change");
                        }else{
                            for (let i=0;i<data.clases.length;i++){
                                $("#classScreen").append('<option value="'+data.clases[i].id+'">'+data.clases[i].name+'</option>');
                            }
                            $('#classScreen').selectpicker('refresh');
                            $('#classScreen').val(data.clases[0].id).trigger("change");
                        }
                    }else{
                        $("#selecttarget").empty();
                        if (logintype==0){
                            $("#selecttarget").append('<tr class="target" ><td><input data_id='+ data.cclass.id+' name="checkItem" type="checkbox" /></td><td>'+data.cclass.name+'</td></tr>');
                        }else{
                            for (let i=0;i<data.clases.length;i++){
                                $("#selecttarget").append('<tr class="target" ><td><input data_id='+data.clases[i].id+' name="checkItem" type="checkbox" /></td><td>'+data.clases[i].name+'</td></tr>');
                            }
                        }
                        checkItemAddBindClick();
                    }
                }
            },
            error:function(data){
                console.log("获取班级服务器错误")
            }
        });
    });
    $("#classScreen").change(function () {
        paretid=$("#classScreen").val();
        $("#groupScreenDiv").css("display","none");
        if (upident!=4 && upident!=6)
            $("#groupScreenDiv").css("display","block");
        if (upident!=6){
            //查询班级下的小组
            $.ajax({
                url:"Group/GetGroup",
                type:"post",
                data: {CclassId: paretid},
                dataType:"json",
                success:function(data) {
                    if (data.resoult) {
                        if (upident != 4) {
                            $("#groupScreen").empty();
                            if (logintype == 0) {
                                $("#groupScreen").append('<option value="' + data.Group.id + '">' + data.Group.name + '</option>');
                                $('#groupScreen').selectpicker('refresh');
                                $('#groupScreen').val(data.Group.id).trigger("change");
                            } else {
                                for (let i = 0; i < data.Groups.length; i++) {
                                    $("#groupScreen").append('<option value="' + data.Groups[i].id + '">' + data.Groups[i].name + '</option>');
                                }
                                $('#groupScreen').selectpicker('refresh');
                                $('#groupScreen').val(data.Groups[0].id).trigger("change");
                            }
                        } else {
                            $("#selecttarget").empty();
                            if (logintype == 0) {
                                $("#selecttarget").append('<tr class="target" ><td><input data_id=' + data.Group.id +' name="checkItem" type="checkbox" /></td><td>' + data.Group.name + '</td></tr>');
                            } else {
                                for (let i = 0; i < data.Groups.length; i++) {
                                    $("#selecttarget").append('<tr class="target" ><td><input data_id=' + data.Groups[i].id +' name="checkItem" type="checkbox" /></td><td>' + data.Groups[i].name + '</td></tr>');
                                }
                            }
                            checkItemAddBindClick();
                        }
                    }
                },
                error:function(data){
                    console.log("获取小组服务器错误")
                }
            });
        }
        if (upident==6){
            $.ajax({
                url:"Teacher/GetTeacher",
                type:"post",
                data: {classId: paretid},
                dataType:"json",
                success:function(data) {
                    if (data.resoult){
                        $("#selecttarget").empty();
                        for (let i = 0; i < data.teachers.length; i++) {
                            $("#selecttarget").append('<tr class="target" ><td><input data_id=' + data.teachers[i].teaId +' name="checkItem" type="checkbox" /></td><td>' + data.teachers[i].teaName + '</td></tr>');
                        }
                        checkItemAddBindClick();
                    }
                },
                error:function(data){
                    console.log("获取老师服务器错误")
                }
            });
        }
    });
    $("#groupScreen").change(function () {
        paretid=$("#groupScreen").val();
        //查询小组下的学生
        $.ajax({
            url:"Student/GetStudent",
            type:"post",
            data: {StuGroup: paretid},
            dataType:"json",
            success:function(data) {
                if (data.resoult){
                    $("#selecttarget").empty();
                    for (let i = 0; i < data.students.length; i++) {
                        $("#selecttarget").append('<tr class="target" ><td><input data_id=' + data.students[i].stuId + ' name="checkItem" type="checkbox" /></td><td>' + data.students[i].stuName + '</td></tr>');
                    }
                    checkItemAddBindClick();
                }
            },
            error:function(data){
                console.log("获取学生服务器错误")
            }
        });
    });
    initadd();
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
    checkItemAddBindClick();
    //上传按钮
    $('#submit').bind("click", function () {
        if($(".file").length==0){
            alert("请添加文件");
            return;
        }
        let checknum=0;
        $('input:checkbox[name="checkItem"]').each(function(){
            if (this.checked){
                checknum++;
            }
        });
        if(checknum==0){
            alert("请选择共享目标");
            return;
        }
        $(".file").each(function(){
            var file=$(this).children(".inp_file")[0].files[0];
           // alert($(this).html());
            let talk={};
            talk["type"]=0;
            talk["upfile"]=file;
            talk["ident"]=upident;
            talk["id"]=[];
            $('input:checkbox[name="checkItem"]').each(function(){
                if (this.checked){
                    talk.id.push($(this).attr("data_id"));
                }
            });
            parent.frames["uploadiframe"].addtalk(talk);//添加文件上传任务
            this.remove();
        });
    });
});
function checkItemAddBindClick(){
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
};
//初始化绑定事件按钮
function initadd(){
    $(".ui.filelist").append("<div  class='addfile'>"+
        "<input type='file' class='inp_file' class='inp_file' style='display: none;'>"+
        "<i id='addfilebtn' class='my-icon lsm-sidebar-icon icon-tianjia'></i>"+
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


