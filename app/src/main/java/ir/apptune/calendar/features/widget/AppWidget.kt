package ir.apptune.calendar.features.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import dagger.hilt.android.AndroidEntryPoint
import ir.apptune.calendar.MainActivity
import ir.apptune.calendar.R
import ir.apptune.calendar.pojo.CalendarModel
import ir.apptune.calendar.utils.extensions.toEnglishMonth
import ir.apptune.calendar.utils.extensions.toPersianMonth
import ir.apptune.calendar.utils.extensions.toPersianNumber
import ir.apptune.calendar.utils.extensions.toPersianWeekDay
import javax.inject.Inject

/**
 * The CLass that widget uses to show data
 */
@AndroidEntryPoint
class AppWidget : AppWidgetProvider() {

    @Inject
    lateinit var today: CalendarModel

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        val remoteViews = RemoteViews(context.packageName, R.layout.widget_layout).apply {
            setOnClickPendingIntent(R.id.layout_widget, pendingIntent)
        }

        with(today) {
            remoteViews.setTextViewText(R.id.txtWidgetPersianDate, context.getString(R.string.persian_full_date,
                    dayOfWeek.toPersianWeekDay(context),
                    iranianDay.toPersianNumber(),
                    iranianMonth.toPersianMonth(context),
                    iranianYear.toPersianNumber()))
            remoteViews.setTextViewText(R.id.txtWidgetGregorianDate, context.getString(R.string.gregorian_full_date,
                    gDay.toPersianNumber(),
                    gMonth.toEnglishMonth(context),
                    gYear.toPersianNumber()))
        }

        appWidgetIds.forEach { appWidgetManager.updateAppWidget(it, remoteViews) }
    }
}