package com.pouyaheydari.calendar

import android.app.Application
import android.os.StrictMode
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PersianCalendarApp : Application() {

    override fun onCreate() {
        super.onCreate()
        enableStrictModeForDebugFlavour()
    }

    private fun enableStrictModeForDebugFlavour() {
        if (BuildConfig.BUILD_TYPE == "debug") {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()
                    .build()
            )
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .build()
            )
        }
    }
}