package ir.apptune.calendar;

import android.content.Context;

/**
 * Calculates the displaying month, contains how many days.
 */

public class DaysOfMonth {
    static int getCount(int thisMonth ,int thisYear , Context context){
        int number = 0;
        if (thisMonth <= 6) {
            number = 31;
        } else if (thisMonth > 6 && thisMonth < 12) {
            number = 30;
        } else if (thisMonth == 12 && CalculateLeapYear.isLeapYear(thisYear,context)) {
            number = 30;
        } else if (thisMonth == 12 && CalculateLeapYear.isLeapYear(thisYear,context)) {
            number = 29;
        }

        return number;

    }
}
