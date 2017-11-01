package com.ankita.momentwedding;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ProgramActivity extends AppCompatActivity {

    RecyclerView rvAttProgram;
    RecyclerView.LayoutManager rvAttProgramManager;
    RecyclerView.Adapter rvAttProgramAdapter;
    ArrayList<HashMap<String,String>> attProgramListArray=new ArrayList<>();

    ArrayList<String> programpos = attProgramListAdapter.positions;

    static String listString ="";

    Button btnProgtam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);

        LinearLayout llBGProgram = (LinearLayout)findViewById(R.id.llBGProgram);
        llBGProgram.setBackgroundColor(ContextCompat.getColor(ProgramActivity.this,R.color.colorBg));

        LinearLayout llBGTProgram = (LinearLayout)findViewById(R.id.llBGTProgram);
        GradientDrawable shapeBg =  new GradientDrawable();
        shapeBg.setColor(ContextCompat.getColor(ProgramActivity.this,R.color.colorTransparentLight));
        llBGTProgram.setBackground(shapeBg);

        btnProgtam = (Button)findViewById(R.id.btnProgtam);

        rvAttProgram = (RecyclerView)findViewById(R.id.rvAttProgram);
        rvAttProgram.setHasFixedSize(true);

        rvAttProgramManager = new LinearLayoutManager(ProgramActivity.this, LinearLayoutManager.VERTICAL, false);
        rvAttProgram.setLayoutManager(rvAttProgramManager);

        GetAttProgramList getAttProgramList = new GetAttProgramList();
        getAttProgramList.execute();

        btnProgtam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                for (String ss : programpos)
                {
                    if(listString == ""){
                        listString += ss;
                    }else{
                        listString += "," + ss;
                    }

                }
                ProgramActivity.super.onBackPressed();
            }
        });

    }

    private class GetAttProgramList extends AsyncTask<String,Void,String> {

        String status,message;

        @Override
        protected String doInBackground(String... strings) {

            attProgramListArray.clear();
            JSONObject joSchedule=new JSONObject();
            try {

                joSchedule.put("wedding_id",HomeActivity.wedding_id);
                Postdata postdata=new Postdata();
                String pdSchedule=postdata.post(MainActivity.mainUrl+"getEventList",joSchedule.toString());
                JSONObject j=new JSONObject(pdSchedule);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
                    JSONArray JsArry=j.getJSONArray("event_list");
                    for (int i=0;i<JsArry.length();i++)
                    {
                        JSONObject jo=JsArry.getJSONObject(i);

                        HashMap<String,String > hashMap = new HashMap<>();

                        String id =jo.getString("id");
                        String name =jo.getString("name");
                        String time =jo.getString("time");
                        String date =jo.getString("date");
                        String image =jo.getString("image");

                        hashMap.put("id",id);
                        hashMap.put("name",name);
                        hashMap.put("time",time);
                        hashMap.put("date",date);
                        hashMap.put("image",image);

                        attProgramListArray.add(hashMap);
                    }
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
                rvAttProgramAdapter=new attProgramListAdapter(ProgramActivity.this,attProgramListArray);
                rvAttProgram.setAdapter(rvAttProgramAdapter);
            }
            else
            {
                Toast.makeText(ProgramActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
