function subjectAddTeacherForm(form){
    let selectpicker = form.find('.selectpicker')
    let val = selectpicker.val()
    let text = selectpicker.find("option:selected").text()
    form.find('.selectpicker option[value="' + selectpicker.val() + '"]').remove()
    selectpicker.first('option').prop('selected',true)
    selectpicker.selectpicker('refresh')
    $("#subjectTeacherListNone").hidden = true
    let liTeacher = document.createElement("li")
    console.log(val)
    console.log(text)
    liTeacher.append(text)

    let aRemoveTeacher = document.createElement("a")
    $(aRemoveTeacher).attr("ajax","subjectRemoveTeacherA")
    aRemoveTeacher.href = "/admin/subject/" + $("#subjectId").val() + "/removeTeacher?teacherId=" + val
    aRemoveTeacher.classList.add("confirm")
    aRemoveTeacher.text = " " + $("#removeTeacherText").text()

    let confirmText = $("#removeTeacherConfirmText").text()
    confirmText = confirmText.replace("{0}",text)
    confirmText = confirmText.replace("{1}", $("#subjectName").val())
    let pConfirm = document.createElement("p")
    pConfirm.innerText = confirmText
    pConfirm.hidden = true
    pConfirm.classList.add("msg")
    aRemoveTeacher.append(pConfirm)

    $(aRemoveTeacher).click((e) => {
        clickOnAConfirmAjax(e)
    })

    liTeacher.append(aRemoveTeacher)

    $("#subjectTeacherList").append(liTeacher)
    $("#subjectTeacherListNone").css("display","none")
}

function subjectRemoveTeacherA(a){
    let select = $("#subjectAddTeacherForm").find("select.selectpicker")
    let href = $(a).attr("href")
    let val = href.split("?")[1].split("=")[1]
    let text = $(a).parent().html().split("<a")[0]
    $(select).append($('<option>',{
        value: val,
        text: text
    }))
    $(select).selectpicker('refresh')
    $(a).parent().remove()
    if($("#subjectTeacherList").find("li").length == 0){
        $("#subjectTeacherListNone").css("display","block")
    }
}