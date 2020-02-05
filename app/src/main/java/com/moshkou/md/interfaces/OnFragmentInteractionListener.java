package com.moshkou.md.interfaces;

import com.moshkou.md.models.BillboardLocationModel;
import com.moshkou.md.models.BillboardModel;
import com.moshkou.md.models.BillboardStatusModel;

import java.util.List;


public interface OnFragmentInteractionListener {
    void onFragmentInteraction(BillboardModel billboard);
    void onLocationFragmentInteraction(BillboardLocationModel location);
    void onBillboardFragmentInteraction(BillboardModel billboard);
    void onStatusFragmentInteraction(BillboardStatusModel status);
}
