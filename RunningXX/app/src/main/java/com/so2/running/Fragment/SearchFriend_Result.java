package com.so2.running.Fragment;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.so2.running.Adapter.FriendsAdapter;
import com.so2.running.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchFriend_Result extends Fragment {
    FriendsListItem item2 = new FriendsListItem();
    Button returnbutton ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences preferences = this.getActivity().getSharedPreferences("searchfriend", Context.MODE_PRIVATE);
        String searchname = preferences.getString("searchname","error");

        System.out.println("here :::::::"+searchname);

        ArrayList<FriendsListItem> sessionList;
        View view = inflater.inflate(R.layout.search_result, container, false);
        final ListView listview = (ListView) view.findViewById(R.id.team_list_view);

        returnbutton = (Button)view.findViewById(R.id.returnbutton);


        returnbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.content_frame, new SearchFriendFragment());
                ft.commit();
            }
        });

        //Set ActionBar title
        getActivity().setTitle("搜尋使用者");
        // Inflate the layout for this fragment


        sessionList = getSessionList(searchname);

        //If there are no sessions emtyListFragment is called
        if (sessionList.size() == 0) {
            view = inflater.inflate(R.layout.null_scene, container, false);
            ;
        }

        //Visualize session list
        else {
            listview.setAdapter(new FriendsAdapter(getActivity(), R.layout.friends_layout, sessionList));

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                //Go to session detail
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // selected item
                    final FriendsListItem item = (FriendsListItem) listview.getItemAtPosition(position);
                    System.out.println("the position : : "+position);
                    SearchFriendsDetail itemDetail = new SearchFriendsDetail();
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

    public ArrayList<FriendsListItem> getSessionList(String searchname) {
        final ArrayList<FriendsListItem> sessionList = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();

        System.out.println("the vaules you give in Search_Result.java are  :::::" + searchname);

        Request req = new Request.Builder()
                .url("http://ncnurunforall-yychiu.rhcloud.com/users/searchname/" + searchname )
                .build();
        Call call = client.newCall(req);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String aFinalString = response.body().string();
                System.out.println(aFinalString);
                FriendsListItem item;
                if (response.isSuccessful()) try {
                    final JSONArray array = new JSONArray(aFinalString);

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        item = new FriendsListItem();

                        item.setBirthday(obj.getString("birthday"));
                        item.setEmail(obj.getString("email"));
                        item.setSex(obj.getString("gender"));
                        item.setUrl(obj.getString("url"));
                        item.setName(obj.getString("name"));

                        item2 = item;

                        sessionList.add(item2);
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

        if (item2.getName() == null) {
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