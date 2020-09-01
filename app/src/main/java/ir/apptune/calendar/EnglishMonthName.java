package ir.apptune.calendar;

/**
 * Returns English Month names in persian.
 */

public class EnglishMonthName {

    public static String getName(int monthNumber) {
        switch (monthNumber) {
            case 1:
                return "ژانویه";
            case 2:
                return "فوریه";
            case 3:
                return "مارس";
            case 4:
                return "آوریل";
            case 5:
                return "مه";
            case 6:
                return "ژوئن";
            case 7:
                return "ژوئیه";
            case 8:
                return "اوت";
            case 9:
                return "سپتامبر";
            case 10:
                return "اکتبر";
            case 11:
                return "نوامبر";
            case 12:
                return "دسامبر";
        }
        return "";
    }
}
