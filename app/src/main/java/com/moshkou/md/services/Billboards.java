package com.moshkou.md.services;


import com.android.volley.Request;
import com.google.gson.Gson;
import com.moshkou.md.App;
import com.moshkou.md.configs.Config;
import com.moshkou.md.helpers.Convertor;
import com.moshkou.md.interfaces.OnBillboardsListener;
import com.moshkou.md.models.BillboardModel;
import com.moshkou.md.models.BillboardStatusModel;
import com.moshkou.md.models.BillboardLocationModel;
import com.moshkou.md.models.BillboardMediaModel;



public class Billboards {

    public static void createBillboard(OnBillboardsListener listener, BillboardModel billboard) {
        Requests request = new Requests(
                Request.Method.POST,
                Config.BILLBOARD_LOCATION_URL,
                null,
                new Gson().toJson(billboard),
                new Error(),
                respond -> listener.onCreateBillboard(Convertor.toObject(respond))
        );
        App.getInstance().addToRequestQueue(request, "createBillboard");
    }


    public static void getBillboards(OnBillboardsListener listener, int index, int limit, String query) {
        Requests request = new Requests(
                Request.Method.GET,
                Config.BILLBOARDS_URL + "?index=" + index + "&limit=" + limit + query,
                null,
                null,
                new Error(),
                respond -> listener.onGetBillboards(Convertor.toObject(respond))
        );
        App.getInstance().addToRequestQueue(request, "getBillboards");
    }


    public static void createBillboardLocation(OnBillboardsListener listener, BillboardLocationModel location) {
        Requests request = new Requests(
                Request.Method.POST,
                Config.BILLBOARD_LOCATION_URL,
                null,
                new Gson().toJson(location),
                new Error(),
                respond -> listener.onCreateBillboardLocation(Convertor.toObject(respond))
        );
        App.getInstance().addToRequestQueue(request, "createBillboardLocation");
    }


    public static void updateBillboardLocation(OnBillboardsListener listener, BillboardLocationModel location) {
        Requests request = new Requests(
                Request.Method.PUT,
                Config.BILLBOARD_LOCATION_URL,
                null,
                new Gson().toJson(location),
                new Error(),
                respond -> listener.onUpdateBillboardLocation(Convertor.toObject(respond))
        );
        App.getInstance().addToRequestQueue(request, "updateBillboardLocation");
    }


    public static void createBillboardInfo(OnBillboardsListener listener, BillboardModel billboard) {
        Requests request = new Requests(
                Request.Method.PUT,
                Config.BILLBOARD_INFO_URL,
                null,
                new Gson().toJson(billboard),
                new Error(),
                respond -> listener.onCreateBillboardInfo(Convertor.toObject(respond))
        );
        App.getInstance().addToRequestQueue(request, "createBillboardInfo");
    }


    public static void updateBillboardInfo(OnBillboardsListener listener, BillboardModel billboard) {
        Requests request = new Requests(
                Request.Method.PUT,
                Config.BILLBOARD_INFO_URL,
                null,
                new Gson().toJson(billboard),
                new Error(),
                respond -> listener.onUpdateBillboardInfo(Convertor.toObject(respond))
        );
        App.getInstance().addToRequestQueue(request, "updateBillboardInfo");
    }


    public static void createBillboardMedia(OnBillboardsListener listener, BillboardMediaModel media) {
        Requests request = new Requests(
                Request.Method.PUT,
                Config.BILLBOARD_MEDIA_URL,
                null,
                new Gson().toJson(media),
                new Error(),
                respond -> listener.onCreateBillboardMedia(Convertor.toObject(respond))
        );
        App.getInstance().addToRequestQueue(request, "createBillboardMedia");
    }


    public static void updateBillboardMedia(OnBillboardsListener listener, BillboardMediaModel media) {
        Requests request = new Requests(
                Request.Method.PUT,
                Config.BILLBOARD_MEDIA_URL,
                null,
                new Gson().toJson(media),
                new Error(),
                respond -> listener.onUpdateBillboardMedia(Convertor.toObject(respond))
        );
        App.getInstance().addToRequestQueue(request, "updateBillboardMedia");
    }


    public static void createBillboardStatus(OnBillboardsListener listener, BillboardStatusModel status) {
        Requests request = new Requests(
                Request.Method.PUT,
                Config.BILLBOARD_STATUS_URL,
                null,
                new Gson().toJson(status),
                new Error(),
                respond -> listener.onCreateBillboardStatus(Convertor.toObject(respond))
        );
        App.getInstance().addToRequestQueue(request, "createBillboardStatus");
    }


    public static void updateBillboardStatus(OnBillboardsListener listener, BillboardStatusModel status) {
        Requests request = new Requests(
                Request.Method.PUT,
                Config.BILLBOARD_STATUS_URL,
                null,
                new Gson().toJson(status),
                new Error(),
                respond -> listener.onUpdateBillboardStatus(Convertor.toObject(respond))
        );
        App.getInstance().addToRequestQueue(request, "updateBillboardStatus");
    }

}
