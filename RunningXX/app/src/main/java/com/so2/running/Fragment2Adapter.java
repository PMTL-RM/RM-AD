package com.so2.running;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class Fragment2Adapter extends ArrayAdapter<Fragment2ListItem> {
    private LayoutInflater mInflater;
    private ArrayList<Fragment2ListItem> sessionList;

    public Fragment2Adapter(Context context, int resource, List<Fragment2ListItem> objects) {
        super(context, resource, objects);
        sessionList = (ArrayList) objects;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return sessionList.size();
    }

    @Override
    public Fragment2ListItem getItem(int arg0) {
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

        convertView = mInflater.inflate(R.layout.frag2_createteam_layout, null);
        groupname = (TextView) convertView.findViewById(R.id.groupname);
        date = (TextView) convertView.findViewById(R.id.date);
        location = (TextView) convertView.findViewById(R.id.location);



        groupname.setText(sessionList.get(position).getGroupname());
        date.setText(new StringBuilder().append(sessionList.get(position).getDate()).append(sessionList.get(position).getTime()).toString());
        location.setText(sessionList.get(position).getLocation());

        return convertView;
    }


}