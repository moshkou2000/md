package com.moshkou.md.fragments;

import android.net.Uri;
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
import com.squareup.picasso.Picasso;


public class InfoItemFragment extends Fragment {


    private BillboardMediaModel data;

    public InfoItemFragment() {
        // Required empty public constructor
    }

    public void setData(BillboardMediaModel data) {
        this.data = data;
    }

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

        brand.setText("brand");
        product.setText("product");

        Utils.setPicasso(data.media, image);

        button.setOnClickListener(v -> Utils.activityPreview(App.getContext(), data.media, "", false));

        return view;
    }
}
