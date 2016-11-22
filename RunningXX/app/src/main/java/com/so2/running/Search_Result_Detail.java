/**
 * This class manage data visualization of the session
 */

package com.so2.running;

import android.app.Fragment;
import android.app.FragmentTransaction;
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

import com.so2.running.Fragment.TeamListItem;
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

public class Search_Result_Detail extends Fragment {
    View view;
    TeamListItem item;
    Button returnbutton ;
    Button join_button;

    public void setItem (TeamListItem item)
    {
        this.item = item;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.search_result_detail, container, false);
        returnbutton = (Button)view.findViewById(R.id.returnbutton);
        join_button = (Button)view.findViewById(R.id.join_button);
        final ImageView viewImage = (ImageView)view.findViewById(R.id.viewImage);

        returnbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.content_frame, new Search_Result());
                ft.commit();
            }
        });
        join_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO -> set up a php server on eu5.org just to test if we can create successful post requests
                String url = "http://ncnurunforall-yychiu.rhcloud.com/notices";
                try {
                    doPostNoticeRequest(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(view.getContext(), "已申請加入", Toast.LENGTH_SHORT).show();
                join_button.setEnabled(false);
            }
        });

        //Get textview from view
        TextView name = (TextView) view.findViewById(R.id.uname);
        TextView content = (TextView) view.findViewById(R.id.content);
        TextView date = (TextView) view.findViewById(R.id.date);
        TextView location = (TextView) view.findViewById(R.id.location);
        TextView join = (TextView)view.findViewById(R.id.join);
        TextView groupname = (TextView)view.findViewById(R.id.groupname);




        //Set data
        name.setText(item.getUsername());
        content.setText(item.getContent());
        date.setText(item.getDate());
        location.setText(item.getLocation());
        join.setText(item.getCount());
        groupname.setText(item.getGroupname());

        final String url ="http://ncnurunforall-yychiu.rhcloud.com/images/"+ item.getUrl();
        Picasso.with(getActivity()).load(url.trim()).resize(50, 50).error(R.drawable.bg).centerInside().into(new Target() {
            @Override
            public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from){

                //Set it in the ImageView
                viewImage.setImageBitmap(bitmap);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {}

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {}
        });


        String creater_name = item.getUsername();

        final SharedPreferences preferences = getActivity().getSharedPreferences("creater_detail", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.putString("creater_name",creater_name).apply();



        //Set ActionBar title
        getActivity().setTitle("SearchDetail");


        return view;
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
                .add("groupname",item.getGroupname())
                .add("content",user_name+" 想加入"+item.getGroupname())
                .add("ViewType","3")
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
