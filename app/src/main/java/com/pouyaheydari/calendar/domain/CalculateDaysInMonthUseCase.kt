package com.pouyaheydari.calendar.domain

import com.pouyaheydari.calendar.core.pojo.ShamsiMonths
import com.pouyaheydari.calendar.core.utils.CalendarTool
import com.pouyaheydari.calendar.core.pojo.ShamsiMonths.Esfand
import javax.inject.Inject

private const val NUMBER_OF_DAYS_IN_FIRST_HALF_OF_THE_YEAR_MONTHS = 31
private const val NUMBER_OF_DAYS_IN_SECOND_HALF_OF_THE_YEAR_MONTHS = 30
private const val NUMBER_OF_DAYS_IN_LAST_MONTH_IN_LEAP_YEARS = 29

class CalculateDaysInMonthUseCase @Inject constructor(private val calendarTool: CalendarTool) {
    operator fun invoke(iranianYear: Int, shamsiMonth: ShamsiMonths): Int = when {
        shamsiMonth.monthNumber <= ShamsiMonths.Shahrivar.monthNumber -> NUMBER_OF_DAYS_IN_FIRST_HALF_OF_THE_YEAR_MONTHS
        shamsiMonth == Esfand && !calendarTool.isLeapYear(iranianYear) -> NUMBER_OF_DAYS_IN_LAST_MONTH_IN_LEAP_YEARS
        else -> NUMBER_OF_DAYS_IN_SECOND_HALF_OF_THE_YEAR_MONTHS
    }
}
