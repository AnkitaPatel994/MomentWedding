package com.ankita.momentwedding;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class RSVPActivity extends AppCompatActivity {

    TabLayout tabRsvp;
    ViewPager vpRsvp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsvp);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        vpRsvp = (ViewPager)findViewById(R.id.vpRsvp);
        setupViewPager(vpRsvp);

        tabRsvp =(TabLayout)findViewById(R.id.tabRsvp);
        tabRsvp.setupWithViewPager(vpRsvp);

    }

    private void setupViewPager(ViewPager viewPager) {

        PagerRsvp adapter = new PagerRsvp(getSupportFragmentManager());

        adapter.addFrag(new RsvpYesFragment(),"Yes");
        adapter.addFrag(new RsvpNoFragment(),"No");

        viewPager.setAdapter(adapter);
    }

}
