package ir.apptune.calendar.models;

/**
 * Data model of dates, to pass to the Adapter to Create the gridView.
 */

public class DateModel {

    private String day;
    private String month;
    private String year;
    private String dayofWeek;
    private String gDay; //Gregorian Day
    private String gMonth;


    private String gYear;
    private Boolean isToday;

    String getgMonth() {
        return gMonth;
    }

    public void setgMonth(String gMonth) {
        this.gMonth = gMonth;
    }

    String getgYear() {
        return gYear;
    }

    public void setgYear(String gYear) {
        this.gYear = gYear;
    }


    public String getgDay() {
        return gDay;
    }

    public void setgDay(String gDay) {
        this.gDay = gDay;
    }


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDayofWeek() {
        return dayofWeek;
    }

    public void setDayofWeek(String dayofWeek) {
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

    public DateModel() {

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

