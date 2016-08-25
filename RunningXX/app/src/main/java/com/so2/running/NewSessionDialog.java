package com.so2.running;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;


public class NewSessionDialog extends DialogFragment
{

   @Override
   public Dialog onCreateDialog(Bundle savedInstanceState) {

      AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
      // Get the layout inflater
      LayoutInflater inflater = getActivity().getLayoutInflater();
      View view = inflater.inflate(R.layout.fragment_new_session_dialog, null);


      final EditText inputSessionName = (EditText) view.findViewById(R.id.sessionName);
      final NumberPicker inputUserSpeed = (NumberPicker) view.findViewById(R.id.speedPicker);

      //Set picker
      SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
      String prefSpeed = sharedPref.getString("pref_default_speed", "3");
      inputUserSpeed.setMaxValue(50);
      inputUserSpeed.setMinValue(1);
      inputUserSpeed.setValue(Integer.valueOf(prefSpeed));

      RunDbHelper helper = new RunDbHelper(getActivity());

      //Set default session name
      String formattedName = "Allenamento_" + helper.findNextID();
      inputSessionName.setText(formattedName);


      // Inflate and set the layout for the dialog
      // Pass null as the parent view because its going in the dialog layout
      builder.setView(view)
              .setTitle(getString(R.string.newSessionDialogTitle))
              // Add action buttons
              .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int id) {
                    Intent i = new Intent(getActivity(), RunSessionActivity.class);
                    Bundle b = new Bundle();

                    b.putString("sessionName", inputSessionName.getText().toString());
                    b.putInt("userSetSpeed", inputUserSpeed.getValue());
                    i.putExtras(b);

                    startActivity(i);
                 }
              })
              .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int id) {
                    dismiss();
                 }
              });

      return builder.create();
   }
}
