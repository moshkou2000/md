package com.moshkou.md.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.moshkou.md.Adapters.ContactsAdapter;
import com.moshkou.md.Configs.Flags;
import com.moshkou.md.Configs.Keys;
import com.moshkou.md.Configs.Permission;
import com.moshkou.md.Configs.RequestCode;
import com.moshkou.md.Controls.ContactsControl;
import com.moshkou.md.Helpers.Utils;
import com.moshkou.md.R;

public class ContactsActivity extends Activity {


    private Context context = this;

    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        listView = findViewById(R.id.contacts);

        if (!Permission.READ_CONTACTS)
            Permission.Check.READ_CONTACTS(this);
        else
            listView.setAdapter(new ContactsAdapter(context, ContactsControl.getContacts(context)));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case RequestCode.READ_CONTACTS:
                if (permissions.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    listView.setAdapter(new ContactsAdapter(context, ContactsControl.getContacts(context)));

                } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                    // TODO: display "Permission was denied. Display an error message."
                }
                break;

        }
    }

}
