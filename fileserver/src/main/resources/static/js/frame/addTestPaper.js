$(function(){

    $("#addchoice").click(function(e){
        openWindw(e.pageX,e.pageY);
    });
//////////////////////////////////////////////////////////////////////悬浮窗口js
    //var oBtn = $('#show');
    var popWindow = $('.popWindow');

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

///////////////////////////////////////////////拖拽排序
    var container = document.getElementById("choices");
    var sort = Sortable.create(container, {
        animation: 150, // ms，动画速度移动项目排序时，' 0 ' -没有动画
        handle: ".choice", // 限制排序开始单击/触摸指定的元素
        draggable: ".choice", // 指定元素中的哪些项应该排序
        onUpdate: function (evt/**Event*/){
            var item = evt.item; // 当前拖动的HTMLElement
            // alert($(item).attr('choiceid'));
            sortRef();
        }
    });
//序号更新
    function sortRef(){
        var choices=$(".choice");
        choices.each(function(i, choice) {
            $(choice).attr("c_index",i);
            $(choice).children(".choice-title").children(".choice-index").html(i+1);
            //alert(i);
        });
    }
    anchorBind();
//快速锚点定位
    function anchorBind(){
        $(".anchor").bind("click",function(){
            var choices=$(".choice");
            var num=$(this).text();
            if(parseInt(num)>choices.length) return;
            $("html,body").animate({
                scrollTop: choices.eq(parseInt(num)-1).offset().top-40
            }, 200 /*scroll实现定位滚动*/ ); /*让整个页面可以滚动*/
        });
    }
});
