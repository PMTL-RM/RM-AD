package com.so2.running;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationListItem extends ArrayAdapter<String> {


    // We call the constructor of the super class ArrayAdapter
    public NotificationListItem(Context context, int resource, int textViewResourceId, String[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    // We use the ViewHolder Pattern for ensuring smooth scrolling
    static class ViewHolder {
        ImageView tripleIcon;
        TextView tripleUser;
        TextView tripleStatus;
        TextView tripleContact;
    }

    int i;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView == null) {

            // The LayoutInflater instantiate a layout xml file into its corresponding View objects:
            LayoutInflater myInflater = LayoutInflater.from(getContext());

            convertView = myInflater.inflate(R.layout.notification_list_item, parent, false);

            // The ViewHolder ensures smooth scrolling of our ListView:
            holder = new ViewHolder();
            holder.tripleUser = (TextView) convertView.findViewById(R.id.triple_user);
            holder.tripleStatus = (TextView) convertView.findViewById(R.id.triple_status);
            holder.tripleContact = (TextView) convertView.findViewById(R.id.triple_contact);
            holder.tripleIcon = (ImageView) convertView.findViewById(R.id.triple_icon);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
            i++;
        }

        // Bind the data efficiently with the holder:
        String animal = getItem(position);
        holder.tripleUser.setText(animal);
        holder.tripleStatus.setText("Hier steht jetzt Text");
        holder.tripleContact.setText("eMail");

        // Change some values
        if ( animal.equals("Tiger") || animal.equals("Wal") || animal.equals("Gnu") ) {
            holder.tripleIcon.setImageResource(R.drawable.circle_blue);
            holder.tripleStatus.setText("Hier steht jetzt Text");
            holder.tripleContact.setText("eMail");

        } else {
            holder.tripleIcon.setImageResource(R.drawable.circle_red);
        }
        return convertView;
    }
}
