package com.moshkou.md.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moshkou.md.adapters.AlphabetsAdapter;
import com.moshkou.md.adapters.ContactsAdapter;
import com.moshkou.md.BuildConfig;
import com.moshkou.md.configs.Permission;
import com.moshkou.md.configs.RequestCode;
import com.moshkou.md.controls.ContactsControl;
import com.moshkou.md.models.ContactModel;
import com.moshkou.md.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContactsActivity extends Activity {


    private Activity activity = this;

    private LinearLayout permissionView;
    private RelativeLayout grantedView;
    private TextView allow;
    private EditText search;
    private ListView listView;
    private ListView indicators;
    private TextView indicator;

    private List<String> alphabets;
    private List<ContactModel> contacts;
    private AlphabetsAdapter alphabetsAdapter;
    private ContactsAdapter adapter;

    private boolean flagAllow = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        permissionView = findViewById(R.id.permissionView);
        grantedView = findViewById(R.id.grantedView);
        allow = findViewById(R.id.allow);
        search = findViewById(R.id.search);
        listView = findViewById(R.id.contacts);
        indicators = findViewById(R.id.indicators);
        indicator = findViewById(R.id.indicator);


        indicators.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                boolean flag = true;
                int index;
                for (index = 0; flag && index < contacts.size(); index++) {
                    if (contacts.get(index).name.toUpperCase().startsWith(alphabets.get(position))) {
                        flag = false;
                    }
                }

                listView.setSelection(index - 1);
            }
        });

        allow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagAllow = true;

                Permission.Check.READ_CONTACTS(activity);
                if (Permission.READ_CONTACTS)
                    setAdapter();
            }
        });

        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence cs, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable arg0) {}

            @Override
            public void onTextChanged(CharSequence cs, int start, int before, int count) {
                if (start + before + count == 0)
                    adapter.restore();
                else
                    adapter.filter(cs);
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (contacts != null && contacts.size() > 0) {
                    indicator.setText("" + contacts.get(firstVisibleItem).name.toUpperCase().charAt(0));
                }
            }
        });

        Permission.Check.READ_CONTACTS(this);
        if (Permission.READ_CONTACTS)
            setAdapter();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case RequestCode.READ_CONTACTS:
                if (permissions.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setAdapter();
                } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                    grantedView.setVisibility(View.GONE);
                    permissionView.setVisibility(View.VISIBLE);
                } else if (flagAllow) {
                    startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
                }
                break;
        }

        flagAllow = false;
    }

    private void setAdapter() {
        grantedView.setVisibility(View.VISIBLE);
        permissionView.setVisibility(View.GONE);

        alphabets = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.alphabets)));
        alphabetsAdapter = new AlphabetsAdapter(activity, alphabets);
        indicators.setAdapter(alphabetsAdapter);

        contacts = ContactsControl.getContacts(activity);
        adapter = new ContactsAdapter(activity, contacts);
        listView.setAdapter(adapter);
    }

}
