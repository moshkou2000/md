package com.moshkou.md.configs;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Config {

    public final static String TEST_URL                 = "https://jsonplaceholder.typicode.com/todos/1";

    private final static String BASE_URL                = "https://sgdev.apptractive.com.my:3020/api/";
    public final static String LOGIN_URL                = BASE_URL + "auth/login";
    public final static String BILLBOARDS_URL           = BASE_URL + "billboards";
    public final static String BILLBOARD_LOCATION_URL   = BASE_URL + "billboards/location";
    public final static String BILLBOARD_INFO_URL       = BASE_URL + "billboards/info";
    public final static String BILLBOARD_MEDIA_URL      = BASE_URL + "billboards/media";
    public final static String BILLBOARD_STATUS_URL     = BASE_URL + "billboards/status";

    public final static String REGEX_NUMBER             = "^[0-9]*$";
    public final static String PATTER_DATETIME          = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public final static List<String> MONTH              = new ArrayList<String>(Arrays.asList("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEPT", "OCT", "NOV", "DEC"));
    public final static List<String> WEEKDAY            = new ArrayList<String>(Arrays.asList("SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"));

    public final static int MIN_YEAR                    = 1900;

}
