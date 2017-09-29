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
        // Inflate the layout for this fragment
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

                joSchedule.put("user_code",MainActivity.user_code);
                Postdata postdata=new Postdata();
                String pdSchedule=postdata.post(MainActivity.mainUrl+"scheduleFatch",joSchedule.toString());
                JSONObject j=new JSONObject(pdSchedule);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
                    JSONArray JsArry=j.getJSONArray("Schedule");
                    for (int i=0;i<JsArry.length();i++)
                    {
                        JSONObject jo=JsArry.getJSONObject(i);

                        HashMap<String,String > hashMap = new HashMap<>();

                        String sch_event =jo.getString("sch_event");
                        String sch_pic =jo.getString("sch_pic");
                        String address =jo.getString("address");
                        String sch_details =jo.getString("sch_details");

                        JSONObject jd=jo.getJSONObject("date");
                        String day =jd.getString("day");
                        String month =jd.getString("month");
                        String time =jd.getString("time");

                        hashMap.put("sch_event",sch_event);
                        hashMap.put("sch_pic",sch_pic);
                        hashMap.put("address",address);
                        hashMap.put("sch_details",sch_details);
                        hashMap.put("day",day);
                        hashMap.put("month",month);
                        hashMap.put("time",time);

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
