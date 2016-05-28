package com.devacademy.running;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


/**
 * Created by Tudor on 06.12.2015.
 */
public class MyArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] date, kilometers, time1, time2;

    public MyArrayAdapter(Context context, String[] date, String[] kilometers, String[] time1, String[] time2) {
        super(context, -1, date);
        this.context = context;
        this.date = date;
        this.kilometers = kilometers;
        this.time1 = time1;
        this.time2 = time2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.row_layout, null);
            holder = new ViewHolder();
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.kilometers = (TextView) convertView.findViewById(R.id.kilometers);
            holder.time1 = (TextView) convertView.findViewById(R.id.time1);
            holder.time2 = (TextView) convertView.findViewById(R.id.time2);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.date.setText(date[position]);
        holder.kilometers.setText(kilometers[position]);
        holder.time1.setText(time1[position]);
        holder.time2.setText(time2[position]);

        return convertView;
    }
}
