package com.ankita.momentwedding;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ProfileFragment extends Fragment {

    View view;
    TextView txtProfileName,txtProfileOccupation,txtProfileDetails;
    CircleImageView ivProfilePic;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();

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
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        txtProfileName = (TextView)view.findViewById(R.id.txtProfileName);
        txtProfileOccupation = (TextView)view.findViewById(R.id.txtProfileOccupation);
        txtProfileDetails = (TextView)view.findViewById(R.id.txtProfileDetails);

        ivProfilePic = (CircleImageView)view.findViewById(R.id.ivProfilePic);

        GetProfileList getProfileList = new GetProfileList();
        getProfileList.execute();

        return view;
    }

    private class GetProfileList extends AsyncTask<String,Void,String> {

        String status,message,profile_pic,name,occupation,profile_details;

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joProfile=new JSONObject();
            try {

                joProfile.put("profile_id",HomeActivity.profile_id);
                Postdata postdata=new Postdata();
                String pdPro=postdata.post(MainActivity.mainUrl+"getProfile",joProfile.toString());
                JSONObject j=new JSONObject(pdPro);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");

                    JSONObject jo=j.getJSONObject("profile_row");

                    profile_pic =jo.getString("profile_pic");
                    name =jo.getString("name");
                    occupation =jo.getString("occupation");
                    profile_details =jo.getString("profile_details");
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
                txtProfileName.setText(name);
                txtProfileOccupation.setText(occupation);
                txtProfileDetails.setText(profile_details);

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
                imageLoader.displayImage(profile_pic,ivProfilePic, options);
            }
            else
            {
                Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
