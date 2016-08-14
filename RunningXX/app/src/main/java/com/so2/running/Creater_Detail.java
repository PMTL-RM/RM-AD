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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Creater_Detail extends Fragment {
    View view;
    Button returnbutton ;
    TextView create_name , email , gender ;
    String name;
    String name1 , email1 , gender1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        SharedPreferences preferences = this.getActivity().getSharedPreferences("creater_detail", Context.MODE_PRIVATE);
        name = preferences.getString("creater_name","error");
        System.out.println("creater detail is::::::::"+name);

        view = inflater.inflate(R.layout.creater_detail, container, false);
        returnbutton = (Button)view.findViewById(R.id.returnbutton);
        create_name = (TextView) view.findViewById(R.id.name);
        email = (TextView) view.findViewById(R.id.email);
        gender = (TextView) view.findViewById(R.id.gender);


        returnbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.content_frame, new Search_Result());
                ft.commit();
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

                    System.out.println(name1+email1+gender1);


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
        return view;
    }
}
