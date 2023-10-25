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
            $(".subjectClassAverage").text(response2)
        })
    })
}

$(".markForm").submit((e) => {
    e.preventDefault()
    let form = $(e.target)
    let studentId = form.parent().parent().find("#student").val()
    let markCategoryId = form.find(".markCategory").val()
    let mark = form.find(".mark").val()
    let markId = form.find(".markId").val()
    let schoolClassId = $("#schoolClass").text()
    let subjectId = $("#subject").text()
    fetch("/mark/add/" + mark + "/" + studentId + "/" + markCategoryId + "/" + markId).then((response) => {
        markClick($(e.target), mark)
        if (response.status === 200) {
            loadAverage(schoolClassId, subjectId)
            alert($("#addedMark").text())
        }
    })
})

$(".markDeleteForm").submit((e) => {
    e.preventDefault()
    let target = $(e.target)
    if (confirm($("#removeMarkConfirm").text())) {
        fetch("/mark/delete/" + target.find(".category").val() + "/" + target.parent().parent().find("#student").val()).then(() => {
            markClick(target, "0")
            loadAverage($("#schoolClass").text(), $("#subject").text())
        })
    }
})