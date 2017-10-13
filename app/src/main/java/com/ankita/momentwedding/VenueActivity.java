package com.ankita.momentwedding;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class VenueActivity extends AppCompatActivity {

    TextView txtVenueEventName,txtVenueEventDateTime,txtVenueEventAddress,txtVenueTitle;
    RelativeLayout rlCall,rlInternet,rlMail;

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

        String VenueEventName = getIntent().getExtras().getString("name");
        txtVenueEventName = (TextView)findViewById(R.id.txtVenueEventName);
        txtVenueEventName.setText(VenueEventName);

        String VenueEventTime = getIntent().getExtras().getString("time");
        String VenueEventDate = getIntent().getExtras().getString("date");
        txtVenueEventDateTime = (TextView)findViewById(R.id.txtVenueEventDateTime);
        txtVenueEventDateTime.setText(VenueEventTime+", "+VenueEventDate);

        txtVenueEventAddress = (TextView)findViewById(R.id.txtVenueEventAddress);
        txtVenueTitle = (TextView)findViewById(R.id.txtVenueTitle);

        rlCall = (RelativeLayout)findViewById(R.id.rlCall);
        rlInternet = (RelativeLayout)findViewById(R.id.rlInternet);
        rlMail = (RelativeLayout)findViewById(R.id.rlMail);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
