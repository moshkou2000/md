package com.moshkou.md.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moshkou.md.R;
import com.moshkou.md.activities.CalendarActivity;
import com.moshkou.md.configs.Settings;
import com.moshkou.md.models.CalendarModel;

import java.util.ArrayList;
import java.util.List;


public class CalendarWeekAdapter extends PagerAdapter {


    private CalendarActivity activity;

    private LayoutInflater inflater;

    private TextView title1;
    private TextView title2;
    private TextView title3;
    private TextView title4;
    private TextView title5;
    private TextView title6;
    private TextView title7;

    private TextView description1;
    private TextView description2;
    private TextView description3;
    private TextView description4;
    private TextView description5;
    private TextView description6;
    private TextView description7;

    private List<CalendarModel> data = new ArrayList<>();

    private int width = 0;


    public CalendarWeekAdapter(CalendarActivity activity, List<CalendarModel> data) {
        this.data = data;
        this.activity = activity;
        this.inflater = LayoutInflater.from(activity);//.inflate(R.layout.item_gallery, null);

        width = (int) Settings.DEVICE_WIDTH / 7;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_calendar_week, container, false);

        title1 = layout.findViewById(R.id.title1);
        title2 = layout.findViewById(R.id.title2);
        title3 = layout.findViewById(R.id.title3);
        title4 = layout.findViewById(R.id.title4);
        title5 = layout.findViewById(R.id.title5);
        title6 = layout.findViewById(R.id.title6);
        title7 = layout.findViewById(R.id.title7);

        title1.setText(data.get(position * 7).getWeekDayShort());
        title2.setText(data.get(position * 7 + 1).getWeekDayShort());
        title3.setText(data.get(position * 7 + 2).getWeekDayShort());
        title4.setText(data.get(position * 7 + 3).getWeekDayShort());
        title5.setText(data.get(position * 7 + 4).getWeekDayShort());
        title6.setText(data.get(position * 7 + 5).getWeekDayShort());
        title7.setText(data.get(position * 7 + 6).getWeekDayShort());

        description1 = layout.findViewById(R.id.description1);
        description2 = layout.findViewById(R.id.description2);
        description3 = layout.findViewById(R.id.description3);
        description4 = layout.findViewById(R.id.description4);
        description5 = layout.findViewById(R.id.description5);
        description6 = layout.findViewById(R.id.description6);
        description7 = layout.findViewById(R.id.description7);

        description1.setText("" + data.get(position * 7).getDay());
        description2.setText("" + data.get(position * 7 + 1).getDay());
        description3.setText("" + data.get(position * 7 + 2).getDay());
        description4.setText("" + data.get(position * 7 + 3).getDay());
        description5.setText("" + data.get(position * 7 + 4).getDay());
        description6.setText("" + data.get(position * 7 + 5).getDay());
        description7.setText("" + data.get(position * 7 + 6).getDay());

        title1.setTag(data.get(position * 7).getDay());
        title2.setTag(data.get(position * 7 + 1).getDay());
        title3.setTag(data.get(position * 7 + 2).getDay());
        title4.setTag(data.get(position * 7 + 3).getDay());
        title5.setTag(data.get(position * 7 + 4).getDay());
        title6.setTag(data.get(position * 7 + 5).getDay());
        title7.setTag(data.get(position * 7 + 6).getDay());

        description1.setTag(data.get(position * 7).getDay());
        description2.setTag(data.get(position * 7 + 1).getDay());
        description3.setTag(data.get(position * 7 + 2).getDay());
        description4.setTag(data.get(position * 7 + 3).getDay());
        description5.setTag(data.get(position * 7 + 4).getDay());
        description6.setTag(data.get(position * 7 + 5).getDay());
        description7.setTag(data.get(position * 7 + 6).getDay());


        TextView.OnClickListener onClickListener = new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = (String) v.getTag();
                Log.e("AAAAA", "" + str);
                activity.updateSelectedDateDay(Integer.valueOf(str));
            }
        };

        title1.setOnClickListener(onClickListener);
        title2.setOnClickListener(onClickListener);
        title3.setOnClickListener(onClickListener);
        title4.setOnClickListener(onClickListener);
        title5.setOnClickListener(onClickListener);
        title6.setOnClickListener(onClickListener);
        title7.setOnClickListener(onClickListener);

        description1.setOnClickListener(onClickListener);
        description2.setOnClickListener(onClickListener);
        description3.setOnClickListener(onClickListener);
        description4.setOnClickListener(onClickListener);
        description5.setOnClickListener(onClickListener);
        description6.setOnClickListener(onClickListener);
        description7.setOnClickListener(onClickListener);


        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        int count = data.size();
        return count % 7 == 0 ? count : count + 1;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
