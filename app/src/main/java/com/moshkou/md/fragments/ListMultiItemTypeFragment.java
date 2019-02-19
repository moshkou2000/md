package com.moshkou.md.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.moshkou.md.adapters.ListMultiTypeItemAdapter;
import com.moshkou.md.configs.Enumerates;
import com.moshkou.md.helpers.Utils;
import com.moshkou.md.models.BaseDataModel;
import com.moshkou.md.models.ContactModel;
import com.moshkou.md.R;

import java.util.ArrayList;
import java.util.List;


public class ListMultiItemTypeFragment extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;

    private List<BaseDataModel> data = new ArrayList<>();
    private ListMultiTypeItemAdapter adapter;

    public ListMultiItemTypeFragment() {
        // Required empty public constructor
    }

    public static ListMultiItemTypeFragment newInstance(String param1, String param2) {
        ListMultiItemTypeFragment fragment = new ListMultiItemTypeFragment();
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
            data.add(new BaseDataModel("" + i * 234, "title " + i, "description asdasda sdasd asd das dasd as dads ads " + i, "location " + i, "12.02.2022", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTV-XL-dvaC-cblm2rqA7am9z2-54v_mGnaLDokhZQqEJbXFduZng", null));
        }
        data.get(1).setContact(new ContactModel("Hassan", "+60172552542", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTV-XL-dvaC-cblm2rqA7am9z2-54v_mGnaLDokhZQqEJbXFduZng"));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_multi_item_type, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        adapter = new ListMultiTypeItemAdapter(getActivity(), data);

        recyclerView.setAdapter(adapter);

        Toolbar toolbar = view.findViewById(R.id.appBar);
        toolbar.inflateMenu(R.menu.profile);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action1:
                        Utils.toast(getContext(), Enumerates.Message.ERROR, "1");
                        return true;
                    case R.id.action2:
                        Utils.toast(getContext(), Enumerates.Message.ERROR, "22");
                        return true;
                    case R.id.actionMore:
                        Utils.toast(getContext(), Enumerates.Message.ERROR, "333");
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
