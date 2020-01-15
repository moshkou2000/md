package com.moshkou.md.models;

import java.util.ArrayList;
import java.util.List;

public class BillboardModel {

    public String _id;
    public String name = "";
    public String media_owner = "";
    public String product = "";
    public String advertiser = "";
    public String format = "";
    public String size = "";
    public String environment;
    public boolean lighting;
    public Integer no_panels;
    public String speed_limit = "";
    public String type = "";
    public String created_at = "";
    public String updated_at = "";

    public BillboardLocationModel location = new BillboardLocationModel();
    public List<BillboardMediaModel> medias = new ArrayList<>();
    public BillboardStatusModel status = new BillboardStatusModel();

    public boolean is_updated;

    public List<String> getMedias() {
        List<String> data = new ArrayList<>();
        for (BillboardMediaModel m: medias) {
            data.add(m.media);
        }
        return data;
    }
}
