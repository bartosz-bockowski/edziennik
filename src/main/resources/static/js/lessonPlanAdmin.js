$(document).ready(() => {
    $(".lessonAddForm button[type='submit']").on("click", async (e) => {
        e.preventDefault()
        let form = $(e.target).parent()
        let response = await fetch("/lesson/validateLesson?" + form.serialize())
        let res = await response.json()
        if (res.length === 0) {
            if (confirm($("#confirmUpdate").val())) {
                form.submit()
            }
        } else {
            let notification = ""
            if (res.includes(0)) {
                notification = $("#noChanges").val()
            } else {
                if (res.includes(2)) {
                    notification += $("#teacherNotFree").val()
                    notification += "\n"
                }
                if (res.includes(3)) {
                    notification += $("#classRoomNotFree").val()
                    notification += "\n"
                }
            }
            alert(notification)
        }
    })
})