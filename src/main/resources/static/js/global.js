$(document).ready(() => {
    $(".confirm").click((e) => {
        let obj = $(e.target)
        let msg = ""
        return confirm($(obj).attr("msg"))
    })
    $(".confirmRemoveDetails").click((e) => {
        return confirm($("#confirmRemoveDetails").val() + " (" + $(e.target).prev().text() + ")")
    })
    $(".selectpicker").selectpicker()
    $(".defaultConfirm").click((e) => {
        return confirm($(e.target).attr("msg"));
    })
})