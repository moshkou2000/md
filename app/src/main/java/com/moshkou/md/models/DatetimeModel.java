package com.moshkou.md.models;


import com.moshkou.md.configs.Config;

import java.util.Calendar;

public class DatetimeModel {

    private Calendar calendar;
    private int year;
    private int month;
    private String monthString;
    private String weekDay;
    private int day;
    private int hour;
    private int minute;

    public DatetimeModel() {
        this.calendar = Calendar.getInstance();
        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH);
        this.monthString = Config.MONTH.get(month);
        this.weekDay = Config.WEEKDAY.get(calendar.get(Calendar.DAY_OF_WEEK) - 1);
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.hour = calendar.get(Calendar.HOUR_OF_DAY);
        this.minute = calendar.get(Calendar.MINUTE);
    }

    public DatetimeModel(Calendar calendar) {
        this.calendar = calendar;
        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH);
        this.monthString = Config.MONTH.get(month);
        this.weekDay = Config.WEEKDAY.get(calendar.get(Calendar.DAY_OF_WEEK) - 1);
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.hour = calendar.get(Calendar.HOUR_OF_DAY);
        this.minute = calendar.get(Calendar.MINUTE);
    }


    public void setCalendar(int year, int month, int day, int hour, int minute) {
        this.calendar.set(year, month, day, hour, minute);
        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH);
        this.monthString = Config.MONTH.get(month);
        this.weekDay = Config.WEEKDAY.get(calendar.get(Calendar.DAY_OF_WEEK) - 1);
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.hour = calendar.get(Calendar.HOUR_OF_DAY);
        this.minute = calendar.get(Calendar.MINUTE);
    }

    public void setYear(int year) {
        this.year = year;
        this.calendar.set(year, month, day, hour, minute);
    }

    public void setMonth(int month) {
        this.month = month;
        this.calendar.set(year, month, day, hour, minute);
    }

    public void setDay(int day) {
        this.day = day;
        this.calendar.set(year, month, day, hour, minute);
    }

    public void setHour(int hour) {
        this.hour = hour;
        this.calendar.set(year, month, day, hour, minute);
    }

    public void setMinute(int minute) {
        this.minute = minute;
        this.calendar.set(year, month, day, hour, minute);
    }


    public Calendar getCalendar() { return calendar; }
    public int getYear() { return calendar.get(Calendar.YEAR); }
    public int getMonth() { return calendar.get(Calendar.MONTH); }
    public String getMonthString() { return Config.MONTH.get(getMonth()); }
    public String getWeekDay() { return Config.WEEKDAY.get(calendar.get(Calendar.DAY_OF_WEEK) - 1); }
    public int getDay() { return calendar.get(Calendar.DAY_OF_MONTH); }
    public int getHour() { return calendar.get(Calendar.HOUR_OF_DAY); }
    public int getMinute() { return calendar.get(Calendar.MINUTE); }

}
