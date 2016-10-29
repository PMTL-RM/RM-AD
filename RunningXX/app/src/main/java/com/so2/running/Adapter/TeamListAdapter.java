package com.so2.running.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.so2.running.Fragment.TeamListItem;
import com.so2.running.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

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
        final ImageView groupimage;
        String url = "http://ncnurunforall-yychiu.rhcloud.com/images/"+ sessionList.get(position).getImagename();

        convertView = mInflater.inflate(R.layout.fragment_team_list_item, null);
        groupname = (TextView) convertView.findViewById(R.id.groupname);
        date = (TextView) convertView.findViewById(R.id.date);
        location = (TextView) convertView.findViewById(R.id.location);
        groupimage = (ImageView)convertView.findViewById(R.id.groupimage) ;



        groupname.setText(sessionList.get(position).getGroupname());
        date.setText(new StringBuilder().append(sessionList.get(position).getDate()).append(sessionList.get(position).getTime()).toString());
        location.setText(sessionList.get(position).getLocation());

        Picasso.with(convertView.getContext()).load(url.trim()).resize(50, 50).error(R.drawable.bg).centerInside().into(new Target() {
            @Override
            public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from){
                /* Save the bitmap or do something with it here */

                //Set it in the ImageView
                groupimage.setImageBitmap(bitmap);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {}

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {}
        });

        return convertView;
    }


}