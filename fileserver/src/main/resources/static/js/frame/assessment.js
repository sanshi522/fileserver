var type = 1;
$(function () {
    //分页参数
    var pageNumber = 16; // 每页显示多少条记录
    var pageIndex = 0;//页码
    var subId = 0;
    var total = 50; // 总共多少记录
    //初始化单页显示条数
    let nu=0;
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


    $("#showNumber").val('10').trigger("change");
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
            inIt(0);
        }
    });



    function PageCallback(index, jq) { // 前一个参数表示当前点击的那个分页的页数索引值，后一个参数表示装载容器。
        pageIndex = index;
        inIt(pageIndex);
    }
    inIt(0);
//头部div的切换
    $(".assessMenu").click(function () {
        nu=0;
        $(".assessMenu").css("background", "#E6F1F1");
        $(this).css("background", "#4DADDB");
        type = $(this).attr("data_type");
        if (type == 3) {
            if ($("#the").text() == undefined) {
                $("#tea").append('<th id="the"  >操作</th>');
            } else {
                $("#the").remove();
            }
        }

        inIt(0);
    })



    //加载数据
    function inIt(index) {
        let a = $("#AssessId").attr("testPaperId");
        $.ajax({
            url: "Assess/findByOneId",
            data: {assessId: a},
            type: "Post",
            sync:false,
            success: function (re) {
                $(".assessMsg").empty();
                $(".assessMsg").append(' <div  class="col-md-6 col-md-offset-3" style=" text-align: center;"><h2>' + re.name + '</h2></div>');
            },
            error: function (re) {
                console.log("服务器异常")
            }
        })
        pageIndex = index;
        let RespondentsPage = {
            "pageNumber": pageNumber,
            "pageIndex": pageIndex,
            "assessId": $("#AssessId").attr("testPaperId"),
            sync: false,
            "type": type
        }
        $.ajax({
            url: "Respondents/selectRespondents",
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(RespondentsPage),
            sync:false,
            type: "Post",
            success: function (data) {
                $("#teacher").children("tbody").empty();
                for (var i = 0; i < data.page.content.length; i++) {
                    var createTime = data.page.content[i].createTime;
                    let respondentId = data.page.content[i].id;
                    $.ajax({
                        url: "Respondents/selectRespondentsMsg",
                        type: "Post",
                    sync:false,
                        data: {"id": data.page.content[i].id},
                        dataType: "json",
                        success: function (data2) {
                            if (data2.score == null) {
                                data2.score = 0;
                            }

                            var opnt = "";
                            if (type == "3") {
                                opnt = '<td><button class="btn btn-info"  onclick="javascript:parent.open(\'approval?id=' + respondentId + '\')"       >审批</button></td>';
                            }
                            $("#teacher").children("tbody").append('<tr>\n' +
                                '                <td>' + data2.student.stuName + '</td>\n' +
                                '                <td>' + data2.student.stuNumber + '</td>\n' +
                                '                <td>' + data2.subName + '</td>\n' +
                                '                <td>' + data2.score + '</td>\n' +
                                '                <td>' + createTime + '</td>\n' +
                                '  ' + opnt + '\n ' +
                                '                </tr>');
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



})

//导出成绩
function  exportScore() {
    let assessId = $("#AssessId").attr("testPaperId");
    window.location.href = "Assess/exportScore?assessId="+assessId;

}
