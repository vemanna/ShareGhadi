package com.shareghadi.views;

/**
 * Created by BVN on 12/24/2015.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.shareghadi.R;
import com.shareghadi.database.SQLiteDAO;
import com.shareghadi.models.SignUp;
import com.shareghadi.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collection;

import static com.shareghadi.util.LogUtil.LOGD;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = LogUtil.makeLogTag(SignUpActivity.class);
    private LoginButton loginButton;
    private CallbackManager callbackManager;;
    private Button btn_login_with_fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_signup);

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


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
}
