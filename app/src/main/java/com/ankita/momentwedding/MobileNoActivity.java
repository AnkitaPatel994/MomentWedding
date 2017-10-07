package com.ankita.momentwedding;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MobileNoActivity extends AppCompatActivity {

    EditText txtMobaileNo;
    Button btnVerification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_no);

        txtMobaileNo = (EditText)findViewById(R.id.txtMobaileNo);
        btnVerification = (Button)findViewById(R.id.btnVerification);

        btnVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mobileNo = txtMobaileNo.getText().toString();
                String weddingId = getIntent().getExtras().getString("wedding_id");

                GetMobaileNo getMobaileNo = new GetMobaileNo(weddingId,mobileNo);
                getMobaileNo.execute();
            }
        });
    }

    private class GetMobaileNo extends AsyncTask<String,Void,String> {

        String weddingId,mobileNo,status,message;

        public GetMobaileNo(String weddingId, String mobileNo) {

            this.weddingId = weddingId;
            this.mobileNo = mobileNo;

        }

        @Override
        protected String doInBackground(String... strings) {
            JSONObject joMN=new JSONObject();

            try {

                joMN.put("wedding_id",weddingId);
                joMN.put("mobile",mobileNo);
                Postdata postdata=new Postdata();
                String pdPro=postdata.post(MainActivity.mainUrl+"sendOTP",joMN.toString());
                JSONObject j=new JSONObject(pdPro);
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
                Intent i = new Intent(getApplicationContext(),OTPCodeActivity.class);
                i.putExtra("wedding_id",weddingId);
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
