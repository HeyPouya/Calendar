package com.pouyaheydari.calendar.domain

import javax.inject.Inject

class CalculateNextMonthUseCase @Inject constructor() {
    operator fun invoke(iranianYear: Int, iranianMonth: Int): Pair<Int, Int> {
        val nextMonth = if (iranianMonth == 12) 1 else iranianMonth + 1
        val nextYear = if (nextMonth == 1) iranianYear + 1 else iranianYear
        return nextYear to nextMonth
    }
}