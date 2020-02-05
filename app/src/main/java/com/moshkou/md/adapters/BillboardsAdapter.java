package com.moshkou.md.adapters;


import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moshkou.md.App;
import com.moshkou.md.R;
import com.moshkou.md.configs.Flags;
import com.moshkou.md.helpers.Utils;
import com.moshkou.md.interfaces.OnBillboardListener;
import com.moshkou.md.models.BillboardModel;
import com.squareup.picasso.Picasso;

import java.util.List;


public class BillboardsAdapter extends RecyclerView.Adapter<BillboardsAdapter.ItemRowHolder> {


    private static String TAG = "BILLBOARD_ADP";


    private OnBillboardListener billboardListener;
    private List<BillboardModel> data;


    public BillboardsAdapter(List<BillboardModel> data, OnBillboardListener billboardListener) {
        this.billboardListener = billboardListener;
        this.data = data;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public BillboardModel getItem(int index) {
        return data.get(index);
    }


    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.item_billboard, null);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder itemRowHolder, int i) {
        final BillboardModel item = getItem(i);

        String url = item.getMedias().get(0);

        Utils.setPicasso(url, itemRowHolder.image);

        itemRowHolder.imageFlag.setImageResource(
                item.type.equals(Flags.STATIC) ?
                        R.drawable.ic_billboard_static :
                        R.drawable.ic_billboard_digital);

        itemRowHolder.textViewName.setText(item.name);
        itemRowHolder.textViewTitle.setText(item.media_owner);
        itemRowHolder.textViewLastUpdate.setText(Utils.humanizerDateTime(item.updated_at));
        itemRowHolder.button.setOnClickListener(view ->
                Utils.activityPreview(App.getContext(), url, item.name, false));
        itemRowHolder.imageFlag.setOnClickListener(view ->
                billboardListener.onBillboardInteraction(item));

        if (item.getKeyValues().size() > 1 && itemRowHolder.buttonMore.getVisibility() != View.VISIBLE)
            itemRowHolder.buttonMore.setVisibility(View.VISIBLE);
        else if (itemRowHolder.buttonMore.getVisibility() != View.GONE)
            itemRowHolder.buttonMore.setVisibility(View.GONE);

        BillboardItemAdapter adapter = new BillboardItemAdapter(item.getKeyValues());
        itemRowHolder.recyclerViewAdvertisers.setAdapter(adapter);

        itemRowHolder.buttonMore.setOnClickListener(view -> {
            LinearLayout.LayoutParams params;

            if (itemRowHolder.buttonMore.getText().equals(App.getContext().getString(R.string.action_less))) {
                itemRowHolder.buttonMore.setText(R.string.action_more);
                params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        itemRowHolder.recyclerViewAdvertisers.getMinimumHeight());

            } else {
                itemRowHolder.buttonMore.setText(R.string.action_less);
                params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
            }
            itemRowHolder.recyclerViewAdvertisers.setLayoutParams(params);
        });
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected Button button;
        protected ImageView image;
        protected Button buttonMore;
        protected ImageView imageFlag;
        protected TextView textViewName;
        protected TextView textViewTitle;
        protected TextView textViewLastUpdate;
        protected RecyclerView recyclerViewAdvertisers;


        public ItemRowHolder(View view) {
            super(view);

            button = view.findViewById(R.id.button);
            image = view.findViewById(R.id.image);
            buttonMore = view.findViewById(R.id.button_more);
            imageFlag = view.findViewById(R.id.image_flag);
            textViewName = view.findViewById(R.id.text_view_name);
            textViewTitle = view.findViewById(R.id.text_view_title);
            textViewLastUpdate = view.findViewById(R.id.text_view_last_update);
            recyclerViewAdvertisers = view.findViewById(R.id.recycler_view_advertisers);
        }
    }

}
