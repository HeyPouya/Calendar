package ir.apptune.calendar;

/**
 * Created by Pouya on 17/11/2016.
 */

class DateModel {

    String day;
    String month;
    String year;
    String dayofWeek;
    String gDay;

    public String getgMonth() {
        return gMonth;
    }

    public void setgMonth(String gMonth) {
        this.gMonth = gMonth;
    }

    public String getgYear() {
        return gYear;
    }

    public void setgYear(String gYear) {
        this.gYear = gYear;
    }

    String gMonth;
    String gYear;
    Boolean isToday;


    public String getgDay() {
        return gDay;
    }

    public void setgDay(String gDay) {
        this.gDay = gDay;
    }


    String getDay() {
        return day;
    }

    void setDay(String day) {
        this.day = day;
    }

    String getMonth() {
        return month;
    }

    void setMonth(String month) {
        this.month = month;
    }

    String getYear() {
        return year;
    }

    void setYear(String year) {
        this.year = year;
    }

    String getDayofWeek() {
        return dayofWeek;
    }

    void setDayofWeek(String dayofWeek) {
        this.dayofWeek = dayofWeek;
    }

    @Override
    public String toString() {
        return "DateModel{" +
                "day='" + day + '\'' +
                ", month='" + month + '\'' +
                ", year='" + year + '\'' +
                ", dayofWeek='" + dayofWeek + '\'' +
                ", gDay='" + gDay + '\'' +
                '}';
    }

    DateModel() {

    }

    public Boolean getToday() {
        return isToday;
    }

    public void setToday(Boolean today) {
        isToday = today;
    }

    public DateModel(String day, String month, String year, String dayofWeek, String gDay, String gMonth, String gYear, Boolean isToday) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.dayofWeek = dayofWeek;
        this.gDay = gDay;
        this.gMonth = gMonth;
        this.gYear = gYear;
        this.isToday = isToday;
    }
}

