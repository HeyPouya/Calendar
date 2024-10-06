package com.pouyaheydari.calendar.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
    elevation: CardElevation,
    headerColor: Color = MaterialTheme.colorScheme.primary
) {
    Card(
        shape = HeaderShape,
        colors = CardDefaults.cardColors().copy(containerColor = headerColor),
        elevation = elevation
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                modifier = Modifier.padding(4.dp),
                text = dayOfWeek,
                color = Color.White,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                modifier = Modifier.padding(4.dp),
                text = iranianDate,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                modifier = Modifier.padding(4.dp),
                text = gregorianDate,
                color = Color.White,
                style = MaterialTheme.typography.titleSmall
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
        HeaderComponent(
            dayOfWeek = "یکشنبه",
            iranianDate = "۲۸ مرداد",
            gregorianDate = "18 آگوست",
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
        )
    }
}
