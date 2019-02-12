package com.moshkou.md.Adapters;


import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moshkou.md.Models.BaseDataModel;
import com.moshkou.md.Models.DateModel;
import com.moshkou.md.R;
import com.squareup.picasso.Picasso;

import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.resolveSize;


public class GalleryAdapter extends BaseAdapter {


    private Context context;
    private LayoutInflater inflater;
    private List<BaseDataModel> data = new ArrayList<>();


    public GalleryAdapter(Context context, List<BaseDataModel> data) {
        this.data = data;
        this.inflater = LayoutInflater.from(context);//.inflate(R.layout.item_gallery, null);
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
            convertView = inflater.inflate(R.layout.item_gallery, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        int bottom = position + 1 == getCount() ? 20 : 0;

        if (position % 2 == 0)
            viewHolder.root.setPadding(20, 20, 10, bottom);
        else
            viewHolder.root.setPadding(10, 20, 20, bottom);

        viewHolder.title.setText(item.getTitle());
        viewHolder.description.setText(item.getDescription());

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

        protected LinearLayout root;
        protected TextView title;
        protected TextView description;
        protected ImageView image;
        protected Button button;

        public ViewHolder(View view) {

            root = (LinearLayout) view;
            title = view.findViewById(R.id.title);
            description = view.findViewById(R.id.description);
            image = view.findViewById(R.id.image);
            button = view.findViewById(R.id.button);
        }
    }

}
