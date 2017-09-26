package com.ankita.momentwedding;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    ViewPager vpAdminEvent;
    TabLayout tabAdminEventLayout;

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
