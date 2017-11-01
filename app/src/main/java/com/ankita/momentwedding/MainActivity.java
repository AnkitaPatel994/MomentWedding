package com.ankita.momentwedding;

import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    /*public static String mainUrl = "http://momentsunlimited.in/weddingWebservices/webservices/";*/
    public static String mainUrl = "http://wedding.momentsunlimited.in/webservices/";

    LinearLayout lnSnackbar;
    SessionManager session;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        session = new SessionManager(getApplicationContext());
        flag = session.checkLogin();

        /*HashMap<String, String> user = session.getUserDetails();

        String guest_id = user.get(SessionManager.guest_id);
        String wedding_id = user.get(SessionManager.wedding_id);

        GetTheme getTheme = new GetTheme(guest_id,wedding_id);
        getTheme.execute();*/

        lnSnackbar = (LinearLayout)findViewById(R.id.lnSnackbar);

        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected())
        {
            Thread background = new Thread()
            {
                public void run()
                {
                    try {
                        sleep(3*1000);

                        if (flag == 1)
                        {
                            Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                            startActivity(i);
                            finish();
                        }
                        else
                        {
                            Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(i);
                            finish();
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            background.start();
        }
        else
        {
            Snackbar.make(lnSnackbar, "No Connection", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        }
    }
}
