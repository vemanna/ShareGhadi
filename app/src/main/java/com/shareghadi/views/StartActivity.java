package com.shareghadi.views;

import android.os.Bundle;
import android.view.View;

import com.shareghadi.R;

public class StartActivity extends BaseActivity implements View.OnClickListener {

    int ids[] = {R.id.btn_sign, R.id.btn_register};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        findViewIdsWithListener(ids, this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_sign:
                launchIntent(LoginActivity.class, null);
                break;
            case R.id.btn_register :
                launchIntent(RegistrationActivity.class,null);
        }
    }
}
