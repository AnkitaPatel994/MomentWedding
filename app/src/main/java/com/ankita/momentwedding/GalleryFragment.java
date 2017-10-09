package com.ankita.momentwedding;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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

public class GalleryFragment extends Fragment {

    View view;
    RecyclerView rvGallery;
    RecyclerView.LayoutManager rvGalleryManager;
    RecyclerView.Adapter rvGalleryAdapter;
    ArrayList<HashMap<String,String>> galleryListArray=new ArrayList<>();

    public GalleryFragment() {
        // Required empty public constructor
    }

    public static GalleryFragment newInstance(String param1, String param2) {
        GalleryFragment fragment = new GalleryFragment();
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
        view = inflater.inflate(R.layout.fragment_gallery, container, false);

        rvGallery = (RecyclerView)view.findViewById(R.id.rvGallery);
        rvGallery.setHasFixedSize(true);

        rvGalleryManager = new GridLayoutManager(getActivity(),2);
        rvGallery.setLayoutManager(rvGalleryManager);

        GetGalleryList getGalleryList = new GetGalleryList();
        getGalleryList.execute();

        return view;
    }

    private class GetGalleryList extends AsyncTask<String,Void,String> {

        String status,message;

        @Override
        protected String doInBackground(String... strings) {

            galleryListArray.clear();
            JSONObject joGallery=new JSONObject();
            try {

                joGallery.put("wedding_id",HomeActivity.wedding_id);
                Postdata postdata=new Postdata();
                String pdGallery=postdata.post(MainActivity.mainUrl+"getGallery",joGallery.toString());
                JSONObject j=new JSONObject(pdGallery);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
                    JSONArray JsArry=j.getJSONArray("galleryEventList");

                    for (int i=0;i<JsArry.length();i++)
                    {
                        JSONObject jo=JsArry.getJSONObject(i);

                        HashMap<String,String > hashMap = new HashMap<>();

                        String eventID =jo.getString("eventID");
                        String name =jo.getString("name");
                        String background =jo.getString("background");
                        String photoCount =jo.getString("photoCount");

                        hashMap.put("eventID",eventID);
                        hashMap.put("name",name);
                        hashMap.put("background",background);
                        hashMap.put("photoCount",photoCount);

                        galleryListArray.add(hashMap);
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
                rvGalleryAdapter=new galleryListAdapter(getActivity(),galleryListArray);
                rvGallery.setAdapter(rvGalleryAdapter);
            }
            else
            {
                Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
