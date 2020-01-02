package com.moshkou.md.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.moshkou.md.adapters.CardsAdapter;
import com.moshkou.md.adapters.PlaylistAdapter;
import com.moshkou.md.adapters.SpeakersAdapter;
import com.moshkou.md.adapters.TopicsAdapter;
import com.moshkou.md.configs.Enumerates;
import com.moshkou.md.helpers.Utils;
import com.moshkou.md.models.BaseDataModel;
import com.moshkou.md.R;

import java.util.ArrayList;
import java.util.List;


public class HorizontalListsFragment extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private com.moshkou.md.fragments.HorizontalListsFragment.OnFragmentInteractionListener mListener;

    private androidx.leanback.widget.HorizontalGridView gridView;
    private androidx.leanback.widget.HorizontalGridView gridView2;
    private androidx.leanback.widget.HorizontalGridView gridView3;
    private androidx.leanback.widget.HorizontalGridView gridView4;

    private List<BaseDataModel> data = new ArrayList<>();
    private TopicsAdapter tAdapter;
    private PlaylistAdapter pAdapter;
    private CardsAdapter cAdapter;
    private SpeakersAdapter sAdapter;

    public HorizontalListsFragment() {
        // Required empty public constructor
    }

    public static com.moshkou.md.fragments.HorizontalListsFragment newInstance(String param1, String param2) {
        com.moshkou.md.fragments.HorizontalListsFragment fragment = new com.moshkou.md.fragments.HorizontalListsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        // TODO: just for test
        for (int i = 0; i < 11; i++) {
            data.add(new BaseDataModel("" + i * 234, "title " + i, "description asdasda sdasd asd das dasd as dads ads " + i, "location " + i, "12.02.2022", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTV-XL-dvaC-cblm2rqA7am9z2-54v_mGnaLDokhZQqEJbXFduZng", null, null));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_horizontal_lists, container, false);

        gridView = view.findViewById(R.id.gridView);
        gridView2 = view.findViewById(R.id.gridView2);
        gridView3 = view.findViewById(R.id.gridView3);
        gridView4 = view.findViewById(R.id.gridView4);

        tAdapter = new TopicsAdapter(getActivity(), data);
        pAdapter = new PlaylistAdapter(getActivity(), data);
        cAdapter = new CardsAdapter(getActivity(), data);
        sAdapter = new SpeakersAdapter(getActivity(), data);

        gridView.setAdapter(tAdapter);
        gridView2.setAdapter(pAdapter);
        gridView3.setAdapter(cAdapter);
        gridView4.setAdapter(sAdapter);

        Toolbar toolbar = view.findViewById(R.id.appBar);
        toolbar.inflateMenu(R.menu.profile);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action1:
                        Utils.toast(getContext(), Enumerates.Message.ERROR, "1", Toast.LENGTH_LONG);
                        return true;
                    case R.id.action2:
                        Utils.toast(getContext(), Enumerates.Message.ERROR, "22", Toast.LENGTH_LONG);
                        return true;
                    case R.id.actionMore:
                        Utils.toast(getContext(), Enumerates.Message.ERROR, "333", Toast.LENGTH_LONG);
                        return true;
                    default:
                        return false;
                }
            }
        });

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof com.moshkou.md.fragments.HorizontalListsFragment.OnFragmentInteractionListener) {
            mListener = (com.moshkou.md.fragments.HorizontalListsFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}