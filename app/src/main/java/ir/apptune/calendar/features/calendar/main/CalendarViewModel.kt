package ir.apptune.calendar.features.calendar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.apptune.calendar.pojo.CalendarModel
import ir.apptune.calendar.pojo.MonthType.NEXT_MONTH
import ir.apptune.calendar.pojo.MonthType.PREVIOUS_MONTH
import ir.apptune.calendar.repository.local.MonthGeneratorClass
import javax.inject.Inject

/**
 * ViewModel of CalendarFragment
 */
@HiltViewModel
class CalendarViewModel @Inject constructor(private val monthGenerator: MonthGeneratorClass) : ViewModel() {

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
    fun getMonthLiveData(): LiveData<List<CalendarModel>> = monthLiveData
}