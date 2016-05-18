package tw.com.flag.RM_MAP;

import android.app.AlertDialog;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity
        implements LocationListener {

    static final int MIN_TIME = 5000;// 位置更新條件：5000 毫秒
    static final float MIN_DIST = 5; // 位置更新條件：5 公尺
    LocationManager mgr;        // 定位總管
    TextView txv;
    GoogleMap map;  //操控地圖的物件
    LatLng currPoint; // 儲存目前的位置


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txv = (TextView) findViewById(R.id.txv);
        mgr = (LocationManager)getSystemService(LOCATION_SERVICE);

        setUpMapIfNeeded();


    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();

        // 取得最佳的定位提供者
        String best = mgr.getBestProvider(new Criteria(), true);
        if (best != null) { // 取得快取的最後位置,如果有的話
            txv.setText("取得定位資訊中...");
            mgr.requestLocationUpdates(best, MIN_TIME, MIN_DIST, this);	// 註冊位置事件監聽器
        }
        else { // 無提供者, 顯示提示訊息
            txv.setText("請確認已開啟定位功能!");
        }
    }

    private void setUpMapIfNeeded() {

        if (map == null) {
            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();                     //取得 Google Map 物件
            if (map != null) {
                map.setMyLocationEnabled(true);    // 顯示『我的位置』圖示按鈕
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);    // 設定地圖為普通街道模式
                map.moveCamera(CameraUpdateFactory.zoomTo(18));    // 將地圖縮放級數改為 18
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mgr.removeUpdates(this);	// 停止監聽位置事件
    }

    @Override
    public void onLocationChanged(Location location) { // 位置變更事件
        if(location != null) { // 如果可以取得座標
            txv.setText(String.format("緯度 %.4f, 經度 %.4f (%s 定位 )",
                    location.getLatitude(),  // 目前緯度
                    location.getLongitude(), // 目前經度
                    location.getProvider()));// 定位方式

            // 將地圖中心點移到目前的經緯度
            currPoint = new LatLng(location.getLatitude(), location.getLongitude());
        }
        else { // 無法取得座標
            txv.setText("暫時無法取得定位資訊...");
        }
    }

    @Override
    public void onProviderDisabled(String provider) { }
    @Override
    public void onProviderEnabled(String provider) { }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) { }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) { // 依照選項的 id 來處理
            case R.id.mark:
                map.clear();
                map.addMarker(new MarkerOptions()
                        .position(map.getCameraPosition().target)
                        .title("到此一遊"));
                break;
            case R.id.satellite:
                item.setChecked(!item.isChecked()); // 切換功能表項目的打勾狀態
                if(item.isChecked())               // 設定是否顯示衛星圖
                    map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                else
                    map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.traffic:
                item.setChecked(!item.isChecked()); // 切換功能表項目的打勾狀態
                map.setTrafficEnabled(item.isChecked()); // 設定是否顯示交通圖
                break;
            case R.id.currLocation:
                map.animateCamera( // 在地圖中移動到目前位置
                        CameraUpdateFactory.newLatLng(currPoint));
                break;
            case R.id.setGPS:
                Intent i = new Intent( // 利用 Intent 啟動系統的定位服務設定
                        Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
                break;
            case R.id.about:
                new AlertDialog.Builder(this) // 用交談窗顯示程式版本與版權聲明
                        .setTitle("關於 我的地圖")
                        .setMessage("作者：\n劉兆倫")
                        .setPositiveButton("關閉", null)
                        .show();
                break;
            case R.id.finish:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



}