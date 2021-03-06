package com.ankita.momentwedding;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ScheduleFragment extends Fragment {

    View view;
    RecyclerView rvSchedule;
    RecyclerView.LayoutManager rvScheduleManager;
    RecyclerView.Adapter rvScheduleAdapter;
    ArrayList<HashMap<String,String>> scheduleListArray=new ArrayList<>();

    public ScheduleFragment() {
        // Required empty public constructor
    }

    public static ScheduleFragment newInstance(String param1, String param2) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_schedule, container, false);

        rvSchedule = (RecyclerView)view.findViewById(R.id.rvSchedule);
        rvSchedule.setHasFixedSize(true);

        rvScheduleManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvSchedule.setLayoutManager(rvScheduleManager);

        GetScheduleList getScheduleList = new GetScheduleList();
        getScheduleList.execute();

        return view;
    }

    private class GetScheduleList extends AsyncTask<String,Void,String> {

        String status,message;

        @Override
        protected String doInBackground(String... strings) {

            scheduleListArray.clear();
            JSONObject joSchedule=new JSONObject();
            try {

                joSchedule.put("wedding_id",HomeActivity.wedding_id);
                joSchedule.put("profile_id",HomeActivity.profile_id);
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

                        String name =jo.getString("name");
                        String time =jo.getString("time");
                        String location =jo.getString("location");
                        String eventDay =jo.getString("eventDay");
                        String eventMonth =jo.getString("eventMonth");
                        String event_note =jo.getString("event_note");
                        String image =jo.getString("image");
                        String date =jo.getString("date");
                        String mapURL =jo.getString("mapURL");
                        String venueName =jo.getString("venueName");
                        String venuePhone =jo.getString("venuePhone");
                        String venueEmail =jo.getString("venueEmail");
                        String venueWeb =jo.getString("venueWeb");

                        hashMap.put("name",name);
                        hashMap.put("time",time);
                        hashMap.put("location",location);
                        hashMap.put("eventDay",eventDay);
                        hashMap.put("eventMonth",eventMonth);
                        hashMap.put("event_note",event_note);
                        hashMap.put("image",image);
                        hashMap.put("date",date);
                        hashMap.put("mapURL",mapURL);
                        hashMap.put("venueName",venueName);
                        hashMap.put("venuePhone",venuePhone);
                        hashMap.put("venueEmail",venueEmail);
                        hashMap.put("venueWeb",venueWeb);

                        scheduleListArray.add(hashMap);
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
                rvScheduleAdapter=new scheduleListAdapter(getActivity(),scheduleListArray);
                rvSchedule.setAdapter(rvScheduleAdapter);
            }
            else
            {
                Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
