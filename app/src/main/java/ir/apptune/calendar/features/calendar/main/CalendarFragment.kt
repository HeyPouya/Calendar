package ir.apptune.calendar.features.calendar.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.apptune.calendar.R
import ir.apptune.calendar.databinding.CalendarFragmentBinding
import ir.apptune.calendar.core.pojo.CalendarModel
import ir.apptune.calendar.core.utils.extensions.toPersianMonth
import ir.apptune.calendar.core.utils.extensions.toPersianNumber
import ir.apptune.calendar.core.utils.extensions.toPersianWeekDay
import javax.inject.Inject

/**
 * Main view of the application that contains the calendar
 */
@AndroidEntryPoint
class CalendarFragment : Fragment() {

    private val viewModel: CalendarViewModel by viewModels()
    private lateinit var binding: CalendarFragmentBinding

    @Inject
    lateinit var today: CalendarModel
    private val adapter: CalendarAdapter = CalendarAdapter {
        val data = Bundle().apply {
            putParcelable(ir.apptune.calendar.core.utils.SELECTED_DAY_DETAILS, it)
        }
        findNavController().navigate(R.id.action_calendarFragment_to_onClickDialogActivity, data)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = CalendarFragmentBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbarTexts()

        viewModel.getMonthLiveData().observe(viewLifecycleOwner, {
            showCalendar(it.toMutableList())
        })
        binding.recyclerCalendar.adapter = adapter
        binding.recyclerCalendar.itemAnimator = null
        binding.imgNextMonth.setOnClickListener { viewModel.getNextMonth() }
        binding.imgPreviousMonth.setOnClickListener { viewModel.getPreviousMonth() }
    }

    private fun setUpToolbarTexts() = with(today) {
        binding.topBar.txtWeekDay.text = dayOfWeek.toPersianWeekDay(requireContext())
        binding.topBar.txtMonthDate.text = iranianDay.toPersianNumber()
        binding.topBar.txtCurrentMonth.text = iranianMonth.toPersianMonth(requireContext())
    }

    private fun showCalendar(list: List<CalendarModel>) {
        binding.txtMonthName.text = list.last().iranianMonth.toPersianMonth(requireContext())
        binding.txtYear.text = list.last().iranianYear.toPersianNumber()
        adapter.submitList(list)
    }


}