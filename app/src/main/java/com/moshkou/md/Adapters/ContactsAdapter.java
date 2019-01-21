package com.moshkou.md.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.moshkou.md.Models.ContactModel;
import com.moshkou.md.R;

import java.util.List;

public class ContactsAdapter extends BaseAdapter {

    private List<ContactModel> listData;
    private LayoutInflater layoutInflater;

    public ContactsAdapter(Context context, List<ContactModel> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
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

        holder.name.setText(listData.get(position).name);
        holder.phone.setText(listData.get(position).phone);
        //holder.image.(listData.get(position).image);

        return convertView;
    }

    static class ViewHolder {
        TextView name;
        TextView phone;
        ImageView image;
    }

}