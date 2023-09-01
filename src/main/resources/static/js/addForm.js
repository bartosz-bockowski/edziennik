$(document).ready(function(){
    $(".formTableErrorCell").each(function(cell){
        console.log($(this))
        if($(this).text() !== ""){
            console.log("niepusty")
            $(this).css("background-color", "#ff8686")
            $(this).parent().parent().children().eq(0).children().eq(1).css("background-color", "#ff8686")
        }
    })
})