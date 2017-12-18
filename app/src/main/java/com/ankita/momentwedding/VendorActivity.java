package com.ankita.momentwedding;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
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

public class VendorActivity extends AppCompatActivity {

    ImageView ivVenderLogo,ivCall,ivInternet,ivMail;
    TextView txtVenderDetails;
    String mobile,email,website;
    RatingBar ratingBar;
    Button btnSR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor);

        setTitle("Vendor");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        if(Build.VERSION.SDK_INT >= 21)
        {
            getWindow().setStatusBarColor(Color.parseColor(GetTheme.colorPrimaryDark));
        }

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(GetTheme.colorPrimary)));

        LinearLayout llBgVendorColor = (LinearLayout)findViewById(R.id.llBgVendorColor);
        llBgVendorColor.setBackgroundColor(Color.parseColor(GetTheme.colorBg));

        LinearLayout llBgVendorImg = (LinearLayout)findViewById(R.id.llBgVendorImg);
        GetImageFromServer getImageFromServer = new GetImageFromServer(llBgVendorImg);
        getImageFromServer.execute();

        LinearLayout llBoVendorColor = (LinearLayout)findViewById(R.id.llBoVendorColor);
        llBoVendorColor.setBackgroundColor(Color.parseColor(GetTheme.colorIcon));

        RelativeLayout rlVendorBGT = (RelativeLayout)findViewById(R.id.rlVendorBGT);
        GradientDrawable shapeBg =  new GradientDrawable();
        shapeBg.setCornerRadius(10);
        shapeBg.setColor(Color.parseColor(GetTheme.colorTransparentDark));
        rlVendorBGT.setBackground(shapeBg);

        ivVenderLogo = (ImageView)findViewById(R.id.ivVenderLogo);

        ivCall = (ImageView)findViewById(R.id.ivCall);

        ivCall.setColorFilter(Color.parseColor(GetTheme.colorIcon), PorterDuff.Mode.SRC_IN);

        ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(VendorActivity.this,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(VendorActivity.this,
                            Manifest.permission.CALL_PHONE)) {

                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + mobile));
                        startActivity(callIntent);

                    } else {
                        ActivityCompat.requestPermissions(VendorActivity.this, new String[]{"android.permission.CALL_PHONE",}, 200);
                    }
                } else {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + mobile));
                    startActivity(callIntent);
                }
            }
        });

        ivInternet = (ImageView)findViewById(R.id.ivInternet);
        ivInternet.setColorFilter(Color.parseColor(GetTheme.colorIcon), PorterDuff.Mode.SRC_IN);
        ivInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(website));
                if(!MyStartActivity(i))
                {
                    i.setData(Uri.parse(website));
                    if(!MyStartActivity(i))
                    {
                        Log.d("Like","Could not open browser");
                    }
                }

            }
        });

        ivMail = (ImageView)findViewById(R.id.ivMail);
        ivMail.setColorFilter(Color.parseColor(GetTheme.colorIcon), PorterDuff.Mode.SRC_IN);
        ivMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { email });
                intent.putExtra(Intent.EXTRA_SUBJECT, "");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(intent, ""));
            }
        });

        txtVenderDetails = (TextView)findViewById(R.id.txtVenderDetails);
        txtVenderDetails.setTextColor(Color.parseColor(GetTheme.colorTextLight));

        GetVendorList getVendorList = new GetVendorList();
        getVendorList.execute();

        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        if(Build.VERSION.SDK_INT >= 21)
        {
            ratingBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor(GetTheme.colorPrimary)));
        }


        GetRatingView getRatingView = new GetRatingView();
        getRatingView.execute();

        btnSR = (Button)findViewById(R.id.btnSR);
        btnSR.setTextColor(Color.parseColor(GetTheme.colorTextLight));

        GradientDrawable shapeBtn =  new GradientDrawable();
        shapeBtn.setCornerRadius(10);
        shapeBtn.setColor(Color.parseColor(GetTheme.colorPrimary));
        btnSR.setBackground(shapeBtn);

        btnSR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String rating=String.valueOf(ratingBar.getRating());

                GetRating getRating = new GetRating(rating);
                getRating.execute();

            }
        });

    }

    private boolean MyStartActivity(Intent i) {

        try
        {
            startActivity(i);
            return true;
        }
        catch (ActivityNotFoundException e)
        {
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private class GetVendorList extends AsyncTask<String,Void,String> {

        String status,message,logo,name,description;
        @Override
        protected String doInBackground(String... strings) {

            JSONObject jovl=new JSONObject();
            try {

                jovl.put("wedding_id",HomeActivity.wedding_id);
                Postdata postdata=new Postdata();
                String pdvl=postdata.post(MainActivity.mainUrl+"getVendorDetails",jovl.toString());
                JSONObject j=new JSONObject(pdvl);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
                    JSONObject jo=j.getJSONObject("vendorDetail");

                    logo =jo.getString("logo");
                    name =jo.getString("name");
                    mobile =jo.getString("mobile");
                    email =jo.getString("email");
                    website =jo.getString("website");
                    description =jo.getString("description");
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
            if(status.equals("1"))
            {
                DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
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

                imageLoader.displayImage(logo, ivVenderLogo, options);

                txtVenderDetails.setText(description);
            }
            else
            {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GetRating extends AsyncTask<String,Void,String> {

        String rating,status,message;

        public GetRating(String rating) {
            this.rating = rating;
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joRat=new JSONObject();
            try {

                joRat.put("guest_id",HomeActivity.guest_id);
                joRat.put("rating",rating);
                Postdata postdata=new Postdata();
                String pdRat=postdata.post(MainActivity.mainUrl+"vendorRatingUpdate",joRat.toString());
                JSONObject j=new JSONObject(pdRat);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
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
            if(status.equals("1"))
            {
                AlertDialog.Builder alert = new AlertDialog.Builder(VendorActivity.this);
                alert.setMessage("Thank You!");
                alert.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(VendorActivity.this,HomeActivity.class);
                                startActivity(intent);
                                VendorActivity.this.finish();
                            }
                        });
                alert.show();
            }
            else
            {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GetRatingView extends AsyncTask<String,Void,String> {

        String status,message,vendor_rating;

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joRatView = new JSONObject();
            try {

                joRatView.put("guest_id",HomeActivity.guest_id);
                Postdata postdata=new Postdata();
                String pdRat=postdata.post(MainActivity.mainUrl+"guestDetails",joRatView.toString());
                JSONObject j=new JSONObject(pdRat);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
                    JSONObject jo=j.getJSONObject("guest");

                    vendor_rating =jo.getString("vendor_rating");
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
            if(status.equals("1"))
            {
                ratingBar.setRating(Float.parseFloat(vendor_rating));
            }
            else
            {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
