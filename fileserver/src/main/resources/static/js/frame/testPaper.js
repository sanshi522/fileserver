// JavaScript Document

$(function(){
	//获取学科
	function getsubtype(){
		$.ajax({
			url:"/subject/findAll",
			type:"post",
			data:{},
			dataType:"json",
			success: function(data) {
				$("#subjectId").empty();
				$("#subIdScreen").empty();
				$("#subIdScreen").append('<option value="0">全部学科</option>');
				for (let i = 0; i < data.length; i++) {
					$("#subjectId").append('<option value="' + data[i].id + '">' + data[i].name + '</option>');
					$("#subIdScreen").append('<option value="' + data[i].id + '">' + data[i].name + '</option>');
				}
				$('#subjectId').selectpicker('refresh');
				$('#subIdScreen').selectpicker('refresh');
				$('#subjectId').val(data[0].id).trigger("change");
				$('#subIdScreen').val(0).trigger("change");
			},
			error: function(data) {

			}
		});
	}
	getsubtype();
	var pageNumber =16 ; // 每页显示多少条记录
    var pageIndex= 0 ;//页码
	var subId=0;
	var likeName = "";

	//分页元素
    var total=50; // 总共多少记录
	//初始化单页显示条数
	$("#showNumber").val('10').trigger("change")
	$("#showNumber").change(function () {
		if(pageNumber!=$("#showNumber").val()){
			pageNumber=$("#showNumber").val();
			$("#Pagination").pagination(total, {
				callback : PageCallback,
				prev_text : '上一页',
				next_text : '下一页',
				items_per_page : pageNumber,
				num_display_entries : 4, // 连续分页主体部分显示的分页条目数
				num_edge_entries : 1, // 两侧显示的首尾分页的条目数
				jump:true,
			});
			Init(0);
		}
	});
    function PageCallback(index, jq) { // 前一个参数表示当前点击的那个分页的页数索引值，后一个参数表示装载容器。
        pageIndex=index;
        Init(pageIndex);
    }
	function Init(index) { // 参数就是点击的那个分页的页数索引值
        pageIndex=index;
		let val={
			"pageNumber":pageNumber,
			"pageIndex":pageIndex,
			"issistId":subId,
			"likeName":likeName==""?null:likeName
		}
        var win=window;
      $.ajax({
           url: "/testPaper/findAll",
         //contentType:"application/json;charset=UTF-8",
          type: "post",
           async: true,
           data:val,
           dataType: "json",
           success: function(data) {
           	if(data.resoult){
				$(".allTestPaper").empty();
				for(let i=0;i<data.page.content.length;i++){
					$.ajax({
						url: "/testPaper/findMsg",
						//contentType:"application/json;charset=UTF-8",
						type: "post",
						async: false,
						data:{id:data.page.content[i].id},
						dataType: "json",
						success: function(data2) {
							$(".allTestPaper").append('<div class="testPaper" style="">\n' +
								'        <table width="100%">\n' +
								'            <tr >\n' +
								'                <th colspan="2" style="text-align:center;">'+data.page.content[i].name+'</th>\n' +
								'            </tr>\n' +
								'            <tr>\n' +
								'                <td>学科：</td>\n' +
								'                <td>'+data2.subName+'</td>\n' +
								'            </tr>\n' +
								'            <tr>\n' +
								'                <td>试卷总分：</td>\n' +
								'                <td>'+data2.choiceScoreNum+'</td>\n' +
								'            </tr>\n' +
								'\n' +
								'            <tr>\n' +
								'                <td>题目数量：</td>\n' +
								'                <td>'+data2.choiceNum+'</td>\n' +
								'            </tr>\n' +
								'            <tr>\n' +
								'                <td>创建时间：</td>\n' +
								'                <td>'+data.page.content[i].createTime+'</td>\n' +
								'            </tr>\n' +
								'            <tr>\n' +
								'                <td>创建人：</td>\n' +
								'                <td>'+data2.creatUserName+'</td>\n' +
								'            </tr>\n' +
								'            <tr>\n' +
								'                <td>编辑操作：</td>\n' +
								'                <td style="font-size: 20px;">\n' +
								'                    <label style="margin-right: 10px;cursor: pointer;"><i onclick="javascript:parent.open(\'addTestPaper?id='+data.page.content[i].id+'\')" class="my-icon lsm-sidebar-icon icon-bianji del"></i></label>\n' +
								'                    <label><i class="my-icon lsm-sidebar-icon icon-shanchu del" style="cursor: pointer;"></i></label>\n' +
								'                </td>\n' +
								'            </tr>\n' +
								'\n' +
								'\n' +
								'        </table>\n' +
								'    </div>')
						},error(data2){
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
	
	$("#subIdScreen").change(function(){
		subId=$("#subIdScreen").val();
		Init(0);
	});

	$(".querybtn").click(function(){
		likeName=$("#query").val();
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
	popinit();
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
	//全部不选中状态
	function checkedno(){
		var obj = document.getElementsByName('opt');
		for (var i = 0; i < obj.length; i++) {
			obj[i].checked=false;
			$("#anwser").val('');
		}
	}
	//点击知识点
	$("#knowledge").click(function(){
		$(".addtext").show();
		$(".popWindow").css("min-width",'710px');
		$(".addtitel").html("添加知识点");
		$(".addcontext").empty();
		$(window).resize();
	});
	//点击附件
	$("#accessory").click(function(){
		$(".addtext").show();
		$(".popWindow").css("min-width",'710px');
		$(".addtitel").html("添加附件");
		$(".addcontext").empty();
		$(window).resize();
	});

	//获取某一试题信息
	function getchoice(){
		
	}
	var cassname="";
	//获取知识点
	function getknowleged(){
		 $.ajax({
                url:"http://localhost/subject/findAll",
				type:"post",
				data:{name:cassname},
				dataType:"json",
                success: function(data) {
					 alert(JSON.stringify(data));
				},
			  	error: function(data) {
                  
                }
            });
	}
	//获取附件
	function getaccessory(){
		$.ajax({
                url:"http://localhost/subject/findAll",
				type:"post",
				data:{name:cassname},
				dataType:"json",
                success: function(data) {
					 alert(JSON.stringify(data));
				},
			  	error: function(data) {
                  
                }
            });
	}
//////////////////////////////////////////////////////////////////////悬浮窗口js
});
function choicebind(){
	$(".input-cho").bind("click",function(){
		$(this).children('input')[0].click();
	});
	$(".pack-up").bind("click",function(){
		var bi=$(this).parent().parent().children(".choice-details").css("display")=="none"?"none":"block";
		$(".choice-details").css("display","none");
		$(this).parent().parent().children(".choice-details").css("display",bi=="none"?"block":"none");
		$(".pack-up").children("i").removeClass("icon-jiantoushang");
		$(".pack-up").children("i").addClass("icon-jiantouxia");
		if($(this).parent().parent().children(".choice-details").css("display")=="block"){
			$(this).children("i").removeClass("icon-jiantouxia");
			$(this).children("i").addClass("icon-jiantoushang");
		}
	});
}

