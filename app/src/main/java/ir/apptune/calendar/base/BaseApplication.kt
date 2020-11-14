package ir.apptune.calendar.base

import android.app.Application
import ir.apptune.calendar.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApplication)
            modules(listOf(monthGeneratorModule, viewModelModule, todayModule, gregorianCalendarModule))
        }
    }
}