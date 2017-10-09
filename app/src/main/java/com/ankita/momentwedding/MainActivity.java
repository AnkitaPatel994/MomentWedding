package com.ankita.momentwedding;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    public static String mainUrl = "http://momentsunlimited.in/weddingWebservices/webservices/";

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

        lnSnackbar = (LinearLayout)findViewById(R.id.lnSnackbar);
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
}
