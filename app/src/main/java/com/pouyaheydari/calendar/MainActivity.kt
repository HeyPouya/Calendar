package com.pouyaheydari.calendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.pouyaheydari.calendar.ui.CalendarScreen
import com.pouyaheydari.calendar.ui.theme.PersianCalendarTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * The single activity of the application
 */
@AndroidEntryPoint
open class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PersianCalendarTheme {
                CalendarScreen()
            }
        }
    }
}