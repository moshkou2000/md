package com.moshkou.md.Helpers;

public interface CameraSupport {
    CameraSupport open(int cameraId);
    int getOrientation(int cameraId);
}