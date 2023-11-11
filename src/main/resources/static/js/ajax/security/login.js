$(document).ready(() => {
    $("#iForgotPasswordButton").click(() => {
        $("#forgottenPasswordDiv").show()
    })
    $("#recoverPasswordButton").click(() => {
        let element = $("#inputUsernameForgottenPassword")
        let username = element.val()
        if(username.length === 0){
            alert($("#cantBeEmpty").text())
            return
        }
        fetch("/user/" + username + "/sendRestorePasswordEmail").then(() => {
            alert($("#sent").text())
        })
    })
})