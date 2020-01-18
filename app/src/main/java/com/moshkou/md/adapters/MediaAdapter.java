package com.moshkou.md.adapters;


import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moshkou.md.R;
import com.moshkou.md.models.BaseDataModel;
import com.moshkou.md.models.BillboardMediaModel;
import com.moshkou.md.models.BillboardModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MediaAdapter extends BaseAdapter {


    private Context context;
    private LayoutInflater inflater;
    private BillboardModel selectedBillboard;

    private int numColumns = 2;


    public MediaAdapter(Context context, BillboardModel selectedBillboard) {
        this.selectedBillboard = selectedBillboard;
        this.inflater = LayoutInflater.from(context);//.inflate(R.layout.item_gallery, null);
    }

    @Override
    public int getCount() {
        return selectedBillboard.medias.size();
    }

    public BillboardMediaModel getItem(int position) {
        return selectedBillboard.medias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final BillboardMediaModel item = getItem(position);

        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.item_media, parent, false);
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

        viewHolder.title.setText(item.tags.get(0).key);
        viewHolder.description.setText(item.tags.get(0).value.toString());
        viewHolder.buttonInteresting.setBackgroundResource(
                item.is_interesting ? R.drawable.ic_star : R.drawable.ic_star_border);

        Picasso.get()
                .load(Uri.parse(item.media))
                .placeholder(R.drawable.bg_placeholder_image)
                .error(R.drawable.bg_placeholder_image)
                .into(viewHolder.image);

        viewHolder.buttonInteresting.setOnClickListener(view -> {
            // TODO: item click ************************
        });
        viewHolder.option.setOnClickListener(view -> {
            // TODO: item click ************************
        });

        return convertView;
    }

    public class ViewHolder {

        protected RelativeLayout root;
        protected TextView title;
        protected TextView description;
        protected ImageView image;
        protected Button buttonInteresting;
        protected Button option;

        public ViewHolder(View view) {

            root = (RelativeLayout) view;
            title = view.findViewById(R.id.title);
            description = view.findViewById(R.id.description);
            image = view.findViewById(R.id.image);
            buttonInteresting = view.findViewById(R.id.button_interesting);
            option = view.findViewById(R.id.button);
        }
    }

}
