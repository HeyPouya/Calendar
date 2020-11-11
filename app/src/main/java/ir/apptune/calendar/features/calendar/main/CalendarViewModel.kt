package ir.apptune.calendar.features.calendar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ir.apptune.calendar.pojo.CalendarModel
import ir.apptune.calendar.pojo.MonthType.NEXT_MONTH
import ir.apptune.calendar.pojo.MonthType.PREVIOUS_MONTH
import ir.apptune.calendar.repository.local.MonthGeneratorClass

class CalendarViewModel(private val monthGenerator: MonthGeneratorClass) : ViewModel() {

    private val monthLiveData = MutableLiveData<List<CalendarModel>>()

    init {
        monthGenerator.getMonthList(PREVIOUS_MONTH)
        monthLiveData.value = monthGenerator.getMonthList(NEXT_MONTH)
    }

    fun getNextMonth() {
        monthLiveData.value = monthGenerator.getMonthList(NEXT_MONTH)
    }

    fun getPreviousMonth() {
        monthLiveData.value = monthGenerator.getMonthList(PREVIOUS_MONTH)
    }

    fun getMonthLiveData(): LiveData<List<CalendarModel>> = monthLiveData
}