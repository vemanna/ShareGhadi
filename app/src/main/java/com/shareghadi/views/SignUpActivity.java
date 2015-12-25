package com.shareghadi.views;

/**
 * Created by BVN on 12/24/2015.
 */

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.shareghadi.BaseActivity;
import com.shareghadi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
public class SignUpActivity extends BaseActivity {

    private LoginButton loginButton;
    private CallbackManager callbackManager;;
    ProfileTracker profileTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_signup);
        loginButton = (LoginButton)findViewById(R.id.login_button);

       /* loginButton.setReadPermissions("user_friends");
        loginButton.setReadPermissions("public_profile");
        loginButton.setReadPermissions("email");
        loginButton.setReadPermissions("user_birthday");*/

        loginButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday, user_friends"));

        callbackManager = CallbackManager.Factory.create();

        // Callback registration


       /* loginButton.setReadPermissions("user_friends");*/
        /*loginButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday, user_friends"));*/

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                // Application code
                                Log.e("GraphResponse", response.toString());
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();


              /*  info.setText(
                        "User ID: "
                        + loginResult.getAccessToken().getUserId()
                        + "\n" +
                        "Auth Token: "
                        + loginResult.getAccessToken().getToken()

                );*/

                /*GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                Log.e("JSONObject ",""+object );
                                Log.e("GraphResponse ",""+response );
                                try {
                                    Log.e("JSONObject ",""+ object.getString("name") );
                                    Log.e("GraphResponse ",""+ object.getString("name") );
                                  *//*  user_info.setText(
                                            "Name: "
                                                    + object.getString("name")
                                                    + "\n" +
                                                    "Email: "
                                                    + object.getString("email")
                                                    + "\n" +
                                                    "Birthday: "
                                                    + object.getString("birthday")
                                                    + "\n" +
                                                    "Gender:"
                                                    + object.getString("gender")

                                    );*//*

                                    profileTracker = new ProfileTracker() {
                                        @Override
                                        protected void onCurrentProfileChanged(
                                                Profile oldProfile,
                                                Profile currentProfile) {

                                            Log.e("oldProfile ",""+oldProfile );
                                            Log.e("currentProfile ","First Name:"+currentProfile.getFirstName()
                                                            +"Profile:"+currentProfile.getProfilePictureUri(40,40)

                                            );
                                            // App code
                                        }
                                    };

                                } catch(JSONException ex) {
                                    ex.printStackTrace();
                                }

                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,gender,birthday,link,email");
                request.setParameters(parameters);
                request.executeAsync();*/

              /*  GraphRequestBatch batch = new GraphRequestBatch(
                        GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject jsonObject,
                                            GraphResponse response) {
                                        Log.e("JSONObject ",""+jsonObject );
                                        Log.e("GraphResponse ",""+response );
                                        // Application code for user
                                    }
                                }),
                        GraphRequest.newMyFriendsRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONArrayCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONArray jsonArray,
                                            GraphResponse response) {
                                        Log.e("JSONArray ",""+jsonArray );
                                        Log.e("GraphResponse ",""+response );
                                        // Application code for users friends
                                    }
                                })
                );
                batch.addCallback(new GraphRequestBatch.Callback() {
                    @Override
                    public void onBatchCompleted(GraphRequestBatch graphRequests) {
                        // Application code for when the batch finishes
                    }
                });
                batch.executeAsync();*/
                /*Log.e("login AccessToken ",""+loginResult.getAccessToken() );
                new GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        loginResult.getAccessToken().getUserId(),
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {

                                Log.e(" AccessToken",""+ AccessToken.getCurrentAccessToken() );

                                Log.e("GraphResponse ",""+response );


                            }
                        }
                ).executeAsync();*/
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
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
