package com.moshkou.md.fragments;

import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.moshkou.md.R;
import com.moshkou.md.helpers.Utils;
import com.moshkou.md.models.BaseDataModel;
import com.squareup.picasso.Picasso;


public class CarouselsItemFragment extends Fragment {


    private BaseDataModel data;

    public CarouselsItemFragment() {
        // Required empty public constructor
    }

    public void setData(BaseDataModel data) {
        this.data = data;
        Log.i("QQQQQ", "setData");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("QQQQQ", "onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.item_fragment_carousels, container, false);

        Log.i("QQQQQ", "onCreateView: " + data.getTitle() + " ________");
        TextView title = view.findViewById(R.id.title);
        TextView description = view.findViewById(R.id.description);
        ImageView image = view.findViewById(R.id.image);
        Button button = view.findViewById(R.id.button);

        title.setText(data.getTitle());
        description.setText(data.getDescription());

        Utils.setPicasso(data.getImage(), image);


        if (!button.hasOnClickListeners())
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: item click ************************
                    Log.i("XXXXX", "card clicked!!!");
                }
            });

        return view;
    }
}
