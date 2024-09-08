package com.pouyaheydari.calendar.core.pojo

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
 * Data model of dates, to pass to the Adapter to Create the gridView.
 */
@Keep
@Parcelize
data class Day(
    val shamsiDay: Int,
    val shamsiMonth: Int,
    val shamsiYear: Int,
    val dayOfWeek: Int,
    var gregorianDay: Int,
    val gregorianMonth: Int,
    val gregorianYear: Int,
    var isShamsiHoliday: Boolean = false,
    var today: Boolean = false
) : Parcelable
