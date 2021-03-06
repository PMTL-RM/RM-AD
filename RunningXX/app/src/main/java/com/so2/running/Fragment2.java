package com.so2.running;


import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class    Fragment2 extends android.app.Fragment {
    Fragment2ListItem item2 = new Fragment2ListItem();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences preferences = this.getActivity().getSharedPreferences("here", Context.MODE_PRIVATE);
        String name = preferences.getString("name","error");
        System.out.println("here :::::::"+name);

        ArrayList<Fragment2ListItem> sessionList;
        View view = inflater.inflate(R.layout.frag2, container, false);
        final ListView listview = (ListView) view.findViewById(R.id.team_list_view);

        //Set ActionBar title
        getActivity().setTitle(getString(R.string.team));
        // Inflate the layout for this fragment


        sessionList = getSessionList(name);

        //If there are no sessions emtyListFragment is called
        if (sessionList.size() == 0) {
            view = inflater.inflate(R.layout.frag2_createteam_layout, container, false);
            ;
        }

        //Visualize session list
        else {
            listview.setAdapter(new Fragment2Adapter(getActivity(), R.layout.frag2_createteam_layout, sessionList));

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                //Go to session detail
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    // selected item
                    final Fragment2ListItem item = (Fragment2ListItem) listview.getItemAtPosition(position);
                    System.out.println("the position : : "+position);
                    Fragment2Detail itemDetail = new Fragment2Detail();
                    itemDetail.setItem(item);
                    FragmentManager fm = getFragmentManager();
                    fm.beginTransaction()
                            .replace(R.id.content_frame, itemDetail)
                            .commit();
                }
            });

            return view;
        }
        return view;
    }

    public ArrayList<Fragment2ListItem> getSessionList(String name) {
        final ArrayList<Fragment2ListItem> sessionList = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();

        System.out.println("you should be :::" + name);

        Request req = new Request.Builder()
                .url("http://ncnurunforall-yychiu.rhcloud.com/groups/" + name)
                .build();
        Call call = client.newCall(req);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String aFinalString = response.body().string();
                System.out.println(aFinalString);
                Fragment2ListItem item;
                if (response.isSuccessful()) try {
                    final JSONArray array = new JSONArray(aFinalString);

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        item = new Fragment2ListItem();

                        item.setUsername(obj.getString("username"));
                        item.setGroupname(obj.getString("groupname"));
                        item.setContent(obj.getString("content"));
                        item.setDate(obj.getString("date"));
                        item.setLocation(obj.getString("location"));

                        item2 = item;

                        sessionList.add(item2);
                        Log.d("JSON:", item2.getUsername() + "/" + item2.getGroupname() + "/" + item2.getDate() + "/" + item2.getContent() + "/" + item2.getLocation());
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

        if (item2.getUsername() == null) {
            synchronized (this) {
                try {
                    wait(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            onStart();
        }
        return sessionList;
    }
}