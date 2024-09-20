package com.pouyaheydari.calendar.core.utils

/**
 * Turns the English numbers to Persian numbers
 */
fun Int.toPersianNumber(): String {
    var newsString = toString()
    newsString = newsString.replace("1", "۱")
    newsString = newsString.replace("2", "۲")
    newsString = newsString.replace("3", "۳")
    newsString = newsString.replace("4", "۴")
    newsString = newsString.replace("5", "۵")
    newsString = newsString.replace("6", "۶")
    newsString = newsString.replace("7", "۷")
    newsString = newsString.replace("8", "۸")
    newsString = newsString.replace("9", "۹")
    newsString = newsString.replace("0", "۰")
    return newsString
}


