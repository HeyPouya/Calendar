package ir.apptune.calendar.repository.local

import ir.apptune.calendar.pojo.CalendarModel
import ir.apptune.calendar.pojo.DateModel
import ir.apptune.calendar.pojo.MonthType
import ir.apptune.calendar.pojo.MonthType.NEXT_MONTH
import ir.apptune.calendar.pojo.MonthType.PREVIOUS_MONTH
import ir.apptune.calendar.utils.CalendarTool

/**
 * This class generated a month before or after the current month that the user is looking at
 *
 * @property calendar: The object that holds the current date that the user is looking
 */
class MonthGeneratorClass(private var calendar: CalendarTool) {

    /**
     * Generates list of all days of the next or previous month
     *
     * @param monthType: it can be [NEXT_MONTH] or [PREVIOUS_MONTH]
     * @return
     */
    fun getMonthList(monthType: MonthType): List<CalendarModel> {
        val list = arrayListOf<CalendarModel>()
        val month = if (monthType == NEXT_MONTH) getNextMonthDate() else getPreviousMonthDate()
        list.addAll(addEmptyDays(calendar.dayOfWeek))
        for (i in 1..month.dayNumber) {
            with(calendar) {
                setIranianDate(month.year, month.month, i)
                list.add(CalendarModel(iranianDay, iranianMonth, iranianYear, dayOfWeek, gregorianDay, gregorianMonth, gregorianYear))
            }
        }
        return list
    }

    private fun getNextMonthDate(): DateModel {
        var month = calendar.iranianMonth
        var year = calendar.iranianYear

        if (month + 1 <= 12) {
            month++
        } else {
            month = 1
            year++
        }

        calendar.setIranianDate(year, month, 1)
        val dayNumber = if (month <= 6) 31 else if (month == 12 && !calendar.IsLeap(calendar.iranianYear)) 29 else 30
        return DateModel(year, month, dayNumber)
    }

    private fun getPreviousMonthDate(): DateModel {
        var month = calendar.iranianMonth
        var year = calendar.iranianYear

        if (month - 1 > 1) {
            month--
        } else {
            month = 12
            year--
        }

        calendar.setIranianDate(year, month, 1)
        val dayNumber = if (month <= 6) 31 else if (month == 12 && !calendar.IsLeap(calendar.iranianYear)) 29 else 30
        return DateModel(year, month, dayNumber)
    }

    private fun addEmptyDays(dayOfWeek: Int): ArrayList<CalendarModel> = when (dayOfWeek) {
        0 -> emptyDayMaker(2)
        1 -> emptyDayMaker(3)
        2 -> emptyDayMaker(4)
        3 -> emptyDayMaker(5)
        4 -> emptyDayMaker(6)
        5 -> emptyDayMaker(0)
        6 -> emptyDayMaker(1)
        else -> throw IllegalArgumentException("Day is not defined in the week!")
    }

    private fun emptyDayMaker(dayOfWeek: Int): ArrayList<CalendarModel> {
        val list = arrayListOf<CalendarModel>()
        for (i in 1..dayOfWeek) {
            list.add(CalendarModel(-1, -1, -1, -1, -1, -1, -1))
        }
        return list
    }

}

