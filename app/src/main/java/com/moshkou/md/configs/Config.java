package com.moshkou.md.configs;


import com.moshkou.md.models.KeyValue;

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

    public final static List<String> MONTH              = new ArrayList<>(
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
    public final static List<String> WEEKDAY            = new ArrayList<>(
            Arrays.asList(
                    "SUNDAY",
                    "MONDAY",
                    "TUESDAY",
                    "WEDNESDAY",
                    "THURSDAY",
                    "FRIDAY",
                    "SATURDAY"));

    public final static List<String> STATES             = new ArrayList<>(
            Arrays.asList(
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
    
    public final static List<KeyValue> CITIES             = new ArrayList<>(
            Arrays.asList(
                    new KeyValue("Johor", Arrays.asList(
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
                            "Ampang",
                            "Cheras", 
                            "Kuala Lumpur", 
                            "Sentul")),
                    new KeyValue("Labuan", Arrays.asList("Victoria")),
                    new KeyValue("Melaka", Arrays.asList(
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
                            "Bahau",
                            "Kuala Klawang", 
                            "Kuala Pilah", 
                            "Nilai", 
                            "Port Dickson", 
                            "Seremban", 
                            "Tampin")),
                    new KeyValue("Pahang", Arrays.asList(
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
                            "Arau",
                            "Kangar", 
                            "Kuala Perlis")),
                    new KeyValue("Pulau Pinang", Arrays.asList(
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
                    new KeyValue("Putrajaya", Arrays.asList("Putrajaya")),
                    new KeyValue("Sabah", Arrays.asList(
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
                            "Cukai",
                            "Jertih", 
                            "Kertih", 
                            "Kuala Dungun", 
                            "Kuala Terengganu", 
                            "Marang", 
                            "Paka"))
                    ));

    public final static int MIN_YEAR                        = 1900;

    public static final int MEDIA_REQUEST_CODE_KITKAT       = 102;


}
