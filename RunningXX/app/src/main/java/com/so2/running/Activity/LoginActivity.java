package com.so2.running.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.so2.running.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private AccessToken accessToken ;
    private final static String TAG = LoginActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final SharedPreferences preferences = getSharedPreferences("here", MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        editor.clear();


        //消除標題列
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //消除狀態列
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);


        //Initialize FB SDK
        //This line must be put here before the  setContentView(R.layout.activity_login);
        //Or else you will get null object error
        FacebookSdk.sdkInitialize(getApplicationContext(), new FacebookSdk.InitializeCallback() {
            @Override
            public void onInitialized() {
                //AccessToken is for us to check whether we have previously logged in into
                //this app, and this information is save in shared preferences and sets it during SDK initialization
                accessToken = AccessToken.getCurrentAccessToken();
                if (accessToken == null) {
                    Log.d(TAG, "not log in yet");
                } else {
                    Log.d(TAG, "Logged in");
                    Intent main = new Intent(LoginActivity.this,GuideActivity.class);
                    startActivity(main);

                }
            }
        });

        setContentView(R.layout.activity_login);

        //register a callback to respond to a login result,
        callbackManager = CallbackManager.Factory.create();

        //register access token to check whether user logged in before
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {
                accessToken = newToken;
            }
        };

        LoginButton loginButton = (LoginButton)findViewById(R.id.login_button);
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //Once authorized from facebook will directly go to MainActivity
                accessToken = loginResult.getAccessToken();
                //this code will help us to obtain information from facebook, if
                //need some other field which not show here, please refer to https://developers.facebook.com/docs/graph-api/using-graph-api/
                GraphRequest request1 = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                // Application code
                                try{
                                    String birthday = object.getString("birthday");
                                    String name = object.getString("name");
                                    String id = object.getString("id");
                                    String gender = object.getString("gender");
                                    String email = object.getString("email");

                                    String url = "http://ncnurunforall-yychiu.rhcloud.com/users";
                                    editor.putString("name",name).apply();
                                    editor.putString("url",object.getJSONObject("picture").getJSONObject("data").getString("url")).apply();

                                    String asd = preferences.getString("name",null);
                                    if (asd != null) {
                                        System.out.println(asd);
                                    }
                                    OkHttpClient client = new OkHttpClient();
                                    RequestBody formBody = new FormBody.Builder()
                                            .add("_id",id)
                                            .add("name",name)
                                            .add("birthday",birthday)
                                            .add("gender",gender)
                                            .add("email",email)
                                            .add("intro","新增你的個人簡介吧!")
                                            .add("url",object.getJSONObject("picture").getJSONObject("data").getString("url"))
                                            .build();

                                    Request request = new Request.Builder()
                                            .url(url)
                                            .post(formBody)
                                            .build();

                                    client.newCall(request).enqueue(new Callback() {
                                        @Override
                                        public void onFailure(Call call, IOException e) {
                                            // Error
                                        }

                                        @Override
                                        public void onResponse(Call call, Response response) throws IOException {
                                            if (response.isSuccessful()) {
                                                String res = response.body().string();
                                                handlePostResponse(res);
                                            } else {
                                                Log.e("APp", "Error");
                                            }
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,birthday,gender,picture.type(large)");
                request1.setParameters(parameters);
                request1.executeAsync();


                Intent main = new Intent(LoginActivity.this,GuideActivity.class);
                startActivity(main);

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        //Set permission to use in this app
        List<String> permissionNeeds = Arrays.asList("user_friends","email","user_birthday");
        loginButton.setReadPermissions(permissionNeeds);

        accessTokenTracker.startTracking();

        //Generate Hash Key, need get this key update
        // into https://developers.facebook.com/quickstarts/1584671128490867/?platform=android
        showHashKey(this);

    }

    public static void showHashKey(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    "com.so2.running", PackageManager.GET_SIGNATURES); //Your            package name here
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.i("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException ignored) {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        super.onActivityResult(requestCode, responseCode, intent);
        //Facebook login
        callbackManager.onActivityResult(requestCode, responseCode, intent);

    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    protected void onStop() {
        super.onStop();
        accessTokenTracker.stopTracking();
    }
    void handlePostResponse(String response) {
        Log.i("OkHttpPost", response);
    }
}
