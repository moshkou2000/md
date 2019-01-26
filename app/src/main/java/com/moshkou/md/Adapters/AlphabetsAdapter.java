package com.moshkou.md.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.moshkou.md.Configs.Data;
import com.moshkou.md.R;

import java.util.List;

public class AlphabetsAdapter extends BaseAdapter {

    private List<String> alphabets;
    private LayoutInflater layoutInflater;
    private float textSize = 18;

    public AlphabetsAdapter(Context context, List<String> alphabets) {
        this.alphabets = alphabets;
        layoutInflater = LayoutInflater.from(context);
        textSize = (int) (Data.DEVICE_HEIGHT * 1.0 / Data.DEVICE_DENSITY / 27 / 1.646);
    }

    @Override
    public int getCount() {
        return alphabets.size();
    }

    @Override
    public Object getItem(int position) {
        return alphabets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_alphabet, null);

            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.name);
            holder.name.setTextSize(textSize);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(alphabets.get(position));

        return convertView;
    }

    static class ViewHolder {
        TextView name;
    }

}