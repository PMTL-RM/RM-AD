package com.so2.running.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.so2.running.Adapter.GuideAdapter;
import com.so2.running.MainActivity;
import com.so2.running.R;

import java.util.ArrayList;
import java.util.List;


public class GuideActivity extends Activity implements ViewPager.OnPageChangeListener {
    private ViewPager vp;
    private GuideAdapter vpAdapter;
    private List<View> views;
    private ImageView[] dots;
    private int[] ids = {R.id.dot_1, R.id.dot_2, R.id.dot_3};
    private ImageView startButton;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lead_page);
        initViews();
        initDots();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void initViews() {
        LayoutInflater inflater = LayoutInflater.from(this);

        views = new ArrayList<View>();
        views.add(inflater.inflate(R.layout.lead_1, null));
        views.add(inflater.inflate(R.layout.lead_2, null));
        views.add(inflater.inflate(R.layout.lead_3, null));

        vpAdapter = new GuideAdapter(views, this);
        vp = (ViewPager) findViewById(R.id.viewpager);
        vp.setAdapter(vpAdapter);
        startButton = (ImageView) views.get(2).findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        vp.addOnPageChangeListener(this);
    }

    private void initDots() {
        dots = new ImageView[views.size()];
        for (int i = 0; i < views.size(); i++) {
            dots[i] = (ImageView) findViewById(ids[i]);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
         for (int i = 0; i < dots.length; i++) {
             if (position == i) {
                 dots[i].setImageResource(R.drawable.dot_selected);
             } else {
                 dots[i].setImageResource(R.drawable.dot_normal);
             }
         }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
