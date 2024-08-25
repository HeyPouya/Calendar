package com.pouyaheydari.calendar.features.calendar.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import com.pouyaheydari.calendar.core.pojo.CalendarModel
import com.pouyaheydari.calendar.pojo.MonthType.NEXT_MONTH
import com.pouyaheydari.calendar.pojo.MonthType.PREVIOUS_MONTH
import com.pouyaheydari.calendar.repository.MonthGeneratorClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * ViewModel of CalendarFragment
 */
@HiltViewModel
class CalendarViewModel @Inject constructor(private val monthGenerator: MonthGeneratorClass) :
    ViewModel() {

    private val monthLiveData = MutableLiveData<List<CalendarModel>>()

    init {
        monthGenerator.getMonthList(PREVIOUS_MONTH)
        monthLiveData.value = monthGenerator.getMonthList(NEXT_MONTH)
    }

    /**
     * Fetches and publishes the next month (from the month that currently user is watching) as a list of days
     */
    fun getNextMonth() {
        monthLiveData.value = monthGenerator.getMonthList(NEXT_MONTH)
    }

    /**
     * Fetches and publishes the previous month (from the month that currently user is watching) as a list of days
     */
    fun getPreviousMonth() {
        monthLiveData.value = monthGenerator.getMonthList(PREVIOUS_MONTH)
    }

    /**
     * Returns live data of [monthLiveData]
     */
    fun getMonthLiveData(): Flow<List<CalendarModel>> = monthLiveData.asFlow()
}
