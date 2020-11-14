package ir.apptune.calendar.di

import ir.apptune.calendar.features.calendar.details.DateDetailsViewModel
import ir.apptune.calendar.features.calendar.main.CalendarViewModel
import ir.apptune.calendar.pojo.CalendarModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CalendarViewModel(get()) }
    viewModel { (date: CalendarModel) -> DateDetailsViewModel(date) }
}