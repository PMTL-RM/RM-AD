package com.so2.running;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import com.so2.running.RunDbHelper;


public class ApplicationSettingsFragment extends PreferenceFragment {
   @Override
   public void onCreate(Bundle savedInstanceState)
   {
      getActivity().setTitle(getString(R.string.title_settings));

      super.onCreate(savedInstanceState);

      // Load the preferences from an XML resource
      addPreferencesFromResource(R.layout.fragment_application_settings);

      //Create a dialog for pref_delete_database
      Preference dialogDelete = (Preference) getPreferenceScreen().findPreference("pref_delete_database");
      dialogDelete.setOnPreferenceClickListener( new Preference.OnPreferenceClickListener() {
         @Override
         public boolean onPreferenceClick(Preference preference) {

            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());

            alertBuilder
                    .setTitle(getString(R.string.deleteDialogTitle))
                    .setMessage(getString(R.string.deleteDataDialogMessage))
                    .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener()
                    {
                       @Override
                       public void onClick(DialogInterface dialog, int which)
                       {

                          //Erase all database content
                          RunDbHelper helper = new RunDbHelper(getActivity());
                          SQLiteDatabase db = helper.getWritableDatabase();
                          db.delete("session", null, null);
                          Toast toast = Toast.makeText(getActivity(), getString(R.string.done), Toast.LENGTH_SHORT);
                          toast.show();
                          dialog.dismiss();
                       }
                    })
                    .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener()
                    {
                       @Override
                       public void onClick(DialogInterface dialog, int which)
                       {
                          dialog.dismiss();
                       }
                    });

            AlertDialog endSessionDialog = alertBuilder.create();

            endSessionDialog.show();

            return false;
         }
      });

      Preference vibCheckbox = (Preference) getPreferenceScreen().findPreference("pref_vibration");
      vibCheckbox.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
         public boolean onPreferenceChange(Preference preference, Object newValue) {
            if (newValue.toString().equals("true")) {
               Toast.makeText(getActivity(), R.string.vibration_active, Toast.LENGTH_SHORT).show();
            }
            else
            {
            Toast.makeText(getActivity(), R.string.vibration_not_active, Toast.LENGTH_SHORT).show();
        }
        return true;
      }
      });
   }
}