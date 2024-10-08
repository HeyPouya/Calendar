package com.pouyaheydari.calendar.core.utils

import com.pouyaheydari.calendar.core.pojo.GregorianDate
import com.pouyaheydari.calendar.core.pojo.GregorianMonths
import com.pouyaheydari.calendar.core.pojo.ShamsiDate
import com.pouyaheydari.calendar.core.pojo.ShamsiMonths
import com.pouyaheydari.calendar.core.pojo.WeekDay
import javax.inject.Inject

/**
 * The Class That converts Gregorian calendar to Shamsi calendar
 */
class CalendarTool @Inject constructor() {

    private var iranianYear = 0
    private var iranianMonth = 0
    private var iranianDay = 0
    private var gregorianYear = 0
    private var gregorianMonth = 0
    private var gregorianDay = 0
    private var julianYear = 0
    private var julianMonth = 0
    private var julianDay = 0
    private var leap = 0
    private var jdn = 0
    private var march = 0

    fun getIranianDate() =
        ShamsiDate(
            year = iranianYear,
            month = ShamsiMonths.entries.first { it.monthNumber == iranianMonth },
            day = iranianDay,
            weekDay = getDayOfWeek()
        )

    fun getGregorianDate() =
        GregorianDate(
            year = gregorianYear,
            month = GregorianMonths.entries.first { it.monthNumber == gregorianMonth },
            day = gregorianDay,
            weekDay = getDayOfWeek()
        )

    /**
     * setIranianDate:
     * Sets the date according to the Iranian calendar and adjusts the other dates.
     *
     * @param year  int
     * @param month int
     * @param day   int
     */
    fun setIranianDate(year: Int, month: Int, day: Int) {
        iranianYear = year
        iranianMonth = month
        iranianDay = day
        jdn = iranianDateToJDN()
        jdnToIranian()
        jdnToJulian()
        jdnToGregorian()
    }

    /**
     * setGregorianDate:
     * Sets the date according to the Gregorian calendar and adjusts the other dates.
     *
     * @param year  int
     * @param month int
     * @param day   int
     */
    fun setGregorianDate(year: Int, month: Int, day: Int) {
        gregorianYear = year
        gregorianMonth = month
        gregorianDay = day
        jdn = gregorianDateToJDN(year, month, day)
        jdnToIranian()
        jdnToJulian()
        jdnToGregorian()
    }

    /**
     * IranianCalendar:
     * This method determines if the Iranian (Jalali) year is leap (366-day long)
     * or is the common year (365 days), and finds the day in March (Gregorian
     * Calendar)of the first day of the Iranian year ('irYear').Iranian year (irYear)
     * ranges from (-61 to 3177).This method will set the following private data
     * members as follows:
     * leap: Number of years since the last leap year (0 to 4)
     * Gy: Gregorian year of the beginning of Iranian year
     * march: The March day of Farvardin the 1st (first day of jaYear)
     */

    private fun getDayOfWeek() = WeekDay.entries.first { it.weekDayNumber == jdn % 7 }

    private fun iranianCalendar() {
        // Iranian years starting the 33-year rule
        val breaks = intArrayOf(
            -61, 9, 38, 199, 426, 686, 756, 818, 1111, 1181,
            1210, 1635, 2060, 2097, 2192, 2262, 2324, 2394, 2456, 3178
        )

        var jump: Int
        gregorianYear = iranianYear + 621
        var leapJ = -14
        var jp = breaks[0]
        // Find the limiting years for the Iranian year 'irYear'
        var j = 1
        do {
            val jm = breaks[j]
            jump = jm - jp
            if (iranianYear >= jm) {
                leapJ += jump / 33 * 8 + jump % 33 / 4
                jp = jm
            }
            j++
        } while (j < 20 && iranianYear >= jm)
        var n = iranianYear - jp
        // Find the number of leap years from AD 621 to the beginning of the current
        // Iranian year in the Iranian (Jalali) calendar
        leapJ += n / 33 * 8 + (n % 33 + 3) / 4
        if (jump % 33 == 4 && jump - n == 4) leapJ++
        // And the same in the Gregorian date of Farvardin the first
        val leapG = gregorianYear / 4 - (gregorianYear / 100 + 1) * 3 / 4 - 150
        march = 20 + leapJ - leapG
        // Find how many years have passed since the last leap year
        if (jump - n < 6) n = n - jump + (jump + 4) / 33 * 33
        leap = ((n + 1) % 33 - 1) % 4
        if (leap == -1) leap = 4
    }

    /**
     * IsLeap:
     * This method determines if the Iranian (Jalali) year is leap (366-day long)
     * or is the common year (365 days), and finds the day in March (Gregorian
     * Calendar)of the first day of the Iranian year ('irYear').Iranian year (irYear)
     * ranges from (-61 to 3177).This method will set the following private data
     * members as follows:
     * leap: Number of years since the last leap year (0 to 4)
     * Gy: Gregorian year of the beginning of Iranian year
     * march: The March day of Farvardin the 1st (first day of jaYear)
     */
    fun isLeapYear(irYear: Int): Boolean {
        // Iranian years starting the 33-year rule
        val breaks = intArrayOf(
            -61, 9, 38, 199, 426, 686, 756, 818, 1111, 1181,
            1210, 1635, 2060, 2097, 2192, 2262, 2324, 2394, 2456, 3178
        )

        var jump: Int
        gregorianYear = irYear + 621
        var leapJ = -14
        var jp = breaks[0]
        // Find the limiting years for the Iranian year 'irYear'
        var j = 1
        do {
            val jm = breaks[j]
            jump = jm - jp
            if (irYear >= jm) {
                leapJ += jump / 33 * 8 + jump % 33 / 4
                jp = jm
            }
            j++
        } while (j < 20 && irYear >= jm)
        var n = irYear - jp
        // Find the number of leap years from AD 621 to the beginning of the current
        // Iranian year in the Iranian (Jalali) calendar
        leapJ += n / 33 * 8 + (n % 33 + 3) / 4
        if (jump % 33 == 4 && jump - n == 4) leapJ++
        // And the same in the Gregorian date of Farvardin the first
        val leapG = gregorianYear / 4 - (gregorianYear / 100 + 1) * 3 / 4 - 150
        march = 20 + leapJ - leapG
        // Find how many years have passed since the last leap year
        if (jump - n < 6) n = n - jump + (jump + 4) / 33 * 33
        leap = ((n + 1) % 33 - 1) % 4
        if (leap == -1) leap = 4
        return leap == 4 || leap == 0
    }

    /**
     * IranianDateToJDN:
     * Converts a date of the Iranian calendar to the Julian Day Number. It first
     * invokes the 'IranianCalender' private method to convert the Iranian date to
     * Gregorian date and then returns the Julian Day Number based on the Gregorian
     * date. The Iranian date is obtained from 'irYear'(1-3100),'irMonth'(1-12) and
     * 'irDay'(1-29/31).
     *
     * @return long (Julian Day Number)
     */
    private fun iranianDateToJDN(): Int {
        iranianCalendar()
        return gregorianDateToJDN(
            gregorianYear,
            3,
            march
        ) + (iranianMonth - 1) * 31 - iranianMonth / 7 * (iranianMonth - 7) + iranianDay - 1
    }

    /**
     * JDNToIranian:
     * Converts the current value of 'JDN' Julian Day Number to a date in the
     * Iranian calendar. The caller should make sure that the current value of
     * 'JDN' is set correctly. This method first converts the JDN to Gregorian
     * calendar and then to Iranian calendar.
     */
    private fun jdnToIranian() {
        jdnToGregorian()
        iranianYear = gregorianYear - 621
        iranianCalendar() // This invocation will update 'leap' and 'march'
        val jdn1F = gregorianDateToJDN(gregorianYear, 3, march)
        var k = jdn - jdn1F
        if (k >= 0) {
            if (k <= 185) {
                iranianMonth = 1 + k / 31
                iranianDay = k % 31 + 1
                return
            } else k -= 186
        } else {
            iranianYear--
            k += 179
            if (leap == 1) k++
        }
        iranianMonth = 7 + k / 30
        iranianDay = k % 30 + 1
    }

    /**
     * JDNToJulian:
     * Calculates Julian calendar dates from the julian day number (JDN) for the
     * period since JDN=-34839655 (i.e. the year -100100 of both calendars) to
     * some millions (10^6) years ahead of the present. The algorithm is based on
     * D.A. Hatcher, Q.Jl.R.Astron.Soc. 25(1984), 53-55 slightly modified by K.M.
     * Borkowski, Post.Astron. 25(1987), 275-279).
     */
    private fun jdnToJulian() {
        val j = 4 * jdn + 139361631
        val i = j % 1461 / 4 * 5 + 308
        julianDay = i % 153 / 5 + 1
        julianMonth = i / 153 % 12 + 1
        julianYear = j / 1461 - 100100 + (8 - julianMonth) / 6
    }

    /**
     * gergorianDateToJDN:
     * Calculates the julian day number (JDN) from Gregorian calendar dates. This
     * integer number corresponds to the noon of the date (i.e. 12 hours of
     * Universal Time). This method was tested to be good (valid) since 1 March,
     * -100100 (of both calendars) up to a few millions (10^6) years into the
     * future. The algorithm is based on D.A.Hatcher, Q.Jl.R.Astron.Soc. 25(1984),
     * 53-55 slightly modified by K.M. Borkowski, Post.Astron. 25(1987), 275-279.
     *
     * @param year  int
     * @param month int
     * @param day   int
     * @return int
     */
    private fun gregorianDateToJDN(year: Int, month: Int, day: Int): Int {
        var jdn =
            (year + (month - 8) / 6 + 100100) * 1461 / 4 + (153 * ((month + 9) % 12) + 2) / 5 + day - 34840408
        jdn = jdn - (year + 100100 + (month - 8) / 6) / 100 * 3 / 4 + 752
        return jdn
    }

    /**
     * JDNToGregorian:
     * Calculates Gregorian calendar dates from the julian day number (JDN) for
     * the period since JDN=-34839655 (i.e. the year -100100 of both calendars) to
     * some millions (10^6) years ahead of the present. The algorithm is based on
     * D.A. Hatcher, Q.Jl.R.Astron.Soc. 25(1984), 53-55 slightly modified by K.M.
     * Borkowski, Post.Astron. 25(1987), 275-279).
     */
    private fun jdnToGregorian() {
        var j = 4 * jdn + 139361631
        j += ((4 * jdn + 183187720) / 146097 * 3 / 4 * 4 - 3908)
        val i = j % 1461 / 4 * 5 + 308
        gregorianDay = i % 153 / 5 + 1
        gregorianMonth = i / 153 % 12 + 1
        gregorianYear = j / 1461 - 100100 + (8 - gregorianMonth) / 6
    }
}