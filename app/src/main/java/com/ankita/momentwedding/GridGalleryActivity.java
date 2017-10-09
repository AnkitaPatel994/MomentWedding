package com.ankita.momentwedding;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class GridGalleryActivity extends AppCompatActivity {

    RecyclerView rvGalleryGrid;
    RecyclerView.LayoutManager rvGalleryGridManager;
    RecyclerView.Adapter rvGalleryGridAdapter;
    ArrayList<HashMap<String,String>> galleryGridListArray=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_gallery);

        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        String eventName = getIntent().getExtras().getString("eventName");
        setTitle(eventName);

        rvGalleryGrid = (RecyclerView)findViewById(R.id.rvGalleryGrid);
        rvGalleryGrid.setHasFixedSize(true);

        rvGalleryGridManager = new GridLayoutManager(GridGalleryActivity.this,2);
        rvGalleryGrid.setLayoutManager(rvGalleryGridManager);

        String eventID = getIntent().getExtras().getString("eventID");

        GetGalleryGridList getGalleryGridList = new GetGalleryGridList(eventID);
        getGalleryGridList.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private class GetGalleryGridList extends AsyncTask<String,Void,String> {

        String eventID,status,message;

        public GetGalleryGridList(String eventID) {

            this.eventID = eventID;
        }

        @Override
        protected String doInBackground(String... strings) {

            galleryGridListArray.clear();
            JSONObject joGg=new JSONObject();
            try {

                joGg.put("event_id",eventID);
                Postdata postdata=new Postdata();
                String pdGg=postdata.post(MainActivity.mainUrl+"getEventPhotos",joGg.toString());
                JSONObject j=new JSONObject(pdGg);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
                    JSONArray JsArry=j.getJSONArray("photoList");

                    for (int i=0;i<JsArry.length();i++)
                    {
                        JSONObject jo=JsArry.getJSONObject(i);

                        HashMap<String,String > hashMap = new HashMap<>();

                        String gallery_pic =jo.getString("gallery_pic");

                        hashMap.put("gallery_pic",gallery_pic);

                        galleryGridListArray.add(hashMap);
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
                rvGalleryGridAdapter=new galleryGridListAdapter(GridGalleryActivity.this,galleryGridListArray);
                rvGalleryGrid.setAdapter(rvGalleryGridAdapter);
            }
            else
            {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
