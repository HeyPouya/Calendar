package ir.apptune.calendar.core.utils

import java.util.*

/**
 * The Class THat converts Gregorian calendar to Shamsi calendar
 */
class CalendarTool {
    /**
     * JavaSource_Calendar:
     * The default constructor uses the current Gregorian date to initialize the
     * other private members of the class (Iranian and Julian dates).
     */
    constructor(calendar: GregorianCalendar) {
        setGregorianDate(calendar[Calendar.YEAR],
                calendar[Calendar.MONTH] + 1,
                calendar[Calendar.DAY_OF_MONTH])
    }

    /**
     * JavaSource_Calendar:
     * This constructor receives a Gregorian date and initializes the other private
     * members of the class accordingly.
     *
     * @param year  int
     * @param month int
     * @param day   int
     */
    constructor(year: Int, month: Int, day: Int) {
        setGregorianDate(year, month, day)
    }

    /**
     * getIranianDate:
     * Returns a string version of Iranian date
     *
     * @return String
     */
    private val iranianDate: String
        get() = "$iranianYear/$iranianMonth/$iranianDay"
    val iranianLongDate: Long
        get() {
            val year = iranianYear.toString()
            val month = iranianMonth.toString()
            val day = iranianDay.toString()
            val result = year + month + day
            return java.lang.Long.valueOf(result)
        }

    /**
     * getGregorianDate:
     * Returns a string version of Gregorian date
     *
     * @return String
     */
    private val gregorianDate: String
        get() = "$gregorianYear/$gregorianMonth/$gregorianDay"

    /**
     * getJulianDate:
     * Returns a string version of Julian date
     *
     * @return String
     */
    private val julianDate: String
        get() = "$julianYear/$julianMonth/$julianDay"

    /**
     * getWeekDayStr:
     * Returns the week day name.
     *
     * @return String
     */
    private val weekDayStr: String
        get() {
            val weekDayStr = arrayOf(
                    "دوشنبه",
                    "سه شنبه",
                    "چهارشنبه",
                    "پنج شنبه",
                    "جمعه",
                    "شنبه",
                    "یکشنبه")
            return weekDayStr[dayOfWeek]
        }

    /**
     * toString:
     * Overrides the default toString() method to return all dates.
     *
     * @return String
     */
    override fun toString(): String {
        return weekDayStr +
                ", Gregorian:[" + gregorianDate +
                "], Julian:[" + julianDate +
                "], Iranian:[" + iranianDate + "]"
    }

    /**
     * getDayOfWeek:
     * Returns the week day number. Monday=0..Sunday=6;
     *
     * @return int
     */
    val dayOfWeek: Int
        get() = jdn % 7

    /**
     * nextDay:
     * Go to icNext julian day number (JDN) and adjusts the other dates.
     */
    fun nextDay() {
        jdn++
        jdnToIranian()
        jdnToJulian()
        jdnToGregorian()
    }

    /**
     * nextDay:
     * Overload the nextDay() method to accept the number of days to go ahead and
     * adjusts the other dates accordingly.
     *
     * @param days int
     */
    fun nextDay(days: Int) {
        jdn += days
        jdnToIranian()
        jdnToJulian()
        jdnToGregorian()
    }

    /**
     * previousDay:
     * Go to previous julian day number (JDN) and adjusts the other dates.
     */
    fun previousDay() {
        jdn--
        jdnToIranian()
        jdnToJulian()
        jdnToGregorian()
    }

    /**
     * previousDay:
     * Overload the previousDay() method to accept the number of days to go backward
     * and adjusts the other dates accordingly.
     *
     * @param days int
     */
    fun previousDay(days: Int) {
        jdn -= days
        jdnToIranian()
        jdnToJulian()
        jdnToGregorian()
    }

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
    private fun setGregorianDate(year: Int, month: Int, day: Int) {
        gregorianYear = year
        gregorianMonth = month
        gregorianDay = day
        jdn = gregorianDateToJDN(year, month, day)
        jdnToIranian()
        jdnToJulian()
        jdnToGregorian()
    }

    /**
     * setJulianDate:
     * Sets the date according to the Julian calendar and adjusts the other dates.
     *
     * @param year  int
     * @param month int
     * @param day   int
     */
    fun setJulianDate(year: Int, month: Int, day: Int) {
        julianYear = year
        julianMonth = month
        julianDay = day
        jdn = julianDateToJDN(year, month, day)
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
    private fun iranianCalendar() {
        // Iranian years starting the 33-year rule
        val breaks = intArrayOf(-61, 9, 38, 199, 426, 686, 756, 818, 1111, 1181,
                1210, 1635, 2060, 2097, 2192, 2262, 2324, 2394, 2456, 3178)

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
    fun isLeap(irYear1: Int): Boolean {
        // Iranian years starting the 33-year rule
        val breaks = intArrayOf(-61, 9, 38, 199, 426, 686, 756, 818, 1111, 1181,
                1210, 1635, 2060, 2097, 2192, 2262, 2324, 2394, 2456, 3178)

        var jump: Int
        gregorianYear = irYear1 + 621
        var leapJ = -14
        var jp = breaks[0]
        // Find the limiting years for the Iranian year 'irYear'
        var j = 1
        do {
            val jm = breaks[j]
            jump = jm - jp
            if (irYear1 >= jm) {
                leapJ += jump / 33 * 8 + jump % 33 / 4
                jp = jm
            }
            j++
        } while (j < 20 && irYear1 >= jm)
        var n = irYear1 - jp
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
        return gregorianDateToJDN(gregorianYear, 3, march) + (iranianMonth - 1) * 31 - iranianMonth / 7 * (iranianMonth - 7) + iranianDay - 1
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
     * julianDateToJDN:
     * Calculates the julian day number (JDN) from Julian calendar dates. This
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
    private fun julianDateToJDN(year: Int, month: Int, day: Int): Int {
        return (year + (month - 8) / 6 + 100100) * 1461 / 4 + (153 * ((month + 9) % 12) + 2) / 5 + day - 34840408
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
        var jdn = (year + (month - 8) / 6 + 100100) * 1461 / 4 + (153 * ((month + 9) % 12) + 2) / 5 + day - 34840408
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

    /**
     * getIranianYear:
     * Returns the 'year' part of the Iranian date.
     *
     * @return int
     */
    var iranianYear // Year part of a Iranian date
            = 0
        private set

    /**
     * getIranianMonth:
     * Returns the 'month' part of the Iranian date.
     *
     * @return int
     */
    var iranianMonth // Month part of a Iranian date
            = 0
        private set

    /**
     * getIranianDay:
     * Returns the 'day' part of the Iranian date.
     *
     * @return int
     */
    var iranianDay // Day part of a Iranian date
            = 0
        private set

    /**
     * getGregorianYear:
     * Returns the 'year' part of the Gregorian date.
     *
     * @return int
     */
    var gregorianYear // Year part of a Gregorian date
            = 0
        private set

    /**
     * getGregorianMonth:
     * Returns the 'month' part of the Gregorian date.
     *
     * @return int
     */
    var gregorianMonth // Month part of a Gregorian date
            = 0
        private set

    /**
     * getGregorianDay:
     * Returns the 'day' part of the Gregorian date.
     *
     * @return int
     */
    var gregorianDay // Day part of a Gregorian date
            = 0
        private set

    /**
     * getJulianYear:
     * Returns the 'year' part of the Julian date.
     *
     * @return int
     */
    private var julianYear // Year part of a Julian date
            = 0

    /**
     * getJulianMonth:
     * Returns the 'month' part of the Julian date.
     *
     * @return int
     */
    private var julianMonth // Month part of a Julian date
            = 0

    /**
     * getJulianDay()
     * Returns the 'day' part of the Julian date.
     *
     * @return int
     */
    private var julianDay // Day part of a Julian date
            = 0
    private var leap // Number of years since the last leap year (0 to 4)
            = 0
    private var jdn // Julian Day Number
            = 0
    private var march // The march day of Farvardin the first (First day of jaYear)
            = 0
}
