package com.pouyaheydari.calendar.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.pouyaheydari.calendar.R
import com.pouyaheydari.calendar.core.pojo.Day
import com.pouyaheydari.calendar.core.utils.SELECTED_DAY_DETAILS
import com.pouyaheydari.calendar.ui.theme.PersianCalendarTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CalendarFragment : Fragment() {

    @Inject
    lateinit var today: Day
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                PersianCalendarTheme {
                    CalendarScreen(today = today) {
                        val data = Bundle().apply {
                            putParcelable(SELECTED_DAY_DETAILS, it)
                        }
                        if (findNavController().currentDestination?.id != R.id.onClickDialogActivity) {
                            findNavController().navigate(
                                R.id.action_calendarFragment_to_onClickDialogActivity,
                                data
                            )
                        }
                    }
                }
            }
        }
    }
}
