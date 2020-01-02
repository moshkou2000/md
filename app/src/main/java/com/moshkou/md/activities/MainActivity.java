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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.moshkou.md.adapters.AutoCompleteAdapter;
import com.moshkou.md.configs.Enumerates;
import com.moshkou.md.configs.RequestCode;
import com.moshkou.md.controls.DraggingPanel;
import com.moshkou.md.fragments.BillboardFragment;
import com.moshkou.md.fragments.LocationFragment;
import com.moshkou.md.fragments.MediaFragment;
import com.moshkou.md.fragments.StatusFragment;
import com.moshkou.md.helpers.Utils;
import com.moshkou.md.R;
import com.moshkou.md.interfaces.OnFragmentInteractionListener;
import com.moshkou.md.interfaces.OnSearchListener;
import com.moshkou.md.models.BillboardModel;
import com.moshkou.md.models.LocationModel;

import java.util.ArrayList;
import java.util.List;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_TERRAIN;


public class MainActivity extends FragmentActivity implements
        OnMapReadyCallback, OnFragmentInteractionListener, OnSearchListener {


    private static String TAG = "MAIN";

    private final Context context = this;

    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private TextView textSearch;
    private RecyclerView listSearch;
    private Button buttonFilter;
    private Button buttonMore;

    private DraggingPanel layoutInfo;
    private FrameLayout layoutDetails;



    private LocationFragment locationFragment;
    private BillboardFragment billboardFragment;
    private MediaFragment mediaFragment;
    private StatusFragment statusFragment;

    private DraggingPanel draggingPanel;

    private GoogleMap map = null;
    private HeatmapTileProvider provider = null;
    private TileOverlay overlay = null;

    private MapStyleOptions mapStyle = null;
    private int mapType = MAP_TYPE_NORMAL;
    private LatLng myLocation = new LatLng(37.774546, -122.433523);
    private static final int[] TAB_TITLES = new int[] { R.string.placeholder_name, R.string.placeholder_email, R.string.placeholder_login, R.string.placeholder_phone };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        pager = findViewById(R.id.pager);
        draggingPanel = findViewById(R.id.layout_info);
        textSearch = findViewById(R.id.text_search);
        listSearch = findViewById(R.id.list_search);
        buttonFilter = findViewById(R.id.button_filter);
        buttonMore = findViewById(R.id.button_more);
        layoutInfo = findViewById(R.id.layout_info);
        layoutDetails = findViewById(R.id.layout_details);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initPager();
        initSearch();
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
        map.addMarker(new MarkerOptions().position(myLocation).title("Marker in myLocation"));
        map.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
    }


    private void initSearch() {
        textSearch.setOnFocusChangeListener((v, hasFocus) -> {
            if (v.getId() == R.id.text_search) {
                if (hasFocus) {
                    layoutInfo.setVisibility(View.GONE);
                    layoutDetails.setVisibility(View.GONE);
                }
            }
        });
        textSearch.setOnKeyListener((v, keyCode, event) -> {
            // If the event is a key-down event on the "enter" button
            //noinspection RedundantIfStatement
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                layoutInfo.setVisibility(View.VISIBLE);
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

        AutoCompleteAdapter adapter = new AutoCompleteAdapter(context, getData(), this);
        listSearch.setAdapter(adapter);

        buttonFilter.setOnClickListener(view ->
                Log.i(TAG, "buttonFilter")
        );
        buttonMore.setOnClickListener(view ->
                Log.i(TAG, "buttonMore")
        );
    }

    @Override
    public void onSearchInteraction(BillboardModel item) {
        Log.i(TAG, "::::: " + item.name);
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
        }

        return data;
    }


    private boolean setSelectedStyle(int selectedStyleId) {
        switch (selectedStyleId) {
            case R.id.action1:
                mapStyle = MapStyleOptions.loadRawResourceStyle(context, R.raw.mapstyle_retro);
                break;
            case R.id.action2:
                mapStyle = MapStyleOptions.loadRawResourceStyle(context, R.raw.mapstyle_night);
                break;
            case R.id.action3:
                mapStyle = MapStyleOptions.loadRawResourceStyle(context, R.raw.mapstyle_grayscale);
                break;
            case R.id.action4:
                mapStyle = new MapStyleOptions("[" +
                        "  {" +
                        "    \"featureType\":\"poi.business\"," +
                        "    \"elementType\":\"all\"," +
                        "    \"stylers\":[" +
                        "      {" +
                        "        \"visibility\":\"off\"" +
                        "      }" +
                        "    ]" +
                        "  }," +
                        "  {" +
                        "    \"featureType\":\"transit\"," +
                        "    \"elementType\":\"all\"," +
                        "    \"stylers\":[" +
                        "      {" +
                        "        \"visibility\":\"off\"" +
                        "      }" +
                        "    ]" +
                        "  }" +
                        "]");
                break;
            case R.id.action6:
                mapType = MAP_TYPE_NORMAL;
                break;
            case R.id.action7:
                mapType = MAP_TYPE_HYBRID;
                break;
            case R.id.action8:
                mapType = MAP_TYPE_SATELLITE;
                break;
            case R.id.action9:
                mapType = MAP_TYPE_TERRAIN;
                break;
            default:
                mapStyle = null;
                break;
        }
        //map.setMapType(mapType);
        map.setMapStyle(mapStyle);

        return true;
    }



    private void showToast() {
        Utils.toast(context, Enumerates.Message.ERROR, "Error dari na", Toast.LENGTH_LONG);
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

    private void initPager() {
        locationFragment = new LocationFragment();
        billboardFragment = new BillboardFragment();
        mediaFragment = new MediaFragment();
        statusFragment = new StatusFragment();

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        pager = findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);


        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(pager);
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

}
