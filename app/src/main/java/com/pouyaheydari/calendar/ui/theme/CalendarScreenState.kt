package com.pouyaheydari.calendar.ui.theme

import com.pouyaheydari.calendar.core.pojo.DayType
import com.pouyaheydari.calendar.domain.Event

data class CalendarScreenState(
    val today: DayType.Day,
    val displayDays: List<DayType> = emptyList(),
    val shouldShowBottomSheet: Boolean = false,
    val selectedDay: DayType.Day,
    val selectedDayEvents: List<Event> = emptyList(),
)
