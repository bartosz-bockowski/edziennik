$(document).ready(() => {
    $(".confirm").click((e) => {
        return confirm($(e.target).attr("msg"))
    })
    $(".selectpicker").selectpicker()
    $(".notificationsButton").click((e) => {
        $(e.target).parent().find(".notificationsList").toggle()
    })
    $(document).click((e) => {
        let classList = e.target.classList
        if(!classList.contains("notificationsList") && !classList.contains("notificationsButton")){
            $(".notificationsList").hide()
        }
    })
    $.ajax({
        type: "GET",
        url: "/getNotifications",
        contentType:"application/json",
        success: function(data){
            console.log(data)
        },
        error: function(err){
            console.log(err)
        }
    })
})