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


//
//package com.moshkou.md.adapters;
//
//
//        import android.content.Context;
//        import android.util.Log;
//        import android.view.LayoutInflater;
//        import android.view.View;
//        import android.view.ViewGroup;
//        import android.widget.ArrayAdapter;
//        import android.widget.Filter;
//        import android.widget.TextView;
//
//        import androidx.annotation.NonNull;
//
//        import com.moshkou.md.R;
//
//        import java.util.ArrayList;
//        import java.util.List;
//
//
//public class AutoCompleteAdapter extends ArrayAdapter {
//
//    private static String TAG = "AC_ADP";
//
//    private List<String> dataList;
//    private Context mContext;
//    private int itemLayout;
//
//    private ListFilter listFilter = new ListFilter();
//    private List<String> dataListAllItems;
//
//
//
//    public AutoCompleteAdapter(Context context, int resource, List<String> storeDataLst) {
//        super(context, resource, storeDataLst);
//
//        dataList = storeDataLst;
//        mContext = context;
//        itemLayout = resource;
//    }
//
//    @Override
//    public int getCount() {
//        return dataList.size();
//    }
//
//    @Override
//    public String getItem(int position) {
//        Log.d(TAG, dataList.get(position));
//
//        return dataList.get(position);
//    }
//
//    @Override
//    public View getView(int position, View view, @NonNull ViewGroup parent) {
//        ViewHolder holder;
//        if (view == null) {
//            view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
//            holder = new ViewHolder();
//            holder.name = view.findViewById(R.id.name);
//            view.setTag(holder);
//        } else {
//            holder = (ViewHolder) view.getTag();
//        }
//
//        holder.name.setText(getItem(position));
//
//        return view;
//    }
//
//    @NonNull
//    @Override
//    public Filter getFilter() {
//        return listFilter;
//    }
//
//    public class ListFilter extends Filter {
//        private Object lock = new Object();
//
//        @Override
//        protected FilterResults performFiltering(CharSequence prefix) {
//            FilterResults results = new FilterResults();
//            if (dataListAllItems == null) {
//                synchronized (lock) {
//                    dataListAllItems = new ArrayList<>(dataList);
//                }
//            }
//
//            if (prefix == null || prefix.length() == 0) {
//                synchronized (lock) {
//                    results.values = dataListAllItems;
//                    results.count = dataListAllItems.size();
//                }
//            } else {
//                final String searchStrLowerCase = prefix.toString().toLowerCase().trim();
//
//                ArrayList<String> matchValues = new ArrayList<>();
//
//                for (String dataItem : dataListAllItems) {
//                    if (dataItem.toLowerCase().contains(searchStrLowerCase)) {
//                        matchValues.add(dataItem);
//                    }
//                }
//
//                results.values = matchValues;
//                results.count = matchValues.size();
//            }
//
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            Log.i(TAG, "0.result.size: " + results.count);
//            Log.i(TAG, "0.result.values: " + results.values);
//
//            dataList.clear();
//            dataList.addAll((List) results.values);
//            notifyDataSetChanged();
//        }
//
//    }
//
//    static class ViewHolder {
//        TextView name;
//    }
//}