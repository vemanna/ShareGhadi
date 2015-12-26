package com.shareghadi.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shareghadi.R;

/**
 * Created by Chandu T on 12/25/2015.
 */
public class RideFragment extends BaseFragment {

    View rootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView=inflater.inflate(R.layout.fragment_layout,container,false);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
