package com.pouyaheydari.calendar.core.pojo

import android.content.Context
import com.pouyaheydari.calendar.core.R

enum class WeekDay(val weekDayNumber: Int, val distanceFromFirstDayOfWeek: Int) {
    Monday(0, 2),
    Tuesday(1, 3),
    Wednesday(2, 4),
    Thursday(3, 5),
    Friday(4, 6),
    Saturday(5, 0),
    Sunday(6, 1);

    fun getName(context: Context): String = when (weekDayNumber) {
        0 -> context.getString(R.string.monday)
        1 -> context.getString(R.string.tuesday)
        2 -> context.getString(R.string.wednesdays)
        3 -> context.getString(R.string.thursday)
        4 -> context.getString(R.string.friday)
        5 -> context.getString(R.string.saturday)
        6 -> context.getString(R.string.sunday)
        else -> throw IllegalArgumentException("Week day number is grater than 6: $this")
    }
}