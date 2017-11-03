package com.ankita.momentwedding;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class GuestNameActivity extends AppCompatActivity {

    EditText txtGuestName;
    Button btnGuestSubmit;
    String weddingId,mobileNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_name);
        getSupportActionBar().hide();

        txtGuestName = (EditText)findViewById(R.id.txtGuestName);
        btnGuestSubmit = (Button)findViewById(R.id.btnGuestSubmit);

        weddingId = getIntent().getExtras().getString("weddingId");
        mobileNo = getIntent().getExtras().getString("mobileNo");
        final String guest_id = getIntent().getExtras().getString("guest_id");

        GradientDrawable shapeBg =  new GradientDrawable();
        shapeBg.setStroke(3, ContextCompat.getColor(GuestNameActivity.this,R.color.colorYellow));
        shapeBg.setCornerRadius(5);
        shapeBg.setColor(ContextCompat.getColor(GuestNameActivity.this,R.color.colorPrimary));
        txtGuestName.setBackground(shapeBg);

        btnGuestSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(txtGuestName.getText().toString().equals(""))
                {
                    Toast.makeText(GuestNameActivity.this,"Please Not Empty!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String GuestName = txtGuestName.getText().toString();

                    GetGuestName getGuestName = new GetGuestName(guest_id,GuestName);
                    getGuestName.execute();
                }
            }
        });
    }

    private class GetGuestName extends AsyncTask<String,Void,String> {

        String guest_id,guestName,message,status;
        ProgressDialog dialog;

        public GetGuestName(String guest_id, String guestName) {

            this.guest_id = guest_id;
            this.guestName = guestName;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(GuestNameActivity.this);
            dialog.setMessage("Loading...");
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joGName=new JSONObject();

            try {

                joGName.put("guest_id",guest_id);
                joGName.put("guest_name",guestName);
                Postdata postdata=new Postdata();
                String pdGN=postdata.post(MainActivity.mainUrl+"updateGuestName",joGName.toString());
                JSONObject j=new JSONObject(pdGN);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");

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
            dialog.dismiss();
            if(status.equals("1"))
            {
                Intent i = new Intent(getApplicationContext(),GuestInviteIdActivity.class);
                i.putExtra("guest_id",guest_id);
                i.putExtra("weddingId",weddingId);
                i.putExtra("mobileNo",mobileNo);
                startActivity(i);
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
