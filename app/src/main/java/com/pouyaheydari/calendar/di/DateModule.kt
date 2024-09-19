package com.pouyaheydari.calendar.di

import android.app.Application
import com.pouyaheydari.calendar.core.pojo.Day
import com.pouyaheydari.calendar.core.utils.CalendarTool
import com.pouyaheydari.calendar.core.utils.ResourceUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.toLocalDateTime
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DateModule {

    @Provides
    fun todayProvider(localDate: LocalDate, calendarTool: CalendarTool): Day {
        val gregorianDay = localDate.dayOfMonth
        val gregorianMonth = localDate.monthNumber
        val gregorianYear = localDate.year
        calendarTool.setGregorianDate(year = gregorianYear, month = gregorianMonth, day = gregorianDay)
        val iranianDate = calendarTool.getIranianDate()
        return Day(
            iranianDate.day,
            iranianDate.month,
            iranianDate.year,
            localDate.dayOfWeek.isoDayNumber,
            gregorianDay,
            gregorianMonth,
            gregorianYear,
            today = true
        )
    }

    @Provides
    fun gregorianCalendarProvider(): LocalDate {
        val currentMoment: Instant = Clock.System.now()
        val datetimeInUtc: LocalDateTime = currentMoment.toLocalDateTime(TimeZone.UTC)
        return LocalDate(datetimeInUtc.year, datetimeInUtc.month, datetimeInUtc.dayOfMonth)
    }

    @Provides
    @Singleton
    fun resourceUtilsProvider(app: Application) = ResourceUtils(app)
}
