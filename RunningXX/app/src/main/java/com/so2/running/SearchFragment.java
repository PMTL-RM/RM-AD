package com.so2.running;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    View view;
    Button result;
    ImageButton datebutton;
    TextView date;

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

    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(getString(R.string.title_info));

        view = inflater.inflate(R.layout.fragment_search, container, false);


        result = (Button)view.findViewById(R.id.result);


        result.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                final SharedPreferences preferences = getActivity().getSharedPreferences("search", Context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.putString("city",type[sp.getSelectedItemPosition()]);
                editor.putString("zone",type2[sp.getSelectedItemPosition()][sp2.getSelectedItemPosition()]);
                editor.putString("date",date.getText().toString());
                editor.apply();
                String city1 = preferences.getString("city","error");
                String zone1 = preferences.getString("zone","error");
                String date1 = preferences.getString("date","error");


                System.out.println("search :::::::"+city1+zone1+date1);

                FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.content_frame, new Search_Result());
                ft.commit();
            }
        });


        //datebutton = (ImageButton)view.findViewById(R.id.datebutton);
        date = (TextView)view.findViewById(R.id.date);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }

        });



        //程式剛啟始時載入第一個下拉選單
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

        return view;
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
}
