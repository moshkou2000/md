package com.moshkou.md.adapters;


import android.content.Context;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moshkou.md.models.BaseDataModel;
import com.moshkou.md.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ListMultiTypeItemAdapter extends RecyclerView.Adapter<ListMultiTypeItemAdapter.ItemRowHolder> {


    private Context context;
    private LayoutInflater inflater;
    private List<BaseDataModel> data = new ArrayList<>();


    public ListMultiTypeItemAdapter(Context context, List<BaseDataModel> data) {
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
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_multi_type_item, null);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder itemRowHolder, int i) {
        final BaseDataModel item = getItem(i);

        if (i + 1 == getItemCount()) {
            itemRowHolder.root.setPadding(0, 0, 0, 20);
        }

        if (item.getChild() == null) {
            itemRowHolder.cardView1.setVisibility(View.VISIBLE);
            itemRowHolder.cardView2.setVisibility(View.GONE);
            itemRowHolder.title1.setText(item.getTitle());
            itemRowHolder.description1.setText(item.getDescription());
            itemRowHolder.time1.setText("44:44");

            Picasso.get()
                    .load(Uri.parse(item.getImage()))
                    .placeholder(R.drawable.bg_placeholder_image)
                    .error(R.drawable.bg_placeholder_image)
                    .into(itemRowHolder.image1);

            itemRowHolder.extra1.bringToFront();
            itemRowHolder.extra1.setOnClickListener(view -> {
                // TODO: item click ************************
            });

            itemRowHolder.button1.setOnClickListener(view -> {
                // TODO: item click ************************
            });

        } else {
            itemRowHolder.cardView1.setVisibility(View.GONE);
            itemRowHolder.cardView2.setVisibility(View.VISIBLE);
            itemRowHolder.title2.setText(item.getTitle());
            itemRowHolder.description2.setText(item.getDescription());

            Picasso.get()
                    .load(Uri.parse(item.getImage()))
                    .placeholder(R.drawable.bg_placeholder_image)
                    .error(R.drawable.bg_placeholder_image)
                    .into(itemRowHolder.image2);

            itemRowHolder.button2.setOnClickListener(view -> {
                // TODO: item click ************************
            });
        }

    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected FrameLayout root;

        protected CardView cardView1;
        protected TextView title1;
        protected TextView description1;

        protected Button option1;
        protected RelativeLayout extra1;

        protected TextView time1;
        protected ImageView image1;
        protected FrameLayout button1;

        protected CardView cardView2;
        protected TextView title2;
        protected TextView description2;
        protected ImageView image2;
        protected Button button2;

        public ItemRowHolder(View view) {
            super(view);

            root = view.findViewById(R.id.root);

            cardView1 = view.findViewById(R.id.cardView1);
            title1 = view.findViewById(R.id.title1);
            description1 = view.findViewById(R.id.description1);

            option1 = view.findViewById(R.id.option1);
            extra1 = view.findViewById(R.id.extra1);

            time1 = view.findViewById(R.id.time1);
            image1 = view.findViewById(R.id.image1);
            button1 = view.findViewById(R.id.button1);

            cardView2 = view.findViewById(R.id.cardView2);
            title2 = view.findViewById(R.id.title2);
            description2 = view.findViewById(R.id.description2);
            image2 = view.findViewById(R.id.image2);
            button2 = view.findViewById(R.id.button2);
        }
    }

}
