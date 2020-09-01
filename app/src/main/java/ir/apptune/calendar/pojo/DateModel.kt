package ir.apptune.calendar.pojo

/**
 * Data model of dates, to pass to the Adapter to Create the gridView.
 */
data class DateModel(
        val day: String,
        val month: String,
        val year: String,
        val dayofWeek: String,
        var gDay: String,
        val gMonth: String,
        val gYear: String,
        val isHoliday: Boolean,
        val today: Boolean
)