$(document).ready(function(){
    $(".formTableErrorCell").each(function(){
        if($(this).text() !== ""){
            $(this).css("background-color", "#ff8686")
            $(this).parent().prev().children().eq(1).css("background-color", "#ff8686")
        }
    })
})