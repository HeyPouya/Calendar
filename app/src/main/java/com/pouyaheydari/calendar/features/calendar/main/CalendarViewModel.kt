package com.pouyaheydari.calendar.features.calendar.main

import androidx.lifecycle.ViewModel
import com.pouyaheydari.calendar.core.pojo.CalendarModel
import com.pouyaheydari.calendar.pojo.MonthType.NEXT_MONTH
import com.pouyaheydari.calendar.pojo.MonthType.PREVIOUS_MONTH
import com.pouyaheydari.calendar.repository.MonthGeneratorClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * ViewModel of CalendarFragment
 */
@HiltViewModel
class CalendarViewModel @Inject constructor(private val monthGenerator: MonthGeneratorClass) :
    ViewModel() {

    private val monthLiveData = MutableStateFlow<List<CalendarModel>>(emptyList())

    init {
        monthGenerator.getMonthList(PREVIOUS_MONTH)
        monthLiveData.update { monthGenerator.getMonthList(NEXT_MONTH) }
    }

    /**
     * Fetches and publishes the next month (from the month that currently user is watching) as a list of days
     */
    fun getNextMonth() {
        monthLiveData.update { monthGenerator.getMonthList(NEXT_MONTH) }
    }

    /**
     * Fetches and publishes the previous month (from the month that currently user is watching) as a list of days
     */
    fun getPreviousMonth() {
        monthLiveData.update { monthGenerator.getMonthList(PREVIOUS_MONTH) }
    }

    /**
     * Returns live data of [monthLiveData]
     */
    fun getMonthLiveData(): StateFlow<List<CalendarModel>> = monthLiveData
}
