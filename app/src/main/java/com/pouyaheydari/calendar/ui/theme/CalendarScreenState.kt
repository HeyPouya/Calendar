package com.pouyaheydari.calendar.ui.theme

import com.pouyaheydari.calendar.core.pojo.Day
import com.pouyaheydari.calendar.domain.Event

data class CalendarScreenState(
    val today: Day,
    val displayDays: List<Day> = emptyList(),
    val shouldShowBottomSheet: Boolean = false,
    val selectedDay: Day,
    val selectedDayEvents: List<Event> = emptyList(),
)
