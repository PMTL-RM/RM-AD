package com.so2.running.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.so2.running.Fragment.TeamListItem;
import com.so2.running.R;

import java.util.ArrayList;
import java.util.List;

public class TeamListAdapter extends ArrayAdapter<TeamListItem> {
    private LayoutInflater mInflater;
    private ArrayList<TeamListItem> sessionList;

    public TeamListAdapter(Context context, int resource, List<TeamListItem> objects) {
        super(context, resource, objects);
        sessionList = (ArrayList) objects;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return sessionList.size();
    }

    @Override
    public TeamListItem getItem(int arg0) {
        return sessionList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        TextView groupname;
        TextView date;
        TextView location;

        convertView = mInflater.inflate(R.layout.fragment_team_list_item, null);
        groupname = (TextView) convertView.findViewById(R.id.groupname);
        date = (TextView) convertView.findViewById(R.id.date);
        location = (TextView) convertView.findViewById(R.id.location);



        groupname.setText(sessionList.get(position).getGroupname());
        date.setText(new StringBuilder().append(sessionList.get(position).getDate()).append(sessionList.get(position).getTime()).toString());
        location.setText(sessionList.get(position).getLocation());

        return convertView;
    }


}