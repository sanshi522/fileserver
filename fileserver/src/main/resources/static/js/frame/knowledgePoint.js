var subid;
$(function () {
    //请求条件
    $("#showNumber").val('10').trigger("change");
    var pageNumber = 10; // 每页显示多少条记录
    var pageIndex = 0;//页码
    var issistId = 0;
    var likeName = "";
    var total = 0; // 总共多少记录
    //获取学科
    $.ajax({
        url: "/subject/findAll",
        type: "post",
        data: {},
        dataType: "json",
        success: function (data) {
            $("#subIdScreen").empty();
            $("#subIdScreen").append('<option value="0">全部学科</option>');
            for (let i = 0; i < data.length; i++) {
                $("#subIdScreen").append('<option value="' + data[i].id + '">' + data[i].name + '</option>');
            }
            $('#subIdScreen').selectpicker('refresh');
            $('#subIdScreen').val(0).trigger("change");
        },
        error: function (data) {

        }
    });
    $("#subIdScreen").change(function () {
        likeName = "";
        $("#query").val("");
        subid = $("#subIdScreen").val();
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
    $("#Pagination").pagination(total, {
        callback: PageCallback,
        prev_text: '上一页',
        next_text: '下一页',
        items_per_page: pageNumber,
        num_display_entries: 4, // 连续分页主体部分显示的分页条目数
        num_edge_entries: 1, // 两侧显示的首尾分页的条目数
        jump: true,
    });

    function PageCallback(index, jq) { // 前一个参数表示当前点击的那个分页的页数索引值，后一个参数表示装载容器。
        pageIndex = index;
        Init(pageIndex);
    }

    function Init(Index) { // 参数就是点击的那个分页的页数索引值
        pageIndex = Index;
        let val = {
            "pageNumber": pageNumber,
            "pageIndex": pageIndex,
            "issistId": subid,
            "likeName": likeName == "" ? null : likeName
        }
        $.ajax({
            url: "/knowledgePoint/getAll",
            //contentType: "application/json;charset=UTF-8",
            type: "post",
            async: false,
            data: val,
            dataType: "json",
            success: function (data) {
                dealNull(data);
                $("#knowledgePoint").children("tbody").empty();
                for (let i = 0; i < data.page.content.length; i++) {
                    dealNull(data.page.content[i]);
                    $("#knowledgePoint").children("tbody").append('<tr data_key="id" data_val="' + data.page.content[i].id + '" up="0">\n' +
                        '<td class="serial">' + (i + 1) + '</td>\n' +
                        '<td type="text" notnull="1" state="1" data_key="name" data_val="' + data.page.content[i].name + '">' + data.page.content[i].name + '</td>\n' +
                        '<td type="text" notnull="1" state="1" data_key="subId" data_val="' + data.page.content[i].subId + '" hidden>' + data.page.content[i].subId + '</td>\n' +
                        '<td type="text" notnull="1" state="1" data_key="analysis" data_val="' + data.page.content[i].analysis + '">' + data.page.content[i].analysis + '</td>\n' +
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
    $(".querybtn").click(function () {
        likeName = "%" + $("#query").val() + "%";
        Init(0);
    });

});

function tableadd() {
    if (subid == 0) {
        $.alert("请选择科目类型");
        return;
    }
    var index;
    if ($("#knowledgePoint").children("tbody").children("tr:last").find("td").length == 0)
        index = 1;
    else
        index = ($("#knowledgePoint").children("tbody").children("tr:last").children("td:first").html() - 0) + 1;
    $("#knowledgePoint").children("tbody").append('<tr data_key="id" data_val="" up="0">\n' +
        '<td class="serial">' + index + '</td>\n' +
        '<td type="text" notnull="1" state="1" data_key="name" data_val=""></td>\n' +
        '<td type="text" notnull="1" state="1" data_key="subId" data_val="' + subid + '" hidden></td>\n' +
        '<td type="text" notnull="1" state="1" data_key="analysis" data_val=""></td>\n' +
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
