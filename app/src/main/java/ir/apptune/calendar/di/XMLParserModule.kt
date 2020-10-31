package ir.apptune.calendar.di

import ir.apptune.calendar.ResourceUtils
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val xmlParserModule = module {
    single { ResourceUtils(androidApplication()) }
}