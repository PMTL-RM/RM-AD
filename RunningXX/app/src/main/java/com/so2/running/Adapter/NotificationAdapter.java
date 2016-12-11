package com.so2.running.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.so2.running.Fragment.NotificationListItem;
import com.so2.running.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class NotificationAdapter extends ArrayAdapter<NotificationListItem> {
    private LayoutInflater mInflater;
    private ArrayList<NotificationListItem> sessionList;
    private SharedPreferences preferences = getContext().getSharedPreferences("here", Context.MODE_PRIVATE);       //目前使用者的名字
    private String user_name = preferences.getString("name","error");
    private String img_url = preferences.getString("url","error");

    public NotificationAdapter(Context context, int resource, List<NotificationListItem> objects) {
        super(context, resource, objects);
        sessionList = (ArrayList) objects;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return sessionList.size();
    }
    @Override
    public int getItemViewType(int position) {return Integer.parseInt(sessionList.get(position).getType());}

    @Override
    public int getViewTypeCount() {
        return 2;
    }


    @Override
    public NotificationListItem getItem(int arg0) {
        return sessionList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @NonNull
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        final String friend_name = sessionList.get(position).getFriend_name();


        if (convertView == null) {   // TODO Auto-generated method stub

            switch (getItemViewType(position)) {
                case 1://加好友
                    convertView = mInflater.inflate(R.layout.notification_list_item, null);

                    final Button decide_button ;
                    decide_button = (Button) convertView.findViewById(R.id.decide_button);
                    decide_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String url = "http://ncnurunforall-yychiu.rhcloud.com/friendlists/"+ friend_name+"/"+user_name;
                            String url1 = "http://ncnurunforall-yychiu.rhcloud.com/notices/"+friend_name+"/"+ user_name;
                            String url2 = "http://ncnurunforall-yychiu.rhcloud.com/friendlists/";
                            try {
                                doPatchFriendListRequest(url);
                                doPatchNoticeRequest(url1);
                                doPostFriendListRequest_Reverse(url2 , position);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(getContext(), "加好友成功", Toast.LENGTH_SHORT).show();
                            decide_button.setEnabled(false);
                        }
                    });

                    break;
                case 2://加入團
                    convertView = mInflater.inflate(R.layout.notification_list_item_team, null);
                    final Button decide_addteam_button;
                    decide_addteam_button = (Button) convertView.findViewById(R.id.decide_addteam_button) ;
                    decide_addteam_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String url1 = "http://ncnurunforall-yychiu.rhcloud.com/notices/"+friend_name+"/"+ user_name;
                            String url2 = "http://ncnurunforall-yychiu.rhcloud.com/groups/";
                            String url3 = "http://ncnurunforall-yychiu.rhcloud.com/locations/";
                            try {
                                doPatchNoticeRequest(url1);
                                doAddTeamPostRequest(url2 ,position);
                                doAddLocationPostRequest(url3 ,position);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(getContext(), "加入團成功", Toast.LENGTH_SHORT).show();
                            decide_addteam_button.setEnabled(false);
                        }
                    });
                    break;

                case 3://申請加入團
                    convertView = mInflater.inflate(R.layout.notification_list_item_request_join, null);

                    final Button accept_button ;
                    accept_button = (Button) convertView.findViewById(R.id.accept_button);
                    accept_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String url1 = "http://ncnurunforall-yychiu.rhcloud.com/notices/"+friend_name+"/"+ user_name;
                            String url2 = "http://ncnurunforall-yychiu.rhcloud.com/groups/";
                            String url3 = "http://ncnurunforall-yychiu.rhcloud.com/locations/";
                            try {
                                doPatchNoticeRequest(url1);
                                doAddTeamPostRequest(url2 , position);
                                doAddLocationPostRequest(url3 ,position);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(getContext(), friend_name+"加入了", Toast.LENGTH_SHORT).show();
                            accept_button.setEnabled(false);
                        }
                    });

                    break;
            }

            TextView content;
            content = (TextView) convertView.findViewById(R.id.uname);
            content.setText(sessionList.get(position).getContent());
        }
        return convertView;
    }

    private void doPatchFriendListRequest(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("status", "1")
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


    private void doPostFriendListRequest_Reverse(String url, int position) throws IOException {
        OkHttpClient client = new OkHttpClient();


        final String friend_name = sessionList.get(position).getFriend_name();

        RequestBody formBody = new FormBody.Builder()
                .add("status", "1")
                .add("user_name", user_name)
                .add("friend_name",friend_name)
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


    private void doAddTeamPostRequest(String url, int position) throws IOException {
        OkHttpClient client2 = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("groupname",sessionList.get(position).getGroupName() )
                .add("username", user_name)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        client2.newCall(request).enqueue(new Callback() {
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

    private void doAddLocationPostRequest(String url, int position) throws IOException {
        OkHttpClient client2 = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("groupname",sessionList.get(position).getGroupName() )
                .add("name", user_name)
                .add("lat","0")
                .add("log","0")
                .add("url",img_url)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        client2.newCall(request).enqueue(new Callback() {
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

    private void doPatchNoticeRequest(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("status", "1")
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

    private void handlePostResponse(String response) {
        Log.i("OkHttpPost", response);
    }

}