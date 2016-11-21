package ir.apptune.calendar;

/**
 * Returns English Month names in persian.
 */

class EnglishMonthName {

    static String getName(int monthNumber) {
        String result = "";
        switch (monthNumber) {
            case 1:
                result = "ژانویه";
                break;
            case 2:
                result = "فوریه";
                break;

            case 3:
                result = "مارس";
                break;

            case 4:
                result = "آوریل";
                break;

            case 5:
                result = "مه";
                break;

            case 6:
                result = "ژوئن";
                break;

            case 7:
                result = "ژوئیه";
                break;

            case 8:
                result = "اوت";
                break;

            case 9:
                result = "سپتامبر";
                break;

            case 10:
                result = "اکتبر";
                break;

            case 11:
                result = "نوامبر";
                break;

            case 12:
                result = "دسامبر";
                break;

        }
        return result;
    }
}
