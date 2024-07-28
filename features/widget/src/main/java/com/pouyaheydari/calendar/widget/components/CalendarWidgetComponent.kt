package com.pouyaheydari.calendar.widget.components

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import com.pouyaheydari.calendar.core.pojo.CalendarModel
import com.pouyaheydari.calendar.core.utils.extensions.toEnglishMonth
import com.pouyaheydari.calendar.core.utils.extensions.toPersianMonth
import com.pouyaheydari.calendar.core.utils.extensions.toPersianNumber
import com.pouyaheydari.calendar.core.utils.extensions.toPersianWeekDay
import com.pouyaheydari.calendar.widget.R

private const val MAIN_ACTIVITY = ".MainActivity"

@Composable
internal fun CalendarWidgetComponent(context: Context, today: CalendarModel) {
    val activity = (Class.forName(context.packageName.plus(MAIN_ACTIVITY))
        .asSubclass(AppCompatActivity::class.java))

    val rlm = '\u200F'
    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .clickable(actionStartActivity(activity = activity))
            .padding(16.dp)
            .background(GlanceTheme.colors.widgetBackground),
        horizontalAlignment = Alignment.Horizontal.CenterHorizontally
    ) {
        Text(
            modifier = GlanceModifier.defaultWeight(),
            maxLines = 1,
            text = context.getString(
                R.string.persian_full_date,
                today.dayOfWeek.toPersianWeekDay(context),
                today.iranianDay.toPersianNumber(),
                today.iranianMonth.toPersianMonth(context),
                today.iranianYear.toPersianNumber()
            ),
            style = TextStyle(
                color = GlanceTheme.colors.onSurface,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            ),
        )
        Text(
            modifier = GlanceModifier.defaultWeight(),
            maxLines = 1,
            text = rlm + context.getString(
                R.string.gregorian_full_date,
                today.gDay.toPersianNumber(),
                today.gMonth.toEnglishMonth(context),
                today.gYear.toPersianNumber()
            ),
            style = TextStyle(
                color = GlanceTheme.colors.onSurface,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
            ),
        )
    }
}
