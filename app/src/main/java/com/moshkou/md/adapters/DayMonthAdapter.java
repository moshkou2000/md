package com.moshkou.md.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moshkou.md.models.DateModel;
import com.moshkou.md.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class DayMonthAdapter extends RecyclerView.Adapter<DayMonthAdapter.ItemRowHolder> {


    private OnDataChangedListener onDataChangedListener;
    public interface OnDataChangedListener {
        void onDataChanged(boolean flag, int addedSize, Calendar calendar);
    }
    private static final OnDataChangedListener NO_OP_CHANGE_LISTENER = new OnDataChangedListener() {
        public void onDataChanged(boolean flag, int addedSize, Calendar calendar) { }
    };
    public void setOnDataChangedListener(OnDataChangedListener onDataChangedListener) {
        this.onDataChangedListener = onDataChangedListener;
    }


    private OnClickListener onClickListener;
    public interface OnClickListener {
        void onClick(int position);
    }
    private static final OnClickListener NO_OP_CLICK_LISTENER = new OnClickListener() {
        public void onClick(int position) { }
    };
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    private Context context;
    private List<DateModel> data = new ArrayList<>();
    public static final int RANGE = 20;


    public DayMonthAdapter(Context context) {
        this.context = context;

        setOnDataChangedListener(NO_OP_CHANGE_LISTENER);
        setOnClickListener(NO_OP_CLICK_LISTENER);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1 * RANGE / 2);

        for (int i = 0; i < RANGE; i++) {
            calendar.add(Calendar.DATE, 1);
            data.add(new DateModel(calendar));
        }
    }

    public void addDayMonth(boolean scrollUp, int firstVisiblePosition) {

        Calendar calendar = Calendar.getInstance();
        DateModel dm = getItem(firstVisiblePosition);
        calendar.set(dm.getYear(), dm.getMonth(), dm.getDay());

        if (scrollUp) {
            calendar.add(Calendar.DATE, 1);
            for (int i = 0; i < RANGE; i++) {
                calendar.add(Calendar.DATE, 1);
                data.add(new DateModel(calendar));
            }
        } else {
            for (int i = 0; i < RANGE; i++) {
                calendar.add(Calendar.DATE, -1);

                // TODO: check this part
                data.add(0, new DateModel(calendar));
            }
        }

        notifyDataSetChanged();
        //onDataChangedListener.onDataChanged(true, RANGE - 3, calendar);

    }

    public void setValue(final Calendar calendar) {
        data.clear();

        calendar.add(Calendar.DATE, -1 * RANGE / 2);

        for (int i = 0; i < RANGE; i++) {
            calendar.add(Calendar.DATE, 1);
            data.add(new DateModel(calendar));
        }

        notifyDataSetChanged();
        onDataChangedListener.onDataChanged(false, RANGE / 2 - 2, calendar);
    }

    public DateModel getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_daymonth, null);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder itemRowHolder, final int i) {
        final DateModel item = getItem(i);

        itemRowHolder.day.setText(String.valueOf(item.getDay()));
        itemRowHolder.month.setText(item.getMonthString());
        itemRowHolder.weekday.setText(item.getWeekDay());

        if (!itemRowHolder.itemView.hasOnClickListeners())
            itemRowHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onClick(i);
                }
            });
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
