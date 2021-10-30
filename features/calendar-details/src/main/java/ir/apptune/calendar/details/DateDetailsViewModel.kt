package ir.apptune.calendar.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.apptune.calendar.core.pojo.CalendarModel
import ir.apptune.calendar.core.utils.ResourceUtils
import javax.inject.Inject

/**
 * ViewModel Of DateDetailsDialogFragment
 */
@HiltViewModel
class DateDetailsViewModel @Inject constructor() : ViewModel() {

    private val eventsLiveData = MutableLiveData<String>()

    /**
     * Checks if there are any events on the selected date
     * @param date the selected date
     */
    fun getEvents(date: CalendarModel) {
        val persianTemp = date.iranianMonth * 100 + date.iranianDay
        val gregorianTemp = date.gMonth * 100 + date.gDay
        val persianEvents = ResourceUtils.eventP[persianTemp].orEmpty()
        val gregorianEvents = ResourceUtils.eventG[gregorianTemp].orEmpty()
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