function tablebind() {
    $("td").bind("click", function () {
        //alert("点击");
        if ($(this).attr("type") == "text") {
            if ($(this).attr("state") == 0) return;
            $(this).attr("state", 0);
            $(this).html('<input type="text" value="' + $(this).attr("data_val") + '">');
            $(this).children("input").focus();
            $(this).children("input").bind("blur", function () {
                if ($(this).val() != $(this).parent("td").attr("data_val")) {
                    if ($(this).parent("td").attr("data_val") == "") {
                        $(this).parent("td").attr("data_val", $(this).val());
                    }
                    if (viv($(this).parents("tr"))) {
                        $(this).parents("tr").attr("up", "1");
                        $(this).parents("tr").attr("class", "info");
                    }
                }
                $(this).parent("td").attr("data_val", $(this).val());
                $(this).parent("td").attr("state", 1);
                $(this).parent("td").html($(this).parent("td").attr("data_val"));
            });
        } else if ($(this).attr("type") == "select") {
            if ($(this).attr("state") == 0) return;
            $(this).attr("state", 0);
            //====================
            var text = $(this).html();
            $(this).html('<select><option value=' + $(this).attr("data_val") + '>' + text + '</option></select>');
            var thetd = $(this);
            $.ajax({
                url: thetd.attr("getlrc"),
                type: "post",
                data: {val: thetd.attr("getval")},
                dataType: "json",
                async: false,
                success: function (data) {
                    thetd.children("select").empty();
                    for (let e = 0; e < data.length; e++) {
                        thetd.children("select").append('<option value=' + data[e].key + '>' + data[e].val + '</option>');
                    }
                    if (thetd.attr("data_val") != "")
                        thetd.children("select").val(thetd.attr("data_val"));
                }
            });
            $(this).children("select").focus();
            $(this).children("select").val($(this).attr("data_val"));
            $(this).children("select").bind("blur", function () {
                if ($(this).val() != $(this).parent("td").attr("data_val")) {
                    if ($(this).parent("td").attr("data_val") == "") {
                        $(this).parent("td").attr("data_val", $(this).val());
                    }
                    if (viv($(this).parents("tr"))) {
                        $(this).parents("tr").attr("up", "1");
                        $(this).parents("tr").attr("class", "info");
                    } else {
                        $(this).parents("tr").attr("up", "0");
                        $(this).parents("tr").removeClass("info");
                    }
                }
                $(this).parent("td").attr("data_val", $(this).val());
                $(this).parent("td").attr("state", 1);
                $(this).parent("td").html($(this).children("option:selected").text());
            });
        }
    })
    $(".del").bind("click", function () {
        let thetable = $(this).parents("table");
        let thetr = $(this).parents("tr");


        if ($(this).parents("tr").attr("data_val") != "" && $(this).parents("tr").attr("data_val") != "") {
            $.ajax({
                url: $(thetable).attr("finddell"),
                type: "post",
                async: false,
                data: {val: $(thetr).attr("data_val")},
                dataType: "json",
                success: function (data) {
                    if (data.ok == false) {
                        $.confirm({
                            confirmButtonClass: 'btn-info',
                            cancelButtonClass: 'btn-danger',
                            title: '提示',
                            content: data.data,
                            confirm: function () {
                                $.ajax({
                                    url: $(thetable).attr("dellrc"),
                                    type: "post",
                                    async: false,
                                    data: {val: $(thetr).attr("data_val")},
                                    dataType: "json",
                                    success: function (data) {
                                        thetr.remove();
                                    }
                                });
                            },
                            cancel: function () {

                            }
                        });
                    }else {
                        $.ajax({
                            url: $(thetable).attr("dellrc"),
                            type: "post",
                            async: false,
                            data: {val: $(thetr).attr("data_val")},
                            dataType: "json",
                            success: function (data) {
                                thetr.remove();
                            }
                        });

                    }
                }
            });

        } else {
            thetr.remove();
        }







    });
}

function viv(thetr) {
    for (var j = 0; j < $(thetr).find("td").length; j++) {
        thetd = $(thetr).find("td")[j];
        if ($(thetd).attr("type") == "text" || $(thetd).attr("type") == "select") {
            if ($(thetd).attr("notnull") == "1" && $(thetd).attr("data_val") == "")
                return false;
        }
    }
    return true;
}

function reload(t) {
    for (var i = 0; i < $("#" + t).children("tbody").find("tr").length; i++) {
        var val = {};
        var thetr = $("#" + t).children("tbody").find("tr")[i];
        //alert($(thetr).attr("data_key"));
        if ($(thetr).attr("up") == "1") {//修改过
            for (var j = 0; j < $(thetr).find("td").length; j++) {
                thetd = $(thetr).find("td")[j];
                $(thetr).attr("data_val") == "" ? val[$(thetr).attr("data_key")] = null : val[$(thetr).attr("data_key")] = $(thetr).attr("data_val");
                if ($(thetd).attr("type") == "text" || $(thetd).attr("type") == "select" || $(thetd).attr("type") == "notext") {
                    $(thetd).attr("data_val") == "" ? val[$(thetd).attr("data_key")] = null : val[$(thetd).attr("data_key")] = $(thetd).attr("data_val");
                }
            }
            $.ajax({
                url: $(thetr).parents("table").attr("savelrc"),
                type: "post",
                data: val,
                async: false,
                dataType: "json",
                success: function (data) {
                    $(thetr).attr("data_val", data[$(thetr).attr("data_key")]);
                    for (var j = 0; j < $(thetr).find("td").length; j++) {
                        thetd = $(thetr).find("td")[j];
                        $(thetd).attr("data_val", data[$(thetd).attr("data_key")]);
                    }
                    $(thetr).attr("up", "0");
                    $(thetr).attr("class", "success");
                },
                error: function (data) {
                    $(thetr).attr("class", "success");
                    console.log("服务器异常");
                }
            });
        }
    }
}