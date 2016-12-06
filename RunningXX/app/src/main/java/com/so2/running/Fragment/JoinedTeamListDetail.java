package com.so2.running.Fragment;

import android.app.DialogFragment;
import android.app.Fragment;
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
import com.so2.running.NewSessionDialog;
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


        //Get textview from view
        TextView groupname = (TextView) view.findViewById(R.id.groupname);
        TextView name = (TextView) view.findViewById(R.id.uname);
        TextView content = (TextView) view.findViewById(R.id.content);
        TextView privacy = (TextView) view.findViewById(R.id.privacy);
        TextView location = (TextView) view.findViewById(R.id.location);
        TextView date = (TextView) view.findViewById(R.id.date);
        Button startrun =(Button) view.findViewById(R.id.startrun);
        final ImageView img = (ImageView)view.findViewById(R.id.img);

        //Set data
        groupname.setText(item.getGroupname());
        name.setText(item.getCreatername());
        content.setText(item.getContent());
        privacy.setText(item.getPrivacy());
        location.setText(item.getLocation());
        date.setText(String.format("%s%s", item.getDate(), item.getTime()));

        startrun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verify GPS availability
                if (((MainActivity) getActivity()).getGPSFix()) {
                    DialogFragment newFragment = new NewSessionDialog();
                    newFragment.show(getFragmentManager(), "New Training");
                } else {
                    DialogFragment newFragment = new NewSessionDialog();
                    newFragment.show(getFragmentManager(), "New Training");
                    //Toast.makeText(getActivity(), R.string.gpsNoFix, Toast.LENGTH_SHORT).show();
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
