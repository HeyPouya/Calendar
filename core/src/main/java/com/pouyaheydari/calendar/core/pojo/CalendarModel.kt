package com.pouyaheydari.calendar.core.pojo

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
 * Data model of dates, to pass to the Adapter to Create the gridView.
 */
@Keep
@Parcelize
data class CalendarModel(
    val iranianDay: Int,
    val iranianMonth: Int,
    val iranianYear: Int,
    val dayOfWeek: Int,
    var gDay: Int,
    val gMonth: Int,
    val gYear: Int,
    var isHoliday: Boolean = false,
    var today: Boolean = false
) : Parcelable
