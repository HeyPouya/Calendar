package com.pouyaheydari.calendar.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.pouyaheydari.calendar.R
import com.pouyaheydari.calendar.core.pojo.Event

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayDetailsBottomSheet(
    dayOfWeek: String,
    iranianDate: String,
    gregorianDate: String,
    events: List<Event>,
    setReminderText: String = "",
    onSetReminderClicked: () -> Unit = {},
    onBottomSheetDismissed: () -> Unit = {},
    shouldShowBottomSheet: Boolean = false,
    isHoliday: Boolean = false,
) {
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
                dayOfWeek = dayOfWeek,
                iranianDate = iranianDate,
                gregorianDate = gregorianDate,
                elevation = CardDefaults.cardElevation(),
                headerColor = headerColor
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(events) {
                    EventComponent(event = it)
                }
                if (events.isEmpty()) {
                    item {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = stringResource(R.string.no_events)
                        )
                    }
                }
                item {
                    OutlinedButton(
                        modifier = Modifier.padding(16.dp),
                        onClick = onSetReminderClicked
                    ) {
                        Text(text = setReminderText)
                    }
                }
            }
        }
    }
}

@PreviewScreenSizes
@PreviewFontScale
@PreviewLightDark
@Composable
private fun Preview() {
    DayDetailsBottomSheet(
        dayOfWeek = "شنبه",
        iranianDate = "۱۰ آذر ۱۴۰۲",
        gregorianDate = "۲۲ سپتامبر ۲۰۲۴",
        setReminderText = "کاری برای انجام در این روز تنظیم کنید",
        events = listOf(Event(10, "روز جهانی درخت کاری")),
    )
}
