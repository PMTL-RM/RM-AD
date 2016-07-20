package com.so2.running;


import android.os.Bundle;
import android.app.Fragment;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateTeamFragment extends Fragment {
    private View view;
    Button button ;
    EditText editText  , editText2 , editText3 , editText4 ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_team, container, false);




        button = (Button)view.findViewById(R.id.button);

        editText = (EditText)view.findViewById(R.id.editText);
        editText2 = (EditText)view.findViewById(R.id.editText2);
        editText3 = (EditText)view.findViewById(R.id.editText3);
        editText4 = (EditText)view.findViewById(R.id.editText4);

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

        return view;
    }

    void doPostRequest(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("name", editText.getText().toString())
                .add("location", editText2.getText().toString())
                .add("content", editText3.getText().toString())
                .add("privacy", editText4.getText().toString())
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
