$(function () {

    var pageNumber = 16; // 每页显示多少条记录
    var pageIndex = 0;//页码
    //分页元素
    var total = 50; // 总共多少记录
    let  nu=0;
    //初始化单页显示条数
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




    init(0);

    function init(index) {
        pageIndex = index;
        let PageGet = {
            "pageNumber": pageNumber,
            "pageIndex": pageIndex,
        }
        $.ajax({
            url: "Respondents/selectScore",
            type: "Post",
            sync: false,
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(PageGet),
            dataType: "json",
            success: function (data) {
                console.log(data);
                $("#teacher").children("tbody").empty();
                for (var i = 0; i < data.page.content.length; i++) {
                    let createTime = data.page.content[i].createTime;
                    let id = data.page.content[i].id;
                    let name = data.page.content[i].name;
                    $.ajax({
                        url: "Respondents/selectRespondentsMsg2",
                        type: "Post",
                        sync: false,
                        data: {"id": data.page.content[i].id},
                        dataType: "json",
                        success: function (data2) {
                            console.log(data2);
                            var op = "";
                            if (data2.type == 1) {
                                op += "<td>已参加</td>";
                            } else {
                                op += "<td>缺考</td>";
                            }
                            $("#teacher").children("tbody").append('<tr>\n' +
                                '        <td>' + id + '</td>\n' +
                                '        <td>' + name + '</td>\n' +
                                '        <td>' + data2.subName + '</td>\n' +
                                '        <td>' + data2.score + '</td>\n' + '        ' +
                                ' <td>' + createTime + '</td>\n' +
                                '  ' + op + '\n ' +
                                '        <td><button class="btn btn-info" onclick="javascript:parent.open(\'particulars?id=' + id + '\')">查看详情</button></td>\n' +
                                '    </tr>');
                        },
                        error: function (data2) {
                        }
                    });
                }
                total = data.page.totalElements;
                $(".totalmsg").html("【共" + total + "条记录，当前显示：" + (data.page.pageable.pageNumber * data.page.pageable.pageSize + 1) + "~" + (data.page.pageable.pageNumber * data.page.pageable.pageSize + data.page.numberOfElements) + "】");
                if(nu==0){
                    refresh();
                }
            },
            error: function (data) {
            }
        });
    }

    $("#showNumber").val('10').trigger("change")
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
            init(0);
        }
    });

    function PageCallback(index, jq) { // 前一个参数表示当前点击的那个分页的页数索引值，后一个参数表示装载容器。
        pageIndex = index;
        init(pageIndex);
    }

});



