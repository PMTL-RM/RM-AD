package com.so2.running;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ManagementTeamActivity extends Fragment {

    Button joined;
    Button invited;
    Button myteam;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        //Set ActionBar title
        getActivity().setTitle(getString(R.string.title_info));

        view = inflater.inflate(R.layout.activity_management_team, container, false);
        /*joined = (Button) view.findViewById(R.id.joined);
        joined.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                //verify GPS availability
                if (((MainActivity)getActivity()).getGPSFix())
                {
                    DialogFragment newFragment = new NewSessionDialog();
                    newFragment.show(getFragmentManager(), "New Training");
                }
                else
                {
                    Toast.makeText(getActivity(), R.string.gpsNoFix, Toast.LENGTH_SHORT).show();
                }
            }
        });
                                  }
        invited = (Button) view.findViewById(R.id.invited);
        myteam = (Button) view.findViewById(R.id.myteam);*/

        return view;
    }



}
