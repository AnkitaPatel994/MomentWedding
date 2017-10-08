package com.ankita.momentwedding;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    public static String mainUrl = "http://momentsunlimited.in/weddingWebservices/webservices/";

    LinearLayout lnSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lnSnackbar = (LinearLayout)findViewById(R.id.lnSnackbar);
        Thread background = new Thread()
        {
            public void run()
            {
                try {
                    sleep(3*1000);
                    /*Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(i);
                    finish();*/

                    Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(i);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        background.start();
    }
}
