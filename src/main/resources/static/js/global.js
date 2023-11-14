function clickOnAConfirmAjax(e){
    e.preventDefault()
    let a = $(e.target)
    let msg = a.find(".msg").length > 0 ? a.find(".msg").text() : ''
    msg = msg.replace(".select.val.","x")
    if(confirm(msg)){
        fetch(a.attr("href")).then(() => {
            eval(a.attr("ajax") + "(a)")
        })
    }
}
$(document).ready(() => {
    $(".clearFilterButton").click((e) => {
        let form = $(e.target).parent().parent()
        form.find("input").val("")
        form.submit()
    })
    $(".datepicker").datepicker({
        format: "dd.mm.yyyy"
    })
    //confirm
    $("a.confirm:not([ajax])").click((e) => {
        return confirm($(e.target).find(".msg").text())
    })
    //confirm form
    $("form.confirm:not([ajax])").on("submit", (e) => {
        e.preventDefault()
        let msg = $(e.target).find(".msg").text()
        msg = msg.replace(".select.val.",$(e.target).find(".dropdown-toggle .filter-option-inner-inner").text())
        if (confirm(msg)) {
            $(e.target).off()
            $(e.target).submit()
        }
    })
    $('form.confirm[ajax]').submit((e) => {
        e.preventDefault()
        let form = $(e.target)
        let msg = form.find(".msg").text()
        msg = msg.replace(".select.val.",form.find('button.btn.dropdown-toggle.btn-light div.filter-option-inner-inner').text())
        if (confirm(msg)) {
            let link = form.attr("action") + "?" + form.serialize()
            let ajaxRes = form.attr("ajaxRes")
            if(typeof ajaxRes !== "undefined" && ajaxRes !== false){
                fetch(link).then(res => {
                    return res.json()
                }).then(data => {
                    eval(form.attr("ajax") + "(data)")
                }).catch(error => {
                    console.error(error)
                })
            } else {
                fetch(link).then(() => {
                    eval(form.attr("ajax") + "(form)")
                })
            }
        }
    })
    $('a.confirm[ajax]').click((e) => {
        clickOnAConfirmAjax(e)
    })
    //selectpicker
    $(".selectpicker").trigger("click")
    $(".selectpicker").selectpicker({noneSelectedText: '-'})
    $(".selectpicker").each(function(){
        if(!$(this).find("option").length){
            $(this).parent().find(".filter-option-inner-inner").text("-")
        }
    })
    //header
    $("#headerUserButton").click((e) => {
        $(e.target).parent().find("div:first").toggle()
    })
})