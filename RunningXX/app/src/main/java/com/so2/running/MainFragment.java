package com.so2.running;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;


public class MainFragment extends Fragment {

   Typeface weatherFont;

   TextView cityField;
   TextView updatedField;
   TextView detailsField;
   TextView currentTemperatureField;
   TextView weatherIcon;

   Handler handler;

   private ImageView newSessionButton;
   private ImageView sessionListButton;
   private ImageView createteamButton;
//   private boolean isGPSFix = false;
//   private long lastUpdateMillis;   //used to verify GPS availability
//   private Location lastLocation;   //used to verify GPS availability
   private View view;

   public MainFragment() {
      handler = new Handler();

   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


      view = inflater.inflate(R.layout.fragment_main_content, container, false);

      cityField = (TextView) view.findViewById(R.id.city_field);
      updatedField = (TextView) view.findViewById(R.id.updated_field);
      detailsField = (TextView) view.findViewById(R.id.details_field);
      currentTemperatureField = (TextView) view.findViewById(R.id.current_temperature_field);
      weatherIcon = (TextView) view.findViewById(R.id.weather_icon);

      weatherIcon.setTypeface(weatherFont);

      newSessionButton = (ImageView) view.findViewById(R.id.newSessionButton);
      newSessionButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            //verify GPS availability
            if (((MainActivity) getActivity()).getGPSFix()) {
               DialogFragment newFragment = new NewSessionDialog();
               newFragment.show(getFragmentManager(), "New Training");
            } else {
               DialogFragment newFragment = new NewSessionDialog();
               newFragment.show(getFragmentManager(), "New Training");
               //Toast.makeText(getActivity(), R.string.gpsNoFix, Toast.LENGTH_SHORT).show();
            }
         }
      });

      //Go to session list
      sessionListButton = (ImageView) view.findViewById(R.id.sessionListButton);
      sessionListButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            FragmentManager sessionsFragmentManager = getFragmentManager();
            sessionsFragmentManager.popBackStackImmediate(null, sessionsFragmentManager.POP_BACK_STACK_INCLUSIVE);
            sessionsFragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new SessionListFragment())
                    .commit();
         }
      });

      //Go to session list
      createteamButton = (ImageView) view.findViewById(R.id.createteamButton);
      createteamButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            FragmentManager sessionsFragmentManager = getFragmentManager();
            sessionsFragmentManager.popBackStackImmediate(null, sessionsFragmentManager.POP_BACK_STACK_INCLUSIVE);
            sessionsFragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new CreateTeamFragment())
                    .commit();
         }
      });

      weatherIcon.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            showInputDialog();
         }
      });

//      //verify GPS availability
//      final LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//      GpsStatus.Listener gpsListener = new GpsStatus.Listener() {
//         @Override
//         public void onGpsStatusChanged(int event) {
//            switch (event) {
//               case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
//                  if (lastLocation != null) {
//                     isGPSFix = (SystemClock.elapsedRealtime() - lastUpdateMillis < 3000);
//                  }
//
//                  if (isGPSFix) {
//                     // A fix has been acquired.
//                  } else {
//                     // The fix has been lost.
//                  }
//                  break;
//
//               case GpsStatus.GPS_EVENT_FIRST_FIX:
//                  // Do something.
//                  isGPSFix = true;
//                  break;
//            }
//         }
//      };
//
//      final LocationListener locationListener = new LocationListener() {
//         public void onLocationChanged(Location newLocation) {
//            lastUpdateMillis = SystemClock.elapsedRealtime();
//            lastLocation = newLocation;
//         }
//
//         public void onStatusChanged(String provider, int status, Bundle extras) {
//         }
//
//         public void onProviderEnabled(String provider) {
//         }
//
//         public void onProviderDisabled(String provider) {
//         }
//      };
//
//      if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//         // TODO: Consider calling
//         //    ActivityCompat#requestPermissions
//         // here to request the missing permissions, and then overriding
//         //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//         //                                          int[] grantResults)
//         // to handle the case where the user grants the permission. See the documentation
//         // for ActivityCompat#requestPermissions for more details.
//         return null;
//      }
//      locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, locationListener);
//      locationManager.addGpsStatusListener(gpsListener);

      getActivity().setTitle("主畫面");

      return view;
   }
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weather.ttf");
      updateWeatherData(new CityPreference(getActivity()).getCity());
   }
   private void updateWeatherData(final String city) {
      new Thread() {
         public void run() {
            final JSONObject json = RemoteFetch.getJSON(getActivity(), city);
            if (json == null) {
               handler.post(new Runnable() {
                  public void run() {
                     Toast.makeText(getActivity(),
                             getActivity().getString(R.string.place_not_found),
                             Toast.LENGTH_LONG).show();
                  }
               });
            } else {
               handler.post(new Runnable() {
                  public void run() {
                     renderWeather(json);
                  }
               });
            }
         }
      }.start();
   }


   private void renderWeather(JSONObject json) {
      try {
         cityField.setText(String.format("%s, %s", json.getString("name").toUpperCase(Locale.US), json.getJSONObject("sys").getString("country")));

         JSONObject details = json.getJSONArray("weather").getJSONObject(0);
         JSONObject main = json.getJSONObject("main");
         detailsField.setText(
                 String.format("%s\nHumidity: %s%%\nPressure: %s hPa", details.getString("description").toUpperCase(Locale.US), main.getString("humidity"), main.getString("pressure")));

         currentTemperatureField.setText(
                 String.format("%s ℃", String.format("%.2f", main.getDouble("temp")-273.15)));

         DateFormat df = DateFormat.getDateTimeInstance();
         String updatedOn = df.format(new Date(json.getLong("dt") * 1000));
         updatedField.setText("Last update: " + updatedOn);

         setWeatherIcon(details.getInt("id"),
                 json.getJSONObject("sys").getLong("sunrise") * 1000,
                 json.getJSONObject("sys").getLong("sunset") * 1000);

      } catch (Exception e) {
         Log.e("SimpleWeather", "One or more fields not found in the JSON data");
      }
   }

   private void setWeatherIcon(int actualId, long sunrise, long sunset){
      int id = actualId / 100;
      String icon = "";
      if(actualId == 800){
         long currentTime = new Date().getTime();
         if(currentTime>=sunrise && currentTime<sunset) {
            icon = getActivity().getString(R.string.weather_sunny);
         } else {
            icon = getActivity().getString(R.string.weather_clear_night);
         }
      } else {
         switch(id) {
            case 2 : icon = getActivity().getString(R.string.weather_thunder);
               break;
            case 3 : icon = getActivity().getString(R.string.weather_drizzle);
               break;
            case 7 : icon = getActivity().getString(R.string.weather_foggy);
               break;
            case 8 : icon = getActivity().getString(R.string.weather_cloudy);
               break;
            case 6 : icon = getActivity().getString(R.string.weather_snowy);
               break;
            case 5 : icon = getActivity().getString(R.string.weather_rainy);
               break;
         }
      }

      weatherIcon.setText(icon);
   }
   private void showInputDialog(){
      android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
      builder.setTitle("Change city");
      final EditText input = new EditText(getActivity());
      input.setInputType(InputType.TYPE_CLASS_TEXT);
      builder.setView(input);
      builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int which) {
            changeCity(input.getText().toString());
         }
      });
      builder.show();
   }

   public void changeCity(String city) {
      updateWeatherData(city);
   }
}


