$(document).ready(() => {
    $(".datepicker").datepicker({
        format: "dd.mm.yyyy"
    })
    $("a.confirm").click((e) => {
        return confirm($(e.target).find(".msg").text())
    })
    $("form.confirm").on("submit", (e) => {
        e.preventDefault()
        let msg = $(e.target).find(".msg").text()
        msg = msg.replace(".select.val.",$(e.target).find(".dropdown-toggle .filter-option-inner-inner").text())
        console.log("test")
        if (confirm(msg)) {
            $(e.target).off()
            $(e.target).submit()
        }
    })
    $(".selectpicker").trigger("click")
    $(".selectpicker").each(function(){
        if(!$(this).find("option").length){
            $(this).parent().find(".filter-option-inner-inner").text("-")
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