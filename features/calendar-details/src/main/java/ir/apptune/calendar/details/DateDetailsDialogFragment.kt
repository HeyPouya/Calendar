package ir.apptune.calendar.details

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ir.apptune.calendar.core.pojo.CalendarModel
import ir.apptune.calendar.core.utils.SELECTED_DAY_DETAILS
import ir.apptune.calendar.core.utils.extensions.toEnglishMonth
import ir.apptune.calendar.core.utils.extensions.toPersianMonth
import ir.apptune.calendar.core.utils.extensions.toPersianNumber
import ir.apptune.calendar.core.utils.extensions.toPersianWeekDay
import ir.apptune.calendar.details.databinding.FragmentDialogDateDetailsBinding
import ir.apptune.calendar.details.utils.parcelable
import java.util.GregorianCalendar


private const val CALENDAR_INTENT_TYPE = "vnd.android.cursor.item/event"

/**
 * Show details about the selected date in a popup form
 */
@AndroidEntryPoint
class DateDetailsDialogFragment : DialogFragment() {

    private val viewModel by lazy { ViewModelProvider(requireActivity())[DateDetailsViewModel::class.java] }
    private lateinit var binding: FragmentDialogDateDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogDateDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val date =
            requireArguments().parcelable<CalendarModel>(SELECTED_DAY_DETAILS)
                ?: throw IllegalArgumentException("You must provide the selected date")

        viewModel.getEvents(date)
        viewModel.getEventsLiveData().observe(viewLifecycleOwner) {
            binding.txtDayEvents.text =
                it.ifEmpty { getString(R.string.no_events) }
        }
        with(date) {
            binding.txtPersianDate.text = getString(
                R.string.persian_full_date,
                dayOfWeek.toPersianWeekDay(requireContext()), date.iranianDay.toPersianNumber(),
                iranianMonth.toPersianMonth(requireContext()),
                date.iranianYear.toPersianNumber()
            )
            binding.txtGregorianDate.text = getString(
                R.string.gregorian_full_date,
                gDay.toPersianNumber(),
                gMonth.toEnglishMonth(requireContext()),
                gYear.toPersianNumber()
            )
        }

        if (date.isHoliday)
            setHolidayColors()

        binding.btnAddEvent.setOnClickListener {
            Intent(Intent.ACTION_EDIT).apply {
                type = CALENDAR_INTENT_TYPE
                putExtra(
                    CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                    GregorianCalendar(date.gYear, date.gMonth - 1, date.gDay).timeInMillis
                )
                try {
                    startActivity(this)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                    Snackbar.make(
                        requireView(),
                        getString(R.string.google_calendar_is_not_installed),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }

    }

    private fun setHolidayColors() {
        binding.txtPersianDate.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.colorAccent
            )
        )
        binding.txtGregorianDate.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.colorAccent
            )
        )
    }
}
