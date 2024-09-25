package com.pouyaheydari.calendar.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.pouyaheydari.calendar.ui.theme.HeaderShape
import com.pouyaheydari.calendar.ui.theme.PersianCalendarTheme

@Composable
fun HeaderComponent(
    modifier: Modifier = Modifier,
    dayOfWeek: String,
    iranianDate: String,
    gregorianDate: String,
    elevation: CardElevation
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = HeaderShape,
        colors = CardDefaults.cardColors().copy(containerColor = MaterialTheme.colorScheme.primary),
        elevation = elevation
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(dayOfWeek, color = Color.White, style = MaterialTheme.typography.headlineSmall)
            Text(iranianDate, color = Color.White, style = MaterialTheme.typography.headlineSmall)
            Text(gregorianDate, color = Color.White, style = MaterialTheme.typography.headlineSmall)
        }
    }
}

@PreviewScreenSizes
@PreviewFontScale
@PreviewLightDark
@Composable
private fun Preview() {
    PersianCalendarTheme {
        HeaderComponent(
            dayOfWeek = "یکشنبه",
            iranianDate = "۲۸ مرداد",
            gregorianDate = "18 آگوست",
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
        )
    }
}
