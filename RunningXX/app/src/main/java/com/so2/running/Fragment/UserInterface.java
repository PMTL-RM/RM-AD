package com.so2.running.Fragment;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.so2.running.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserInterface extends Fragment implements View.OnClickListener{
    ImageView imageView;
    TextView txtName, txtURL, txtGender,txtBd , about_me;
    Button btnShare , returnbutton , edit_button;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_user, container, false);

        returnbutton = (Button) view.findViewById(R.id.returnbutton);

        imageView = (ImageView) view.findViewById(R.id.userPhoto);
        txtName = (TextView) view.findViewById(R.id.uname);
        txtBd = (TextView) view.findViewById(R.id.birth);
        txtGender = (TextView) view.findViewById(R.id.gender);
        about_me = (TextView) view.findViewById(R.id.about_me);
        edit_button = (Button) view.findViewById(R.id.edit_button);

        edit_button.setOnClickListener(this);
        getActivity().setTitle(getString(R.string.user));

/*        txtURL = (TextView) view.findViewById(R.id.txtURL);
        txtGender = (TextView) view.findViewById(R.id.txtGender);
        txtBd = (TextView) view.findViewById(R.id.txtBd);*/



//        returnbutton.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.content_frame, new MainFragment());
//                ft.commit();
//            }
//        });

        //Another way to share content
/*        btnShare = (Button) view.findViewById(R.id.btnShare);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle("Hello Guys")
                            .setContentDescription(
                                    "Coder who learned and share")
                            .setContentUrl(Uri.parse("http://so2.com"))
                            .setImageUrl(Uri.parse("https://scontent-sin1-1.xx.fbcdn.net/hphotos-xap1/v/t1.0-9/12936641_845624472216348_1810921572759298872_n.jpg?oh=72421b8fa60d05e68c6fedbb824adfbf&oe=577949AA"))
                            .build();

                    shareDialog.show(linkContent);
                }
            }
        });*/

        GetUserInfo();
/*

        //Like
        LikeView likeView = (LikeView) view.findViewById(R.id.likeView);
        likeView.setLikeViewStyle(LikeView.Style.STANDARD);
        likeView.setAuxiliaryViewPosition(LikeView.AuxiliaryViewPosition.INLINE);

        likeView.setObjectIdAndType(
                "http://so2.com",
                LikeView.ObjectType.OPEN_GRAPH);

*/


        //Share Dialog
        //You cannot preset the shared link in design time, if you do so, the fb share button will
        //look disabled. You need to set in the code as below
/*        ShareButton fbShareButton = (ShareButton) view.findViewById(R.id.fb_share_button);
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentTitle("Hello Guys")
                .setContentDescription(
                        "Coder who learned and share")
                .setContentUrl(Uri.parse("http://so2.com"))
                .setImageUrl(Uri.parse("https://scontent-sin1-1.xx.fbcdn.net/hphotos-xap1/v/t1.0-9/12936641_845624472216348_1810921572759298872_n.jpg?oh=72421b8fa60d05e68c6fedbb824adfbf&oe=577949AA"))

                .build();
        fbShareButton.setShareContent(content);*/
        return view;
    }

    private void GetUserInfo() {
        //this code will help us to obtain information from facebook, if
        //need some other field which not show here, please refer to https://developers.facebook.com/docs/graph-api/using-graph-api/
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        // Application code
                        try {
                            String gender = object.getString("gender");
                            String birthday = object.getString("birthday");
                            String name = object.getString("name");
                            String id = object.getString("id");

                            txtName.setText(name);
                            txtGender.setText(gender);
                            txtBd.setText(birthday);
                            if (object.has("picture")) {
                                String profilePicUrl = object.getJSONObject("picture").getJSONObject("data").getString("url");
                                Picasso.with(getActivity()).load(profilePicUrl.trim()).resize(50, 50).error(R.drawable.bg).centerInside().into(new Target() {
                                    @Override
                                    public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from){

                                        //Set it in the ImageView
                                        imageView.setImageBitmap(bitmap);
                                    }

                                    @Override
                                    public void onPrepareLoad(Drawable placeHolderDrawable) {}

                                    @Override
                                    public void onBitmapFailed(Drawable errorDrawable) {}
                                });
                            }

                            doGetIntroRequest(name);


                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,gender,name,birthday,picture.type(large)");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onClick(View view) {showAlertDialog();}

    private void showAlertDialog() {

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final View v = inflater.inflate(R.layout.edit_button, null);
        EditText editText = (EditText) (v.findViewById(R.id.editText1));
        editText.setText(about_me.getText().toString());
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("編輯簡介");
        dialog.setView(v);
        dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText editText = (EditText) (v.findViewById(R.id.editText1));
                Toast.makeText(getActivity(), "已更新內容", Toast.LENGTH_SHORT).show();
                about_me.setText(editText.getText().toString());
                String url = "http://ncnurunforall-yychiu.rhcloud.com/users/"+txtName.getText().toString();
                try {
                    doPatchIntroRequest(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).show();
        dialog.setNeutralButton("取消",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                Toast.makeText(getActivity(), "取消",Toast.LENGTH_SHORT).show();
            }

        });
    }

    void doPatchIntroRequest(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String intro = about_me.getText().toString();
        System.out.println(about_me.getText().toString());
        RequestBody formBody = new FormBody.Builder()
                .add("intro",intro )
                .build();

        Request request = new Request.Builder()
                .url(url)
                .patch(formBody)
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
    }

    void doGetIntroRequest(String name) throws IOException {

        OkHttpClient client = new OkHttpClient();


        Request req = new Request.Builder()
                .url("http://ncnurunforall-yychiu.rhcloud.com/users/" + name)
                .build();
        Call call = client.newCall(req);

        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String aFinalString = response.body().string();
                System.out.println(aFinalString);
                if (response.isSuccessful()) try {
                    final JSONArray array = new JSONArray(aFinalString);

                    for (int i = 0; i < array.length(); i++) {
                        final JSONObject obj = array.getJSONObject(i);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    about_me.setText(obj.getString("intro"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                else {
                    Log.e("APp", "Error");
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                //告知使用者連線失敗
            }
        });

        if (about_me == null) {
            synchronized (this) {
                try {
                    wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            onStart();
        }

    }


    void handlePostResponse(String response) {
        Log.i("OkHttpPost", response);
    }
}

