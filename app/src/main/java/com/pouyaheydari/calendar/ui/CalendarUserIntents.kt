package com.pouyaheydari.calendar.ui

sealed interface CalendarUserIntents {
    data object OnNextMonthClicked : CalendarUserIntents
    data object OnPreviousMonthClicked : CalendarUserIntents
    data object OnDayClicked : CalendarUserIntents
}
