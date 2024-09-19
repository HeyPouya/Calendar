package com.pouyaheydari.calendar.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pouyaheydari.calendar.core.pojo.Day
import com.pouyaheydari.calendar.domain.CalculateNextMonthUseCase
import com.pouyaheydari.calendar.domain.CalculatePreviousMonthUseCase
import com.pouyaheydari.calendar.domain.GenerateDaysOfMonthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val calculateNextMonthUseCase: CalculateNextMonthUseCase,
    private val calculatePreviousMonthUseCase: CalculatePreviousMonthUseCase,
    private val generateDaysOfMonthUseCase: GenerateDaysOfMonthUseCase,
    today: Day
) : ViewModel() {

    private var currentDisplayedDate: Pair<Int, Int> = today.shamsiYear to today.shamsiMonth

    private val _screenState = MutableStateFlow<List<Day>>(emptyList())
    val screenState: StateFlow<List<Day>> = _screenState
        .onStart {
            _screenState.update {
                generateDaysOfMonthUseCase(currentDisplayedDate.first, currentDisplayedDate.second)
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /**
     * Fetches and publishes the next month (from the month that currently user is watching) as a list of days
     */
    fun getNextMonth() {
        currentDisplayedDate =
            calculateNextMonthUseCase(currentDisplayedDate.first, currentDisplayedDate.second)
        _screenState.update {
            generateDaysOfMonthUseCase(currentDisplayedDate.first, currentDisplayedDate.second)
        }
    }

    /**
     * Fetches and publishes the previous month (from the month that currently user is watching) as a list of days
     */
    fun getPreviousMonth() {
        currentDisplayedDate =
            calculatePreviousMonthUseCase(currentDisplayedDate.first, currentDisplayedDate.second)
        _screenState.update {
            generateDaysOfMonthUseCase(currentDisplayedDate.first, currentDisplayedDate.second)
        }
    }
}
