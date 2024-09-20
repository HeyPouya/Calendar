package com.pouyaheydari.calendar.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import com.pouyaheydari.calendar.core.pojo.Day
import com.pouyaheydari.calendar.core.utils.ResourceUtils
import javax.inject.Inject

/**
 * ViewModel Of DateDetailsDialogFragment
 */
@HiltViewModel
class DateDetailsViewModel @Inject constructor(private val resourceUtils: ResourceUtils) : ViewModel() {

    private val eventsLiveData = MutableLiveData<String>()

    /**
     * Checks if there are any events on the selected date
     * @param date the selected date
     */
    fun getEvents(date: Day) {
        val persianTemp = date.shamsiMonth.monthNumber * 100 + date.shamsiDay
        val gregorianTemp = date.gregorianMonth.monthNumber * 100 + date.gregorianDay
        val persianEvents = resourceUtils.eventP[persianTemp].orEmpty()
        val gregorianEvents = resourceUtils.eventG[gregorianTemp].orEmpty()
        eventsLiveData.value = buildString {
            if (persianEvents.isNotEmpty()) append(persianEvents)
            if (gregorianEvents.isNotEmpty()) append("\n$gregorianEvents")
        }
    }

    /**
     * Returns live data of [eventsLiveData]
     */
    fun getEventsLiveData(): LiveData<String> = eventsLiveData
}