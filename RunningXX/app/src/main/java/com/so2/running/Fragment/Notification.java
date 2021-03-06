package com.so2.running.Fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.so2.running.Adapter.NotificationAdapter;
import com.so2.running.R;

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

public class Notification extends android.app.Fragment {
    NotificationListItem item2 = new NotificationListItem();
//    Button returnbutton ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences preferences = this.getActivity().getSharedPreferences("here", Context.MODE_PRIVATE);
        String name = preferences.getString("name","error");

        ArrayList<NotificationListItem> sessionList;
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        final ListView listview = (ListView) view.findViewById(R.id.myListView);

//        returnbutton = (Button)view.findViewById(R.id.returnbutton);
//
//
//        returnbutton.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.content_frame, new MainFragment());
//                ft.commit();
//            }
//        });

        //Set ActionBar title
        getActivity().setTitle("通知");
        // Inflate the layout for this fragment


        sessionList = getSessionList(name);

        //If there are no sessions emtyListFragment is called
        if (sessionList.size() == 0) {
            view = inflater.inflate(R.layout.null_scene, container, false);
        }

        //Visualize session list
        else {
            listview.setAdapter(new NotificationAdapter(getActivity(), R.layout.notification_list_item, sessionList));

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                //Go to session detail
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });

            return view;
        }
        return view;
    }

    public ArrayList<NotificationListItem> getSessionList(String name) {
        final ArrayList<NotificationListItem> sessionList = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();

        System.out.println("you should be :::" + name);

        Request req = new Request.Builder()
                .url("http://ncnurunforall-yychiu.rhcloud.com/notices/" + name)
                .build();
        Call call = client.newCall(req);

        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String aFinalString = response.body().string();
                System.out.println(aFinalString);
                NotificationListItem item;
                if (response.isSuccessful()) try {
                    final JSONArray array = new JSONArray(aFinalString);

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        item = new NotificationListItem();

                        item.setContent(obj.getString("content"));
                        item.setFriend_name(obj.getString("user_name"));
                        item.setType(obj.getString("ViewType"));
                        item.setGroupName(obj.getString("groupname"));

                        item2 = item;

                        sessionList.add(item2);
                        Log.d("JSON:",  item2.getContent());
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

        if (item2.getContent() == null) {
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