function moveValueFromListToSelectAndReturnTextValueOfOption(form, a){
    let select = form.find("select.selectpicker")
    let href = $(a).attr("href")
    let val = href.split("?")[1].split("=")[1]
    let text = $(a).parent().html().split("<a")[0]
    $(select).append($('<option>',{
        value: val,
        text: text.trim()
    }))
    $(select).selectpicker('refresh')
    $(a).parent().remove()
    return text
}

function removeFromSelectAndCreateLi(form){
    let selectpicker = form.find('.selectpicker')
    let val = selectpicker.val()
    let text = selectpicker.find("option:selected").text()
    form.find('.selectpicker option[value="' + selectpicker.val() + '"]').remove()
    selectpicker.first('option').prop('selected',true)
    selectpicker.selectpicker('refresh')
    let li = $('<li>',{
        text: text.trim()
    })
    return {
        val: val,
        text: text,
        li: li
    }
}

function subjectAddTeacherForm(form){
    let res = removeFromSelectAndCreateLi(form)
    let val = res.val
    let text = res.text
    let liTeacher = res.li

    let aRemoveTeacher = $('<a>',{
        ajax: "subjectRemoveTeacherA",
        href: "/admin/subject/" + $("#subjectId").val() + "/removeTeacher?teacherId=" + val,
        class: "confirm",
        text: " " + $("#removeTeacherText").text()
    })
    $(aRemoveTeacher).click((e) => {
        clickOnAConfirmAjax(e)
    })
    $(aRemoveTeacher).appendTo(liTeacher)

    let confirmText = $("#removeTeacherConfirmText").text().replace("{0}",text)
    confirmText = confirmText.replace("{1}", $("#subjectName").val())

    $('<p>',{
        text: confirmText.trim(),
        hidden: true,
        class: 'msg'
    }).appendTo(aRemoveTeacher)

    $(liTeacher).appendTo($("#subjectTeacherList"))
    $("#subjectTeacherListNone").css("display","none")
}

function subjectRemoveTeacherA(a){
    let form = $("#subjectAddTeacherForm")
    let text = moveValueFromListToSelectAndReturnTextValueOfOption(form, a)
    if($("#subjectTeacherList").find("li").length === 0){
        $("#subjectTeacherListNone").css("display","block")
    }
    if(form.find('div.inner.show li').length === 1){
        form.find('button .filter-option-inner-inner').text(text)
    }
}

function subjectAddSchoolClassForm(form){
    let res = removeFromSelectAndCreateLi(form)
    let val = res.val
    let text = res.text
    let liSchoolClass = res.li

    let aRemoveSchoolClass = $('<a>',{
        ajax: "subjectRemoveSchoolClassA",
        href: "/admin/subject/" + $("#subjectId").val() + "/removeSchoolClass?schoolClassId=" + val,
        class: "confirm",
        text: " " + $("#removeSchoolClassText").text()
    })
    $(aRemoveSchoolClass).click((e) => {
        clickOnAConfirmAjax(e)
    })
    $(aRemoveSchoolClass).appendTo(liSchoolClass)

    let confirmText = $("#removeSchoolClassConfirmText").text().replace("{0}",text)
    confirmText = confirmText.replace("{1}", $("#subjectName").val())

    $('<p>',{
        text: confirmText.trim(),
        hidden: true,
        class: 'msg'
    }).appendTo(aRemoveSchoolClass)

    $(liSchoolClass).appendTo($("#subjectSchoolClassList"))
    $("#subjectSchoolClassListNone").css("display","none")
}

function subjectRemoveSchoolClassA(a){
    let form = $("#subjectAddSchoolClassForm")
    let text = moveValueFromListToSelectAndReturnTextValueOfOption(form, a)
    if($("#subjectSchoolClassList").find("li").length === 0){
        $("#subjectSchoolClassListNone").css("display","block")
    }
    if(form.find('div.inner.show li').length === 1){
        form.find('button .filter-option-inner-inner').text(text)
    }
}