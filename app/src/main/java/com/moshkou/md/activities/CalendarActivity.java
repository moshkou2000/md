package com.moshkou.md.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.moshkou.md.R;
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
    private ViewPager viewDate;

    private Button buttonJAN;
    private Button buttonFEB;
    private Button buttonMAR;
    private Button buttonAPR;
    private Button buttonMAY;
    private Button buttonJUN;
    private Button buttonJUL;
    private Button buttonAUG;
    private Button buttonSEP;
    private Button buttonOCT;
    private Button buttonNOV;
    private Button buttonDEC;

    private CalendarYearAdapter yearAdapter;
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


        // TODO: just for test
        for (int i = Config.MIN_YEAR; i <= 2037; i++) {
            years.add(i);
        }
        for (int i = 0; i < 365; i++) {
            days.add(new CalendarModel(i));
        }


        viewYear = findViewById(R.id.pagerYear);
        viewDate = findViewById(R.id.pagerDate);

        yearAdapter = new CalendarYearAdapter(this, years);
        weekAdaptor = new CalendarWeekAdapter(this, days);

        viewYear.setAdapter(yearAdapter);
        viewDate.setAdapter(weekAdaptor);

        viewDate.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int i) { }

            @Override
            public void onPageScrolled(int i, float v, int i1) { }

            @Override
            public void onPageScrollStateChanged(int i) { }
        });

        intButtons();
    }

    private void intButtons() {

        buttonJAN = findViewById(R.id.buttonJAN);
        buttonFEB = findViewById(R.id.buttonFEB);
        buttonMAR = findViewById(R.id.buttonMAR);
        buttonAPR = findViewById(R.id.buttonAPR);
        buttonMAY = findViewById(R.id.buttonMAY);
        buttonJUN = findViewById(R.id.buttonJUN);
        buttonJUL = findViewById(R.id.buttonJUL);
        buttonAUG = findViewById(R.id.buttonAUG);
        buttonSEP = findViewById(R.id.buttonSEP);
        buttonOCT = findViewById(R.id.buttonOCT);
        buttonNOV = findViewById(R.id.buttonNOV);
        buttonDEC = findViewById(R.id.buttonDEC);

        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSelectedDateMonth(Integer.valueOf((String) v.getTag()));
            }
        };

        buttonJAN.setOnClickListener(onClickListener);
        buttonFEB.setOnClickListener(onClickListener);
        buttonMAR.setOnClickListener(onClickListener);
        buttonAPR.setOnClickListener(onClickListener);
        buttonMAY.setOnClickListener(onClickListener);
        buttonJUN.setOnClickListener(onClickListener);
        buttonJUL.setOnClickListener(onClickListener);
        buttonAUG.setOnClickListener(onClickListener);
        buttonSEP.setOnClickListener(onClickListener);
        buttonOCT.setOnClickListener(onClickListener);
        buttonNOV.setOnClickListener(onClickListener);
        buttonDEC.setOnClickListener(onClickListener);
    }

    public void updateSelectedDateYear(int year) {
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
