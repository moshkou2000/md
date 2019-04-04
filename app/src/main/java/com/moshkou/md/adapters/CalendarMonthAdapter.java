package com.moshkou.md.adapters;


import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moshkou.md.R;
import com.moshkou.md.activities.CalendarActivity;
import com.moshkou.md.configs.Settings;

import java.util.ArrayList;
import java.util.List;


public class CalendarMonthAdapter extends PagerAdapter {


    private CalendarActivity activity;

    private LayoutInflater inflater;

    private TextView title1;
    private TextView title2;
    private TextView title3;
    private TextView title4;
    private TextView title5;
    private TextView title6;

    private List<String> data = new ArrayList<>();

    private int width = 0;


    public CalendarMonthAdapter(CalendarActivity activity, List<String> data) {
        this.data = data;
        this.activity = activity;
        this.inflater = LayoutInflater.from(activity);//.inflate(R.layout.item_gallery, null);

        width = (int) Settings.DEVICE_WIDTH / 6;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_calendar_month, container, false);

        title1 = layout.findViewById(R.id.title1);
        title2 = layout.findViewById(R.id.title2);
        title3 = layout.findViewById(R.id.title3);
        title4 = layout.findViewById(R.id.title4);
        title5 = layout.findViewById(R.id.title5);
        title6 = layout.findViewById(R.id.title6);

        Log.e("AAAAA", "position " + position);

        position = position > 1 ? 1 : position;

        title1.setText(data.get(position * 6));
        title2.setText(data.get(position * 6 + 1));
        title3.setText(data.get(position * 6 + 2));
        title4.setText(data.get(position * 6 + 3));
        title5.setText(data.get(position * 6 + 4));
        title6.setText(data.get(position * 6 + 5));

        title1.setTag(position * 6);
        title2.setTag(position * 6 + 1);
        title3.setTag(position * 6 + 2);
        title4.setTag(position * 6 + 3);
        title5.setTag(position * 6 + 4);
        title6.setTag(position * 6 + 5);


        TextView.OnClickListener onClickListener = new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer i = (Integer) v.getTag();
                activity.updateSelectedDateMonth(i);
            }
        };

        title1.setOnClickListener(onClickListener);
        title2.setOnClickListener(onClickListener);
        title3.setOnClickListener(onClickListener);
        title4.setOnClickListener(onClickListener);
        title5.setOnClickListener(onClickListener);
        title6.setOnClickListener(onClickListener);


        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
