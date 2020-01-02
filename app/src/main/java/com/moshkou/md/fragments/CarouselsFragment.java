package com.moshkou.md.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.moshkou.md.R;
import com.moshkou.md.configs.Enumerates;
import com.moshkou.md.helpers.Utils;
import com.moshkou.md.models.BaseDataModel;

import java.util.ArrayList;
import java.util.List;


public class CarouselsFragment extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private CarouselsFragment.OnFragmentInteractionListener mListener;

    private TabLayout dots1;

    private ViewPager pager1;
    private ViewPager pager2;
    private ViewPager pager3;
    private ViewPager pager4;

    private PagerAdapter pagerAdapter1;
    private PagerAdapter pagerAdapter2;
    private PagerAdapter pagerAdapter3;
    private PagerAdapter pagerAdapter4;

    private List<Fragment> pages1 = new ArrayList<>();
    private List<Fragment> pages2 = new ArrayList<>();
    private List<Fragment> pages3 = new ArrayList<>();
    private List<Fragment> pages4 = new ArrayList<>();

    private List<BaseDataModel> data = new ArrayList<>();

    public CarouselsFragment() {
        // Required empty public constructor
    }

    public static CarouselsFragment newInstance(String param1, String param2) {
        CarouselsFragment fragment = new CarouselsFragment();
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
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_carousels, container, false);

        pages1.clear();
        pages2.clear();
        pages3.clear();
        pages4.clear();

        // TODO: just for test
        //
        for (int i = 0; i < 11; i++) {
            BaseDataModel b = new BaseDataModel("" + i * 17, "title " + i, "description asdasda sdasd asd das dasd as dads ads " + i, "location " + i, "12.02.2022", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTV-XL-dvaC-cblm2rqA7am9z2-54v_mGnaLDokhZQqEJbXFduZng", null, null);
            data.add(b);

            CarouselsItemFragment carouselsItem = new CarouselsItemFragment();
            carouselsItem.setData(b);

            CarouselsItem2Fragment carouselsItem2 = new CarouselsItem2Fragment();
            carouselsItem2.setData(b);

            CarouselsItem3Fragment carouselsItem3 = new CarouselsItem3Fragment();
            carouselsItem3.setData(b);

            CarouselsItem4Fragment carouselsItem4 = new CarouselsItem4Fragment();
            carouselsItem4.setData(b);

            pages1.add(carouselsItem);
            pages2.add(carouselsItem2);
            pages3.add(carouselsItem3);
            pages4.add(carouselsItem4);
        }

        dots1 = view.findViewById(R.id.dots1);
        pager1 = view.findViewById(R.id.pager1);
        pager2 = view.findViewById(R.id.pager2);
        pager3 = view.findViewById(R.id.pager3);
        pager4 = view.findViewById(R.id.pager4);

        // Instantiate a ViewPager and a PagerAdapter.
        pagerAdapter1 = new ScreenSlidePagerAdapter1(getActivity().getSupportFragmentManager());
        pager1.setAdapter(pagerAdapter1);
        dots1.setupWithViewPager(pager1, true);
        pager1.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int i) { }

            @Override
            public void onPageScrolled(int i, float v, int i1) { }

            @Override
            public void onPageScrollStateChanged(int i) { }
        });


        pagerAdapter2 = new ScreenSlidePagerAdapter2(getActivity().getSupportFragmentManager());
        pager2.setAdapter(pagerAdapter2);
        pager2.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int i) { }

            @Override
            public void onPageScrolled(int i, float v, int i1) { }

            @Override
            public void onPageScrollStateChanged(int i) { }
        });


        pagerAdapter3 = new ScreenSlidePagerAdapter3(getActivity().getSupportFragmentManager());
        pager3.setAdapter(pagerAdapter3);
        pager3.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int i) { }

            @Override
            public void onPageScrolled(int i, float v, int i1) { }

            @Override
            public void onPageScrollStateChanged(int i) { }
        });


        pagerAdapter4 = new ScreenSlidePagerAdapter4(getActivity().getSupportFragmentManager());
        pager4.setAdapter(pagerAdapter4);
        pager4.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int i) { }

            @Override
            public void onPageScrolled(int i, float v, int i1) { }

            @Override
            public void onPageScrollStateChanged(int i) { }
        });



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
        if (context instanceof CarouselsFragment.OnFragmentInteractionListener) {
            mListener = (CarouselsFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        Log.i("QQQQQ", "onDetach");
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private class ScreenSlidePagerAdapter1 extends FragmentStatePagerAdapter {

        private ScreenSlidePagerAdapter1(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return pages1.get(position);
        }

        @Override
        public int getCount() {
            return pages1.size();
        }
    }

    private class ScreenSlidePagerAdapter2 extends FragmentStatePagerAdapter {

        private ScreenSlidePagerAdapter2(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return pages2.get(position);
        }

        @Override
        public int getCount() {
            return pages2.size();
        }
    }

    private class ScreenSlidePagerAdapter3 extends FragmentStatePagerAdapter {

        private ScreenSlidePagerAdapter3(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return pages3.get(position);
        }

        @Override
        public int getCount() {
            return pages3.size();
        }
    }

    private class ScreenSlidePagerAdapter4 extends FragmentStatePagerAdapter {

        private ScreenSlidePagerAdapter4(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return pages4.get(position);
        }

        @Override
        public int getCount() {
            return pages4.size();
        }
    }
}