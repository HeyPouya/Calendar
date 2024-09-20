package com.pouyaheydari.calendar.core.utils.extensions

import android.content.Context
import com.pouyaheydari.calendar.core.R

/**
 * Turns the day number to its Persian equivalent.
 * Days must start from 0(Monday) to 6(Sunday)
 * @param context To use getString method
 */
fun Int.toPersianWeekDay(context: Context): String = when (this) {
    0 -> context.getString(R.string.monday)
    1 -> context.getString(R.string.tuesday)
    2 -> context.getString(R.string.wednesdays)
    3 -> context.getString(R.string.thursday)
    4 -> context.getString(R.string.friday)
    5 -> context.getString(R.string.saturday)
    6 -> context.getString(R.string.sunday)
    else -> throw IllegalArgumentException("Week day number is grater than 6: $this")
}

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


