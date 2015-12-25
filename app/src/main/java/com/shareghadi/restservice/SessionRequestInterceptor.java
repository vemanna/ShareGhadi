package com.shareghadi.restservice;

import retrofit.RequestInterceptor;

/**
 * Created by BVN on 12/25/2015.
 */
public class SessionRequestInterceptor implements RequestInterceptor {
    private static final String TAG = SessionRequestInterceptor.class.getSimpleName();

    @Override
    public void intercept(RequestFacade request) {
        //you can add header here if you need in your api
        request.addHeader("Content-Type", "application/json");
        request.addHeader("Content-Type", "application/x-www-form-urlencoded");
    }
}
