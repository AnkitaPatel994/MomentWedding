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
import android.widget.LinearLayout;
import android.widget.TextView;

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

        LinearLayout llBGRsvpColor = (LinearLayout)findViewById(R.id.llBGRsvpColor);
        llBGRsvpColor.setBackgroundColor(ContextCompat.getColor(RSVPActivity.this,R.color.colorBg));

        LinearLayout llBgImgRsvp = (LinearLayout)findViewById(R.id.llBgImgRsvp);

        LinearLayout llBGTRSVPTransparent = (LinearLayout)findViewById(R.id.llBGTRSVPTransparent);
        GradientDrawable shapeBg =  new GradientDrawable();
        shapeBg.setColor(ContextCompat.getColor(RSVPActivity.this,R.color.colorTransparentLight));
        llBGTRSVPTransparent.setBackground(shapeBg);

        TextView tvAreComing = (TextView)findViewById(R.id.tvAreComing);
        tvAreComing.setTextColor(ContextCompat.getColor(RSVPActivity.this,R.color.colorTextDark));

        vpRsvp = (ViewPager)findViewById(R.id.vpRsvp);
        setupViewPager(vpRsvp);

        tabRsvp =(TabLayout)findViewById(R.id.tabRsvp);
        //tabRsvp.setSelectedTabIndicatorColor(ContextCompat.getColor(RSVPActivity.this,R.color.colorTextLight));
        //tabRsvp.setTabTextColors(Color.parseColor(GetTheme.colorTextDark), Color.parseColor(GetTheme.colorTextLight));

        //tabRsvp.setBackgroundColor(Color.parseColor(GetTheme.primaryColor));

        /*StateListDrawable states = new StateListDrawable();

        states.addState(new int[] {android.R.attr.state_selected},
                new ColorDrawable(getResources().getColor(R.color.colorGray)));

        states.addState(new int[] {},
                new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        tabRsvp.setBackgroundDrawable(states);*/

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
