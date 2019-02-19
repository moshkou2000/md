package com.moshkou.md.adapters;


import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

        itemRowHolder.title.setText(item.getTitle());
        itemRowHolder.description.setText(item.getDescription());

        Picasso.get()
                .load(Uri.parse(item.getImage()))
                .placeholder(R.drawable.bg_placeholder_image)
                .error(R.drawable.bg_placeholder_image)
                .into(itemRowHolder.image);

        if (!itemRowHolder.button.hasOnClickListeners())
            itemRowHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: item click ************************
            }
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

            root = view.findViewById(R.id.root);
            title = view.findViewById(R.id.title);
            description = view.findViewById(R.id.description);
            image = view.findViewById(R.id.image);
            button = view.findViewById(R.id.button);
        }
    }

}
