package com.moshkou.md.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.moshkou.md.R;
import com.moshkou.md.adapters.GroupAdapter;
import com.moshkou.md.models.BaseDataModel;
import com.moshkou.md.models.ContactModel;

import java.util.ArrayList;
import java.util.List;

public class GroupActivity extends AppCompatActivity {


    private final Context context = this;

    private RecyclerView recyclerView;

    private List<BaseDataModel> data = new ArrayList<>();
    private GroupAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);


        // TODO: just for test
        for (int i = 0; i < 3; i++) {
            List<ContactModel> cs = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                cs.add(new ContactModel("Hassan", "+60172552542", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTV-XL-dvaC-cblm2rqA7am9z2-54v_mGnaLDokhZQqEJbXFduZng"));
            }
            data.add(new BaseDataModel("" + i * 234, "title " + i, "description asdasda sdasd asd das dasd as dads ads " + i, "location " + i, "12.02.2022", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTV-XL-dvaC-cblm2rqA7am9z2-54v_mGnaLDokhZQqEJbXFduZng", null, cs));
        }

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new GroupAdapter(context, data);
        recyclerView.setAdapter(adapter);
    }
}
