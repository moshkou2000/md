package com.moshkou.md.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moshkou.md.App;
import com.moshkou.md.R;
import com.moshkou.md.configs.Flags;
import com.moshkou.md.configs.Keys;
import com.moshkou.md.helpers.Utils;
import com.moshkou.md.models.BillboardMediaModel;
import com.moshkou.md.models.BillboardModel;
import com.moshkou.md.models.KeyValue;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class MediaActivity extends AppCompatActivity {


    private static String TAG = "MEDIA";


    private EditText autoCompleteBrand;
    private EditText autoCompleteProduct;
    private CheckBox checkboxInteresting;
    private ImageView image;
    private Button buttonCamera;
    private Button buttonGallery;

    private BillboardMediaModel selectedMedia = new BillboardMediaModel();




    /**
     * Override functions ->
     * onCreate
     * onDestroy
     * onNewIntent
     * onBackPressed
     **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        checkboxInteresting = findViewById(R.id.checkbox_interesting);
        autoCompleteBrand = findViewById(R.id.auto_complete_brand);
        autoCompleteProduct = findViewById(R.id.auto_complete_product);
        image = findViewById(R.id.image);
        buttonCamera = findViewById(R.id.button_camera);
        buttonGallery = findViewById(R.id.button_gallery);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_media);
        toolbar.inflateMenu(R.menu.filter);
        toolbar.setNavigationOnClickListener(view -> backPressed());
        toolbar.setOnMenuItemClickListener(menuItem -> setSelectedStyle(menuItem.getItemId()));

        init();
        getExtra();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        getExtra();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            try {
                InputStream input = getApplicationContext()
                        .getContentResolver()
                        .openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                image.setImageBitmap(bitmap);
                toggleButtons();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        backPressed();
    }




    /**
     * Init functions ->
     * init
     * getExtra
     */

    private void init() {
        buttonCamera.setOnClickListener(v -> startActivity(new Intent(App.getContext(), CameraActivity.class)));
        buttonGallery.setOnClickListener(v -> Utils.openDefaultGalleryApp(this));
    }




    /**
     * Helper functions ->
     * getExtra
     * backPressed
     * setSelectedStyle
     * clear
     * done
     * toggleButtons
     */

    private void getExtra() {
        Intent i = getIntent();

//        if (i.hasExtra(Keys.DATA)) {
//            selectedBillboard = new Gson().fromJson(i.getStringExtra(Keys.DATA), BillboardModel.class);
//        } else
        if (i.hasExtra(Keys.URI)) {
            try {
                Log.i(TAG, "+++++++++++++++++++++++++++1111: " + i.getStringExtra(Keys.URI));
                selectedMedia.media = i.getStringExtra(Keys.URI);

                InputStream input = App.getContext()
                        .getContentResolver()
                        .openInputStream(i.getParcelableExtra(Keys.URI));
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                image.setImageBitmap(bitmap);
                toggleButtons();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void backPressed() {
        Utils.hideKeyboard(this);
        new Handler().postDelayed(() -> {
            Intent i = new Intent(App.getContext(), BillboardActivity.class);
            i.putExtra(Keys.TYPE, Flags.MEDIA);
            selectedMedia.is_interesting = checkboxInteresting.isChecked();
            selectedMedia.tags.add(
                    new KeyValue(autoCompleteProduct.getText().toString(),
                            autoCompleteProduct.getText().toString()));
            Type type = new TypeToken<BillboardMediaModel>(){}.getType();
            i.putExtra(Keys.DATA, new Gson().toJson(selectedMedia, type));
            startActivity(i);
            finish();
        }, 200);
    }

    private boolean setSelectedStyle(int selectedStyleId) {
        switch (selectedStyleId) {
            case R.id.action1:
                clear();
                break;
            case R.id.action2:
                done();
                break;
            default:
                break;
        }

        return true;
    }

    private void clear() {
        backPressed();
    }

    private void done() {
        // TODO: add new media

        backPressed();
    }

    private void toggleButtons() {
        buttonCamera.setVisibility(View.GONE);
        buttonGallery.setVisibility(View.GONE);
    }


}
