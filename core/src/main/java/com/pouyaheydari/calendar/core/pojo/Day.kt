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
    val shamsiMonth: ShamsiMonths,
    val shamsiYear: Int,
    val dayOfWeek: Int,
    var gregorianDay: Int,
    val gregorianMonth: GregorianMonths,
    val gregorianYear: Int,
    var isShamsiHoliday: Boolean = false,
    var today: Boolean = false
) : Parcelable
