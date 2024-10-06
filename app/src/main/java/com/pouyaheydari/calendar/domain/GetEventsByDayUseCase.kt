package com.pouyaheydari.calendar.domain

import com.pouyaheydari.calendar.core.pojo.Event
import com.pouyaheydari.calendar.core.pojo.GregorianDate
import com.pouyaheydari.calendar.core.pojo.ShamsiDate
import com.pouyaheydari.calendar.core.utils.ResourceUtils
import javax.inject.Inject

class GetEventsByDayUseCase @Inject constructor(private val resourceUtils: ResourceUtils) {
    operator fun invoke(shamsiDate: ShamsiDate, gregorianDate: GregorianDate): List<Event> {
        val persianTemp = shamsiDate.month.monthNumber * 100 + shamsiDate.day
        val gregorianTemp = gregorianDate.month.monthNumber * 100 + gregorianDate.day
        val persianEvents = resourceUtils.eventP[persianTemp].orEmpty()
        val gregorianEvents = resourceUtils.eventG[gregorianTemp].orEmpty()

        return buildList {
            persianEvents.forEach {
                add(Event(day = shamsiDate.day, description = it))
            }
            gregorianEvents.forEach {
                add(Event(day = shamsiDate.day, description = it))
            }
        }
    }
}
