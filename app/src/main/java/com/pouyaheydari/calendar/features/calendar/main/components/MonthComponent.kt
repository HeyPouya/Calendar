package com.pouyaheydari.calendar.features.calendar.main.components

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.pouyaheydari.calendar.core.pojo.CalendarModel
import com.pouyaheydari.calendar.core.utils.extensions.toPersianNumber

@Composable
fun MonthComponent(
    modifier: Modifier = Modifier,
    list: List<CalendarModel>,
    onItemClicked: (CalendarModel) -> Unit = {}
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        LazyVerticalGrid(
            modifier = modifier.fillMaxWidth(),
            columns = GridCells.Fixed(7),
            verticalArrangement = Arrangement.SpaceEvenly,
            contentPadding = PaddingValues(4.dp)
        ) {
            items(list) {
                if (it.iranianDay != -1) {
                    DayItemComponent(
                        modifier = Modifier.fillMaxSize(),
                        persianDay = it.iranianDay.toPersianNumber(),
                        gregorianDay = it.gDay.toString(),
                        onItemClicked = { onItemClicked(it) },
                        isHoliday = it.isHoliday,
                        isToday = it.today
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    val list = mutableListOf<CalendarModel>()
    repeat(30) {
        list.add(
            CalendarModel(
                iranianDay = 1,
                iranianMonth = 1,
                iranianYear = 1402,
                dayOfWeek = 2,
                gDay = 20,
                gMonth = 4,
                gYear = 2022
            ),
        )
    }
    MonthComponent(list = list)
}
