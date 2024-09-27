package com.pouyaheydari.calendar.ui

import com.pouyaheydari.calendar.core.pojo.DayType

sealed interface CalendarUserIntents {
    data object OnNextMonthClicked : CalendarUserIntents
    data object OnPreviousMonthClicked : CalendarUserIntents
    data class OnDayClicked(val day: DayType.Day) : CalendarUserIntents
    data object OnBottomSheetDismissed : CalendarUserIntents
}
