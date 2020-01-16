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
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.leanback.widget.HorizontalGridView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.moshkou.md.adapters.BillboardsAdapter;
import com.moshkou.md.adapters.SearchAdapter;
import com.moshkou.md.adapters.FilterAdapter;
import com.moshkou.md.configs.Enumerates;
import com.moshkou.md.configs.Keys;
import com.moshkou.md.configs.Permission;
import com.moshkou.md.configs.RequestCode;
import com.moshkou.md.configs.Settings;
import com.moshkou.md.fragments.BillboardFragment;
import com.moshkou.md.fragments.InfoItemFragment;
import com.moshkou.md.fragments.LocationFragment;
import com.moshkou.md.fragments.MediaFragment;
import com.moshkou.md.fragments.StatusFragment;
import com.moshkou.md.helpers.Utils;
import com.moshkou.md.R;
import com.moshkou.md.interfaces.OnBillboardListener;
import com.moshkou.md.interfaces.OnBillboardsListener;
import com.moshkou.md.interfaces.OnFilterListener;
import com.moshkou.md.interfaces.OnFragmentInteractionListener;
import com.moshkou.md.interfaces.OnSearchListener;
import com.moshkou.md.models.BillboardMediaModel;
import com.moshkou.md.models.BillboardModel;
import com.moshkou.md.models.BillboardLocationModel;
import com.moshkou.md.models.BillboardStatusModel;
import com.moshkou.md.models.KeyValue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;


public class MainActivity extends FragmentActivity implements
        OnMapReadyCallback, GoogleMap.OnMarkerClickListener, OnFragmentInteractionListener,
        OnSearchListener, OnFilterListener, OnBillboardsListener, OnBillboardListener {


    private static String TAG = "MAIN";

    private final Context context = this;

    private View mapView;

    private LinearLayout layoutInfoContent;
    private RelativeLayout layoutBottomSheetDetails;
    private RelativeLayout layoutBottomSheetBillboards;
    private BottomSheetBehavior layoutDetails;
    private BottomSheetBehavior layoutList;

    private TabLayout tabs;
    private ViewPager pager;
    private EditText textSearch;
    private EditText textSearchJustForFocus;
    private RecyclerView listSearch;
    private RelativeLayout layoutSearch;

    private Spinner spinnerMore;
    private Button buttonBack;
    private Button buttonFilter;
    private Button buttonFilterClear;
    private Button buttonSearchClear;
    private Button buttonInfoClose;
    private Button buttonInfoEdit;
    private Button actionButtonAdd;
    private Button actionButtonMyLocation;
    private RecyclerView recyclerViewBillboards;
    private HorizontalGridView gridViewFilter;
    private ViewPager viewPagerInfo;

    private TextView textInfoBillboardName;
    private TextView textInfoLastUpdate;
    private TextView textSearchNothing;

    private SearchAdapter searchAdapter;
    private FilterAdapter filterAdapter;
    private BillboardsAdapter billboardsAdapter;
    private InfoPagerAdapter infoPagerAdapter;

    private LocationFragment locationFragment;
    private BillboardFragment billboardFragment;
    private MediaFragment mediaFragment;
    private StatusFragment statusFragment;

    private GoogleMap map = null;
    private MapStyleOptions mapStyle = null;
    private LatLng myLocation = new LatLng(3.129489, 101.594188);
    private Marker myMarker;
    private static final int[] TAB_TITLES = new int[] { R.string.placeholder_location, R.string.placeholder_billboards, R.string.placeholder_media, R.string.placeholder_status };


    private List<Fragment> infoPages = new ArrayList<>();

    private List<BillboardModel> allBillboards = new ArrayList<>();
    private BillboardModel selectedBillboard = new BillboardModel();



    /**
     * Override functions ->
     * onCreate
     * onActivityResult
     * onNewIntent
     * onBackPressed
     * onRequestPermissionsResult
     * onMapReady
     * onMarkerClick
     **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapView = findViewById(R.id.map);

        layoutInfoContent = findViewById(R.id.layout_info_content);
        layoutBottomSheetDetails = findViewById(R.id.bottom_sheet_details);
        layoutBottomSheetBillboards = findViewById(R.id.bottom_sheet_billboards);
        layoutDetails = BottomSheetBehavior.from(layoutBottomSheetDetails);
        layoutList = BottomSheetBehavior.from(layoutBottomSheetBillboards);

        tabs = findViewById(R.id.tabs);
        pager = findViewById(R.id.pager);
        textSearch = findViewById(R.id.text_search);
        textSearchJustForFocus = findViewById(R.id.text_search_just_for_focus);
        listSearch = findViewById(R.id.list_search);
        layoutSearch = findViewById(R.id.layout_search);

        textInfoBillboardName = findViewById(R.id.text_view_info_billboard_name);
        textInfoLastUpdate = findViewById(R.id.text_view_info_last_update);
        textSearchNothing = findViewById(R.id.text_search_nothing);

        spinnerMore = findViewById(R.id.spinner_more);
        buttonBack = findViewById(R.id.button_back);
        buttonFilter = findViewById(R.id.button_filter);
        buttonFilterClear = findViewById(R.id.button_filter_clear);
        buttonSearchClear = findViewById(R.id.button_search_clear);
        actionButtonAdd = findViewById(R.id.action_button_add);
        actionButtonMyLocation = findViewById(R.id.action_button_my_location);
        recyclerViewBillboards = findViewById(R.id.recycler_view_billboards);
        gridViewFilter = findViewById(R.id.grid_view_filter);
        viewPagerInfo = findViewById(R.id.view_pager_info);
        buttonInfoClose = findViewById(R.id.layout_info_close);
        buttonInfoEdit = findViewById(R.id.layout_info_edit);


        init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

        String type = intent.getStringExtra(Keys.TYPE);

        if (type != null && type.equals(Keys.FILTER)) {
            filterAdapter.setItems();
            filter();
        }
    }

    @Override
    public void onBackPressed() {
        if (Settings.LAYOUT_STATUS != Enumerates.LayoutStatus.NULL) {
            showLayoutList();
            clearSearchFocus();
            clearFilter();
            clearSelected();
        } else {
            super.onBackPressed();
        }
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
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMarkerClickListener(this);
        map.getUiSettings().setMapToolbarEnabled(false);
        map.getUiSettings().setZoomControlsEnabled(false);

        if (Permission.ACCESS_FINE_LOCATION)
            map.setMyLocationEnabled(true);

        // MyLocation button
        View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        if (locationButton.getVisibility() != View.GONE)
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

        // TODO: just for testing
        addMarker(myMarker, myLocation, ":D", "billboard_id_3");
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        myMarker = marker;
        findBillboard(marker.getTag().toString());
        showLayoutInfo();
        return true;
    }




    /**
     * Init functions ->
     * init
     * initPager
     * initMap
     * initBillboards
     * initSearch
     * initFilter
     * initMore
     * initInfo
     */

    private void init() {
        getData();

        initPager();
        initMap();
        initBillboards();
        initSearch();
        initFilter();
        initMore();
        initInfo();


        // set the height to 70%
        int height = (int) (Settings.DEVICE_HEIGHT * 0.64);

        CoordinatorLayout.LayoutParams paramsBillboards = (CoordinatorLayout.LayoutParams) layoutBottomSheetBillboards.getLayoutParams();
        paramsBillboards.height = height;
        layoutBottomSheetBillboards.setLayoutParams(paramsBillboards);

        CoordinatorLayout.LayoutParams paramsDetails = (CoordinatorLayout.LayoutParams) layoutBottomSheetDetails.getLayoutParams();
        paramsDetails.height = height;
        layoutBottomSheetDetails.setLayoutParams(paramsDetails);
        layoutDetails.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (i == BottomSheetBehavior.STATE_COLLAPSED) {
                    clearSearchFocus();
                    showLayoutList();
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) { }
        });

        actionButtonAdd.setOnClickListener(view -> {
            clearSelected();
            showLayoutDetails();
            tabs.getTabAt(0).select();
        });

        buttonBack.setOnClickListener(view -> {
            clearSearchFocus();
            showLayoutList();
        });
    }

    private void initPager() {
        locationFragment = new LocationFragment();
        billboardFragment = new BillboardFragment();
        mediaFragment = new MediaFragment();
        statusFragment = new StatusFragment();

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        pager.setAdapter(pagerAdapter);
        tabs.setupWithViewPager(pager);
    }

    private void initMap() {
        Permission.Check.LOCATION(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void initBillboards() {
        billboardsAdapter = new BillboardsAdapter(allBillboards, this);
        recyclerViewBillboards.setAdapter(billboardsAdapter);
    }

    private void initSearch() {
        searchAdapter = new SearchAdapter(allBillboards, this);
        listSearch.setAdapter(searchAdapter);

        buttonSearchClear.setOnClickListener(view -> {
            if (buttonSearchClear.getVisibility() != View.GONE)
                buttonSearchClear.setVisibility(View.GONE);
            textSearch.setText("");
            // TODO: clear search
        });

        textSearch.setOnFocusChangeListener((v, hasFocus) -> {
            if (v.getId() == R.id.text_search) {
                if (hasFocus) {
                    showSearchList();
                }
            }
        });
        textSearch.setOnKeyListener((v, keyCode, event) -> {
            // If the event is a key-down event on the "enter" button
            //noinspection RedundantIfStatement
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {

                // TODO: search and display result in search list

                return true;
            }
            return false;
        });
        textSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    toggleListSearch(View.INVISIBLE);
                    searchAdapter.restore();
                } else {
                    toggleListSearch(View.VISIBLE);
                    searchAdapter.search(s.toString());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
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
            clearFilter();
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

    private void initInfo() {
        infoPagerAdapter = new InfoPagerAdapter(getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerInfo.setAdapter(infoPagerAdapter);

        buttonInfoEdit.setOnClickListener(view -> {
            showLayoutDetails();
            tabs.getTabAt(2).select();
        });

        buttonInfoClose.setOnClickListener(view -> {
            showLayoutList();
        });

    }




    /**
     * Helper functions ->
     * filter
     * addMarker
     * findBillboard
     * toggleListSearch
     * hasSearchText
     */

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

    private void clearFilter() {
        Settings.FILTER_ITEMS = new BillboardModel();
        filterAdapter.setItems();
        if (buttonFilterClear.getVisibility() != View.GONE)
            buttonFilterClear.setVisibility(View.GONE);
    }

    private void clearSelected() {
        selectedBillboard = new BillboardModel();
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
        if (allBillboards != null)
            for (BillboardModel b : allBillboards) {
                if (b._id.equals(id)) {
                    selectedBillboard = b;
                    return true;
                }
            }
        selectedBillboard = new BillboardModel();
        return false;
    }

    private void toggleListSearch(int visibility) {
        if (listSearch.getVisibility() != visibility)
            listSearch.setVisibility(visibility);

        if (buttonSearchClear.getVisibility() != visibility)
            buttonSearchClear.setVisibility(visibility);
    }

    private boolean hasSearchText() {
        return textSearch.getText().length() > 0;
    }

    private void clearSearchFocus() {
        textSearchJustForFocus.requestFocus();

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) buttonSearchClear.getLayoutParams();
        params.removeRule(RelativeLayout.ALIGN_PARENT_END);
        params.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
        buttonSearchClear.setLayoutParams(params);
    }



    /**
     * Populate billboards data ->
     * setBillboardInfo
     * setBillboardDetails
     * setBillboardList
     */

    private void setBillboardInfo() {

    }

    private void setBillboardDetails() {}

    private void setBillboardList() {}




    /**
     * Layouts Visibility ->
     * showLayoutInfo
     * showLayoutList
     * showLayoutDetails
     * showSearchList
     */

    private void showLayoutInfo() {
        populateInfo();

        Settings.LAYOUT_STATUS = Enumerates.LayoutStatus.INFO;

        if (layoutInfoContent.getVisibility() != View.VISIBLE)
            layoutInfoContent.setVisibility(View.VISIBLE);

        if (layoutBottomSheetBillboards.getVisibility() != View.GONE)
            layoutBottomSheetBillboards.setVisibility(View.GONE);

        if (layoutBottomSheetDetails.getVisibility() != View.GONE)
            layoutBottomSheetDetails.setVisibility(View.GONE);

        if (layoutSearch.getVisibility() != View.GONE)
            layoutSearch.setVisibility(View.GONE);

        Utils.hideKeyboard(this);
    }

    private void showLayoutList() {
        Settings.LAYOUT_STATUS = Enumerates.LayoutStatus.NULL;

        if (layoutInfoContent.getVisibility() != View.GONE)
            layoutInfoContent.setVisibility(View.GONE);

        if (layoutBottomSheetBillboards.getVisibility() != View.VISIBLE)
            layoutBottomSheetBillboards.setVisibility(View.VISIBLE);

        if (layoutBottomSheetDetails.getVisibility() != View.GONE)
            layoutBottomSheetDetails.setVisibility(View.GONE);

        if (layoutSearch.getVisibility() != View.GONE)
            layoutSearch.setVisibility(View.GONE);


        if (buttonBack.getVisibility() != View.GONE)
            buttonBack.setVisibility(View.GONE);

        if (buttonFilter.getVisibility() != View.VISIBLE)
            buttonFilter.setVisibility(View.VISIBLE);

        if (spinnerMore.getVisibility() != View.VISIBLE)
            spinnerMore.setVisibility(View.VISIBLE);

        layoutList.setState(BottomSheetBehavior.STATE_COLLAPSED);
        Utils.hideKeyboard(this);
    }

    private void showLayoutDetails() {
        populateDetails();

        Settings.LAYOUT_STATUS = Enumerates.LayoutStatus.DETAILS;

        if (layoutInfoContent.getVisibility() != View.GONE)
            layoutInfoContent.setVisibility(View.GONE);

        if (layoutBottomSheetBillboards.getVisibility() != View.GONE)
            layoutBottomSheetBillboards.setVisibility(View.GONE);

        if (layoutBottomSheetDetails.getVisibility() != View.VISIBLE)
            layoutBottomSheetDetails.setVisibility(View.VISIBLE);

        if (layoutSearch.getVisibility() != View.GONE)
            layoutSearch.setVisibility(View.GONE);


        if (buttonBack.getVisibility() != View.VISIBLE)
            buttonBack.setVisibility(View.VISIBLE);

        if (buttonFilter.getVisibility() != View.GONE)
            buttonFilter.setVisibility(View.GONE);

        if (spinnerMore.getVisibility() != View.GONE)
            spinnerMore.setVisibility(View.GONE);

        layoutDetails.setState(BottomSheetBehavior.STATE_EXPANDED);
        Utils.hideKeyboard(this);
    }

    private void showSearchList() {
        Settings.LAYOUT_STATUS = Enumerates.LayoutStatus.SEARCH;

        if (layoutBottomSheetDetails.getVisibility() != View.GONE)
            layoutBottomSheetDetails.setVisibility(View.GONE);

        if (layoutSearch.getVisibility() != View.VISIBLE)
            layoutSearch.setVisibility(View.VISIBLE);


        if (buttonBack.getVisibility() != View.VISIBLE)
            buttonBack.setVisibility(View.VISIBLE);

        if (buttonFilter.getVisibility() != View.GONE)
            buttonFilter.setVisibility(View.GONE);

        if (spinnerMore.getVisibility() != View.GONE)
            spinnerMore.setVisibility(View.GONE);

        if (spinnerMore.getVisibility() != View.GONE)
            spinnerMore.setVisibility(View.GONE);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) buttonSearchClear.getLayoutParams();
        params.removeRule(RelativeLayout.ALIGN_PARENT_START);
        params.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
        buttonSearchClear.setLayoutParams(params);
    }




    /**
     * Populate layouts ->
     * populateInfo
     */

    private void populateInfo() {

        textInfoBillboardName.setText(selectedBillboard.product);
        textInfoLastUpdate.setText(Utils.humanizerDateTime(selectedBillboard.updated_at) + " was last update.");

        List<Fragment> temp = new ArrayList<>();
        for (BillboardMediaModel media: selectedBillboard.medias) {
            InfoItemFragment infoItemFragment = new InfoItemFragment();
            infoItemFragment.setData(media);
            temp.add(infoItemFragment);
        }

        infoPages.clear();
        if (temp.size() > 0) {
            infoPages.addAll(temp);
            infoPagerAdapter.notifyDataSetChanged();
            viewPagerInfo.setCurrentItem(0);
        }
    }

    private void populateDetails() {
        locationFragment.setSelectedBillboard(selectedBillboard);
        billboardFragment.setSelectedBillboard(selectedBillboard);
        mediaFragment.setSelectedBillboard(selectedBillboard);
        statusFragment.setSelectedBillboard(selectedBillboard);
    }




    /**
     * Interfaces callback ->
     * onSearchInteraction
     * onFilterInteraction
     */

    @Override
    public void onSearchInteraction(BillboardModel billboard) {
        selectedBillboard = billboard;

        LatLng location = new LatLng(billboard.location.latitude, billboard.location.longitude);
        addMarker(myMarker, location, billboard.name, billboard._id);

        clearSearchFocus();
        showLayoutInfo();
    }

    @Override
    public void onSearchHasResult(boolean isNothing) {
        if (isNothing) {
            if (textSearchNothing.getVisibility() != View.VISIBLE)
                textSearchNothing.setVisibility(View.VISIBLE);
        } else {
            if (textSearchNothing.getVisibility() != View.GONE)
                textSearchNothing.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFilterInteraction() {
        Log.i(TAG, "onFilterInteraction");

        filter();
    }

    @Override
    public void onBillboardInteraction(BillboardModel billboard) {
        Log.i(TAG, "onBillboardInteraction.billboard.name: " + billboard.name);
    }




    /**
     * Fragments functions ->
     * onFragmentInteraction
     */

    public void onFragmentInteraction(BillboardModel selectedBillboard) {

    }




    /**
     * API callback ->
     * onGetBillboards
     * onCreateBillboardLocation
     * onUpdateBillboardLocation
     * onCreateBillboardInfo
     * onUpdateBillboardInfo
     * onCreateBillboardMedia
     * onUpdateBillboardMedia
     * onCreateBillboardStatus
     * onUpdateBillboardStatus
     */

    @Override
    public void onGetBillboards(List<BillboardModel> billboards) {

    }

    @Override
    public void onCreateBillboardLocation(BillboardLocationModel location) {

    }

    @Override
    public void onUpdateBillboardLocation(BillboardLocationModel location) {

    }

    @Override
    public void onCreateBillboardInfo(BillboardModel billboard) {

    }

    @Override
    public void onUpdateBillboardInfo(BillboardModel billboard) {

    }

    @Override
    public void onCreateBillboardMedia(BillboardMediaModel media) {

    }

    @Override
    public void onUpdateBillboardMedia(BillboardMediaModel media) {

    }

    @Override
    public void onCreateBillboardStatus(BillboardStatusModel status) {

    }

    @Override
    public void onUpdateBillboardStatus(BillboardStatusModel status) {

    }




    /**
     * Alert ->
     * showToast
     * showSnackBar
     * showDialog
     */

    private void showToast(String message) {
        Utils.toast(context, Enumerates.Message.ERROR, message, Toast.LENGTH_LONG);
    }

    private void showSnackBar() {
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




    /**
     * Private classes ->
     * PagerAdapter
     * InfoPagerAdapter
     */

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

    private class InfoPagerAdapter extends FragmentPagerAdapter {

        public InfoPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @Override
        public Fragment getItem(int position) {
            return infoPages.get(position);
        }

        @Override
        public int getCount() {
            return infoPages.size();
        }
    }



    /**
     * TODO: testing mode only
     * Sample data ->
     * getData: name, product, address
     */

    // name, product, address
    private void getData() {
        for (int i = 0; i < 30; i++) {
            BillboardModel b = new BillboardModel();
            b._id = "billboard_id_" + i;
            b.name = "billboard name " + i;
            b.media_owner = "billboard  media owner" + i;
            b.product = "billboard product " + i;
            b.advertiser = "billboard advertiser " + i;
            b.format = "billboard format " + i;
            b.size = "billboard size " + i;
            b.environment = "billboard environment " + i;
            b.lighting = true;
            b.no_panels = 3;
            b.speed_limit = "<30";
            b.type = "static";
            b.created_at = "2020-01-11T08:15:39.736Z";
            b.updated_at = "2020-01-14T08:15:39.736Z";

            b.location = new BillboardLocationModel();
            b.location._id = "location_id_" + i;
            b.location.name = "location name " + i;
            b.location.country = "Malaysia";
            b.location.state = "location state " + i;
            b.location.city = "location city " + i;
            b.location.address = "location address " + i;
            b.location.postcode = "47320";
            b.location.latitude = 3.124636;
            b.location.longitude = 101.588264;
            b.location.by = "h3j4h53hbh3r3hh3434hj34brrhbj34";


            for (int j = 0; j < 3; j++) {
                BillboardMediaModel m = new BillboardMediaModel();
                m._id = "status_id_" + i;
                m.media = "https://media.gettyimages.com/photos/powder-explosion-picture-id642320289?s=2048x2048";
                m.tags = new ArrayList<>(Arrays.asList(
                        new KeyValue("brand 0", " product 0"),
                        new KeyValue("brand 1", " product 1"),
                        new KeyValue("brand 2", " product 2")));
                m.is_interesting = false;

                b.medias.add(m);
            }

            b.status = new BillboardStatusModel();
            b.status._id = "status_id_" + i;
            b.status.status = "status " + i;
            b.status.comment = "comment " + i;

            allBillboards.add(b);
        }
    }
}
