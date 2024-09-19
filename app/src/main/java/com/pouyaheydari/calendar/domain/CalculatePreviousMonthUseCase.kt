package com.pouyaheydari.calendar.domain

import javax.inject.Inject

class CalculatePreviousMonthUseCase @Inject constructor() {
    operator fun invoke(iranianYear: Int, iranianMonth: Int): Pair<Int, Int> {
        val previousMonth = if (iranianMonth == 1) 12 else iranianMonth - 1
        val previousYear = if (previousMonth == 12) iranianYear - 1 else iranianYear
        return Pair(previousYear, previousMonth)
    }
}