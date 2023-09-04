$(document).ready(() => {
    $(".submit").on("click",function(e){
        e.preventDefault()
        let idField = $("#studentId")
        let val = $(idField).val()
        if(val === ""){
            alert($("#cantBeEmpty").val())
            return;
        }
        fetch("/admin/schoolclass/" + idField.val() + "/checkStudent")
            .then(res => res.json()).then(res => {
             if (res === "NO_CLASS") {
                 $(e.target).parent().submit()
             } else if (res === "NON_EXISTENT"){
                 alert($("#nonExistentStudentMsg").val())
             } else {
                 alert($("#studentAlreadyHasClass").val())
             }
        })
    })
    $(".confirmRemoveStudent").click((e) => {
        return confirm($("#confirmRemoveStudent").val() + " (" + $(e.target).prev().text() + ")")
    })
})