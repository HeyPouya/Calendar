package ir.apptune.calendar;

import android.content.Context;

/**
 * Calculates how many days are included in displaying month.
 * 29 or 30 or 31
 */

public class DaysOfMonth {
   public static int getCount(int thisMonth, int thisYear, Context context) {
        if (thisMonth <= 6) {
            return 31;
        } else if (thisMonth > 6 && thisMonth < 12) {
            return 30;
        } else if (thisMonth == 12 && CalculateLeapYear.isLeapYear(thisYear, context)) {
            return 30;
        } else if (thisMonth == 12 && !CalculateLeapYear.isLeapYear(thisYear, context)) {
            return 29;
        }

        return 0;

    }
}
