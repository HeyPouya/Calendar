package ir.apptune.calendar;

import android.accounts.AccountManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.apptune.calendar.base.extensions.IntExtensionsKt;
import ir.apptune.calendar.repository.local.CalendarTool;

/**
 * The activity that Pops-Up when user clicks on days, in MainPage calendar.
 */

public class OnClickDialogActivity extends AppCompatActivity {
    @BindView(R.id.txt_show_persian_date_on_click)
    TextView txtShowPersianDateOnclick;
    @BindView(R.id.txt_show_gregorian_date_on_click)
    TextView txtShowGregorianDateOnclick;
    @BindView(R.id.txt_show_events)
    TextView txtShowEvents;
    @BindView(R.id.layout_show_day_color)
    LinearLayout layoutShowDayColor;
    @BindView(R.id.txt_show_google_events)
    TextView txt_show_google_events;
    @BindView(R.id.btn_insert_job_to_calendar)
    Button btnInsertJobToCalendar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    GoogleAccountCredential mCredential;
    ProgressDialog mProgress;
    CalendarTool calendarTool;
    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;
    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = {CalendarScopes.CALENDAR};
    String day;
    String accountName1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        setContentView(R.layout.activity_on_click_dialog);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        setFinishOnTouchOutside(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade explode = new Fade();
            explode.setDuration(500);
            getWindow().setEnterTransition(explode);
        }


        final Intent intent = getIntent();
        String irDay = intent.getStringExtra("IranianDay");
        String irMonth = intent.getStringExtra("IranianMonth");
        String irYear = intent.getStringExtra("IranianYear");
        accountName1 = intent.getStringExtra("accountName");

        calendarTool = new CalendarTool();
        calendarTool.setIranianDate(Integer.parseInt(irYear), Integer.parseInt(irMonth), Integer.parseInt(irDay));

        String shamsi = calendarTool.getWeekDayStr() + " " + irDay + " " +
                IntExtensionsKt.toPersianWeekDay(Integer.parseInt(irMonth), this) + " " + irYear;
        String gregorian = calendarTool.getGregorianDay() + " "
                + IntExtensionsKt.toEnglishMonth(calendarTool.getGregorianMonth(), this) + " "
                + calendarTool.getGregorianYear();


        txtShowPersianDateOnclick.setText(shamsi);
        txtShowGregorianDateOnclick.setText(gregorian);


        ResourceUtils eventCalendar = new ResourceUtils(this);
        int persianTemp = Integer.parseInt(irMonth) * 100;
        persianTemp += Integer.parseInt(irDay);
        int gregorianTemp = calendarTool.getGregorianMonth() * 100 + calendarTool.getGregorianDay();
        String s = "";
        String g = "";
        if (ResourceUtils.eventP.containsKey(persianTemp))
            s = ResourceUtils.eventP.get(persianTemp);

        if (ResourceUtils.eventG.containsKey(gregorianTemp))
            g = ResourceUtils.eventG.get(gregorianTemp);

        txtShowEvents.setText(s + "\n" + g);
        if (calendarTool.getDayOfWeek() == 4 || ResourceUtils.vacationP.containsKey(persianTemp)) {
            toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        }

        mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());
        mProgress = new ProgressDialog(this);
        mProgress.setMessage(getResources().getString(R.string.connecting_to_google_calendar));

        if (accountName1 != null) {
            mCredential.setSelectedAccountName(accountName1);
            getResultsFromApi();
        }
        btnInsertJobToCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(OnClickDialogActivity.this, SetJobToDoActivity.class);
                if (calendarTool.getGregorianDay() < 10) {
                    day = "0" + calendarTool.getGregorianDay();
                } else {
                    day = calendarTool.getGregorianDay() + "";
                }
                intent1.putExtra("gDay", day);
                intent1.putExtra("gMonth", calendarTool.getGregorianMonth());
                intent1.putExtra("gYear", calendarTool.getGregorianYear());
                intent1.putExtra("accountName", accountName1);
                startActivity(intent1);
            }
        });

    }

    /**
     * This will reload the task page when the user comes icPrevious from setting event Page
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (accountName1 != null) {
            mCredential.setSelectedAccountName(accountName1);
            getResultsFromApi();
        }
    }

    /**
     * The rest are google calendar stuff:
     */


    private void getResultsFromApi() {
        if (!isGooglePlayServicesAvailable()) {
            acquireGooglePlayServices();
        } else if (!isDeviceOnline()) {
            Toast.makeText(OnClickDialogActivity.this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        } else if (mCredential.getSelectedAccountName() == null) {
//            chooseAccount();
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

//    private void chooseAccount() {
//        if (EasyPermissions.hasPermissions(
//                this, Manifest.permission.GET_ACCOUNTS)) {
//            String accountName = getPreferences(Context.MODE_PRIVATE)
//                    .getString(PREF_ACCOUNT_NAME, null);
//            if (accountName != null) {
//                mCredential.setSelectedAccountName(accountName);
//                getResultsFromApi();
//            } else {
//                // Start a dialog from which the user can choose an account
//                startActivityForResult(
//                        mCredential.newChooseAccountIntent(),
//                        REQUEST_ACCOUNT_PICKER);
//            }
//        } else {
//            // Request the GET_ACCOUNTS permission via a user dialog
//            EasyPermissions.requestPermissions(
//                    this,
//                    getResources().getString(R.string.provide_permission),
//                    REQUEST_PERMISSION_GET_ACCOUNTS,
//                    Manifest.permission.GET_ACCOUNTS);
//        }
//    }

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

            java.util.Calendar calendar = java.util.Calendar.getInstance();
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
                    .setTimeZone("Asia/Tehran")
                    .execute();
            List<Event> items = events.getItems();

            for (Event event : items) {

                DateTime start = event.getStart().getDateTime();
                DateTime end = event.getEnd().getDateTime();
                String dateToString = start + "";
                String dateToString1 = end + "";

                Calendar calS = Calendar.getInstance();
                SimpleDateFormat sdfS = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

                Calendar calE = Calendar.getInstance();
                SimpleDateFormat sdfE = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

                try {
                    calS.setTime(sdfS.parse(dateToString));
                    calE.setTime(sdfE.parse(dateToString1));
                    dateToString = "از" + " " + calS.get(Calendar.HOUR_OF_DAY) + "" + ":" + calS.get(Calendar.MINUTE) + ":" + "00" + " " +
                            "تا" + " " + calE.get(Calendar.HOUR_OF_DAY) + "" + ":" + calE.get(Calendar.MINUTE) + ":" + "00";

                } catch (ParseException e) {

                }

                if (start == null) {
                    // All-day events don't have start times, so just use
                    // the start date.
                    dateToString = getString(R.string.all_day);
                }
                eventStrings.add(
                        //String.format("%s (%s)", event.getSummary(), start)
                        event.getSummary() + " :" + "\n" + "(" + dateToString + ")");
            }
            return eventStrings;
        }


        @Override
        protected void onPreExecute() {
            mProgress.show();
        }

        @Override
        protected void onPostExecute(List<String> output) {
            mProgress.dismiss();
            if (output == null || output.size() == 0) {
                txt_show_google_events.setText(R.string.you_have_no_events_in_this_day);
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
                Toast.makeText(OnClickDialogActivity.this, getResources().getString(R.string.your_request_cancelled), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                    Toast.makeText(OnClickDialogActivity.this, R.string.install_google_play_service
                            , Toast.LENGTH_LONG).show();
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

    @Override
    public boolean onSupportNavigateUp() {
        ActivityCompat.finishAfterTransition(this);
        return true;
    }

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAfterTransition(this);
    }

    @Override
    protected void onDestroy() {
        ActivityCompat.finishAfterTransition(this);
        if (mProgress != null)
            mProgress.dismiss();
        super.onDestroy();
    }
}