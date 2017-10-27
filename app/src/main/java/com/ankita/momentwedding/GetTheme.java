package com.ankita.momentwedding;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kalpe on 10/27/2017.
 */

class GetTheme extends AsyncTask<String,Void,String> {

    public static String primaryColor,primaryDarkColor,backgroundColor,backgroundImg,galleryImgBG,transparentColor,textLight,textDark,textTitleColor,lableTextColor,clockTopBgColor;
    String guest_id,wedding_id,status,message;

    public GetTheme(String guest_id, String wedding_id) {

        this.guest_id = guest_id;
        this.wedding_id = wedding_id;

    }

    @Override
    protected String doInBackground(String... strings) {

        JSONObject joTheme=new JSONObject();
        try {

            joTheme.put("guest_id",guest_id);
            joTheme.put("wedding_id",wedding_id);
            Postdata postdata=new Postdata();
            String pdInt=postdata.post(MainActivity.mainUrl+"getTheme",joTheme.toString());
            JSONObject j=new JSONObject(pdInt);
            status=j.getString("status");
            if(status.equals("1"))
            {
                Log.d("Like","Successfully");
                message=j.getString("message");
                JSONObject jo=j.getJSONObject("theme");

                primaryColor = jo.getString("primaryColor");
                primaryDarkColor = jo.getString("primaryDarkColor");
                transparentColor = jo.getString("transparentColor");
                textLight = jo.getString("textLight");
                textDark = jo.getString("textDark");
                lableTextColor = jo.getString("lableTextColor");
                textTitleColor = jo.getString("textTitleColor");
                clockTopBgColor = jo.getString("clockTopBgColor");
                backgroundColor = jo.getString("backgroundColor");
                backgroundImg = jo.getString("backgroundImg");
                galleryImgBG = jo.getString("galleryImgBG");

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
            Log.d("Like","Successfully");
        }
        else
        {
            Log.d("Like","Unsuccessful");
        }
    }
}
