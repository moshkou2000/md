package com.moshkou.md.adapters;


import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moshkou.md.R;
import com.moshkou.md.models.BaseDataModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ItemRowHolder> {


    private Context context;
    private LayoutInflater inflater;
    private List<BaseDataModel> data = new ArrayList<>();


    public GroupAdapter(Context context, List<BaseDataModel> data) {
        this.data = data;
        this.context = context;
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
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_group, null);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder itemRowHolder, int i) {
        final BaseDataModel item = getItem(i);

        if (i + 1 == getItemCount()) {
            itemRowHolder.root.setPadding(0, 0, 0, 20);
        }

        itemRowHolder.title.setText(item.getTitle());

        if (!itemRowHolder.more.hasOnClickListeners())
            itemRowHolder.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: item click ************************
                    Log.i("XXXXX", "card clicked!!!");
                }
            });

        GroupItemAdapter groupItemAdapter = new GroupItemAdapter(context, item.getChildren());
        itemRowHolder.recyclerView.setAdapter(groupItemAdapter);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected LinearLayout root;
        protected TextView title;
        protected Button more;
        protected RecyclerView recyclerView;


        public ItemRowHolder(View view) {
            super(view);

            root = view.findViewById(R.id.root);
            title = view.findViewById(R.id.title);
            more = view.findViewById(R.id.more);
            recyclerView = view.findViewById(R.id.recyclerView);
        }
    }

}
