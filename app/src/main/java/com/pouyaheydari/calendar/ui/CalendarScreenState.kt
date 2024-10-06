package com.pouyaheydari.calendar.ui

import com.pouyaheydari.calendar.core.pojo.DayType
import com.pouyaheydari.calendar.core.pojo.Event
import com.pouyaheydari.calendar.domain.Month

data class CalendarScreenState(
    val today: DayType.Day,
    val displayMonth: Month = Month(),
    val shouldShowBottomSheet: Boolean = false,
    val selectedDay: DayType.Day,
    val selectedDayEvents: List<Event> = emptyList(),
)
