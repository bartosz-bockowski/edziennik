function loadMoreNotifications(){
    let countElem = $("#notificationsCount")
    let count = parseInt(countElem.text()) + 1
    countElem.text(count)
    $.ajax({
        type: "GET",
        url: "/getNotifications?count=" + count,
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
                console.log(data[i])
                let element = document.createElement("div")
                element.classList.add("text-dark", "text-left", "alert", "alert-" + data[i].bootstrapColor)

                let title = document.createElement("h5")
                title.innerText = data[i].title

                let date = document.createElement("p")
                date.innerText = data[i].sent

                let message = document.createElement("div")
                message.innerHTML = data[i].message

                let href = document.createElement("a")
                href.href = data[i].href
                href.innerText = $("#moreVal").text()

                element.appendChild(title)
                element.appendChild(date)
                element.appendChild(message)
                element.appendChild(href)
                list.appendChild(element)
            }
        },
        error: function (err) {
            console.log(err)
        }
    })
}

$(document).ready(() => {
    loadMoreNotifications()
    $("#loadMoreNotificationsButton").click(() => {
        loadMoreNotifications()
    })
})