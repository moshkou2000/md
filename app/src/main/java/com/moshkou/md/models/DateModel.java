package com.moshkou.md.models;


import com.moshkou.md.configs.Config;

import java.util.Calendar;

public class DateModel {

    private int year;
    private int month;
    private String monthString;
    private String weekDay;
    private int day;

    public DateModel(Calendar calendar) {
        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH);
        this.monthString = Config.MONTH.get(month);
        this.weekDay = Config.WEEKDAY.get(calendar.get(Calendar.DAY_OF_WEEK) - 1);
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    public DateModel(int year, int month, int day, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH);
        this.monthString = Config.MONTH.get(month);
        this.weekDay = Config.WEEKDAY.get(calendar.get(Calendar.DAY_OF_WEEK) - 1);
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    public int getYear() { return this.year; }
    public int getMonth() { return this.month; }
    public String getMonthString() { return monthString; }
    public String getWeekDay() { return weekDay; }
    public int getDay() { return this.day; }

}
