package com.moshkou.md.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.moshkou.md.R;
import com.moshkou.md.models.ContactModel;
import com.moshkou.md.models.OriginDestinationModel;

import java.util.ArrayList;
import java.util.List;

public class OriginDestinationAdapter extends BaseAdapter {

    private List<OriginDestinationModel> data;
    private List<OriginDestinationModel> dataTemp;
    private LayoutInflater layoutInflater;

    public OriginDestinationAdapter(Context context, List<OriginDestinationModel> data) {
        this.data = data;
        layoutInflater = LayoutInflater.from(context);
    }

    public void filter(CharSequence cs) {
        if (dataTemp == null) {
            dataTemp = new ArrayList<>();
            dataTemp.addAll(data);
        }
        data.clear();

        cs = cs.toString().toLowerCase();
        for (int i = 0; i < dataTemp.size(); i++) {
            String name = dataTemp.get(i).name;
            String code = dataTemp.get(i).code;
            if (name.toLowerCase().contains(cs.toString()) ||
                    code.contains(cs.toString()))
                data.add(dataTemp.get(i));
        }

        notifyDataSetChanged();
    }

    public void restore() {
        data.clear();
        data.addAll(dataTemp);
        dataTemp.clear();
        dataTemp = null;

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_booking_list, null);

            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.title);
            holder.code = convertView.findViewById(R.id.description);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(data.get(position).name);
        holder.code.setText(data.get(position).code);

        return convertView;
    }

    static class ViewHolder {
        TextView name;
        TextView code;
    }

}