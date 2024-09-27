package com.pouyaheydari.calendar.core.pojo

sealed interface DayType {

    data object EmptyDay : DayType

    data class Day(
        val shamsiDay: Int,
        val shamsiMonth: ShamsiMonths,
        val shamsiYear: Int,
        val weekDay: WeekDay,
        val gregorianDay: Int,
        val gregorianMonth: GregorianMonths,
        val gregorianYear: Int,
        val isShamsiHoliday: Boolean = false,
        val today: Boolean = false
    ) : DayType
}
