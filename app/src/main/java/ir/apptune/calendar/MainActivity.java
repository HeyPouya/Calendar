package ir.apptune.calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {


    /*
     Declare all variables here :
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
    GridView gridView;
    TextView txtMonthName;
    TextView txtShowToday;
    TextView txtshowDate;
    Button btn_previous;
    Button btn_next;
    TextView txtYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Instantiate all variables Here :
         */
        dateModels = new ArrayList<>();
        cTool = new CalendarTool();
        txtMonthName = (TextView) findViewById(R.id.txt_month_name);
        txtShowToday = (TextView) findViewById(R.id.txt_show_today);
        txtshowDate = (TextView) findViewById(R.id.txt_show_date);
        txtYear = (TextView) findViewById(R.id.txt_year);
        gridView = (GridView) findViewById(R.id.grid_view);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_previous = (Button) findViewById(R.id.btn_previous);
        DAY = cTool.getIranianDay();
        MONTH = cTool.getIranianMonth();
        YEAR = cTool.getIranianYear();
        STATE_OF_DAY = cTool.getWeekDayStr();


        if (savedInstanceState != null) {
            thisMonth = savedInstanceState.getInt("thisMonth");
            thisYear = savedInstanceState.getInt("thisYear");
        }

        if (Build.MANUFACTURER + "" != "HTC" && (Build.MANUFACTURER + "") != "Htc" && (Build.MANUFACTURER + "" != "htc")) {
            Configuration configuration = getResources().getConfiguration();
            configuration.setLayoutDirection(new Locale("fa"));
            getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
        }

        txtShowToday.setText(STATE_OF_DAY);
        txtshowDate.setText(DAY + "");
        showCalendar();
        setNotificationAlarmManager(this);
        showNotification();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (dateModels.get(i).getDay() == "-")
                    return;
                Intent intent = new Intent(MainActivity.this, OnClickDialogActivity.class);

                intent.putExtra("IranianDay", dateModels.get(i).getDay());
                intent.putExtra("IranianMonth", dateModels.get(i).getMonth());
                intent.putExtra("IranianYear", dateModels.get(i).getYear());
                startActivity(intent);

//                ResourceUtils eventCalendar = new ResourceUtils(MainActivity.this);
//                int temp = Integer.parseInt(dateModels.get(i).getMonth()) * 100;
//                temp +=Integer.parseInt(dateModels.get(i).getDay());
//                String s = "";
//                if (eventCalendar.eventP.containsKey(temp))
//                    s = eventCalendar.eventP.get(temp);
//                Calendar cal = Calendar.getInstance();
//                Intent intent = new Intent(Intent.ACTION_EDIT);
//                intent.setType("vnd.android.cursor.item/event");
//                intent.putExtra("beginTime", cal.getTimeInMillis());
//                intent.putExtra("allDay", true);
//                intent.putExtra("rrule", "FREQ=YEARLY");
//                intent.putExtra("endTime", cal.getTimeInMillis() + 60 * 60 * 1000);
//                intent.putExtra("title", "A Test Event from android app");
//                startActivity(intent);
            }
        });

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

        //End of onCreate


    }
    /*
    This Method add spaces to Array of days. So it can Arranges days of weeks. for ex, if a month
     starts with Mon, it adds 2 days of "-" to Sets dates Correctly.

     */

    private void setArrangeOfWeek(int v) {
        DateModel dateModel = new DateModel();
        switch (v) {
            case 5:
                break;
            case 6:
                for (int i = 0; i < 1; i++) {
                    dateModel.setDay("-");
                    dateModel.setMonth("-");
                    dateModel.setYear("-");
                    dateModel.setDayofWeek("-");
                    dateModel.setToday(false);
                    dateModels.add(dateModel);
                }
                break;
            case 0:
                for (int i = 0; i < 2; i++) {
                    dateModel.setDay("-");
                    dateModel.setMonth("-");
                    dateModel.setYear("-");
                    dateModel.setDayofWeek("-");
                    dateModel.setToday(false);
                    dateModels.add(dateModel);
                }
                break;
            case 1:
                for (int i = 0; i < 3; i++) {
                    dateModel.setDay("-");
                    dateModel.setMonth("-");
                    dateModel.setYear("-");
                    dateModel.setDayofWeek("-");
                    dateModel.setToday(false);
                    dateModels.add(dateModel);
                }
                break;
            case 2:
                for (int i = 0; i < 4; i++) {
                    dateModel.setDay("-");
                    dateModel.setMonth("-");
                    dateModel.setYear("-");
                    dateModel.setDayofWeek("-");
                    dateModel.setToday(false);
                    dateModels.add(dateModel);
                }
                break;
            case 3:
                for (int i = 0; i < 5; i++) {
                    dateModel.setDay("-");
                    dateModel.setMonth("-");
                    dateModel.setYear("-");
                    dateModel.setDayofWeek("-");
                    dateModel.setToday(false);
                    dateModels.add(dateModel);
                }
                break;
            case 4:
                for (int i = 0; i < 6; i++) {
                    dateModel.setDay("-");
                    dateModel.setMonth("-");
                    dateModel.setYear("-");
                    dateModel.setDayofWeek("-");
                    dateModel.setToday(false);
                    dateModels.add(dateModel);
                }
                break;

        }


    }

    /*
    Calculates the Year and returns the int Number
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

    /*
Calculates the Month and returns the int Number
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

    /*
    Makes Array Of Days to pass to the Adapter
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

            Log.d("LOG DAteModel", dateModel.toString());
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
        String result = PersianMonthName.getName(thisMonth);
        txtMonthName.setText(result);
        txtYear.setText(thisYear + "");
        CalendarAdapter adapter = new CalendarAdapter(MainActivity.this, dateModels);
        gridView.setAdapter(adapter);

    }

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

    private void showNotification() {
        Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
        try {
            pendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("thisMonth", Integer.parseInt(dateModels.get(20).getMonth()));
        outState.putInt("thisYear", Integer.parseInt(dateModels.get(20).getYear()));
    }

}