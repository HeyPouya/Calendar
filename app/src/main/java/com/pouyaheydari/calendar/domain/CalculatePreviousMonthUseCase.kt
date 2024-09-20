package com.pouyaheydari.calendar.domain

import com.pouyaheydari.calendar.core.pojo.ShamsiMonths
import com.pouyaheydari.calendar.core.pojo.ShamsiMonths.Esfand
import com.pouyaheydari.calendar.core.pojo.ShamsiMonths.Farwarding
import javax.inject.Inject

class CalculatePreviousMonthUseCase @Inject constructor() {
    operator fun invoke(iranianYear: Int, shamsiMonth: ShamsiMonths): Pair<Int, ShamsiMonths> {
        val month =
            if (shamsiMonth == Farwarding) Esfand else ShamsiMonths.entries[shamsiMonth.monthNumber - 1]
        val year = if (month == Esfand) iranianYear - 1 else iranianYear
        return year to month
    }
}
