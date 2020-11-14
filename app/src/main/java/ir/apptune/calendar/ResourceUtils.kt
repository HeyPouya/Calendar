package ir.apptune.calendar

import android.content.Context
import android.util.Log
import org.koin.java.KoinJavaComponent
import org.xmlpull.v1.XmlPullParser

/**
 * This class returns Persian Calendar events , with parsing the xml's inside the app.
 * please look at xml folder
 */

private const val XCalendarH = "ObservedHijriCalendar"
private const val XCalendarG = "GregorianCalendar"
private const val XCalendarP = "PersianCalendar"
private const val EVENT = "Event"
private const val TITLE = "Title"
private const val DAY = "Day"
private const val MONTH = "Month"
private const val VACATION = "IsVacation"
private const val X_CALENDAR = "XCalendar"
private const val IS_VACATION = "1"

class ResourceUtils(c: Context) {

    private fun getHashMapResource(c: Context, hashMapResId: Int) {
        val parser = c.resources.getXml(hashMapResId)
        var key: String? = null
        var value: String? = null
        var title: String?
        var day: String?
        var month: String?
        var isVacation: String?
        var xCalendar: String?
        try {
            var eventType = parser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {
                    Log.d("utils", "Start document")
                } else if (eventType == XmlPullParser.START_TAG) {
                    if (parser.name == EVENT) {
                        title = parser.getAttributeValue(null, TITLE)
                        day = parser.getAttributeValue(null, DAY)
                        month = parser.getAttributeValue(null, MONTH)
                        isVacation = parser.getAttributeValue(null, VACATION)
                        xCalendar = parser.getAttributeValue(null, X_CALENDAR)
                        if (null == title) {
                            parser.close()
                            return
                        }
                        if (day != null && month != null && xCalendar != null) {
                            val dayMonth = month.toInt() * 100 + day.toInt()
                            var vacation = false
                            if (isVacation != null) if (isVacation == IS_VACATION) vacation = true
                            if (xCalendar == XCalendarP) {
                                if (eventP.containsKey(dayMonth)) eventP[dayMonth] = eventP[dayMonth].toString() + " " + title else eventP[dayMonth] = title
                                if (vacation) vacationP[dayMonth] = true
                            } else if (xCalendar == XCalendarH) {
                                if (eventH.containsKey(dayMonth)) eventH[dayMonth] = eventH[dayMonth].toString() + " " + title else eventH[dayMonth] = title
                                if (vacation) vacationH[dayMonth] = true
                            } else if (xCalendar == XCalendarG) {
                                if (eventG.containsKey(dayMonth)) eventG[dayMonth] = eventG[dayMonth].toString() + " " + title else eventG[dayMonth] = title
                                if (vacation) vacationG[dayMonth] = true
                            }
                        }
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (parser.name == EVENT) {
                        key = null
                        value = null
                    }
                } else if (eventType == XmlPullParser.TEXT) {
                    if (null != key) {
                        value = parser.text
                    }
                }
                eventType = parser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }
    }

    companion object {
        var eventG = hashMapOf<Int, String>()
        var eventH = hashMapOf<Int, String>()
        var eventP = hashMapOf<Int, String>()
        var vacationG = hashMapOf<Int, Boolean>()
        var vacationH = hashMapOf<Int, Boolean>()
        var vacationP = hashMapOf<Int, Boolean>()
    }

    init {
        getHashMapResource(c, R.xml.events_gregorian)
        getHashMapResource(c, R.xml.events_persian)
        getHashMapResource(c, R.xml.events_misc)
        getHashMapResource(c, R.xml.events_arabic)
    }
}