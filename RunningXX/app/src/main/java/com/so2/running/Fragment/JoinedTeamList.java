package com.so2.running.Fragment;


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

import com.so2.running.Adapter.JoinedTeamListAdapter;
import com.so2.running.MainFragment;
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

public class JoinedTeamList extends android.app.Fragment {
    JoinedTeamListItem item2 = new JoinedTeamListItem();
    Button returnbutton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences preferences = this.getActivity().getSharedPreferences("here", Context.MODE_PRIVATE);
        String name = preferences.getString("name","error");

        ArrayList<JoinedTeamListItem> sessionList;
        View view = inflater.inflate(R.layout.fragment_team_list, container, false);
        final ListView listview = (ListView) view.findViewById(R.id.team_list_view);

        returnbutton = (Button)view.findViewById(R.id.returnbutton);


        returnbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction ft;
                ft = getFragmentManager().beginTransaction().replace(R.id.content_frame, new MainFragment());
                ft.commit();
            }
        });

        //Set ActionBar title
        getActivity().setTitle("加入的團");
        // Inflate the layout for this fragment


        sessionList = getSessionList(name);

        //If there are no sessions emtyListFragment is called
        if (sessionList.size() == 0) {
            view = inflater.inflate(R.layout.null_scene, container, false);
        }

        //Visualize session list
        else {
            listview.setAdapter(new JoinedTeamListAdapter(getActivity(), R.layout.fragment_team_list_item, sessionList));

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                //Go to session detail
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // selected item
                    final JoinedTeamListItem item = (JoinedTeamListItem) listview.getItemAtPosition(position);
                    System.out.println("the position : : "+position);
                    JoinedTeamListDetail itemDetail = new JoinedTeamListDetail();
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

    public ArrayList<JoinedTeamListItem> getSessionList(String name) {
        final ArrayList<JoinedTeamListItem> sessionList = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();


        Request req = new Request.Builder()
                .url("http://ncnurunforall-yychiu.rhcloud.com/groups/joined/" + name)
                .build();
        Call call = client.newCall(req);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String [] strArray;
                final String aFinalString = response.body().string();
                System.out.println(aFinalString);
                final JoinedTeamListItem item = new JoinedTeamListItem();
                if (response.isSuccessful()) try {
                    final JSONArray array = new JSONArray(aFinalString);
                    strArray = new String[array.length()];
                    System.out.println("strarray length     "+strArray.length);

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        item.setGroupname(obj.getString("groupname"));
                        strArray[i] = item.getGroupname();
                        System.out.println("my friend name is "+strArray[i]);



                        OkHttpClient client2 = new OkHttpClient();


                        Request req1 = new Request.Builder()
                                .url("http://ncnurunforall-yychiu.rhcloud.com/groups/joined/info/" + strArray[i])
                                .build();
                        Call call2 = client2.newCall(req1);

                        final int finalI = i;
                        call2.enqueue(new Callback() {

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                final String aFinalString2 = response.body().string();
                                final JoinedTeamListItem item3 = new JoinedTeamListItem();
                                System.out.println(aFinalString2);
                                if (response.isSuccessful()) try {
                                    final JSONArray array = new JSONArray(aFinalString2);

                                    JSONObject obj = array.getJSONObject(0);
                                    item3.setGroupname(strArray[finalI]);
                                    item3.setUsername(obj.getString("username"));
                                    item3.setContent(obj.getString("content"));
                                    item3.setDate(obj.getString("date"));
                                    item3.setTime(obj.getString("time"));
                                    item3.setLocation(obj.getString("location"));
                                    item3.setPrivacy(obj.getString("privacy"));
                                    item3.setCreatername(obj.getString("creatername"));
                                    item3.setImagename(obj.getString("imagename"));
                                    item3.setUrl(obj.getString("imagename"));
                                    item3.setCount(obj.getString("count"));
                                    item3.setStart(obj.getString("start"));
                                    item3.setEnd(obj.getString("end"));

                                    item2 = item3;
                                    Log.d("JSON:", item2.getUsername() + "/" + item2.getGroupname() + "/" + item2.getDate()+item2.getTime() + "/" + item2.getContent() + "/" + item2.getLocation()+ "/" + item2.getPrivacy());
                                    sessionList.add(item2);


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
                    wait(4500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            onStart();
        }
        return sessionList;
    }
}