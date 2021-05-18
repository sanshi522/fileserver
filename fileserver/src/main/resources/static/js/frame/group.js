var classid;
var className;
$(function () {
    //请求条件
    let nu=0;
    $("#showNumber").val('10').trigger("change");
    var pageNumber = 10; // 每页显示多少条记录
    var pageIndex = 0;//页码
    var issistId = 0;
    var likeName = "";
    var total = 0; // 总共多少记录
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
                $('#yearScreen').selectpicker('refresh');
                $('#yearScreen').val(data.years[0]).trigger("change");
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
                    $('#gradeScreen').selectpicker('refresh');
                    $('#gradeScreen').val(data.grades[0].id).trigger("change");
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
                    $('#classScreen').selectpicker('refresh');
                    $('#classScreen').val(data.clases[0].id).trigger("change");
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
        className = $("#classScreen").children("option:selected").text()
        nu=0;
        Init(0);
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

    //分页请求
    //分页请求
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

    function PageCallback(index, jq) { // 前一个参数表示当前点击的那个分页的页数索引值，后一个参数表示装载容器。
        pageIndex = index;
        Init(pageIndex);
    }

    function Init(Index) { // 参数就是点击的那个分页的页数索引值
        pageIndex = Index;
        let val = {
            "pageNumber": pageNumber,
            "pageIndex": pageIndex,
            "issistId": classid,
            "likeName": likeName == "" ? null : likeName
        }
        $.ajax({
            url: "/Group/GetGroupsByClassId",
            //contentType: "application/json;charset=UTF-8",
            type: "post",
            async: false,
            data: val,
            dataType: "json",
            success: function (data) {
                dealNull(data);
                $("#group").children("tbody").empty();
                for (let i = 0; i < data.page.content.length; i++) {
                    dealNull(data.page.content[i]);
                    //  let stuGroupName="";
                    // $.ajax({url: "/Grade/GetNameById", type: "post", async: false, data: {val:data.page.content[i].stuGroup}, dataType: "json", success: function (da) {stuGroupName=da.name}});
                    $("#group").children("tbody").append('<tr data_key="id" data_val="' + data.page.content[i].id + '" up="0">\n' +
                        '<td class="serial">' + (i + 1) + '</td>\n' +
                        '<td type="text" notnull="1" state="1" data_key="name" data_val="' + data.page.content[i].name + '">' + data.page.content[i].name + '</td>\n' +
                        '<td type="notext" notnull="1" state="1" data_key="cclassId" data_val="' + data.page.content[i].cclassId + '">' + className + '</td>\n' +
                        '<td style="color: red;font-size: 20px;"><i class="my-icon lsm-sidebar-icon icon-shanchu del"></i></td>' +
                        '</tr>')
                }
                total = data.page.totalElements;
                $(".totalmsg").html("【共" + total + "条记录，当前显示：" + (data.page.pageable.pageNumber * data.page.pageable.pageSize + 1) + "~" + (data.page.pageable.pageNumber * data.page.pageable.pageSize + data.page.numberOfElements) + "】");
                tablebind();
                if(nu==0){
                    refresh()
                }
            },
            error: function (data) {
                console.log("服务器异常");
            }
        });
    };
    $(".querybtn").click(function () {
        likeName = "%" + $("#query").val() + "%";
        nu=0;
        Init(0);
    });

});

function tableadd() {
    var index;
    if ($("#group").children("tbody").children("tr:last").find("td").length == 0)
        index = 1;
    else
        index = ($("#group").children("tbody").children("tr:last").children("td:first").html() - 0) + 1;
    $("#group").children("tbody").append('<tr data_key="id" data_val="" up="0">\n' +
        '<td class="serial">' + index + '</td>\n' +
        '<td type="text" notnull="1" state="1" data_key="name" data_val=""></td>\n' +
        '<td type="notext" notnull="1" state="1" data_key="cclassId" data_val="' + classid + '">' + className + '</td>\n' +
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