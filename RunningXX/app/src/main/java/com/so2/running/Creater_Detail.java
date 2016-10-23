/*
                    RUNNING
   Copyright (C) 2015  Alessandro Mereu, Maurizio Romano, Matteo Enrico Serpi

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * This class manage data visualization of the session
 */

package com.so2.running;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Creater_Detail extends Fragment {
    View view;
    Button returnbutton , addfriend ;
    TextView create_name , email , gender ;
    String name;
    String name1 , email1 , gender1;
    String username , friendname ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        SharedPreferences preferences = this.getActivity().getSharedPreferences("creater_detail", Context.MODE_PRIVATE);
        name = preferences.getString("creater_name","error");

        view = inflater.inflate(R.layout.creater_detail, container, false);
        returnbutton = (Button)view.findViewById(R.id.returnbutton);
        create_name = (TextView) view.findViewById(R.id.uname);
        email = (TextView) view.findViewById(R.id.email);
        gender = (TextView) view.findViewById(R.id.gender);
        addfriend = (Button) view.findViewById(R.id.addfriend);

        returnbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.content_frame, new Search_Result());
                ft.commit();
            }
        });

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

        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder()
                .url("http://ncnurunforall-yychiu.rhcloud.com/users/" + name)    //這個是創團人的名字
                .build();
        Call call = client.newCall(req);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String aFinalString = response.body().string();
                if (response.isSuccessful()) try {
                    final JSONArray array = new JSONArray(aFinalString);
                    JSONObject obj = array.getJSONObject(0);

                    name1 = obj.getString("name");
                    email1 = obj.getString("email");
                    gender1 = obj.getString("gender") ;

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
        if ( name1 == null) {
            synchronized (this) {
                try {
                    wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            onStart();
        }
        //用API把創團人印出來

        //Set ActionBar title
        getActivity().setTitle("個人資料");

        create_name.setText(name1);
        email.setText(email1);
        gender.setText(gender1);

        try {
            doGetRequest();
            if(Objects.equals(create_name.getText().toString(), friendname)) {
                addfriend.setEnabled(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }



    void doPostFriendListRequest(String url) throws IOException {
        int status = 0;
        SharedPreferences preferences = getActivity().getSharedPreferences("here", Context.MODE_PRIVATE);       //目前使用者的名字
        SharedPreferences preferences1 = this.getActivity().getSharedPreferences("creater_detail", Context.MODE_PRIVATE);       //你想加好友的人
        String user_name = preferences.getString("name","error");
        String friend_name = preferences1.getString("creater_name","error1");
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("status", String.valueOf(status))
                .add("user_name", user_name)
                .add("friend_name", friend_name)
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
        SharedPreferences preferences1 = this.getActivity().getSharedPreferences("creater_detail", Context.MODE_PRIVATE);       //你想加好友的人
        String user_name = preferences.getString("name","error");
        String notice_name = preferences1.getString("creater_name","error1");
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("user_name", user_name)
                .add("notice_name", notice_name)
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

    private void doGetRequest() throws IOException {
        SharedPreferences preferences = getActivity().getSharedPreferences("here", Context.MODE_PRIVATE);       //目前使用者的名字
        SharedPreferences preferences1 = this.getActivity().getSharedPreferences("creater_detail", Context.MODE_PRIVATE);       //你想加好友的人
        String user_name = preferences.getString("name","error");
        String friend_name = preferences1.getString("creater_name","error1");
        OkHttpClient client = new OkHttpClient();

        Request req = new Request.Builder()
                .url("http://ncnurunforall-yychiu.rhcloud.com/friendlists/" + user_name+"/"+ friend_name)    //這個是創團人的名字
                .build();
        Call call = client.newCall(req);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String aFinalString = response.body().string();
                if (response.isSuccessful()) try {
                    final JSONArray array = new JSONArray(aFinalString);
                    JSONObject obj = array.getJSONObject(0);

                    username = obj.getString("user_name");
                    friendname = obj.getString("friend_name");


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
        if ( friendname == null) {
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
}
