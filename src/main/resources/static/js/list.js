$(document).ready(() => {
    let sortedVal = $("#sorted").val()
    let sortedCell = $('[sort="' + sortedVal + '"]')
    let direction = $("#direction").val()
    let directionSign = direction === "ASC" ? "\u25b2" : "\u25bc"
    let directionName = direction === "ASC" ? "desc" : "asc"
    sortedCell.attr("href",sortedCell.attr("href") + "," + directionName)
    sortedCell.text(sortedCell.text() + " " + directionSign)
    $('a[sort]:not([sort="' + sortedVal + '"])').each(function(){
        $(this).attr("href",$(this).attr("href") + ",desc")
    })
})