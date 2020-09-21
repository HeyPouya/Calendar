package ir.apptune.calendar.di

import ir.apptune.calendar.features.calendar.CalendarViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CalendarViewModel(get()) }
}