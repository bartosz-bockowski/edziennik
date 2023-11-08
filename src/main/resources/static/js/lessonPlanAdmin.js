$(document).ready(() => {
    $(".lessonAddForm button[type='submit']").on("click", async (e) => {
        e.preventDefault()
        let form = $(e.target).parent()
        if(form.find('[name="subject"]').val() === "" || form.find('[name="teacher"]') || form.find('[name="classRoom"]')){
            alert($("#emptyVal").text())
            return
        }
        let response = await fetch("/lesson/validateLesson?" + form.serialize())
        let res = await response.json()
        if (res.length === 0) {
            if (confirm($("#confirmUpdate").text())) {
                form.submit()
            }
        } else {
            let notification = ""
            if (res.includes(0)) {
                notification = $("#noChanges").text()
            } else {
                if (res.includes(2)) {
                    notification += $("#teacherNotFree").text()
                    notification += "\n"
                }
                if (res.includes(3)) {
                    notification += $("#classRoomNotFree").text()
                    notification += "\n"
                }
            }
            alert(notification)
        }
    })
})