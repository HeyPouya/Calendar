package com.pouyaheydari.calendar.core.pojo

import android.content.Context
import com.pouyaheydari.calendar.core.R

enum class GregorianMonths(val monthNumber: Int) {
    January(1),
    February(2),
    March(3),
    April(4),
    May(5),
    Jun(6),
    July(7),
    August(8),
    September(9),
    October(10),
    November(11),
    December(12);

    fun getName(context: Context): String = when (monthNumber) {
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
}