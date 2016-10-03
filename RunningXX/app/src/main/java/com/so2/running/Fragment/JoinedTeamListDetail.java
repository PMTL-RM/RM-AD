package com.so2.running.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.so2.running.R;

public class JoinedTeamListDetail extends Fragment {
    View view;
    JoinedTeamListItem item;
    Button returnbutton ;

    public void setItem (JoinedTeamListItem item)
    {
        this.item = item;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_team_list_detail, container, false);


        //Get textview from view
        TextView groupname = (TextView) view.findViewById(R.id.groupname);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView content = (TextView) view.findViewById(R.id.content);
        TextView privacy = (TextView) view.findViewById(R.id.privacy);
        TextView location = (TextView) view.findViewById(R.id.location);
        TextView date = (TextView) view.findViewById(R.id.date);

        //Set data
        groupname.setText(item.getGroupname());
        name.setText(item.getCreatername());
        content.setText(item.getContent());
        privacy.setText(item.getPrivacy());
        location.setText(item.getLocation());
        date.setText(String.format("%s%s", item.getDate(), item.getTime()));





        //Set ActionBar title
        getActivity().setTitle("詳細資料");


        return view;
    }
}
