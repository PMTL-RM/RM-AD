package com.so2.running.Fragment;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.share.widget.ShareDialog;
import com.so2.running.MainFragment;
import com.so2.running.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class UserInterface extends Fragment {
    ImageView imageView;
    TextView txtName, txtURL, txtGender,txtBd;
    Button btnShare , returnbutton;

    private ShareDialog shareDialog;
    private View view;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_user, container, false);

        returnbutton = (Button)view.findViewById(R.id.returnbutton);
        shareDialog = new ShareDialog(this);

        imageView = (ImageView) view.findViewById(R.id.userPhoto);
        txtName = (TextView) view.findViewById(R.id.name);
/*        txtURL = (TextView) view.findViewById(R.id.txtURL);
        txtGender = (TextView) view.findViewById(R.id.txtGender);
        txtBd = (TextView) view.findViewById(R.id.txtBd);*/



        returnbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.content_frame, new MainFragment());
                ft.commit();
            }
        });

        //Another way to share content
/*        btnShare = (Button) view.findViewById(R.id.btnShare);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle("Hello Guys")
                            .setContentDescription(
                                    "Coder who learned and share")
                            .setContentUrl(Uri.parse("http://so2.com"))
                            .setImageUrl(Uri.parse("https://scontent-sin1-1.xx.fbcdn.net/hphotos-xap1/v/t1.0-9/12936641_845624472216348_1810921572759298872_n.jpg?oh=72421b8fa60d05e68c6fedbb824adfbf&oe=577949AA"))
                            .build();

                    shareDialog.show(linkContent);
                }
            }
        });*/


        GetUserInfo();
/*

        //Like
        LikeView likeView = (LikeView) view.findViewById(R.id.likeView);
        likeView.setLikeViewStyle(LikeView.Style.STANDARD);
        likeView.setAuxiliaryViewPosition(LikeView.AuxiliaryViewPosition.INLINE);

        likeView.setObjectIdAndType(
                "http://so2.com",
                LikeView.ObjectType.OPEN_GRAPH);

*/


        //Share Dialog
        //You cannot preset the shared link in design time, if you do so, the fb share button will
        //look disabled. You need to set in the code as below
/*        ShareButton fbShareButton = (ShareButton) view.findViewById(R.id.fb_share_button);
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentTitle("Hello Guys")
                .setContentDescription(
                        "Coder who learned and share")
                .setContentUrl(Uri.parse("http://so2.com"))
                .setImageUrl(Uri.parse("https://scontent-sin1-1.xx.fbcdn.net/hphotos-xap1/v/t1.0-9/12936641_845624472216348_1810921572759298872_n.jpg?oh=72421b8fa60d05e68c6fedbb824adfbf&oe=577949AA"))

                .build();
        fbShareButton.setShareContent(content);*/
        return view;
    }

    private void GetUserInfo(){
        //this code will help us to obtain information from facebook, if
        //need some other field which not show here, please refer to https://developers.facebook.com/docs/graph-api/using-graph-api/
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        // Application code
                        try{
                            String gender = object.getString("gender");
                            String birthday = object.getString("birthday");
                            String name = object.getString("name");
                            String id = object.getString("id");

                            txtName.setText(name);
                            txtURL.setText(id);
                            txtGender.setText(gender);
                            txtBd.setText(birthday);
                            if (object.has("picture")) {
                                String profilePicUrl = object.getJSONObject("picture").getJSONObject("data").getString("url");
                                Picasso.with(imageView.getContext()).load(profilePicUrl).into(imageView);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,gender,name,birthday,picture.type(large)");
        request.setParameters(parameters);
        request.executeAsync();

    }




}

