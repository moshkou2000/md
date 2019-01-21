package com.moshkou.md.Controls;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.moshkou.md.Models.ContactModel;
import com.moshkou.md.Models.KeyValue;

import java.util.ArrayList;
import java.util.List;


public class ContactsControl {

    public static List<ContactModel> getContacts(Context context) {
        List<ContactModel> contacts = new ArrayList<>();
        Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (phones.moveToNext())
        {
            contacts.add(new ContactModel(
                    phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),
                    phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)),
                    ""
            ));

        }
        phones.close();

        return contacts;
    }
}
