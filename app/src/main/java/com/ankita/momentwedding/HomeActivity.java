package com.ankita.momentwedding;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class HomeActivity extends AppCompatActivity {

    ViewPager vpAdminEvent;
    TabLayout tabAdminEventLayout;
    LinearLayout llClock;
    ImageView ivClock;

    public static int user_code = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        vpAdminEvent = (ViewPager)findViewById(R.id.vpAdminEvent);
        setupViewPager(vpAdminEvent);

        tabAdminEventLayout =(TabLayout)findViewById(R.id.tabAdminEventLayout);
        tabAdminEventLayout.setupWithViewPager(vpAdminEvent);

        tabAdminEventLayout.getTabAt(0).setIcon(R.drawable.ic_profile_black_24dp);
        tabAdminEventLayout.getTabAt(1).setIcon(R.drawable.schedule);
        tabAdminEventLayout.getTabAt(2).setIcon(R.drawable.gallery);
        tabAdminEventLayout.getTabAt(3).setIcon(R.drawable.invite);
        tabAdminEventLayout.getTabAt(4).setIcon(R.drawable.members);

        /*---------------- Clock ------------------*/
        llClock = (LinearLayout)findViewById(R.id.llClock);
        ivClock =(ImageView)findViewById(R.id.ivClock);

        ivClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(HomeActivity.this,android.R.style.Theme_Light_NoTitleBar);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.clock_dialog);
                dialog.setCancelable(true);
                final TextView txtDate =(TextView)dialog.findViewById(R.id.txtDate);
                final TextView txtDays =(TextView)dialog.findViewById(R.id.txtDays);
                final TextView txtHours =(TextView)dialog.findViewById(R.id.txtHours);
                final TextView txtMins =(TextView)dialog.findViewById(R.id.txtMins);
                final TextView txtSecs =(TextView)dialog.findViewById(R.id.txtSecs);
                dialog.show();

                txtDate.setText("8 October 2017");
                int d = 1000*60*30;
                CountDownTimer timer = new CountDownTimer(d, 1000) {
                    @Override
                    public void onTick(long l) {

                        txtDays.setText(""+String.format("%d",
                                TimeUnit.MILLISECONDS.toDays(l)));

                        txtHours.setText(""+String.format("%d",
                                TimeUnit.MILLISECONDS.toHours(l)));

                        txtMins.setText(""+String.format("%d",
                                TimeUnit.MILLISECONDS.toMinutes(l)));

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
        });
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
}
