package ir.apptune.calendar;

import android.content.Context;
import android.widget.Toast;

/**
 * Calculates if displaying year, is a Leap (Kabise) year or not.
 */

class CalculateLeapYear {
    static Boolean isLeapYear(int thisYear, Context context) {
        int leapYear = thisYear;
        Boolean result;
        if (leapYear > 1395) {
            while (leapYear > 1395) {
                leapYear = leapYear - 4;
            }
        } else {
            while (leapYear < 1395) {
                leapYear = leapYear + 4;
            }

        }

        if (leapYear == 1395) {
            result = true;
            Toast.makeText(context, "امسال، سال کبیسه است", Toast.LENGTH_SHORT).show();
        } else {
            result = false;
        }
        return result;

    }
}
