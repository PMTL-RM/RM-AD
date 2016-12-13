package com.so2.running.Fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.so2.running.MainActivity;
import com.so2.running.MapFragment;
import com.so2.running.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class JoinedTeamListDetail extends Fragment {
    View view;
    JoinedTeamListItem item;
    Button returnbutton, startrun ;

    public void setItem (JoinedTeamListItem item)
    {
        this.item = item;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_team_list_detail, container, false);

        final SharedPreferences preferences = this.getActivity().getSharedPreferences("group_running", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.putString("groupname",item.getGroupname());
        editor.apply();


        //Get textview from view
        TextView groupname = (TextView) view.findViewById(R.id.groupname);
        TextView name = (TextView) view.findViewById(R.id.uname);
        TextView content = (TextView) view.findViewById(R.id.content);
        TextView privacy = (TextView) view.findViewById(R.id.privacy);
        TextView location = (TextView) view.findViewById(R.id.location);
        TextView date = (TextView) view.findViewById(R.id.date);
        TextView start = (TextView) view.findViewById(R.id.starttext);
        TextView end = (TextView) view.findViewById(R.id.endtext);
        TextView join = (TextView) view.findViewById(R.id.join);
        Button startrun =(Button) view.findViewById(R.id.startrun);

        final ImageView img = (ImageView)view.findViewById(R.id.img);

        //Set data
        groupname.setText(item.getGroupname());
        name.setText(item.getCreatername());
        content.setText(item.getContent());
        privacy.setText(item.getPrivacy());
        location.setText(item.getLocation());
        start.setText(item.getStart());
        end.setText(item.getEnd());
        join.setText(item.getCount());
        date.setText(String.format("%s%s", item.getDate(), item.getTime()));

        startrun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verify GPS availability
                if (((MainActivity) getActivity()).getGPSFix()) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.content_frame, new MapFragment());
                    ft.commit();
                } else {
                    FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.content_frame, new MapFragment());
                    ft.commit();
                }
            }
        });


        final String url ="http://ncnurunforall-yychiu.rhcloud.com/images/"+ item.getUrl();
        Picasso.with(view.getContext()).load(url.trim()).resize(50, 50).error(R.drawable.bg).centerInside().into(new Target() {
            @Override
            public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from){
                /* Save the bitmap or do something with it here */

                //Set it in the ImageView
                img.setImageBitmap(bitmap);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {}

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {}
        });





        //Set ActionBar title
        getActivity().setTitle("詳細資料");


        return view;
    }
}
