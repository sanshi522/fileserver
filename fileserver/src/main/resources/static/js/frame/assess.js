// JavaScript Document
var upident;
var issueId = 0;
var pageNumber = 10; // 每页显示多少条记录
var pageIndex = 0;//页码
var subId = 0;
var name = "";
var gradeId = 0;
var classId = 0;
//分页元素
var total = 50; // 总共多少记录
$(function () {



    $(".assessuser-add").click(function () {
        $(this).parent().attr("");

    })

	var startdatatime="";
	var enddatatime="";
	//起始时间部分
	countdown(new Date());
	function countdown(myDate) {
		//获取当前年
		var year=myDate.getFullYear();
		//获取当前月
		var month=myDate.getMonth()+1;
		//获取当前日
		var date=myDate.getDate();
		var h=myDate.getHours();       //获取当前小时数(0-23)
		var m=myDate.getMinutes();     //获取当前分钟数(0-59)
		var s=myDate.getSeconds();
		var now=year+'-'+getNow(month)+"-"+getNow(date)+" "+getNow(h)+':'+getNow(m)+":"+getNow(s);
		return now;
	}
	// 获取当前时间
	function getNow(s) {
		return s < 10 ? '0' + s: s;
	}
	Date.prototype.Format = function(fmt)
	{ //author: meizz
		var o = {
			"M+" : this.getMonth()+1,                 //月份
			"d+" : this.getDate(),                    //日
			"h+" : this.getHours(),                   //小时
			"m+" : this.getMinutes(),                 //分
			"s+" : this.getSeconds(),                 //秒
			"q+" : Math.floor((this.getMonth()+3)/3), //季度
			"S"  : this.getMilliseconds()             //毫秒
		};
		if(/(y+)/.test(fmt))
			fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substring(4 - RegExp.$1.length));
		for(var k in o)
			if(new RegExp("("+ k +")").test(fmt))
				fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substring((""+ o[k]).length)));
		return fmt;
	}
	//显示起始时间为系统当前时间
	$('#search-to-date').datetimepicker({
		format:'Y-m-d H:i:00',
		value:new Date().Format('yyyy-MM-dd hh:mm:ss'),
		theme: 'dark',
		step:1,
		onSelectTime: function(dateText, inst){
			startdatatime=dateText.Format('yyyy-MM-dd hh:mm:ss');
		}
	});
	$('#search-from-date').datetimepicker({
		format:'Y-m-d H:i:00',
		value:new Date().Format('yyyy-MM-dd hh:mm:ss'),
		theme: 'dark',
		step:1,
		onSelectTime: function(dateText, inst){
			enddatatime=dateText.Format('yyyy-MM-dd hh:mm:ss');
		}
	});

	$.datetimepicker.setLocale('zh');

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
                                    $("#selecttarget").append('<tr class="target" data_id=' + data.years[i].year +' ><td><td>' + data.years[i] + '</td>  <td><i class="my-icon lsm-sidebar-icon icon-tianjia assessuser-add"/></td></tr>');
                                }

                            checkItemAddBindClick();
                        }
                    }
                },
                error: function (data) {
                    console.log("获取学年服务器错误")
                }
            });
        } else {
            //获取所有管理员
            $.ajax({
                url: "Teacher/GetAdmin",
                type: "post",
                dataType: "json",
                success: function (data) {
                    if (data.resoult) {
                        $("#selecttarget").empty();
                        for (let i = 0; i < data.teachers.length; i++) {
                            $("#selecttarget").append('<tr class="target" ><td>' + data.teachers[i].teaName + '</td>  <td><i class="my-icon lsm-sidebar-icon icon-tianjia assessuser-add"/></td></tr>');
                        }
                        checkItemAddBindClick();
                    }
                },
                error: function (data) {
                    console.log("获取管理员服务器错误")
                }
            });
        }
    });

    //根据上传授权条件展示筛选框
    var paretid;
    $("#yearScreen").change(function () {
        paretid=$("#yearScreen").val();
        $("#gradeScreenDiv").css("display","none")
        $("#classScreenDiv").css("display","none");
        $("#groupScreenDiv").css("display","none");
        if (upident!=2)
            $("#gradeScreenDiv").css("display","block")
        //查询学年下的学院
        $.ajax({
            url:"Grade/GetGrade",
            type:"post",
            data: {yearNumber: paretid},
            dataType:"json",
            success:function(data) {
                if (data.resoult){
                    if (upident!=2){
                        $("#gradeScreen").empty();
                            for (let i=0;i<data.grades.length;i++){
                                $("#gradeScreen").append('<option value="'+data.grades[i].id+'">'+data.grades[i].name+'</option>');
                            }
                            $('#gradeScreen').selectpicker('refresh');
                            $('#gradeScreen').val(data.grades[0].id).trigger("change");
                    }else{
                        $("#selecttarget").empty();
                            $("#selecttarget").empty();
                            for (let i=0;i<data.grades.length;i++){
                                $("#selecttarget").append('<tr class="target" data_id='+ data.grades[i].id+' ><td><td>'+data.grades[i].name+'</td>  <td><i class="my-icon lsm-sidebar-icon icon-tianjia assessuser-add"/></td></tr>');
                            }
                        checkItemAddBindClick();
                    }
                }
            },
            error:function(data){
                console.log("获取院系服务器错误")
            }
        });
    });

    $("#gradeScreen").change(function () {
        paretid=$("#gradeScreen").val();
        $("#classScreenDiv").css("display","none");
        $("#groupScreenDiv").css("display","none");
        if (upident!=3)
            $("#classScreenDiv").css("display","block");
        $.ajax({
            url:"Class/GetClass",
            type:"post",
            data: {GradeId: paretid},
            dataType:"json",
            success:function(data) {
                if (data.resoult){
                    if (upident!=3){
                        $("#classScreen").empty();
                            for (let i=0;i<data.clases.length;i++){
                                $("#classScreen").append('<option value="'+data.clases[i].id+'">'+data.clases[i].name+'</option>');
                            }
                            $('#classScreen').selectpicker('refresh');
                            $('#classScreen').val(data.clases[0].id).trigger("change");

                    }else{
                        $("#selecttarget").empty();
                            for (let i=0;i<data.clases.length;i++){
                                $("#selecttarget").append('<tr class="target" data_id='+data.clases[i].id+'  ><td>'+data.clases[i].name+'</td> <td><i class="my-icon lsm-sidebar-icon icon-tianjia assessuser-add"/></td></tr>');
                            }

                        checkItemAddBindClick();
                    }
                }
            },
            error:function(data){
                console.log("获取班级服务器错误")
            }
        });
    });

    $("#classScreen").change(function () {
        paretid=$("#classScreen").val();
        $("#groupScreenDiv").css("display","none");
        if (upident!=4 && upident!=6)
            $("#groupScreenDiv").css("display","block");
        if (upident!=6){
            //查询班级下的小组
            $.ajax({
                url:"Group/GetGroup",
                type:"post",
                data: {CclassId: paretid},
                dataType:"json",
                success:function(data) {
                    if (data.resoult) {
                        if (upident != 4) {
                            $("#groupScreen").empty();
                                for (let i = 0; i < data.Groups.length; i++) {
                                    $("#groupScreen").append('<option value="' + data.Groups[i].id + '">' + data.Groups[i].name + '</option>');
                                $('#groupScreen').selectpicker('refresh');
                                $('#groupScreen').val(data.Groups[0].id).trigger("change");
                            }
                        } else {
                            $("#selecttarget").empty();
                                for (let i = 0; i < data.Groups.length; i++) {
                                    $("#selecttarget").append('<tr class="target"  data_id=' + data.Groups[i].id +'><td><td>' + data.Groups[i].name + '</td> <td><i class="my-icon lsm-sidebar-icon icon-tianjia assessuser-add"/></td></tr>');
                                }
                            checkItemAddBindClick();
                        }
                    }
                },
                error:function(data){
                    console.log("获取小组服务器错误")
                }
            });
        }
        if (upident==6){
            $.ajax({
                url:"Teacher/GetTeacher",
                type:"post",
                data: {classId: paretid},
                dataType:"json",
                success:function(data) {
                    if (data.resoult){
                        $("#selecttarget").empty();
                        for (let i = 0; i < data.teachers.length; i++) {
                            $("#selecttarget").append('<tr class="target"  data_id=' + data.teachers[i].teaId +'><td>' + data.teachers[i].teaName + '</td><td><i class="my-icon lsm-sidebar-icon icon-tianjia assessuser-add"/></td></tr>');
                        }
                        checkItemAddBindClick();
                    }
                },
                error:function(data){
                    console.log("获取老师服务器错误")
                }
            });
        }
    });


    $("#classScreen").change(function () {
        paretid=$("#classScreen").val();
        $("#groupScreenDiv").css("display","none");
        if (upident!=4 && upident!=6)
            $("#groupScreenDiv").css("display","block");
        if (upident!=6){
            //查询班级下的小组
            $.ajax({
                url:"Group/GetGroup",
                type:"post",
                data: {CclassId: paretid},
                dataType:"json",
                success:function(data) {
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
                                    $("#selecttarget").append('<tr class="target" ><td>' + data.Groups[i].name + '</td><td><i class="my-icon lsm-sidebar-icon icon-tianjia assessuser-add"/></td></tr>');
                                }

                            checkItemAddBindClick();
                        }
                    }
                },
                error:function(data){
                    console.log("获取小组服务器错误")
                }
            });
        }
        if (upident==6){
            $.ajax({
                url:"Teacher/GetTeacher",
                type:"post",
                data: {classId: paretid},
                dataType:"json",
                success:function(data) {
                    if (data.resoult){
                        $("#selecttarget").empty();
                        for (let i = 0; i < data.teachers.length; i++) {
                            $("#selecttarget").append('<tr class="target" ><td>' + data.teachers[i].teaName + '</td> <td><i class="my-icon lsm-sidebar-icon icon-tianjia assessuser-add"/></td></tr>');
                        }
                        checkItemAddBindClick();
                    }
                },
                error:function(data){
                    console.log("获取老师服务器错误")
                }
            });
        }
    });


    $("#groupScreen").change(function () {
        paretid=$("#groupScreen").val();
        //查询小组下的学生
        $.ajax({
            url:"Student/GetStudent",
            type:"post",
            data: {StuGroup: paretid},
            dataType:"json",
            success:function(data) {
                if (data.resoult){
                    $("#selecttarget").empty();
                    for (let i = 0; i < data.students.length; i++) {
                        $("#selecttarget").append('<tr class="target" data_id=' + data.students[i].stuId + '><td>' + data.students[i].stuName + '</td><td><i class="my-icon lsm-sidebar-icon icon-tianjia assessuser-add"/></td></tr>');
                    }
                    checkItemAddBindClick();
                }
            },
            error:function(data){
                console.log("获取学生服务器错误")
            }
        });
    });



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


    Init(0);
    getsubtype();
    Identity();
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
                                    '                    <label style="margin-right: 10px;cursor: pointer;"><i onclick="javascript:parent.open(\'addTestPaper?id=' + data.page.content[i].id + '\')" class="my-icon lsm-sidebar-icon icon-bianji del"></i></label>\n' +
                                    '                    <label><i class="my-icon lsm-sidebar-icon icon-shanchu del" style="cursor: pointer;"></i></label>\n' +
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

				total=data.page.totalElements;
				$(".totalmsg").html("【共"+total+"条记录，当前显示："+(data.page.pageable.pageNumber*data.page.pageable.pageSize+1)+"~"+(data.page.pageable.pageNumber*data.page.pageable.pageSize+data.page.numberOfElements)+"】");

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
		openWindw(e.pageX, e.pageY);
	});


/** 弹出框**/
function openWindw(x,y){
	ref();
	$(popWindow).css("left",x-popWindowWidth+'px');
	$(popWindow).css("top",y+'px');
	popWindow.show().animate({
        'left':positionLeft+'px',
        'top':positionTop+'px',
    },500);
    $('body').append(oMask);
    $('.mask').width(maskWidth).height(maskHeight);
	popWindow.removeClass("hide");
};
  $(window).resize(function(){
	  ref();
	  $('.mask').width(maskWidth).height(maskHeight);
    if(popWindow.is(':visible')){
		popWindow.animate({
            'left':positionLeft+'px',
            'top':positionTop+'px',
      },100);
    }
  });
  $(window).scroll(function(){
    if(popWindow.is(':visible')){
      browserScrollTop = $(window).scrollTop();
      browserScrollLeft = $(window).scrollLeft();
      //positionLeft = browserWidth/2 - popWindowWidth/2+browserScrollLeft;
      //positionTop = browserHeight/2 - popWindowHeight/2+browserScrollTop;
		positionLeft = browserWidth/2 - popWindowWidth/2;
      positionTop = browserHeight/2 - popWindowHeight/2;
      popWindow.animate({
            'left':positionLeft+'px',
            'top':positionTop+'px'
      },100).dequeue();
    }
  });
	$("#cancel").click(function(){
		popWindow.hide();
    	$('.mask').remove();
  	});
  oClose.click(function(){
    popWindow.hide();
    $('.mask').remove();
  });
	//切换题目类型
	$("#choicetype").change(function(){
		if($("#choicetype").val()==1){
			$(".choice_option_tr").show();
			$("#choicenum").change();
			$(".choice_option").attr("type","radio");
		}else if($("#choicetype").val()==2){
			$(".choice_option_tr").show();
			$("#choicenum").change();
			$(".choice_option").attr("type","checkbox");
		}else if($("#choicetype").val()==3){
			$(".choice_option_tr").hide();
		}else{
			$(".choice_option_tr").hide();
		}
	});
	$("#choicetype").change();
//设置选项个数
	$(".optionE").hide();
	$(".optionF").hide();
	$("#choicenum").change(function(){
		if($("#choicenum").val()==4){
			$(".choice_option_tr").show();
			$(".optionE").hide();
			$(".optionF").hide();
			checkedno();
		}else if($("#choicenum").val()==5){
			$(".choice_option_tr").show();
			$(".optionF").hide();
			checkedno();
		}else{
			$(".choice_option_tr").show();
		}
	});
//点击选项
	$(".choice_option").click(function(){
		var obj = document.getElementsByName('opt');
		var s = '';
		for (var i = 0; i < obj.length; i++) {
 			if (obj[i].checked){
				if(s=='')
					s += obj[i].value;
				else
					s += ','+ obj[i].value ;
			}
				
		}
		$("#anwser").val(s);
	});




	var cassname="";


//////////////////////////////////////////////////////////////////////悬浮窗口js
});


