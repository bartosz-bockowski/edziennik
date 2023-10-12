$(document).ready(() => {
    $(".lessonAddForm button[type='submit']").on("click", async (e) => {
        e.preventDefault()
        let form = $(e.target).parent()
        let response = await fetch("/admin/lessonplan/validateLesson?" + form.serialize())
        let res = await response.json()
        if (res.includes(1)) {
            if (confirm($("#confirmUpdate").val())) {
                form.submit()
            }
        }
        if (res.includes(0)) {
            alert($("#noChanges").val())
        }
        if (res.includes(2) || res.includes(3)) {
            if (res.length === 2) {
                alert($("#bothNotFree").val())
            } else {
                if (res.includes(2)) {
                    alert($("#teacherNotFree").val())
                }
                if (res.includes(3)) {
                    alert($("#classRoomNotFree").val())
                }
            }
        }
    })
})