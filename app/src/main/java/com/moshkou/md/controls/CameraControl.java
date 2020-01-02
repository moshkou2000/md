package com.moshkou.md.controls;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import androidx.annotation.NonNull;

import com.moshkou.md.configs.Permission;
import com.moshkou.md.helpers.CameraSupport;

public class CameraControl implements CameraSupport {

    private CameraDevice camera;
    private CameraManager manager;

    public CameraControl(final Activity activity) {
        Permission.Check.CAMERA(activity);

        this.manager = (CameraManager) activity.getSystemService(Context.CAMERA_SERVICE);
    }

    @SuppressLint("MissingPermission")
    @Override
    public CameraControl open(final int cameraId) {
        try {
            String[] cameraIds = manager.getCameraIdList();

            manager.openCamera(cameraIds[cameraId], new CameraDevice.StateCallback() {
                @Override
                public void onOpened(@NonNull CameraDevice camera) {
                    CameraControl.this.camera = camera;
                }

                @Override
                public void onDisconnected(@NonNull CameraDevice camera) {
                    CameraControl.this.camera = camera;
                    // TODO handle
                }

                @Override
                public void onError(@NonNull CameraDevice camera, int error) {
                    CameraControl.this.camera = camera;
                    // TODO handle
                }
            }, null);
        } catch (Exception e) {
            // TODO handle
        }
        return this;
    }

    @Override
    public int getOrientation(final int cameraId) {
        try {
            String[] cameraIds = manager.getCameraIdList();
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraIds[cameraId]);
            //noinspection ConstantConditions
            return characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
        } catch (CameraAccessException e) {
            // TODO handle
            return 0;
        }
    }
}
