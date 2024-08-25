package com.pouyaheydari.calendar.features.calendar.main

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pouyaheydari.calendar.R
import com.pouyaheydari.calendar.core.pojo.CalendarModel
import com.pouyaheydari.calendar.core.utils.extensions.toEnglishMonth
import com.pouyaheydari.calendar.core.utils.extensions.toPersianMonth
import com.pouyaheydari.calendar.core.utils.extensions.toPersianNumber
import com.pouyaheydari.calendar.core.utils.extensions.toPersianWeekDay
import com.pouyaheydari.calendar.features.calendar.main.components.HeaderComponent
import com.pouyaheydari.calendar.features.calendar.main.components.MonthComponent
import com.pouyaheydari.calendar.features.calendar.main.components.MonthYearTitleComponent
import com.pouyaheydari.calendar.features.calendar.main.components.WeekDaysComponent

@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    today: CalendarModel,
    viewModel: CalendarViewModel = viewModel(),
    onDaySelected: (CalendarModel) -> Unit
) {
    val date =
        viewModel.getMonthLiveData().collectAsStateWithLifecycle(initialValue = emptyList()).value
    val context = LocalContext.current
    CalendarComponent(
        modifier,
        today,
        date,
        context,
        onDaySelected = onDaySelected,
        onNextMonthClicked = { viewModel.getNextMonth() },
        onPreviousMonthClicked = { viewModel.getPreviousMonth() })
}

@Composable
fun CalendarComponent(
    modifier: Modifier = Modifier,
    today: CalendarModel,
    date: List<CalendarModel>?,
    context: Context,
    onDaySelected: (CalendarModel) -> Unit,
    onNextMonthClicked: () -> Unit = {},
    onPreviousMonthClicked: () -> Unit = {}
) {
    Column(modifier = modifier.fillMaxSize()) {

        // We have to add this to prevent misplacement of dates when combining persian text with numbers
        val rlm = '\u200F'

        HeaderComponent(
            dayOfWeek = stringResource(
                id = R.string.today_is_day_of_week,
                today.dayOfWeek.toPersianWeekDay(context)
            ),
            iranianDate = rlm + stringResource(
                id = R.string.persian_day_month,
                today.iranianDay.toPersianNumber(),
                today.iranianMonth.toPersianMonth(context),
                today.iranianYear.toPersianNumber()
            ),
            gregorianDate = rlm + stringResource(
                id = R.string.gregorian_full_date,
                today.gDay.toPersianNumber(),
                today.gMonth.toEnglishMonth(context),
                today.gYear.toPersianNumber()
            )
        )
        Spacer(modifier = Modifier.padding(all = 8.dp))
        MonthYearTitleComponent(
            monthName = date?.lastOrNull()?.iranianMonth?.toPersianMonth(context).orEmpty(),
            year = date?.lastOrNull()?.iranianYear?.toPersianNumber().orEmpty(),
            onNextMonthClicked = onNextMonthClicked,
            onPreviousMonthClicked = onPreviousMonthClicked
        )
        Spacer(modifier = Modifier.padding(all = 8.dp))
        WeekDaysComponent(weekDays = getWeekDays())
        MonthComponent(list = date.orEmpty(), onItemClicked = onDaySelected)
    }
}

@Composable
private fun getWeekDays() = listOf(
    stringResource(id = R.string.saturday),
    stringResource(id = R.string.sunday),
    stringResource(id = R.string.monday),
    stringResource(id = R.string.tuesday),
    stringResource(id = R.string.wednesdays),
    stringResource(id = R.string.thursday),
    stringResource(id = R.string.friday),
).reversed()