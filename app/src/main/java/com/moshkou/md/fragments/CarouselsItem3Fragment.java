package com.moshkou.md.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.moshkou.md.R;
import com.moshkou.md.models.BaseDataModel;
import com.squareup.picasso.Picasso;


public class CarouselsItem3Fragment extends Fragment {


    private BaseDataModel data;

    public CarouselsItem3Fragment() {
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
        View view = inflater.inflate(R.layout.item3_fragment_carousels, container, false);

        Log.i("QQQQQ", "onCreateView: " + data.getTitle() + " ________");
        final TextView title = view.findViewById(R.id.title);
        final TextView description = view.findViewById(R.id.description);
        final ImageView image = view.findViewById(R.id.image);
        final Button button = view.findViewById(R.id.button);
        final ImageButton plus = view.findViewById(R.id.imageButtonPlus);
        final ImageButton minus = view.findViewById(R.id.imageButtonMinus);
        final TextView editText = view.findViewById(R.id.editText);
        editText.setText("254534565");

        title.setText(data.getTitle());
        description.setText(data.getDescription());

        Picasso.get()
                .load(Uri.parse(data.getImage()))
                .placeholder(R.drawable.bg_placeholder_image)
                .error(R.drawable.bg_placeholder_image)
                .into(image);

        if (!button.hasOnClickListeners())
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: item click ************************
                    Log.i("XXXXX", "card clicked!!!");
                }
            });

        if (!plus.hasOnClickListeners())
            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int current = Integer.valueOf(data.get_id());
                    data.set_id(String.valueOf(current + 1));
                    editText.setText(data.get_id());
                }
            });

        if (!minus.hasOnClickListeners())
            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int current = Integer.valueOf(data.get_id());
                    if (current > 0) {
                        data.set_id(String.valueOf(current - 1));
                        editText.setText(data.get_id());
                    }
                }
            });

        return view;
    }
}
