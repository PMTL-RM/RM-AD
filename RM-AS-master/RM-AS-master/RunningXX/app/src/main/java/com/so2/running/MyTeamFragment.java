package com.so2.running;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyTeamFragment extends Fragment {


    public MyTeamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Set ActionBar title
        getActivity().setTitle(getString(R.string.title_info));
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

}
