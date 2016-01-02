package com.shareghadi.views;

/**
 * Created by BVN on 12/24/2015.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.shareghadi.R;
import com.shareghadi.database.SQLiteDAO;
import com.shareghadi.models.SignUp;
import com.shareghadi.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collection;

import static com.shareghadi.util.LogUtil.LOGD;

public class SignUpActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{

    private static final String TAG = LogUtil.makeLogTag(SignUpActivity.class);
    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;
    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;

    /* Is there a ConnectionResult resolution in progress? */
    private boolean mIsResolving = false;
    /* Should we automatically resolve ConnectionResults when possible? */
    private boolean mShouldResolve = false;

    private SignInButton mPlusSignInButton;
    private LoginButton loginButton;
    private CallbackManager callbackManager;;
    private Button btn_login_with_fb;

    ProgressDialog ringProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_signup);

        intiView();

    }


    private void intiView(){

        //Facebook Login
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday, user_friends"));
        callbackManager = CallbackManager.Factory.create();

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object,GraphResponse response) {
                                try {
                                    LOGD(TAG, response+"");
                                    JSONObject data = response.getJSONObject();
                                    SignUp signup = new SignUp();
                                    signup.setFirstName(data.getString("first_name"));
                                    signup.setLastName(data.getString("last_name"));
                                    signup.setEmail(data.getString("email"));
                                    if (data.has("picture")) {
                                        String profileImageUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");
                                        signup.setProfileImageURL(profileImageUrl);
                                    }
                                    if (data.has("cover")) {
                                        String coverImageUrl = data.getJSONObject("cover").getString("source");
                                        signup.setCoverImageURL(coverImageUrl);
                                    }

                                    //Inserting data
                                    SQLiteDAO sqLiteDAO = new SQLiteDAO(getApplication());
                                    sqLiteDAO.open();
                                    sqLiteDAO.insertSignUpDetails(signup);
                                    sqLiteDAO.close();


                                }catch (JSONException e){

                                }

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,first_name, last_name, email, gender, birthday, location ,cover,picture.type(large)");
                request.setParameters(parameters);
                request.executeAsync();

                Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

            }

            @Override
            public void onCancel() {
                /*info.setText("Login attempt canceled.");*/
            }

            @Override
            public void onError(FacebookException error) {
               /* info.setText("Login attempt failed.");*/
            }
        });


        //Google+ Login
        mPlusSignInButton = (SignInButton) findViewById(R.id.g_sign_in_button);
        mPlusSignInButton.setSize(SignInButton.SIZE_WIDE);
        mPlusSignInButton.setOnClickListener(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .build();
    }
    /**
     * Fetching user's information name, email, profile pic
     * */
    private void getProfileInformation() {
        ringProgressDialog.dismiss();
        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
            Person currentPerson = Plus.PeopleApi
                    .getCurrentPerson(mGoogleApiClient);
            LOGD(TAG, currentPerson+"");
            String personName = currentPerson.getDisplayName();
            String personPhotoUrl = currentPerson.getImage().getUrl();
            String personGooglePlusProfile = currentPerson.getUrl();
            String birth = currentPerson.getBirthday();
            String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
            LOGD(TAG, "personName"+personName);
            // by default the profile url gives 50x50 px image only
            // we can replace the value with whatever dimension we want by
            // replacing sz=X
//                personPhotoUrl = personPhotoUrl.substring(0,
//                        personPhotoUrl.length() - 2)
//                        + PROFILE_PIC_SIZE;

            //new LoadProfileImage().execute(personPhotoUrl);

        } else {
            Toast.makeText(getApplicationContext(),
                    "Person information is null", Toast.LENGTH_LONG).show();
        }
    }
    private void onSignInClicked() {
//        toastLoading.show();
        // User clicked the sign-in button, so begin the sign-in process and automatically
        // attempt to resolve any errors that occur.
        ringProgressDialog = ProgressDialog.show(SignUpActivity.this, "Connecting...", "Atempting to connect", true);
        ringProgressDialog.setCancelable(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mShouldResolve = true;
                    mGoogleApiClient.connect();
                } catch (Exception e) {
                    ringProgressDialog.dismiss();
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onConnected(Bundle bundle) {
        mShouldResolve = false;
        getProfileInformation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        ringProgressDialog.dismiss();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        // Could not connect to Google Play Services.  The user needs to select an account,
        // grant permissions or resolve an error in order to sign in. Refer to the javadoc for
        // ConnectionResult to see possible error codes.
        LOGD(TAG, "onConnectionFailed:" + connectionResult);
        ringProgressDialog.dismiss();

        if (!mIsResolving && mShouldResolve) {
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(this, RC_SIGN_IN);
                    mIsResolving = true;
                } catch (IntentSender.SendIntentException e) {
                    LOGD(TAG, "Could not resolve ConnectionResult.", e);
                    Toast.makeText(SignUpActivity.this, "Could not resolve ConnectionResult", Toast.LENGTH_LONG).show();
                    mIsResolving = false;
                }
            } else {
                // Could not resolve the connection result, show the user an
                // error dialog.
                Toast.makeText(SignUpActivity.this, "Error on Login, check your google + login method", Toast.LENGTH_LONG).show();
            }
        } else {
            // Show the signed-out UI
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.g_sign_in_button:
                onSignInClicked();
                break;
        }
    }

    /*private void initView() {
        btn_login_with_fb = (Button)findViewById(R.id.btn_login_with_fb);
        btn_login_with_fb.setOnClickListener(this);
    }
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login_with_fb:
                signUpWithFaceBook();
                break;
        }
    }

    private void signUpWithFaceBook() {

        Collection<String> permissions = Arrays.asList("public_profile, email, user_birthday, user_friends");
        LoginManager.getInstance().logInWithReadPermissions(this, permissions);
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResults) {

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResults.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                        // Application code
                                        try {
                                            LOGD(TAG, response+"");
                                            JSONObject data = response.getJSONObject();
                                            SignUp signup = new SignUp();
                                            signup.setFirstName(data.getString("first_name"));
                                            signup.setLastName(data.getString("last_name"));
                                            signup.setEmail(data.getString("email"));
                                            if (data.has("picture")) {
                                                String profileImageUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");
                                                signup.setProfileImageURL(profileImageUrl);
                                                LOGD(TAG, profileImageUrl + "");
                                            }
                                            if (data.has("cover")) {
                                                String coverImageUrl = data.getJSONObject("cover").getString("source");
                                                signup.setCoverImageURL(coverImageUrl);
                                            }

                                            //Inserting data
                                            SQLiteDAO sqLiteDAO = new SQLiteDAO(getApplication());
                                            sqLiteDAO.open();
                                            long insertRow = sqLiteDAO.insertSignUpDetails(signup);
                                            LOGD(TAG, insertRow+"");
                                            sqLiteDAO.close();
                                            launchIntentFinish(getApplication(), MainActivity.class);

                                        }catch (JSONException e){

                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,first_name, last_name, email, gender, birthday, location ,cover,picture.type(large)");
                        request.setParameters(parameters);
                        request.executeAsync();


                    }
                    @Override
                    public void onCancel() {

                        LOGD(TAG, "facebook login canceled");

                    }


                    @Override
                    public void onError(FacebookException e) {


                        LOGD(TAG, "facebook login failed error");

                    }
                });

    }*/
    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
}
