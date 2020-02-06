package com.moshkou.md.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.moshkou.md.App;
import com.moshkou.md.R;
import com.moshkou.md.helpers.Utils;
import com.moshkou.md.interfaces.OnAdapterListener;
import com.moshkou.md.models.BillboardMediaModel;

import java.util.List;


public class MediaAdapter extends BaseAdapter {

    private static String TAG = "MEDIA_ADT";


    private LayoutInflater inflater;
    private List<BillboardMediaModel> medias;
    private BillboardMediaModel selectedItem;
    private int selectedPosition;

    private OnAdapterListener onAlertListener;


    public MediaAdapter(OnAdapterListener onAlertListener, List<BillboardMediaModel> medias) {
        this.medias = medias;
        this.onAlertListener = onAlertListener;
        this.inflater = LayoutInflater.from(App.getContext());
    }



    @Override
    public int getCount() {
        return medias.size();
    }

    public void setItem(List<BillboardMediaModel> medias) {
        this.medias = medias;
        notifyDataSetChanged();
    }

    public BillboardMediaModel getItem(int position) {
        return medias.get(position);
    }

    public void clearItems() {
        medias.clear();
        medias = null;
        notifyDataSetChanged();
    }

    public void removeItem(int index) {
        this.selectedPosition = index;
        this.selectedItem = getItem(index);
        this.medias.remove(index);
        notifyDataSetChanged();
    }

    public void restoreItem() {
        this.medias.add(selectedPosition, selectedItem);
        selectedPosition = -1;
        selectedItem = null;
        notifyDataSetChanged();
    }

    public void toggleItem(final int index, View view) {
        Animation animation = new AlphaAnimation(0, 1);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(1400);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                removeItem(index);
                onAlertListener.onDeleteMedia(index);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
        view.startAnimation(animation);
    }

    public void toggleInteresting(final int index, boolean isChecked, View view) {
        view.setBackgroundResource(isChecked ? R.drawable.ic_star : R.drawable.ic_star_border);
        onAlertListener.onInteresting(index, isChecked);
    }

    @Override
    public long getItemId(int position) {
        return position;
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

        viewHolder.title.setText(item.tags.get(0).key);
        viewHolder.description.setText(item.tags.get(0).value.toString());
        viewHolder.checkboxInteresting.setBackgroundResource(
                item.is_interesting ? R.drawable.ic_star : R.drawable.ic_star_border);

        Utils.setPicasso(item.media, viewHolder.image);

        viewHolder.image.setOnClickListener(view -> onAlertListener.onUpdate(position));
        viewHolder.checkboxInteresting.setOnClickListener(view ->
                toggleInteresting(position, viewHolder.checkboxInteresting.isChecked(), view));
        viewHolder.button.setOnClickListener(view -> toggleItem(position, viewHolder.root));

        return convertView;
    }

    public class ViewHolder {

        protected FrameLayout root;
        protected TextView title;
        protected TextView description;
        protected ImageView image;
        protected CheckBox checkboxInteresting;
        protected Button button;

        public ViewHolder(View view) {

            root = (FrameLayout) view;
            title = view.findViewById(R.id.title);
            description = view.findViewById(R.id.description);
            image = view.findViewById(R.id.image);
            checkboxInteresting = view.findViewById(R.id.checkbox_interesting);
            button = view.findViewById(R.id.button_delete);
        }
    }

}
