/*
                    RUNNING
   Copyright (C) 2015  Alessandro Mereu, Maurizio Romano, Matteo Enrico Serpi

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * This is the application main activity
 */

package com.so2.running;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends ActionBarActivity {
   final Context context = this;
   private DrawerLayout mDrawerLayout;
   private ListView mDrawerList;
   private String[] itemList; //Navigation drawer items
   private ActionBarDrawerToggle mDrawerToggle;
   private boolean isGPSFix = false;
   private long lastUpdateMillis;   //used to verify GPS availability
   private Location lastLocation;   //used to verify GPS availability

   protected void onCreate(Bundle savedInstanceState) {

      //消除標題列
      requestWindowFeature(Window.FEATURE_NO_TITLE);
      //消除狀態列
      getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      //Set ActionBar
      ActionBar actionBar = getSupportActionBar();
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setElevation(0);
      actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.Black)));

      //Set NavigationDrawer
      mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
      mDrawerList = (ListView) findViewById(R.id.left_drawer);
      mDrawerList.setBackgroundResource(R.color.Black);
      itemList = getResources().getStringArray(R.array.item_list);

      //Set the content of the activity with the MainFagment
      MainActivity.changeFragment(getFragmentManager(), new MainFragment());

      // Set the adapter for the list view
      mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, itemList));
      mDrawerList.setOnItemClickListener(new ListView.OnItemClickListener() {
         //Manage events on NavigationDrawer items click
         public void onItemClick(AdapterView parent, View view, int position, long id) {

            switch (position) {
               //Go to session list
               case 0:
                  MainActivity.changeFragment(getFragmentManager(), new UserActivity());
                  mDrawerLayout.closeDrawer(mDrawerList);
                  break;
               //Go to session list
               case 1:
                  FragmentManager sessionsFragmentManager = getFragmentManager();
                  sessionsFragmentManager.popBackStackImmediate(null, sessionsFragmentManager.POP_BACK_STACK_INCLUSIVE);
                  sessionsFragmentManager.beginTransaction()
                          .replace(R.id.content_frame, new SessionListFragment())
                          .commit();

                  mDrawerLayout.closeDrawer(mDrawerList);
                  break;

               //Show import dialog
               case 2:
                  AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                  alertBuilder
                          .setTitle(getString(R.string.importDialogTitle))
                          .setMessage(getString(R.string.importDialogMessage))
                          .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                             }
                          });

                  AlertDialog endSessionDialog = alertBuilder.create();

                  endSessionDialog.show();
                  mDrawerLayout.closeDrawer(mDrawerList);
                  break;

               //Show export dialog
               case 3:
                  MainActivity.alertExportDialogBehavior(context);
                  mDrawerLayout.closeDrawer(mDrawerList);
                  break;

               //Go to settings
               case 4:
                  MainActivity.changeFragment(getFragmentManager(), new ApplicationSettingsFragment());
                  mDrawerLayout.closeDrawer(mDrawerList);
                  break;

               //Go to info
               case 5:
                  MainActivity.changeFragment(getFragmentManager(), new InfoFragment());
                  mDrawerLayout.closeDrawer(mDrawerList);
                  break;
               //Go to Back
               case 6:
                  MainActivity.changeFragment(getFragmentManager(), new MainFragment());
                  mDrawerLayout.closeDrawer(mDrawerList);
                  break;
            }

         }
      });


      mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
         @Override
         public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            invalidateOptionsMenu();
         }

         @Override
         public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
            invalidateOptionsMenu();
         }
      };

      mDrawerLayout.setDrawerListener(mDrawerToggle);

      //verify GPS availability
      final LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
      GpsStatus.Listener gpsListener = new GpsStatus.Listener() {
         @Override
         public void onGpsStatusChanged(int event) {
            switch (event) {
               case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                  if (lastLocation != null) {
                     isGPSFix = (SystemClock.elapsedRealtime() - lastUpdateMillis < 3000);
                  }

                  if (isGPSFix) {
                     // A fix has been acquired.
                  } else {
                     // The fix has been lost.
                  }
                  break;

               case GpsStatus.GPS_EVENT_FIRST_FIX:
                  // Do something.
                  isGPSFix = true;
                  break;
            }
         }
      };

      final LocationListener locationListener = new LocationListener() {
         public void onLocationChanged(Location newLocation) {
            lastUpdateMillis = SystemClock.elapsedRealtime();
            lastLocation = newLocation;
         }

         public void onStatusChanged(String provider, int status, Bundle extras) {
         }

         public void onProviderEnabled(String provider) {
         }

         public void onProviderDisabled(String provider) {
         }
      };

      if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
         // TODO: Consider calling
         //    ActivityCompat#requestPermissions
         // here to request the missing permissions, and then overriding
         //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
         //                                          int[] grantResults)
         // to handle the case where the user grants the permission. See the documentation
         // for ActivityCompat#requestPermissions for more details.
         return;
      }
      locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, locationListener);
      locationManager.addGpsStatusListener(gpsListener);
   }

   //actionBar item
   //android.support.v7.app.ActionBar actionBar = getSupportActionBar();

   public boolean onCreateOptionsMenu(Menu menu){
      MenuInflater menuInflater =getMenuInflater();
      menuInflater.inflate(R.menu.menu_main,menu);
      return super.onCreateOptionsMenu(menu);
   }


   // Called when invalidateOptionsMenu() is invoked
   public boolean onPrepareOptionsMenu(Menu menu) {
      return super.onPrepareOptionsMenu(menu);
   }

   // Pass the event to ActionBarDrawerToggle
   public boolean onOptionsItemSelected(MenuItem item) {
      if (mDrawerToggle.onOptionsItemSelected(item)) {
         return true;
      }
      switch (item.getItemId()){
         case R.id.action_location:
            startActivity(new Intent(this, MapsActivity.class));
            //MainActivity.changeFragment(getFragmentManager(), new ManagementTeamActivity());
            Toast.makeText(getApplicationContext(),"Fuck you",Toast.LENGTH_SHORT).show();
            return true;
         case R.id.action_search:
            MainActivity.changeFragment(getFragmentManager(), new SearchFragment());
            //startActivity(new Intent(this, SearchFragment.class));
            Toast.makeText(getApplicationContext(),"Settings option selected",Toast.LENGTH_SHORT).show();
            return true;
         case R.id.action_friend:
            startActivity(new Intent(this, ManagementTeamActivity.class));
            //MainActivity.changeFragment(getFragmentManager(), new ManagementTeamActivity());
            Toast.makeText(getApplicationContext(),"Fuck you",Toast.LENGTH_SHORT).show();
            return true;
         case R.id.action_announcement:
            //startActivity(new Intent(this, MapsActivity.class));
            Toast.makeText(getApplicationContext(),"Settings option selected",Toast.LENGTH_SHORT).show();
            return true;
      }
      return false;
   }

   //If a JSON file is opened, the application import the session
   protected void onStart() {
      super.onStart();

      final Intent intent = getIntent();
      if (intent != null){

         //got intent
         final android.net.Uri data = intent.getData ();

         if (data != null){

            //got data
            RunDbHelper dbHelper = new RunDbHelper(getApplicationContext());
            if(dbHelper.importTraining(new File(data.getEncodedPath()))){
               Toast.makeText(this, R.string.done, Toast.LENGTH_SHORT).show();
            }
            else{
               Toast.makeText(this, R.string.importFailed, Toast.LENGTH_SHORT).show();
            }

         }
      }
   }

   public void setGPSFix (boolean status )
   {
      this.isGPSFix = status;
   }

   public boolean getGPSFix ()
   {
      return this.isGPSFix;
   }

   //Update session list on restart
   protected void onRestart ()
   {
      super.onRestart();
      FragmentManager sessionsFragmentManager = getFragmentManager();
      sessionsFragmentManager.popBackStackImmediate(null, sessionsFragmentManager.POP_BACK_STACK_INCLUSIVE);
      sessionsFragmentManager.beginTransaction()
              .replace(R.id.content_frame, new SessionListFragment())
              .commit();
   }

   @Override
   protected void onPostCreate(Bundle savedInstanceState) {
      super.onPostCreate(savedInstanceState);
      mDrawerToggle.syncState();
   }

   /**
    * Change the activity content with the new fragment
    * @param manager FrgamentManager
    * @param newFragment new fragment to display
    */
   public static void changeFragment (FragmentManager manager, Fragment newFragment)
   {
      FragmentManager fragmentManager = manager;
      fragmentManager.beginTransaction()
              .replace(R.id.content_frame, newFragment)
              .addToBackStack("")
              .commit();
   }

   @Override
   //Back button override
   public void onBackPressed()
   {
      /*FragmentManager fm = getFragmentManager();

      if(fm.getBackStackEntryCount()>0)
      {
         fm.popBackStackImmediate(null, fm.POP_BACK_STACK_INCLUSIVE);

         fm.beginTransaction()
                 .replace(R.id.content_frame, new SessionListFragment())
                 .commit();
      }
      else
      {
         super.onBackPressed();
      }*/
      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setTitle(R.string.app_name);
      builder.setMessage("回上頁將會清除目前作業資料，確定返回上一頁？");
      builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int which) {
            finish();
         }
      });

      builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
         }
      });

      AlertDialog alert = builder.create();
      alert.show();
   }

   /**
    * Create export dialog
    * @param context activity context
    */
   public static void alertExportDialogBehavior (final Context context)
   {
      String storageDir = Environment.getExternalStorageDirectory() + "/Running/";
      final File saveFolder = new File(storageDir);
      boolean success = true;

      //Verify memory availability
      if (!saveFolder.exists())
      {
         success = saveFolder.mkdir();
      }

      if (!success)
      {
         //TODO: handle error
      }

      //Set export path
      final String path = Environment.getExternalStorageDirectory()+ "/Running/";


      //Create the dialog
      AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
      alertBuilder
              .setTitle("Export sessions")
              .setMessage("Do you want to export sessions? \nFiles will be exported in: " + path)
              .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {

                    RunDbHelper helper = new RunDbHelper(context);
                    helper.exportDb(path);

                    //CharSequence text = Resources.getSystem().getString(R.string.done);
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, R.string.done, duration);
                    toast.show();

                    dialog.dismiss();
                 }
              })
              .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                 }
              });

      AlertDialog endSessionDialog = alertBuilder.create();

      endSessionDialog.show();
   }

}

