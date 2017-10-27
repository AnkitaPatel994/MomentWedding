package com.ankita.momentwedding;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class InviteFragment extends Fragment {

    View view;
    CircleImageView ivProfileGroom,ivProfileBride;
    TextView txtInviteGroomName,txtInviteBrideName,txtInviteName,txtInviteDetails;

    public InviteFragment() {
        // Required empty public constructor
    }

    public static InviteFragment newInstance(String param1, String param2) {
        InviteFragment fragment = new InviteFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_invite, container, false);

        ivProfileGroom = (CircleImageView)view.findViewById(R.id.ivProfileGroom);
        ivProfileGroom.setBorderColor(Color.parseColor(GetTheme.primaryDarkColor));

        ivProfileBride = (CircleImageView)view.findViewById(R.id.ivProfileBride);
        ivProfileBride.setBorderColor(Color.parseColor(GetTheme.primaryDarkColor));

        LinearLayout llInviteBGT = (LinearLayout)view.findViewById(R.id.llInviteBGT);
        GradientDrawable shapeBg =  new GradientDrawable();
        shapeBg.setCornerRadius(10);
        shapeBg.setColor(Color.parseColor(GetTheme.transparentColor));
        llInviteBGT.setBackground(shapeBg);

        txtInviteGroomName = (TextView)view.findViewById(R.id.txtInviteGroomName);
        txtInviteGroomName.setTextColor(Color.parseColor(GetTheme.textLight));

        txtInviteBrideName = (TextView)view.findViewById(R.id.txtInviteBrideName);
        txtInviteBrideName.setTextColor(Color.parseColor(GetTheme.textLight));

        txtInviteName = (TextView)view.findViewById(R.id.txtInviteName);
        txtInviteName.setTextColor(Color.parseColor(GetTheme.textLight));

        txtInviteDetails = (TextView)view.findViewById(R.id.txtInviteDetails);
        txtInviteDetails.setTextColor(Color.parseColor(GetTheme.textLight));

        TextView lableInviteGroom = (TextView)view.findViewById(R.id.lableInviteGroom);
        lableInviteGroom.setTextColor(Color.parseColor(GetTheme.lableTextColor));

        LinearLayout llInviteBorder = (LinearLayout)view.findViewById(R.id.llInviteBorder);
        llInviteBorder.setBackgroundColor(Color.parseColor(GetTheme.lableTextColor));

        TextView lableInviteWeds = (TextView)view.findViewById(R.id.lableInviteWeds);
        lableInviteWeds.setTextColor(Color.parseColor(GetTheme.lableTextColor));

        LinearLayout llInviteBo = (LinearLayout)view.findViewById(R.id.llInviteBo);
        llInviteBo.setBackgroundColor(Color.parseColor(GetTheme.lableTextColor));

        TextView lableInviteBride = (TextView)view.findViewById(R.id.lableInviteBride);
        lableInviteBride.setTextColor(Color.parseColor(GetTheme.lableTextColor));

        TextView lableHello = (TextView)view.findViewById(R.id.lableHello);
        lableHello.setTextColor(Color.parseColor(GetTheme.lableTextColor));

        GetInviteCard getInviteCard = new GetInviteCard();
        getInviteCard.execute();
        return view;
    }

    private class GetInviteCard extends AsyncTask<String,Void,String> {

        String status,message,groom_pic,bride_pic,groom_name,bride_name,guest_name,inv_details;

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joInvite=new JSONObject();
            try {

                joInvite.put("wedding_id",HomeActivity.wedding_id);
                joInvite.put("guest_id",HomeActivity.guest_id);
                Postdata postdata=new Postdata();
                String pdInt=postdata.post(MainActivity.mainUrl+"getInvitationCard",joInvite.toString());
                JSONObject j=new JSONObject(pdInt);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
                    JSONObject jo=j.getJSONObject("data");

                    groom_pic =jo.getString("groom_pic");
                    bride_pic =jo.getString("bride_pic");
                    groom_name =jo.getString("groom_name");
                    bride_name =jo.getString("bride_name");
                    guest_name =jo.getString("guest_name");
                    inv_details =jo.getString("wedding_invitation");

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(status.equals("1"))
            {

                DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                        .cacheOnDisc(true).cacheInMemory(true)
                        .imageScaleType(ImageScaleType.EXACTLY)
                        .displayer(new FadeInBitmapDisplayer(300)).build();
                final ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity())
                        .defaultDisplayImageOptions(defaultOptions)
                        .memoryCache(new WeakMemoryCache())
                        .discCacheSize(100 * 1024 * 1024).build();

                ImageLoader.getInstance().init(config);

                ImageLoader imageLoader = ImageLoader.getInstance();
                int fallback = 0;
                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                        .cacheOnDisc(true).resetViewBeforeLoading(true)
                        .showImageForEmptyUri(fallback)
                        .showImageOnFail(fallback)
                        .showImageOnLoading(fallback).build();

                txtInviteGroomName.setText(groom_name);
                txtInviteBrideName.setText(bride_name);
                txtInviteName.setText(guest_name);
                txtInviteDetails.setText(inv_details);

                imageLoader.displayImage(groom_pic,ivProfileGroom, options);
                imageLoader.displayImage(bride_pic,ivProfileBride, options);
            }
            else
            {
                Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
