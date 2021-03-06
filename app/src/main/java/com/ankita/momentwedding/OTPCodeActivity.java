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

public class OTPCodeActivity extends AppCompatActivity {

    EditText txtOtpCode;
    Button btnSubmit;
    TextView txtResendSms;
    SessionManager session;
    String statusCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_code);
        getSupportActionBar().hide();

        session = new SessionManager(getApplicationContext());

        txtOtpCode = (EditText) findViewById(R.id.txtOtpCode);
        txtResendSms = (TextView)findViewById(R.id.txtResendSms);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        statusCheck = getIntent().getExtras().getString("status");

        final String weddingId = getIntent().getExtras().getString("wedding_id");
        final String mobileNo = getIntent().getExtras().getString("mobileNo");

        GradientDrawable shapeBg =  new GradientDrawable();
        shapeBg.setStroke(3,ContextCompat.getColor(OTPCodeActivity.this,R.color.colorYellow));
        shapeBg.setCornerRadius(5);
        shapeBg.setColor(ContextCompat.getColor(OTPCodeActivity.this,R.color.colorPrimary));
        txtOtpCode.setBackground(shapeBg);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(txtOtpCode.getText().toString().equals(""))
                {
                    Toast.makeText(OTPCodeActivity.this,"Please Not Empty!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String OtpCode = txtOtpCode.getText().toString();

                    GetOtpCode getOtpCode = new GetOtpCode(OtpCode,weddingId,mobileNo);
                    getOtpCode.execute();
                }
            }
        });

        txtResendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MobileNoActivity.class);
                i.putExtra("wedding_id",weddingId);
                startActivity(i);
                finish();
            }
        });
    }

    private class GetOtpCode extends AsyncTask<String,Void,String> {

        String otpCode,weddingId,mobileNo,status,message,id,profileId;
        ProgressDialog dialog;

        public GetOtpCode(String otpCode, String weddingId, String mobileNo) {
            this.otpCode = otpCode;
            this.weddingId = weddingId;
            this.mobileNo = mobileNo;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(OTPCodeActivity.this);
            dialog.setMessage("Loading...");
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joOtp=new JSONObject();

            try {

                joOtp.put("wedding_id",weddingId);
                joOtp.put("mobile",mobileNo);
                joOtp.put("otp",otpCode);
                Postdata postdata=new Postdata();
                String pdOtp=postdata.post(MainActivity.mainUrl+"verifyOTP",joOtp.toString());
                JSONObject j=new JSONObject(pdOtp);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
                    JSONObject jo=j.getJSONObject("guest_row");
                    id =jo.getString("id");
                    profileId =jo.getString("profile_id");

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
                if(statusCheck.equals("2"))
                {
                    Intent i = new Intent(getApplicationContext(),GuestNameActivity.class);
                    i.putExtra("guest_id",id);
                    i.putExtra("weddingId",weddingId);
                    i.putExtra("mobileNo",mobileNo);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Intent i = new Intent(getApplicationContext(),GuestInviteIdActivity.class);
                    i.putExtra("guest_id",id);
                    i.putExtra("weddingId",weddingId);
                    i.putExtra("mobileNo",mobileNo);
                    startActivity(i);
                    finish();
                }

            }
            else
            {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
