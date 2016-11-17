package ir.apptune.calendar;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    /*
     Declare all variables here :
      */

    int numberOfDays; // How many days a month has
    CalendarTool cTool; // An instance of CalendarTool Class that converts Garegorian Date to Persian Date
    int thisMonth = 0; // The int number of current Month. ex : 8 For Aban
    int thisYear = 0; // The int number of current year. ex : 1395
    static int DAY = 0;
    static int MONTH = 0;
    static int YEAR = 0;

    //    int dayOfWeek = 0; // The int number of dayOfWeek. ex : 5 stands for sat , 6 for sun ...
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

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                if(dateModels.get(i).getDay() == "-")
                                                    return;
                                                new AlertDialog.Builder(MainActivity.this)
                                                        .setTitle("More Information About This Day :")
                                                        .setMessage(dateModels.get(i).getDay() + " " +
                                                                dateModels.get(i).getMonth() + " " +
                                                                dateModels.get(i).getYear() + "\n"+
                                                                dateModels.get(i).getgDay()+ " "+
                                                                dateModels.get(i).getgMonth()+ " "+
                                                                dateModels.get(i).getgYear()+ " "
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

    private int countDaysOfMonth() {
        int number = 0;
        if (thisMonth <= 6) {
            number = 31;
        } else if (thisMonth > 6 && thisMonth < 12) {
            number = 30;
        } else if (thisMonth == 12 && isLeapYear()) {
            number = 30;
        } else if (thisMonth == 12 && !isLeapYear()) {
            number = 29;
        }

        return number;

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
        numberOfDays = countDaysOfMonth();
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


            Log.d("DATE MODEL TO STRING", dateModel.toString());
            dateModels.add(dateModel);
        }

    }

    private Boolean isLeapYear() {
        int leapYear = thisYear;
        Boolean result;
        if (leapYear>1395) {
            while (leapYear > 1395) {
                leapYear = leapYear - 4;
            }
        }else {
            while (leapYear < 1395) {
                leapYear = leapYear + 4;
            }

        }

        if (leapYear == 1395) {
            result = true;
            Toast.makeText(MainActivity.this, "امسال، سال کبیسه است", Toast.LENGTH_SHORT).show();
            Log.d("LOG KABISE", result + "");
        } else {
            result = false;
        }
        return result;
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
        showMonthName();
        CalendarAdapter adapter = new CalendarAdapter(MainActivity.this, dateModels);
        gridView.setAdapter(adapter);

    }

    private void showMonthName() {
        Log.d("LOG THIS MONTH", thisMonth + "");
        switch (thisMonth) {
            case 1:
                txtMonthName.setText(thisYear + " " + "فروردین");
                break;
            case 2:
                txtMonthName.setText(thisYear + " " + "اردیبهشت");
                break;

            case 3:
                txtMonthName.setText(thisYear + " " + "خرداد");
                break;

            case 4:
                txtMonthName.setText(thisYear + " " + "تیر");
                break;

            case 5:
                txtMonthName.setText(thisYear + " " + "مرداد");
                break;

            case 6:
                txtMonthName.setText(thisYear + " " + "شهریور");
                break;

            case 7:
                txtMonthName.setText(thisYear + " " + "مهر");
                break;

            case 8:
                txtMonthName.setText(thisYear + " " + "آبان");
                break;

            case 9:
                txtMonthName.setText(thisYear + " " + "آذر");
                break;

            case 10:
                txtMonthName.setText(thisYear + " " + "دی");
                break;

            case 11:
                txtMonthName.setText(thisYear + " " + "بهمن");
                break;

            case 12:
                txtMonthName.setText(thisYear + " " + "اسفند");
                break;


        }
    }

}




