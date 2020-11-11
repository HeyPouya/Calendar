package ir.apptune.calendar.pojo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Data model of dates, to pass to the Adapter to Create the gridView.
 */
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
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        return other is CalendarModel &&
                other.iranianYear == iranianYear &&
                other.iranianMonth == iranianMonth &&
                other.iranianDay == iranianDay
    }
}