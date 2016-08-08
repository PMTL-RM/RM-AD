package com.so2.running;


import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class CreateTeamFragment extends DialogFragment {
    private View view;
    Button button ,timebutton ,datebutton;
    EditText editText  , editText1 , editText2 ;

    public void showTimePickerDialog(View v) {
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }
    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_team, container, false);


        button = (Button)view.findViewById(R.id.button);

        datebutton = (Button)view.findViewById(R.id.datebutton);
        timebutton = (Button)view.findViewById(R.id.timebutton);


        editText = (EditText)view.findViewById(R.id.editText);
        editText1 = (EditText)view.findViewById(R.id.editText1);
        editText2 = (EditText)view.findViewById(R.id.editText2);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO -> set up a php server on eu5.org just to test if we can create successful post requests
                String url = "http://ncnurunforall-yychiu.rhcloud.com/groups";
                try {
                    doPostRequest(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        datebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(view);
            }
        });
        timebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(view);
            }
        });

        return view;
    }

    void doPostRequest(String url) throws IOException {
        SharedPreferences preferences = getActivity().getSharedPreferences("here", Context.MODE_PRIVATE);
        String name = preferences.getString("name","error");
        System.out.println("this name in dopostrequest ::::"+name);
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("groupname", editText.getText().toString())
                .add("location", editText1.getText().toString())
                .add("content", editText2.getText().toString())
                .add("time", timebutton.getText().toString())
                .add("date", datebutton.getText().toString())
                .add("username", name)
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
