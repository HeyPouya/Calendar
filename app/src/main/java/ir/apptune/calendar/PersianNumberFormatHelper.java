package ir.apptune.calendar;

/**
 * Turns english numbers to Persian One.
 */

public class PersianNumberFormatHelper {
    private static String[] persianNumbers = new String[]{"۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹"};

    public static String toPersianNumber(String text) {
        if (text.isEmpty())
            return "";
        String out = "";
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if ('0' <= c && c <= '9') {
                out += persianNumbers[Integer.parseInt(String.valueOf(c))];
            } else if (c == '٫') {
                out += '،';
            } else {
                out += c;
            }

        }
        return out;
    }

}
