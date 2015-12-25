package com.shareghadi.restservice;


import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by BVN on 12/24/2015.
 */
public class RestClient {

    private static RestApi REST_CLIENT;

    //Service URl
    private static String ROOT ="http://ubifoods.sattvaq.com/rest";

    static {
        setupRestClient();

    }

    public static RestApi get() {
        return REST_CLIENT;
    }

    private static void setupRestClient() {


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(ROOT)
                .setClient(new OkClient(new OkHttpClient()))
                .setRequestInterceptor(new SessionRequestInterceptor())
                .build();

        REST_CLIENT = restAdapter.create(RestApi.class);
    }

    public RestAdapter getRestAdapterWithRoot(String root) {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(root)
                .setClient(new OkClient(new OkHttpClient()))
                .setRequestInterceptor(new SessionRequestInterceptor())
                .build();
        return restAdapter;
    }
}
