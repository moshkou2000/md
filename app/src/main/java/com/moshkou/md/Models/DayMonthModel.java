package com.moshkou.md.Models;


import com.moshkou.md.Configs.Config;

import java.util.Calendar;

public class DayMonthModel {
    public int day;
    public String month;
    public String weekDay;

    public DayMonthModel() {
        Calendar calendar = Calendar.getInstance();
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.month = Config.MONTH.get(calendar.get(Calendar.MONTH) + 1);
        this.weekDay = Config.WEEKDAY.get(calendar.get(Calendar.DAY_OF_WEEK) - 1);
    }

    public DayMonthModel(Calendar calendar) {
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.month = Config.MONTH.get(calendar.get(Calendar.MONTH) + 1);
        this.weekDay = Config.WEEKDAY.get(calendar.get(Calendar.DAY_OF_WEEK) - 1);
    }

}
