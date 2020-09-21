package ir.apptune.calendar.calendar

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.CanceledException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ir.apptune.calendar.R
import ir.apptune.calendar.adapters.CalendarAdapter
import ir.apptune.calendar.base.extensions.toPersianMonth
import ir.apptune.calendar.base.extensions.toPersianNumber
import ir.apptune.calendar.base.extensions.toPersianWeekDay
import ir.apptune.calendar.notification.NotificationActivity
import ir.apptune.calendar.pojo.CalendarModel
import kotlinx.android.synthetic.main.calendar_fragment.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class CalendarFragment : Fragment() {

    private val viewModel: CalendarViewModel by viewModel()
    private val today: CalendarModel by inject()
    private val adapter: CalendarAdapter = CalendarAdapter {}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.calendar_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        txtWeekDay.text = today.dayOfWeek.toPersianWeekDay(requireContext())
        txtMonthDate.text = today.iranianDay.toPersianNumber()

        viewModel.getMonthLiveData().observe(viewLifecycleOwner, {
            showCalendar(it.toMutableList())
        })
        recyclerCalendar.adapter = adapter
        setNotificationAlarmManager(requireContext())
        showNotification()

        imgNextMonth.setOnClickListener { viewModel.getNextMonth() }
        imgPreviousMonth.setOnClickListener { viewModel.getPreviousMonth() }
    }

    private fun showCalendar(list: List<CalendarModel>) {
        txtMonthName.text = list.last().iranianMonth.toPersianMonth(requireContext())
        txtYear.text = list.last().iranianYear.toPersianNumber()
        adapter.submitList(list)
    }

    /**
     * Sets an alarmManager to change the notification content at 24:00 every night
     *
     * @param context
     */
    private fun setNotificationAlarmManager(context: Context) {
        val intent = Intent(requireContext(), NotificationActivity::class.java)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val calendar = java.util.Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar[java.util.Calendar.SECOND] = 0
        calendar[java.util.Calendar.MINUTE] = 0
        calendar[java.util.Calendar.HOUR_OF_DAY] = 24
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent) //Repeat every 24 hours
    }

    /**
     * shows the Notification immediately after user opens the app
     */
    private fun showNotification() {
        val intent = Intent(requireContext(), NotificationActivity::class.java)
        val pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, 0)
        try {
            pendingIntent.send()
        } catch (e: CanceledException) {
            e.printStackTrace()
        }
    }

}