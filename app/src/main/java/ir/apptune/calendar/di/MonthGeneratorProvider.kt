package ir.apptune.calendar.di

import ir.apptune.calendar.repository.local.CalendarTool
import ir.apptune.calendar.repository.local.MonthGeneratorClass
import org.koin.dsl.module

val monthGeneratorModule = module {
    single {
        MonthGeneratorClass(get())
    }

    single {
        CalendarTool()
    }
}