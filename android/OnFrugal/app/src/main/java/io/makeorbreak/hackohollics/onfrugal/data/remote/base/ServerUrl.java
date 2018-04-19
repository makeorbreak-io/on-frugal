package io.makeorbreak.hackohollics.onfrugal.data.remote.base;


import java.util.concurrent.TimeUnit;

import io.makeorbreak.hackohollics.onfrugal.BuildConfig;

public class ServerUrl {

    private static final String DOMAIN = BuildConfig.API_HOSTNAME;

    public static final long TIMEOUT = 3;
    public static final TimeUnit TIMEOUT_TIME_UNIT = TimeUnit.SECONDS;
    public static final String AUTH = "/auth";

    public static String getUrl(){
        return DOMAIN;
    }

    //PATHS
    public static final String API = "/api";

    public static final String USER = "/user";
    public static final String UNAUTH = "/unauth";
    public static final String REGISTER = "/register";
    public static final String REGISTER_BY_PROVIDER = "/register-by-provider";

    public static final String POI = "/poi";
    public static final String RANGE = "/range";
    public static final String MEDIA = "/media";
    public static final String RATING = "/rating";
    public static final String SEARCH = "/search";
    public static final String CONTENT = "/post";
    public static final String LIKE = "/like";
    public static final String POI_CONTENT = "/poi_posts";
    public static final String ROUTE = "/route";
}
