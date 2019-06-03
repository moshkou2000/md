package com.moshkou.md.activities;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.moshkou.md.R;
import com.moshkou.md.configs.Enumerates;
import com.moshkou.md.helpers.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {


    private final Context context = this;

    private GoogleMap map = null;
    private HeatmapTileProvider provider = null;
    private TileOverlay overlay = null;

    private MapStyleOptions mapStyle = null;
    private LatLng myLocation = new LatLng(37.774546, -122.433523);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setTitle("Test Title");
//        toolbar.setSubtitle("Test Subtitle");
        toolbar.inflateMenu(R.menu.map);
        toolbar.setNavigationOnClickListener(view -> finish());
        toolbar.setOnMenuItemClickListener(menuItem -> setSelectedStyle(menuItem.getItemId()));
    }

    @Override
    public void onBackPressed() {
        finish();
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        // Store the selected map style, so we can assign it when the activity resumes.
//        outState.putInt(Keys.STYLE, mapStyle);
//        super.onSaveInstanceState(outState);
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.addMarker(new MarkerOptions().position(myLocation).title("Marker in myLocation"));
        map.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
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
            case R.id.action5:
                takeSnapshot();
                break;
            case R.id.action10:
                addHeatMap();
                break;
            case R.id.action11:
                changeOpacity(0.7);
                break;
            case R.id.action12:
                removeHeatMap();
                break;
            default:
                mapStyle = null;
                break;
        }
        map.setMapStyle(mapStyle);

        return true;
    }


    private void takeSnapshot() {
        if (map == null) {
            return;
        }

        final ImageView snapshotHolder = findViewById(R.id.snapshot_holder);

        final GoogleMap.SnapshotReadyCallback callback = snapshotHolder::setImageBitmap;

        map.setOnMapLoadedCallback(() -> map.snapshot(callback));
    }


    private void addHeatMap() {
        // Create the gradient.
        int[] colors = {
                Color.rgb(102, 225, 0), // green
                Color.rgb(255, 0, 0)    // red
        };

        float[] startPoints = {
                0.2f, 1f
        };

        Gradient gradient = new Gradient(colors, startPoints);

        List<LatLng> list = Utils.getMapData(this, R.raw.police_stations);

        // Create a heat map tile provider, passing it the latlngs of the police stations.
        provider = new HeatmapTileProvider.Builder()
                .data(list)
                .gradient(gradient)
                .build();
        // Add a tile overlay to the map, using the heat map tile provider.
        overlay = map.addTileOverlay(new TileOverlayOptions().tileProvider(provider));
    }


    private void changeOpacity(double opacity) {
        provider.setOpacity(opacity);
        overlay.clearTileCache();
    }


    private void removeHeatMap() {
        overlay.remove();
        overlay.clearTileCache();
    }



}
