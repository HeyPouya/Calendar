package ir.apptune.calendar;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * The activity that shows when user clicks on days, in calendar
 */

public class OnClickDialogActivity extends Activity implements EasyPermissions.PermissionCallbacks {
    TextView txtShowPersianDateOnclick;
    TextView txtShowGregorianDateOnclick;
    TextView txtShowEvents;
    LinearLayout layoutShowDayColor;
    GoogleAccountCredential mCredential;
    ProgressDialog mProgress;
    CalendarTool calendarTool;
    TextView txt_show_google_events;

    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;

    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = {CalendarScopes.CALENDAR_READONLY};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_on_click_dialog);
        txtShowPersianDateOnclick = (TextView) findViewById(R.id.txt_show_persian_date_on_click);
        txtShowGregorianDateOnclick = (TextView) findViewById(R.id.txt_show_gregorian_date_on_click);
        txtShowEvents = (TextView) findViewById(R.id.txt_show_events);
        layoutShowDayColor = (LinearLayout) findViewById(R.id.layout_show_day_color);
        txt_show_google_events = (TextView) findViewById(R.id.txt_show_google_events);

        Intent intent = getIntent();
        String irDay = intent.getStringExtra("IranianDay");
        String irMonth = intent.getStringExtra("IranianMonth");
        String irYear = intent.getStringExtra("IranianYear");
        String accountName = intent.getStringExtra("accountName");

        calendarTool = new CalendarTool();
        calendarTool.setIranianDate(Integer.parseInt(irYear), Integer.parseInt(irMonth), Integer.parseInt(irDay));

        String shamsi = calendarTool.getWeekDayStr() + " " + irDay + " " +
                PersianMonthName.getName(Integer.parseInt(irMonth)) + " " + irYear;
        String gregorian = calendarTool.getGregorianDay() + " "
                + EnglishMonthName.getName(calendarTool.getGregorianMonth()) + " "
                + calendarTool.getGregorianYear();


        txtShowPersianDateOnclick.setText(shamsi);
        txtShowGregorianDateOnclick.setText(gregorian);


        ResourceUtils eventCalendar = new ResourceUtils(this);
        int persianTemp = Integer.parseInt(irMonth) * 100;
        persianTemp += Integer.parseInt(irDay);
        int gregorianTemp = calendarTool.getGregorianMonth() * 100 + calendarTool.getGregorianDay();
        String s = "";
        String g = "";
        if (eventCalendar.eventP.containsKey(persianTemp))
            s = eventCalendar.eventP.get(persianTemp);

        if (eventCalendar.eventG.containsKey(gregorianTemp))
            g = eventCalendar.eventG.get(gregorianTemp);

        txtShowEvents.setText(s + "\n" + g);
        if (calendarTool.getDayOfWeek() == 4 || eventCalendar.vacationP.containsKey(persianTemp)) {
            layoutShowDayColor.setBackgroundColor(Color.parseColor("#FF4081"));
        }

        mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());
        mProgress = new ProgressDialog(this);
        mProgress.setMessage("در حال اتصال به تقویم گوگل...");

        if (accountName != null) {
            mCredential.setSelectedAccountName(accountName);
            getResultsFromApi();
        }
    }


    private void getResultsFromApi() {
        if (!isGooglePlayServicesAvailable()) {
            acquireGooglePlayServices();
        } else if (!isDeviceOnline()) {
            Toast.makeText(OnClickDialogActivity.this, "دستگاه به اینترنت متصل نیست", Toast.LENGTH_SHORT).show();
        } else if (mCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else {
            new OnClickDialogActivity.MakeRequestTask(mCredential).execute();
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
                OnClickDialogActivity.this,
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
                    "برای دریافت اطلاعات تقویم گوگل شما، لطفا دسترسی به اکانت خود را فراهم کنید",
                    REQUEST_PERMISSION_GET_ACCOUNTS,
                    Manifest.permission.GET_ACCOUNTS);
        }
    }

    private class MakeRequestTask extends AsyncTask<Void, Void, List<String>> {
        private com.google.api.services.calendar.Calendar mService = null;
        private Exception mLastError = null;

        public MakeRequestTask(GoogleAccountCredential credential) {
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
         * Fetch a list of the next 10 events from the primary calendar.
         *
         * @return List of Strings describing returned events.
         * @throws IOException
         */
        private List<String> getDataFromApi() throws IOException {
            // List the next 10 events from the primary calendar.

            Calendar calendar = Calendar.getInstance();
            calendar.set(calendarTool.getGregorianYear(), calendarTool.getGregorianMonth() - 1, calendarTool.getGregorianDay(), 0, 0);
            DateTime today = new DateTime(calendar.getTimeInMillis());
            calendar.set(calendarTool.getGregorianYear(), calendarTool.getGregorianMonth() - 1, calendarTool.getGregorianDay(), 23, 59);
            DateTime tonight = new DateTime(calendar.getTimeInMillis());

            List<String> eventStrings = new ArrayList<String>();
            Events events = mService.events().list("primary")
                    .setMaxResults(10)
                    .setTimeMin(today)
                    .setTimeMax(tonight)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
            List<Event> items = events.getItems();

            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
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
                txt_show_google_events.setText("شما در این روز ریدادی ندارید");
            } else {

//                output.add(0, "Data retrieved using the Google Calendar API:");
                txt_show_google_events.setText(TextUtils.join("\n", output));
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
                Toast.makeText(OnClickDialogActivity.this, "درخواست شما لغو شد.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(OnClickDialogActivity.this, "این عمل برای اجرا نیاز به گوگل پلی سرویس دارد.لطفا گوگل پلی را نصب کنید و دوباره اپلیکیشن را اجرا کنید "
                            , Toast.LENGTH_SHORT).show();
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

}
