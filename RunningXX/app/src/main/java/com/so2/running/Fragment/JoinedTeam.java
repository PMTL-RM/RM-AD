package com.so2.running.Fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.so2.running.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class JoinedTeam extends Fragment {


    public JoinedTeam() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_joined_team, container, false);
    }

}
