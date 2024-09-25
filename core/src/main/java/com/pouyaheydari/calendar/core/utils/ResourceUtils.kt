package com.pouyaheydari.calendar.core.utils

import android.app.Application
import android.content.Context
import android.util.Log
import com.pouyaheydari.calendar.core.R
import org.xmlpull.v1.XmlPullParser
import javax.inject.Inject
import javax.inject.Singleton

private const val XCalendarP = "PersianCalendar"
private const val XCalendarG = "GregorianCalendar"
private const val EVENT = "Event"
private const val TITLE = "Title"
private const val DAY = "Day"
private const val MONTH = "Month"
private const val VACATION = "IsVacation"
private const val X_CALENDAR = "XCalendar"
private const val IS_VACATION = "1"

@Singleton
class ResourceUtils @Inject constructor(app: Application) {

    var eventG = hashMapOf<Int, List<String>>()
        private set
    var eventP = hashMapOf<Int, List<String>>()
        private set
    var vacationP = hashMapOf<Int, Boolean>()
        private set

    init {
        getHashMapResource(app, R.xml.events_gregorian)
        getHashMapResource(app, R.xml.events_persian)
        getHashMapResource(app, R.xml.events_misc)
        getHashMapResource(app, R.xml.events_arabic)
    }

    private fun getHashMapResource(c: Context, hashMapResId: Int) {
        val parser = c.resources.getXml(hashMapResId)
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
                                if (eventP.containsKey(dayMonth)) {
                                    eventP[dayMonth] =
                                        buildList {
                                            addAll(eventP[dayMonth].orEmpty())
                                            add(title)
                                        }
                                } else {
                                    eventP[dayMonth] = listOf(title)
                                }
                                if (vacation) vacationP[dayMonth] = true
                            }else if (xCalendar == XCalendarG) {
                                if (eventG.containsKey(dayMonth)) {
                                    eventG[dayMonth] =
                                        buildList {
                                            addAll(eventG[dayMonth].orEmpty())
                                            add(title)
                                        }
                                } else {
                                    eventG[dayMonth] = listOf(title)
                                }
                            }
                        }
                    }
                }
                eventType = parser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }
    }
}