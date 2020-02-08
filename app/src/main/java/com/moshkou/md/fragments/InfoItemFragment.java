package com.moshkou.md.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.moshkou.md.App;
import com.moshkou.md.R;
import com.moshkou.md.helpers.Utils;
import com.moshkou.md.models.BillboardMediaModel;


public class InfoItemFragment extends Fragment {


    private BillboardMediaModel media;

    public InfoItemFragment() { }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_info, container, false);

        TextView brand = view.findViewById(R.id.brand);
        TextView product = view.findViewById(R.id.product);
        ImageView image = view.findViewById(R.id.image);
        Button button = view.findViewById(R.id.button);

        brand.setText(media.tags.get(0).key);
        product.setText(media.tags.get(0).value.toString());

        Utils.setPicasso(media.media, image);

        button.setOnClickListener(v -> Utils.activityPreview(App.getContext(), new String[]{media.media}, "", false));

        return view;
    }




    public void setData(BillboardMediaModel data) {
        this.media = data;
    }
}
