package ir.apptune.calendar;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.apptune.calendar.notification.NotificationActivity;
import ir.apptune.calendar.adapters.CalendarMainAdapter;
import ir.apptune.calendar.models.DateModel;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    /**
     * Declare all variables here :
     */
    int numberOfDays; // How many days a month has
    CalendarTool cTool; // An instance of CalendarTool Class that converts Garegorian Date to Persian Date
    int thisMonth = 0; // The int number of current Month that application refers to. ex : 8 For Aban
    int thisYear = 0; // The int number of current year. ex : 1395
    static int DAY = 0; //Always carries The DAY that we are in.
    static int MONTH = 0; //Always carries The MONTH that we are in.
    static int YEAR = 0; //Always carries The YEAR that we are in.
    static String STATE_OF_DAY;
    List<DateModel> dateModels;
    @BindView(R.id.txtMonthName)
    TextView txtMonthName;
    @BindView(R.id.txtWeekDay)
    TextView txtShowToday;
    @BindView(R.id.txtMonthDate)
    TextView txtshowDate;
    @BindView(R.id.imgPreviousMonth)
    ImageView btn_previous;
    @BindView(R.id.imgNextMonth)
    ImageView btn_next;
    @BindView(R.id.txtYear)
    TextView txtYear;
    @BindView(R.id.recyclerCalendar)
    RecyclerView gridView;
    @BindView(R.id.txt_show_work)
    TextView txtShowWork;
    GoogleAccountCredential mCredential;
    ProgressDialog mProgress;
    Calendar instanceOfCalendar;
    CalendarMainAdapter adapter;
    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;
    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = {CalendarScopes.CALENDAR};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        /**
         Instantiate all variables Here :
         */
        dateModels = new ArrayList<>();
        adapter = new CalendarMainAdapter(this, dateModels);
        cTool = new CalendarTool();
        DAY = cTool.getIranianDay();
        MONTH = cTool.getIranianMonth();
        YEAR = cTool.getIranianYear();
        STATE_OF_DAY = cTool.getWeekDayStr();
        mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());
        mProgress = new ProgressDialog(this);
        instanceOfCalendar = Calendar.getInstance();

        /**
         * Check if phone has rotated, so show the month that user were looking at
         */
        if (savedInstanceState != null) {
            thisMonth = savedInstanceState.getInt("thisMonth");
            thisYear = savedInstanceState.getInt("thisYear");
        }

        txtShowToday.setText(STATE_OF_DAY);
        txtshowDate.setText(DAY + "");
        showCalendar();
        gridView.setAdapter(adapter);
        gridView.setLayoutManager(new GridLayoutManager(this, 7));
        setNotificationAlarmManager(this);
        showNotification();

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateNextMonth();
                showCalendar();
            }
        });

        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculatePreviousMonth();
                showCalendar();
            }
        });

        mProgress.setMessage(getString(R.string.connecting_to_google_calendar));

        String accountName = getPreferences(Context.MODE_PRIVATE)
                .getString(PREF_ACCOUNT_NAME, null);
        /**
         *  Check if user has logged in to his Google account or not
         */

        if (accountName != null) {
            mCredential.setSelectedAccountName(accountName);
            getResultsFromApi();
        }

        /**
         * force All phone SPECIALLY HUAWEI ONES to show 3dots menu
         */
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception ex) {
            // Ignore
        }

        gridView.addOnItemTouchListener(new RecyclerTouchListener(this, gridView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (dateModels.get(position).getDay() == "-")
                    return;

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this);
                Intent intent = new Intent(MainActivity.this, OnClickDialogActivity.class);
                intent.putExtra("IranianDay", dateModels.get(position).getDay());
                intent.putExtra("IranianMonth", dateModels.get(position).getMonth());
                intent.putExtra("IranianYear", dateModels.get(position).getYear());
                String accountName = getPreferences(Context.MODE_PRIVATE)
                        .getString(PREF_ACCOUNT_NAME, null);

                if (accountName != null) {
                    intent.putExtra("accountName", accountName);
                }
                startActivity(intent, options.toBundle());

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        //End of onCreate


    }

    /**
     * This Method add spaces to Array of days. So it can Arranges days of weeks. for ex, if a month
     * starts with Mon, it adds 2 days of "-" to Sets dates Correctly.
     */
    private void setArrangeOfWeek(int v) {
        switch (v) {
            case 5:
                break;
            case 6:
                for (int i = 0; i < 1; i++)
                    makeDaysNull();

                break;
            case 0:
                for (int i = 0; i < 2; i++)
                    makeDaysNull();

                break;
            case 1:
                for (int i = 0; i < 3; i++)
                    makeDaysNull();

                break;
            case 2:
                for (int i = 0; i < 4; i++)
                    makeDaysNull();

                break;
            case 3:
                for (int i = 0; i < 5; i++)
                    makeDaysNull();

                break;
            case 4:
                for (int i = 0; i < 6; i++)
                    makeDaysNull();

                break;
        }
    }

    /**
     * Calculates the Year and returns the int Number
     */
    private int calculateYear(int i) {
        int calclateYearNumber;
        if (i == 0) {
            calclateYearNumber = cTool.getIranianYear();
            thisYear = cTool.getIranianYear();
        } else {
            calclateYearNumber = i;
        }
        return calclateYearNumber;
    }

    /**
     * Calculates the Month and returns the int Number
     */
    private int calculateMonth(int i) {
        int calclateMonthNumber;
        if (i == 0) {
            calclateMonthNumber = cTool.getIranianMonth();
            thisMonth = cTool.getIranianMonth();
        } else {
            calclateMonthNumber = i;
        }
        return calclateMonthNumber;
    }

    /**
     * Makes Array Of Days to pass to the Adapter
     */

    private void makingArrayOfDays() {
        cTool.setIranianDate(calculateYear(thisYear), calculateMonth(thisMonth), 1);
        numberOfDays = DaysOfMonth.getCount(thisMonth, thisYear, MainActivity.this);
        setArrangeOfWeek(cTool.getDayOfWeek());
        for (int i = 1; i <= numberOfDays; i++) {
            DateModel dateModel = new DateModel();
            cTool.setIranianDate(calculateYear(thisYear), calculateMonth(thisMonth), i);
            dateModel.setDay(cTool.getIranianDay() + "");
            dateModel.setgDay(cTool.getGregorianDay() + "");
            dateModel.setgMonth(cTool.getGregorianMonth() + "");
            dateModel.setgYear(cTool.getGregorianYear() + "");
            dateModel.setMonth(cTool.getIranianMonth() + "");
            dateModel.setYear(cTool.getIranianYear() + "");
            dateModel.setDayofWeek(String.valueOf(cTool.getDayOfWeek()));
            dateModel.setToday(false);
            if (thisYear == YEAR)
                if (thisMonth == MONTH)
                    if (i == DAY)
                        dateModel.setToday(true);
            dateModels.add(dateModel);
        }

    }

    private void calculateNextMonth() {
        if (Integer.parseInt(dateModels.get(20).getMonth()) + 1 <= 12) {
            thisMonth = Integer.parseInt(dateModels.get(20).getMonth()) + 1;
        } else {
            thisMonth = 1;
            thisYear = Integer.parseInt(dateModels.get(20).getYear()) + 1;
        }

    }

    private void calculatePreviousMonth() {
        if (Integer.parseInt(dateModels.get(20).getMonth()) - 1 >= 1) {
            thisMonth = Integer.parseInt(dateModels.get(20).getMonth()) - 1;
        } else {
            thisMonth = 12;
            thisYear = Integer.parseInt(dateModels.get(20).getYear()) - 1;
        }

    }

    private void showCalendar() {
        dateModels.clear();
        makingArrayOfDays();
        txtMonthName.setText(PersianMonthName.getName(thisMonth));
        txtYear.setText(thisYear + "");
        adapter.notifyDataSetChanged();

    }

    /**
     * Sets an alarmManager to change the notification content at 24:00 every night
     *
     * @param context
     */

    private void setNotificationAlarmManager(Context context) {
        Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent); //Repeat every 24 hours
    }

    /**
     * shows the Notification immediately after user opens the app
     */

    private void showNotification() {
        Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
        try {
            pendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves instance of app when user rotates the phone, to prevent data restart
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("thisMonth", Integer.parseInt(dateModels.get(20).getMonth()));
        outState.putInt("thisYear", Integer.parseInt(dateModels.get(20).getYear()));
    }

    /**
     * adds "-" to days that are not a part of this month
     */
    private void makeDaysNull() {
        DateModel dateModel = new DateModel();
        dateModel.setDay("-");
        dateModel.setMonth("-");
        dateModel.setYear("-");
        dateModel.setDayofWeek("-");
        dateModel.setToday(false);
        dateModels.add(dateModel);

    }

    /**
     * if user clicks on the 3dot menu and choose the item, this method will be called
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 0) {
            getResultsFromApi();

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * adds a 3dot menu
     *
     * @param menu
     * @return
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "اتصال به تقویم گوگل");
        return super.onCreateOptionsMenu(menu);
    }


    /**
     * And All the rest, are Google Calendar Stuff :
     */

    private void getResultsFromApi() {
        if (!isGooglePlayServicesAvailable()) {
            acquireGooglePlayServices();
        } else if (!isDeviceOnline()) {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else if (mCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else {
            new MainActivity.MakeRequestTask(mCredential).execute();
        }
    }

    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }

    private boolean isDeviceOnline() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    private void acquireGooglePlayServices() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }

    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                MainActivity.this,
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }

    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
    private void chooseAccount() {
        if (EasyPermissions.hasPermissions(
                this, Manifest.permission.GET_ACCOUNTS)) {
            String accountName = getPreferences(Context.MODE_PRIVATE)
                    .getString(PREF_ACCOUNT_NAME, null);
            if (accountName != null) {
                mCredential.setSelectedAccountName(accountName);
                getResultsFromApi();
            } else {
                // Start a dialog from which the user can choose an account
                startActivityForResult(
                        mCredential.newChooseAccountIntent(),
                        REQUEST_ACCOUNT_PICKER);
            }
        } else {
            // Request the GET_ACCOUNTS permission via a user dialog
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.provide_permission),
                    REQUEST_PERMISSION_GET_ACCOUNTS,
                    Manifest.permission.GET_ACCOUNTS);
        }
    }

    private class MakeRequestTask extends AsyncTask<Void, Void, List<String>> {
        private com.google.api.services.calendar.Calendar mService = null;
        private Exception mLastError = null;

        MakeRequestTask(GoogleAccountCredential credential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.calendar.Calendar.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("تقویم پارسی")
                    .build();
        }

        /**
         * Background task to call Google Calendar API.
         *
         * @param params no parameters needed for this task.
         */
        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                return getDataFromApi();
            } catch (Exception e) {
                mLastError = e;
                cancel(true);
                return null;
            }
        }

        /**
         * Fetch a list of the icNext 10 events from the primary calendar.
         *
         * @return List of Strings describing returned events.
         * @throws IOException
         */
        private List<String> getDataFromApi() throws IOException {
            // List the icNext 10 events from the primary calendar.
            Calendar calendar = Calendar.getInstance();
            CalendarTool calendarTool = new CalendarTool();
            calendar.set(calendarTool.getGregorianYear(), calendarTool.getGregorianMonth() - 1, calendarTool.getGregorianDay(), 0, 0);
            DateTime today = new DateTime(calendar.getTimeInMillis());
            calendar.set(calendarTool.getGregorianYear(), calendarTool.getGregorianMonth() - 1, calendarTool.getGregorianDay(), 23, 59);
            DateTime tonight = new DateTime(calendar.getTimeInMillis());

            List<String> eventStrings = new ArrayList<String>();
            Events events = mService.events().list("primary")
                    .setMaxResults(50)
                    .setTimeMin(today)
                    .setTimeMax(tonight)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
            List<Event> items = events.getItems();

            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                //getDateTime();
                if (start == null) {
                    // All-day events don't have start times, so just use
                    // the start date.
                    start = event.getStart().getDate();
                }
                eventStrings.add(
                        String.format("%s (%s)", event.getSummary(), start));
            }
            return eventStrings;
        }


        @Override
        protected void onPreExecute() {
            mProgress.show();
        }

        @Override
        protected void onPostExecute(List<String> output) {
            mProgress.hide();
            if (output == null || output.size() == 0) {
                txtShowWork.setText(R.string.you_have_no_events_today);
            } else {
                txtShowWork.setText("شما امروز" + output.size() + " " + "رویداد دارید");
            }
        }

        @Override
        protected void onCancelled() {
            mProgress.hide();
            if (mLastError != null) {
                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                    showGooglePlayServicesAvailabilityErrorDialog(
                            ((GooglePlayServicesAvailabilityIOException) mLastError)
                                    .getConnectionStatusCode());
                } else if (mLastError instanceof UserRecoverableAuthIOException) {
                    startActivityForResult(
                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
                            OnClickDialogActivity.REQUEST_AUTHORIZATION);
                } else {
                    Log.d("LOG", "The following error occurred:\n"
                            + mLastError.getMessage());
                }
            } else {
                Toast.makeText(MainActivity.this, R.string.your_request_cancelled, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(
                requestCode, permissions, grantResults, this);
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Do nothing.
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Do nothing.
    }

    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.install_google_play_service), Toast.LENGTH_LONG).show();
                } else {
                    getResultsFromApi();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null &&
                        data.getExtras() != null) {
                    String accountName =
                            data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        SharedPreferences settings =
                                getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.apply();
                        mCredential.setSelectedAccountName(accountName);
                        getResultsFromApi();
                    }
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode == RESULT_OK) {
                    getResultsFromApi();
                }
                break;
        }
    }


    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        GestureDetector detector;
        ClickListener clickListener;

        RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {

                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildLayoutPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && detector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildLayoutPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    @Override
    protected void onDestroy() {
        if (mProgress != null)
            mProgress.dismiss();
        super.onDestroy();
    }
}

