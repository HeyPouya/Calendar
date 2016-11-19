package ir.apptune.calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


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
    List<DateModel> dateModels;
    GridView gridView;
    TextView txtMonthName;
    Button btn_previous;
    Button btn_next;

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
        gridView = (GridView) findViewById(R.id.grid_view);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_previous = (Button) findViewById(R.id.btn_previous);
        DAY = cTool.getIranianDay();
        MONTH = cTool.getIranianMonth();
        YEAR = cTool.getIranianYear();

        showCalendar();
        setNotificationAlarmManager(this);
        showNotification();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (dateModels.get(i).getDay() == "-")
                    return;
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("More Information About This Day :")
                        .setMessage(dateModels.get(i).getDay() + " " +
                                dateModels.get(i).getMonth() + " " +
                                dateModels.get(i).getYear() + "\n" +
                                dateModels.get(i).getgDay() + " " +
                                dateModels.get(i).getgMonth() + " " +
                                dateModels.get(i).getgYear() + " "
                        )
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
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
    Calculates number of days that a month has and returns the int value
     */

//    private int countDaysOfMonth() {
//        int number = 0;
//        if (thisMonth <= 6) {
//            number = 31;
//        } else if (thisMonth > 6 && thisMonth < 12) {
//            number = 30;
//        } else if (thisMonth == 12 && isLeapYear()) {
//            number = 30;
//        } else if (thisMonth == 12 && !isLeapYear()) {
//            number = 29;
//        }
//
//        return number;
//
//    }

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
        numberOfDays = DaysOfMonth.getCount(thisMonth,thisYear,MainActivity.this);
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

//    private Boolean isLeapYear() {
//        int leapYear = thisYear;
//        Boolean result;
//        if (leapYear > 1395) {
//            while (leapYear > 1395) {
//                leapYear = leapYear - 4;
//            }
//        } else {
//            while (leapYear < 1395) {
//                leapYear = leapYear + 4;
//            }
//
//        }
//
//        if (leapYear == 1395) {
//            result = true;
//            Toast.makeText(MainActivity.this, "امسال، سال کبیسه است", Toast.LENGTH_SHORT).show();
//        } else {
//            result = false;
//        }
//        return result;
//    }

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
        txtMonthName.setText(thisYear + " " + result);
        CalendarAdapter adapter = new CalendarAdapter(MainActivity.this, dateModels);
        gridView.setAdapter(adapter);

    }

    private void setNotificationAlarmManager(Context context) {
        Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
        intent.putExtra("IranianDay", DAY);
        intent.putExtra("IranianMonth", MONTH);
        intent.putExtra("IranianYear", YEAR);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 22);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent); //Repeat every 24 hours
    }

    private void showNotification() {
        Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
        intent.putExtra("IranianDay", DAY);
        intent.putExtra("IranianMonth", MONTH);
        intent.putExtra("IranianYear", YEAR);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        try {
            pendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

}