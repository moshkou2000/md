package com.moshkou.md.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.moshkou.md.Adapters.ContactsAdapter;
import com.moshkou.md.Configs.Flags;
import com.moshkou.md.Configs.Keys;
import com.moshkou.md.Configs.Permission;
import com.moshkou.md.Configs.RequestCode;
import com.moshkou.md.Controls.ContactsControl;
import com.moshkou.md.Helpers.Utils;
import com.moshkou.md.Models.ContactModel;
import com.moshkou.md.R;

import java.util.List;

public class ContactsActivity extends Activity {


    private Activity activity = this;

    private LinearLayout permissionView;
    private TextView allow;
    private FrameLayout header;
    private EditText search;
    private ListView listView;

    private List<ContactModel> contacts;
    private ContactsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        permissionView = findViewById(R.id.permissionView);
        allow = findViewById(R.id.allow);
        header = findViewById(R.id.header);
        search = findViewById(R.id.search);
        listView = findViewById(R.id.contacts);

        allow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Permission.Check.READ_CONTACTS(activity);
            }
        });

        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }

            @Override
            public void afterTextChanged(Editable arg0) {}

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                if (arg3 == 0)
                    adapter.restore();
                else
                    adapter.filter(cs);
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
                    header.setVisibility(View.GONE);
                    listView.setVisibility(View.GONE);
                    permissionView.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private void setAdapter() {
        header.setVisibility(View.VISIBLE);
        listView.setVisibility(View.VISIBLE);
        permissionView.setVisibility(View.GONE);

        contacts = ContactsControl.getContacts(activity);
        adapter = new ContactsAdapter(activity, contacts);
        listView.setAdapter(adapter);
    }

}
