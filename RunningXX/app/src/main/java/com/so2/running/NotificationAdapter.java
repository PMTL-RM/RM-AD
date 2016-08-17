package com.so2.running;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class NotificationAdapter extends ArrayAdapter<NotificationListItem> {
    private LayoutInflater mInflater;
    private ArrayList<NotificationListItem> sessionList;

    public NotificationAdapter(Context context, int resource, List<NotificationListItem> objects) {
        super(context, resource, objects);
        sessionList = (ArrayList) objects;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return sessionList.size();
    }

    @Override
    public NotificationListItem getItem(int arg0) {
        return sessionList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        TextView content;

        convertView = mInflater.inflate(R.layout.notification_list_item, null);

        content = (TextView) convertView.findViewById(R.id.content);


        content.setText(sessionList.get(position).getContent());

        return convertView;
    }


}