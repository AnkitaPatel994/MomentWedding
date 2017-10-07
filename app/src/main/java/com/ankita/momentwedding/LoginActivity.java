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

public class LoginActivity extends AppCompatActivity {

    EditText txtInviteCode;
    Button btnNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtInviteCode = (EditText)findViewById(R.id.txtInviteCode);
        btnNext = (Button)findViewById(R.id.btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Intent i = new Intent(getApplicationContext(),MobileNoActivity.class);
                startActivity(i);
                finish();*/

                String InviteCode = txtInviteCode.getText().toString();

                GetInviteCode getInviteCode = new GetInviteCode(InviteCode);
                getInviteCode.execute();
            }
        });
    }

    private class GetInviteCode extends AsyncTask<String,Void,String> {

        String inviteCode,status,message,wedding_id;

        public GetInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
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
