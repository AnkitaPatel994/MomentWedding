package com.ankita.momentwedding;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ViewPager vpAdminEvent;
    TabLayout tabAdminEventLayout;
    ImageView ivClock;
    TextView txtRsvp,txtDate,txtDays,txtHours,txtMins,txtSecs,txtGuestName;
    SessionManager session;
    Dialog dialog;
    CircleImageView ivGuestPic;
    LinearLayout llDialogOther,llDialog;

    public static String wedding_id;
    public static String profile_id;
    public static String guest_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);

        /*---------------- Navigation Header ------------------*/
        txtGuestName = (TextView)header.findViewById(R.id.txtGuestName);
        ivGuestPic = (CircleImageView)header.findViewById(R.id.ivGuestPic);

        GetGuestListone getGuestListone = new GetGuestListone();
        getGuestListone.execute();

        setTitle("Profile");

        session = new SessionManager(getApplicationContext());

        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();

        guest_id = user.get(SessionManager.guest_id);
        wedding_id = user.get(SessionManager.wedding_id);
        profile_id = user.get(SessionManager.profile_id);

        vpAdminEvent = (ViewPager)findViewById(R.id.vpAdminEvent);
        setupViewPager(vpAdminEvent);

        vpAdminEvent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if(position == 0)
                {
                    setTitle("Profile");
                }
                else if(position == 1)
                {
                    setTitle("Functions");
                    navigationView.getMenu().getItem(2).setChecked(true);
                }
                else if(position == 2)
                {
                    setTitle("Gallery");
                    navigationView.getMenu().getItem(3).setChecked(true);
                }
                else if(position == 3)
                {
                    setTitle("Invitation");
                    navigationView.getMenu().getItem(4).setChecked(true);
                }
                else if(position == 4)
                {
                    setTitle("Family Member");
                    navigationView.getMenu().getItem(5).setChecked(true);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabAdminEventLayout =(TabLayout)findViewById(R.id.tabAdminEventLayout);
        tabAdminEventLayout.setupWithViewPager(vpAdminEvent);

        tabAdminEventLayout.getTabAt(0).setIcon(R.drawable.ic_profile_black_24dp);
        tabAdminEventLayout.getTabAt(1).setIcon(R.drawable.schedule);
        tabAdminEventLayout.getTabAt(2).setIcon(R.drawable.gallery);
        tabAdminEventLayout.getTabAt(3).setIcon(R.drawable.invite);
        tabAdminEventLayout.getTabAt(4).setIcon(R.drawable.members);

        /*---------------- RSVP ------------------*/
        txtRsvp =(TextView) findViewById(R.id.txtRsvp);
        txtRsvp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),RSVPActivity.class);
                startActivity(i);
            }
        });

        /*---------------- Clock ------------------*/
        ivClock =(ImageView)findViewById(R.id.ivClock);

        ivClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog = new Dialog(HomeActivity.this,android.R.style.Theme_Light_NoTitleBar);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.clock_dialog);
                dialog.setCancelable(true);
                txtDate =(TextView)dialog.findViewById(R.id.txtDate);
                txtDays =(TextView)dialog.findViewById(R.id.txtDays);
                txtHours =(TextView)dialog.findViewById(R.id.txtHours);
                txtMins =(TextView)dialog.findViewById(R.id.txtMins);
                txtSecs =(TextView)dialog.findViewById(R.id.txtSecs);

                llDialogOther = (LinearLayout)dialog.findViewById(R.id.llDialogOther);
                llDialog = (LinearLayout)dialog.findViewById(R.id.llDialog);

                llDialogOther.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                llDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.show();
                    }
                });

                dialog.show();

                GetCountDown getCountDown = new  GetCountDown();
                getCountDown.execute();


            }
        });



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Groom_profile)
        {
            Intent intent = new Intent(getApplicationContext(),OneProfileActivity.class);
            intent.putExtra("profile_id",ProfileFragment.groomId);
            intent.putExtra("GB","Groom");
            startActivity(intent);
        }
        else if (id == R.id.nav_Bride_profile)
        {
            Intent intent = new Intent(getApplicationContext(),OneProfileActivity.class);
            intent.putExtra("profile_id",ProfileFragment.brideId);
            intent.putExtra("GB","Bride");
            startActivity(intent);
        }
        else if (id == R.id.nav_functions)
        {
            vpAdminEvent.setCurrentItem(1);
        }
        else if (id == R.id.nav_gallery)
        {
            vpAdminEvent.setCurrentItem(2);
        }
        else if (id == R.id.nav_invitation)
        {
            vpAdminEvent.setCurrentItem(3);
        }
        else if (id == R.id.nav_fm)
        {
            vpAdminEvent.setCurrentItem(4);
        }
        else if (id == R.id.nav_vendor)
        {
            Intent intent = new Intent(getApplicationContext(),VendorActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_share)
        {
            Intent i=new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            String body="https://play.google.com/store/apps/details?id=com.ankita.momentwedding";
            i.putExtra(Intent.EXTRA_SUBJECT,body);
            i.putExtra(Intent.EXTRA_TEXT,body);
            startActivity(Intent.createChooser(i,"Share using"));
        }
        /*else if (id == R.id.nav_rate)
        {
            Intent i=new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.ankita.momentwedding"));
            if(!MyStartActivity(i))
            {
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.ankita.momentwedding"));
                if(!MyStartActivity(i))
                {
                    Log.d("Like","Could not open browser");
                }
            }
        }*/
        else if (id == R.id.nav_logout)
        {
            session.logoutUser();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

    private void setupViewPager(ViewPager viewPager) {
        Pager adapter = new Pager(getSupportFragmentManager());

        adapter.addFrag(new ProfileFragment());
        adapter.addFrag(new ScheduleFragment());
        adapter.addFrag(new GalleryFragment());
        adapter.addFrag(new InviteFragment());
        adapter.addFrag(new MembarFragment());

        viewPager.setAdapter(adapter);
    }

    private class GetCountDown extends AsyncTask<String,Void,String> {

        String status,message,wedding_date;
        long countdown;

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joCount=new JSONObject();
            try {

                joCount.put("wedding_id",HomeActivity.wedding_id);
                Postdata postdata=new Postdata();
                String pdPro=postdata.post(MainActivity.mainUrl+"weddingCountDown",joCount.toString());
                JSONObject j=new JSONObject(pdPro);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
                    countdown=j.getLong("countdown");
                    wedding_date=j.getString("wedding_date");

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            txtDate.setText(wedding_date);

            CountDownTimer timer = new CountDownTimer(countdown, 1000) {
                @Override
                public void onTick(long l) {

                   long count = l;

                    txtDays.setText(""+String.format("%d",
                            TimeUnit.MILLISECONDS.toDays(l)));

                    count = count-(Long.parseLong(txtDays.getText().toString())*1000*60*60*24);

                    txtHours.setText(""+String.format("%d",
                            TimeUnit.MILLISECONDS.toHours(count)));

                    count = count-(Long.parseLong(txtHours.getText().toString())*1000*60*60);

                    txtMins.setText(""+String.format("%d",
                            TimeUnit.MILLISECONDS.toMinutes(count)));

                    txtSecs.setText(""+String.format("%d",
                            TimeUnit.MILLISECONDS.toSeconds(l) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                            toMinutes(l))));
                }

                @Override
                public void onFinish() {
                    ivClock.setVisibility(View.GONE);
                    dialog.dismiss();
                }
            };
            timer.start();
        }
    }

    private class GetGuestListone extends AsyncTask<String,Void,String> {
        String status,message,name,image;
        @Override
        protected String doInBackground(String... strings) {

            JSONObject jogl=new JSONObject();
            try {

                jogl.put("guest_id",HomeActivity.guest_id);
                Postdata postdata=new Postdata();
                String pdgl=postdata.post(MainActivity.mainUrl+"guestDetails",jogl.toString());
                JSONObject j=new JSONObject(pdgl);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
                    JSONObject jo=j.getJSONObject("guest");

                    name =jo.getString("name");
                    image =jo.getString("image");
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
                txtGuestName.setText(name);

                DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                        .cacheOnDisc(true).cacheInMemory(true)
                        .imageScaleType(ImageScaleType.EXACTLY)
                        .displayer(new FadeInBitmapDisplayer(300)).build();
                final ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(HomeActivity.this)
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


                imageLoader.displayImage(image,ivGuestPic, options);


            }
            else
            {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
