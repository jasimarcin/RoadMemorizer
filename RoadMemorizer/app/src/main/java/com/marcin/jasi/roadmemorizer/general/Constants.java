package com.marcin.jasi.roadmemorizer.general;

public class Constants {

    public static final String EMPTY_STRING = "";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "roadMemorizerDb.db";

    // location settings
    public static final int LOCATION_INTERVAL = 1000;
    public static final float LOCATION_DISTANCE = 25f;
    public static final boolean ENABLE_NETWORK_PROVIDER = false;

    public static final int REQUEST_CODE_ASK_PERMISSION = 123;
    public static final int SCREENSHOT_ROUTE_PADDING = 100;
    public static final String ROAD_ID_KEY = "roadIdBundleKey";

    public static final String CURRENT_LOCATION_FRAGMENT_TITLE = "Aktualna lokalizacja";
    public static final String ROADS_ARCHIVE_FRAGMENT_TITLE = "Zapisane trasy";
    public static final String ROADS_LOADER_FRAGMENT_TITLE = "Zapisana trasa";

    public static final String GOOGLE_PLACES_URL = "https://maps.googleapis.com/";
    public static final String GOOGLE_PLACES_ID_URL = "https://maps.googleapis.com/maps/api/geocode/json?latlng=%s,%s&sender=false";

    public static final String API_KEY_NAME = "com.google.android.maps.v2.API_KEY";

}
