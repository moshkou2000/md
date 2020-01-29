package com.moshkou.md.adapters;


import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moshkou.md.App;
import com.moshkou.md.R;
import com.moshkou.md.helpers.Utils;
import com.moshkou.md.models.BaseDataModel;
import com.moshkou.md.models.BillboardMediaModel;
import com.moshkou.md.models.BillboardModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MediaAdapter extends BaseAdapter {

    private static String TAG = "MEDIA_ADT";


    private Context context;
    private LayoutInflater inflater;
    private BillboardModel billboard;

    private static int NO_COLUMNS = 2;
    private static int PADDING = 2;
    private static int HALF_PADDING = PADDING / NO_COLUMNS;


    public MediaAdapter(Context context, BillboardModel billboard) {
        this.billboard = billboard;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return billboard.medias.size();
    }

    public BillboardMediaModel getItem(int position) {
        return billboard.medias.get(position);
    }

    public void clearItems() {
        billboard = null;
        notifyDataSetChanged();
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

        int bottom = position + 1 == getCount() ? PADDING : 0;

        // 1..2 < more columns
        //
        int padding = ((NO_COLUMNS - 2) * 2 + 2) * HALF_PADDING;
        int middlePadding = HALF_PADDING;

        if (padding == 0) {
            padding = PADDING;
        } else if (padding == PADDING) {
            padding = HALF_PADDING;
        } else {
            middlePadding = padding / NO_COLUMNS;
            padding = middlePadding - HALF_PADDING;
        }

        int p = (position + 1) % NO_COLUMNS;
        if (p == 1)
            viewHolder.root.setPadding(PADDING, PADDING, padding, bottom);     // first column
        else if (p == 0)
            viewHolder.root.setPadding(padding, PADDING, PADDING, bottom);     // last column
        else
            viewHolder.root.setPadding(middlePadding, PADDING, middlePadding, bottom);     // middle

        viewHolder.title.setText(item.tags.get(0).key);
        viewHolder.description.setText(item.tags.get(0).value.toString());
        viewHolder.buttonInteresting.setBackgroundResource(
                item.is_interesting ? R.drawable.ic_star : R.drawable.ic_star_border);

        Picasso.get()
                .load(Uri.parse(item.media))
                .placeholder(R.drawable.bg_placeholder_image)
                .error(R.drawable.bg_placeholder_image)
                .into(viewHolder.image);

        viewHolder.image.setOnClickListener(view ->
                Utils.activityPreview(App.getContext(), item.media, billboard.name, false));
        viewHolder.buttonInteresting.setOnClickListener(view -> {
            // TODO: item click ************************
            Log.i(TAG, "interesting click");
        });
        viewHolder.option.setOnClickListener(view -> {
            // TODO: item click ************************
            Log.i(TAG, "option click");
        });

        return convertView;
    }

    public class ViewHolder {

        protected FrameLayout root;
        protected TextView title;
        protected TextView description;
        protected ImageView image;
        protected Button buttonInteresting;
        protected Button option;

        public ViewHolder(View view) {

            root = (FrameLayout) view;
            title = view.findViewById(R.id.title);
            description = view.findViewById(R.id.description);
            image = view.findViewById(R.id.image);
            buttonInteresting = view.findViewById(R.id.button_interesting);
            option = view.findViewById(R.id.option);
        }
    }

}
