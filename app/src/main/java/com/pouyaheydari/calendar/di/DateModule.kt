package com.pouyaheydari.calendar.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.pouyaheydari.calendar.core.pojo.Day
import com.pouyaheydari.calendar.core.utils.CalendarTool
import com.pouyaheydari.calendar.core.utils.ResourceUtils
import com.pouyaheydari.calendar.repository.MonthGeneratorClass
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DateModule {

    @Provides
    @Singleton
    fun todayProvider(calendar: GregorianCalendar): Day {
        val calendarTool = CalendarTool(calendar)
        return with(calendarTool) {
            Day(
                iranianDay,
                iranianMonth,
                iranianYear,
                dayOfWeek,
                gregorianDay,
                gregorianMonth,
                gregorianYear,
                today = true
            )
        }
    }

    @Provides
    @Singleton
    fun gregorianCalendarProvider() = GregorianCalendar()

    @Provides
    @Singleton
    fun calendarToolProvider(calendar: GregorianCalendar) =
        CalendarTool(calendar)

    @Provides
    @Singleton
    fun monthGeneratorProvider(
        app: Application,
        day: Day,
        calendarTool: CalendarTool
    ): MonthGeneratorClass {
        ResourceUtils(app)
        return MonthGeneratorClass(calendarTool, day)
    }
}
