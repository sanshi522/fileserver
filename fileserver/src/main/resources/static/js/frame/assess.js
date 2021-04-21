// JavaScript Document
$(function () {

    var upident;
    //指定类型的对象id集合
    var userList = [];
    function updatelist() {
        userList.splice(0);
        let users = $(".test_suer");
        users.each(function (i, user) {
            if ($(user).attr("data_ident") == upident) userList.push($(user).attr("data_id"));
        });
        let users1 = $(".assuser");
        users1.each(function (i, user) {
            if ($.inArray($(user).attr("data_id"), userList) >= 0)
                $(user).find(".assessuser-add").hide();
            else
                $(user).find(".assessuser-add").show();
        })

    }

    //考核所需参数
    var issueId = 0;
    //===================================================时间控件
    var startdatatime = "";
    var enddatatime = "";
    var startData = new Date();
    var endData = new Date();
    //起始时间部分
    countdown(new Date());

    function countdown(myDate) {
        //获取当前年
        var year = myDate.getFullYear();
        //获取当前月
        var month = myDate.getMonth() + 1;
        //获取当前日
        var date = myDate.getDate();
        var h = myDate.getHours();       //获取当前小时数(0-23)
        var m = myDate.getMinutes();     //获取当前分钟数(0-59)
        var s = myDate.getSeconds();
        var now = year + '-' + getNow(month) + "-" + getNow(date) + " " + getNow(h) + ':' + getNow(m) + ":" + getNow(s);
        return now;
    }

    // 获取当前时间
    function getNow(s) {
        return s < 10 ? '0' + s : s;
    }

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
    //显示起始时间为系统当前时间
    $('#search-to-date').datetimepicker({
        format: 'Y-m-d H:i:00',
        value: new Date().Format('yyyy-MM-dd hh:mm:ss'),
        theme: 'dark',
        step: 1,
        onSelectTime: function (dateText, inst) {
            endData = dateText;
            enddatatime = dateText.Format('yyyy-MM-dd hh:mm:ss');
        },
        onSelectDate: function (dateText, inst) {
            endData = dateText;
            enddatatime = dateText.Format('yyyy-MM-dd hh:mm:ss');
        }
    });
    $('#search-from-date').datetimepicker({
        format: 'Y-m-d H:i:00',
        value: new Date().Format('yyyy-MM-dd hh:mm:ss'),
        theme: 'dark',
        step: 1,
        onSelectTime: function (dateText, inst) {
            startData = dateText;
            startdatatime = dateText.Format('yyyy-MM-dd hh:mm:ss');
        },
        onSelectDate: function (dateText, inst) {
            startData = dateText;
            startdatatime = dateText.Format('yyyy-MM-dd hh:mm:ss');
        }
    });

	$.datetimepicker.setLocale('zh');
//===================================================时间控件
    /**
     * 获取身份
     */
    function  Identity() {
        $.ajax({
            url: "login/Islogin",
            type: "post",
            dataType: "json",
            async: false,
            success: function (data) {
                console.log(data);
                if (data.user.teaIdentity == 2 || data.user.teaIdentity == 3) {
                    $("#TeacherDiv").css("display", "block");
                    teachAll();
                }
            },
            error: function (data) {
                console.log('服务器异常');
            }
        });
    }

    /**
     * 查询所有老师
     */

    function teachAll() {
        $.ajax({
            url: "Teacher/findAll",
            type: "post",
            dataType: "json",
            async: false,
            success: function (data) {
                console.log(data);
                for (let i = 0; i < data.length; i++) {
                    $("#TeacherSelect").append('<option value="' + data[i].teaId + '">' + data[i].teaName + '</option>');
                }
                $('#TeacherSelect').selectpicker('refresh');
                $('#TeacherSelect').val(0).trigger("change");
            }
        })
    }

    //获取学科
    function getsubtype() {
        $.ajax({
            url: "/subject/findAll",
            type: "post",
            data: {},
            dataType: "json",
            success: function (data) {
                $("#subIdScreen2").empty();
                $("#subIdScreen").empty();
                $("#subIdScreen").append('<option value="0">全部学科</option>');
                for (let i = 0; i < data.length; i++) {
                    $("#subIdScreen").append('<option value="' + data[i].id + '">' + data[i].name + '</option>');
                    $("#subIdScreen2").append('<option value="' + data[i].id + '">' + data[i].name + '</option>');
                }
                $('#subIdScreen2').selectpicker('refresh');
                $('#subIdScreen2').val(data[0].id).trigger("change");
                $('#subIdScreen').selectpicker('refresh');
                $('#subIdScreen').val(0).trigger("change");
            },
            error: function (data) {

			}
		});
	}


	getsubtype();
    Identity();
    var pageNumber = 16; // 每页显示多少条记录
    var pageIndex = 0;//页码
    var subId = 0;
    var name = "";
    var gradeId = 0;
    var classId = 0;
    //分页元素
    var total = 50; // 总共多少记录
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
            Init(0);
        }
    });

    //初始化单页显示条数

    function PageCallback(index, jq) { // 前一个参数表示当前点击的那个分页的页数索引值，后一个参数表示装载容器。
        pageIndex = index;
        Init(pageIndex);
    }

    /**
     * 加载考核页面请求
     * @param index
     * @constructor
     */
    function Init(index) { // 参数就是点击的那个分页的页数索引值
        pageIndex = index;
        subId = $("#subIdScreen").val();
        issueId=$("#TeacherSelect").val();
        let ScreenAssess = {
            "pageNumber": pageNumber,
            "pageIndex": pageIndex,
            "subId": subId,
            "name": name,
            "gradeId": gradeId,
            "classId": classId,
            "issueId": issueId
        }
        var win = window;
        $.ajax({
            url: "/Assess/findAll",
            contentType: "application/json;charset=UTF-8",
            type: "post",
            data: JSON.stringify(ScreenAssess),
            dataType: "json",
            success: function (data) {
                console.log(data);
                if (data) {
                    $(".allTestPaper").empty();
                    for (let i = 0; i < data.page.content.length; i++) {
                        $.ajax({
                            url: "/Assess/findMsg",
                            //contentType:"application/json;charset=UTF-8",
                            type: "post",
                            async: false,
                            data: {id: data.page.content[i].id},
                            dataType: "json",
                            success: function (data2) {
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
                                    '\n' +
                                    '            <tr>\n' +
                                    '                <td>批阅/未批阅：</td>\n' +
                                    '                <td>' + data2.redNumber +"/"+data2.notredNumber + '</td>\n' +
                                    '            </tr>\n' +
                                    '            <tr>\n' +
                                    '                <td>题目数量：</td>\n' +
                                    '                <td>' + data2.choiceNum + '</td>\n' +
                                    '            </tr>\n' +
                                    '            <tr>\n' +
                                    '                <td>创建时间：</td>\n' +
                                    '                <td>' + data.page.content[i].createTime + '</td>\n' +
                                    '            </tr>\n' +
                                    '            <tr>\n' +
                                    '                <td>发布人：</td>\n' +
                                    '                <td>' + data2.name + '</td>\n' +
                                    '            </tr>\n' +
                                    '            <tr>\n' +
                                    '                <td>编辑操作：</td>\n' +
                                    '                <td style="font-size: 20px;">\n' +
                                    '                    <label style="margin-right: 10px;cursor: pointer;"><i  id=' + data.page.content[i].id + ' class="my-icon lsm-sidebar-icon icon-bianji"></i></label>\n' +
                                    '                    <label><i class="my-icon lsm-sidebar-icon icon-shanchu " id=' + data.page.content[i].id + ' style="cursor: pointer;"></i></label>\n' +
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
                    deltelAssess();
                    Enint();
                    total = data.page.totalElements;
                    $(".totalmsg").html("【共" + total + "条记录，当前显示：" + (data.page.pageable.pageNumber * data.page.pageable.pageSize + 1) + "~" + (data.page.pageable.pageNumber * data.page.pageable.pageSize + data.page.numberOfElements) + "】");
                }

           	console.log(data);

         	}, error: function(data){
               console.log("服务器异常");
         	}
			});
    }

    /**学科筛选 **/
    $("#subIdScreen").change(function () {
        subId = $("#subIdScreen").val();
        Init(0);

    });
    /**搜索 **/
    $(".querybtn").click(function () {
        name = $("#query").val();
        Init(0);
    });
    /**老师赛选**/
    $("#TeacherSelect").change(function () {
		issueId=$("#TeacherSelect").val();
		Init(0);
	});

    /**编辑绑定框*/
    function Enint() {
        $(".icon-bianji").bind("click", function (e) {
            var assId = $(this).attr("id");
            $.ajax({
                url: "/Assess/findById",
                type: "post",
                data: {"assessId": assId},
                dataType: "json",
                success: function (data) {
                    console.log(data);
                    openWindw(e.pageX, e.pageY);
                    $("#AssessId").val(data.assess.id);
                    $("#assname").val(data.assess.name);
                    $("#subIdScreen2").val(data.assess.subId).trigger("change");
                    $("#testpaper_id").val(data.testPaper.name);
                    $("#make_time").val(data.assess.makeTime);
                    $("#search-from-date").val(data.assess.startTime);
                    $("#search-to-date").val(data.assess.endTime);
                    endData = new Date(data.assess.endTime);
                    startData = new Date(data.assess.startTime);
                    $("#testpaper_id").attr("data_value", data.testPaper.id);
                    $("#testObject").empty();
                    for (var i = 0; i < data.listAssessUser.length; i++) {
                        var testObjectname = "";
                        if (data.listAssessUser[i].assessUser.testObject == 1) {
                            testObjectname = "学年"
                        } else if (data.listAssessUser[i].assessUser.testObject == 2) {
                            testObjectname = "学院"
                        } else if (data.listAssessUser[i].assessUser.testObject == 3) {
                            testObjectname = "班级"
                        } else if (data.listAssessUser[i].assessUser.testObject == 4) {
                            testObjectname = "小组"
                        } else if (data.listAssessUser[i].assessUser.testObject == 5) {
                            testObjectname = "学生"
                        }
                        $("#testObject").append('<tr class="test_suer" data_ident=' + data.listAssessUser[i].assessUser.testObject + '  data_id=' + data.listAssessUser[i].assessUser.testObjectId + ' data_name=' + data.listAssessUser[i].name + ' ><td>' + testObjectname + '</td><td>' + data.listAssessUser[i].name + '</td><td style="text-align:right;"><i class="my-icon lsm-sidebar-icon icon-shanchu assessuser-del"></i></td></tr>');
                    }
                    updatelist();
                    assessuser_delbind();
                },
                error: function (data) {
                    console.log("服务器异常");
                }
            })
        });
    }
    //删除考核

 function  deltelAssess() {
$(".icon-shanchu").bind("click",function () {
    var assId = $(this).attr("id");
    $.confirm({
        confirmButtonClass: 'btn-info',
        cancelButtonClass: 'btn-danger',
        title: '提示',
        content: '确定要删除该考核吗？',
        confirm: function () {
            $.ajax({
                url: "/Assess/delete",
                type: "post",
                async: false,
                data: {"assessId": assId},
                dataType: "json",
                success:function (data) {
                    $.alert(data.data);
                    window.location.reload();
                },
                error: function (data) {
                    console.log("服务器异常");
                }
            });
        },
        cancel: function () {
        }
    });

})

 }


//////////////////////////////////////////////////////////////////////悬浮窗口js
  //var oBtn = $('#show');
  	var popWindow = $('.popWindow');
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
  var positionLeft = browserWidth/2 - popWindowWidth/2+browserScrollLeft;
  //top的值＝浏览器可视区域的高度／2－弹出窗口的高度／2+浏览器纵向滚动条距离上边界的值
  var positionTop = browserHeight/2 - popWindowHeight/2+browserScrollTop;
  var oMask = '<div class="mask"></div>'
  //遮照层的宽度
  var maskWidth = $(document).width();
  //遮照层的高度
  var maskHeight = $(document).height();
	//刷新
function ref(){
	maskWidth = $(document).width();
	maskHeight = $(document).height();
	$(popWindow).css("max-width",maskWidth/2+'px');
	$(popWindow).css("max-height",maskHeight+'px');
	$(".wContent").css("max-height",maskHeight/3*2+'px');
	$(".addcontext").css("height",$(".wContent").height());
//	popWindowWidth = popWindow.outerWidth(true);
//	popWindowHeight = popWindow.outerHeight(true);
	popWindowWidth = $(popWindow).width();
	popWindowHeight = $(popWindow).height();
	browserWidth = $(window).width();
    browserHeight = $(window).height();
    positionLeft = browserWidth/2 - popWindowWidth/2+browserScrollLeft;
	positionTop = browserHeight/2 - popWindowHeight/2+browserScrollTop;


    }

    $("#addAssess").click(function (e) {
        $("#AssessId").val(null);
        $("#assname").val("");
        $("#make_time").val(null);
        $("#search-from-date").val(new Date().Format('yyyy-MM-dd hh:mm:ss'));
        $("#search-to-date").val(new Date().Format('yyyy-MM-dd hh:mm:ss'));
        $("#testpaper_id").attr("data_value", "");
        $("#testpaper_id").val("");
        $("#testObject").empty();
        $("#addUser_div").hide();
        $("#screenTest_div").hide();
        updatelist();
        openWindw(e.pageX, e.pageY);
    });


    /** 弹出框**/
    function openWindw(x, y) {
        $(".popWindow").css("width", "320px");
        $("#addUser_div").hide();
        $("#screenTest_div").hide();
        ref();
        $(popWindow).css("left", x - popWindowWidth + 'px');
        $(popWindow).css("top", y + 'px');
        popWindow.show().animate({
            'left': positionLeft + 'px',
            'top': positionTop + 'px',
        }, 500);
        $('body').append(oMask);
        $('.mask').width(maskWidth).height(maskHeight);
        popWindow.removeClass("hide");
    };
    $(window).resize(function () {
        ref();
        $('.mask').width(maskWidth).height(maskHeight);
        if (popWindow.is(':visible')) {
            popWindow.animate({
                'left': positionLeft + 'px',
                'top': positionTop + 'px',
            }, 100);
        }
    });
    $(window).scroll(function () {
        if (popWindow.is(':visible')) {
            browserScrollTop = $(window).scrollTop();
            browserScrollLeft = $(window).scrollLeft();
            //positionLeft = browserWidth/2 - popWindowWidth/2+browserScrollLeft;
            //positionTop = browserHeight/2 - popWindowHeight/2+browserScrollTop;
            positionLeft = browserWidth / 2 - popWindowWidth / 2;
            positionTop = browserHeight / 2 - popWindowHeight / 2;
            popWindow.animate({
                'left': positionLeft + 'px',
                'top': positionTop + 'px'
            }, 100).dequeue();
        }
    });
    $("#cancel").click(function () {
        popWindow.hide();
        $('.mask').remove();
    });

	$("#make_time").bind("keyup",function () {
        this.value = this.value.replace(/[^\d]/g, "");
    });


	//试卷分页控件部分
	var pageNumber1 =10 ; // 每页显示多少条记录
	var pageIndex1= 0 ;//页码
	//分页元素
	var total1=0; // 总共多少记录
	let likeName1="";
	//弹出框学科id
	let subId1=0;
	//学科选择
	$("#subIdScreen2").change(function () {
		subId1=$(this).val();
		if($("#screenTest_div").is(":visible")) findTesTPaper(0);
		$("#testpaper_id").val("");
		$("#testpaper_id").attr("data_value",0);
	})
	//点击试卷框
	$("#testpaper_id").click(function () {
		$(".popWindow").css("width","auto");
		$("#addUser_div").hide();
		$("#screenTest_div").show();
		if (subId1==0) $.alert("请选择考核学科！");
		findTesTPaper(0);
	});
	//点击添加考核对象添加按钮
	$(".add-assessuser").click(function () {
		$(".popWindow").css("width","auto");
		$("#addUser_div").show();
		$("#screenTest_div").hide();
        $("#queryLevels").change();
	})
	//弹出框试题搜索
	$(".querybtn1").click(function(){
		likeName1=$("#query1").val();
		findTesTPaper(0);
	});
	$("#Pagination1").pagination(total1, {
				callback : PageCallback1,
				prev_text : '上一页',
				next_text : '下一页',
				items_per_page : pageNumber,
				num_display_entries : 4, // 连续分页主体部分显示的分页条目数
				num_edge_entries : 1, // 两侧显示的首尾分页的条目数
				jump:true,
	});
	function PageCallback1(index, jq) { // 前一个参数表示当前点击的那个分页的页数索引值，后一个参数表示装载容器。
		pageIndex=index;
		findTesTPaper(pageIndex);
	}
	//加载试卷
	function findTesTPaper(index){
		pageIndex=index;
		let val={
			"pageNumber":pageNumber1,
			"pageIndex":pageIndex1,
			"issistId":subId1,
			"likeName":likeName1==""?null:likeName1
		}
		$.ajax({
			url: "/testPaper/findAll",
			//contentType:"application/json;charset=UTF-8",
			type: "post",
			async: true,
			data:val,
			dataType: "json",
			success: function(data) {
				if(data.resoult){
					$("#allTestPaper1").empty();
					for(let i=0;i<data.page.content.length;i++){
						$.ajax({
							url: "/testPaper/findMsg",
							//contentType:"application/json;charset=UTF-8",
							type: "post",
							async: false,
							data:{id:data.page.content[i].id},
							dataType: "json",
							success: function(data2) {
								$("#allTestPaper1").append(' <tr class="testpa" test_id="'+data.page.content[i].id+'" test_name="'+data.page.content[i].name+'"><td>'+data.page.content[i].name+'</td><td>'+data2.choiceNum+'</td><td>'+data2.choiceScoreNum+'</td><td style="text-align: right;"><i class="my-icon lsm-sidebar-icon icon-tianjia test-change"/></td></tr>');
							},error(data2){
							}
						});
					}
					total1=data.page.totalElements;
					$(".totalmsg1").html("【共"+total1+"条记录，当前显示："+(data.page.pageable.pageNumber*data.page.pageable.pageSize+1)+"~"+(data.page.pageable.pageNumber*data.page.pageable.pageSize+data.page.numberOfElements)+"】");
					TestPaperBind();
				}
				console.log(data);

			}, error: function(data){
				console.log("服务器异常");
			}
		});
	}
	function TestPaperBind(){
		$('.test-change').bind("click",function () {
			$("#testpaper_id").val($(this).parent().parent().attr("test_name"));
			$("#testpaper_id").attr("data_value",$(this).parent().parent().attr("test_id"));
		})
	}


//指定类型的对象id集合
    var userList=[];
    function updatelist(){
        userList.splice(0);
        let users=$(".test_suer");
        users.each(function (i, user) {
            if ($(user).attr("data_ident")==upident) userList.push($(user).attr("data_id"));
        });
        let users1=$(".assuser");
        users1.each(function (i, user) {
            if ($.inArray($(user).attr("data_id"),userList)>=0)
                $(user).find(".assessuser-add").hide();
            else
                $(user).find(".assessuser-add").show();
        })

    }
    /**
     * 考核对象
     */
    $("#queryLevels").change(function() {
        $("#checkAll").prop("checked",false);//全选按钮取消全选
        upident = $("#queryLevels").val();
        //切换则查询
        //根据权限级别显示二级筛选框
        $("#selecttarget").empty();
        if (upident != '1')
            $("#yearScreenDiv").css("display", "block");
        if (upident == '7' || upident == '1')
            $("#yearScreenDiv").css("display", "none");
        $("#gradeScreenDiv").css("display", "none");
        $("#classScreenDiv").css("display", "none");
        $("#groupScreenDiv").css("display", "none");

        if (upident != 7) {
            //获取该身份下的学年
            $.ajax({
                url: "Grade/GetYear",
                type: "post",
                dataType: "json",
                success: function (data) {
                    if (data.resoult) {
                        if (upident != 1) {
                            $("#yearScreen").empty();
                            for (let i = 0; i < data.years.length; i++) {
                                $("#yearScreen").append('<option value="' + data.years[i] + '">' + data.years[i] + '</option>');
                            }
                            $('#yearScreen').selectpicker('refresh');
                            $('#yearScreen').val(data.years[0]).trigger("change");
                        } else {
                            $("#selecttarget").empty();
                            $("#selecttarget").empty();
                            for (let i = 0; i < data.years.length; i++) {
                                $("#selecttarget").append('<tr class="assuser" data_id=' + data.years[i] + ' data_name=' + data.years[i] + ' ><td>' + data.years[i] + '</td>  <td  style="text-align:right;"><i class="my-icon lsm-sidebar-icon icon-tianjia assessuser-add"/></td></tr>');
                            }
                            assessuser_addbind();

                            updatelist();
                        }
                    }
                },
                error: function (data) {
                    console.log("获取学年服务器错误")
                }
            });
        }
    });

    //根据上传授权条件展示筛选框
    var paretid;
    $("#yearScreen").change(function () {
        paretid = $("#yearScreen").val();
        $("#gradeScreenDiv").css("display", "none")
        $("#classScreenDiv").css("display", "none");
        $("#groupScreenDiv").css("display", "none");
        if (upident != 2)
            $("#gradeScreenDiv").css("display", "block")
        //查询学年下的学院
        $.ajax({
            url: "Grade/GetGrade",
            type: "post",
            data: {yearNumber: paretid},
            dataType: "json",
            success: function (data) {
                if (data.resoult) {
                    if (upident != 2) {
                        $("#gradeScreen").empty();
                        for (let i = 0; i < data.grades.length; i++) {
                            $("#gradeScreen").append('<option value="' + data.grades[i].id + '">' + data.grades[i].name + '</option>');
                        }
                        $('#gradeScreen').selectpicker('refresh');
                        $('#gradeScreen').val(data.grades[0].id).trigger("change");
                    } else {
                        $("#selecttarget").empty();
                        $("#selecttarget").empty();
                        for (let i = 0; i < data.grades.length; i++) {
                            $("#selecttarget").append('<tr class="assuser" data_id=' + data.grades[i].id + ' data_name=' + data.grades[i].name + ' ><td>' + data.grades[i].name + '</td>  <td style="text-align:right;"><i class="my-icon lsm-sidebar-icon icon-tianjia assessuser-add"/></td></tr>');
                        }

                        assessuser_addbind();
                        updatelist();
                    }
                }
            },
            error: function (data) {
                console.log("获取院系服务器错误")
            }
        });
    });

    $("#gradeScreen").change(function () {
        paretid = $("#gradeScreen").val();
        $("#classScreenDiv").css("display", "none");
        $("#groupScreenDiv").css("display", "none");
        if (upident != 3)
            $("#classScreenDiv").css("display", "block");
        $.ajax({
            url: "Class/GetClass",
            type: "post",
            data: {GradeId: paretid},
            dataType: "json",
            success: function (data) {
                if (data.resoult) {
                    if (upident != 3) {
                        $("#classScreen").empty();
                        for (let i = 0; i < data.clases.length; i++) {
                            $("#classScreen").append('<option value="' + data.clases[i].id + '">' + data.clases[i].name + '</option>');
                        }
                        $('#classScreen').selectpicker('refresh');
                        $('#classScreen').val(data.clases[0].id).trigger("change");

                    } else {
                        $("#selecttarget").empty();
                        for (let i = 0; i < data.clases.length; i++) {
                            $("#selecttarget").append('<tr class="assuser"  data_id=' + data.clases[i].id + ' data_name=' + data.clases[i].name + ' ><td>' + data.clases[i].name + '</td> <td  style="text-align:right;"><i class="my-icon lsm-sidebar-icon icon-tianjia assessuser-add"/></td></tr>');
                        }
                        assessuser_addbind();
                        updatelist();
                    }
                }
            },
            error: function (data) {
                console.log("获取班级服务器错误")
            }
        });
    });

    $("#classScreen").change(function () {
        paretid = $("#classScreen").val();
        $("#groupScreenDiv").css("display", "none");
        if (upident != 4 && upident != 6)
            $("#groupScreenDiv").css("display", "block");
        if (upident != 6) {
            //查询班级下的小组
            $.ajax({
                url: "Group/GetGroup",
                type: "post",
                data: {CclassId: paretid},
                dataType: "json",
                success: function (data) {
                    if (data.resoult) {
                        if (upident != 4) {
                            $("#groupScreen").empty();
                            for (let i = 0; i < data.Groups.length; i++) {
                                $("#groupScreen").append('<option value="' + data.Groups[i].id + '">' + data.Groups[i].name + '</option>');

                            }
                            $('#groupScreen').selectpicker('refresh');
                            $('#groupScreen').val(data.Groups[0].id).trigger("change");
                        } else {
                            $("#selecttarget").empty();
                            for (let i = 0; i < data.Groups.length; i++) {
                                $("#selecttarget").append('<tr class="assuser"  data_id=' + data.Groups[i].id + ' data_name=' + data.Groups[i].name + ' ><td>' + data.Groups[i].name + '</td> <td  style="text-align:right;"><i class="my-icon lsm-sidebar-icon icon-tianjia assessuser-add"/></td></tr>');
                            }

                            assessuser_addbind();
                            updatelist();
                        }
                    }
                },
                error: function (data) {
                    console.log("获取小组服务器错误")
                }
            });
        }
        if (upident == 6) {
            $.ajax({
                url: "Teacher/GetTeacher",
                type: "post",
                data: {classId: paretid},
                dataType: "json",
                success: function (data) {
                    if (data.resoult) {
                        $("#selecttarget").empty();
                        for (let i = 0; i < data.teachers.length; i++) {
                            $("#selecttarget").append('<tr class="assuser"  data_id=' + data.teachers[i].teaId + '  data_name=' + data.teachers[i].teaName + ' ><td>' + data.teachers[i].teaName + '</td><td style="text-align:right;"><i class="my-icon lsm-sidebar-icon icon-tianjia assessuser-add"/></td></tr>');
                        }

                        assessuser_addbind();
                        updatelist();
                    }
                },
                error: function (data) {
                    console.log("获取老师服务器错误")
                }
            });
        }
    });


    $("#groupScreen").change(function () {
        paretid = $("#groupScreen").val();
        //查询小组下的学生
        console.log("进来了");
        $.ajax({
            url: "Student/GetStudent",
            type: "post",
            data: {StuGroup: paretid},
            dataType: "json",
            success: function (data) {
                if (data.resoult) {
                    $("#selecttarget").empty();
                    for (let i = 0; i < data.students.length; i++) {
                        $("#selecttarget").append('<tr class="assuser" data_id=' + data.students[i].stuId + ' data_name=' + data.students[i].stuName + '><td>' + data.students[i].stuName + '</td><td  style="text-align:right;"><i class="my-icon lsm-sidebar-icon icon-tianjia assessuser-add"/></td></tr>');
                    }
                    assessuser_addbind();
                    updatelist();
                }
            },
            error: function (data) {
                console.log("获取学生服务器错误")
            }
        });
    });


    //添加考核对象bind方法
    function assessuser_addbind(){
        $(".assessuser-add").bind("click", function () {
            var id=  $(this).parent().parent().attr("data_id");
            var testObject=$("#queryLevels").val();
            var testObjectname =$("#queryLevels option:selected").text();
            var  name= $(this).parent().parent().attr("data_name");
            console.log(id +"-"+testObject+""+name+""+testObjectname);
            $("#testObject").append('<tr class="test_suer" data_ident='+$("#queryLevels").val()+'  data_id='+id+' data_name='+name+' ><td>'+testObjectname+'</td><td>'+name+'</td><td style="text-align:right;"><i class="my-icon lsm-sidebar-icon icon-shanchu assessuser-del"></i></td></tr>');
            updatelist();
            assessuser_delbind();
        });
    }
//删除考核bind对象
    function assessuser_delbind() {
        $(".assessuser-del").bind("click", function () {
            $(this).parent().parent().remove();
            updatelist();
        });
    }

//保存考核
    $("#save_ok").bind("click", function () {
        let AssessList = [];
        let users = $(".test_suer");
        users.each(function (i, user) {
            let AssessUser = {
                "assessId": $("#AssessId").val(),
                "testObject": $(user).attr("data_ident"),
                "testObjectId": $(user).attr("data_id"),
            }
            AssessList.push(AssessUser);
        });
        console.log(endData + "" + startData);
        if ($("#assname").val() == "") {
            $.alert("考核名称不能为空");
            return;
        }
        if ($("#subIdScreen2").val() == "") {
            $.alert("学科不能为空");
            return;
        }
        if ($("#testpaper_id").val() == "") {
            $.alert("试卷不能为空");
            return;
        }
        if ($("#make_time").val() == "") {
            $.alert("答题时间不能为空");
            return;
        }
        if ($("#search-from-date").val() == "") {
            $.alert("开始时间不能为空");
            return;
        }
        if ($("#search-to-date").val() == "") {
            $.alert("结束时间不能为空");
            return;
        }
        if (endData.getTime() <= startData.getTime()) {
            $.alert("结束时间不能早于开始时间");
            return;
        }
        ;
        if (AssessList.length < 1) {
            $.alert("考核对象不能为空");
            return;
        }
        let Assess = {
            "id": $("#AssessId").val(),
            "name": $("#assname").val(),
            "subId": $("#subIdScreen2").val(),
            "testPaperId": $("#testpaper_id").attr("data_value"),
            "startTime": startData.Format('yyyy-MM-dd hh:mm:ss'),
            "endTime": endData.Format('yyyy-MM-dd hh:mm:ss'),
            "makeTime": $("#make_time").val()
        }

        let AssessUserVo = {
            "assess": Assess,
            "listAssessUser": AssessList
        }

        $.ajax({
            url: "Assess/save",
            contentType: "application/json;charset=UTF-8",
            type: "post",
            data: JSON.stringify(AssessUserVo),
            dataType: "json",
            success: function (data) {
                if ($("#AssessId").val() != "" && $("#AssessId").val() != null) {
                    $.alert("修改成功");
                } else {
                    $.alert("添加成功");
                }
                popWindow.hide();
                $('.mask').remove();
                Init(0);

            },
            error: function (data) {
                console.log("服务器异常");

            }

        })

    });


});





