package com.ankita.momentwedding;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import de.hdodenhof.circleimageview.CircleImageView;

public class OneProfileActivity extends AppCompatActivity {

    TextView txtProfileName,txtProfileOccupation,txtProfileDetails;
    CircleImageView ivProfilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_profile);

        if(Build.VERSION.SDK_INT >= 21)
        {
            getWindow().setStatusBarColor(Color.parseColor(GetTheme.colorPrimaryDark));
        }
        String GB = getIntent().getExtras().getString("GB");
        setTitle(GB);

        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(GetTheme.colorPrimary)));

        LinearLayout llBgOPColor = (LinearLayout)findViewById(R.id.llBgOPColor);
        llBgOPColor.setBackgroundColor(Color.parseColor(GetTheme.colorBg));

        LinearLayout llBgOPImg = (LinearLayout)findViewById(R.id.llBgOPImg);
        GetImageFromServer getImageFromServer = new GetImageFromServer(llBgOPImg);
        getImageFromServer.execute();

        LinearLayout llBgTransparent = (LinearLayout)findViewById(R.id.llBgTransparent);
        GradientDrawable shapeBg =  new GradientDrawable();
        shapeBg.setCornerRadius(10);
        shapeBg.setColor(Color.parseColor(GetTheme.colorTransparentDark));
        llBgTransparent.setBackground(shapeBg);

        txtProfileName = (TextView)findViewById(R.id.txtProfileName);
        txtProfileName.setTextColor(Color.parseColor(GetTheme.colorTextLight));

        txtProfileOccupation = (TextView)findViewById(R.id.txtProfileOccupation);
        txtProfileOccupation.setTextColor(Color.parseColor(GetTheme.colorTextLight));

        txtProfileDetails = (TextView)findViewById(R.id.txtProfileDetails);
        txtProfileDetails.setTextColor(Color.parseColor(GetTheme.colorTextLight));

        ivProfilePic = (CircleImageView)findViewById(R.id.ivProfilePic);
        ivProfilePic.setBorderColor(Color.parseColor(GetTheme.colorPrimaryDark));

        String profileId = getIntent().getExtras().getString("profile_id");

        GetProfileList getProfileList = new GetProfileList(profileId);
        getProfileList.execute();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private class GetProfileList extends AsyncTask<String,Void,String> {

        String status,message,profile_pic,name,occupation,profile_details,profileId;

        public GetProfileList(String profileId) {
            this.profileId = profileId;
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joProfile=new JSONObject();
            try {

                joProfile.put("profile_id",profileId);
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

                /*DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                        .cacheOnDisc(true).cacheInMemory(true)
                        .imageScaleType(ImageScaleType.EXACTLY)
                        .displayer(new FadeInBitmapDisplayer(300)).build();
                final ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
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
                imageLoader.displayImage(profile_pic,ivProfilePic, options);*/

                Picasso.with(getApplicationContext()).load(profile_pic).into(ivProfilePic);
            }
            else
            {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
