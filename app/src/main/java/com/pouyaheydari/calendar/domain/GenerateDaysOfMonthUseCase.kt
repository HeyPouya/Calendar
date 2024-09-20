package com.pouyaheydari.calendar.domain

import com.pouyaheydari.calendar.core.pojo.Day
import com.pouyaheydari.calendar.core.pojo.GregorianDate
import com.pouyaheydari.calendar.core.pojo.GregorianMonths
import com.pouyaheydari.calendar.core.pojo.ShamsiMonths
import com.pouyaheydari.calendar.core.utils.CalendarTool
import com.pouyaheydari.calendar.core.utils.EMPTY_DATE
import com.pouyaheydari.calendar.core.utils.FRIDAY
import com.pouyaheydari.calendar.core.utils.MONDAY
import com.pouyaheydari.calendar.core.utils.ResourceUtils
import com.pouyaheydari.calendar.core.utils.SATURDAY
import com.pouyaheydari.calendar.core.utils.SUNDAY
import com.pouyaheydari.calendar.core.utils.THURSDAY
import com.pouyaheydari.calendar.core.utils.TUESDAY
import com.pouyaheydari.calendar.core.utils.WEDNESDAY
import javax.inject.Inject

class GenerateDaysOfMonthUseCase @Inject constructor(
    private val calculateDaysInMonthUseCase: CalculateDaysInMonthUseCase,
    private val calendarTool: CalendarTool,
    private val resourceUtils: ResourceUtils,
    private val today: Day,
) {
    operator fun invoke(iranianYear: Int, shamsiMonth: ShamsiMonths) = buildList {
        val days = calculateDaysInMonthUseCase(iranianYear, shamsiMonth)

        calendarTool.setIranianDate(iranianYear, shamsiMonth.monthNumber, day = 1)
        val firstDay = calendarTool.getIranianDate()

        val addEmptyDays = addEmptyDays(firstDay.dayOfWeek)
        addAll(addEmptyDays)

        for (shamsiDay in 1..days) {
            calendarTool.setIranianDate(year = iranianYear, month = shamsiMonth.monthNumber, day = shamsiDay)
            val gregorianDay = calendarTool.getGregorianDate()
            add(
                Day(
                    shamsiDay = shamsiDay,
                    shamsiMonth = shamsiMonth,
                    shamsiYear = iranianYear,
                    dayOfWeek = gregorianDay.dayOfWeek,
                    gregorianDay = gregorianDay.day,
                    gregorianMonth = gregorianDay.month,
                    gregorianYear = gregorianDay.year,
                    today = checkToday(gregorianDay),
                    isShamsiHoliday = checkIsHoliday(
                        iranianYear,
                        shamsiMonth,
                        shamsiDay,
                        gregorianDay.dayOfWeek
                    )
                )
            )
        }
    }

    private fun checkIsHoliday(
        iranianYear: Int,
        shamsiMonth: ShamsiMonths,
        iranianDay: Int,
        dayOfWeek: Int
    ) = dayOfWeek == FRIDAY || (iranianYear == today.shamsiYear &&
            resourceUtils.vacationP.containsKey(shamsiMonth.monthNumber * 100 + iranianDay)
            )

    private fun checkToday(gregorianDay: GregorianDate) =
        today.gregorianYear == gregorianDay.year && today.gregorianMonth == gregorianDay.month && today.gregorianDay == gregorianDay.day

    private fun addEmptyDays(dayOfWeek: Int): ArrayList<Day> = when (dayOfWeek) {
        MONDAY -> emptyDayMaker(2)
        TUESDAY -> emptyDayMaker(3)
        WEDNESDAY -> emptyDayMaker(4)
        THURSDAY -> emptyDayMaker(5)
        FRIDAY -> emptyDayMaker(6)
        SATURDAY -> emptyDayMaker(0)
        SUNDAY -> emptyDayMaker(1)
        else -> throw IllegalArgumentException("Day is not defined in the week!")
    }

    private fun emptyDayMaker(dayOfWeek: Int): ArrayList<Day> {
        val list = arrayListOf<Day>()
        for (i in 1..dayOfWeek) {
            list.add(
                Day(
                    EMPTY_DATE,
                    ShamsiMonths.Farwarding,
                    EMPTY_DATE,
                    EMPTY_DATE,
                    EMPTY_DATE,
                    GregorianMonths.January,
                    EMPTY_DATE
                )
            )
        }
        return list
    }
}
