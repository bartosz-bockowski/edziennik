$(document).ready(() => {
    $(".datepicker").datepicker({
        format: "dd.mm.yyyy"
    })
    $(".confirm").click((e) => {
        return confirm($(e.target).find(".msg").text())
    })
    $(".confirmForm").one("submit", (e) => {
        e.preventDefault()
        if (confirm($(e.target).find(".msg").text())) {
            $(e.target).submit()
        }
    })
    $(".selectpicker").trigger("click")
    $('form[executed-by-js="true"]').submit((e) => {
        e.preventDefault()
        let target = $(e.target)
        if(target.find("p.msg").length === 0 || confirm(target.find("p.msg").text())){
            fetch(target.attr("action") + "?" + target.serialize()).then(() => {
                location.reload()
            })
        }
    })
    $('a[executed-by-js="true"]').click((e) => {
        e.preventDefault()
        let target = $(e.target)
        if(target.find("p.msg").length === 0 || confirm(target.find("p.msg").text())){
            fetch(target.attr("href")).then(() => {
                location.reload()
            })
        }
    })
})