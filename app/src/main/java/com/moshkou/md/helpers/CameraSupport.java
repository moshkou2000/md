package com.moshkou.md.helpers;

public interface CameraSupport {
    CameraSupport open(int cameraId);
    int getOrientation(int cameraId);
}