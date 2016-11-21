package ir.apptune.calendar;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

public class OnClickDialogActivity extends Activity {
    TextView txtShowPersianDateOnclick;
    TextView txtShowGregorianDateOnclick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_on_click_dialog);
        txtShowPersianDateOnclick = (TextView) findViewById(R.id.txt_show_persian_date_on_click);
        txtShowGregorianDateOnclick = (TextView) findViewById(R.id.txt_show_gregorian_date_on_click);


        Intent intent = getIntent();
        String irDay = intent.getStringExtra("IranianDay");
        String irMonth = intent.getStringExtra("IranianMonth");
        String irYear = intent.getStringExtra("IranianYear");
        Log.d("LOG", irDay + "");
        CalendarTool calendarTool = new CalendarTool();
        calendarTool.setIranianDate(Integer.parseInt(irYear), Integer.parseInt(irMonth), Integer.parseInt(irDay));

        String shamsi = calendarTool.getWeekDayStr() + " " + irDay + " " +
                PersianMonthName.getName(Integer.parseInt(irMonth)) + " " + irYear;
        String gregorian = calendarTool.getGregorianDay() + " "
                + EnglishMonthName.getName(calendarTool.getGregorianMonth()) + " "
                + calendarTool.getGregorianYear();

        txtShowPersianDateOnclick.setText(shamsi);
        txtShowGregorianDateOnclick.setText(gregorian);
    }
}
