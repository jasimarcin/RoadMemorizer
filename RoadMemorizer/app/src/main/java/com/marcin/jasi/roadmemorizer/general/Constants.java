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

}
