package com.pouyaheydari.calendar.domain

import com.pouyaheydari.calendar.core.pojo.Event
import com.pouyaheydari.calendar.core.pojo.GregorianDate
import com.pouyaheydari.calendar.core.pojo.ShamsiDate
import com.pouyaheydari.calendar.core.utils.EventRepository
import javax.inject.Inject

class GetEventsByDayUseCase @Inject constructor(private val resourceUtils: EventRepository) {
    operator fun invoke(shamsiDate: ShamsiDate, gregorianDate: GregorianDate): List<Event> {
        val persianEvents =
            resourceUtils.getShamsiEvents(shamsiDate.month, shamsiDate.day)
        val gregorianEvents =
            resourceUtils.getGregorianEvents(gregorianDate.month, gregorianDate.day)
        val hijriEvents =
            resourceUtils.getHijriEvents(shamsiDate.month, shamsiDate.day)

        return buildList {
            persianEvents.forEach {
                add(
                    Event(description = it.description, isHoliday = it.isHoliday)
                )
            }
            gregorianEvents.forEach {
                add(
                    Event(description = it.description, isHoliday = it.isHoliday)
                )
            }
            hijriEvents.forEach {
                add(
                    Event(description = it.description, isHoliday = it.isHoliday)
                )
            }
        }
    }
}
