package ir.apptune.calendar.pojo

/**
 * Data model of dates, to pass to the Adapter to Create the gridView.
 */
data class CalendarModel(
        val iranianDay: Int,
        val iranianMonth: Int,
        val iranianYear: Int,
        val dayOfWeek: Int,
        var gDay: Int,
        val gMonth: Int,
        val gYear: Int,
        var isHoliday: Boolean = false,
        val today: Boolean = false
)