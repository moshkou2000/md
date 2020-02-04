package com.moshkou.md.interfaces;


public interface OnAdapterListener {
    void onDeleteMedia(int index);
    void onDelete(int index);
    void onUpdate(int index);
    void onInteresting(int index, boolean isInteresting);
}
