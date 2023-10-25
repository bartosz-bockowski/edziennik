$(document).ready(() => {
    $(".confirm").click((e) => {
        return confirm($(e.target).attr("msg"))
    })
    $(".confirmForm").one("submit", (e) => {
        e.preventDefault()
        if (confirm($(e.target).attr("msg"))) {
            $(e.target).submit()
        }
    })
    $(".selectpicker").selectpicker()
    $(".notificationsButton").click((e) => {
        $(e.target).parent().find(".notificationsList").toggle()
    })
    $(document).click((e) => {
        let target = e.target
        let classList = target.classList
        if (!classList.contains("notificationsList") && !classList.contains("notificationsButton") && $(target).closest($(".notificationsList")).length === 0) {
            $(".notificationsList").hide()
        }
    })
    $.ajax({
        type: "GET",
        url: "/getNotifications",
        contentType: "application/json",
        success: function (data) {
            let list = document.querySelector(".notificationsList")
            let len = data.length
            if (len === 0) {
                let title = document.createElement("div")
                title.classList.add("noNotificationsDiv")
                title.innerText = $("#noNotificationsVal").text()
                list.appendChild(title)
            }
            for (let i = 0; i < len; i++) {
                let element = document.createElement("div")
                element.classList.add("notificationOnList")

                let title = document.createElement("div")
                title.classList.add("notificationOnListTitle")
                title.innerText = data[i].title

                let message = document.createElement("div")
                message.innerHTML = data[i].message

                let href = document.createElement("a")
                href.href = data[i].href
                href.innerText = $("#moreVal").text()

                element.appendChild(title)
                element.appendChild(message)
                element.appendChild(href)
                list.appendChild(element)
            }
        },
        error: function (err) {
            console.log(err)
        }
    })
})