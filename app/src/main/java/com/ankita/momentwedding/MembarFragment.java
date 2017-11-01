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

public class MembarFragment extends Fragment {

    View view;
    RecyclerView rvFamilyMember;
    RecyclerView.LayoutManager rvFamilyMemberManager;
    RecyclerView.Adapter rvFamilyMemberAdapter;
    ArrayList<HashMap<String,String>> familyMemberListArray=new ArrayList<>();

    public MembarFragment() {
        // Required empty public constructor
    }

    public static MembarFragment newInstance(String param1, String param2) {
        MembarFragment fragment = new MembarFragment();
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

        view = inflater.inflate(R.layout.fragment_membar, container, false);

        rvFamilyMember = (RecyclerView)view.findViewById(R.id.rvFamilyMember);
        rvFamilyMember.setHasFixedSize(true);

        rvFamilyMemberManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvFamilyMember.setLayoutManager(rvFamilyMemberManager);

        GetFamilyMemberList getFamilyMemberList = new GetFamilyMemberList();
        getFamilyMemberList.execute();

        return view;
    }

    private class GetFamilyMemberList extends AsyncTask<String,Void,String> {

        String status,message;

        @Override
        protected String doInBackground(String... strings) {

            familyMemberListArray.clear();
            JSONObject joMember=new JSONObject();
            try {

                joMember.put("profile_id",HomeActivity.profile_id);
                Postdata postdata=new Postdata();
                String pdMember=postdata.post(MainActivity.mainUrl+"familyMembers",joMember.toString());
                JSONObject j=new JSONObject(pdMember);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
                    JSONArray JsArry=j.getJSONArray("member_list");

                    for (int i=0;i<JsArry.length();i++)
                    {
                        JSONObject jo=JsArry.getJSONObject(i);

                        HashMap<String,String > hashMap = new HashMap<>();

                        String member_name =jo.getString("member_name");
                        String member_relation =jo.getString("member_relation");
                        String member_pic =jo.getString("member_pic");
                        String member_details =jo.getString("member_details");

                        hashMap.put("member_name",member_name);
                        hashMap.put("member_relation",member_relation);
                        hashMap.put("member_pic",member_pic);
                        hashMap.put("member_details",member_details);

                        familyMemberListArray.add(hashMap);
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
                rvFamilyMemberAdapter=new familyMemberListAdapter(getActivity(),familyMemberListArray);
                rvFamilyMember.setAdapter(rvFamilyMemberAdapter);
            }
            else
            {
                Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
