package com.pouyaheydari.calendar.ui

import com.pouyaheydari.calendar.core.pojo.DayType
import com.pouyaheydari.calendar.core.pojo.ShamsiMonths
import com.pouyaheydari.calendar.domain.Event

data class CalendarScreenState(
    val today: DayType.Day,
    val displayMonth: Month = Month(),
    val shouldShowBottomSheet: Boolean = false,
    val selectedDay: DayType.Day,
    val selectedDayEvents: List<Event> = emptyList(),
)

data class Month(
    val shamsiYear: Int = 1403,
    val shamsiMonth: ShamsiMonths = ShamsiMonths.Shahrivar,
    val days: List<DayType> = emptyList()
)
