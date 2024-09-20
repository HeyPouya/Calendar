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
    val weekDay: WeekDay,
    val gregorianDay: Int,
    val gregorianMonth: GregorianMonths,
    val gregorianYear: Int,
    val isShamsiHoliday: Boolean = false,
    val today: Boolean = false
) : Parcelable
