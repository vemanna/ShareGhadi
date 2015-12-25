package com.shareghadi.views;

/**
 * Created by BVN on 12/24/2015.
 */

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.shareghadi.R;
import com.shareghadi.database.SQLiteDAO;
import com.shareghadi.database.SQLiteDB;
import com.shareghadi.models.SignUp;
import com.shareghadi.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import static com.shareghadi.util.LogUtil.LOGD;

public class SignUpActivity extends BaseActivity {

    private static final String TAG = LogUtil.makeLogTag(SignUpActivity.class);
    private LoginButton loginButton;
    private CallbackManager callbackManager;;
    ProfileTracker profileTracker;

    SignUp signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_signup);

        loginButton = (LoginButton)findViewById(R.id.login_button);

        loginButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday, user_friends"));

        callbackManager = CallbackManager.Factory.create();

        signup = new SignUp();

        /*profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

                signup.setProfileImageURL(String.valueOf(currentProfile.getProfilePictureUri(100, 100)));

                LOGD(TAG, "First Name:" + currentProfile.getFirstName()
                          + "Profile:" + currentProfile.getProfilePictureUri(40,40)
                );
            }
        };*/


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

                                    launchIntentFinish(getApplication(),MainActivity.class);
                                }catch (JSONException e){

                                }

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,first_name, last_name, email, gender, birthday, location ,cover,picture.type(large)");
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
