package ir.apptune.calendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * The activity that shows when user clicks on days, in calendar
 */

public class OnClickDialogActivity extends Activity {
    TextView txtShowPersianDateOnclick;
    TextView txtShowGregorianDateOnclick;
    TextView txtShowEvents;
    LinearLayout layoutShowDayColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_on_click_dialog);
        txtShowPersianDateOnclick = (TextView) findViewById(R.id.txt_show_persian_date_on_click);
        txtShowGregorianDateOnclick = (TextView) findViewById(R.id.txt_show_gregorian_date_on_click);
        txtShowEvents = (TextView) findViewById(R.id.txt_show_events);
        layoutShowDayColor = (LinearLayout) findViewById(R.id.layout_show_day_color);

        Intent intent = getIntent();
        String irDay = intent.getStringExtra("IranianDay");
        String irMonth = intent.getStringExtra("IranianMonth");
        String irYear = intent.getStringExtra("IranianYear");
        CalendarTool calendarTool = new CalendarTool();
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

    }
}
