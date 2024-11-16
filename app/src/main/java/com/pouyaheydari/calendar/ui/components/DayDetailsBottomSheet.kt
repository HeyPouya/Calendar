package com.pouyaheydari.calendar.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.pouyaheydari.calendar.R
import com.pouyaheydari.calendar.core.pojo.DayType
import com.pouyaheydari.calendar.core.pojo.Event
import com.pouyaheydari.calendar.core.pojo.GregorianMonths
import com.pouyaheydari.calendar.core.pojo.ShamsiMonths
import com.pouyaheydari.calendar.core.pojo.WeekDay
import com.pouyaheydari.calendar.core.utils.toPersianNumber
import com.pouyaheydari.calendar.ui.RLM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DayDetailsBottomSheet(
    selectedDay: DayType.Day,
    isHoliday: Boolean = false,
    setReminderText: String = "",
    onSetReminderClicked: () -> Unit = {},
    onBottomSheetDismissed: () -> Unit = {},
    shouldShowBottomSheet: Boolean = false,
) {
    val context = LocalContext.current
    AnimatedVisibility(visible = shouldShowBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = onBottomSheetDismissed,
            containerColor = MaterialTheme.colorScheme.surface,
        ) {
            val headerColor = if (isHoliday) Color.Red else MaterialTheme.colorScheme.primary
            HeaderComponent(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                dayOfWeek = selectedDay.weekDay.getName(context),
                iranianDate = RLM + stringResource(
                    id = R.string.persian_day_month,
                    selectedDay.shamsiDay.toPersianNumber(),
                    selectedDay.shamsiMonth.getName(context),
                    selectedDay.shamsiYear.toPersianNumber()
                ),
                gregorianDate = RLM + stringResource(
                    id = R.string.gregorian_full_date,
                    selectedDay.gregorianDay.toPersianNumber(),
                    selectedDay.gregorianMonth.getName(context),
                    selectedDay.gregorianYear.toPersianNumber()
                ),
                elevation = CardDefaults.cardElevation(),
                headerColor = headerColor
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (selectedDay.events.isEmpty()) {
                    emptyStateComponent()
                } else {
                    item {
                        EventComponent(day = selectedDay)
                    }
                }
                setReminderInCalendarButtonComponent(onSetReminderClicked, setReminderText)
            }
        }
    }
}

private fun LazyListScope.emptyStateComponent() {
    item {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(R.string.no_events)
        )
    }
}

private fun LazyListScope.setReminderInCalendarButtonComponent(
    onSetReminderClicked: () -> Unit,
    setReminderText: String
) {
    item {
        OutlinedButton(modifier = Modifier.padding(16.dp), onClick = onSetReminderClicked) {
            Text(text = setReminderText)
        }
    }
}

@PreviewScreenSizes
@PreviewFontScale
@PreviewLightDark
@Composable
private fun Preview() {
    DayDetailsBottomSheet(
        shouldShowBottomSheet = true,
        setReminderText = "کاری برای انجام در این روز تنظیم کنید",
        selectedDay = DayType.Day(
            shamsiDay = 10,
            shamsiMonth = ShamsiMonths.Farwarding,
            shamsiYear = 1402,
            weekDay = WeekDay.Saturday,
            gregorianDay = 22,
            gregorianMonth = GregorianMonths.Jun,
            gregorianYear = 2024,
            isShamsiHoliday = false,
            today = false,
            events = listOf(Event("روز جهانی درخت کاری", true))
        ),
    )
}
