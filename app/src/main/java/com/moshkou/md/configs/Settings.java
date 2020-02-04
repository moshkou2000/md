package com.moshkou.md.configs;

import com.moshkou.md.models.BillboardModel;
import com.moshkou.md.models.UserModel;

public class Settings {

    public static UserModel USER;

    public static String APP_PICTURE_DIRECTORY              = "";
    public static Enumerates.Connectivity CONNECTIVITY      = Enumerates.Connectivity.NO_CONNECTIVITY;

    public static double DEVICE_WIDTH                       = 0;
    public static double DEVICE_HEIGHT                      = 0;
    public static double DEVICE_DENSITY                     = 0;

    public static BillboardModel FILTER_BILLBOARD           = new BillboardModel();

    public static Enumerates.LayoutStatus LAYOUT_STATUS     = Enumerates.LayoutStatus.NULL;

    public static Enumerates.MediaPicker MEDIA_PICKER       = Enumerates.MediaPicker.NULL;
}
