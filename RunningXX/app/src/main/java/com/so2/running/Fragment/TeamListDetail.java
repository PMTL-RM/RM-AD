package com.so2.running.Fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.so2.running.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TeamListDetail extends Fragment  implements View.OnClickListener{
    View view;
    TeamListItem item;
    Button returnbutton ;
    TextView content ;
    String Username , Creatername , Groupname ;
    public void setItem (TeamListItem item)
    {
        this.item = item;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_team_list_detail_creater, container, false);
        //returnbutton = (Button)view.findViewById(R.id.returnbutton);


//        returnbutton.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.content_frame, new TeamList());
//                ft.commit();
//            }
//        });




        //Get textview from view
        TextView groupname = (TextView) view.findViewById(R.id.groupname);
        TextView name = (TextView) view.findViewById(R.id.uname);

        TextView privacy = (TextView) view.findViewById(R.id.privacy);
        TextView location = (TextView) view.findViewById(R.id.location);
        TextView date = (TextView) view.findViewById(R.id.date);
        Button edit_button = (Button)view.findViewById(R.id.edit_button);
        content = (TextView) view.findViewById(R.id.content);
        //Set data
        name.setText(item.getCreatername());
        groupname.setText(item.getGroupname());
        content.setText(item.getContent());
        privacy.setText(item.getPrivacy());
        location.setText(item.getLocation());
        date.setText(String.format("%s%s", item.getDate(), item.getTime()));

        Username = item.getCreatername(); //只有創團的人可以編輯內容 所以在username==creatername的document更新
        Groupname = item.getGroupname();


        edit_button.setOnClickListener(this);



        //Set ActionBar title
        getActivity().setTitle("詳細資料");


        return view;
    }

    @Override
    public void onClick(View view) {showAlertDialog();}

    private void showAlertDialog() {

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final View v = inflater.inflate(R.layout.edit_button, null);
        EditText editText = (EditText) (v.findViewById(R.id.editText1));
        editText.setText(content.getText().toString());
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("基本訊息對話按鈕");
        dialog.setView(v);
        dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText editText = (EditText) (v.findViewById(R.id.editText1));
                Toast.makeText(getActivity(), "以更新內容", Toast.LENGTH_SHORT).show();
                content.setText(editText.getText().toString());
                String url = "http://ncnurunforall-yychiu.rhcloud.com/groups/"+Username+"/"+Groupname;
                try {
                    doPatchContnetRequest(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).show();
        dialog.setNeutralButton("取消",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                Toast.makeText(getActivity(), "取消",Toast.LENGTH_SHORT).show();
            }

        });
    }

    void doPatchContnetRequest(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String Content = content.getText().toString();
        System.out.println(content.getText().toString());
        RequestBody formBody = new FormBody.Builder()
                .add("content",Content )
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

    void handlePostResponse(String response) {
        Log.i("OkHttpPost", response);
    }
}
