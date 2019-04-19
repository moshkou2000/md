package com.moshkou.md.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moshkou.md.R;
import com.moshkou.md.adapters.AlphabetsAdapter;
import com.moshkou.md.adapters.ContactsAdapter;
import com.moshkou.md.adapters.OriginDestinationAdapter;
import com.moshkou.md.controls.ContactsControl;
import com.moshkou.md.models.ContactModel;
import com.moshkou.md.models.OriginDestinationModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A login screen that offers login via email/password.
 */
public class BookingListActivity extends AppCompatActivity {


    private Activity activity = this;

    private ListView listView;
    private ListView indicators;
    private TextView indicator;

    private List<String> alphabets;
    private List<OriginDestinationModel> data = new ArrayList<>();
    private OriginDestinationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_list);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        listView = findViewById(R.id.bookings);
        indicators = findViewById(R.id.indicators);
        indicator = findViewById(R.id.indicator);


        indicators.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                boolean flag = true;
                int index;
                for (index = 0; flag && index < data.size(); index++) {
                    if (data.get(index).name.toUpperCase().startsWith(alphabets.get(position))) {
                        flag = false;
                    }
                }

                listView.setSelection(index - 1);
            }
        });

        EditText search = findViewById(R.id.search);
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
                if (data != null && data.size() > 0) {
                    indicator.setText("" + data.get(firstVisibleItem).name.toUpperCase().charAt(0));
                }
            }
        });

        setAdapter();
    }

    private void setAdapter() {
        alphabets = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.alphabets)));
        AlphabetsAdapter alphabetsAdapter = new AlphabetsAdapter(activity, alphabets);
        indicators.setAdapter(alphabetsAdapter);

        // TODO: populate data
        //
        for (int i = 0; i < 120; i++) {
            data.add(new OriginDestinationModel("" + i + "name " + i, "code " + i, "city " + i, "country " + i));
        }

        adapter = new OriginDestinationAdapter(activity, data);
        listView.setAdapter(adapter);
    }

}

