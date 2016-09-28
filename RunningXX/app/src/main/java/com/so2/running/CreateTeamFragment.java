package com.so2.running;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class CreateTeamFragment extends Fragment implements View.OnClickListener {

    private View view;
    private ImageButton privacy_button;
    Button button,image_button;
    ImageButton timebutton ,datebutton,myImage, addfriend;
    EditText editText  , editText1 , editText2 ;
    TextView privacy, date, time, friend_txv;
    String choice;
    String friends = "";

    private ListView lv;


    ImageView viewImage;



    private String[] type = new String[] {"基隆市", "新北市","臺北市","宜蘭縣","新竹市","新竹縣","桃園市"
            ,"苗栗縣","臺中市","彰化縣","南投縣","嘉義縣","雲林縣","臺南市","高雄市","澎湖縣","金門縣"
            ,"屏東縣","台東縣","花蓮縣"};


    private String[] address = new String[]{"仁愛區","信義區","中正區","安樂區","七堵區","中山區","暖暖區"};
    private String[][] type2 = new String[][]{{"仁愛區","信義區","中正區","安樂區","七堵區","中山區","暖暖區"},
            {"萬里區","板橋區","深坑區","瑞芳區","平溪區","貢寮區","坪林區","永和區","土城區","樹林區","三重區","泰山區","蘆洲區","八里區","三芝區","金山區","汐止區","石碇區","雙溪區","新店區","烏來區","中和區","三峽區","鶯歌區","新莊區","林口區","五股區","淡水區","石門區"},
            {"中正區","大同區","中山區","大安區","萬華區","信義區","北投區","內湖區","文山區","士林區","松山區","南港區"},
            {"宜蘭市","頭城鎮","羅東鎮","蘇澳鎮","礁溪鄉","壯圍鄉","員山鄉","冬山鄉","五結鄉","三星鄉","大同鄉","南澳鄉"},
            {"東區","北區","香山區"},
            {"竹北市","竹東鎮","新埔鎮","關西鎮","湖口鄉","新豐鄉","峨眉鄉","寶山鄉","北埔鄉","芎林鄉","橫山鄉","尖石鄉","五峰鄉"},
            {"桃園區","中壢區","平鎮區","八德區","楊梅區","蘆竹區","大溪區","龍潭區","龜山區","大園區","觀音區","新屋區","復興區"},
            {"苗栗市","頭份市","卓蘭鎮","竹南鎮","後龍鎮","通霄鎮","苑裡鎮","造橋鄉","西湖鄉","頭屋鄉","公館鄉","銅鑼鄉","三義鄉","大湖鄉","獅潭鄉","三灣鄉","南庄鄉","泰安鄉"},
            {"中區","東區","南區","西區","北區","北屯區","西屯區","南屯區","太平區","大里區","霧峰區","烏日區","豐原區","后里區","石岡區","東勢區","和平區","新社區","潭子區","神岡區","大雅區","大肚區","沙鹿區","龍井區","梧棲區","清水區","大甲區","外埔區","大安區"},
            {"彰化市","員林市","和美鎮","鹿港鎮","溪湖鎮","二林鎮","田中鎮","北斗鎮","花壇鄉","芬園鄉","大村鄉","永靖鄉","伸港鄉","線西鄉","福興鄉","秀水鄉","埔心鄉","埔鹽鄉","大城鄉","芳苑鄉","竹塘鄉","社頭鄉","二水鄉","田尾鄉","埤頭鄉","溪州鄉"},
            {"南投市","埔里鎮","草屯鎮","竹山鎮","集集鎮","名間鄉","鹿谷鄉","中寮鄉","魚池鄉","國姓鄉","水里鄉","信義鄉","仁愛鄉"},
            {"太保市","朴子市","布袋鎮","大林鎮","民雄鄉","溪口鄉","新港鄉","六腳鄉","東石鄉","義竹鄉","鹿草鄉","水上鄉","中埔鄉","竹崎鄉","梅山鄉","番路鄉","大埔鄉","阿里山鄉"},
            {"斗六市","斗南鎮","虎尾鎮","西螺鎮","土庫鎮","北港鎮","林內鄉","古坑鄉","大埤鄉","莿桐鄉","褒忠鄉","二崙鄉","崙背鄉","麥寮鄉","臺西鄉","東勢鄉","元長鄉","四湖鄉","口湖鄉","水林鄉"},
            {"中西區","東區","南區","北區","安平區","安南區","永康區","歸仁區","新化","左鎮區","玉井區","楠西區","南化區","仁德區","關廟區","龍崎區","官田區","麻豆區","佳里區","西港區","七股區","將軍區","學甲區","北門區","新營區","後壁區","白河區","東山區","六甲區","下營區","柳營區","鹽水區","善化區","大內區","山上區","新市區","安定區"},
            {"楠梓區","左營區","鼓山區","三民區","鹽埕區","前金區","新興區","苓雅區","前鎮區","旗津區","小港區","鳳山區","大寮區","鳥松區","林園區","仁武區","大樹區","大社區","岡山區","路竹區","橋頭區","梓官區","彌陀區","永安區","燕巢區","田寮區","阿蓮區","茄萣區","湖內區","旗山區","美濃區","內門區","杉林區","甲仙區","六龜區","茂林區","桃源區","那瑪夏區"},
            {"馬公市","湖西鄉","白沙鄉","西嶼鄉","望安鄉","七美鄉"},
            {"金城鎮","金湖鎮","金沙鎮","金寧鄉","烈嶼鄉","烏坵鄉"},
            {"屏東市","潮州鎮","東港鎮","恆春鎮","萬丹鄉","長治鄉","麟洛鄉","九如鄉","里港鄉","鹽埔鄉","高樹鄉","萬巒鄉","內埔鄉","竹田鄉","新埤鄉","枋寮鄉","新園鄉","崁頂鄉","林邊鄉","南州鄉","佳冬鄉","琉球鄉","車城鄉","滿州鄉","枋山鄉","霧臺鄉","瑪家鄉","泰武鄉","來義鄉","春日鄉","獅子鄉","牡丹鄉","三地門鄉"},
            {"臺東市","成功鎮","關山鎮","長濱鄉","池上鄉","東河鄉","鹿野鄉","卑南鄉","大武鄉","綠島鄉","太麻里鄉","海端鄉","延平鄉","金峰鄉","達仁鄉","蘭嶼鄉"},
            {"花蓮市","鳳林鎮","玉里鎮","新城鄉","吉安鄉","壽豐鄉","光復鄉","豐濱鄉","瑞穗鄉","富里鄉","秀林鄉","萬榮鄉","卓溪鄉"}};
    private Spinner sp;//第一個下拉選單
    private Spinner sp2;//第二個下拉選單
    private Context context;

    ArrayAdapter<String> adapter ;

    ArrayAdapter<String> adapter2;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

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
        friend_txv = (TextView)view.findViewById(R.id.friend_txv);


        addfriend = (ImageButton)view.findViewById(R.id.addfriend);
        addfriend.setOnClickListener(this);


        editText = (EditText)view.findViewById(R.id.editText);
//        editText1 = (EditText)view.findViewById(R.id.editText1);
        editText2 = (EditText)view.findViewById(R.id.editText2);

        privacy = (TextView)view.findViewById(R.id.privacy);
        date = (TextView)view.findViewById(R.id.date);
        time = (TextView)view.findViewById(R.id.time);

        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp = (Spinner) view.findViewById(R.id.type);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(selectListener);

        //因為下拉選單第一個為地址，所以先載入地址群組進第二個下拉選單
        adapter2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, address);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2 = (Spinner) view.findViewById(R.id.type2);
        sp2.setAdapter(adapter2);

        final AlertDialog mutiItemDialog = getMutiItemDialog(new String[]{"隱私","公開"});

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO -> set up a php server on eu5.org just to test if we can create successful post requests
                String url = "http://ncnurunforall-yychiu.rhcloud.com/groups";
                String url2 = "http://ncnurunforall-yychiu.rhcloud.com/notices";
                try {
                    doPostRequest(url);
                    doNotificationPostRequest(url2);
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

//        image_button =(Button)view.findViewById(R.id.btnSelectPhoto);
        viewImage=(ImageView)view.findViewById(R.id.viewImage);
        viewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
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
                .add("location", sp.getSelectedItem().toString() + sp2.getSelectedItem().toString())
                .add("content", editText2.getText().toString())
                .add("time", time.getText().toString())
                .add("date", date.getText().toString())
                .add("privacy", privacy.getText().toString())
                .add("username", name)
                .add("creatername",name)
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


    void doNotificationPostRequest(String url) throws IOException {
        String[] selectedfriends = friends.split(" ");
        SharedPreferences preferences = getActivity().getSharedPreferences("here", Context.MODE_PRIVATE);       //目前使用者的名字
        String user_name = preferences.getString("name","error");
        for(String d : selectedfriends) {
            if(Objects.equals(d, "")){
                break;
            }
            OkHttpClient client = new OkHttpClient();

            RequestBody formBody = new FormBody.Builder()
                    .add("user_name", user_name)
                    .add("notice_name", d)
                    .add("content", user_name + " 想邀請你加入" + editText.getText().toString() + "團!")
                    .add("groupname",editText.getText().toString())
                    .add("ViewType", "2")
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
    }


    void handlePostResponse(String response) {
        Log.i("OkHttpPost", response);
    }

    //第一個下拉類別的監看
    private AdapterView.OnItemSelectedListener selectListener = new AdapterView.OnItemSelectedListener(){
        public void onItemSelected(AdapterView<?> parent, View v, int position,long id){
            //讀取第一個下拉選單是選擇第幾個
            int pos = sp.getSelectedItemPosition();
            //重新產生新的Adapter，用的是二維陣列type2[pos]
            adapter2 = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, type2[pos]);
            //載入第二個下拉選單Spinner
            sp2.setAdapter(adapter2);
        }

        public void onNothingSelected(AdapterView<?> arg0){

        }

    };
    private void selectImage() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                    viewImage.setImageBitmap(bitmap);

                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {

                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getActivity().getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                //Log.w("path of image from gallery......******************.........", picturePath+"");
                viewImage.setImageBitmap(thumbnail);
            }
        }
    }



    @Override
    public void onClick(View view) {
        showMultiChoiceItems();
    }

    private void showMultiChoiceItems() {

        SharedPreferences preferences = this.getActivity().getSharedPreferences("here", Context.MODE_PRIVATE);
        String name = preferences.getString("name","error");

        final String[] province = getFriend_Name(name);


        AlertDialog builder = new AlertDialog.Builder(getActivity())
                .setTitle("想新增的好友：")
                .setMultiChoiceItems(province,

                        new boolean[]{false, false, false, false, false},

                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                // TODO Auto-generated method stub
                            }
                        })

                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s = "新增了：";
                        // 扫描所有的列表项，如果当前列表项被选中，将列表项的文本追加到s变量中。
                        for (int i = 0; i < province.length; i++) {
                            if (lv.getCheckedItemPositions().get(i)) {
                                friends += lv.getAdapter().getItem(i) + " ";
                            }
                        }

                        // 用户至少选择了一个列表项
                        if (lv.getCheckedItemPositions().size() > 0) {
                            new AlertDialog.Builder(getActivity()).setMessage(s+friends).show();
                            System.out.println(lv.getCheckedItemPositions().size());
                        }

                        // 用户未选择任何列表项
                        else if (lv.getCheckedItemPositions().size() <= 0) {
                            new AlertDialog.Builder(getActivity()).setMessage("您未選取任何好友！").show();
                        }
                        friend_txv.setText(new StringBuilder().append(s).append(friends).toString());
                    }
                }).setNegativeButton("取消", null).create();
        lv = builder.getListView();
        builder.show();
    }

    public String[] getFriend_Name(String name) {
        final String[][] friend_name = new String[1][1];

        OkHttpClient client = new OkHttpClient();


        Request req = new Request.Builder()
                .url("http://ncnurunforall-yychiu.rhcloud.com/friendlists/" + name)
                .build();
        Call call = client.newCall(req);

        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String aFinalString = response.body().string();
                System.out.println(aFinalString);

                if (response.isSuccessful()) try {
                    final JSONArray array = new JSONArray(aFinalString);
                    friend_name[0] = new String[array.length()];
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        friend_name[0][i] = obj.getString("friend_name");

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
        if (friend_name[0][0] == null) {
            synchronized (this) {
                try {
                    wait(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            onStart();
        }
        return friend_name[0];
    }
}
