package ir.apptune.calendar.features.calendar.details

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import ir.apptune.calendar.R
import ir.apptune.calendar.pojo.CalendarModel
import ir.apptune.calendar.pojo.DateModel
import ir.apptune.calendar.utils.CALENDAR_INTENT_TYPE
import ir.apptune.calendar.utils.SELECTED_DAY_DETAILS
import ir.apptune.calendar.utils.extensions.toEnglishMonth
import ir.apptune.calendar.utils.extensions.toPersianMonth
import ir.apptune.calendar.utils.extensions.toPersianNumber
import ir.apptune.calendar.utils.extensions.toPersianWeekDay
import kotlinx.android.synthetic.main.fragment_dialog_date_details.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.*

/**
 * The activity that Pops-Up when user clicks on days, in MainPage calendar.
 */
class DateDetailsDialogFragment : DialogFragment() {

    private val viewModel: DateDetailsViewModel by viewModel { parametersOf(date) }
    private lateinit var date: CalendarModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_dialog_date_details, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        date = requireArguments().getParcelable(SELECTED_DAY_DETAILS)
                ?: throw IllegalArgumentException("You must provide the selected date")

        viewModel.getEventsLiveData().observe(viewLifecycleOwner, {
            txtDayEvents.text =
                    if (it.isNotEmpty()) it else getString(R.string.no_events)
        })
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

        if (date.isHoliday)
            setHolidayColors()

        btnAddEvent.setOnClickListener {
            Intent(Intent.ACTION_EDIT).apply {
                type = CALENDAR_INTENT_TYPE
                putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                        GregorianCalendar(date.gYear, date.gMonth - 1, date.gDay).timeInMillis)
                try {
                    startActivity(this)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                    Snackbar.make(requireView(), getString(R.string.google_calendar_is_not_installed), Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setHolidayColors() {
        txtPersianDate.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorAccent))
        txtGregorianDate.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorAccent))
    }
}