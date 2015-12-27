package com.shareghadi.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shareghadi.R;
import com.shareghadi.database.SQLiteDAO;
import com.shareghadi.models.SignUp;
import com.shareghadi.util.LogUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.shareghadi.util.LogUtil.LOGD;

/**
 * Created by Chandu T on 11/23/2015.
 */
public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = LogUtil.makeLogTag(BaseActivity.class);
    public static final String EXT_BUNDLE = "bundle";
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private CircleImageView circleImageView;
    private TextView tv_userName;
    private RelativeLayout headerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setUpToolbar();
        setUpNavDrawer();
    }

    private void setUpNavDrawer() {


        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView == null) {
            return;
        }
        navigationView.setNavigationItemSelectedListener(this);

        initView();
        setNavigationHeaderImageAndName();
        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();


    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);


    }

    private void setUpToolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
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

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        //Checking if the item is in checked state or not, if not make it in checked state
        if (menuItem.isChecked()) menuItem.setChecked(false);
        else menuItem.setChecked(true);

        //Closing drawer on item click
        drawerLayout.closeDrawers();

        //Check to see which item was being clicked and perform appropriate action
        switch (menuItem.getItemId()) {

            //Replacing the main content with ContentFragment Which is our Inbox View;
            case R.id.home:
                launchIntent(MapsActivity.class,null);
                return true;
            case R.id.schedule_ride:
                Toast.makeText(getApplicationContext(), "Stared Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.payment:
                Toast.makeText(getApplicationContext(), "Send Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.verification:
                Toast.makeText(getApplicationContext(), "Drafts Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.share:
                Toast.makeText(getApplicationContext(), "All Mail Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.settings:
                Toast.makeText(getApplicationContext(), "Trash Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.faq:
                Toast.makeText(getApplicationContext(), "Spam Selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                return true;

        }
    }
    private void initView() {

        //Initializing Navigation Header View
        View headerView =  navigationView.inflateHeaderView(R.layout.header_nav);
        circleImageView = (CircleImageView)headerView.findViewById(R.id.profile_image);
        tv_userName = (TextView)headerView.findViewById(R.id.tv_userName);
        headerLayout = (RelativeLayout)headerView.findViewById(R.id.headerLayout);

    }
    private void setNavigationHeaderImageAndName(){

        SQLiteDAO sqLiteDAO = new SQLiteDAO(getApplication());
        sqLiteDAO.open();
        List<SignUp> signUpList = sqLiteDAO.getSignUpDetails();
        sqLiteDAO.close();
        if(signUpList != null && signUpList.size()>0){
            SignUp signup = signUpList.get(0);
            LOGD(TAG, "" + signup.getProfileImageURL());
            Picasso.with(this)
                    .load(signup.getProfileImageURL())
                    .into(circleImageView);
            tv_userName.setText(signup.getFirstName() +" "+ signup.getLastName());
        }

    }




}

