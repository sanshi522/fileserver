var sampleUrl="";
var size=0;
var endData = new Date();
var endData2 = new Date();
$(function () {
    let pageIndex=1;
    let pageNumber=10;
    let  total=5;
    let likeName="";
    let  nu=0;

    $.ajax({
        url: "Sample/sampleUrl",
        contentType: "application/json;charset=UTF-8",
        type: "post",
        success: function (data) {
            sampleUrl = data;
        },
        error: function (data) {
            console.log("服务器异常")
        }
    })




    Date.prototype.Format = function (fmt) { //author: meizz
        var o = {
            "M+": this.getMonth() + 1,                 //月份
            "d+": this.getDate(),                    //日
            "h+": this.getHours(),                   //小时
            "m+": this.getMinutes(),                 //分
            "s+": this.getSeconds(),                 //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds()             //毫秒
        };
        if (/(y+)/.test(fmt))
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substring(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substring(("" + o[k]).length)));
        return fmt;
    }


    $('#search-to-date').datetimepicker({
        format: 'Y-m-d H:i:00',
        value: new Date().Format('yyyy-MM-dd hh:mm:ss'),
        theme: 'dark',
        step: 1,
        onSelectTime: function (dateText, inst) {
            endData = dateText;
            dateText.Format('yyyy-MM-dd hh:mm:ss');
        },
        onSelectDate: function (dateText, inst) {
            endData = dateText;
            dateText.Format('yyyy-MM-dd hh:mm:ss');
        }
    });


    $('#search-fom-date').datetimepicker({
        format: 'Y-m-d H:i:00',
        value: new Date().Format('yyyy-MM-dd hh:mm:ss'),
        theme: 'dark',
        step: 1,
        onSelectTime: function (dateText, inst) {
            endData2=dateText;
            dateText.Format('yyyy-MM-dd hh:mm:ss');
        },
        onSelectDate: function (dateText, inst) {
            endData2=dateText;
            dateText.Format('yyyy-MM-dd hh:mm:ss');
        }
    });




    $.datetimepicker.setLocale('zh');



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

   Init(0);

    function PageCallback(index, jq) { // 前一个参数表示当前点击的那个分页的页数索引值，后一个参数表示装载容器。
        pageIndex = index;
        Init(pageIndex+1);
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
            Init(1);
        }
    });


    
    function Init(index) {
        pageIndex=index;
        let SamplePage={
            "pageIndex":pageIndex,
            "pageNumber":pageNumber,
            "likeName":likeName,
            "type":$("#typeScreen").val()
        }
        $.ajax({
        url:"/Sample/findAll",
        type:"post",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(SamplePage),
        dataType:"json",
         success:function (data) {
             $(".filtr-container").empty();
             console.log(data);
             for (let i = 0; i < data.size; i++) {
                 let me=0;
                 if (me == 0) {

                     $(".filtr-container").append('<div class="filtr-item chunk" fileid=' + data.list[i].id + '>\n' +
                         '<img src="../../images/file_logo_png/1140224.png"/>\n' +
                         '<div class="sharefilename"  style="width:60%;height: 80%" >' +
                         '<div><span style="float: left" >样本名：</span>'+   data.list[i].fileName+' </div> '+
                         '<div style="width: 100%;float: left"><span  style="float: left">调制方式：</span>'+data.list[i].modulationType+' </div> '+
                         '<div style="width: 100%;float: left"><span  style="float: left">编码方式：</span>'+data.list[i].codType+' </div> '+
                         '<div style="width: 100%;float: left"><span  style="float: left">体制：</span>'+data.list[i].systemType+' </div> '+
                         '<div style="width: 100%;float: left"><span  style="float: left">文件数量：</span>'+data.list[i].fileNumber+' </div> '+
                         '<div style="width: 100%;float: left"><span  style="float: left">采集时间：</span>'+data.list[i].getData+' </div> '+
                         '</div>\n' +
                         '<div class="sharefilesize"></div>\n' +
                         '<div class="susdiv">\n' +
                         '<div style="width: 30%;float: left"> <button onclick="sampleDownload( '+data.list[i].id+')"  class="btn-info" style="width: 60px">下载</button> </div>\n' +
                         '<div style="width: 30%;float: left"> <button class="btn-warning icon-bianji" sampleId='+data.list[i].id+'>编辑</button> </div>\n' +
                         '<div style="width: 30%;float: left"> <button class="btn-danger" onclick="deleteById('+data.list[i].id+')">删除</button></div>\n' +

                         // '<div class="operation download"><i class="my-icon lsm-sidebar-icon icon-xiazai " onclick="sampleDownload( '+data.list[i].id+')"></i></div>\n' +
                         // '<div class="operation delete"><i class="my-icon lsm-sidebar-icon icon-shanchu querybtn" onclick="deleteById('+data.list[i].id+')"></i></div>\n' +
                         // '<div class="operation compile"><i class="my-icon lsm-sidebar-icon icon-bianji querybtn" sampleId='+data.list[i].id+' ></i></div>\n' +
                         '</div>\n' +
                         '</div>');
                 } else {
                     $(".filtr-container").append('' +
                         '<div class="filtr-item line" fileid=' +data.list[i].id + '>\n' +
                         '<img src="../../images/file_logo_png/1140224.png"/>\n' +
                         '<div class="sharefilename">' + data.list[i].fileName + '</div>\n' +
                         '<div class="sharefilesize"></div>\n' +
                         '<div class="susdiv">\n' +
                         '<div class="operation download"><i class="my-icon lsm-sidebar-icon icon-xiazai "></i></div>\n' +
                         '<div class="operation compile"><i class="my-icon lsm-sidebar-icon icon-chazhao "></i></div>\n' +
                         '<div class="operation delete"><i class="my-icon lsm-sidebar-icon icon-chazhao "></i></div>\n' +
                         '</div>\n' +
                         '</div>');
                 }
             }
             // $(".download").bind("click", function () {
             //     win.location.href = "/file/downloadShareFile?fileId=" + $(this).parent().parent().attr("fileid");
             // });
             // total=21;
             total = data.total;
             let  p=pageIndex
             if (pageIndex==0){
                 p=1
             }
             $(".totalmsg").html("【共" + total + "条记录，当前显示：" + (data.prePage * data.pageSize + 1) + "~" + (p * data.pageSize ) + "】");
             if(nu==0){
                 refresh();
             }

             initadd();
         },
         error:function (data) {

         }
        })
    }

    //搜索
    $(".querybtn").click(function () {
        likeName = $("#query").val();
        nu=0;
        Init(0);
    });
    /**
     * 弹出窗
     */
    $("#addsample").click(function (e) {
        $(":text").val("");
        $("#search-to-date").val(new Date().Format('yyyy-MM-dd hh:mm:ss'));
        openWindw(e.pageX, e.pageY);
    });



    var popWindow = $('.popWindow');
    var popWindow2 = $('.popWindow2');
    var oClose = $('.popWindow h3 span');
    //浏览器可视区域的宽度
    var browserWidth = $(window).width();
    //浏览器可视区域的高度
    var browserHeight = $(window).height();
    //浏览器纵向滚动条距离上边界的值
    var browserScrollTop = $(window).scrollTop();
    //浏览器横向滚动条距离左边界的值
    var browserScrollLeft = $(window).scrollLeft();
    //弹出窗口的宽度
    var popWindowWidth = popWindow.outerWidth(true);
    //弹出窗口的高度
    var popWindowHeight = popWindow.outerHeight(true);
    //left的值＝浏览器可视区域的宽度／2－弹出窗口的宽度／2+浏览器横向滚动条距离左边界的值
    var positionLeft = browserWidth / 2 - popWindowWidth / 2 + browserScrollLeft;
    //top的值＝浏览器可视区域的高度／2－弹出窗口的高度／2+浏览器纵向滚动条距离上边界的值
    var positionTop = browserHeight / 2 - popWindowHeight / 2 + browserScrollTop;
    var oMask = '<div class="mask"></div>'
    //遮照层的宽度
    var maskWidth = $(document).width();
    //遮照层的高度
    var maskHeight = $(document).height();


    /** 弹出框**/
    function openWindw(x, y) {
        imgUrl="";
        $(popWindow).css("left", x - popWindowWidth + 'px');
        $(popWindow).css("top", y + 'px');
        popWindow.show().animate({
            'left': positionLeft + 'px',
            'top': positionTop + 'px',
        }, 500);
        $('body').append(oMask);
        $('.mask').width(maskWidth).height(maskHeight);
        popWindow.removeClass("hide");
        $('#playRate').val(280).trigger("change");
        $(".ui.filelist").empty();
        initadd();

    };

    function openWindw2(x, y) {
        $(popWindow2).css("left", x - popWindowWidth + 'px');
        $(popWindow2).css("top", y + 'px');
        popWindow2.show().animate({
            'left': positionLeft + 'px',
            'top': positionTop + 'px',
        }, 500);
        $('body').append(oMask);
        $('.mask').width(maskWidth).height(maskHeight);
        popWindow2.removeClass("hide");
        initadd();

    };


//初始化绑定事件按钮
    function initadd() {
        $(".ui.filelist").append("<div  class='addfile'>" +
            "<input type='file' class='inp_file' class='inp_file' style='display: none;'>" +
            "<i id='addfilebtn' class='my-icon lsm-sidebar-icon icon-tianjia'></i>" +
            "</div>");
        //添加文件
        $(".icon-bianji").bind("click", function (e) {
            $(":text").val("");
            $("#search-to-date").val(new Date().Format('yyyy-MM-dd hh:mm:ss'));
            openWindw2(e.pageX, e.pageY);
            let id=  $(this).attr("sampleId")
            findSamle(id);

        });

        $("#addfilebtn").bind("click", function () {
            $("#addfilebtn").parent().children("input").trigger("click");
            //var sum=$("#inp_file").files.length;
        });
        EditfileName();
        $(".inp_file").bind("change", function () {
            if (this.files.length < 1) {
                return;
            } else {
                //新文件
                $(this).parent().children("i").remove();
                $(this).parent().removeClass("addfile");
                $(this).parent().addClass("file");
                $(this).parent().append("<img class='filetypeimg' src='images/file_logo_png/1140192.png'/>" +
                    "<div class='sharefilename'>" + this.files[0].name + "</div>" +
                    "<div class='delfile' >" +
                    "<i class='my-icon lsm-sidebar-icon icon-shanchu' style='font-size: 20px;color:#B4DD33;''></i>" +
                    "</div>");
                $(".delfile").bind("click", function () {
                    $(this).parent().remove();
                    //var sum=$("#inp_file").files.length;
                    EditfileName();
                });
                //添加文件按钮并绑定事件
                initadd();
            }
        })
    }
    $("#cancel").click(function () {
        $(".popWindow").hide();
        $(".ui.filelist").empty();
    });

    $("#cancel2").click(function () {
        $(".popWindow2").hide();
        $(".ui.filelist").empty();
    });


    //上传按钮
    $('#submit').bind("click", function () {
        if ($(".file").length == 0) {
            $.alert("请添加文件");
            return;
        }
        if ($("#fileName").val()=="" || $("#fileName").val()==undefined) {
            $.alert("请添加文件名");
            return;
        }

        var formData = new FormData();
        let  files =$(".file");
        let  number=1;

        let suffix="";
        for (let i=0;i<files.length;i++) {
            number++;
            var file = $(files[i]).children(".inp_file")[0].files[0];
            size+=file.size;
            let index= file.name.lastIndexOf(".");
            let ext = file.name.substr(index+1);
            if(suffix==""){
                suffix=ext;
            }else {
                if(ext!=suffix){
                    $.alert("文件后缀名不一致");
                    return;
                }

            }
            formData.append("file", file);
        }
        formData.append("fileName", $("#fileName").val());
        $.ajax({
            url: sampleUrl+"/upload",
            type: 'POST',
            data: formData,
            // 告诉jQuery不要去处理发送的数据
            processData: false,
            // 告诉jQuery不要去设置Content-Type请求头
            contentType: false,
            processData: false,
            contentType: false,
            xhr: function () {
                var xhr = $.ajaxSettings.xhr();

                if (xhr.upload) {
                    //处理进度条的事件
                    xhr.upload.addEventListener("progress", progressHandle, false);
                    //加载完成的事件
                    xhr.addEventListener("load", completeHandle, false);
                    //加载出错的事件
                    xhr.addEventListener("error", failedHandle, false);
                    //加载取消的事件
                    xhr.addEventListener("abort", canceledHandle, false);
                    //开始显示进度条
                    showProgress();
                    return xhr;
                }
            },
            beforeSend: function () {
                console.log("正在进行，请稍候");
            }, success: function (responseStr) {
                console.log(responseStr)
                if(responseStr=="false"){
                    $.alert("文件上传失败");
                }else{
                    let path=responseStr.substring(0,responseStr.lastIndexOf("/"));
                    let name=responseStr.substring(path.length+1,responseStr.lastIndexOf("_"))
                    let name2=responseStr.substring(responseStr.lastIndexOf("."),responseStr.length)
                    let fileName=name+name2;
                    let protoType=parseInt($("#playRate").val());
                    let referenceTime=Math.ceil(size/2/(protoType*1000*1000));
                    let Sample={
                        "id":null,
                        "referenceFreq":$("#referenceFreq").val(),
                        "referencePower":$("#referencePower").val(),
                        "modulationType":$("#modulationType").val(),
                        "codType":$("#modulationType").val(),
                        "protoType":$("#protoType").val(),
                        "sourceType":$("#sourceType").val(),
                        "tranModel":$("#tranModel").val(),
                        "carrierNum":$("#carrierNum").val(),
                        "systemType":$("#systemType").val(),
                        "referenceTime":referenceTime,
                        "fileName":$("#fileName").val(),
                        "fileNumber":files.length,
                        "filePath":path,
                        "fileSize":size,
                        "playRate":$("#playRate").val(),
                        "name":fileName,
                        "getData": endData.Format('yyyy-MM-dd hh:mm:ss'),
                    }

                    $.ajax({
                        url:sampleUrl+"Sample/save",
                        contentType: "application/json;charset=UTF-8",
                        type: "post",
                        async: false,
                        data: JSON.stringify(Sample),
                        dataType: "json",
                        success:function (data) {
                            console.log(data);
                            $(".popWindow").hide();
                            $(":input").val("");
                            $(".ui.filelist").empty();
                            if(data>0){
                                alert("添加成功");
                                history.go(0)
                            }else {
                                //$.alert("添加失败");
                                alert("添加失败");
                                history.go(0)
                            }

                        },
                    })
                }
            }
        });

    });



    //上传按钮
    $('#submit2').bind("click", function () {
        if ($("#fileName2").val()=="" || $("#fileName2").val()==undefined) {
            $.alert("文件名不能为空");
            return;
        }
        let fileSize=parseInt($("#sampleSize").val());
        let protoType=parseInt($("#playRate2").val());
        let referenceTime=Math.ceil(fileSize/2/(protoType*1000*1000));
        let Sample={
            "id": $("#sampleId").val(),
            "referenceFreq":$("#referenceFreq2").val(),
            "referencePower":$("#referencePower2").val(),
            "modulationType":$("#modulationType2").val(),
            "codType":$("#modulationType2").val(),
            "protoType":$("#protoType2").val(),
            "sourceType":$("#sourceType2").val(),
            "tranModel":$("#tranModel2").val(),
            "carrierNum":$("#carrierNum2").val(),
            "fileName":$("#fileName2").val(),
            "playRate":$("#playRate2").val(),
            "systemType":$("#systemType2").val(),
            "referenceTime":referenceTime,
            "getData":endData2.Format('yyyy-MM-dd hh:mm:ss')
        }

        $.ajax({
            url:sampleUrl+"Sample/Update",
            contentType: "application/json;charset=UTF-8",
            type: "post",
            async: false,
            data: JSON.stringify(Sample),
            dataType: "json",
            success:function (data) {
                $.alert("添加成功");
                history.go(0)
            },
            error:function (data) {
                console.log("服务器异常")

            }
        })
    });

})


var  dataSource=null;

var  win=window;
var imgUrl="";

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
    for(let i=0;i<dataSource.fileNumber;i++){
        if (dataSource.name != "" && dataSource.name != null) {
            setTimeout(()=>{
                let fileName=dataSource.name.substring(0,dataSource.name.lastIndexOf("."));
                let suffix= dataSource.name.substring(dataSource.name.lastIndexOf("."),dataSource.name.length);
                value= dataSource.filePath+"/"+fileName+"_"+i+suffix
                let name=dataSource.fileName+"_"+i+suffix;
                var newhref =sampleUrl+"Sample/downloadShareFile?filename="+value+"&names="+name;
                win.location.href=newhref;
            },SPEED*i)

        } else {
            $.alert("没有附件");
        }
    }



}

//删除样本文件
function deleteById(id) {
    $.ajax({
        url:"/ChoiceFile/findDelete",
        data:{"id":id},
        type:"post",
        dataType: "json",
        success:function (data) {
          //  history.go(0)
         if (!data.ok){
             $.alert(data.data);
         }else {
             $.ajax({
                 url:sampleUrl+"Sample/deleteById",
                 data:{"id":id},
                 type:"post",
                 dataType: "json",
                 success:function (data) {
                     history.go(0)
                 },
                 error:function (data) {
                     history.go(0)
                 }
             })
         }
        },
        error:function (data) {
            history.go(0)
        }

    })

}



function isAssetTypeAnImage(ext) {
    return [
        'png', 'jpg', 'jpeg', 'bmp', 'gif', 'webp', 'psd', 'svg', 'tiff'].
    indexOf(ext.toLowerCase()) !== -1;
}


function showProgress() {
    $(".progress-body").css("display", "block");
}
//隐藏进度条的函数
function hideProgress() {
    $('.progress-body>.progress-speed', window.parent.document).html("0 bytes");
    $('.progress-body>.progress-info', window.parent.document).html("资料正准备上传");
    $(".progress-body", window.parent.document).css("display", "none");
}
//进度条更新
function progressHandle(e) {
    $('.progress-body progress').attr({value: e.loaded, max: e.total});
    var percent = e.loaded / e.total * 100;
    $('.progress-body>.progress-speed').html(((e.loaded / 1024) / 1024).toFixed(2) + "/" + ((e.total / 1024) / 1024).toFixed(2) + " MB. " + percent.toFixed(2) + "%");
    if (percent == 100) {
        $('.progress-body>.progress-info').html("上传完成,后台正在处理...");
    } else {
        $('.progress-body>.progress-info').html("资料上传ing...");
    }
};
//上传完成处理函数
function completeHandle(e) {
    $('.progress-body>.progress-info').html("上传资料完成");
    setTimeout(hideProgress, 2000);

};
//上传出错处理函数
function failedHandle(e) {
    $('.progress-body>.progress-info', window.parent.document).html("上传资源出错");
};
//上传取消处理函数
function canceledHandle(e) {
    $('.progress-body>.progress-info', window.parent.document).html("上传资源取消");
};



function EditfileName() {
    let  files =$(".file");
    if (files.length>0) {
        for (let i = 0; i < files.length; i++) {
            var file = $(files[i]).children(".inp_file")[0].files[0];
            if (i==0){
                let name=file.name;
                let  fileName=name.substring(0,name.lastIndexOf("."));
                $("#fileName").val(fileName);
            }
        }
    }

}


function  findSamle(id) {
    $.ajax({
        url: "/Sample/findById",
        type: "post",
        data: {"id": id},
        dataType: "json",
        success: function (data) {
            $('#playRate2').val(data.playRate).trigger("change");
            $("#sampleId").val(data.id);
            $("#fileName2").val(data.fileName);
            $("#referenceFreq2").val(data.referenceFreq);
            $("#referencePower2").val(data.referencePower);
            $("#modulationType2").val(data.modulationType);
            $("#codType2").val(data.codType);
            $("#protoType2").val(data.protoType);
            $("#sourceType2").val(data.sourceType);
            $("#systemType2").val(data.systemType);
            $("#tranModel2").val(data.tranModel);
            $("#carrierNum2").val(data.carrierNum);
            $("#systemType2").val(data.systemType);
            $("#sampleSize").val(data.fileSize);
            $("#search-fom-date").val(data.getData);
        }, error: function (data) {
        }
    })


}
