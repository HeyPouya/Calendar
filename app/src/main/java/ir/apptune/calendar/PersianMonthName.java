package ir.apptune.calendar;

/**
 * Converts MonthNumber to Its Persian Name
 */

class PersianMonthName {
    static String getName(int monthNumber) {
        String result = "";
        switch (monthNumber) {
            case 1:
                result = "فروردین";
                break;
            case 2:
                result = "اردیبهشت";
                break;

            case 3:
                result = "خرداد";
                break;

            case 4:
                result = "اردیبهشت";
                break;

            case 5:
                result = "مرداد";
                break;

            case 6:
                result = "شهریور";
                break;

            case 7:
                result = "مهر";
                break;

            case 8:
                result = "آبان";
                break;

            case 9:
                result = "آذر";
                break;

            case 10:
                result = "دی";
                break;

            case 11:
                result = "بهمن";
                break;

            case 12:
                result = "اسفند";
                break;

        }
        return result;

    }
}
