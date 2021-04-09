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
	choicebind();
	getsubtype();
	var pageNumber = 10; // 每页显示多少条记录
    var pageIndex= 0 ;//页码
	var subId=0;
	var choicetype=0;
	var likeName = "";
    var sort="";
    var sortName="";
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

	$("#subIdScreen").change(function(){
		subId=$("#subIdScreen").val();
		Init(0);
	});
	$("#typeScreen").change(function(){
		choicetype=$("#typeScreen").val();
		Init(0);
	});

	$(".querybtn").click(function(){
		likeName=$("#query").val();
		Init(0);
	});
	function Init(index) { // 参数就是点击的那个分页的页数索引值
		pageIndex=index;
		let ScreenChoice = {
			"pageNumber": pageNumber,
			"pageIndex": pageIndex,
			"subId": subId,
			"type": choicetype,
			"name": likeName,
			"sort": sort,
			"sortName": sortName,
			"abilityIds":null,
			"difficultyLevel":null
		}
		var win=window;
		$.ajax({
			url: "/choice/findAll",
			contentType:"application/json;charset=UTF-8",
			type: "post",
			async: false,
			data:JSON.stringify(ScreenChoice),
			dataType: "json",
			success: function(data) {
				$(".allchoice").empty();
				let difficul="";
				for(let i=0;i<data.page.content.length;i++){
					difficul="";
					for (let k=0;k<5;k++){
						if (k<data.page.content[i].difficultyLevel)
							difficul+='<label style="color: #85822b;font-size: 14px;">★</label>';
						else
							difficul+='<label style="color: #9d9ea3;font-size: 14px;">★</label>';
					}
					if(data.page.content[i].type==1||data.page.content[i].type==2){//单选题和多选
						let type="";
						let opent="";
						if (data.page.content[i].type==1)type="单选题";else type="多选题"
						for (let o=0;o<data.page.content[i].optionNum;o++){
							let op='';
							switch (o) {
								case 0:op="A."+data.page.content[i].optionA;break;
								case 1:op="B."+data.page.content[i].optionB;break;
								case 2:op="C."+data.page.content[i].optionB;break;
								case 3:op="D."+data.page.content[i].optionD;break;
								case 4:op="E."+data.page.content[i].optionE;break;
								default:op="F."+data.page.content[i].optionF;break;
							}
							if (data.page.content[i].type==1)
								opent+='<div class="input-cho"><input class="single" type="radio" name="single1" />'+op+'</div>\n';
							else
								opent+='<div class="input-cho"><input class="single" type="checkbox" name="single1" />'+op+'</div>\n';
						}

						$(".allchoice").append('<div class="choice" choiceid="'+data.page.content[i].id+'">\n'+
								'<div class="choice-title"><div class="choice-type" data="'+data.page.content[i].type+'">'+type+'</div><div class="choice-name" >'+data.page.content[i].topic+'</div>\n' +
								'<div class="choice-oper pack-up"><i class="my-icon lsm-sidebar-icon  icon-jiantouxia"></i></div>\n' +
								'<div class="choice-oper delete-choice"><i class="my-icon lsm-sidebar-icon icon-shanchu "></i></div>\n' +
								'<div class="choice-oper edit-choice"><i class="my-icon lsm-sidebar-icon icon-bianji "></i></div>\n' +
							'</div>\n' +
							'<div class="choice-details">\n' +
								'<div class="make-choice">'+opent+'</div>\n'+
							'<div class="answer">标答：'+data.page.content[i].correct+'</div>\n' +
							'<div class="difficulty">难度：'+difficul+'</div>\n' +
							'<div class="analysis">解析：'+data.page.content[i].analysis+'</div>\n' +
							'<div class="knowledge">知识点：</div>\n' +
							'</div>\n' +
							'</div>');
					}else if(data.page.content[i].type==3){//判断题
						let correct=""
						if (data.page.content[i].correct==1) correct="正确";else correct="错误";
						$(".allchoice").append('<div class="choice"  choiceid="'+data.page.content[i].id+'">\n' +
							'\t\t\t<div class="choice-title"><div class="choice-type" data="'+data.page.content[i].type+'">判断题</div><div class="choice-name" >'+data.page.content[i].topic+'</div>\n' +
							'\t\t\t\t<div class="choice-oper pack-up"><i class="my-icon lsm-sidebar-icon  icon-jiantouxia"></i></div>\n' +
							'\t\t\t\t<div class="choice-oper delete-choice"><i class="my-icon lsm-sidebar-icon icon-shanchu "></i></div>\n' +
							'\t\t\t\t<div class="choice-oper edit-choice"><i class="my-icon lsm-sidebar-icon icon-bianji "></i></div>\n' +
							'\t\t\t</div>\n' +
							'\t\t\t<div class="choice-details">\n' +
							'\t\t\t\t<div class="make-choice">\n' +
							'\t\t\t\t\t<div class="input-cho"><input class="single" type="radio" name="single3" />正确</div>\n' +
							'\t\t\t\t\t<div class="input-cho"><input class="single" type="radio" name="single3" />错误</div>\n' +
							'\t\t\t\t</div>\n' +
							'\t\t\t\t<div class="answer">标答：'+correct+'</div>\n' +
							'\t\t\t\t<div class="difficulty">难度：'+difficul+'</div>\n' +
							'\t\t\t\t<div class="analysis">解析：'+data.page.content[i].analysis+'</div>\n' +
							'\t\t\t\t<div class="knowledge">知识点：</div>\n' +
							'\t\t\t</div>\n' +
							'\t\t</div>');
					}else if(data.page.content[i].type==4){//简答题
						$(".allchoice").append('<div class="choice"  choiceid="'+data.page.content[i].id+'">\n' +
							'\t\t\t<div class="choice-title"><div class="choice-type" data="'+data.page.content[i].type+'">简答题</div><div class="choice-name" >'+data.page.content[i].topic+'</div>\n' +
							'\t\t\t\t<div class="choice-oper pack-up"><i class="my-icon lsm-sidebar-icon  icon-jiantouxia"></i></div>\n' +
							'\t\t\t\t<div class="choice-oper delete-choice"><i class="my-icon lsm-sidebar-icon icon-shanchu "></i></div>\n' +
							'\t\t\t\t<div class="choice-oper edit-choice"><i class="my-icon lsm-sidebar-icon icon-bianji "></i></div>\n' +
							'\t\t\t</div>\n' +
							'\t\t\t<div class="choice-details">\n' +
							'\t\t\t\t<div class="make-choice">\n' +
							'\t\t\t\t\t<div class="text-cho"><textarea rows="3" cols="50" wrap="hard"  placeholder="请输入参考答案"></textarea></div>\n' +
							'\t\t\t\t</div>\n' +
							'\t\t\t\t<div class="answer">标答：'+data.page.content[i].correct+'</div>\n' +
							'\t\t\t\t<div class="difficulty">难度：'+difficul+'</div>\n' +
							'\t\t\t\t<div class="analysis">解析：'+data.page.content[i].analysis+'</div>\n' +
							'\t\t\t\t<div class="knowledge">知识点：</div>\n' +
							'\t\t\t</div>\n' +
							'\t\t</div>');
					}
				}
				choicebind();
				total=data.page.totalElements;
				$(".totalmsg").html("【共"+total+"条记录，当前显示："+(data.page.pageable.pageNumber*data.page.pageable.pageSize+1)+"~"+(data.page.pageable.pageNumber*data.page.pageable.pageSize+data.page.numberOfElements)+"】");
			},
			error: function(data){
				console.log("服务器异常");
			}
		});
	}

	$("#addchoice").click(function(e){
		//清空savchoice内容
		openWindw(e.pageX,e.pageY,0);
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
function openWindw(x,y,id){
	ref();
	popinit(id);
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
      // positionLeft = browserWidth/2 - popWindowWidth/2+browserScrollLeft;
      // positionTop = browserHeight/2 - popWindowHeight/2+browserScrollTop;
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
	//收起
	oClose.click(function(){
  		$(this).hide();
  		$(".addtext").hide();
		$(".popWindow").css("min-width",'300px');
		$(window).resize();
  	});
	//切换学科
	$("#subjectId").change(function () {
		$("#query1").empty();
		$(".knowledges").empty();
		if ($(".addtext").is(':visible')){
			getknowleged();
		}
	});
	//切换题目类型
	$(".choice_judje_tr").hide();
	$(".awser_tr2").hide();
	$("#choicetype").change(function(){
		if($("#choicetype").val()==1){
			$(".choice_judje_tr").hide();
			$(".choice_option_tr").show();
			$("#choicenum").change();
			$(".choice_option").attr("type","radio");
			$(".awser_tr1").show();
			$(".awser_tr2").hide();
		}else if($("#choicetype").val()==2){
			$(".choice_judje_tr").hide();
			$(".choice_option_tr").show();
			$("#choicenum").change();
			$(".choice_option").attr("type","checkbox");
			$(".awser_tr1").show();
			$(".awser_tr2").hide();
		}else if($("#choicetype").val()==3){
			$(".choice_option_tr").hide();
			$(".choice_judje_tr").show();
			$(".awser_tr1").show();
			$(".awser_tr2").hide();
		}else{
			$(".choice_option_tr").hide();
			$(".choice_judje_tr").hide();
			$(".awser_tr2").show();
			$(".awser_tr1").hide();
		}
		checkedno();
	});
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
	//点击对错按钮
	$(".choice_judje").click(function() {
		if ($(this).val()==1)
			$("#anwser").val("正确");
		else
			$("#anwser").val("错误");
	});
	//全部不选中状态
	function checkedno(){
		var obj = document.getElementsByName('opt');
		for (var i = 0; i < obj.length; i++) {
			obj[i].checked=false;
		}
		var obj2 = document.getElementsByName('judje');
		for (var j = 0; j < obj2.length; j++) {
			obj2[j].checked=false;
		}
		$("#anwser").val('');
	}
	//搜索
	var querident;
	$("#query101").click(function () {
		if (querident==1)
			getknowleged();
		else
			getaccessory();
	});
	//点击知识点
	$("#knowledge_open").click(function(){
		querident=1;
		$(".addtext").show();
		$(".library_div").hide();
		$('.popWindow h3 span').show();
		$(window).resize();
		$("#query1").val("");
		$(".popWindow").css("min-width",'710px');
		$(".addtitel").html("知识点");
		$(".addcontext").empty();
		getknowleged();
	});
	//点击附件
	$("#accessory_open").click(function(){
		querident=2;
		$(".addtext").show();
		$(".library_div").show();
		$('.popWindow h3 span').show();
		$(window).resize();
		$("#query1").val("");
		$(".popWindow").css("min-width",'710px');
		$(".addtitel").html("附件");
		$(".addcontext").empty();
		getaccessory();
	});

	//初始化数据
	function popinit(id){
		if (id!=0){
			getchoice(id);
		}else{
			return;
		}
	}
	String.prototype.toArray = function () {  //把字符串转换为数组
		var p = this.length; a = [];  //获取当前字符串长度，并定义空数组
		if (p) {  //如果存在则执行循环操作，预防空字符串
			for (var i = 0; i < p; i ++) {  //遍历字符串，枚举每个字符
				a.push(this.charAt(i));  //把每个字符按顺序装入数组
			}
		}
		return a;  //返回数组
	}
	//获取某一试题信息
	function getchoice(id){
		$.ajax({
			url:"/choice/findOneById",
			type:"post",
			data:{id:id},
			dataType:"json",
			success: function(data) {
				$(".choice_table").attr("choice_id",data.id);
				$("#choicetype").val(data.type).trigger("change");
				$("#subjectId").val(data.subId).trigger("change");
				$("#choice_name").val(data.topic);
				if(data.type==1||data.type==2){
					$("#choicenum").val(data.optionNum).trigger("change");
					$("#optionA").val(data.optionA!=null?data.optionA:"");
					$("#optionB").val(data.optionB!=null?data.optionB:"");
					$("#optionC").val(data.optionC!=null?data.optionC:"");
					$("#optionD").val(data.optionD!=null?data.optionD:"");
					$("#optionE").val(data.optionE!=null?data.optionE:"");
					$("#optionF").val(data.optionF!=null?data.optionF:"");
					$("#anwser").val(data.correct);
					let s=data.correct.toArray();
					for(let l in s){
						if (s[l]=="A") $(".optionA").children("td").children(".choice_option").prop("checked",true);
						if (s[l]=="B") $(".optionB").children("td").children(".choice_option").prop("checked",true);
						if (s[l]=="C") $(".optionC").children("td").children(".choice_option").prop("checked",true);
						if (s[l]=="D") $(".optionD").children("td").children(".choice_option").prop("checked",true);
						if (s[l]=="E") $(".optionE").children("td").children(".choice_option").prop("checked",true);
						if (s[l]=="F") $(".optionF").children("td").children(".choice_option").prop("checked",true);
					}
				}else if(data.type==3){
					$("input[name='judje']").val(data.correct);
					if(data.correct==1) {
						$(".choice_judje cor1").prop("checked",true);
						$("#anwser").val("正确");
					}else {
						$(".choice_judje cor2").prop("checked",true);
						$("#anwser").val("错误");
					}
				}else if(data.type==4){
					$("#answer2").val(data.correct);
				}
				if(data.difficultyLevel==5) $("#star5").prop("checked",true);
				if(data.difficultyLevel==4) $("#star4").prop("checked",true);
				if(data.difficultyLevel==3) $("#star3").prop("checked",true);
				if(data.difficultyLevel==2) $("#star2").prop("checked",true);
				if(data.difficultyLevel==1) $("#star1").prop("checked",true);

				$("#choice_analysis").val(data.analysis);
				$("#scaleofmarks").val(data.scaleRule).trigger("change");
				console.log(data);
			},
			error: function(data) {
				$.alert("服务器异常");
			}
		});
	}
	//获取知识点
	function getknowleged(){
		$.ajax({
			url:"/knowledgePoint/findAllByNameLike",
			type:"post",
			data:{subid:$('#subjectId').val(),name:$("#query1").val()},
			dataType:"json",
			success: function(data) {
				$(".addcontext").empty();
				for (let i = 0; i < data.length; i++) {
					//$(".addcontext").append(data[i].subId+";"+data[i].name+"<br>")
					$(".addcontext").append('<div class="knowledge_b" knowid="'+data[i].subId+'" knowname="'+data[i].name+'" >\n' +
						'<span class="knowledge_bp1" >'+data[i].name+'</span>\n' +
						'<span class="knowledge_bp2" >\n' +
						'<i class="my-icon lsm-sidebar-icon icon-tianjia know_add"></i>\n' +
						'</span>\n' +
						'</div>')
				}
				$(".know_add").bind("click",function () {
					$(".knowledges").append('<span class="knowledge_a" knowid="'+$(this).parent().parent().attr("knowid")+'" knowname="'+$(this).parent().parent().attr("knowname")+'">' +$(this).parent().parent().attr("knowname")+'</span>')
					$(".knowledge_a").bind("click",function () {
						$(this).remove();
					})
				});
			},
			error: function(data) {
				$.alert("服务器异常");
			}
		});
	}
	//获取附件
	function getaccessory(){
		if($("#library").val()==0){
			//查询文件库

		}else{
			//查询样本库
		}
	}
	//重置
	$("#resetchoice").click(function(){
		$("#choice_name").val("");
		$(".operation1").val("");
		checkedno();
		$("#anwser").val("");
		$(".knowledges").empty();//知识点
		$(".accessorys").empty();//附件
		$("#choice_analysis").val("");//解析
	});
	//提交
	$("#savechoice").click(function () {
		let id;
		let subId;//学科id
		let topic;//题目
		let type;//类型（单选，多选，判断题，简答题）
		let fileIds="";//附件id集合
		let optionNum=null;//选项个数
		let optionA=null;//选项A
		let optionB=null;
		let optionC=null;
		let optionD=null;
		let optionE=null;
		let optionF=null;
		let correct;//正确选项
		let analysis;//解析
		let abilityIds="";//知识点id集合
		let difficultyLevel;//难易程度
		let scaleRule;//评分标准
		let createTime=null;
		let uapdateTime=null;
		if($(".choice_table").attr("choice_id")=="")
			id=null;
		else
			id=$(".choice_table").attr("choice_id");
		subId=$("#subjectId").val();
		topic=$("#choice_name").val();
		if (topic==""||topic.length==0){
			$.alert("请添加题目");
			return;
		}
		type=$("#choicetype").val();
		//附件集合-待添加
		//for ()
		optionNum=$("#choicenum").val();
		optionA=$("#optionA").val();
		optionB=$("#optionB").val();
		optionC=$("#optionC").val();
		optionD=$("#optionD").val();
		if (optionNum==5){
			optionE=$("#optionE").val();
		}
		if (optionNum==6){
			optionE=$("#optionE").val();
			optionF=$("#optionF").val();
		}
		if (type==1||type==2){
			if (optionA==""||optionB==""||optionC==""||optionD==""||optionE==""||optionF==""){
				$.alert("请完善选项");
				return;
			}
			correct=$("#anwser").val();
			if (correct==""||correct.length==0){
				$.alert("请勾选正确选项");
				return;
			}
		}
		if (type==3){
			correct=$("input[name='judje']:checked").val();
			if (correct==null){
				$.alert("请勾选正确选项");
				return;
			}
		}
		if (type==4){
			correct=$("#answer2").val();
			if (correct==null||correct.length==0){
				$.alert("请输入参考答案");
				return;
			}
		}
		analysis=$("#choice_analysis").val();
		//知识点集合
		abilityIds="";
		difficultyLevel=$("input[name='star']:checked").val();
		if (difficultyLevel==null){
			$.alert("请选择难易程度");
			return;
		}
		scaleRule=$("#scaleofmarks").val();
		let Choice={
			"id":id,
			"subId":subId,
			"topic":topic,
			"type":type,
			"fileIds":fileIds,
			"optionNum":optionNum,
			"optionA":optionA,
			"optionB":optionB,
			"optionC":optionC,
			"optionD":optionD,
			"optionE":optionE,
			"optionF":optionF,
			"correct":correct,
			"analysis":analysis,
			"abilityIds":abilityIds,
			"difficultyLevel":difficultyLevel,
			"scaleRule":scaleRule,
			"createTime":createTime,
			"uapdateTime":uapdateTime
		}
		$.ajax({
			url: "/choice/save",
			contentType:"application/json;charset=UTF-8",
			type: "post",
			async: false,
			data:JSON.stringify(Choice),
			dataType: "json",
			success: function(data) {
				$("#resetchoice").click();
				$(".addtext").hide();
				$("#cancel").click();
				Init(pageIndex);
			},
			error: function(data){
				$.alert("服务器异常,添加失败！");
			}
		});
	})
//////////////////////////////////////////////////////////////////////悬浮窗口js
	function choicebind(){
		$(".edit-choice").bind("click",function(e){
			openWindw(e.pageX,e.pageY,$(this).parent().parent().attr("choiceid"));
		});
		$(".delete-choice").bind("click",function(e){
			var thi=$(this);
			$.confirm({
				confirmButtonClass: 'btn-info',
				cancelButtonClass: 'btn-danger',
				title: '提示',
				content: '确定要删除该试题吗？',
				autoClose: 'confirm|3000',
				confirm: function(){
					$.ajax({
						url: "/choice/deleteById",
						//contentType:"application/json;charset=UTF-8",
						type: "post",
						async: false,
						data:{id:thi.parent().parent().attr("choiceid")},
						dataType: "json",
						success: function(data) {
							if (!data.resoult){
								$.confirm({
									title: data.msg,
									cancelButton: false,
									content: data.testPaperNames,
									autoClose: 'confirm|10000',
								});
							}else{
								$.alert("删除成功");
							}
							Init(pageIndex);
						},
						error: function(data){
							$.alert("服务器异常,删除失败！");
						}
					});
				},
				cancel: function(){

				}
			});
		});
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
});
//搜索框回车
function query1(e){
	var keyCode = null;
	if(e.which)
		keyCode = e.which;
	else if(e.keyCode)
		keyCode = e.keyCode;
	if(keyCode == 13)
		$("#query101").click();
}



