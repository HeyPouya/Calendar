package com.pouyaheydari.calendar.domain

import com.pouyaheydari.calendar.core.pojo.DayType
import com.pouyaheydari.calendar.core.utils.ResourceUtils
import javax.inject.Inject

class GetEventsByDayUseCase @Inject constructor(private val resourceUtils: ResourceUtils) {
    operator fun invoke(day: DayType.Day): List<Event> {
        val persianTemp = day.shamsiMonth.monthNumber * 100 + day.shamsiDay
        val gregorianTemp = day.gregorianMonth.monthNumber * 100 + day.gregorianDay
        val persianEvents = resourceUtils.eventP[persianTemp].orEmpty()
        val gregorianEvents = resourceUtils.eventG[gregorianTemp].orEmpty()

        return buildList {
            persianEvents.forEach {
                add(Event(day = day.shamsiDay, description = it))
            }
            gregorianEvents.forEach {
                add(Event(day = day.gregorianDay, description = it))
            }
        }
    }
}
