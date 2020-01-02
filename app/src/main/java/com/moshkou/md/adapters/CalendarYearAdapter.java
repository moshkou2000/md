package com.moshkou.md.adapters;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moshkou.md.R;
import com.moshkou.md.activities.CalendarActivity;
import com.moshkou.md.configs.Settings;

import java.util.ArrayList;
import java.util.List;


public class CalendarYearAdapter extends RecyclerView.Adapter<CalendarYearAdapter.ItemRowHolder> {


    private CalendarActivity activity;
    private LayoutInflater inflater;
    private List<Integer> data = new ArrayList<>();

    private int width = 0;


    public CalendarYearAdapter(CalendarActivity activity, List<Integer> data) {
        this.data = data;
        this.activity = activity;
        this.inflater = LayoutInflater.from(activity);//.inflate(R.layout.item_gallery, null);

        width = (int) Settings.DEVICE_WIDTH / 10;
    }

    public int getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_calendar_year, null);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder itemRowHolder, final int i) {

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        itemRowHolder.root.setLayoutParams(params);

        itemRowHolder.title.setText("" + getItem(i));

        if (!itemRowHolder.title.hasOnClickListeners()) {
            itemRowHolder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.updateSelectedDateYear(getItem(i));
                }
            });
        }
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected RelativeLayout root;
        protected TextView title;


        public ItemRowHolder(View view) {
            super(view);

            root = view.findViewById(R.id.root);
            title = view.findViewById(R.id.title);

        }
    }

}
