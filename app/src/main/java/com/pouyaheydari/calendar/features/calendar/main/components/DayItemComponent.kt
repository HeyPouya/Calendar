package com.pouyaheydari.calendar.features.calendar.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pouyaheydari.calendar.features.calendar.main.theme.DarkBlue80
import com.pouyaheydari.calendar.features.calendar.main.theme.TodayShape

@Composable
fun DayItemComponent(
    modifier: Modifier = Modifier,
    persianDay: String,
    gregorianDay: String,
    onItemClicked: () -> Unit = {},
    isHoliday: Boolean = false,
    isToday: Boolean = false
) {
    val textColor = if (isHoliday) Color.Red else MaterialTheme.colorScheme.onSurface
    val background = if (isToday) DarkBlue80 else Color.Unspecified

    Box(modifier = modifier
        .fillMaxSize()
        .clickable { onItemClicked() }
        .clip(TodayShape)
        .background(background)
        .padding(4.dp)) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = persianDay,
            style = MaterialTheme.typography.titleLarge,
            color = textColor
        )
        Text(
            modifier = Modifier.align(Alignment.BottomEnd),
            text = gregorianDay,
            style = MaterialTheme.typography.labelSmall,
            color = textColor
        )
    }
}

@Preview
@Composable
private fun Preview() {
    DayItemComponent(persianDay = "۳۰", gregorianDay = "9", isToday = true)
}