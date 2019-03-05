package com.moshkou.md.adapters;


import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moshkou.md.models.BaseDataModel;
import com.moshkou.md.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class GalleryAdapter extends BaseAdapter {


    private Context context;
    private LayoutInflater inflater;
    private List<BaseDataModel> data = new ArrayList<>();

    private int numColumns = 2;


    public GalleryAdapter(Context context, List<BaseDataModel> data) {
        this.data = data;
        this.inflater = LayoutInflater.from(context);//.inflate(R.layout.item_gallery, null);
    }

    public void setNumColumns(int numColumns) {
        this.numColumns = numColumns;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    public BaseDataModel getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final BaseDataModel item = getItem(position);

        ViewHolder viewHolder = null;
        if(convertView == null){
            if (numColumns == 1) {
                convertView = inflater.inflate(R.layout.item2_gallery, parent, false);
            } else
                convertView = inflater.inflate(R.layout.item_gallery, parent, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        // padding: 20
        // half padding: 10
        //
        int bottom = position + 1 == getCount() ? 20 : 0;

        // 1..2 < more columns
        //
        int padding = ((numColumns - 2) * 2 + 2) * 10;
        int middlePadding = 10;

        if (padding == 0) {
            padding = 20;
        } else if (padding == 20) {
            padding = 10;
        } else {
            middlePadding = padding / numColumns;
            padding = middlePadding - 10;
        }

        int p = (position + 1) % numColumns;
        if (p == 1)
            viewHolder.root.setPadding(20, 20, padding, bottom);     // first column
        else if (p == 0)
            viewHolder.root.setPadding(padding, 20, 20, bottom);     // last column
        else
            viewHolder.root.setPadding(middlePadding, 20, middlePadding, bottom);     // middle

        viewHolder.title.setText(item.getTitle());
        viewHolder.description.setText(item.getDescription());

        if (numColumns == 1)
            viewHolder.location.setText(item.getLocation());

        Picasso.get()
                .load(Uri.parse(item.getImage()))
                .placeholder(R.drawable.bg_placeholder_image)
                .error(R.drawable.bg_placeholder_image)
                .into(viewHolder.image);

        if (!viewHolder.button.hasOnClickListeners())
            viewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: item click ************************
                }
            });

        return convertView;
    }

    public class ViewHolder {

        protected RelativeLayout root;
        protected TextView title;
        protected TextView description;
        protected TextView location;
        protected ImageView image;
        protected Button button;

        public ViewHolder(View view) {

            root = (RelativeLayout) view;
            title = view.findViewById(R.id.title);
            description = view.findViewById(R.id.description);
            location = view.findViewById(R.id.location);
            image = view.findViewById(R.id.image);
            button = view.findViewById(R.id.button);
        }
    }

}
