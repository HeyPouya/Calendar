package ir.apptune.calendar.calendar

import android.Manifest
import android.accounts.AccountManager
import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.CanceledException
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.*
import android.view.GestureDetector.SimpleOnGestureListener
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.util.ExponentialBackOff
import com.google.api.services.calendar.CalendarScopes
import ir.apptune.calendar.*
import ir.apptune.calendar.adapters.CalendarAdapter
import ir.apptune.calendar.notification.NotificationActivity
import ir.apptune.calendar.pojo.DateModel
import kotlinx.android.synthetic.main.calendar_fragment.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks

const val REQUEST_ACCOUNT_PICKER = 1000
const val REQUEST_AUTHORIZATION = 1001
const val REQUEST_GOOGLE_PLAY_SERVICES = 1002
const val REQUEST_PERMISSION_GET_ACCOUNTS = 1003
const val PREF_ACCOUNT_NAME = "accountName"

class CalendarFragment : Fragment(), PermissionCallbacks {

    /**
     * Declare all variables here :
     */
    var numberOfDays // How many days a month has
            = 0
    var cTool // An instance of CalendarTool Class that converts Garegorian Date to Persian Date
            : CalendarTool? = null
    var thisMonth = 0 // The int number of current Month that application refers to. ex : 8 For Aban

    var thisYear = 0 // The int number of current year. ex : 1395

    var DAY = 0 //Always carries The DAY that we are in.

    var MONTH = 0 //Always carries The MONTH that we are in.

    var YEAR = 0 //Always carries The YEAR that we are in.

    var STATE_OF_DAY: String? = null
    var dateModels: ArrayList<DateModel>? = null

    var mCredential: GoogleAccountCredential? = null
    var mProgress: ProgressDialog? = null
    var instanceOfCalendar: java.util.Calendar? = null
    var adapter: CalendarAdapter? = null
    private val SCOPES = arrayOf(CalendarScopes.CALENDAR)

    private lateinit var viewModel: CalendarViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.calendar_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CalendarViewModel::class.java)
        /**
        Instantiate all variables Here :
         */
        /**
         * Instantiate all variables Here :
         */
        val utils = ResourceUtils(requireContext())
        dateModels = ArrayList<DateModel>()
        adapter = CalendarAdapter(dateModels!!)
        cTool = CalendarTool()
        DAY = cTool!!.iranianDay
        MONTH = cTool!!.iranianMonth
        YEAR = cTool!!.iranianYear
        STATE_OF_DAY = cTool!!.weekDayStr
        mCredential = GoogleAccountCredential.usingOAuth2(
                requireContext(), SCOPES.toMutableList())
                .setBackOff(ExponentialBackOff())
        mProgress = ProgressDialog(requireContext())
        instanceOfCalendar = java.util.Calendar.getInstance()

        /**
         * Check if phone has rotated, so show the month that user were looking at
         */
        /**
         * Check if phone has rotated, so show the month that user were looking at
         */
        if (savedInstanceState != null) {
            thisMonth = savedInstanceState.getInt("thisMonth")
            thisYear = savedInstanceState.getInt("thisYear")
        }

        txtWeekDay.text = STATE_OF_DAY
        txtMonthDate.text = DAY.toString() + ""
        showCalendar()
        recyclerCalendar!!.adapter = adapter
        recyclerCalendar!!.layoutManager = GridLayoutManager(requireContext(), 7)
        setNotificationAlarmManager(requireContext())
        showNotification()

        imgNextMonth!!.setOnClickListener {
            calculateNextMonth()
            showCalendar()
        }

        imgPreviousMonth!!.setOnClickListener {
            calculatePreviousMonth()
            showCalendar()
        }

        mProgress!!.setMessage(getString(R.string.connecting_to_google_calendar))

        val accountName: String? = requireActivity().getPreferences(Context.MODE_PRIVATE)
                .getString(PREF_ACCOUNT_NAME, null)
        /**
         *  Check if user has logged in to his Google account or not
         */

        /**
         * Check if user has logged in to his Google account or not
         */
        if (accountName != null) {
            mCredential!!.setSelectedAccountName(accountName)
            getResultsFromApi()
        }

        /**
         * force All phone SPECIALLY HUAWEI ONES to show 3dots menu
         */
        /**
         * force All phone SPECIALLY HUAWEI ONES to show 3dots menu
         */
        try {
            val config = ViewConfiguration.get(requireContext())
            val menuKeyField = ViewConfiguration::class.java.getDeclaredField("sHasPermanentMenuKey")
            if (menuKeyField != null) {
                menuKeyField.isAccessible = true
                menuKeyField.setBoolean(config, false)
            }
        } catch (ex: java.lang.Exception) {
            // Ignore
        }

        recyclerCalendar!!.addOnItemTouchListener(RecyclerTouchListener(requireContext(), recyclerCalendar!!, object : ClickListener {
            override fun onClick(view: View?, position: Int) {
                if (dateModels!![position].day === "-") return
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity())
                val intent = Intent(requireActivity(), OnClickDialogActivity::class.java)
                intent.putExtra("IranianDay", dateModels!![position].day)
                intent.putExtra("IranianMonth", dateModels!![position].month)
                intent.putExtra("IranianYear", dateModels!![position].year)
                val accountName: String? = requireActivity().getPreferences(Context.MODE_PRIVATE)
                        .getString(PREF_ACCOUNT_NAME, null)
                if (accountName != null) {
                    intent.putExtra("accountName", accountName)
                }
                startActivity(intent, options.toBundle())
            }

            override fun onLongClick(view: View?, position: Int) {}
        }))
    }


    /**
     * This Method add spaces to Array of days. So it can Arranges days of weeks. for ex, if a month
     * starts with Mon, it adds 2 days of "-" to Sets dates Correctly.
     */
    private fun setArrangeOfWeek(v: Int) {
        when (v) {
            5 -> {
            }
            6 -> {
                var i = 0
                while (i < 1) {
                    makeDaysNull()
                    i++
                }
            }
            0 -> {
                var i = 0
                while (i < 2) {
                    makeDaysNull()
                    i++
                }
            }
            1 -> {
                var i = 0
                while (i < 3) {
                    makeDaysNull()
                    i++
                }
            }
            2 -> {
                var i = 0
                while (i < 4) {
                    makeDaysNull()
                    i++
                }
            }
            3 -> {
                var i = 0
                while (i < 5) {
                    makeDaysNull()
                    i++
                }
            }
            4 -> {
                var i = 0
                while (i < 6) {
                    makeDaysNull()
                    i++
                }
            }
        }
    }

    /**
     * Calculates the Year and returns the int Number
     */
    private fun calculateYear(i: Int): Int {
        val calclateYearNumber: Int?
        if (i == 0) {
            calclateYearNumber = cTool!!.getIranianYear()
            thisYear = cTool!!.getIranianYear()
        } else {
            calclateYearNumber = i
        }
        return calclateYearNumber
    }

    /**
     * Calculates the Month and returns the int Number
     */
    private fun calculateMonth(i: Int): Int {
        val calclateMonthNumber: Int
        if (i == 0) {
            calclateMonthNumber = cTool!!.getIranianMonth()
            thisMonth = cTool!!.getIranianMonth()
        } else {
            calclateMonthNumber = i
        }
        return calclateMonthNumber
    }

    /**
     * Makes Array Of Days to pass to the Adapter
     */
    private fun makingArrayOfDays() {
        cTool!!.setIranianDate(calculateYear(thisYear), calculateMonth(thisMonth), 1)
        numberOfDays = DaysOfMonth.getCount(thisMonth, thisYear, requireActivity())
        setArrangeOfWeek(cTool!!.getDayOfWeek())
        for (i in 1..numberOfDays) {
            cTool!!.setIranianDate(calculateYear(thisYear), calculateMonth(thisMonth), i)
            var isToday = false
            if (thisYear == YEAR) if (thisMonth == MONTH) if (i == DAY) isToday = true
            var isHoliday = false
            if (cTool!!.getDayOfWeek() == 4) isHoliday = true else if (ResourceUtils.vacationP.containsKey(cTool!!.getIranianMonth() * 100 + cTool!!.getIranianDay())) isHoliday = true
            val dateModel = DateModel(cTool!!.getIranianDay().toString() + "", cTool!!.getIranianMonth().toString() + "", cTool!!.getIranianYear().toString() + "", cTool!!.getDayOfWeek().toString(), cTool!!.getGregorianDay().toString() + "", cTool!!.getGregorianMonth().toString() + "", cTool!!.getGregorianYear().toString() + "", isHoliday, isToday)
            dateModels!!.add(dateModel)
        }
    }

    private fun calculateNextMonth() {
        if (dateModels!!.get(20).month.toInt() + 1 <= 12) {
            thisMonth = dateModels!!.get(20).month.toInt() + 1
        } else {
            thisMonth = 1
            thisYear = dateModels!!.get(20).year.toInt() + 1
        }
    }

    private fun calculatePreviousMonth() {
        if (dateModels!!.get(20).month.toInt() - 1 >= 1) {
            thisMonth = dateModels!!.get(20).month.toInt() - 1
        } else {
            thisMonth = 12
            thisYear = dateModels!!.get(20).year.toInt() - 1
        }
    }

    private fun showCalendar() {
        dateModels!!.clear()
        makingArrayOfDays()
        txtMonthName!!.setText(PersianMonthName.getName(thisMonth))
        txtYear!!.setText(thisYear.toString() + "")
        adapter!!.notifyDataSetChanged()
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

    /**
     * Saves instance of app when user rotates the phone, to prevent data restart
     *
     * @param outState
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("thisMonth", dateModels!!.get(20).month.toInt())
        outState.putInt("thisYear", dateModels!!.get(20).year.toInt())
    }

    /**
     * adds "-" to days that are not a part of this month
     */
    private fun makeDaysNull() {
        val dateModel = DateModel("-", "-", "-", "-", "-", "-", "-", false, false)
        dateModels!!.add(dateModel)
    }

    /**
     * And All the rest, are Google Calendar Stuff :
     */
    private fun getResultsFromApi() {
        if (!isGooglePlayServicesAvailable()) {
            acquireGooglePlayServices()
        } else if (!isDeviceOnline()) {
            Toast.makeText(requireActivity(), resources.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show()
        } else if (mCredential!!.getSelectedAccountName() == null) {
            chooseAccount()
        } else {
//            MakeRequestTask(mCredential).execute()
        }
    }

    private fun isGooglePlayServicesAvailable(): Boolean {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(requireContext())
        return connectionStatusCode == ConnectionResult.SUCCESS
    }

    private fun isDeviceOnline(): Boolean {
        val connMgr = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    private fun acquireGooglePlayServices() {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(requireContext())
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode)
        }
    }

    fun showGooglePlayServicesAvailabilityErrorDialog(
            connectionStatusCode: Int) {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val dialog = apiAvailability.getErrorDialog(
                requireActivity(),
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES)
        dialog.show()
    }

    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
    private fun chooseAccount() {
        if (EasyPermissions.hasPermissions(
                        requireContext(), Manifest.permission.GET_ACCOUNTS)) {
            val accountName: String? = requireActivity().getPreferences(Context.MODE_PRIVATE)
                    .getString(PREF_ACCOUNT_NAME, null)
            if (accountName != null) {
                mCredential?.setSelectedAccountName(accountName)
                getResultsFromApi()
            } else {
                // Start a dialog from which the user can choose an account
                startActivityForResult(
                        mCredential?.newChooseAccountIntent(),
                        REQUEST_ACCOUNT_PICKER)
            }
        } else {
            // Request the GET_ACCOUNTS permission via a user dialog
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.provide_permission),
                    REQUEST_PERMISSION_GET_ACCOUNTS,
                    Manifest.permission.GET_ACCOUNTS)
        }
    }

//    private class MakeRequestTask constructor(credential: GoogleAccountCredential?) : AsyncTask<Void?, Void?, List<String>?>() {
//        private var mService: Calendar? = null
//        private var mLastError: Exception? = null
//
//        /**
//         * Background task to call Google Calendar API.
//         *
//         * @param params no parameters needed for this task.
//         */
//        protected override fun doInBackground(vararg params: Void): List<String>? {
//            return try {
//                dataFromApi
//            } catch (e: Exception) {
//                mLastError = e
//                cancel(true)
//                null
//            }
//        }// All-day events don't have start times, so just use
//        // the start date.
////getDateTime();// List the icNext 10 events from the primary calendar.
//        /**
//         * Fetch a list of the icNext 10 events from the primary calendar.
//         *
//         * @return List of Strings describing returned events.
//         * @throws IOException
//         */
//        @get:Throws(IOException::class)
//        private val dataFromApi: List<String>
//            private get() {
//                // List the icNext 10 events from the primary calendar.
//                val calendar = java.util.Calendar.getInstance()
//                val calendarTool = CalendarTool()
//                calendar[calendarTool.gregorianYear, calendarTool.gregorianMonth - 1, calendarTool.gregorianDay, 0] = 0
//                val today = DateTime(calendar.timeInMillis)
//                calendar[calendarTool.gregorianYear, calendarTool.gregorianMonth - 1, calendarTool.gregorianDay, 23] = 59
//                val tonight = DateTime(calendar.timeInMillis)
//                val eventStrings: MutableList<String> = ArrayList()
//                val events = mService!!.events().list("primary")
//                        .setMaxResults(50)
//                        .setTimeMin(today)
//                        .setTimeMax(tonight)
//                        .setOrderBy("startTime")
//                        .setSingleEvents(true)
//                        .execute()
//                val items = events.items
//                for (event in items) {
//                    var start = event.start.dateTime
//                    //getDateTime();
//                    if (start == null) {
//                        // All-day events don't have start times, so just use
//                        // the start date.
//                        start = event.start.date
//                    }
//                    eventStrings.add(String.format("%s (%s)", event.summary, start))
//                }
//                return eventStrings
//            }
//
//        override fun onPreExecute() {
//            mProgress.show()
//        }
//
//        override fun onPostExecute(output: List<String>?) {
//            mProgress.hide()
//            if (output == null || output.size == 0) {
//                txtShowWork.setText(R.string.you_have_no_events_today)
//            } else {
//                txtShowWork.setText("شما امروز" + output.size + " " + "رویداد دارید")
//            }
//        }
//
//        override fun onCancelled() {
//            mProgress.hide()
//            if (mLastError != null) {
//                if (mLastError is GooglePlayServicesAvailabilityIOException) {
//                    showGooglePlayServicesAvailabilityErrorDialog(
//                            (mLastError as GooglePlayServicesAvailabilityIOException)
//                                    .connectionStatusCode)
//                } else if (mLastError is UserRecoverableAuthIOException) {
//                    startActivityForResult(
//                            (mLastError as UserRecoverableAuthIOException).intent,
//                            OnClickDialogActivity.REQUEST_AUTHORIZATION)
//                } else {
//                    Log.d("LOG", """
//     The following error occurred:
//     ${mLastError!!.message}
//     """.trimIndent())
//                }
//            } else {
//                Toast.makeText(requireContext(), R.string.your_request_cancelled, Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        init {
//            val transport = AndroidHttp.newCompatibleTransport()
//            val jsonFactory: JsonFactory = JacksonFactory.getDefaultInstance()
//            mService = Calendar.Builder(
//                    transport, jsonFactory, credential)
//                    .setApplicationName("تقویم پارسی")
//                    .build()
//        }
//    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String?>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(
                requestCode, permissions, grantResults, this)
    }


    override fun onPermissionsGranted(requestCode: Int, list: List<String?>?) {
        // Do nothing.
    }

    override fun onPermissionsDenied(requestCode: Int, list: List<String?>?) {
        // Do nothing.
    }

    override fun onActivityResult(
            requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_GOOGLE_PLAY_SERVICES -> if (resultCode != Activity.RESULT_OK) {
                Toast.makeText(requireContext(), resources.getString(R.string.install_google_play_service), Toast.LENGTH_LONG).show()
            } else {
                getResultsFromApi()
            }
            REQUEST_ACCOUNT_PICKER -> if (resultCode == Activity.RESULT_OK && data != null && data.extras != null) {
                val accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)
                if (accountName != null) {
                    val settings: SharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
                    val editor = settings.edit()
                    editor.putString(PREF_ACCOUNT_NAME, accountName)
                    editor.apply()
                    mCredential?.setSelectedAccountName(accountName)
                    getResultsFromApi()
                }
            }
            REQUEST_AUTHORIZATION -> if (resultCode == Activity.RESULT_OK) {
                getResultsFromApi()
            }
        }
    }


    internal class RecyclerTouchListener(context: Context?, recyclerView: RecyclerView, var clickListener: ClickListener?) : OnItemTouchListener {
        var detector: GestureDetector
        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            val child = rv.findChildViewUnder(e.x, e.y)
            if (child != null && clickListener != null && detector.onTouchEvent(e)) {
                clickListener!!.onClick(child, rv.getChildLayoutPosition(child))
            }
            return false
        }

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

        init {
            detector = GestureDetector(context, object : SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent): Boolean {
                    return true
                }

                override fun onLongPress(e: MotionEvent) {
                    val child = recyclerView.findChildViewUnder(e.x, e.y)
                    if (child != null && clickListener != null) {
                        clickListener!!.onLongClick(child, recyclerView.getChildLayoutPosition(child))
                    }
                }
            })
        }
    }

    interface ClickListener {
        fun onClick(view: View?, position: Int)
        fun onLongClick(view: View?, position: Int)
    }

    override fun onDestroy() {
        if (mProgress != null) mProgress!!.dismiss()
        super.onDestroy()
    }
}