package com.moshkou.md.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.moshkou.md.App;
import com.moshkou.md.R;
import com.moshkou.md.helpers.Utils;
import com.moshkou.md.interfaces.OnAdapterListener;

import java.util.List;


public class StatusAdapter extends BaseAdapter {


    private static String TAG = "STATUS_ADP";


    private LayoutInflater inflater;
    private List<String> files;
    private String selectedItem;
    private int selectedPosition;

    private OnAdapterListener onAlertListener;



    public StatusAdapter(OnAdapterListener onAlertListener, List<String> files) {
        this.files = files;
        this.onAlertListener = onAlertListener;
        this.inflater = LayoutInflater.from(App.getContext());
    }



    @Override
    public int getCount() {
        return files.size();
    }

    public void setItem(List<String> files) {
        this.files = files;
        notifyDataSetChanged();
    }

    public String getItem(int position) {
        return files.get(position);
    }

    public void clearItems() {
        files.clear();
        files = null;
        notifyDataSetChanged();
    }

    public void removeItem(int index) {
        this.selectedPosition = index;
        this.selectedItem = getItem(index);
        this.files.remove(index);
        notifyDataSetChanged();
    }

    public void restoreItem() {
        this.files.add(selectedPosition, selectedItem);
        selectedPosition = -1;
        selectedItem = null;
        notifyDataSetChanged();
    }

    public void toggleItem(int index, View view) {
        Animation animation = new AlphaAnimation(0, 1);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(2000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                removeItem(index);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
        view.startAnimation(animation);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String item = getItem(position);

        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.item_status, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        Utils.setPicasso(item, viewHolder.image);

        viewHolder.image.setOnClickListener(view ->
                Utils.activityPreview(App.getContext(), item, "", false));
        viewHolder.button.setOnClickListener(view -> {
            toggleItem(position, viewHolder.root);
            onAlertListener.onDeleteMedia(position);
        });

        return convertView;
    }

    public class ViewHolder {

        protected FrameLayout root;
        protected ImageView image;
        protected Button button;

        public ViewHolder(View view) {

            root = (FrameLayout) view;
            image = view.findViewById(R.id.image);
            button = view.findViewById(R.id.button_delete);
        }
    }

}
