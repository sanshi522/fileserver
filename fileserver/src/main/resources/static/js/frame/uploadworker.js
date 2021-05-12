// JavaScript Document
//上传文件线程js
importScripts('/js/jquery/jquery-nodom/jquery.nodom.js');
//importScripts('/js/jquery/nodom.js');


importScripts('/js/jquery/jquery-3.5.1.min.js');
importScripts('/js/spark-md5.min.js');


//import '/js/spark-md5.min.js';
let threadid;
let divid;
let upfile;
onmessage = function (event) {
    let msg = event.data;
    if (msg.msgid == 0) {
        threadid = msg.threadid;
    } else if (msg.msgid == 1) {
        divid = msg.talk.divid;
        upfile = msg.talk.upfile;
        patchUpload.init(divid, upfile);
        patchUpload.start();
    }
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
        this.file = fil;
        this.div = di;
    },
    start: function () {
        this.succeed = [];
        this.failed = [];
        this.try = 3;
        //this.loadProcess(0);
        this.md5checkUpload(this.file);
    },
    /**
     * 设置页面事件监听
     */
    setEvent: function () {
        let me = this;
        me.succeed = [];
        me.failed = [];
        me.try = 3;
        //me.loadProcess(0);
        // $("#upload").click(function (e) {
        //     var files = $("#file")[0].files;
        //     if(files.length < 1) {
        //         alert("请选择文件！");
        //         return;
        //     }
        //     me.succeed = [];
        //     me.failed = [];
        //     me.try = 3;
        //     me.loadProcess(0);
        //     me.md5checkUpload(files[0]);
        // });
        //
        // $("#try").click(function (e) {
        //     var files = $("#file")[0].files;
        //     me.try = 3;
        //     me.md5checkUpload(files[0]);
        // });
    },

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
                    //$("#"+me.div).find(".talkstate").html("已完成 - "+getKbMbGb(file.size));
                    // $("#"+me.div).find(".progress").remove();
                    // alert("急速秒传！");
                    me.end();
                    return;
                }
                //文件传输了一部分
                if (data.id && data.status === 0) {
                    me.succeed = data.patchIndex;
                    me.upload(data.id, file);
                    me.end();
                    return;
                }
                //上传文件
                me.upload(me.prepareUpload(md5, file), file);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
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
                // $("#"+me.div).find(".talkstate").html("上传文件失败！ - "+getKbMbGb(file.size));
                //this.div.children(".progress").remove();
                // alert("上传文件失败！");
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                // $("#"+me.div).find(".talkstate").html("服务器错误！ - "+getKbMbGb(file.size));
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
        var me = this;
        if (!id) {
            me.end();
            return;
        }
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
        reader.onload(function (e) {

            // })
            // $(reader).on('load',function (e) {
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
                        return;
                    }
                    me.succeed.push(index);
                    console.log("上传分片" + index + "成功！");
                    // me.loadProcess(((me.succeed.length - 1) * me.shardSize + patch.size) / file.size * 100);
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
                    // $("#"+me.div).find(".talkstate").html("已完成 - "+getKbMbGb(file.size));
                    // $("#"+me.div).find(".progress").remove();
                    // $("#"+me.div).removeClass("nowtalk");
                    // $("#"+me.div).addClass("finishtalk")
                    // alert("上传文件成功！");
                    me.end();
                    return;
                }
                // $("#"+me.div).find(".talkstate").html("上传文件失败！ - "+getKbMbGb(file.size));
                // $("#"+me.div).find(".progress").remove();
                //alert("上传文件失败！");
                me.end();
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                // $("#"+me.div).find(".talkstate").html("服务器错误！ - "+getKbMbGb(file.size));
                //  $("#"+me.div).find(".progress").remove();
                me.end();
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
            $("#try").css("display", "block");
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
    },
    end: function () {
        let mythread = {};
        mythread['threadstate'] = 0;
        postMessage(mythread);
    }
};