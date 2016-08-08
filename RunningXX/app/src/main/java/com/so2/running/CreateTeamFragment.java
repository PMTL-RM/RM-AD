package com.so2.running;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    Button button ,timebutton ,datebutton,privacy_button;
    EditText editText  , editText1 , editText2 ;
    ListView listview = null;

    Activity activity;

    public void showTimePickerDialog(View v) {
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }
    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }
    public void showDialogListView(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setPositiveButton("OK", null);
        builder.setView(listview);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity=activity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_team, container, false);
        listview = new ListView(getActivity());
        String[] items={"私密","公開"};
        ArrayAdapter<String > adapter = new ArrayAdapter<String>(getActivity(), R.layout.fragment_createam_privacy, R.id.txtitem, items);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ViewGroup vg = (ViewGroup)view;
                TextView txt = (TextView)vg.findViewById(R.id.txtitem);
                Toast.makeText(getActivity(), txt.getText().toString(), Toast.LENGTH_SHORT).show();

            }
        });
        button = (Button)view.findViewById(R.id.button);

        datebutton = (Button)view.findViewById(R.id.datebutton);
        timebutton = (Button)view.findViewById(R.id.timebutton);
        privacy_button = (Button)view.findViewById(R.id.privacy_button);


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
                showDatePickerDialog(v);
            }
        });
        timebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v);
            }
        });
        privacy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogListView(v);
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
