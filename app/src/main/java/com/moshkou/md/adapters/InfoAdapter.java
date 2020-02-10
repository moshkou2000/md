package com.moshkou.md.adapters;


import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.moshkou.md.App;
import com.moshkou.md.R;
import com.moshkou.md.helpers.Utils;
import com.moshkou.md.models.BillboardMediaModel;
import com.moshkou.md.models.BillboardModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ItemRowHolder> {


    BillboardModel billboard;


    public InfoAdapter(BillboardModel billboard) {
        this.billboard = billboard;
    }

    public BillboardMediaModel getItem(int position) {
        return billboard.medias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return billboard.medias.size();
    }


    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_info, null);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder itemRowHolder, int i) {
        final BillboardMediaModel item = getItem(i);

//        if (i + 1 == getItemCount()) {
//            itemRowHolder.root.setPadding(0, 0, 0, 20);
//        }

        itemRowHolder.brand.setText("brand");
        itemRowHolder.product.setText("product");

        Utils.setPicasso(item.media, itemRowHolder.image);

        itemRowHolder.button.setOnClickListener(view ->
                Utils.activityPreview(App.getContext(), new String[]{item.media}, i, billboard.name, false));
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected FrameLayout root;
        protected TextView brand;
        protected TextView product;
        protected ImageView image;
        protected FrameLayout button;

        public ItemRowHolder(View view) {
            super(view);

            root = view.findViewById(R.id.root);
            brand = view.findViewById(R.id.brand);
            product = view.findViewById(R.id.product);
            image = view.findViewById(R.id.image);
            button = view.findViewById(R.id.button);
        }
    }

}
