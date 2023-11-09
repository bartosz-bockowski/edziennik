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
        //@TODO - bug after logging in after recovering password
        fetch("/user/" + username + "/sendRestorePasswordEmail").then(() => {
            alert($("#sent").text())
        })
    })
})