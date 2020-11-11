package ir.apptune.calendar.features.calendar.details

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import ir.apptune.calendar.R
import ir.apptune.calendar.ResourceUtils
import ir.apptune.calendar.pojo.CalendarModel
import ir.apptune.calendar.utils.CALENDAR_INTENT_TYPE
import ir.apptune.calendar.utils.SELECTED_DAY_DETAILS
import ir.apptune.calendar.utils.extensions.toEnglishMonth
import ir.apptune.calendar.utils.extensions.toPersianMonth
import ir.apptune.calendar.utils.extensions.toPersianNumber
import ir.apptune.calendar.utils.extensions.toPersianWeekDay
import kotlinx.android.synthetic.main.activity_on_click_dialog.*
import java.util.*

/**
 * The activity that Pops-Up when user clicks on days, in MainPage calendar.
 */
class DateDetailsDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_dialog_date_details, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val date = requireArguments().getParcelable<CalendarModel>(SELECTED_DAY_DETAILS)
                ?: throw IllegalArgumentException("You must provide the selected date")

        with(date) {
            txtPersianDate.text = getString(R.string.persian_full_date,
                    dayOfWeek.toPersianWeekDay(requireContext()), date.iranianDay.toPersianNumber(),
                    iranianMonth.toPersianMonth(requireContext()),
                    date.iranianYear.toPersianNumber())
            txtGregorianDate.text = getString(R.string.gregorian_full_date,
                    gDay.toPersianNumber(),
                    gMonth.toEnglishMonth(requireContext()),
                    gYear.toPersianNumber())
        }

        var persianTemp = date.iranianMonth * 100
        persianTemp += date.iranianDay
        val gregorianTemp = date.gMonth * 100 + date.gDay
        var s: String? = ""
        var g: String? = ""
        if (ResourceUtils.eventP.containsKey(persianTemp)) s = ResourceUtils.eventP[persianTemp]
        if (ResourceUtils.eventG.containsKey(gregorianTemp)) g = ResourceUtils.eventG[gregorianTemp]
        txtDayEvents.text = buildString {
            if (!s.isNullOrEmpty()) append(s)
            if (!g.isNullOrEmpty()) append("\n$g")
            if (s.isNullOrEmpty() && g.isNullOrEmpty()) append(getString(R.string.no_events))
        }

        btnAddEvent.setOnClickListener {
            Intent(Intent.ACTION_EDIT).apply {
                type = CALENDAR_INTENT_TYPE
                putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                        GregorianCalendar(date.gYear, date.gMonth - 1, date.gDay).timeInMillis)
                startActivity(this)
            }
        }
    }
}