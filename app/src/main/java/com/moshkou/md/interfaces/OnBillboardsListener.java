package com.moshkou.md.interfaces;

import com.moshkou.md.models.BillboardMediaModel;
import com.moshkou.md.models.BillboardModel;
import com.moshkou.md.models.BillboardLocationModel;
import com.moshkou.md.models.BillboardStatusModel;

import java.util.List;


public interface OnBillboardsListener {
    void onCreateBillboard(BillboardModel billboard);
    void onGetBillboards(List<BillboardModel> billboards);
    void onCreateBillboardLocation(BillboardLocationModel location);
    void onUpdateBillboardLocation(BillboardLocationModel location);
    void onCreateBillboardInfo(BillboardModel billboard);
    void onUpdateBillboardInfo(BillboardModel billboard);
    void onCreateBillboardMedia(BillboardMediaModel media);
    void onUpdateBillboardMedia(BillboardMediaModel media);
    void onCreateBillboardStatus(BillboardStatusModel status);
    void onUpdateBillboardStatus(BillboardStatusModel status);
}
