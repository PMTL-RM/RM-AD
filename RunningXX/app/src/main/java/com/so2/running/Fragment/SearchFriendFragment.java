package com.so2.running.Fragment;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.so2.running.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFriendFragment extends Fragment {

    View view;
    Button result;
    EditText searchname;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("搜尋人");

        view = inflater.inflate(R.layout.fragment_friend_search, container, false);


        searchname = (EditText)view.findViewById(R.id.searchname);
        result = (Button)view.findViewById(R.id.result);


        result.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final SharedPreferences preferences = getActivity().getSharedPreferences("searchfriend", Context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.putString("searchname",searchname.getText().toString());
                editor.apply();
                String searchname1 = preferences.getString("searchname","error");



                System.out.println("searchfriend :::::::"+searchname1);

                FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.content_frame, new SearchFriend_Result());
                ft.commit();
            }
        });


        return view;
    }

}
