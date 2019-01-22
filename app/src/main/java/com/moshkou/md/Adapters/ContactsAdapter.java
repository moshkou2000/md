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

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter extends BaseAdapter {

    private List<ContactModel> contacts;
    private List<ContactModel> contactsTemp;
    private LayoutInflater layoutInflater;

    public ContactsAdapter(Context context, List<ContactModel> contacts) {
        this.contacts = contacts;
        layoutInflater = LayoutInflater.from(context);
    }

    public void filter(CharSequence cs) {
        if (contactsTemp == null) {
            contactsTemp = new ArrayList<>();
            contactsTemp.addAll(contacts);
        }
        contacts.clear();

        cs = cs.toString().toLowerCase();
        for (int i = 0; i < contactsTemp.size(); i++) {
            String name = contactsTemp.get(i).name;
            String phone = contactsTemp.get(i).phone;
            if (name.toLowerCase().contains(cs.toString()) ||
                    phone.contains(cs.toString()))
                contacts.add(contactsTemp.get(i));
        }

        notifyDataSetChanged();
    }

    public void restore() {
        contacts.clear();
        contacts.addAll(contactsTemp);
        contactsTemp.clear();
        contactsTemp = null;

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return contacts.get(position);
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

        holder.name.setText(contacts.get(position).name);
        holder.phone.setText(contacts.get(position).phone);
        //holder.image.(listData.get(position).image);

        return convertView;
    }

    static class ViewHolder {
        TextView name;
        TextView phone;
        ImageView image;
    }

}