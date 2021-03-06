package com.ankita.momentwedding;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    View view;
    CircleImageView ivGroomProfilePic,ivBrideProfilePic;
    TextView txtProGroomName,txtProBrideName;
    RecyclerView rvGuestList;
    RecyclerView.LayoutManager rvGuestListManager;
    RecyclerView.Adapter rvGuestListAdapter;
    ArrayList<HashMap<String,String>> GuestListArray=new ArrayList<>();
    static String groomId,brideId;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();

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
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        ivGroomProfilePic = (CircleImageView)view.findViewById(R.id.ivGroomProfilePic);
        ivGroomProfilePic.setBorderColor(Color.parseColor(GetTheme.colorPrimaryDark));

        ivBrideProfilePic = (CircleImageView)view.findViewById(R.id.ivBrideProfilePic);
        ivBrideProfilePic.setBorderColor(Color.parseColor(GetTheme.colorPrimaryDark));

        txtProGroomName = (TextView) view.findViewById(R.id.txtProGroomName);
        txtProGroomName.setTextColor(Color.parseColor(GetTheme.colorTextDark));


        txtProBrideName = (TextView) view.findViewById(R.id.txtProBrideName);
        txtProBrideName.setTextColor(Color.parseColor(GetTheme.colorTextDark));

        rvGuestList = (RecyclerView) view.findViewById(R.id.rvGuestList);
        rvGuestList.setHasFixedSize(true);

        rvGuestListManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvGuestList.setLayoutManager(rvGuestListManager);

        GetGuestList getGuestList = new GetGuestList();
        getGuestList.execute();

        ivGroomProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),OneProfileActivity.class);
                i.putExtra("profile_id",groomId);
                i.putExtra("GB","Groom");
                startActivity(i);
            }
        });

        ivBrideProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(),OneProfileActivity.class);
                i.putExtra("profile_id",brideId);
                i.putExtra("GB","Bride");
                startActivity(i);
            }
        });

        GetGroomBrideProfileList getGroomBrideProfileList = new GetGroomBrideProfileList();
        getGroomBrideProfileList.execute();

        return view;
    }

    private class GetGroomBrideProfileList extends AsyncTask<String,Void,String> {

        String status,message,groomProfilePic,groomName,brideProfilePic,brideName;

        @Override
        protected String doInBackground(String... strings) {

            JSONObject jojl=new JSONObject();
            try {

                jojl.put("wedding_id",HomeActivity.wedding_id);
                Postdata postdata=new Postdata();
                String pdJl=postdata.post(MainActivity.mainUrl+"getWeddingProfile",jojl.toString());
                JSONObject j=new JSONObject(pdJl);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
                    JSONObject jo=j.getJSONObject("profiles");

                    JSONObject jog=jo.getJSONObject("groomData");

                    groomId =jog.getString("id");
                    groomProfilePic =jog.getString("profile_pic");
                    groomName =jog.getString("name");

                    JSONObject job=jo.getJSONObject("brideData");

                    brideId =job.getString("id");
                    brideProfilePic =job.getString("profile_pic");
                    brideName =job.getString("name");
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
                Picasso.with(getContext()).load(groomProfilePic).into(ivGroomProfilePic);
                Picasso.with(getContext()).load(brideProfilePic).into(ivBrideProfilePic);

                txtProGroomName.setText(groomName);
                txtProBrideName.setText(brideName);
            }
            else
            {
                Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
            }
        }
    }


    private class GetGuestList extends AsyncTask<String,Void,String> {

        String status,message;
        @Override
        protected String doInBackground(String... strings) {

            GuestListArray.clear();
            JSONObject joGuest=new JSONObject();
            try {

                joGuest.put("wedding_id",HomeActivity.wedding_id);
                joGuest.put("guest_id",HomeActivity.guest_id);
                Postdata postdata=new Postdata();
                String pdGl=postdata.post(MainActivity.mainUrl+"activityFeed",joGuest.toString());
                JSONObject j=new JSONObject(pdGl);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
                    JSONArray JsArry=j.getJSONArray("feed");

                    for (int i=0;i<JsArry.length();i++)
                    {
                        JSONObject jo=JsArry.getJSONObject(i);

                        HashMap<String,String > hashMap = new HashMap<>();

                        String name =jo.getString("name");
                        String time =jo.getString("time");
                        String date =jo.getString("date");
                        String by =jo.getString("by");
                        String guest =jo.getString("guest");
                        String image =jo.getString("image");

                        hashMap.put("name",name);
                        hashMap.put("time",time);
                        hashMap.put("date",date);
                        hashMap.put("by",by);
                        hashMap.put("guest",guest);
                        hashMap.put("image",image);

                        GuestListArray.add(hashMap);
                    }
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
                rvGuestListAdapter=new guestListAdapter(getActivity(),GuestListArray);
                rvGuestList.setAdapter(rvGuestListAdapter);
            }
            else
            {
                Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
            }
        }
    }


}
