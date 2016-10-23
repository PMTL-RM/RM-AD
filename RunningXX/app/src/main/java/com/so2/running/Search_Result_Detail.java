/*
                    RUNNING
   Copyright (C) 2015  Alessandro Mereu, Maurizio Romano, Matteo Enrico Serpi

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * This class manage data visualization of the session
 */

package com.so2.running;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.so2.running.Fragment.TeamListItem;

public class Search_Result_Detail extends Fragment {
    View view;
    TeamListItem item;
    Button returnbutton ;

    public void setItem (TeamListItem item)
    {
        this.item = item;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.search_result_detail, container, false);
        returnbutton = (Button)view.findViewById(R.id.returnbutton);


        returnbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.content_frame, new Search_Result());
                ft.commit();
            }
        });

        //Get textview from view
        TextView name = (TextView) view.findViewById(R.id.uname);
        TextView content = (TextView) view.findViewById(R.id.content);
        TextView privacy = (TextView) view.findViewById(R.id.privacy);
        TextView date = (TextView) view.findViewById(R.id.date);
        TextView location = (TextView) view.findViewById(R.id.location);


        //Set data
        name.setText(item.getUsername());
        content.setText(item.getContent());
        privacy.setText(item.getPrivacy());
        date.setText(item.getDate());
        location.setText(item.getLocation());

        String creater_name = item.getUsername();

        final SharedPreferences preferences = getActivity().getSharedPreferences("creater_detail", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.putString("creater_name",creater_name).apply();



        //Set ActionBar title
        getActivity().setTitle("詳細資料");


        return view;
    }
}
