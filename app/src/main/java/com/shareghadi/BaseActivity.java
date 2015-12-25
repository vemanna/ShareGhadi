package com.shareghadi;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by Chandu T on 11/23/2015.
 */
public class BaseActivity extends AppCompatActivity {

    public static final String EXT_BUNDLE = "bundle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setUpToolbar();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);



    }

    private void setUpToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar == null) {
            return;
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    /*set ids with listner*/
    public void findViewIdsWithListener(int ids[], View.OnClickListener listener) {

        for (int id : ids) {
            try {

                View view = findViewById(id);
                view.setOnClickListener(listener);
            } catch (NullPointerException npe) {

            }
        }

    }

    public void launchIntent(Class clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtra(EXT_BUNDLE, bundle);

        }
        startActivity(intent);
    }

    public Bundle getBundle() {

        if (getIntent() != null) {
            return getIntent().hasExtra(EXT_BUNDLE) ? getIntent().getBundleExtra(EXT_BUNDLE) : null;
        }
        return null;
    }
}
