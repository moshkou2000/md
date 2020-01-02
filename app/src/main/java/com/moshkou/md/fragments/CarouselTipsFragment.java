package com.moshkou.md.fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moshkou.md.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarouselTipsFragment extends Fragment {


    public CarouselTipsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_carousel_tips, container, false);
        return rootView;
    }

}
