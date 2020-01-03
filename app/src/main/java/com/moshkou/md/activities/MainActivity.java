package com.moshkou.md.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.moshkou.md.adapters.AutoCompleteAdapter;
import com.moshkou.md.adapters.FilterAdapter;
import com.moshkou.md.configs.Enumerates;
import com.moshkou.md.configs.Keys;
import com.moshkou.md.configs.Permission;
import com.moshkou.md.configs.RequestCode;
import com.moshkou.md.configs.Settings;
import com.moshkou.md.controls.DraggingPanel;
import com.moshkou.md.fragments.BillboardFragment;
import com.moshkou.md.fragments.LocationFragment;
import com.moshkou.md.fragments.MediaFragment;
import com.moshkou.md.fragments.StatusFragment;
import com.moshkou.md.helpers.Utils;
import com.moshkou.md.R;
import com.moshkou.md.interfaces.OnFilterListener;
import com.moshkou.md.interfaces.OnFragmentInteractionListener;
import com.moshkou.md.interfaces.OnSearchListener;
import com.moshkou.md.models.BillboardModel;
import com.moshkou.md.models.LocationModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;


public class MainActivity extends FragmentActivity implements
        OnMapReadyCallback, GoogleMap.OnMarkerClickListener, OnFragmentInteractionListener, OnSearchListener, OnFilterListener {


    private static String TAG = "MAIN";

    private final Context context = this;

    private View mapView;
    private DraggingPanel layoutInfo;
    private DraggingPanel layoutDetails;
    private DraggingPanel layoutList;
    private ViewPager pager;
    private TextView textSearch;
    private RecyclerView listSearch;
    private Spinner spinnerMore;
    private Button buttonFilter;
    private Button buttonFilterClear;
    private Button actionButtonAdd;
    private Button actionButtonMyLocation;
    private androidx.leanback.widget.HorizontalGridView gridViewFilter;

    private AutoCompleteAdapter autoCompleteAdapter;
    private FilterAdapter filterAdapter;

    private LocationFragment locationFragment;
    private BillboardFragment billboardFragment;
    private MediaFragment mediaFragment;
    private StatusFragment statusFragment;

    private GoogleMap map = null;
    private MapStyleOptions mapStyle = null;
    private LatLng myLocation = new LatLng(37.774546, -122.433523);
    private Marker myMarker;
    private static final int[] TAB_TITLES = new int[] { R.string.placeholder_name, R.string.placeholder_email, R.string.placeholder_login, R.string.placeholder_phone };

    private List<BillboardModel> allBillboards;
    private BillboardModel selectedBillboard;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        pager = findViewById(R.id.pager);
        mapView = findViewById(R.id.map);
        textSearch = findViewById(R.id.text_search);
        listSearch = findViewById(R.id.list_search);
        buttonFilter = findViewById(R.id.button_filter);
        layoutInfo = findViewById(R.id.layout_info);
        layoutDetails = findViewById(R.id.layout_details);
        layoutList = findViewById(R.id.layout_list);
        gridViewFilter = findViewById(R.id.grid_view_filter);
        spinnerMore = findViewById(R.id.spinner_more);
        buttonFilterClear = findViewById(R.id.button_filter_clear);
        actionButtonAdd = findViewById(R.id.action_button_add);
        actionButtonMyLocation = findViewById(R.id.action_button_my_location);

        init();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case RequestCode.ACCESS_NETWORK_STATE:
                if (permissions.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_NETWORK_STATE)) {
                    // TODO: display "Permission was denied. Display an error message."
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMarkerClickListener(this);
        map.setMyLocationEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(false);
        map.getUiSettings().setZoomControlsEnabled(false);

        // MyLocation button
        View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        locationButton.setVisibility(View.GONE);
        actionButtonMyLocation.setOnClickListener(view -> {
            Permission.Check.LOCATION(this);
            if (Permission.ACCESS_FINE_LOCATION)
                locationButton.callOnClick();
        });

        // get time now
        SimpleDateFormat sdf = new SimpleDateFormat("HHmm", Locale.getDefault());
        Integer currentTime = Integer.valueOf(sdf.format(new Date()));

        // 1915: sunset time in KL
        mapStyle = currentTime < 1915 ? null : MapStyleOptions.loadRawResourceStyle(context, R.raw.mapstyle_night);

        addMarker(myMarker, myLocation, ":D", "1233213123123123");
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        myMarker = marker;
        findBillboard(marker.getTag().toString());
        showLayoutInfo();
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        getExtra();
    }

    private void getExtra() {
        Intent i = getIntent();
        String type = i.getStringExtra(Keys.TYPE);

        if (type != null && type.equals(Keys.FILTER)) {
            filterAdapter.setItems();
            filter();
        }
    }


    @Override
    public void onSearchInteraction(BillboardModel item) {
        Log.i(TAG, "::::: " + item.name);
        // goto item clicked
    }

    @Override
    public void onFilterInteraction() {
        Log.i(TAG, "onFilterInteraction");

        filter();
    }



    private void init() {
        initMap();
        initPager();
        initSearch();
        initFilter();
        initMore();

        layoutInfo.setQueenButton(R.id.layout_info_main);
        layoutDetails.setQueenButton(R.id.layout_details_main);
        layoutList.setQueenButton(R.id.layout_list_main);

        actionButtonAdd.setOnClickListener(view -> {
            layoutInfo.setVisibility(View.GONE);
            layoutDetails.setVisibility(View.VISIBLE);
            listSearch.setVisibility(View.GONE);
            Utils.hideKeyboard(this);
        });
    }

    private void initPager() {
        locationFragment = new LocationFragment();
        billboardFragment = new BillboardFragment();
        mediaFragment = new MediaFragment();
        statusFragment = new StatusFragment();

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        pager.setAdapter(pagerAdapter);


        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(pager);
    }

    private void initMap() {
        Permission.Check.LOCATION(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void initSearch() {
        autoCompleteAdapter = new AutoCompleteAdapter(context, getData(), this);
        listSearch.setAdapter(autoCompleteAdapter);

        textSearch.setOnFocusChangeListener((v, hasFocus) -> {
            if (v.getId() == R.id.text_search) {
                if (hasFocus) {
                    layoutInfo.setVisibility(View.GONE);
                    layoutDetails.setVisibility(View.GONE);
                    listSearch.setVisibility(View.VISIBLE);
                }
            }
        });
        textSearch.setOnKeyListener((v, keyCode, event) -> {
            // If the event is a key-down event on the "enter" button
            //noinspection RedundantIfStatement
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                layoutInfo.setVisibility(View.GONE);
                layoutDetails.setVisibility(View.GONE);
                Utils.hideKeyboard(this);
                return true;
            }
            return false;
        });
        textSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, "a: " + s);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i(TAG, "b: " + s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0) {
                    // TODO: search and filter
                    Log.i(TAG, "o: " + s);
                }
            }
        });
    }

    private void initFilter() {
        filterAdapter = new FilterAdapter(context, this);
        gridViewFilter.setAdapter(filterAdapter);

        buttonFilter.setOnClickListener(view -> {
            Intent i = new Intent(context, FilterActivity.class);
            startActivity(i);
        });

        buttonFilterClear.setOnClickListener(view -> {
            Settings.FILTER_ITEMS = new BillboardModel();
            filterAdapter.setItems();
            buttonFilterClear.setVisibility(View.GONE);
        });
    }

    private void initMore() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.more_menu_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMore.setAdapter(adapter);
        spinnerMore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    switch (position) {
                        case 0:
                            showToast("do nothing...");
                            break;
                        case 1:
                            // TODO: logout
                            // clear data
                            // goto login
                            showToast(getString(R.string.action_exit));
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
    }



    private void filter() {
        Log.i(TAG, "filter");

        if (Settings.FILTER_ITEMS.is_updated) {
            if (Settings.FILTER_ITEMS.location.state.isEmpty() &&
                    Settings.FILTER_ITEMS.location.city.isEmpty() &&
                    Settings.FILTER_ITEMS.media_owner.isEmpty() &&
                    Settings.FILTER_ITEMS.format.isEmpty() &&
                    Settings.FILTER_ITEMS.advertiser.isEmpty())
                buttonFilterClear.setVisibility(View.GONE);
            else
                buttonFilterClear.setVisibility(View.VISIBLE);

            // TODO: do filter -> FILTER_ITEMS

        }
    }

    private void addMarker(Marker marker, LatLng latLng, String title, String id) {
        marker = map.addMarker(new MarkerOptions()
                .position(latLng)
                .title(title)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        marker.setTag(id);

        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f));
    }

    private boolean findBillboard(String id) {
        for (BillboardModel b : allBillboards) {
            if (b._id.equals(id)) {
                selectedBillboard = b;
                return true;
            }
        }
        selectedBillboard = null;
        return false;
    }


    private void setBillboardInfo() {

    }
    private void setBillboardDetails() {}
    private void setBillboardList() {}


    private void showLayoutInfo() {
        if (layoutInfo.getVisibility() != View.VISIBLE)
            layoutInfo.setVisibility(View.VISIBLE);

        if (layoutDetails.getVisibility() != View.GONE)
            layoutDetails.setVisibility(View.GONE);

        if (listSearch.getVisibility() != View.GONE)
            listSearch.setVisibility(View.GONE);

        Utils.hideKeyboard(this);
    }

    private void showLayoutDetails() {
        if (layoutInfo.getVisibility() != View.GONE)
            layoutInfo.setVisibility(View.GONE);

        if (layoutDetails.getVisibility() != View.VISIBLE)
            layoutDetails.setVisibility(View.VISIBLE);

        if (listSearch.getVisibility() != View.GONE)
            listSearch.setVisibility(View.GONE);

        Utils.hideKeyboard(this);
    }

    private void showLayoutList() {
        if (layoutInfo.getVisibility() != View.GONE)
            layoutInfo.setVisibility(View.GONE);

        if (layoutDetails.getVisibility() != View.GONE)
            layoutDetails.setVisibility(View.GONE);

        if (listSearch.getVisibility() != View.VISIBLE)
            listSearch.setVisibility(View.VISIBLE);
    }




    private void showToast(String message) {
        Utils.toast(context, Enumerates.Message.ERROR, message, Toast.LENGTH_LONG);
    }

    private void showSnackbar() {
        final View coordinatorLayout = findViewById(R.id.coordinatorLayout);
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "Message is deleted", Snackbar.LENGTH_LONG)
                .setAction("UNDO", (view1) -> Snackbar.make(coordinatorLayout, "Message is restored!", Snackbar.LENGTH_SHORT).show());
        snackbar.show();
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(context);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);

        TextView header_text = dialog.findViewById(R.id.header_text);
        TextView text_body = dialog.findViewById(R.id.text_body);
        Button cancel = dialog.findViewById(R.id.btn_cancel);
        Button ok = dialog.findViewById(R.id.btn_ok);

        header_text.setText("This is sample header");
        text_body.setText("This is sample body text.");


        ok.setOnClickListener((view) -> {
            dialog.dismiss();
        });
        cancel.setOnClickListener((view) -> {
            dialog.dismiss();
        });

        dialog.show();
    }



    public void onFragmentInteraction(Uri uri) {

    }

    private class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return locationFragment;
                case 1:
                    return billboardFragment;
                case 2:
                    return mediaFragment;
                case 3:
                    return statusFragment;
                default:
                    return null;
            }
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getString(TAB_TITLES[position]);
        }

        @Override
        public int getCount() {
            return 4;
        }
    }




    // TODO: sample data
    // name, product, address
    private List<BillboardModel> getData(){
        List<BillboardModel> data = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            BillboardModel b = new BillboardModel();
            b.name = "name " + i;
            b.product = "product " + i;
            b.location = new LocationModel();
            b.location.address = "address " + i;
            data.add(b);
        }

        return data;
    }

}
