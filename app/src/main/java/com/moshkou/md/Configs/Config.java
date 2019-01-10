package com.moshkou.md.Configs;


import android.os.Environment;

import java.io.File;

public class Config {
    public static final String APP_NAME                 = "DS";

    //private static String IP                            = "http://192.168.1.157";
    //private static String IP                            = "http://192.168.1.20";
    //private static String IP                            = "http://192.168.1.34";
    private static final String IP                      = "https://sgdev.apptractive.com.my";

    private static final String BASE_URL                = IP + ":3020/api/";
    public static final String CDN_URL                  = IP + ":3008/cdn";
    public static final String PREVIEW_URL              = IP + ":3008/watchdog";

    public static final String LOGIN_URL                = BASE_URL + "auth/login";
    public static final String LICENSE_URL              = BASE_URL + "auth/license";
    public static final String TEMPLATE_READ_URL        = BASE_URL + "template/read/all";

    public static final String MEDIA_READ_URL           = BASE_URL + "media/read/all";
    public static final String MEDIA_DELETE_URL         = BASE_URL + "media/delete";
    public static final String MEDIA_CREATE_URL         = CDN_URL + "/media/create";

    public static final String PLAYERS_READ_URL         = BASE_URL + "category/read/tree";
    public static final String PLAYER_READ_URL          = BASE_URL + "schedule/read/player";

    public static final String PLAYLIST_READ_URL        = BASE_URL + "playlist/read/playlist-medias";
    public static final String PLAYLIST_DELETE_URL      = BASE_URL + "playlist/delete";
    public static final String PLAYLIST_CREATE_URL      = BASE_URL + "playlist/create";

    public static final String CAMPAIGN_READ_URL        = BASE_URL + "campaign-category/read/all";

    public static double DEVICE_WIDTH                   = 360;
    public static double DEVICE_HEIGHT                  = 640;
    public static double DEVICE_DENSITY                 = 1;
    public static int SIZE_2_COLUMNS                    = 180;
    public static int SIZE_3_COLUMNS                    = 120;
    public static int SIZE_35_COLUMNS                   = 80;

    public enum MediaType {
        small_320x180,
        small_180x320,
        medium_360x640,
        medium_640x360,
        large_1024x576,
        large_576x1024,
        xlarge_1080x1920,
        xlarge_1920x1080
    }
    public enum OrientationType {
        portrait,
        landscape,
        normal
    }
    public static final String[] ALLOWED_IMAGE_TYPES    = { "jpg", "jpeg", "png", "gif" };
    public static final String[] ALLOWED_VIDEO_TYPES    = { "mp4", "3gp" };

    public static final String REGEX_NUMBER             = "^[0-9]*$";
}
