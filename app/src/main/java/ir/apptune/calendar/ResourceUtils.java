package ir.apptune.calendar;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;

import java.util.HashMap;


/**
 * This class returns Persian Calendar events , with parsing the xml's inside the app.
 * please look at xml folder
 */

public final class ResourceUtils {
    private final static String XCalendarH = "ObservedHijriCalendar";
    private final static String XCalendarG = "GregorianCalendar";
    private final static String XCalendarP = "PersianCalendar";
    static HashMap<Integer, String> eventG, eventH, eventP;
    static HashMap<Integer, Boolean> vacationG;
    static HashMap<Integer, Boolean> vacationH;
    public static HashMap<Integer, Boolean> vacationP;

    public ResourceUtils(Context c) {
        eventP = new HashMap<Integer, String>();
        eventH = new HashMap<Integer, String>();
        eventG = new HashMap<Integer, String>();
        vacationP = new HashMap<Integer, Boolean>();
        vacationH = new HashMap<Integer, Boolean>();
        vacationG = new HashMap<Integer, Boolean>();
        getHashMapResource(c, R.xml.events_gregorian);
        getHashMapResource(c, R.xml.events_persian);
        getHashMapResource(c, R.xml.events_misc);
    }

    private static void getHashMapResource(Context c, int hashMapResId) {

        XmlResourceParser parser = c.getResources().getXml(hashMapResId);

        String key = null, value = null;
        String title, day, month, isVacation, xCalendar;

        try {
            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {
                    Log.d("utils", "Start document");
                } else if (eventType == XmlPullParser.START_TAG) {
                    if (parser.getName().equals("XCalendarEvents")) {


                    } else if (parser.getName().equals("Event")) {
                        title = parser.getAttributeValue(null, "Title");
                        day = parser.getAttributeValue(null, "Day");

                        month = parser.getAttributeValue(null, "Month");

                        isVacation = parser.getAttributeValue(null, "IsVacation");


                        xCalendar = parser.getAttributeValue(null, "XCalendar");
                        if (null == title) {
                            parser.close();
                            return;
                        }
                        if (day != null && month != null && xCalendar != null) {
                            int dayMonth = Integer.parseInt(month) * 100 + Integer.parseInt(day);
                            boolean vacation = false;
                            if (isVacation != null) if (isVacation.equals("1")) vacation = true;


                            if (xCalendar.equals(XCalendarP)) {
                                if (eventP.containsKey(dayMonth))
                                    eventP.put(dayMonth, eventP.get(dayMonth) + " " + title);
                                else
                                    eventP.put(dayMonth, title);
                                if (vacation) vacationP.put(dayMonth, true);
                            } else if (xCalendar.equals(XCalendarH)) {
                                if (eventH.containsKey(dayMonth))
                                    eventH.put(dayMonth, eventH.get(dayMonth) + " " + title);
                                else
                                    eventH.put(dayMonth, title);
                                if (vacation) vacationH.put(dayMonth, true);
                            } else if (xCalendar.equals(XCalendarG)) {
                                if (eventG.containsKey(dayMonth))
                                    eventG.put(dayMonth, eventG.get(dayMonth) + " " + title);
                                else

                                    eventG.put(dayMonth, title);
                                if (vacation) vacationG.put(dayMonth, true);

                            }
                        }
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (parser.getName().equals("Event")) {


                        key = null;
                        value = null;
                    }
                } else if (eventType == XmlPullParser.TEXT) {
                    if (null != key) {
                        value = parser.getText();
                    }
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }


    }

}
