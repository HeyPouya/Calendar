package ir.apptune.calendar.utils.extensions

import android.content.Context
import ir.apptune.calendar.R

/**
 * Turns the month number to its Persian equivalent
 * @param context To use getString method
 */
fun Int.toPersianMonth(context: Context): String = when (this) {
    1 -> context.getString(R.string.farvardin)
    2 -> context.getString(R.string.ordibehesht)
    3 -> context.getString(R.string.khordad)
    4 -> context.getString(R.string.tir)
    5 -> context.getString(R.string.mordad)
    6 -> context.getString(R.string.shahrivar)
    7 -> context.getString(R.string.mehr)
    8 -> context.getString(R.string.aban)
    9 -> context.getString(R.string.azar)
    10 -> context.getString(R.string.dey)
    11 -> context.getString(R.string.bahman)
    12 -> context.getString(R.string.esfand)
    else -> throw IllegalArgumentException("Month number is grater than 12")
}

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
 * Turns the month number to its Gregorian equivalent in Persian
 * @param context To use getString method
 */
fun Int.toEnglishMonth(context: Context): String = when (this) {
    1 -> context.getString(R.string.january)
    2 -> context.getString(R.string.february)
    3 -> context.getString(R.string.march)
    4 -> context.getString(R.string.april)
    5 -> context.getString(R.string.may)
    6 -> context.getString(R.string.jun)
    7 -> context.getString(R.string.july)
    8 -> context.getString(R.string.august)
    9 -> context.getString(R.string.september)
    10 -> context.getString(R.string.october)
    11 -> context.getString(R.string.november)
    12 -> context.getString(R.string.december)
    else -> throw IllegalArgumentException("Month number is grater than 12")
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


