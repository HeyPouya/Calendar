package com.pouyaheydari.calendar.core.utils

import com.pouyaheydari.calendar.core.pojo.Event
import com.pouyaheydari.calendar.core.pojo.EventItem
import com.pouyaheydari.calendar.core.pojo.GregorianMonths
import com.pouyaheydari.calendar.core.pojo.ShamsiMonths
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class EventRepository @Inject constructor(
    @Named("shamsi_events") private val shamsiEvents: List<EventItem>,
    @Named("gregorian_events") private val gregorianEvents: List<EventItem>,
    @Named("hijri_events") private val hijriEvents: List<EventItem>,
) {

    fun getShamsiEvents(month: ShamsiMonths, day: Int): List<Event> =
        shamsiEvents.filter { it.day == day && it.month == month.monthNumber }.map {
            Event(description = it.title, isHoliday = it.holiday)
        }

    fun getGregorianEvents(month: GregorianMonths, day: Int): List<Event> =
        gregorianEvents.filter { it.day == day && it.month == month.monthNumber }.map {
            Event(description = it.title, isHoliday = it.holiday)
        }

    fun getHijriEvents(month: ShamsiMonths, day: Int): List<Event> =
        hijriEvents.filter { it.day == day && it.month == month.monthNumber }.map {
            Event(description = it.title, isHoliday = it.holiday)
        }
}
