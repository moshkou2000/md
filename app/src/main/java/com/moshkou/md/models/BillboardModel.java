package com.moshkou.md.models;

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
    public Integer speed_limit;

    public LocationModel location = new LocationModel();
    public List<MediaModel> medias;
    public BillboardStatusModel status;

    public boolean is_updated;
}
