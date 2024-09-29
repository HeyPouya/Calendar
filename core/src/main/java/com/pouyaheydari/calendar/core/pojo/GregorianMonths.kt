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
        January.monthNumber -> context.getString(R.string.january)
        February.monthNumber -> context.getString(R.string.february)
        March.monthNumber -> context.getString(R.string.march)
        April.monthNumber -> context.getString(R.string.april)
        May.monthNumber -> context.getString(R.string.may)
        Jun.monthNumber -> context.getString(R.string.jun)
        July.monthNumber -> context.getString(R.string.july)
        August.monthNumber -> context.getString(R.string.august)
        September.monthNumber -> context.getString(R.string.september)
        October.monthNumber -> context.getString(R.string.october)
        November.monthNumber -> context.getString(R.string.november)
        December.monthNumber -> context.getString(R.string.december)
        else -> throw IllegalArgumentException("Month number is grater than 12")
    }
}
