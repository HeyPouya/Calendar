package com.pouyaheydari.calendar.domain

import com.pouyaheydari.calendar.core.pojo.DayType
import com.pouyaheydari.calendar.core.pojo.Event
import com.pouyaheydari.calendar.core.pojo.GregorianDate
import com.pouyaheydari.calendar.core.pojo.GregorianMonths
import com.pouyaheydari.calendar.core.pojo.ShamsiDate
import com.pouyaheydari.calendar.core.pojo.ShamsiMonths
import com.pouyaheydari.calendar.core.pojo.WeekDay
import com.pouyaheydari.calendar.core.utils.CalendarTool
import javax.inject.Inject

class GenerateMonthUseCase @Inject constructor(
    private val calculateDaysInMonthUseCase: CalculateDaysInMonthUseCase,
    private val getEventsByDayUseCase: GetEventsByDayUseCase,
    private val calendarTool: CalendarTool,
    private val today: DayType.Day,
) {
    operator fun invoke(iranianYear: Int, shamsiMonth: ShamsiMonths): Month {
        val gregorianMonths = mutableSetOf<GregorianMonths>()
        val gregorianYears = mutableSetOf<Int>()

        val days = buildList {
            val daysNumber = calculateDaysInMonthUseCase(iranianYear, shamsiMonth)

            calendarTool.setIranianDate(iranianYear, shamsiMonth.monthNumber, day = 1)
            val firstDay = calendarTool.getIranianDate()

            val addEmptyDays = emptyDayMaker(firstDay.weekDay.distanceFromFirstDayOfWeek)
            addAll(addEmptyDays)

            for (shamsiDay in 1..daysNumber) {
                calendarTool.setIranianDate(
                    year = iranianYear,
                    month = shamsiMonth.monthNumber,
                    day = shamsiDay
                )
                val gregorianDay = calendarTool.getGregorianDate()
                gregorianMonths.add(gregorianDay.month)
                gregorianYears.add(gregorianDay.year)
                val events = generateEvents(
                    calendarTool.getIranianDate(),
                    calendarTool.getGregorianDate(),
                )
                add(
                    DayType.Day(
                        shamsiDay = shamsiDay,
                        shamsiMonth = shamsiMonth,
                        shamsiYear = iranianYear,
                        weekDay = gregorianDay.weekDay,
                        gregorianDay = gregorianDay.day,
                        gregorianMonth = gregorianDay.month,
                        gregorianYear = gregorianDay.year,
                        today = checkToday(gregorianDay),
                        isShamsiHoliday = checkIsHoliday(events, gregorianDay.weekDay),
                        events = events
                    )
                )
            }
        }
        return Month(
            shamsiYear = iranianYear,
            shamsiMonth = shamsiMonth,
            days = days,
            gregorianMonths = gregorianMonths,
            gregorianYear = gregorianYears
        )
    }

    private fun generateEvents(iranianDate: ShamsiDate, gregorianDate: GregorianDate): List<Event> =
        getEventsByDayUseCase(iranianDate, gregorianDate)

    private fun checkIsHoliday(events: List<Event>, weekDay: WeekDay) =
        weekDay == WeekDay.Friday || events.any { it.isHoliday }

    private fun checkToday(gregorianDay: GregorianDate) =
        today.gregorianYear == gregorianDay.year && today.gregorianMonth == gregorianDay.month && today.gregorianDay == gregorianDay.day

    private fun emptyDayMaker(dayOfWeek: Int) = buildList {
        repeat(dayOfWeek) {
            add(DayType.EmptyDay)
        }
    }
}
