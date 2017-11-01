package com.ankita.momentwedding;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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

        view = inflater.inflate(R.layout.fragment_invite, container, false);

        ivProfileGroom = (CircleImageView)view.findViewById(R.id.ivProfileGroom);
        ivProfileGroom.setBorderColor(ContextCompat.getColor(getActivity(),R.color.colorPrimaryDark));

        ivProfileBride = (CircleImageView)view.findViewById(R.id.ivProfileBride);
        ivProfileBride.setBorderColor(ContextCompat.getColor(getActivity(),R.color.colorPrimaryDark));

        LinearLayout llInviteBGT = (LinearLayout)view.findViewById(R.id.llInviteBGT);
        GradientDrawable shapeBg =  new GradientDrawable();
        shapeBg.setCornerRadius(10);
        shapeBg.setColor(ContextCompat.getColor(getActivity(),R.color.colorTransparentDark));
        llInviteBGT.setBackground(shapeBg);

        txtInviteGroomName = (TextView)view.findViewById(R.id.txtInviteGroomName);
        txtInviteGroomName.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorTextLight));

        txtInviteBrideName = (TextView)view.findViewById(R.id.txtInviteBrideName);
        txtInviteBrideName.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorTextLight));

        txtInviteName = (TextView)view.findViewById(R.id.txtInviteName);
        txtInviteName.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorTextLight));

        txtInviteDetails = (TextView)view.findViewById(R.id.txtInviteDetails);
        txtInviteDetails.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorTextLight));

        TextView lableInviteGroom = (TextView)view.findViewById(R.id.lableInviteGroom);
        lableInviteGroom.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorLableText));

        LinearLayout llInviteBorder = (LinearLayout)view.findViewById(R.id.llInviteBorder);
        llInviteBorder.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.colorLableText));

        TextView lableInviteWeds = (TextView)view.findViewById(R.id.lableInviteWeds);
        lableInviteWeds.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorLableText));

        LinearLayout llInviteBo = (LinearLayout)view.findViewById(R.id.llInviteBo);
        llInviteBo.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.colorLableText));

        TextView lableInviteBride = (TextView)view.findViewById(R.id.lableInviteBride);
        lableInviteBride.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorLableText));

        TextView lableHello = (TextView)view.findViewById(R.id.lableHello);
        lableHello.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorLableText));

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
