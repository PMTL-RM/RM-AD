package tw.com.flag.RM_MAP;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class Main2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void gotoMainActivity(View v) {
        Intent it = new Intent(this,MainActivity.class); //建立Intent並設定目標Activity
        startActivity(it);  //啟動Intent中目標Activity
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) { // 依照選項的 id 來處理
            case R.id.finish:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
