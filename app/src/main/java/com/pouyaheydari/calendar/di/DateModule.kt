package com.pouyaheydari.calendar.di

import com.pouyaheydari.calendar.core.pojo.DayType
import com.pouyaheydari.calendar.core.pojo.WeekDay
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
    fun todayProvider(localDate: LocalDate, calendarTool: CalendarTool): DayType.Day {
        calendarTool.setGregorianDate(
            year = localDate.year,
            month = localDate.monthNumber,
            day = localDate.dayOfMonth
        )
        val shamsiDate = calendarTool.getIranianDate()
        val gregorianDate = calendarTool.getGregorianDate()
        return DayType.Day(
            shamsiDate.day,
            shamsiDate.month,
            shamsiDate.year,
            WeekDay.entries.first { it.weekDayNumber == localDate.dayOfWeek.isoDayNumber - 1 },
            gregorianDate.day,
            gregorianDate.month,
            gregorianDate.year,
            today = true
        )
    }

    @Provides
    fun gregorianCalendarProvider(): LocalDate {
        val currentMoment: Instant = Clock.System.now()
        val datetimeInUtc: LocalDateTime = currentMoment.toLocalDateTime(TimeZone.currentSystemDefault())
        return LocalDate(datetimeInUtc.year, datetimeInUtc.month, datetimeInUtc.dayOfMonth)
    }
}
