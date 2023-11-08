function clearPasswordChangeForm(res){
    let list = $("#passwordCriteriaErrorList")
    if(res.length === 0){
        $("#passErrorDiv").hide()
        $("#passSuccessDiv").show()
    } else {
        $("#passErrorDiv").show()
        $("#passSuccessDiv").hide()
    }
    list.find("li").remove()
    for(let i = 0; i < res.length; i++){
        $("<li>",{
            text: res[i]
        }).appendTo(list)
    }
}