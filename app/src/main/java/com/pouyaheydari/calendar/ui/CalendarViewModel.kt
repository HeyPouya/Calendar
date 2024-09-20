package com.pouyaheydari.calendar.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pouyaheydari.calendar.core.pojo.Day
import com.pouyaheydari.calendar.domain.CalculateNextMonthUseCase
import com.pouyaheydari.calendar.domain.CalculatePreviousMonthUseCase
import com.pouyaheydari.calendar.domain.GenerateDaysOfMonthUseCase
import com.pouyaheydari.calendar.core.pojo.ShamsiMonths
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

    private var currentDisplayedDate: Pair<Int, ShamsiMonths> = today.shamsiYear to today.shamsiMonth

    private val _screenState = MutableStateFlow<List<Day>>(emptyList())
    val screenState: StateFlow<List<Day>> = _screenState
        .onStart { updateDisplayedDate() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onIntent(intent: CalendarUserIntents) = when (intent) {
        CalendarUserIntents.OnDayClicked -> {}
        CalendarUserIntents.OnNextMonthClicked -> getNextMonth()
        CalendarUserIntents.OnPreviousMonthClicked -> getPreviousMonth()
    }

    private fun getNextMonth() {
        currentDisplayedDate =
            calculateNextMonthUseCase(currentDisplayedDate.first, currentDisplayedDate.second)
        updateDisplayedDate()
    }

    private fun getPreviousMonth() {
        currentDisplayedDate =
            calculatePreviousMonthUseCase(currentDisplayedDate.first, currentDisplayedDate.second)
        updateDisplayedDate()
    }

    private fun updateDisplayedDate() {
        _screenState.update {
            generateDaysOfMonthUseCase(currentDisplayedDate.first, currentDisplayedDate.second)
        }
    }
}
