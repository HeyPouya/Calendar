package ir.apptune.calendar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;

import java.io.IOException;
import java.util.Arrays;

/**
 * This class gets event data from user and sets the data in His google calendar.
 */

public class SetJobToDoActivity extends AppCompatActivity {
    GoogleAccountCredential mCredential;
    private static final String[] SCOPES = {CalendarScopes.CALENDAR};
    String gDay;
    String gMonth;
    String gYear;
    EditText edtEventName;
    EditText edtEventSummary;
    EditText edtEventLocation;
    EditText timeEventStartHour;
    EditText timeEventStartMinute;
    EditText timeEventEndHour;
    EditText timeEventEndMinute;
    Button btnSave;
    String name;
    String summary;
    String location;
    String startH;
    String startM;
    String endH;
    String endM;
    ProgressDialog mProgress;
    Boolean result;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_job_to_do);
        mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());

        Intent intent = getIntent();
        gDay = intent.getStringExtra("gDay");
        gMonth = intent.getIntExtra("gMonth", 0) + "";
        gYear = intent.getIntExtra("gYear", 0) + "";
        String accountName = intent.getStringExtra("accountName");
        // getResultsFromApi();
        edtEventName = (EditText) findViewById(R.id.edt_event_name);
        edtEventSummary = (EditText) findViewById(R.id.edt_event_summary);
        edtEventLocation = (EditText) findViewById(R.id.edt_event_location);
        timeEventStartHour = (EditText) findViewById(R.id.time_event_start_hour);
        timeEventStartMinute = (EditText) findViewById(R.id.time_event_start_minute);
        timeEventEndHour = (EditText) findViewById(R.id.time_event_end_hour);
        timeEventEndMinute = (EditText) findViewById(R.id.time_event_end_minute);
        btnSave = (Button) findViewById(R.id.btn_save);
        mProgress = new ProgressDialog(this);
        mProgress.setMessage(getString(R.string.please_wait));


        if (accountName != null) {
            mCredential.setSelectedAccountName(accountName);
        } else {
            Toast.makeText(this, R.string.please_login_from_main_page, Toast.LENGTH_LONG).show();
            finish();
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = edtEventName.getText().toString();
                summary = edtEventSummary.getText().toString();
                location = edtEventLocation.getText().toString();
                startH = timeEventStartHour.getText().toString();
                startM = timeEventStartMinute.getText().toString();
                endH = timeEventEndHour.getText().toString();
                endM = timeEventEndMinute.getText().toString();

                if (name.equals("") || summary.equals("") || location.equals("") || startH.equals("") || startM.equals("") || endH.equals("") || endM.equals("")) {
                    Toast.makeText(SetJobToDoActivity.this, R.string.please_fill_all_fields, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(startH) < 10 && !startH.contains("0")) {
                    startH = "0" + startH;
                }
                if (Integer.parseInt(startM) < 10 && !startM.contains("0")) {
                    startM = "0" + startM;
                }
                if (Integer.parseInt(endH) < 10 && !endH.contains("0")) {
                    endH = "0" + endH;
                }
                if (Integer.parseInt(endM) < 10 && !endM.contains("0")) {
                    endM = "0" + endM;
                }

                if (Integer.parseInt(startH) > 24 || Integer.parseInt(startM) > 60 || Integer.parseInt(endH) > 24 || Integer.parseInt(endM) > 60) {
                    Toast.makeText(SetJobToDoActivity.this, R.string.please_insert_correct_time, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(startH) == Integer.parseInt(endH) && Integer.parseInt(startM) >= Integer.parseInt(endM) || Integer.parseInt(startH) > Integer.parseInt(endH)) {
                    Toast.makeText(SetJobToDoActivity.this, R.string.start_time_cannot_be_greater_than_end_time, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isDeviceOnline()) {
                    Toast.makeText(SetJobToDoActivity.this, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    return;
                }

                AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {

                    @Override
                    protected void onPreExecute() {
                        mProgress.show();
                        mProgress.setCancelable(false);
                    }

                    @Override
                    protected Boolean doInBackground(Void... voids) {
                        HttpTransport transport = AndroidHttp.newCompatibleTransport();
                        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
                        com.google.api.services.calendar.Calendar service = new com.google.api.services.calendar.Calendar.Builder(
                                transport, jsonFactory, mCredential)
                                .setApplicationName("تقویم پارسی")
                                .build();

                        Event event = new Event()
                                .setSummary(name)
                                .setLocation(location)
                                .setDescription(summary);

                        DateTime startDateTime = new DateTime(gYear + "-" + gMonth + "-" + gDay + "T" + startH + ":" + startM + getString(R.string.harcoding_date_string));
                        EventDateTime start = new EventDateTime()
                                .setDateTime(startDateTime)
                                .setTimeZone("Asia/Tehran");
                        event.setStart(start);

                        DateTime endDateTime = new DateTime(gYear + "-" + gMonth + "-" + gDay + "T" + endH + ":" + endM + getString(R.string.harcoding_date_string));
                        EventDateTime end = new EventDateTime()
                                .setDateTime(endDateTime)
                                .setTimeZone("Asia/Tehran");
                        event.setEnd(end);

                        EventReminder[] reminderOverrides = new EventReminder[]{
                                new EventReminder().setMethod("email").setMinutes(24 * 60),
                                new EventReminder().setMethod("popup").setMinutes(10),
                        };
                        Event.Reminders reminders = new Event.Reminders()
                                .setUseDefault(false)
                                .setOverrides(Arrays.asList(reminderOverrides));
                        event.setReminders(reminders);

                        String calendarId = "primary";
                        try {
                            event = service.events().insert(calendarId, event).execute();
                            result = CallingGoogleWebservice.getUrl(event.getHtmlLink(), SetJobToDoActivity.this);
                        } catch (IOException e) {
                            e.printStackTrace();

                        }
                        return result;

                    }

                    @Override
                    protected void onPostExecute(Boolean aVoid) {
                        super.onPostExecute(aVoid);
                        if (!aVoid) {
                            Toast.makeText(SetJobToDoActivity.this, R.string.some_thing_happend_try_again, Toast.LENGTH_LONG).show();
                            return;
                        }
                        Toast.makeText(SetJobToDoActivity.this, R.string.event_has_set_successfully, Toast.LENGTH_LONG).show();
                        mProgress.hide();
                        finish();
                    }
                };
                task.execute();


            }
        });
    }

    private boolean isDeviceOnline() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

}



