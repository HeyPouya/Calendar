package ir.apptune.calendar.di

import ir.apptune.calendar.pojo.CalendarModel
import ir.apptune.calendar.repository.local.CalendarTool
import org.koin.dsl.module

val todayModule = module {
    single {
        val calendarTool = CalendarTool()
        with(calendarTool) {
            CalendarModel(iranianDay, iranianMonth, iranianYear, dayOfWeek, gregorianDay, gregorianMonth, gregorianYear, today = true)
        }
    }
}