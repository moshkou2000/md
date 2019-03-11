package com.moshkou.md.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.moshkou.md.R;
import com.moshkou.md.configs.Config;
import com.moshkou.md.models.DatetimeModel;

import java.util.Calendar;


public class CalendarItemFragment extends Fragment {


    private int flag = Calendar.DATE;
    private int year = Config.MIN_YEAR;
    private int month = 0;
    private int day = 1;
    private DatetimeModel data;


    public CalendarItemFragment() {
        // Required empty public constructor
    }

    public void setData(int year, int month, DatetimeModel data, int flag) {
        this.flag = flag;
        this.year = year;
        this.month = month;
        this.data = data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.item_fragment_calendar, container, false);


        final TextView textView1 = view.findViewById(R.id.textView1);

        switch (flag) {
            case Calendar.YEAR:
                textView1.setText("" + year);
                break;
            case Calendar.MONTH:
                textView1.setText(Config.MONTH.get(month));
                break;
            case Calendar.DATE:
                textView1.setText("" + data.getDay());

                final TextView textView2 = view.findViewById(R.id.textView2);
                textView2.setVisibility(View.VISIBLE);
                textView2.setText(data.getWeekDay().substring(0, 2));
                break;
        }

        if (!view.hasOnClickListeners())
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: item click ************************
                    Log.i("XXXXX", "card clicked!!!");
                }
            });

        return view;
    }
}
