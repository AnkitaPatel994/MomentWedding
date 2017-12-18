package com.ankita.momentwedding;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class GuestInviteIdActivity extends AppCompatActivity {

    LinearLayout llGroom,llBride;
    CircleImageView ivGroomPic,ivBridePic;
    TextView txtGroomName,txtBrideName;
    String weddingId,mobileNo,guest_id,groomId,brideId;
    SessionManager session;
    LinearLayout llBgGuestInviteId,llBoGuestInviteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_invite_id);
        getSupportActionBar().hide();

        session = new SessionManager(getApplicationContext());

        llGroom = (LinearLayout)findViewById(R.id.llGroom);
        llBride = (LinearLayout)findViewById(R.id.llBride);

        ivGroomPic = (CircleImageView)findViewById(R.id.ivGroomPic);

        ivBridePic = (CircleImageView)findViewById(R.id.ivBridePic);

        txtGroomName = (TextView)findViewById(R.id.txtGroomName);

        txtBrideName = (TextView)findViewById(R.id.txtBrideName);

        llBgGuestInviteId = (LinearLayout)findViewById(R.id.llBgGuestInviteId);

        llBoGuestInviteId = (LinearLayout)findViewById(R.id.llBoGuestInviteId);

        weddingId = getIntent().getExtras().getString("weddingId");
        mobileNo = getIntent().getExtras().getString("mobileNo");
        guest_id = getIntent().getExtras().getString("guest_id");

        GetTheme getTheme = new GetTheme(guest_id,weddingId);
        getTheme.execute();

        GetJodiList getJodiList = new GetJodiList(weddingId);
        getJodiList.execute();

        llGroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ProfileId = groomId;
                GetGroomBride getGroomBride = new GetGroomBride(ProfileId);
                getGroomBride.execute();
            }
        });

        llBride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ProfileId = brideId;
                GetGroomBride getGroomBride = new GetGroomBride(ProfileId);
                getGroomBride.execute();
            }
        });

    }

    private class GetJodiList extends AsyncTask<String,Void,String> {

        String weddingId,status,message,groomProfilePic,groomName,brideProfilePic,brideName;
        ProgressDialog dialog;

        public GetJodiList(String weddingId) {
            this.weddingId = weddingId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(GuestInviteIdActivity.this);
            dialog.setMessage("Loading...");
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject jojl=new JSONObject();
            try {

                jojl.put("wedding_id",weddingId);
                Postdata postdata=new Postdata();
                String pdJl=postdata.post(MainActivity.mainUrl+"getWeddingProfile",jojl.toString());
                JSONObject j=new JSONObject(pdJl);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
                    JSONObject jo=j.getJSONObject("profiles");

                    JSONObject jog=jo.getJSONObject("groomData");

                    groomId =jog.getString("id");
                    groomProfilePic =jog.getString("profile_pic");
                    groomName =jog.getString("name");

                    JSONObject job=jo.getJSONObject("brideData");

                    brideId =job.getString("id");
                    brideProfilePic =job.getString("profile_pic");
                    brideName =job.getString("name");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            if(status.equals("1"))
            {
                DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                        .cacheOnDisc(true).cacheInMemory(true)
                        .imageScaleType(ImageScaleType.EXACTLY)
                        .displayer(new FadeInBitmapDisplayer(300)).build();
                final ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(GuestInviteIdActivity.this)
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


                imageLoader.displayImage(groomProfilePic,ivGroomPic, options);
                imageLoader.displayImage(brideProfilePic,ivBridePic, options);
                txtGroomName.setText(groomName);
                txtBrideName.setText(brideName);
            }
            else
            {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GetGroomBride extends AsyncTask<String,Void,String> {

        ProgressDialog dialog;
        String status,message,profileId,guestName;

        public GetGroomBride(String profileId) {
            this.profileId = profileId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(GuestInviteIdActivity.this);
            dialog.setMessage("Loading...");
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject jogb=new JSONObject();
            try {

                jogb.put("guest_id",guest_id);
                jogb.put("profile_id",profileId);
                Postdata postdata=new Postdata();
                String pdgb=postdata.post(MainActivity.mainUrl+"updateGuestProfile",jogb.toString());
                JSONObject j=new JSONObject(pdgb);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
                    JSONObject jo=j.getJSONObject("guest_row");
                    guestName =jo.getString("name");
                }
                else
                {
                    message=j.getString("message");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            if(status.equals("1"))
            {
                session.createLoginSession(guest_id,weddingId,profileId,mobileNo);

                Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(i);
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
