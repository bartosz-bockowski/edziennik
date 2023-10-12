$(document).ready(() => {
    $(".lessonAddForm").on("submit", (e) => {
        let form = $(e.target)
        fetch("/admin/lessonplan/validateLesson?" + form.serialize()).then((res) => res.json()).then((res) => {
            console.log(res)
            if (res.includes(1)) {
                console.log("ok")
            }
            if (res.includes(0)) {
                console.log("brak zmian")
            }
            if (res.includes(2)) {
                console.log("teacher zaety")
            }
            if (res.includes(3)) {
                console.log("sala zajeta")
            }
        })
        e.preventDefault()
    })
})