package com.so2.running.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.so2.running.Fragment.FriendsListItem;
import com.so2.running.R;

import java.util.ArrayList;
import java.util.List;


public class FriendsAdapter extends ArrayAdapter<FriendsListItem> {
    private LayoutInflater mInflater;
    private ArrayList<FriendsListItem> sessionList;

    public FriendsAdapter(Context context, int resource, List<FriendsListItem> objects) {
        super(context, resource, objects);
        sessionList = (ArrayList) objects;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return sessionList.size();
    }

    @Override
    public FriendsListItem getItem(int arg0) {
        return sessionList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // TODO Auto-generated method stub

        TextView name;

        convertView = mInflater.inflate(R.layout.friends_layout, null);

        name = (TextView) convertView.findViewById(R.id.uname);


        name.setText(sessionList.get(position).getName());

        return convertView;
    }


}