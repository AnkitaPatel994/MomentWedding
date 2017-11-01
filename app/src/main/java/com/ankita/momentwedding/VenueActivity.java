package com.ankita.momentwedding;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class VenueActivity extends AppCompatActivity {

    TextView txtVenueEventName, txtVenueEventDateTime, txtVenueEventAddress,txtVenueTitle;
    RelativeLayout rlCall, rlInternet, rlMail;
    ImageView ivVenueImg;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue);
        setTitle("Venue");

        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        LinearLayout llBGTVenue = (LinearLayout)findViewById(R.id.llBGTVenue);
        GradientDrawable shapeBg =  new GradientDrawable();
        shapeBg.setColor(ContextCompat.getColor(VenueActivity.this,R.color.colorTransparentDark));
        llBGTVenue.setBackground(shapeBg);

        LinearLayout llBGTVenueBorder = (LinearLayout)findViewById(R.id.llBGTVenueBorder);
        llBGTVenueBorder.setBackgroundColor(ContextCompat.getColor(VenueActivity.this,R.color.colorPrimary));

        String VenueEventName = getIntent().getExtras().getString("name");

        GradientDrawable shapeRoundBg =  new GradientDrawable();
        shapeRoundBg.setShape(GradientDrawable.OVAL);
        shapeRoundBg.setSize(120,120);
        shapeRoundBg.setStroke(1,ContextCompat.getColor(VenueActivity.this,R.color.colorLableText));
        shapeRoundBg.setCornerRadius(10);

        ImageView ivEarthRound = (ImageView)findViewById(R.id.ivEarthRound);
        ivEarthRound.setBackground(shapeRoundBg);

        ImageView ivPhoneRound = (ImageView)findViewById(R.id.ivPhoneRound);
        ivPhoneRound.setBackground(shapeRoundBg);

        ImageView ivEmailRound = (ImageView)findViewById(R.id.ivEmailRound);
        ivEmailRound.setBackground(shapeRoundBg);

        txtVenueEventName = (TextView) findViewById(R.id.txtVenueEventName);
        txtVenueEventName.setTextColor(ContextCompat.getColor(VenueActivity.this,R.color.colorTextTitle));
        txtVenueEventName.setText(VenueEventName);

        String VenueEventTime = getIntent().getExtras().getString("time");
        String VenueEventDate = getIntent().getExtras().getString("date");
        txtVenueEventDateTime = (TextView) findViewById(R.id.txtVenueEventDateTime);
        txtVenueEventDateTime.setTextColor(ContextCompat.getColor(VenueActivity.this,R.color.colorTextLight));
        txtVenueEventDateTime.setText(VenueEventTime + ", " + VenueEventDate);

        String VenueEventAddress = getIntent().getExtras().getString("location");
        txtVenueEventAddress = (TextView) findViewById(R.id.txtVenueEventAddress);
        txtVenueEventAddress.setTextColor(ContextCompat.getColor(VenueActivity.this,R.color.colorTextLight));
        txtVenueEventAddress.setText(VenueEventAddress);

        String VenueEventImage = getIntent().getExtras().getString("image");
        ivVenueImg = (ImageView) findViewById(R.id.ivVenueImg);

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
        imageLoader.displayImage(VenueEventImage, ivVenueImg, options);

        txtVenueTitle = (TextView) findViewById(R.id.txtVenueTitle);
        String venueName = getIntent().getExtras().getString("venueName");
        txtVenueTitle.setText(venueName);

        webView = (WebView) findViewById(R.id.webView);

        String mapURL = getIntent().getExtras().getString("mapURL");

        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(mapURL);

        rlCall = (RelativeLayout) findViewById(R.id.rlCall);
        final String venuePhone = getIntent().getExtras().getString("venuePhone");
        rlCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(VenueActivity.this,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(VenueActivity.this,
                            Manifest.permission.CALL_PHONE)) {

                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + venuePhone));
                        startActivity(callIntent);

                    } else {
                        ActivityCompat.requestPermissions(VenueActivity.this, new String[]{"android.permission.CALL_PHONE",}, 200);
                    }
                } else {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + venuePhone));
                    startActivity(callIntent);
                }
            }
        });

        rlInternet = (RelativeLayout)findViewById(R.id.rlInternet);
        final String venueWeb = getIntent().getExtras().getString("venueWeb");
        rlInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(venueWeb));
                if(!MyStartActivity(i))
                {
                    i.setData(Uri.parse(venueWeb));
                    if(!MyStartActivity(i))
                    {
                        Log.d("Like","Could not open browser");
                    }
                }

            }
        });

        rlMail = (RelativeLayout)findViewById(R.id.rlMail);
        final String venueEmail = getIntent().getExtras().getString("venueEmail");
        rlMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { venueEmail });
                intent.putExtra(Intent.EXTRA_SUBJECT, "");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(intent, ""));
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
}
