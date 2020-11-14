package ir.apptune.calendar.features.calendar.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ir.apptune.calendar.ResourceUtils
import ir.apptune.calendar.pojo.CalendarModel

class DateDetailsViewModel(date: CalendarModel) : ViewModel() {

    private val eventsLiveData = MutableLiveData<String>()

    init {
        val persianTemp = date.iranianMonth * 100 + date.iranianDay
        val gregorianTemp = date.gMonth * 100 + date.gDay
        val persianEvents = ResourceUtils.eventP[persianTemp].orEmpty()
        val gregorianEvents = ResourceUtils.eventG[gregorianTemp].orEmpty()
        eventsLiveData.value = buildString {
            if (persianEvents.isNotEmpty()) append(persianEvents)
            if (gregorianEvents.isNotEmpty()) append("\n$gregorianEvents")
        }
    }

    fun getEventsLiveData(): LiveData<String> = eventsLiveData
}