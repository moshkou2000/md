package com.moshkou.md.configs;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Config {

    public final static String TEST_URL                 = "https://jsonplaceholder.typicode.com/todos/1";

    private final static String BASE_URL                = "https://sgdev.apptractive.com.my:3020/api/";
    public final static String LOGIN_URL                = BASE_URL + "auth/login";
    public final static String LICENSE_URL              = BASE_URL + "auth/license";

    public final static String REGEX_NUMBER             = "^[0-9]*$";

    public final static List<String> MONTH              = new ArrayList<String>(Arrays.asList("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEPT", "OCT", "NOV", "DEC"));
    public final static List<String> WEEKDAY            = new ArrayList<String>(Arrays.asList("SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"));

}
