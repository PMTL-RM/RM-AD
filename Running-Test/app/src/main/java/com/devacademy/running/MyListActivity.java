package com.devacademy.running;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MyListActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_list);

        String[] date = new String[] {"TODAY", "23/11/2015"};
        String[] kilometers = new String[] {"7.72km", "6.34km"};
        String[] time1 = new String[] {"8\'58\"", "6\'56\""};
        String[] time2 = new String[] {"1:09:18", "43:57"};

        MyArrayAdapter adapter = new MyArrayAdapter(this, date, kilometers, time1, time2);

        setListAdapter(adapter);
    }

}
