package com.pouyaheydari.calendar.domain

import com.pouyaheydari.calendar.core.pojo.ShamsiMonths
import com.pouyaheydari.calendar.core.pojo.ShamsiMonths.Esfand
import com.pouyaheydari.calendar.core.pojo.ShamsiMonths.Farwarding
import javax.inject.Inject

class CalculateNextMonthUseCase @Inject constructor() {
    operator fun invoke(iranianYear: Int, shamsiMonth: ShamsiMonths): Pair<Int, ShamsiMonths> {
        val month =
            if (shamsiMonth == Esfand) Farwarding else ShamsiMonths.entries.first { it.monthNumber == shamsiMonth.monthNumber + 1 }
        val year = if (month == Farwarding) iranianYear + 1 else iranianYear
        return year to month
    }
}
