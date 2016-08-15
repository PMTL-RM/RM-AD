package com.so2.running;


import android.content.res.Resources;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {


    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notification, container, false);

        Resources res = getResources();
        String[] animals = res.getStringArray(R.array.animals);

        // We need a ListAdapter to adapt the Array to the ListView:
        ListAdapter myArrayAdapter = new NotificationListItem(
                getActivity(),                        // context
                R.layout.notification_list_item,  // layoutResource int
                R.id.triple_user,            // textViewResourceId int
                animals                      // list of objects
        );
                /*
                When you specify your own row layout, you must specify the id (e.g. firstLine)
                of the view element which shall contain the array elements

                Another Basic Example:
                ListAdapter myArrayAdapter = new ArrayAdapter<>(
                                          this, android.R.layout.simple_list_item_1, animals);
                */

        ListView theListView = (ListView) view.findViewById(R.id.myListView);
        // Define a reference to the ListView in our layout resource

        theListView.setAdapter(myArrayAdapter);
        // Tell listView what data to use


        // catch any clicks on our listView:
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                // Display a message with Toast:
                String animalPicked = "You selected " + String.valueOf(adapterView.getItemAtPosition(pos));
                Toast.makeText(getActivity(), animalPicked, Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }

}
