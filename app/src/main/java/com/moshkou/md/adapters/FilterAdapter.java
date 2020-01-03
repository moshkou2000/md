package com.moshkou.md.adapters;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moshkou.md.R;
import com.moshkou.md.configs.Keys;
import com.moshkou.md.configs.Settings;
import com.moshkou.md.interfaces.OnFilterListener;
import com.moshkou.md.models.KeyValue;

import java.util.ArrayList;
import java.util.List;


public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ItemRowHolder> {


    private static String TAG = "FILTER_ADP";


    private Context context;
    private LayoutInflater inflater;
    private OnFilterListener filterListener;
    private List<KeyValue> data;


    public FilterAdapter(Context context, OnFilterListener filterListener) {
        this.inflater = LayoutInflater.from(context);
        this.filterListener = filterListener;
        setItems();
    }

    public void setItems() {
        data = new ArrayList<>();

        if (!Settings.FILTER_ITEMS.location.state.isEmpty())
            data.add(new KeyValue(Keys.STATE, Settings.FILTER_ITEMS.location.state));
        if (!Settings.FILTER_ITEMS.location.city.isEmpty())
            data.add(new KeyValue(Keys.CITY, Settings.FILTER_ITEMS.location.city));
        if (!Settings.FILTER_ITEMS.media_owner.isEmpty())
            data.add(new KeyValue(Keys.MEDIA_OWNER, Settings.FILTER_ITEMS.media_owner));
        if (!Settings.FILTER_ITEMS.format.isEmpty())
            data.add(new KeyValue(Keys.FORMAT, Settings.FILTER_ITEMS.format));
        if (!Settings.FILTER_ITEMS.advertiser.isEmpty())
            data.add(new KeyValue(Keys.ADVERTISER, Settings.FILTER_ITEMS.advertiser));

        notifyDataSetChanged();
    }

    public KeyValue getItem(int position) {
        KeyValue item = data.get(position);

        if (item.key.equals(Keys.STATE))
            Settings.FILTER_ITEMS.location.state = item.value.toString();
        if (item.key.equals(Keys.CITY))
            Settings.FILTER_ITEMS.location.city = item.value.toString();
        if (item.key.equals(Keys.MEDIA_OWNER))
            Settings.FILTER_ITEMS.media_owner = item.value.toString();
        if (item.key.equals(Keys.FORMAT))
            Settings.FILTER_ITEMS.format = item.value.toString();
        if (item.key.equals(Keys.ADVERTISER))
            Settings.FILTER_ITEMS.advertiser = item.value.toString();

        return item;
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
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_filter, null);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder itemRowHolder, int i) {
        final KeyValue item = getItem(i);

        itemRowHolder.filter.setText(item.value.toString());
        if (!itemRowHolder.filter.hasOnClickListeners())
            itemRowHolder.filter.setOnClickListener(view -> {
                Log.i(TAG, item.key + ", " + item.value);

                data.remove(item);
                filterListener.onFilterInteraction();
                notifyDataSetChanged();
            });

    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected Button filter;

        public ItemRowHolder(View view) {
            super(view);

            filter = view.findViewById(R.id.filter);
        }
    }

}
