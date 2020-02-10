package com.moshkou.md.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moshkou.md.R;
import com.moshkou.md.interfaces.OnSearchListener;
import com.moshkou.md.models.BillboardModel;

import java.util.ArrayList;
import java.util.List;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ItemRowHolder> {


    private static String TAG = "SEARCH_ADP";


    private OnSearchListener searchListener;
    private List<BillboardModel> data;
    private final List<BillboardModel> dataTemp = new ArrayList<>();


    public SearchAdapter (List<BillboardModel> data, OnSearchListener searchListener) {
        this.data = data;
        dataTemp.addAll(data);
        this.searchListener = searchListener;
    }

    public void search(String keyword) {
        data.clear();

        keyword = keyword.toLowerCase();
        for (int i = 0; i < dataTemp.size(); i++) {
            String name = dataTemp.get(i).name;
            String product = dataTemp.get(i).advertiser;
            String address = dataTemp.get(i).location.address;
            if (name.toLowerCase().contains(keyword) ||
                    product.contains(keyword) ||
                    address.contains(keyword))
                data.add(dataTemp.get(i));
        }

        notifyDataSetChanged();
    }

    public void restore() {
        data.clear();
        data.addAll(dataTemp);

        notifyDataSetChanged();
    }

    public BillboardModel getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        searchListener.onSearchHasResult(data.size() == 0);

        return data.size();
    }


    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_search, null);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder itemRowHolder, int i) {
        BillboardModel item = getItem(i);
        itemRowHolder.name.setText(item.name);
        itemRowHolder.product.setText(item.advertiser);
        itemRowHolder.address.setText(item.location.address);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemRowHolder.itemView.setLayoutParams(lp);
        itemRowHolder.itemView.setOnClickListener(view -> searchListener.onSearchInteraction(item));
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

         TextView name;
         TextView product;
         TextView address;

        public ItemRowHolder(View view) {
            super(view);

            name = view.findViewById(R.id.name);
            product = view.findViewById(R.id.product);
            address = view.findViewById(R.id.address);
        }
    }

}