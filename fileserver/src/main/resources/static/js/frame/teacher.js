$(function () {
    let nu=0;
    //请求条件
    $("#showNumber").val('10').trigger("change");
    var pageNumber = 10; // 每页显示多少条记录
    var pageIndex = 0;//页码
    var issistId = 0;
    var likeName = "";
    var total = 0; // 总共多少记录

    $("#query").val("");
    Init(0);

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
            "issistId": 0,
            "likeName": likeName == "" ? null : likeName
        }
        $.ajax({
            url: "/Teacher/findTeachers",
            //contentType: "application/json;charset=UTF-8",
            type: "post",
            async: false,
            data: val,
            dataType: "json",
            success: function (data) {
                dealNull(data);
                $("#teacher").children("tbody").empty();
                for (let i = 0; i < data.page.content.length; i++) {
                    dealNull(data.page.content[i]);
                    var identName = "";
                    $.ajax({
                        url: "/Teacher/GetIdentName",
                        type: "post",
                        async: false,
                        data: {val: data.page.content[i].teaIdentity},
                        dataType: "json",
                        success: function (da) {
                            identName = da.name
                        }
                    });
                    $("#teacher").children("tbody").append('<tr data_key="teaId" data_val="' + data.page.content[i].teaId + '" up="0">\n' +
                        '<td class="serial">' + (i + 1) + '</td>\n' +
                        '<td type="text" notnull="1" state="1" data_key="teaNumber" data_val="' + data.page.content[i].teaNumber + '">' + data.page.content[i].teaNumber + '</td>\n' +
                        '<td type="text" notnull="1" state="1" data_key="teaName" data_val="' + data.page.content[i].teaName + '">' + data.page.content[i].teaName + '</td>\n' +
                        '<td type="text" notnull="1" state="1" data_key="teaGender" data_val="' + data.page.content[i].teaGender + '">' + data.page.content[i].teaGender + '</td>\n' +
                        '<td type="text" notnull="1" state="1" data_key="teaPass" data_val="' + data.page.content[i].teaPass + '">' + data.page.content[i].teaPass + '</td>\n' +
                        '<td type="select" notnull="1" state="1" data_key="teaIdentity" data_val="' + data.page.content[i].teaIdentity + '" getlrc="/Teacher/GetteaIdentity" getval="" > ' + identName + '</td>\n' +
                        '<td type="text" state="1" data_key="teaRemake" data_val="' + data.page.content[i].teaRemake + '">' + data.page.content[i].teaRemake + '</td>\n' +
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
        Init(0);
    });

});

function tableadd() {
    var index;
    if ($("#teacher").children("tbody").children("tr:last").find("td").length == 0)
        index = 1;
    else
        index = ($("#teacher").children("tbody").children("tr:last").children("td:first").html() - 0) + 1;
    $("#teacher").children("tbody").append('<tr data_key="teaId" data_val="" up="0">\n' +
        '<td class="serial">' + index + '</td>\n' +
        '<td type="text" notnull="1" state="1" data_key="teaNumber" data_val=""></td>\n' +
        '<td type="text" notnull="1" state="1" data_key="teaName" data_val=""></td>\n' +
        '<td type="text" notnull="1" state="1" data_key="teaGender" data_val=""></td>\n' +
        '<td type="text" notnull="1" state="1" data_key="teaPass" data_val=""></td>\n' +
        '<td type="select" notnull="1" state="1" data_key="teaIdentity" data_val="" getlrc="/Teacher/GetteaIdentity" getval=""> </td>\n' +
        '<td type="text" state="1" data_key="teaRemake" data_val=""></td>\n' +
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
