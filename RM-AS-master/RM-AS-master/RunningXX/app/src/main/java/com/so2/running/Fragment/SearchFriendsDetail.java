package com.so2.running.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.so2.running.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SearchFriendsDetail extends Fragment{
    FriendsListItem item;
    Button returnbutton , addfriend;
    TextView content ;
    String Username , Creatername , Groupname ;
    ImageView imageView ;
    public void setItem (FriendsListItem item)
    {
        this.item = item;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.creater_detail, container, false);
        //returnbutton = (Button)view.findViewById(R.id.returnbutton);
        addfriend = (Button) view.findViewById(R.id.addfriend);

//        returnbutton.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.content_frame, new TeamList());
//                ft.commit();
//            }
//        });

        addfriend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO -> set up a php server on eu5.org just to test if we can create successful post requests
                String url = "http://ncnurunforall-yychiu.rhcloud.com/friendlists";
                String url2 = "http://ncnurunforall-yychiu.rhcloud.com/notices";
                try {
                    doPostFriendListRequest(url);
                    doPostNoticeRequest(url2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(view.getContext(), "已送出邀請", Toast.LENGTH_SHORT).show();
                addfriend.setEnabled(false);
            }
        });


        //Get textview from view
        TextView uname = (TextView) view.findViewById(R.id.uname);
        TextView email = (TextView) view.findViewById(R.id.email);
        TextView birth = (TextView) view.findViewById(R.id.birth);
        TextView gender = (TextView) view.findViewById(R.id.gender);
        imageView = (ImageView) view.findViewById(R.id.userPhoto);
        final String url = item.getUrl();
        Picasso.with(view.getContext()).load(url.trim()).resize(50, 50).error(R.drawable.bg).centerInside().into(new Target() {
            @Override
            public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from){
                /* Save the bitmap or do something with it here */

                //Set it in the ImageView
                imageView.setImageBitmap(bitmap);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {}

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {}
        });



        //Set data
        uname.setText(item.getName());
        email.setText(item.getEmail());
        birth.setText(item.getBirthday());
        gender.setText(item.getSex());





        //Set ActionBar title
        getActivity().setTitle("詳細資料");


        return view;
    }
    void doPostFriendListRequest(String url) throws IOException {
        int status = 0;
        SharedPreferences preferences = getActivity().getSharedPreferences("here", Context.MODE_PRIVATE);       //目前使用者的名字
        String user_name = preferences.getString("name","error");
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("status", String.valueOf(status))
                .add("user_name", user_name)
                .add("friend_name", item.getName())
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
    }

    void doPostNoticeRequest(String url) throws IOException {
        SharedPreferences preferences = getActivity().getSharedPreferences("here", Context.MODE_PRIVATE);       //目前使用者的名字
        String user_name = preferences.getString("name","error");
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("user_name", user_name)
                .add("notice_name", item.getName())
                .add("content",user_name+" 想加你為好友")
                .add("ViewType","1")
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
    }

    void handlePostResponse(String response) {
        Log.i("OkHttpPost", response);
    }
}
