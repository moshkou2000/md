package com.moshkou.md.configs;


import com.moshkou.md.models.KeyValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Config {

    public static final String TEST_URL                 = "https://jsonplaceholder.typicode.com/todos/1";

    public static final String SG_LOGIN_URL             = "https://api.sgdev4.apptractive.com.my/api/auth/login";
    public static final String SG_LICENSE_URL           = "https://api.sgdev4.apptractive.com.my/api/auth/license";
    public static final String SG_CDN                   = "https://cdn.sgdev4.apptractive.com.my/cdn/media";

    private static final String BASE_URL                = "https://DOMAIN/api/";
    public static final String LOGIN_URL                = BASE_URL + "auth/login";
    public static final String BILLBOARDS_URL           = BASE_URL + "billboards";
    public static final String BILLBOARD_LOCATION_URL   = BASE_URL + "billboards/location";
    public static final String BILLBOARD_INFO_URL       = BASE_URL + "billboards/info";
    public static final String BILLBOARD_MEDIA_URL      = BASE_URL + "billboards/media";
    public static final String BILLBOARD_STATUS_URL     = BASE_URL + "billboards/status";


    public static final String REGEX_NUMBER             = "^[0]{1}[1]{1}[0-9]{8,9}$";
    public static final String PATTER_DATETIME          = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static final String SG_LOGIN_EMAIL           = "hassan.n@tractive.com.my";
    public static final String SG_LOGIN_PASSWORD        = "123456";
    public static final String SG_LICENSE_KEY           = "67a02356-87bd-4125-8371-df4241c2c9c5";

    public static final List<String> MONTH              = new ArrayList<>(
            Arrays.asList(
                    "JAN",
                    "FEB",
                    "MAR",
                    "APR",
                    "MAY",
                    "JUN",
                    "JUL",
                    "AUG",
                    "SEPT",
                    "OCT",
                    "NOV",
                    "DEC"));
    public static final List<String> WEEKDAY            = new ArrayList<>(
            Arrays.asList(
                    "SUNDAY",
                    "MONDAY",
                    "TUESDAY",
                    "WEDNESDAY",
                    "THURSDAY",
                    "FRIDAY",
                    "SATURDAY"));

    public static final List<String> STATES             = new ArrayList<>(
            Arrays.asList(
//                    "Select State",
                    "Johor",
                    "Kedah",
                    "Kelantan",
                    "Kuala Lumpur",
                    "Labuan",
                    "Melaka",
                    "Negeri Sembilan",
                    "Pahang",
                    "Perak",
                    "Perlis",
                    "Pulau Pinang",
                    "Putrajaya",
                    "Sabah",
                    "Sarawak",
                    "Selangor",
                    "Terengganu"));
    
    public static final List<KeyValue> CITIES             = new ArrayList<>(
            Arrays.asList(
                    new KeyValue("Johor", Arrays.asList(
//                            "Select City",
                            "Ayer Baloi",
                            "Ayer Hitam", 
                            "Ayer Tawar 2", 
                            "Bandar Penawar", 
                            "Bandar Tenggara", 
                            "Batu Anam", 
                            "Batu Pahat", 
                            "Bekok", 
                            "Benut", 
                            "Bukit Gambir", 
                            "Bukit Pasir", 
                            "Chaah", 
                            "Endau", 
                            "Gelang Patah", 
                            "Gerisek", 
                            "Gugusan Taib Andak", 
                            "Jementah", 
                            "Johor Bahru", 
                            "Kahang", 
                            "Kluang", 
                            "Kota Tinggi", 
                            "Kukup", 
                            "Kulai", 
                            "Labis", 
                            "Layang-Layang", 
                            "Masai", 
                            "Mersing",
                            "Muar", 
                            "Nusajaya", 
                            "Pagoh", 
                            "Paloh", 
                            "Panchor", 
                            "Parit Jawa", 
                            "Parit Raja", 
                            "Parit Sulong", 
                            "Pasir Gudang", 
                            "Pekan Nenas", 
                            "Pengerang", 
                            "Pontian", 
                            "Pulau Satu", 
                            "Rengam", 
                            "Rengit", 
                            "Segamat", 
                            "Semerah", 
                            "Senai", 
                            "Senggarang", 
                            "Seri Gading", 
                            "Seri Medan", 
                            "Simpang Rengam", 
                            "Sungai Mati", 
                            "Tangkak", 
                            "Ulu Tiram", 
                            "Yong Peng")),
                    new KeyValue("Kedah", Arrays.asList(
//                            "Select City",
                            "Alor Setar",
                            "Bedong", 
                            "Gurun", 
                            "Jitra", 
                            "Kuah", 
                            "Kuala Kedah", 
                            "Kulim", 
                            "Pokok Sena", 
                            "Sungai Petani")),
                    new KeyValue("Kelantan", Arrays.asList(
//                            "Select City",
                            "Gua Musang",
                            "Kampong Kadok", 
                            "Kampong Pangkal Kalong", 
                            "Kampung Lemal", 
                            "Ketereh", 
                            "Kota Bharu", 
                            "Pasir Mas", 
                            "Peringat", 
                            "Pulai Chondong", 
                            "Tanah Merah", 
                            "Tumpat")),
                    new KeyValue("Kuala Lumpur", Arrays.asList(
//                            "Select City",
                            "Ampang",
                            "Cheras", 
                            "Kuala Lumpur", 
                            "Sentul")),
                    new KeyValue("Labuan", Arrays.asList(
//                            "Select City",
                            "Victoria")),
                    new KeyValue("Melaka", Arrays.asList(
//                            "Select City",
                            "Alor Gajah",
                            "Ayer Keroh", 
                            "Ayer Molek", 
                            "Batu Berendam", 
                            "Bemban", 
                            "Bukit Baru", 
                            "Bukit Rambai", 
                            "Jasin", 
                            "Klebang Besar", 
                            "Kuala Sungai Baru", 
                            "Masjid Tanah", 
                            "Melaka", 
                            "Pulau Sebang", 
                            "Sungai Udang")),
                    new KeyValue("Negeri Sembilan", Arrays.asList(
//                            "Select City",
                            "Bahau",
                            "Kuala Klawang", 
                            "Kuala Pilah", 
                            "Nilai", 
                            "Port Dickson", 
                            "Seremban", 
                            "Tampin")),
                    new KeyValue("Pahang", Arrays.asList(
//                            "Select City",
                            "Bentong Town",
                            "Bukit Tinggi", 
                            "Jerantut", 
                            "Kuala Lipis", 
                            "Kuantan", 
                            "Mentekab", 
                            "Pekan", 
                            "Raub", 
                            "Tanah Rata", 
                            "Temerluh")),
                    new KeyValue("Perak", Arrays.asList(
//                            "Select City",
                            "Bagan Serai",
                            "Batu Gajah", 
                            "Bidor", 
                            "Ipoh", 
                            "Kampar", 
                            "Kuala Kangsar", 
                            "Lumut", 
                            "Pantai Remis", 
                            "Parit Buntar", 
                            "Simpang Empat", 
                            "Sitiawan", 
                            "Taiping", 
                            "Tapah Road", 
                            "Teluk Intan")),
                    new KeyValue("Perlis", Arrays.asList(
//                            "Select City",
                            "Arau",
                            "Kangar", 
                            "Kuala Perlis")),
                    new KeyValue("Pulau Pinang", Arrays.asList(
//                            "Select City",
                            "Bayan Lepas", 
                            "Bukit Mertajam", 
                            "Butterworth", 
                            "George Town", 
                            "Juru", 
                            "Kepala Batas", 
                            "Nibong Tebal", 
                            "Perai", 
                            "Permatang Kuching", 
                            "Sungai Ara", 
                            "Tanjung Tokong", 
                            "Tasek Glugor")),
                    new KeyValue("Putrajaya", Arrays.asList(
//                            "Select City",
                            "Putrajaya")),
                    new KeyValue("Sabah", Arrays.asList(
//                            "Select City",
                            "Bandar Labuan",
                            "Beaufort", 
                            "Donggongon", 
                            "Keningau", 
                            "Kinarut", 
                            "Kota Belud", 
                            "Kota Kinabalu", 
                            "Kudat", 
                            "Lahad Datu", 
                            "Papar", 
                            "Putatan", 
                            "Ranau", 
                            "Sandakan", 
                            "Semporna", 
                            "Tawau")),
                    new KeyValue("Sarawak", Arrays.asList(
//                            "Select City",
                            "Bintulu",
                            "Kapit", 
                            "Kuching", 
                            "Limbang", 
                            "Miri", 
                            "Sarikei", 
                            "Sibu", 
                            "Simanggang", 
                            "Sri Aman")),
                    new KeyValue("Selangor", Arrays.asList(
//                            "Select City",
                            "Balakong",
                            "Bangi", 
                            "Banting", 
                            "Batang Berjuntai", 
                            "Batu Arang", 
                            "Beranang", 
                            "Cyberjaya", 
                            "Jenjarum", 
                            "Kajang", 
                            "Klang", 
                            "Kuala Selangor", 
                            "Kuang", 
                            "Kundang", 
                            "Petaling Jaya", 
                            "Puchong", 
                            "Rawang", 
                            "Sabak Bernam", 
                            "Selayang", 
                            "Semenyih", 
                            "Serendah", 
                            "Seri Kembangan", 
                            "Shah Alam", 
                            "Subang", 
                            "Subang Jaya", 
                            "Sungai Besar", 
                            "Sungai Pelek", 
                            "Tanjung Karang", 
                            "Tanjung Sepat")),
                    new KeyValue("Terengganu", Arrays.asList(
//                            "Select City",
                            "Cukai",
                            "Jertih", 
                            "Kertih", 
                            "Kuala Dungun", 
                            "Kuala Terengganu", 
                            "Marang", 
                            "Paka"))
                    ));

    public static final List<String> MEDIA_OWNER            = new ArrayList<>(Arrays.asList(
//            "Select Media Owner"
    ));
    public static final List<String> ADVERTISER          = new ArrayList<>(Arrays.asList(
//            "Select Advertiser"
    ));
    public static final List<String> FORMAT                 = new ArrayList<>(Arrays.asList(
//            "Select Format",
            "Citylight",
            "Spectacular",
            "96 Sheets",
            "OHB",
            "Digital Screen",
            "Unipole" ));
    public static final List<String> ENVIRONMENT            = new ArrayList<>(Arrays.asList(
//            "Select Environment",
            "Roadside"
    ));

    public static final int MIN_YEAR                        = 1900;

    public static final int MEDIA_REQUEST_CODE_KITKAT       = 102;


}
