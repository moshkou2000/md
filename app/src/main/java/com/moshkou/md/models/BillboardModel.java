package com.moshkou.md.models;

import android.util.Log;

import com.moshkou.md.App;
import com.moshkou.md.R;
import com.moshkou.md.configs.Flags;

import java.util.ArrayList;
import java.util.List;

public class BillboardModel {

    public String _id = "";
    public String name = "";
    public String media_owner = "";
    public String product = "";
    public String brand = "";
    public String advertiser = "";
    public String format = "";
    public String size = "";
    public String environment = "";
    public boolean lighting = false;
    public Integer no_panels = 1;
    public String speed_limit = "<30";
    public String type = Flags.STATIC;
    public String created_at = "";
    public String updated_at = "";

    public BillboardLocationModel location = new BillboardLocationModel();
    public List<BillboardMediaModel> medias = new ArrayList<>();
    public BillboardStatusModel status = new BillboardStatusModel();

    public boolean is_updated = false;

    public List<String> getMedias() {
        List<String> data = new ArrayList<>();
        for (BillboardMediaModel m: medias) {
            data.add(m.media);
        }
        return data;
    }

    public int getNoPanelsIndex() {
        return no_panels - 1;
    }

    public int getSpeedLimitIndex() {
        if (speed_limit.equals(App.getContext().getResources().getString(R.string.speed_limits_1)))
            return 0;
        else if (speed_limit.equals(App.getContext().getResources().getString(R.string.speed_limits_2)))
            return 1;
        else if (speed_limit.equals(App.getContext().getResources().getString(R.string.speed_limits_3)))
            return 2;
        else if (speed_limit.equals(App.getContext().getResources().getString(R.string.speed_limits_4)))
            return 3;
        else if (speed_limit.equals(App.getContext().getResources().getString(R.string.speed_limits_5)))
            return 4;

        return 0;
    }

    public List<KeyValue> getKeyValues() {
        List<KeyValue> data = new ArrayList<>();
        for (BillboardMediaModel m: medias) {
            for (KeyValue t: m.tags) {
                boolean has = false;
                for (KeyValue d: data) {
                    if (t.key.equals(d.key) && t.value.equals(d.value)) {
                        has = true;
                        break;
                    }
                }
                if (!has) {
                    data.add(t);
                }
            }
        }
        return data;
    }
}
