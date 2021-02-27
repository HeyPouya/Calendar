package ir.apptune.calendar.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.apptune.calendar.ResourceUtils
import ir.apptune.calendar.pojo.CalendarModel
import ir.apptune.calendar.repository.local.MonthGeneratorClass
import ir.apptune.calendar.utils.CalendarTool
import java.util.*
import javax.inject.Singleton

/**
 * This object contains hilt modules needed for injection
 */
@Module
@InstallIn(SingletonComponent::class)
object DateModule {

    /**
     * Provides current date details
     */
    @Provides
    @Singleton
    fun todayProvider(calendar: GregorianCalendar): CalendarModel {
        val calendarTool = CalendarTool(calendar)
        return with(calendarTool) {
            CalendarModel(iranianDay, iranianMonth, iranianYear, dayOfWeek, gregorianDay, gregorianMonth, gregorianYear, today = true)
        }
    }

    /**
     * Provides Gregorian calendar instance
     */
    @Provides
    @Singleton
    fun gregorianCalendarProvider() = GregorianCalendar()

    /**
     * Provides CalendarTool instance to convert dates from Gregorian to Shamsi
     */
    @Provides
    @Singleton
    fun calendarToolProvider(calendar: GregorianCalendar) = CalendarTool(calendar)

    /**
     * Provides local repository
     */
    @Provides
    @Singleton
    fun monthGeneratorProvider(app: Application, calendarModel: CalendarModel, calendarTool: CalendarTool): MonthGeneratorClass {
        ResourceUtils(app)
        return MonthGeneratorClass(calendarTool, calendarModel)
    }
}