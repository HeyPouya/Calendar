package com.pouyaheydari.calendar.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.pouyaheydari.calendar.ui.theme.PersianCalendarTheme

@Composable
fun WeekDaysComponent(modifier: Modifier = Modifier, weekDays: List<String>) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        weekDays.forEach {
            Text(
                modifier = modifier.weight(1f),
                text = it,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@PreviewScreenSizes
@PreviewFontScale
@PreviewLightDark
@Composable
private fun Preview() {
    PersianCalendarTheme {
        WeekDaysComponent(
            weekDays = listOf(
                "شنبه",
                "شنبه",
                "شنبه",
                "شنبه",
                "شنبه",
                "شنبه",
                "شنبه",
            )
        )
    }
}