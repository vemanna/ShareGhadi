package com.shareghadi.restservice;

import com.shareghadi.models.RestError;

import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * Created by BVN on 12/24/2015.
 */
public abstract class RestCallback<T> implements Callback<T> {

    public abstract void failure(RestError restError);

    @Override
    public void failure(RetrofitError error) {
        RestError restError = (RestError) error.getBodyAs(RestError.class);

        if (restError != null)
            failure(restError);
        else
        {
            failure(new RestError(error.getLocalizedMessage()));
        }
    }
}
