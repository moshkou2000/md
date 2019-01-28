package com.moshkou.md.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moshkou.md.Models.DatetimeModel;
import com.moshkou.md.R;

import java.util.ArrayList;
import java.util.List;

public class DayMonthAdapter extends RecyclerView.Adapter<DayMonthAdapter.ItemRowHolder> {

    private OnDataChangedListener onDataChangedListener;
    public interface OnDataChangedListener {
        void onDataChanged(boolean success, int addedSize);
    }
    private static final OnDataChangedListener NO_OP_CHANGE_LISTENER = new OnDataChangedListener() {
        public void onDataChanged(boolean success, int addedSize) { }
    };
    public void setOnDataChangedListener(OnDataChangedListener onDataChangedListener) {
        this.onDataChangedListener = onDataChangedListener;
    }


    private final Context context;
    private final List<DatetimeModel> dayMonthData = new ArrayList<>();


    public DayMonthAdapter(Context context) {
        this.context = context;

        setOnDataChangedListener(NO_OP_CHANGE_LISTENER);

        addDayMonth();
    }

    public void addDayMonth() {
        for (int i = 0; i < 11; i++)
            dayMonthData.add(new DatetimeModel());
        notifyDataSetChanged();
        onDataChangedListener.onDataChanged(true, 11);
    }


    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_daymonth, null);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT));
//        v.setLayoutParams(params);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder itemRowHolder, int i) {
        final DatetimeModel item = getItem(i);

        itemRowHolder.day.setText(String.valueOf(item.getDay()));
        itemRowHolder.month.setText(item.getMonthString());
        itemRowHolder.weekday.setText(item.getWeekDay());
    }

    public DatetimeModel getItem(int position) {
        return dayMonthData.get(position);
    }

    @Override
    public int getItemCount() {
        return dayMonthData.size();
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView day;
        protected TextView month;
        protected TextView weekday;

        public ItemRowHolder(View view) {
            super(view);

            day = view.findViewById(R.id.day);
            month = view.findViewById(R.id.month);
            weekday = view.findViewById(R.id.weekday);
        }
    }
}
