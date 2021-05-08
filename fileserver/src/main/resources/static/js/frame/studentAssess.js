// JavaScript Document
var pageNumber = 16; // 每页显示多少条记录
var pageIndex = 0;//页码
var subId = 0;
var likeName = "";
//分页元素
var total = 50; // 总共多少记录
let opent = "";
$(function () {
    Init(0);
    //初始化单页显示条数
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
            Init(0);
        }
    });

    function PageCallback(index, jq) { // 前一个参数表示当前点击的那个分页的页数索引值，后一个参数表示装载容器。
        pageIndex = index;
        Init(pageIndex);
    }

    function Init(index) { // 参数就是点击的那个分页的页数索引值
        pageIndex = index;
        let StudentAssessVo = {
            "pageNumber": pageNumber,
            "pageIndex": pageIndex,
        }
        var win = window;
        $.ajax({
            url: "Assess/StudentAssess",
            contentType:"application/json;charset=UTF-8",
            type: "post",
            async: true,
            data: JSON.stringify(StudentAssessVo),
            dataType: "json",
            success: function (data) {
                console.log(data);
                $(".allTestPaper").empty();
                for(var i=0;i<data.page.content.length;i++){

                    $.ajax({
                        url: "Assess/findMsg2",
                        //contentType:"application/json;charset=UTF-8",
                        type: "post",
                        async: false,
                        data: {id: data.page.content[i].id},
                        dataType: "json",
                        success: function (data2) {
                            var  po ="";
                            let  tite="进入考试";
                            if(data2.submit !=2){

                            }else {
                                tite="已参加";
                                po+="disabled=disabled"
                            }

                            console.log(data2);
                                     $(".allTestPaper").append('<div class="testPaper" style="">\n' +
                                         '        <table width="100%">\n' +
                                         '            <tr >\n' +
                                         '                <th colspan="2" style="text-align:center;">' + data.page.content[i].name + '</th>\n' +
                                         '            </tr>\n' +
                                         '            <tr>\n' +
                                         '                <td>学科：</td>\n' +
                                         '                <td>' + data2.subName + '</td>\n' +
                                         '            </tr>\n' +
                                         '            <tr>\n' +
                                         '                <td>试卷总分：</td>\n' +
                                         '                <td>' + data2.choiceScoreNum + '</td>\n' +
                                         '            </tr>\n' +
                                         '            <tr>\n' +
                                         '                <td>题目数量：</td>\n' +
                                         '                <td>' + data2.choiceNum + '</td>\n' +
                                         '            </tr>\n' +
                                         '            <tr>\n' +
                                         '                <td>结束时间：</td>\n' +
                                         '                <td>' + data.page.content[i].endTime + '</td>\n' +
                                         '            </tr>\n' +
                                         '            <tr>\n' +
                                         '                <td>答题时长：</td>\n' +
                                         '                <td>' + data.page.content[i].makeTime+ ' (分钟) </td>\n' +
                                         '            </tr>\n' +
                                         '            <tr>\n' +
                                         '                <td>创建人：</td>\n' +
                                         '                <td>' + data2.name + '</td>\n' +
                                         '            </tr>\n' +
                                         '            <tr>\n' +
                                         '                <td>编辑操作：</td>\n' +
                                         '                <td style="font-size: 20px;">\n' +
                                         '  <button class="btn btn-info"  '+po+' onclick="javascript:parent.open(\'answer?id=' + data.page.content[i].id + '\')">'+tite+'</button> \n ' +
                                         // '                    <label style="margin-right: 10px;cursor: pointer;"><i onclick="javascript:parent.open(\'addTestPaper?id=' + data.page.content[i].id + '\')" class="my-icon lsm-sidebar-icon icon-bianji del"></i></label>\n' +
                                         // '                    <label><i class="my-icon lsm-sidebar-icon icon-shanchu " data_id=' + data.page.content[i].id + '  style="cursor: pointer;"></i></label>\n' +
                                         '                </td>\n' +
                                         '            </tr>\n' +
                                         '\n' +
                                         '\n' +
                                         '        </table>\n' +
                                         '    </div>')

                        }, error(data2) {
                        }
                    });
                }

                 total = data.page.totalElements;
                $(".totalmsg").html("【共" + total + "条记录，当前显示：" + (data.page.pageable.pageNumber * data.page.pageable.pageSize + 1) + "~" + (data.page.pageable.pageNumber * data.page.pageable.pageSize + data.page.numberOfElements) + "】");
            }, error: function (data) {
                console.log("服务器异常");
            }
        });
    }



});



