package com.ankita.momentwedding;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class OTPCodeActivity extends AppCompatActivity {

    EditText txtOtpCode;
    Button btnSubmit;
    TextView txtResendSms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_code);

        txtOtpCode = (EditText) findViewById(R.id.txtOtpCode);
        txtResendSms = (TextView)findViewById(R.id.txtResendSms);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(i);
                finish();*/

                String OtpCode = txtOtpCode.getText().toString();
                String weddingId = getIntent().getExtras().getString("wedding_id");
                String mobileNo = getIntent().getExtras().getString("mobileNo");

                GetOtpCode getOtpCode = new GetOtpCode(OtpCode,weddingId,mobileNo);
                getOtpCode.execute();
            }
        });
    }

    private class GetOtpCode extends AsyncTask<String,Void,String> {

        String otpCode,weddingId,mobileNo,status,message;

        public GetOtpCode(String otpCode, String weddingId, String mobileNo) {
            this.otpCode = otpCode;
            this.weddingId = weddingId;
            this.mobileNo = mobileNo;
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
            if(status.equals("1"))
            {
                Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(i);
                finish();
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
