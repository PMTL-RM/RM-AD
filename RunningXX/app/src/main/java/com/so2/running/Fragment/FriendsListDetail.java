package com.so2.running.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.so2.running.R;

public class FriendsListDetail extends Fragment{
    View view;
    FriendsListItem item;
    Button returnbutton ;
    TextView content ;
    String Username , Creatername , Groupname ;
    public void setItem (FriendsListItem item)
    {
        this.item = item;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.friend_detail, container, false);
        //returnbutton = (Button)view.findViewById(R.id.returnbutton);


//        returnbutton.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.content_frame, new TeamList());
//                ft.commit();
//            }
//        });




        //Get textview from view
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView email = (TextView) view.findViewById(R.id.email);
        TextView birth = (TextView) view.findViewById(R.id.birth);
        TextView gender = (TextView) view.findViewById(R.id.gender);


        //Set data
        name.setText(item.getName());
        email.setText(item.getEmail());
        birth.setText(item.getBirthday());
        gender.setText(item.getSex());
        //Set ActionBar title
        getActivity().setTitle("詳細資料");


        return view;
    }

}
