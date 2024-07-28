package com.pouyaheydari.calendar.widget

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import com.pouyaheydari.calendar.core.pojo.CalendarModel
import com.pouyaheydari.calendar.widget.components.CalendarWidgetComponent
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

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

        provideContent {
            GlanceTheme {
                CalendarWidgetComponent(context, today)
            }
        }
    }
}
