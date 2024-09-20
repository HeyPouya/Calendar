package com.pouyaheydari.calendar.ui

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
import com.pouyaheydari.calendar.core.pojo.Day
import com.pouyaheydari.calendar.core.utils.extensions.toPersianNumber
import com.pouyaheydari.calendar.core.utils.extensions.toPersianWeekDay
import com.pouyaheydari.calendar.ui.components.HeaderComponent
import com.pouyaheydari.calendar.ui.components.MonthComponent
import com.pouyaheydari.calendar.ui.components.MonthYearTitleComponent
import com.pouyaheydari.calendar.ui.components.WeekDaysComponent

@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    today: Day,
    viewModel: CalendarViewModel = viewModel(),
    onDaySelected: (Day) -> Unit
) {
    val days = viewModel.screenState.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    CalendarComponent(
        modifier,
        today,
        days,
        context,
        onDaySelected = onDaySelected,
        onNextMonthClicked = { viewModel.onIntent(CalendarUserIntents.OnNextMonthClicked) },
        onPreviousMonthClicked = { viewModel.onIntent(CalendarUserIntents.OnPreviousMonthClicked) })
}

@Composable
fun CalendarComponent(
    modifier: Modifier = Modifier,
    today: Day,
    days: List<Day>?,
    context: Context,
    onDaySelected: (Day) -> Unit,
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
                today.shamsiDay.toPersianNumber(),
                today.shamsiMonth.getName(context),
                today.shamsiYear.toPersianNumber()
            ),
            gregorianDate = rlm + stringResource(
                id = R.string.gregorian_full_date,
                today.gregorianDay.toPersianNumber(),
                today.gregorianMonth.getName(context),
                today.gregorianYear.toPersianNumber()
            )
        )
        Spacer(modifier = Modifier.padding(all = 8.dp))
        MonthYearTitleComponent(
            monthName = days?.lastOrNull()?.shamsiMonth?.getName(context).orEmpty(),
            year = days?.lastOrNull()?.shamsiYear?.toPersianNumber().orEmpty(),
            onNextMonthClicked = onNextMonthClicked,
            onPreviousMonthClicked = onPreviousMonthClicked
        )
        Spacer(modifier = Modifier.padding(all = 8.dp))
        WeekDaysComponent(weekDays = getWeekDays())
        MonthComponent(list = days.orEmpty(), onItemClicked = onDaySelected)
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