package com.ankita.momentwedding;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class RSVPActivity extends AppCompatActivity {

    TabLayout tabRsvp;
    ViewPager vpRsvp;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsvp);

        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        getWindow().setStatusBarColor(Color.parseColor(GetTheme.colorPrimaryDark));

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(GetTheme.colorPrimary)));

        LinearLayout llBGRsvpColor = (LinearLayout)findViewById(R.id.llBGRsvpColor);
        llBGRsvpColor.setBackgroundColor(Color.parseColor(GetTheme.colorBg));

        LinearLayout llBgImgRsvp = (LinearLayout)findViewById(R.id.llBgImgRsvp);
        llBgImgRsvp.setBackground(ContextCompat.getDrawable(RSVPActivity.this,R.drawable.imgi));

        LinearLayout llBGTRSVPTransparent = (LinearLayout)findViewById(R.id.llBGTRSVPTransparent);
        GradientDrawable shapeBg =  new GradientDrawable();
        shapeBg.setColor(Color.parseColor(GetTheme.colorTransparentLight));
        llBGTRSVPTransparent.setBackground(shapeBg);

        TextView tvAreComing = (TextView)findViewById(R.id.tvAreComing);
        tvAreComing.setTextColor(Color.parseColor(GetTheme.colorTextDark));

        vpRsvp = (ViewPager)findViewById(R.id.vpRsvp);
        setupViewPager(vpRsvp);

        tabRsvp =(TabLayout)findViewById(R.id.tabRsvp);
        tabRsvp.setSelectedTabIndicatorColor(Color.parseColor(GetTheme.colorTextLight));
        tabRsvp.setTabTextColors(Color.parseColor(GetTheme.colorTextSelect), Color.parseColor(GetTheme.colorTextLight));

        tabRsvp.setBackgroundColor(Color.parseColor(GetTheme.colorPrimary));

        tabRsvp.setupWithViewPager(vpRsvp);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {

        PagerRsvp adapter = new PagerRsvp(getSupportFragmentManager());

        adapter.addFrag(new RsvpYesFragment(),"Yes");
        adapter.addFrag(new RsvpNoFragment(),"No");

        viewPager.setAdapter(adapter);
    }

}
