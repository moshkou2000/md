package com.moshkou.md.interfaces;

import com.moshkou.md.models.BillboardModel;

public interface OnSearchListener {
    void onSearchInteraction(BillboardModel item);
    void onSearchHasResult(boolean hasResult);
}
