package com.moshkou.md.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moshkou.md.R;
import com.moshkou.md.models.KeyValue;

import java.util.List;


public class BillboardItemAdapter extends RecyclerView.Adapter<BillboardItemAdapter.ItemRowHolder> {


    private static String TAG = "BILLBOARD_ITEM_ADP";


    private List<KeyValue> data;


    public BillboardItemAdapter(List<KeyValue> data) {
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

    public KeyValue getItem(int index) {
        return data.get(index);
    }


    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.item_billboard_item, viewGroup, false);

        v.setLayoutParams(new RecyclerView.LayoutParams(
                ((RecyclerView) viewGroup).getLayoutManager().getWidth(),
                ViewGroup.LayoutParams.WRAP_CONTENT));

        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder itemRowHolder, int i) {
        final KeyValue item = getItem(i);

        itemRowHolder.textViewBrand.setText(item.key);
        itemRowHolder.textViewProduct.setText(item.value.toString());
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView textViewBrand;
        protected TextView textViewProduct;


        public ItemRowHolder(View view) {
            super(view);

            textViewBrand = view.findViewById(R.id.text_view_brand);
            textViewProduct = view.findViewById(R.id.text_view_product);
        }
    }

}
