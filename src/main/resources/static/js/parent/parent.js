$(document).ready(() => {
    $("#addStudentForm").one("submit",function(e){
        e.preventDefault()
        let idField = $("#studentId")
        let val = $(idField).val()
        if(val === ""){
            $(idField).css("background","red");
            return;
        }
        fetch("/admin/parent/" + idField.val() + "/checkIfStudentExists")
            .then(res => res.json()).then(res => {
                console.log(res)
            if (res) {
                $(this).submit()
            } else {
                alert($("#nonExistentStudentMsg").val())
            }
        })
    })
    $(".confirmRemoveStudent").click((e) => {
        return confirm($("#confirmRemoveStudent").attr("msg") + " (" + $(e.target).prev().text() + ")")
    })
})