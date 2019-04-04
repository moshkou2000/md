package com.moshkou.md.models;

import java.util.Calendar;


public class CalendarModel extends DatetimeModel {


    public CalendarModel(int day) {
        Calendar calendar = getCalendar();// Calendar.getInstance();
        //calendar.set( getYear(), 0, 1);
        calendar.add(Calendar.DAY_OF_YEAR, day);
        setCalendar(calendar);
    }

//    public CalendarModel(int day) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DATE, day);
//        setCalendar(calendar);
//    }

//    public Calendar getCalendar() { return calendar; }
//    public void setCalendar(Calendar calendar) { this.calendar = calendar; }

}
