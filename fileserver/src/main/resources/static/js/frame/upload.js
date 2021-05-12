function minview() {
    parent.upload_minview();
    $(".uptitle").css("display", "none");
    $(".talks").css("display", "none");
    $(".viewimg").css("display", "block");
    $("body").css("overflow-y", "hidden");
    $("body").css("overflow-x", "hidden");
    $("body").css("background-color", "transparent");
}

function maxview() {
    parent.upload_maxview();
    $(".uptitle").css("display", "block");
    $(".talks").css("display", "block");
    $(".viewimg").css("display", "none");
    $("body").css("overflow-y", "auto");
    $("body").css("-ms-overflow-style", "none");
    $("body").css("background-color", "#FFF");
}

function getKbMbGb(limit) {
    var size = "";
    if (limit < 0.1 * 1024) {                            //小于0.1KB，则转化成B
        size = limit.toFixed(2) + "B"
    } else if (limit < 0.1 * 1024 * 1024) {            //小于0.1MB，则转化成KB
        size = (limit / 1024).toFixed(2) + "KB"
    } else if (limit < 0.1 * 1024 * 1024 * 1024) {        //小于0.1GB，则转化成MB
        size = (limit / (1024 * 1024)).toFixed(2) + "MB"
    } else {                                            //其他转化成GB
        size = (limit / (1024 * 1024 * 1024)).toFixed(2) + "GB"
    }

    var sizeStr = size + "";                        //转成字符串
    var index = sizeStr.indexOf(".");                    //获取小数点处的索引
    var dou = sizeStr.substr(index + 1, 2)            //获取小数点后两位的值
    if (dou == "00") {                                //判断后两位是否为00，如果是则删除00
        return sizeStr.substring(0, index) + sizeStr.substr(index + 3, 2)
    }
    return size;
}

function addtalk(talk) {
    const timestamp = parseInt((new Date()).valueOf()); //唯一的标识
    $(".talks").append('<div id=' + timestamp + ' class="talk nowtalk">\n' +
        '        <div class="filename"></div>\n' +
        '        <div  class="progress progress-striped active" style="width:240px;float: left;">\n' +
        '            <div  class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100">' +
        '            </div>\n' +
        '        </div>\n' +
        '        <div class="talkstate"> - ' + getKbMbGb(talk.upfile.size) + '</div>\n' +
        '        <div class="pausetalkdiv">\n' +
        '            <i class="my-icon lsm-sidebar-icon icon-shezhi pausetalkbth"></i>\n' +
        '        </div>\n' +
        '    </div>');
    $("#" + timestamp).children(".filename").html(talk.upfile.name);

    talk['divid'] = timestamp;

    //talk['state']=0;//等待
    talks.push(talk);
    startnewtalk();
    // this.upload=patchUpload;
    // this.upload.init(timestamp,file);
    // this.upload.start();
    //alert(file.name+"||"+file.size);
}

let talks = [];//任务集合
let upstate = 0;
let nowtalk = {};

function startnewtalk() {
    if (talks.length == 0) {
        $(".uploadimg").attr("src", "./images/upload/uploadnotalk.png")
        $(".talknum").html("");
    } else {
        $(".uploadimg").attr("src", "./images/upload/upload.gif")
        $(".talknum").html(talks.length);
    }

    for (let i = 0; i < talks.length; i++) {
        //for (let j=0;j<workers.length;j++){
        if (upstate == 0) {
            // let msg={};
            // msg["msgid"]=1;//设置id
            // msg["talk"]=talks[i];
            // workers[j].postMessage(msg)
            nowtalk = talks[i];
            patchUpload.init(talks[i].divid, talks[i].upfile);
            talks.splice(i, 1); // 将使后面的元素依次前移，数组长度减1
            i--;
            upstate = 1;
            break;
        } else {
            break;
        }
    }
    //}
}

function talkend() {
    upstate = 0;
    startnewtalk();
}

/**
 * 添加共享文件数据及授权数据
 * @param id
 */
function addShareFileAndRight(id) {
    //添加共享文件数据（返回共享文件id）并添加授权信息
    let sharefileid;
    let ShareFile = {
        "id": null,
        "ownerIdent": null,
        "ownerId": null,
        "fileId": id
    }
    if (nowtalk.type == 0) {
        $.ajax({
            url: "/ShareFile/getIdIsNoAdd",
            contentType: "application/json;charset=UTF-8",
            type: "post",
            data: JSON.stringify(ShareFile),
            dataType: "json",
            success: function (data) {
                sharefileid = data.resoult;
                if (sharefileid != -1) {
                    for (let g = 0; g < nowtalk.id.length; g++) {
                        let ShareRight = {
                            "id": null,
                            "shareIdent": nowtalk.ident,
                            "shareId": nowtalk.id[g],
                            "shareFileId": sharefileid,
                            "allottedTime": null
                        }
                        //添加授权信息
                        $.ajax({
                            url: "/ShareRight/AddIsHaveUpTime",
                            contentType: "application/json;charset=UTF-8",
                            type: "post",
                            data: JSON.stringify(ShareRight),
                            dataType: "json",
                            success: function (data) {
                                console.log("添加授权信息成功");
                            },
                            error: function (data) {
                                console.log("添加授权信息服务器异常");
                            }
                        });
                    }

                } else {
                    console.log("添加共享文件失败");
                }
            },
            error: function (data) {
                console.log("添加共享文件服务器异常");
            }
        });
        //循环添加共享文件授权表
        // for()
    }
}

//
// let talks=[];//任务集合
// let workers=[];//线程集合
// let state=[];//线程状态
// for(let i=0;i<3;i++){//初始化线程
//     let worker=new Worker("/js/frame/uploadworker.js");
//     workers.push(worker);
//     state[i]=0;
//     let msg={};
//     msg["msgid"]=0;//设置id
//     msg["threadid"]=i;
//     workers[i].postMessage(msg);
// }
// onmessage=function (event) {
//     if(event.data.mythread.threadstate==0){//任务执行完毕
//         state[event.data.mythread.threadid]=0;//线程状态重置
//         starttalk();
//     }
// }
//
// function starttalk() {
//     for (let i=0;i<talks.length;i++){
//         for (let j=0;j<workers.length;j++){
//             if (state[j]==0){
//                 let msg={};
//                 msg["msgid"]=1;//设置id
//                 msg["talk"]=talks[i];
//                 workers[j].postMessage(msg)
//                 talks.splice(i, 1); // 将使后面的元素依次前移，数组长度减1
//                 i--;
//                 //talks.remove(i);
//                 break;
//             }
//         }
//     }
// }

var patchUpload = {
    /**
     * 分片上传成功索引
     */
    succeed: [],

    /**
     * 分片上传失败索引
     */
    failed: [],

    /**
     * 失败重试次数
     */
    try: 3,

    /**
     * 分片大小，这里是 5MB
     */
    shardSize: 5 * 1024 * 1024,

    /**
     * 显示dom
     */
    div: null,
    /**
     * 文件对象
     */
    file: null,
    /**
     * 初始化
     */
    init: function (di, fil) {
        //this.setEvent();
        this.succeed = [];
        this.failed = [];
        this.try = 3;
        this.file = fil;
        this.div = di;
        this.loadProcess(0);
        this.start();
    },
    start: function () {
        this.md5checkUpload(this.file);
    },
    /**
     * 设置页面事件监听
     */
    // setEvent: function () {
    //     var me = this;
    //     $("#upload").click(function (e) {
    //         var files = $("#file")[0].files;
    //         if(files.length < 1) {
    //             alert("请选择文件！");
    //             return;
    //         }
    //         me.succeed = [];
    //         me.failed = [];
    //         me.try = 3;
    //         me.loadProcess(0);
    //         me.md5checkUpload(files[0]);
    //     });
    //
    //     $("#try").click(function (e) {
    //         var files = $("#file")[0].files;
    //         me.try = 3;
    //         me.md5checkUpload(files[0]);
    //     });
    // },

    /**
     * 检查文件是否已存在
     * @param file
     * @param md5
     */
    checkUpload: function (file, md5) {
        var me = this;
        $.ajax({
            url: "/file/exists",
            type: "get",
            data: {md5: md5, size: file.size},
            dataType: "json",
            success: function (data) {
                //文件已经完全存在
                if (data.status === 1) {
                    // me.loadProcess(100);
                    $("#" + me.div).find(".talkstate").html("已完成 - " + getKbMbGb(file.size));
                    $("#" + me.div).find(".progress").remove();
                    // alert("急速秒传！");
                    addShareFileAndRight(data.id);
                    talkend();
                    return;
                }
                //文件传输了一部分
                if (data.id && data.status === 0) {
                    me.succeed = data.patchIndex;
                    me.upload(data.id, file);
                    return;
                }
                //上传文件
                me.upload(me.prepareUpload(md5, file), file);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                talkend();
                alert("服务器错误！");
            }
        });
    },

    /**
     * 文件不存在时，插入文件的基本信息，为上传文件分片做准备
     * @param md5
     * @param file
     * @returns {*}
     */
    prepareUpload: function (md5, file) {
        var me = this;
        var id;
        $.ajax({
            url: "/file/new",
            type: "post",
            async: false,
            data: JSON.stringify({name: file.name, md5: md5, size: 0}),
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            success: function (data) {
                if (data && data.id) {
                    id = data.id;
                    return;
                }
                $("#" + me.div).find(".talkstate").html("上传文件失败！ - " + getKbMbGb(file.size));
                //this.div.children(".progress").remove();
                // alert("上传文件失败！");
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                $("#" + me.div).find(".talkstate").html("服务器错误！ - " + getKbMbGb(file.size));
                //alert("服务器错误！");
            }
        });
        return id;
    },

    /**
     * 上传文件
     * @param id
     * @param file
     */
    upload: function (id, file) {
        addShareFileAndRight(id);
        var me = this;
        if (!id) return;
        var shardCount = Math.ceil(file.size / this.shardSize);//文件片数
        for (var i = 0; i < shardCount; i++) {
            if (me.succeed.length !== 0 && me.succeed.indexOf(i) > -1 && me.failed.indexOf(i) === -1) {
                continue;
            }
            this.uploadPatch(id, file, i, shardCount);
        }
    },

    /**
     * 上传分片文件
     * @param parent
     * @param file
     * @param index
     * @param shardCount
     */
    uploadPatch: function (parent, file, index, shardCount) {
        var me = this;
        var start = index * this.shardSize;
        var end = Math.min(file.size, start + this.shardSize);
        var patch = file.slice(start, end);
        var spark = new SparkMD5();
        var reader = new FileReader();
        reader.readAsBinaryString(patch);
        $(reader).on('load', function (e) {
            spark.appendBinary(e.target.result);
            var md5 = spark.end();
            var form = new FormData();
            form.append("index", index);
            form.append("parent", parent);
            form.append("md5", md5);
            form.append("size", patch.size);
            form.append("patch", patch);
            form.append("name", file.name + "-patch-" + index);
            $.ajax({
                url: "/file/patch/upload",
                type: "post",
                data: form,
                processData: false,
                contentType: false,
                dataType: "json",
                success: function (data) {
                    if (!data || !data.ok) {
                        me.failed.push(index);
                        console.log("上传分片" + index + "失败！");
                        talkend();
                        return;
                    }
                    me.succeed.push(index);
                    console.log("上传分片" + index + "成功！");
                    me.loadProcess(((me.succeed.length - 1) * me.shardSize + patch.size) / file.size * 100);
                    me.mergePatch(parent, file, shardCount);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    me.failed.push(index);
                    console.log("服务器错误，上传分片" + index + "失败！");
                    me.tryAgain(parent, file, shardCount);
                }
            });
        });
    },

    /**
     * 请求合并文件
     * @param parent
     * @param file
     * @param shardCount
     */
    mergePatch: function (parent, file, shardCount) {
        var me = this;
        if (me.succeed.length + me.failed.length !== shardCount) return;
        if (me.failed.length !== 0) {
            me.tryAgain(parent, file, shardCount);
            return;
        }
        $.ajax({
            url: "/file/patch/merge",
            type: "post",
            data: {parent: parent, size: file.size},
            dataType: "json",
            success: function (data) {
                if (data && data.ok) {
                    //me.loadProcess(100);
                    $("#" + me.div).find(".talkstate").html("已完成 - " + getKbMbGb(file.size));
                    $("#" + me.div).find(".progress").remove();
                    $("#" + me.div).removeClass("nowtalk");
                    $("#" + me.div).addClass("finishtalk")
                    talkend();
                    // alert("上传文件成功！");
                    return;
                }
                $("#" + me.div).find(".talkstate").html("上传文件失败！ - " + getKbMbGb(file.size));
                $("#" + me.div).find(".progress").remove();
                talkend();
                //alert("上传文件失败！");
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                $("#" + me.div).find(".talkstate").html("服务器错误！ - " + getKbMbGb(file.size));
                $("#" + me.div).find(".progress").remove();
                talkend();
                //alert("服务器错误！");
            }
        });
    },

    /**
     * 重试
     */
    tryAgain: function (parent, file, shardCount) {
        var me = this;
        if (me.succeed.length + me.failed.length !== shardCount) return;
        if (me.failed.length === 0) {
            me.mergePatch(parent, file, shardCount);
            return;
        }
        if (me.try === 0) {
            // $("#try").css("display", "block");
            return;
        }
        me.try--;
        console.log("重试...");
        while (me.failed.length !== 0) {
            me.uploadPatch(parent, file, me.failed.pop(), shardCount);
        }
    },

    /**
     * 读取进度条（见笑了）
     * @param process
     */
    loadProcess: function (process) {
        $("#" + this.div).find(".progress-bar").css("width", process + "%");
        $("#" + this.div).find(".progress-bar").html(process + "%");
        // process = Math.min(100, process);
        // if(process === 100) {
        //     $("#try").css("display", "none");
        // }
        // $("#process").html(process.toFixed(2) + '<span>%</span>');
    },

    /**
     * 获取文件的 md5 值
     * @param file
     * @returns {*|number}
     */
    md5checkUpload: function (file) {
        var me = this;
        var index = 0;
        var shardCount = Math.ceil(file.size / this.shardSize);
        var spark = new SparkMD5.ArrayBuffer();
        var fileReader = new FileReader();
        var blobSlice = File.prototype.slice || File.prototype.mozSlice || File.prototype.webkitSlice;

        fileReader.onload = function (e) {
            index++;
            spark.append(e.target.result);
            if (index < shardCount) {
                loadNext();
                return;
            }
            me.checkUpload(file, spark.end());
        };

        function loadNext() {
            var start = index * me.shardSize;
            var end = Math.min(start + me.shardSize, file.size);
            fileReader.readAsArrayBuffer(blobSlice.call(file, start, end));
        }

        loadNext();
    }
};

// $(function () {
//     patchUpload.init();
// });

