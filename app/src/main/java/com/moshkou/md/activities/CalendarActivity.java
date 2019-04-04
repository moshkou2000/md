package com.moshkou.md.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.moshkou.md.R;
import com.moshkou.md.adapters.CalendarMonthAdapter;
import com.moshkou.md.adapters.CalendarWeekAdapter;
import com.moshkou.md.adapters.CalendarYearAdapter;
import com.moshkou.md.configs.Config;
import com.moshkou.md.models.CalendarModel;

import java.util.ArrayList;
import java.util.List;

public class CalendarActivity extends FragmentActivity {



    private List<Integer> years = new ArrayList<>();
    private List<CalendarModel> days = new ArrayList<>();

    private TextView toolbarTitle;
    private RecyclerView viewYear;
    private ViewPager viewMonth;
    private ViewPager viewDate;

    private CalendarYearAdapter yearAdapter;
    private CalendarMonthAdapter monthAdaptor;
    private CalendarWeekAdapter weekAdaptor;

    private CalendarModel selectedDate = new CalendarModel(0);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(selectedDate.getDateString());

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        for (int i = Config.MIN_YEAR; i <= 2037; i++) {
            years.add(i);
        }
        for (int i = 0; i < 365; i++) {
            days.add(new CalendarModel(i));
        }


        viewYear = findViewById(R.id.pagerYear);
        viewMonth = findViewById(R.id.pagerMonth);
        viewDate = findViewById(R.id.pagerDate);

        yearAdapter = new CalendarYearAdapter(this, years);
        monthAdaptor = new CalendarMonthAdapter(this, Config.MONTH);
        weekAdaptor = new CalendarWeekAdapter(this, days);

        viewYear.setAdapter(yearAdapter);
        viewMonth.setAdapter(monthAdaptor);
        viewDate.setAdapter(weekAdaptor);

        viewMonth.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int i) { }

            @Override
            public void onPageScrolled(int i, float v, int i1) { }

            @Override
            public void onPageScrollStateChanged(int i) { }
        });
        viewDate.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int i) { }

            @Override
            public void onPageScrolled(int i, float v, int i1) { }

            @Override
            public void onPageScrollStateChanged(int i) { }
        });

    }


    public void updateSelectedDateYear(int year) {
        Log.e("YYYYY", "" + year);
        selectedDate.setYear(year);
        toolbarTitle.setText(selectedDate.getDateString());
    }

    public void updateSelectedDateMonth(int month) {
        selectedDate.setMonth(month);
        toolbarTitle.setText(selectedDate.getDateString());
    }

    public void updateSelectedDateDay(int day) {
        selectedDate.setDay(day);
        toolbarTitle.setText(selectedDate.getDateString());
    }

}
