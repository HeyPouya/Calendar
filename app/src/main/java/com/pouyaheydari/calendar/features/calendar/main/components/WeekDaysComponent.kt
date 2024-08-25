package com.pouyaheydari.calendar.features.calendar.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.pouyaheydari.calendar.features.calendar.main.theme.PersianCalendarTheme

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

@Preview
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