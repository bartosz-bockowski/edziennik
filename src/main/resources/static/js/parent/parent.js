$(document).ready(() => {
    $(".submit").on("click",function(e){
        e.preventDefault()
        let idField = $("#studentId")
        let val = $(idField).val()
        if(val === ""){
            alert($("#cantBeEmpty").val())
            return;
        }
        fetch("/admin/parent/" + idField.val() + "/checkIfStudentExists")
            .then(res => res.json()).then(res => {
            if (res) {
                $(e.target).parent().submit()
            } else {
                alert($("#nonExistentStudentMsg").val())
            }
        })
    })
    $(".confirmRemoveDetails").click((e) => {
        return confirm($("#confirmRemoveDetails").val() + " (" + $(e.target).prev().text() + ")")
    })
})