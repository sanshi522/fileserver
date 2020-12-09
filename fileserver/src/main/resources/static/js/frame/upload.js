function minview(){
    parent.upload_minview();
    $(".upload-tile").css("display","none");
    $(".talk").css("display","none");
    $(".viewimg").css("display","block");
    $("body").css("overflow-y","hidden");
    $("body").css("overflow-x","hidden");
}
function maxview(){
    parent.upload_maxview();
    $(".upload-tile").css("display","block");
    $(".talk").css("display","block");
    $(".viewimg").css("display","none");
    $("body").css("overflow-y","auto");
    $("body").css("-ms-overflow-style","none");
}
