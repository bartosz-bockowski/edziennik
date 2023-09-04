$(document).ready(() => {
    $(".confirm").click((e) => {
        let obj = $(e.target)
        let msg = ""
        return confirm($(obj).attr("msg"))
    })
})