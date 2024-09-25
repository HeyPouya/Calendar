package com.pouyaheydari.calendar.ui

import com.pouyaheydari.calendar.core.pojo.Day

sealed interface CalendarUserIntents {
    data object OnNextMonthClicked : CalendarUserIntents
    data object OnPreviousMonthClicked : CalendarUserIntents
    data class OnDayClicked(val day: Day) : CalendarUserIntents
    data object OnBottomSheetDismissed : CalendarUserIntents
}
