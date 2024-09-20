package com.pouyaheydari.calendar.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.pouyaheydari.calendar.core.pojo.Day
import com.pouyaheydari.calendar.core.pojo.GregorianMonths
import com.pouyaheydari.calendar.core.pojo.ShamsiMonths
import com.pouyaheydari.calendar.core.pojo.WeekDay
import com.pouyaheydari.calendar.core.utils.toPersianNumber

@Composable
fun MonthComponent(
    modifier: Modifier = Modifier,
    list: List<Day>,
    onItemClicked: (Day) -> Unit = {}
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        LazyVerticalGrid(
            modifier = modifier.fillMaxWidth(),
            columns = GridCells.Fixed(7),
            verticalArrangement = Arrangement.SpaceEvenly,
            contentPadding = PaddingValues(4.dp)
        ) {
            items(list) {
                if (it.shamsiDay != -1) {
                    DayItemComponent(
                        modifier = Modifier.fillMaxSize(),
                        persianDay = it.shamsiDay.toPersianNumber(),
                        gregorianDay = it.gregorianDay.toString(),
                        onItemClicked = { onItemClicked(it) },
                        isHoliday = it.isShamsiHoliday,
                        isToday = it.today
                    )
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
    val list = mutableListOf<Day>()
    repeat(30) {
        list.add(
            Day(
                shamsiDay = 1,
                shamsiMonth = ShamsiMonths.Farwarding,
                shamsiYear = 1402,
                weekDay = WeekDay.Monday,
                gregorianDay = 20,
                gregorianMonth = GregorianMonths.April,
                gregorianYear = 2022
            ),
        )
    }
    MonthComponent(list = list)
}
