package ir.apptune.calendar.di

import ir.apptune.calendar.repository.local.MonthGeneratorClass
import ir.apptune.calendar.utils.CalendarTool
import org.koin.dsl.module

val monthGeneratorModule = module {
    single {
        MonthGeneratorClass(get(), get(), get())
    }

    single {
        CalendarTool()
    }
}