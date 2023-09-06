$(document).ready(() => {
    $(".submit").on("click",(e) => {
        e.preventDefault()
        let idField = $("#teacherId")
        let val = $(idField).val()
        if(val === ""){
            alert($("#cantBeEmpty").val())
            return;
        }
        fetch("/admin/teacher/checkIfTeacherExists/" + val).then(res => res.json()).then(res => {
            if(res){
                $(e.target).parent().submit()
            } else {
                alert($("#nonExistentTeacherMsg").val())
            }
        })
    })
})