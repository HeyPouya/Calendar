package ir.apptune.calendar.di

import ir.apptune.calendar.ResourceUtils
import ir.apptune.calendar.pojo.CalendarModel
import ir.apptune.calendar.repository.local.MonthGeneratorClass
import ir.apptune.calendar.utils.CalendarTool
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import java.util.*

val todayModule = module {
    single {
        val calendarTool = CalendarTool(get())
        with(calendarTool) {
            CalendarModel(iranianDay, iranianMonth, iranianYear, dayOfWeek, gregorianDay, gregorianMonth, gregorianYear, today = true)
        }
    }
}

val monthGeneratorModule = module {
    single {
        ResourceUtils(androidApplication())
        MonthGeneratorClass(get(), get())
    }

    single {
        CalendarTool(get())
    }
}

val gregorianCalendarModule = module {
    single { GregorianCalendar() }
}