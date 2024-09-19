package com.pouyaheydari.calendar.domain

import com.pouyaheydari.calendar.core.utils.CalendarTool
import javax.inject.Inject

class CalculateDaysInMonthUseCase @Inject constructor(private val calendarTool: CalendarTool) {
    operator fun invoke(iranianYear: Int, iranianMonth: Int): Int = when {
        iranianMonth <= 6 -> 31
        iranianMonth == 12 && !calendarTool.isLeapYear(iranianYear) -> 29
        else -> 30
    }
}