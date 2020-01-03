package com.moshkou.md.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moshkou.md.R;
import com.moshkou.md.interfaces.OnSearchListener;
import com.moshkou.md.models.BillboardModel;

import java.util.List;


public class AutoCompleteAdapter extends RecyclerView.Adapter<AutoCompleteAdapter.ItemRowHolder> {


    private Context context;
    private LayoutInflater inflater;
    private OnSearchListener searchListener;
    private List<BillboardModel> data;


    public AutoCompleteAdapter(Context context, List<BillboardModel> data, OnSearchListener searchListener) {
        this.data = data;
        this.inflater = LayoutInflater.from(context);
        this.searchListener = searchListener;
    }

    public BillboardModel getItem(int position) {
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
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_auto_complete, null);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder itemRowHolder, int i) {
        final BillboardModel item = getItem(i);

        itemRowHolder.name.setText(item.name);
        itemRowHolder.product.setText(item.product);
        itemRowHolder.address.setText(item.location.address);
        if (!itemRowHolder.itemView.hasOnClickListeners())
            itemRowHolder.itemView.setOnClickListener(view -> searchListener.onSearchInteraction(item));
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView name;
        protected TextView product;
        protected TextView address;

        public ItemRowHolder(View view) {
            super(view);

            name = view.findViewById(R.id.title);
            product = view.findViewById(R.id.image);
            address = view.findViewById(R.id.button);
        }
    }

}