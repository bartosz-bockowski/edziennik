$(".markSwitch").click((e) => {
    click($(e.target))
})
function click(e,m){
    let parent = $(e).parent()
    let form = $(parent).find(".markForm")
    let span = $(parent).find(".markSpan")
    if(m != null){
        let mm = parseFloat(m).toFixed(2)
        span.text(mm)
        form.find(".mark").val(mm)
    }
    $(form).toggle()
    $(span).toggle()
}
$(".markFormSubmit").click((e) => {
    e.preventDefault()
    let form = $(e.target).parent()
    let studentId = form.find(".student").val()
    let markCategoryId = form.find(".markCategory").val()
    let mark = form.find(".mark").val()
    let markId = form.find(".markId").val()
    fetch("/mark/add/" + mark + "/" + studentId + "/" + markCategoryId + "/" + markId).then(() => {
            click($(e.target).parent(),mark)
        })
})