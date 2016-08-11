package com.so2.running;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
    private ImageButton privacy_button;
    Button button ;
    ImageButton timebutton ,datebutton;
    EditText editText  , editText1 , editText2 ;
    TextView privacy, date, time;
    String choice;

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

        datebutton = (ImageButton)view.findViewById(R.id.datebutton);
        timebutton = (ImageButton)view.findViewById(R.id.timebutton);
        privacy_button = (ImageButton)view.findViewById(R.id.privacy_button);


        editText = (EditText)view.findViewById(R.id.editText);
        editText1 = (EditText)view.findViewById(R.id.editText1);
        editText2 = (EditText)view.findViewById(R.id.editText2);

        privacy = (TextView)view.findViewById(R.id.privacy);
        date = (TextView)view.findViewById(R.id.date);
        time = (TextView)view.findViewById(R.id.time);

        final AlertDialog mutiItemDialog = getMutiItemDialog(new String[]{"隱私","公開"});

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
            public void onClick(final View v) {
                //showDialogListView(v);
                mutiItemDialog.show();
            }
        });


        return view;
    }
    public AlertDialog getMutiItemDialog(final String[] items) {
        Builder builder = new Builder(getActivity());
        //設定對話框內的項目
        builder.setItems(items, new DialogInterface.OnClickListener(){
            @Override

            public void onClick(DialogInterface dialog,int which){
                //當使用者點選對話框時，顯示使用者所點選的項目

                Toast.makeText(getActivity(), "您選擇的是"+items[which], Toast.LENGTH_SHORT).show();
                choice = items[which];
                privacy.setText(String.valueOf(choice));
            }

        });

        return builder.create();
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
                .add("time", time.getText().toString())
                .add("date", date.getText().toString())
                .add("date", privacy.getText().toString())
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
