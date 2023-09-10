$(document).ready(() => {
    $(".confirm").click((e) => {
        return confirm($(e.target).attr("msg"))
    })
    $(".selectpicker").selectpicker()
})