
$(function () {
    let pageIndex=1;
    let pageNumber=5;
    let  total=5;
    let  nu=0;

     function refresh() {
         nu=1;
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

   Init(0);

    function PageCallback(index, jq) { // 前一个参数表示当前点击的那个分页的页数索引值，后一个参数表示装载容器。
        pageIndex = index;
        Init(pageIndex+1);
    }
    $("#showNumber").val('5').trigger("change")
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
            Init(1);
        }
    });


    
    function Init(index) {
        pageIndex=index;
      let  Page={
          "pageIndex":pageIndex,
          "pageNumber":pageNumber
      }
        $.ajax({
        url:"/Sample/findAll",
        type:"post",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(Page),
        dataType:"json",
         success:function (data) {
            console.log(data);
             $(".filtr-container").empty();
             console.log(data);
             for (let i = 0; i < data.size; i++) {
                     $(".filtr-container").append('<div class="filtr-item chunk1" fileid=' + data.list[i].id + '>\n' +
                         '<img src="../../images/file_logo_png/1140224.png"/>\n' +
                         '<div class="sharefilename">'+data.list[i].fileName+'</div>\n' +
                         '<div class="sharefilesize"></div>\n' +
                         '<div class="susdiv">\n' +
                         '<div class="operation download"><i class="my-icon lsm-sidebar-icon icon-xiazai " onclick="sampleDownload( '+data.list[i].id+')"></i></div>\n' +
                         '<div class="operation compile"><i class="my-icon lsm-sidebar-icon icon-chazhao "></i></div>\n' +
                         '<div class="operation delete"><i class="my-icon lsm-sidebar-icon icon-chazhao "></i></div>\n' +
                         '</div>\n' +
                         '</div>');
             }
             // $(".download").bind("click", function () {
             //     win.location.href = "/file/downloadShareFile?fileId=" + $(this).parent().parent().attr("fileid");
             // });
             // total=21;
             total = data.total;
             $(".totalmsg").html("【共" + total + "条记录，当前显示：" + (data.prePage * data.pageSize + 1) + "~" + (data.pageNum * data.pageSize ) + "】");
             if(nu==0){
                 refresh();
             }


         },
         error:function (data) {

         }
        })
    }
})


var  dataSource=null;

var  win=window;

function  sampleDownload(id) {
    $.ajax({
        url: "/Sample/findById",
        type: "post",
        data: {"id": id},
        dataType: "json",
        success: function (data) {
            dataSource=data;
            xiazai();
            console.log(data);
        }, error: function (data) {

        }


    })
}

function  xiazai() {
    let value="";
    const  SPEED=500;
    for(let i=1;i<=dataSource.fileNumbe;i++){

        if (dataSource.fileName != "" && dataSource.fileName != null) {
            setTimeout(()=>{
                let fileName=dataSource.fileName.substring(0,dataSource.fileName.lastIndexOf("."));
                let suffix= dataSource.fileName.substring(dataSource.fileName.lastIndexOf("."),dataSource.fileName.length);
                value= dataSource.filePath+"/"+fileName+"_"+i+suffix
                win.location.href = "Sample/downloadShareFile?filename="+value;
            },SPEED*i)

        } else {
            $.alert("没有附件");
        }
    }

}