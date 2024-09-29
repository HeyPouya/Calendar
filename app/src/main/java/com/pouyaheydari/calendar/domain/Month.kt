package com.pouyaheydari.calendar.domain

import com.pouyaheydari.calendar.core.pojo.DayType
import com.pouyaheydari.calendar.core.pojo.GregorianMonths
import com.pouyaheydari.calendar.core.pojo.ShamsiMonths

data class Month(
    val shamsiYear: Int = 1403,
    val shamsiMonth: ShamsiMonths = ShamsiMonths.Shahrivar,
    val days: List<DayType> = emptyList(),
    val gregorianMonths: Set<GregorianMonths> = emptySet(),
    val gregorianYear: Set<Int> = emptySet()
)
