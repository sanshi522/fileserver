var classid;
$(function () {
    $("#upload").on("click", function () {
        $("#myModal").modal("show");
    });
    //请求条件
    var pageNumber = 10; // 每页显示多少条记录
    var pageIndex = 0;//页码
    var issistId = 0;
    var likeName = "";
    var total = 0; // 总共多少记录
    Init(0);
    //获取该身份下的学年
    $.ajax({
        url: "Grade/GetYear",
        type: "post",
        dataType: "json",
        success: function (data) {
            if (data.resoult) {
                $("#yearScreen").empty();
                for (let i = 0; i < data.years.length; i++) {
                    $("#yearScreen").append('<option value="' + data.years[i] + '">' + data.years[i] + '</option>');
                }
                if (data.years.length > 0) {
                    $('#yearScreen').selectpicker('refresh');
                    $('#yearScreen').val(data.years[0]).trigger("change");
                }
            }
        },
        error: function (data) {
            console.log("获取学年服务器错误")
        }
    });
    //根据上传授权条件展示筛选框
    $("#yearScreen").change(function () {
        //查询学年下的学院
        $.ajax({
            url: "Grade/GetGrade",
            type: "post",
            data: {yearNumber: $("#yearScreen").val()},
            dataType: "json",
            success: function (data) {
                if (data.resoult) {
                    $("#gradeScreen").empty();
                    for (let i = 0; i < data.grades.length; i++) {
                        $("#gradeScreen").append('<option value="' + data.grades[i].id + '">' + data.grades[i].name + '</option>');
                    }
                    if (data.grades.length > 0) {
                        $('#gradeScreen').selectpicker('refresh');
                        $('#gradeScreen').val(data.grades[0].id).trigger("change");
                    }
                }
            },
            error: function (data) {
                console.log("获取院系服务器错误")
            }
        });
    });
    $("#gradeScreen").change(function () {
        $.ajax({
            url: "Class/GetClass",
            type: "post",
            data: {GradeId: $("#gradeScreen").val()},
            dataType: "json",
            success: function (data) {
                if (data.resoult) {
                    $("#classScreen").empty();
                    for (let i = 0; i < data.clases.length; i++) {
                        $("#classScreen").append('<option value="' + data.clases[i].id + '">' + data.clases[i].name + '</option>');
                    }
                    if (data.clases.length > 0) {
                        $('#classScreen').selectpicker('refresh');
                        $('#classScreen').val(data.clases[0].id).trigger("change");
                    }
                }
            },
            error: function (data) {
                console.log("获取班级服务器错误")
            }
        });
    });
    $("#classScreen").change(function () {
        likeName = "";
        $("#query").val("");
        $(".stugroup").attr("getval", $("#classScreen").val());
        classid = $("#classScreen").val();
        Init(0);
    });
    function Init(Index) { // 参数就是点击的那个分页的页数索引值
        pageIndex = Index;
        let val = {
            "pageNumber": pageNumber,
            "pageIndex": pageIndex,
            "issistId": classid,
            "likeName": likeName == "" ? null : likeName
        }
        $.ajax({
            url: "/Student/GetStudentByClassId",
            //contentType: "application/json;charset=UTF-8",
            type: "post",
            async: false,
            data: val,
            dataType: "json",
            success: function (data) {
                dealNull(data);
                $("#student").children("tbody").empty();
                for (let i = 0; i < data.page.content.length; i++) {
                    dealNull(data.page.content[i]);
                    let stuGroupName = "";
                    $.ajax({
                        url: "/Group/GetNameById",
                        type: "post",
                        async: false,
                        data: {val: data.page.content[i].stuGroup},
                        dataType: "json",
                        success: function (da) {
                            stuGroupName = da.name
                        }
                    });
                    $("#student").children("tbody").append('<tr data_key="stuId" data_val="' + data.page.content[i].stuId + '" up="0">\n' +
                        '<td class="serial">' + (i + 1) + '</td>\n' +
                        '<td type="text" notnull="1" state="1" data_key="stuNumber" data_val="' + data.page.content[i].stuNumber + '">' + data.page.content[i].stuNumber + '</td>\n' +
                        '<td type="text" notnull="1" state="1" data_key="stuName" data_val="' + data.page.content[i].stuName + '">' + data.page.content[i].stuName + '</td>\n' +
                        '<td type="text" notnull="1" state="1" data_key="stuGender" data_val="' + data.page.content[i].stuGender + '">' + data.page.content[i].stuGender + '</td>\n' +
                        '<td type="text" notnull="1" state="1" data_key="stuPass" data_val="' + data.page.content[i].stuPass + '">' + data.page.content[i].stuPass + '</td>\n' +
                        '<td type="select" notnull="1" state="1" data_key="stuGroup" data_val="' + data.page.content[i].stuGroup + '" getlrc="/Group/GetGroupByClassId" getval="' + classid + '" class="stugroup" > ' + stuGroupName + '</td>\n' +
                        '<td type="text" state="1" data_key="stuRemake" data_val="' + data.page.content[i].stuRemake + '">' + data.page.content[i].stuRemake + '</td>\n' +
                        '<td style="color: red;font-size: 20px;"><i class="my-icon lsm-sidebar-icon icon-shanchu del"></i></td>' +
                        '</tr>')
                }
                total = data.page.totalElements;
                tablebind();
            },
            error: function (data) {
                console.log("服务器异常");
            }
        });
    };
    //分页请求
    $("#Pagination").pagination(total, {
        callback: PageCallback,
        prev_text: '上一页',
        next_text: '下一页',
        items_per_page: pageNumber,
        num_display_entries: 4, // 连续分页主体部分显示的分页条目数
        num_edge_entries: 1, // 两侧显示的首尾分页的条目数
        jump: true,
    });
    //初始化单页显示条数
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



    function PageCallback(index, jq) { // 前一个参数表示当前点击的那个分页的页数索引值，后一个参数表示装载容器。
        pageIndex = index;
        Init(pageIndex);
        return true;
    }
    $(".querybtn").click(function () {
        likeName = "%" + $("#query").val() + "%";
        Init(0);
    });

    $(".btn-primary").on('click', function () {
        var formData = new FormData();
        var name = $("#f_upload").val();
        let cclassId = $("#classScreen").val();
        formData.append("file", $("#f_upload")[0].files[0]);
        formData.append("classId", cclassId);//这个地方可以传递多个参数
        $.ajax({
            url: "/exportExcel/importExcel",
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
                } else {
                    alert("导入失败");
                }
            }
        });
    });

    $("#showNumber").val('10').trigger("change");
});

function tableadd() {
    var index;
    if ($("#student").children("tbody").children("tr:last").find("td").length == 0)
        index = 1;
    else
        index = ($("#student").children("tbody").children("tr:last").children("td:first").html() - 0) + 1;
    $("#student").children("tbody").append('<tr data_key="stuId" data_val="" up="0">\n' +
        '<td class="serial">' + index + '</td>\n' +
        '<td type="text" notnull="1" state="1" data_key="stuNumber" data_val=""></td>\n' +
        '<td type="text" notnull="1" state="1" data_key="stuName" data_val=""></td>\n' +
        '<td type="text" notnull="1" state="1" data_key="stuGender" data_val=""></td>\n' +
        '<td type="text" notnull="1" state="1" data_key="stuPass" data_val=""></td>\n' +
        '<td type="select" notnull="1" state="1" data_key="stuGroup" data_val="" getlrc="/Group/GetGroupByClassId" getval="' + classid + '" class="stugroup" > </td>\n' +
        '<td type="text" state="1" data_key="stuRemake" data_val=""></td>\n' +
        '<td style="color: red;font-size: 20px;"><i class="my-icon lsm-sidebar-icon icon-shanchu del"></i></td>' +
        '</tr>');
    tablebind();
}

function dealNull(obj) {
    for (var i in obj) {
        if (null == obj[i] || 'null' == obj[i]) {
            obj[i] = '';
        } else if ('object' == typeof obj[i]) {
            dealNull(obj[i]);
        }
    }
};

function exportstudent() {
    let cclassId = $("#classScreen").val();
    if (cclassId == "" || cclassId == undefined || cclassId == null) {
        return;
    }
    window.location.href = "exportExcel/studentexportExcel?cclassId=" + cclassId;
}






