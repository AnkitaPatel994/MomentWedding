package com.ankita.momentwedding;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
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

public class LoginActivity extends AppCompatActivity {

    EditText txtInviteCode;
    Button btnNext;
    LinearLayout llBgLogin;
    TextView lableInviteCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        txtInviteCode = (EditText)findViewById(R.id.txtInviteCode);
        btnNext = (Button)findViewById(R.id.btnNext);

        llBgLogin = (LinearLayout)findViewById(R.id.llBgLogin);
        llBgLogin.setBackgroundColor(Color.parseColor(MainActivity.primaryColor));

        lableInviteCode = (TextView)findViewById(R.id.lableInviteCode);
        lableInviteCode.setTextColor(Color.parseColor(MainActivity.textLight));

        GradientDrawable shapeBg =  new GradientDrawable();
        shapeBg.setStroke(3,Color.parseColor(MainActivity.editTextLoginBorderColor));
        shapeBg.setCornerRadius(5);
        shapeBg.setColor(Color.parseColor(MainActivity.primaryColor));
        txtInviteCode.setBackground(shapeBg);
        txtInviteCode.setTextColor(Color.parseColor(MainActivity.textLoginColor));

        btnNext.setTextColor(Color.parseColor(MainActivity.buttonTextLoginColor));
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String InviteCode = txtInviteCode.getText().toString();

                GetInviteCode getInviteCode = new GetInviteCode(InviteCode);
                getInviteCode.execute();
            }
        });
    }

    private class GetInviteCode extends AsyncTask<String,Void,String> {

        String inviteCode,status,message,wedding_id;
        ProgressDialog dialog;

        public GetInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(LoginActivity.this);
            dialog.setMessage("Loading...");
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joIC=new JSONObject();

            try {

                joIC.put("invite_code",inviteCode);
                Postdata postdata=new Postdata();
                String pdPro=postdata.post(MainActivity.mainUrl+"verifyWedding",joIC.toString());
                JSONObject j=new JSONObject(pdPro);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
                    wedding_id=j.getString("wedding_id");
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
                Intent i = new Intent(getApplicationContext(),MobileNoActivity.class);
                i.putExtra("wedding_id",wedding_id);
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
