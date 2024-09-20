package com.pouyaheydari.calendar.di

import com.pouyaheydari.calendar.core.pojo.Day
import com.pouyaheydari.calendar.core.utils.CalendarTool
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

@Module
@InstallIn(SingletonComponent::class)
object DateModule {

    @Provides
    fun todayProvider(localDate: LocalDate, calendarTool: CalendarTool): Day {
        calendarTool.setGregorianDate(
            year = localDate.year,
            month = localDate.monthNumber,
            day = localDate.dayOfMonth
        )
        val shamsiDate = calendarTool.getIranianDate()
        val gregorianDate = calendarTool.getGregorianDate()
        return Day(
            shamsiDate.day,
            shamsiDate.month,
            shamsiDate.year,
            localDate.dayOfWeek.isoDayNumber,
            gregorianDate.day,
            gregorianDate.month,
            gregorianDate.year,
            today = true
        )
    }

    @Provides
    fun gregorianCalendarProvider(): LocalDate {
        val currentMoment: Instant = Clock.System.now()
        val datetimeInUtc: LocalDateTime = currentMoment.toLocalDateTime(TimeZone.UTC)
        return LocalDate(datetimeInUtc.year, datetimeInUtc.month, datetimeInUtc.dayOfMonth)
    }
}
