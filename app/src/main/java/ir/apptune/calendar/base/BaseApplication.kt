package ir.apptune.calendar.base

import android.app.Application
import ir.apptune.calendar.di.monthGeneratorModule
import ir.apptune.calendar.di.todayModule
import ir.apptune.calendar.di.viewModelModule
import ir.apptune.calendar.di.xmlParserModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApplication)
            modules(listOf(xmlParserModule, monthGeneratorModule, viewModelModule, todayModule))
        }
    }
}