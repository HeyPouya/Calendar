package com.pouyaheydari.calendar.ui

import androidx.lifecycle.ViewModel
import com.pouyaheydari.calendar.core.pojo.Day
import com.pouyaheydari.calendar.pojo.MonthType.NEXT_MONTH
import com.pouyaheydari.calendar.pojo.MonthType.PREVIOUS_MONTH
import com.pouyaheydari.calendar.repository.MonthGeneratorClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(private val monthGenerator: MonthGeneratorClass) :
    ViewModel() {

    private val _screenState = MutableStateFlow<List<Day>>(emptyList())
    val screenState: StateFlow<List<Day>> = _screenState

    init {
        monthGenerator.getMonthList(PREVIOUS_MONTH)
        _screenState.update { monthGenerator.getMonthList(NEXT_MONTH) }
    }

    /**
     * Fetches and publishes the next month (from the month that currently user is watching) as a list of days
     */
    fun getNextMonth() {
        _screenState.update { monthGenerator.getMonthList(NEXT_MONTH) }
    }

    /**
     * Fetches and publishes the previous month (from the month that currently user is watching) as a list of days
     */
    fun getPreviousMonth() {
        _screenState.update { monthGenerator.getMonthList(PREVIOUS_MONTH) }
    }
}
