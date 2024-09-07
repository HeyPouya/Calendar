package com.pouyaheydari.calendar.widget

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.glance.GlanceId
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import com.pouyaheydari.calendar.core.pojo.CalendarModel
import com.pouyaheydari.calendar.core.utils.extensions.toEnglishMonth
import com.pouyaheydari.calendar.core.utils.extensions.toPersianMonth
import com.pouyaheydari.calendar.core.utils.extensions.toPersianNumber
import com.pouyaheydari.calendar.core.utils.extensions.toPersianWeekDay
import com.pouyaheydari.calendar.widget.components.CalendarWidgetComponent
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

private const val MAIN_ACTIVITY = ".MainActivity"

class CalendarAppWidget : GlanceAppWidget() {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface CalendarAppWidgetEntryPoint {
        fun provideToday(): CalendarModel
    }

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val appContext = context.applicationContext ?: throw IllegalStateException()
        val hiltEntryPoint =
            EntryPointAccessors.fromApplication(
                appContext,
                CalendarAppWidgetEntryPoint::class.java
            )

        val today = hiltEntryPoint.provideToday()
        val persianDate = getPersianDate(context, today)
        val gregorianDate = getGregorianDate(context, today)
        val mainActivityPath = context.packageName.plus(MAIN_ACTIVITY)
        val activity = (Class.forName(mainActivityPath).asSubclass(AppCompatActivity::class.java))

        provideContent {
            GlanceTheme {
                CalendarWidgetComponent(
                    persianDate = persianDate,
                    gregorianDate = gregorianDate,
                    mainActivity = activity
                )
            }
        }
    }

    private fun getPersianDate(context: Context, today: CalendarModel) = context.getString(
        R.string.persian_full_date,
        today.dayOfWeek.toPersianWeekDay(context),
        today.iranianDay.toPersianNumber(),
        today.iranianMonth.toPersianMonth(context),
        today.iranianYear.toPersianNumber()
    )

    private fun getGregorianDate(context: Context, today: CalendarModel): String {

        // We have to add this to prevent misplacement of dates when combining persian text with numbers
        val rlm = '\u200F'

        return rlm + context.getString(
            R.string.gregorian_full_date,
            today.gDay.toPersianNumber(),
            today.gMonth.toEnglishMonth(context),
            today.gYear.toPersianNumber()
        )
    }
}
