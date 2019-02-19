package com.moshkou.md.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.moshkou.md.models.ContactModel;
import com.moshkou.md.R;

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter extends BaseAdapter {

    private List<ContactModel> data;
    private List<ContactModel> dataTemp;
    private LayoutInflater layoutInflater;

    public ContactsAdapter(Context context, List<ContactModel> data) {
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
            String phone = dataTemp.get(i).phone;
            if (name.toLowerCase().contains(cs.toString()) ||
                    phone.contains(cs.toString()))
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
            convertView = layoutInflater.inflate(R.layout.item_contacts, null);

            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.name);
            holder.phone = convertView.findViewById(R.id.phone);
            holder.image = convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(data.get(position).name);
        holder.phone.setText(data.get(position).phone);
        //holder.image.(listData.get(position).image);

        return convertView;
    }

    static class ViewHolder {
        TextView name;
        TextView phone;
        ImageView image;
    }

}