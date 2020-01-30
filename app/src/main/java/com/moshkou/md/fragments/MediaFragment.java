package com.moshkou.md.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moshkou.md.App;
import com.moshkou.md.R;
import com.moshkou.md.activities.MainActivity;
import com.moshkou.md.activities.MediaActivity;
import com.moshkou.md.adapters.GalleryAdapter;
import com.moshkou.md.adapters.MediaAdapter;
import com.moshkou.md.configs.Flags;
import com.moshkou.md.configs.Keys;
import com.moshkou.md.interfaces.OnFragmentInteractionListener;
import com.moshkou.md.models.BillboardMediaModel;
import com.moshkou.md.models.BillboardModel;

import java.lang.reflect.Type;
import java.util.List;


public class MediaFragment extends Fragment {


    private static String TAG = "MEDIA_FRG";

    private OnFragmentInteractionListener mListener;

    private MediaAdapter adapter;

    private GridView gridViewMedia;
    private Button buttonAdd;

    private List<BillboardMediaModel> medias;
    private boolean isInitialized = false;



    public MediaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media, container, false);

        gridViewMedia = view.findViewById(R.id.grid_view_media);
        buttonAdd = view.findViewById(R.id.button_add);

        init();
        populate();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setSelectedBillboard(List<BillboardMediaModel> medias) {
        this.medias = medias;

        populate();
    }

    private void init() {
        isInitialized = true;

        buttonAdd.setOnClickListener(v -> {
            Intent i = new Intent(App.getContext(), MediaActivity.class);
            Type type = new TypeToken<List<BillboardMediaModel>>(){}.getType();
            i.putExtra(Keys.DATA, new Gson().toJson(medias, type));
            startActivity(i);
        });
    }

    private void populate() {
        if (isInitialized) {
            if (medias == null) {
                if (adapter != null)
                    adapter.clearItems();

            } else {
                if (adapter == null) {
                    adapter = new MediaAdapter(getActivity(), medias);
                    gridViewMedia.setAdapter(adapter);

                } else {
                    adapter.setItem(medias);
                }
            }
        }
    }
}
