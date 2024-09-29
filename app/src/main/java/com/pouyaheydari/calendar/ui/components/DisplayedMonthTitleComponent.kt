package com.pouyaheydari.calendar.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.pouyaheydari.calendar.R

@Composable
fun MonthYearTitleComponent(
    shamsiDate: String = "",
    gregorianDate: String = "",
    onNextMonthClicked: () -> Unit = {},
    onPreviousMonthClicked: () -> Unit = {}
) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Image(
            modifier = Modifier
                .size(48.dp)
                .rotate(180f)
                .clickable { onNextMonthClicked() },
            painter = painterResource(id = R.drawable.ic_next), contentDescription = null
        )
        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = shamsiDate,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = gregorianDate,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
        }
        Image(
            modifier = Modifier
                .size(48.dp)
                .clickable { onPreviousMonthClicked() },
            painter = painterResource(id = R.drawable.ic_next), contentDescription = null
        )
    }
}

@PreviewScreenSizes
@PreviewFontScale
@PreviewLightDark
@Composable
private fun Preview() {
    MonthYearTitleComponent(shamsiDate = "شهریور ۱۴۰۲", gregorianDate = "ژانویه - فوریه ۲۰۲۴")
}