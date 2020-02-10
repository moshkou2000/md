package com.moshkou.md.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.leanback.widget.HorizontalGridView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moshkou.md.App;
import com.moshkou.md.adapters.BillboardsAdapter;
import com.moshkou.md.adapters.SearchAdapter;
import com.moshkou.md.adapters.FilterAdapter;
import com.moshkou.md.configs.Enumerates;
import com.moshkou.md.configs.Flags;
import com.moshkou.md.configs.Keys;
import com.moshkou.md.configs.Permission;
import com.moshkou.md.configs.RequestCode;
import com.moshkou.md.configs.Settings;
import com.moshkou.md.fragments.InfoItemFragment;
import com.moshkou.md.helpers.Utils;
import com.moshkou.md.R;
import com.moshkou.md.interfaces.OnBillboardListener;
import com.moshkou.md.interfaces.OnBillboardsListener;
import com.moshkou.md.interfaces.OnFilterListener;
import com.moshkou.md.interfaces.OnInfoListener;
import com.moshkou.md.interfaces.OnSearchListener;
import com.moshkou.md.models.BillboardMediaModel;
import com.moshkou.md.models.BillboardModel;
import com.moshkou.md.models.BillboardLocationModel;
import com.moshkou.md.models.BillboardStatusModel;
import com.moshkou.md.models.KeyValue;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;


public class MainActivity extends FragmentActivity implements
        OnMapReadyCallback,
        OnSearchListener, OnFilterListener, OnBillboardsListener, OnBillboardListener, OnInfoListener {


    private static String TAG = "MAIN";

    private View mapView;

    private LinearLayout layoutInfo;
    private LinearLayout layoutBillboards;
    private BottomSheetBehavior bottomSheetInfo;
    private BottomSheetBehavior bottomSheetBillboards;

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
    private Button actionButtonAddLocation;
    private RecyclerView recyclerViewBillboards;
    private HorizontalGridView gridViewFilter;
    private ViewPager viewPagerInfo;

    private TextView textMap;
    private TextView textInfoBillboardName;
    private TextView textInfoLastUpdate;
    private TextView textSearchNothing;

    private SearchAdapter searchAdapter;
    private FilterAdapter filterAdapter;
    private BillboardsAdapter billboardsAdapter;
    private InfoPagerAdapter infoPagerAdapter;

    private Animation animationAdd;
    private List<Fragment> infoPages = new ArrayList<>();
    private boolean allowed = false;

    private GoogleMap map = null;
    private MapStyleOptions mapStyle = null;

    private List<Marker> markers = new ArrayList<>();
    private List<BillboardModel> billboards = new ArrayList<>();
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

        layoutInfo = findViewById(R.id.bottom_sheet_info);
        bottomSheetInfo = BottomSheetBehavior.from(layoutInfo);

        layoutBillboards = findViewById(R.id.bottom_sheet_billboards);
        bottomSheetBillboards = BottomSheetBehavior.from(layoutBillboards);

        textSearch = findViewById(R.id.text_search);
        textSearchJustForFocus = findViewById(R.id.text_search_just_for_focus);
        listSearch = findViewById(R.id.list_search);
        layoutSearch = findViewById(R.id.layout_search);

        textMap = findViewById(R.id.text_map);
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
        actionButtonAddLocation = findViewById(R.id.action_button_add_location);
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
    protected void onDestroy() {
        searchAdapter = null;
        filterAdapter = null;
        billboardsAdapter = null;
        infoPagerAdapter = null;
        infoPages.clear();
        infoPages = null;
        animationAdd = null;
        map = null;
        mapStyle = null;
        markers.clear();
        markers = null;
        billboards.clear();
        billboards = null;
        selectedBillboard = null;

        Runtime.getRuntime().gc();
        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

        String type = intent.getStringExtra(Keys.TYPE);

        if (type != null && type.equals(Keys.FILTER)) {
            filterAdapter.setItems();
            filter(false);
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
            if (allowed) {
                super.onBackPressed();
            } else {
                allowed = true;
                Utils.toast(App.getContext(), Enumerates.Message.INFO, "Click BACK again to exit", Toast.LENGTH_SHORT);
                new Handler().postDelayed(()-> allowed = false, 2000);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case RequestCode.ACCESS_NETWORK_STATE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_NETWORK_STATE)) {
                    // TODO: display "Permission was denied. Display an error message."
                }
                break;
            case RequestCode.ACCESS_FINE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    showDialogLocationPermission();
//                    String permission = permissions[0];
//                    boolean showRationale = shouldShowRequestPermissionRationale(permission);
//                    if (! showRationale) {
//                        // user also CHECKED "never ask again"
//                        // you can either enable some fall back,
//                        // disable features of your app
//                        // or open another dialog explaining
//                        // again the permission and directing to
//                        // the app setting
//
//                    } else if (Manifest.permission.ACCESS_FINE_LOCATION.equals(permission)) {
//                        // user did NOT check "never ask again"
//                        // this is a good place to explain the user
//                        // why you need the permission and ask if he wants
//                        // to accept it (the rationale)
//                    } else if ( /* possibly check more permissions...*/ ) {
//                    }
                }
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setMapToolbarEnabled(false);
        map.getUiSettings().setZoomControlsEnabled(false);
        map.getUiSettings().setCompassEnabled(true);

        if (Permission.ACCESS_FINE_LOCATION)
            map.setMyLocationEnabled(true);

        // Marker click
        googleMap.setOnMarkerClickListener(marker -> {
            if (Settings.LAYOUT_STATUS != Enumerates.LayoutStatus.ADD) {
                findBillboard(Objects.requireNonNull(marker.getTag()).toString());
                showLayoutInfo();
                gotoMarker();
            }
            return true;
        });

        // Map click
//        googleMap.setOnMapClickListener(latLng -> { });
//        googleMap.setOnCameraIdleListener(() -> {
//            Log.i(TAG,"centerLat:: " + map.getCameraPosition().target.latitude);
//        });
//        googleMap.setOnCameraChangeListener(cameraPosition -> {
//            Log.i(TAG,"centerLat: " + cameraPosition.target.latitude);
//            Log.i(TAG,"centerLong: " + cameraPosition.target.longitude);
//        });

        final View mapViewParent = ((View) mapView.findViewById(Integer.parseInt("1")).getParent());

        // MyLocation button
        View locationButton = mapViewParent.findViewById(Integer.parseInt("2"));
        if (locationButton != null) {
            if (locationButton.getVisibility() != View.GONE)
                locationButton.setVisibility(View.GONE);

            actionButtonMyLocation.setOnClickListener(view -> {
                if (!Permission.ACCESS_FINE_LOCATION) {
                    Permission.Check.LOCATION(this);
                } else {
                    if (Permission.Check.LOCATION_STATUS(this)) {
                        Log.i(TAG, "CLICKED");
                        locationButton.callOnClick();
                    } else
                        showDialogLocationAccess();
                }
            });
        }

        // Compass position
        View locationCompass = mapViewParent.findViewById(Integer.parseInt("5"));
        if (locationCompass != null) {
            RelativeLayout.LayoutParams params =
                    (RelativeLayout.LayoutParams) locationCompass.getLayoutParams();
            params.removeRule(RelativeLayout.ALIGN_PARENT_TOP);
            params.removeRule(RelativeLayout.ALIGN_PARENT_START);
            params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            params.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
            params.setMargins(0, 0, 0, 373);
            params.width = (int) (68 * Settings.DEVICE_DENSITY);
            params.height = (int) (68 * Settings.DEVICE_DENSITY);
            locationCompass.setVisibility(View.VISIBLE);
        }

        // get time now
        SimpleDateFormat sdf = new SimpleDateFormat("HHmm", Locale.getDefault());
        Integer currentTime = Integer.valueOf(sdf.format(new Date()));

        // TODO: just for testing
        // 1915: sunset time in KL
        mapStyle = currentTime < 1915 ? null : MapStyleOptions.loadRawResourceStyle(App.getContext(), R.raw.mapstyle_night);

        populateMap();
    }




    /**
     * Init functions ->
     * init
     * initMap
     * initBillboards
     * initSearch
     * initFilter
     * initMore
     * initInfo
     */

    private void init() {
        getData();

        initMap();
        initBillboards();
        initSearch();
        initFilter();
        initMore();
        initInfo();
        initAdd();
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void initBillboards() {
        CoordinatorLayout.LayoutParams paramsBillboards = (CoordinatorLayout.LayoutParams) layoutBillboards.getLayoutParams();
        paramsBillboards.height = (int) (Settings.DEVICE_HEIGHT * 0.64); // set the height to 64%
        layoutBillboards.setLayoutParams(paramsBillboards);

        billboardsAdapter = new BillboardsAdapter(billboards, this);
        recyclerViewBillboards.setAdapter(billboardsAdapter);
    }

    private void initSearch() {
        searchAdapter = new SearchAdapter(billboards, this);
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

        buttonBack.setOnClickListener(view -> {
            clearSearchFocus();
            showLayoutList();
        });
    }

    private void initFilter() {
        filterAdapter = new FilterAdapter(App.getContext(), this);
        gridViewFilter.setAdapter(filterAdapter);

        buttonFilter.setOnClickListener(view ->
                startActivity(new Intent(App.getContext(), FilterActivity.class)));

        buttonFilterClear.setOnClickListener(view -> clearFilter());
    }

    private void initMore() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(App.getContext(),
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
        bottomSheetInfo.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (bottomSheetInfo.getState() == BottomSheetBehavior.STATE_COLLAPSED)
                    showLayoutList();
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        infoPagerAdapter = new InfoPagerAdapter(getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerInfo.setAdapter(infoPagerAdapter);

        buttonInfoEdit.setOnClickListener(view -> {
            toggleAdd(true);
            // TODO: move to the coordinate
            //
        });

        buttonInfoClose.setOnClickListener(view -> {
            showLayoutList();
        });
    }

    private void initAdd() {
        actionButtonAdd.setOnClickListener(view -> {
            clearSelected();
            toggleAdd(true);
        });
        actionButtonAddLocation.setOnClickListener(view -> {
            gotoBillboardActivity();

            Handler handler = new Handler();
            handler.postDelayed(this::showLayoutList, 600);
        });
    }




    /**
     * Helper functions ->
     * filter
     * findBillboard
     * clearSelected
     * clearSearchFocus
     * addMarker
     * getMapCenter
     * gotoMarker
     * gotoBillboardActivity
     * toggleListSearch
     * toggleFilter
     * toggleAdd
     */

    private void filter(boolean isEmpty) {
        Log.i(TAG, "filter.state: " + Settings.FILTER_BILLBOARD.location.state);
        Log.i(TAG, "filter.city: " + Settings.FILTER_BILLBOARD.location.state);
        Log.i(TAG, "filter.media_owner: " + Settings.FILTER_BILLBOARD.media_owner);
        Log.i(TAG, "filter.format: " + Settings.FILTER_BILLBOARD.format);
        Log.i(TAG, "filter.advertiser: " + Settings.FILTER_BILLBOARD.advertiser);

//        if (Settings.FILTER_BILLBOARD.is_updated)
        {
            if (isEmpty || (Settings.FILTER_BILLBOARD.location.state.isEmpty() &&
                    Settings.FILTER_BILLBOARD.location.city.isEmpty() &&
                    Settings.FILTER_BILLBOARD.media_owner.isEmpty() &&
                    Settings.FILTER_BILLBOARD.format.isEmpty() &&
                    Settings.FILTER_BILLBOARD.advertiser.isEmpty())) {

                if (buttonFilterClear.getVisibility() != View.GONE)
                    buttonFilterClear.setVisibility(View.GONE);

            } else if (buttonFilterClear.getVisibility() != View.VISIBLE)
                buttonFilterClear.setVisibility(View.VISIBLE);

            // TODO: do filter -> FILTER_BILLBOARD

        }
    }

    private void findBillboard(String id) {
        if (billboards != null)
            for (BillboardModel b : billboards) {
                if (b._id.equals(id)) {
                    selectedBillboard = b;
                    break;
                }
            }
    }


    private void clearFilter() {
        Settings.FILTER_BILLBOARD = new BillboardModel();
        filterAdapter.setItems();
        if (buttonFilterClear.getVisibility() != View.GONE)
            buttonFilterClear.setVisibility(View.GONE);
    }

    private void clearSelected() {
        selectedBillboard = new BillboardModel();
    }

    private void clearSearchFocus() {
        textSearchJustForFocus.requestFocus();

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) buttonSearchClear.getLayoutParams();
        params.removeRule(RelativeLayout.ALIGN_PARENT_END);
        params.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
        buttonSearchClear.setLayoutParams(params);
    }


    private Marker addMarker(LatLng latLng, String title, String id, BitmapDescriptor icon) {
        Marker marker = map.addMarker(new MarkerOptions()
                .position(latLng)
                .title(title)
                .icon(icon));
        marker.setTag(id);

        return marker;
    }

    private LatLng getMapCenter() {
//        return map.getProjection().getVisibleRegion().latLngBounds.getCenter();
        return map.getCameraPosition().target;
    }

    private void gotoMarker() {
        LatLng latLng = new LatLng(selectedBillboard.location.latitude, selectedBillboard.location.longitude);
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f));
    }

    private void gotoBillboardActivity() {
        LatLng latLng = getMapCenter();
        selectedBillboard.location.latitude = latLng.latitude;
        selectedBillboard.location.longitude = latLng.longitude;

        if (latLng.latitude != 0.0 || latLng.longitude != 0.0) {
            Intent i = new Intent(App.getContext(), BillboardActivity.class);
            i.putExtra(Keys.TYPE, Keys.BILLBOARD);
            Type type = new TypeToken<BillboardModel>() {
            }.getType();
            i.putExtra(Keys.DATA, new Gson().toJson(selectedBillboard, type));
            startActivity(i);

        } else {
            showToast("Zoom In Please");
        }
    }


    private void toggleListSearch(int visibility) {
        if (listSearch.getVisibility() != visibility)
            listSearch.setVisibility(visibility);

        if (buttonSearchClear.getVisibility() != visibility)
            buttonSearchClear.setVisibility(visibility);
    }

    private void toggleFilter(int visibility) {
        if (gridViewFilter.getVisibility() != visibility)
            gridViewFilter.setVisibility(visibility);
    }

    private void toggleAdd(boolean doIt) {
        final Handler handler = new Handler();

        if (doIt) {
            showAdd();

            double parentCenterX = actionButtonAdd.getX() / 2 * -1;
            double parentCenterY = actionButtonAdd.getY() / 2 * -1 + 70;
            animationAdd = new TranslateAnimation(0, (int)parentCenterX, 0, (int)parentCenterY);
            animationAdd.setDuration(300);
            animationAdd.setFillAfter(true);
            animationAdd.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) { }

                @Override
                public void onAnimationEnd(Animation animation) {
                    actionButtonAdd.clearAnimation();
                }

                @Override
                public void onAnimationRepeat(Animation animation) { }
            });
            actionButtonAdd.startAnimation(animationAdd);
            actionButtonAdd.setBackgroundResource(R.drawable.ic_location);
            handler.postDelayed(() -> {
                actionButtonAdd.setVisibility(View.INVISIBLE);
                actionButtonAddLocation.setVisibility(View.VISIBLE);
            }, 200);

        } else {
            showLayoutDetails();

            if (animationAdd != null) {
                animationAdd.setRepeatCount(1);
                animationAdd.setRepeatMode(Animation.REVERSE);
                actionButtonAdd.setVisibility(View.VISIBLE);
                actionButtonAdd.setBackgroundResource(R.drawable.bg_add);
                actionButtonAddLocation.setVisibility(View.GONE);
            }
        }
    }




    /**
     * Layouts Visibility ->
     * showLayoutInfo
     * showLayoutList
     * showSearchList
     * showAdd
     */

    private void showLayoutInfo() {
        populateInfo();

        Settings.LAYOUT_STATUS = Enumerates.LayoutStatus.INFO;

        if (layoutInfo.getVisibility() != View.VISIBLE)
            layoutInfo.setVisibility(View.VISIBLE);

        if (layoutBillboards.getVisibility() != View.GONE)
            layoutBillboards.setVisibility(View.GONE);

        if (layoutSearch.getVisibility() != View.GONE)
            layoutSearch.setVisibility(View.GONE);


        if (buttonFilter.getVisibility() != View.VISIBLE)
            buttonFilter.setVisibility(View.VISIBLE);

        if (spinnerMore.getVisibility() != View.VISIBLE)
            spinnerMore.setVisibility(View.VISIBLE);

        bottomSheetInfo.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBillboards.setState(BottomSheetBehavior.STATE_COLLAPSED);
        Utils.hideKeyboard(this);
    }

    private void showLayoutList() {
        toggleFilter(View.VISIBLE);
        toggleAdd(false);

        Settings.LAYOUT_STATUS = Enumerates.LayoutStatus.NULL;

        if (layoutInfo.getVisibility() != View.GONE)
            layoutInfo.setVisibility(View.GONE);

        if (layoutBillboards.getVisibility() != View.VISIBLE)
            layoutBillboards.setVisibility(View.VISIBLE);

        if (layoutSearch.getVisibility() != View.GONE)
            layoutSearch.setVisibility(View.GONE);

        if (textMap.getVisibility() != View.GONE)
            textMap.setVisibility(View.GONE);


        if (buttonBack.getVisibility() != View.GONE)
            buttonBack.setVisibility(View.GONE);

        if (buttonFilter.getVisibility() != View.VISIBLE)
            buttonFilter.setVisibility(View.VISIBLE);

        if (spinnerMore.getVisibility() != View.VISIBLE)
            spinnerMore.setVisibility(View.VISIBLE);

        bottomSheetInfo.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBillboards.setState(BottomSheetBehavior.STATE_COLLAPSED);
        Utils.hideKeyboard(this);
    }

    private void showLayoutDetails() {

        Settings.LAYOUT_STATUS = Enumerates.LayoutStatus.DETAILS;

        if (layoutInfo.getVisibility() != View.GONE)
            layoutInfo.setVisibility(View.GONE);

        if (layoutBillboards.getVisibility() != View.GONE)
            layoutBillboards.setVisibility(View.GONE);

        if (layoutSearch.getVisibility() != View.GONE)
            layoutSearch.setVisibility(View.GONE);


        if (buttonBack.getVisibility() != View.VISIBLE)
            buttonBack.setVisibility(View.VISIBLE);

        if (buttonFilter.getVisibility() != View.GONE)
            buttonFilter.setVisibility(View.GONE);

        if (spinnerMore.getVisibility() != View.GONE)
            spinnerMore.setVisibility(View.GONE);

        Utils.hideKeyboard(this);
    }

    private void showSearchList() {
        toggleFilter(View.GONE);

        Settings.LAYOUT_STATUS = Enumerates.LayoutStatus.SEARCH;

        if (layoutSearch.getVisibility() != View.VISIBLE)
            layoutSearch.setVisibility(View.VISIBLE);


        if (buttonBack.getVisibility() != View.VISIBLE)
            buttonBack.setVisibility(View.VISIBLE);

        if (buttonFilter.getVisibility() != View.GONE)
            buttonFilter.setVisibility(View.GONE);

        if (spinnerMore.getVisibility() != View.GONE)
            spinnerMore.setVisibility(View.GONE);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) buttonSearchClear.getLayoutParams();
        params.removeRule(RelativeLayout.ALIGN_PARENT_START);
        params.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
        buttonSearchClear.setLayoutParams(params);
    }

    private void showAdd() {

        Settings.LAYOUT_STATUS = Enumerates.LayoutStatus.ADD;

        if (layoutInfo.getVisibility() != View.GONE)
            layoutInfo.setVisibility(View.GONE);

        if (layoutBillboards.getVisibility() != View.GONE)
            layoutBillboards.setVisibility(View.GONE);

        if (textMap.getVisibility() != View.VISIBLE)
            textMap.setVisibility(View.VISIBLE);


        if (buttonBack.getVisibility() != View.VISIBLE)
            buttonBack.setVisibility(View.VISIBLE);

        if (buttonFilter.getVisibility() != View.GONE)
            buttonFilter.setVisibility(View.GONE);

        if (spinnerMore.getVisibility() != View.GONE)
            spinnerMore.setVisibility(View.GONE);
    }




    /**
     * Populate layouts ->
     * populateMap
     * populateInfo
     * setBillboardInfo
     * setBillboardDetails
     * setBillboardList
     */

    private void populateMap() {
        for (BillboardModel billboard: billboards) {
            Marker marker = addMarker(
                    new LatLng(billboard.location.latitude, billboard.location.longitude),
                    billboard.name,
                    billboard._id,
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
//                    BitmapDescriptorFactory.fromResource(R.drawable.ic_location_searching)
            markers.add(marker);
        }
    }

    private void populateInfo() {
        textInfoBillboardName.setText(selectedBillboard.name);
        textInfoLastUpdate.setText(getString(
                R.string.placeholder_last_update,
                Utils.humanizerDateTime(selectedBillboard.updated_at)));

        List<Fragment> temp = new ArrayList<>();
        for (int i = 0; i < selectedBillboard.getUrls().size(); i++) {
            InfoItemFragment infoItemFragment = new InfoItemFragment();
            infoItemFragment.setData(i, selectedBillboard.getMedias().get(i), this);
            temp.add(infoItemFragment);
        }

        infoPages.clear();
        if (temp.size() > 0) {
            infoPages.addAll(temp);
            infoPagerAdapter.notifyDataSetChanged();
            viewPagerInfo.setCurrentItem(0);
        }
    }

    private void setBillboardInfo() {}

    private void setBillboardDetails() {}

    private void setBillboardList() {}




    /**
     * Interfaces callback ->
     * onSearchInteraction
     * onBillboardInteraction
     * onSearchHasResult
     * onFilterInteraction
     */

    public void onSearchInteraction(BillboardModel billboard) {
        selectedBillboard = billboard;
        clearSearchFocus();
        gotoMarker();
        showLayoutInfo();
    }

    public void onBillboardInteraction(BillboardModel billboard) {
        selectedBillboard = billboard;
        gotoMarker();
        showLayoutInfo();
    }

    public void onSearchHasResult(boolean isNothing) {
        if (isNothing) {
            if (textSearchNothing.getVisibility() != View.VISIBLE)
                textSearchNothing.setVisibility(View.VISIBLE);
        } else {
            if (textSearchNothing.getVisibility() != View.GONE)
                textSearchNothing.setVisibility(View.GONE);
        }
    }

    public void onFilterInteraction(boolean isEmpty) {
        filter(isEmpty);
    }

    public void onInfoInteraction(int index) {
        Utils.activityPreview(App.getContext(),
                selectedBillboard.getUrls().toArray(new String[0]),
                index,
                selectedBillboard.medias.get(index).tags.get(0).key,
                false);
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

    public void onGetBillboards(List<BillboardModel> billboards) {

    }

    public void onCreateBillboardLocation(BillboardLocationModel location) {

    }

    public void onUpdateBillboardLocation(BillboardLocationModel location) {

    }

    public void onCreateBillboardInfo(BillboardModel billboard) {

    }

    public void onUpdateBillboardInfo(BillboardModel billboard) {

    }

    public void onCreateBillboardMedia(BillboardMediaModel media) {

    }

    public void onUpdateBillboardMedia(BillboardMediaModel media) {

    }

    public void onCreateBillboardStatus(BillboardStatusModel status) {

    }

    public void onUpdateBillboardStatus(BillboardStatusModel status) {

    }




    /**
     * Alert ->
     * showToast
     * showSnackBar
     * showDialogLocationAccess
     */

    private void showToast(String message) {
        Utils.toast(App.getContext(), Enumerates.Message.ERROR, message, Toast.LENGTH_LONG);
    }

    private void showDialogLocationAccess() {
        final Dialog dialog = new Dialog(this);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);

        TextView header_text = dialog.findViewById(R.id.header_text);
        TextView text_body = dialog.findViewById(R.id.text_body);
        Button cancel = dialog.findViewById(R.id.btn_cancel);
        Button ok = dialog.findViewById(R.id.btn_ok);

        header_text.setText(getString(R.string.title_location_access));
        text_body.setText(getString(R.string.message_error_required_location_access));

        ok.setOnClickListener((view) -> {
            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            dialog.dismiss();
        });
        cancel.setOnClickListener((view) -> dialog.dismiss());

        dialog.show();
    }

    private void showDialogLocationPermission() {
        final Dialog dialog = new Dialog(this);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);

        TextView header_text = dialog.findViewById(R.id.header_text);
        TextView text_body = dialog.findViewById(R.id.text_body);
        Button cancel = dialog.findViewById(R.id.btn_cancel);
        cancel.setVisibility(View.GONE);
        Button ok = dialog.findViewById(R.id.btn_ok);

        header_text.setText(getString(R.string.title_location_permission));
        text_body.setText(getString(R.string.message_error_required_location_permission));

        ok.setOnClickListener((view) -> {
            dialog.dismiss();
        });

        dialog.show();
    }



    /**
     * Private classes ->
     * InfoPagerAdapter
     */

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
            b.advertiser = "billboard advertiser " + i;
            b.format = "96 Sheets";
            b.size = "billboard size " + i;
            b.environment = "Roadside";
            b.lighting = true;
            b.no_panels = 3;
            b.speed_limit = "<30";
            b.type = Flags.STATIC;
            b.created_at = "2020-01-11T08:15:39.736Z";
            b.updated_at = "2020-01-14T08:15:39.736Z";

            b.is_new = false;

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

            for (int j = 0; j < 11; j++) {
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

            for (int j = 0; j < 7; j++) {
                b.status.medias.add("https://media.gettyimages.com/photos/powder-explosion-picture-id642320289?s=2048x2048");
            }

            billboards.add(b);
        }
    }
}
