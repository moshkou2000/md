package com.moshkou.md.adapters;


import android.content.Context;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moshkou.md.helpers.Utils;
import com.moshkou.md.models.BaseDataModel;
import com.moshkou.md.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ItemRowHolder> {


    private Context context;
    private LayoutInflater inflater;
    private List<BaseDataModel> data = new ArrayList<>();


    public PlaylistAdapter(Context context, List<BaseDataModel> data) {
        this.data = data;
        this.inflater = LayoutInflater.from(context);//.inflate(R.layout.item_gallery, null);
    }

    public BaseDataModel getItem(int position) {
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
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_playlist, null);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder itemRowHolder, int i) {
        final BaseDataModel item = getItem(i);

        int right = i + 1 == getItemCount() ? 20 : 0;
        itemRowHolder.root.setPadding(20, 20, right, 20);

        itemRowHolder.title.setText(item.getTitle());
        itemRowHolder.description.setText(item.getDescription());

        Utils.setPicasso(item.getImage(), itemRowHolder.image);


        itemRowHolder.button.setOnClickListener(view -> {
            // TODO: item click ************************
        });

    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected LinearLayout root;
        protected TextView title;
        protected TextView description;
        protected ImageView image;
        protected Button button;

        public ItemRowHolder(View view) {
            super(view);

            root = (LinearLayout) view;
            title = view.findViewById(R.id.title);
            description = view.findViewById(R.id.description);
            image = view.findViewById(R.id.image);
            button = view.findViewById(R.id.button);
        }
    }

}
