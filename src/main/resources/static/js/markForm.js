$(".markSwitch").click((e) => {
    markClick($(e.target))
})

function markClick(e, m) {
    let parent = $(e).parent()
    let form = $(parent).find(".markForm")
    let span = $(parent).find(".markSpan")
    if (m != null) {
        let mm = parseFloat(m).toFixed(2)
        if (mm === "0.00") {
            mm = ""
        }
        span.text(mm)
        form.find(".mark").val(mm)
    }
    $(form).toggle()
    $(span).toggle()
}

function loadAverage(classId, subjectId) {
    let url = "/schoolclass/" + classId + "/getAverageMarkBySubjectId/" + subjectId
    fetch(url).then(response2 => {
        response2.text().then(response2 => {
            alert($("#addedMark").val())
            $(".subjectClassAverage").text(response2)
        })
    })
}

$(".markFormSubmit").click((e) => {
    e.preventDefault()
    let form = $(e.target).parent()
    let studentId = form.find(".student").val()
    let markCategoryId = form.find(".markCategory").val()
    let mark = form.find(".mark").val()
    let markId = form.find(".markId").val()
    let schoolClassId = form.find(".schoolClass").val()
    let subjectId = form.find(".subject").val()
    fetch("/mark/add/" + mark + "/" + studentId + "/" + markCategoryId + "/" + markId).then((response) => {
        markClick($(e.target).parent(), mark)
        if (response.status === 200) {
            loadAverage(schoolClassId, subjectId)
        }
    })
})

$(".deleteButton").click((e) => {
    e.preventDefault()
    let target = $(e.target)
    if (confirm($("#removeMarkConfirm").val())) {
        fetch("/mark/delete/" + target.parent().find(".category").val() + "/" + target.parent().find(".student").val()).then(() => {
            markClick(target.parent(), "0")
            loadAverage(target.parent().find(".schoolClassId").val(), target.parent().find(".subjectId").val())
        })
    }
})