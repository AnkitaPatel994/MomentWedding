package com.ankita.momentwedding;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kalpe on 11/3/2017.
 */

class GetTheme extends AsyncTask<String,Void,String> {

    public static String colorPrimary,colorTextSelect,colorTabIcon,colorRoundBorder,colorPrimaryDark,colorTransparentDark,colorTransparentLight,colorTextLight,colorTextDark,colorLableText,colorTextTitle,colorClockTopBg,colorBg,imgBg,colorGalleryImgBG,colorIcon,colorIconDark,colorIconLight;

    String guest_id,wedding_id,status,message;

    public GetTheme(String guest_id, String wedding_id) {
        this.guest_id = guest_id;
        this.wedding_id = wedding_id;
    }
    String primaryColor,textSelectColor,tabIconColor,roundBorderColor,iconColorDark,iconColorLight,primaryDarkColor,transparentDarkColor,transparentLightColor,textLightColor,textDarkColor,lableTextColor,textTitleColor,clockTopBgColor,backgroundColor,backgroundImg,galleryImgBG,iconColor;
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
                transparentDarkColor = jo.getString("colorTransparentDark");
                transparentLightColor = jo.getString("colorTransparentLight");
                textLightColor = jo.getString("textLight");
                textDarkColor = jo.getString("textDark");
                lableTextColor = jo.getString("lableTextColor");
                textTitleColor = jo.getString("textTitleColor");
                clockTopBgColor = jo.getString("clockTopBgColor");
                backgroundColor = jo.getString("backgroundColor");
                backgroundImg = jo.getString("backgroundImg");
                galleryImgBG = jo.getString("galleryImgBG");
                iconColor = jo.getString("iconColor");
                iconColorDark = jo.getString("iconColorDark");
                iconColorLight = jo.getString("iconColorLight");
                roundBorderColor = jo.getString("roundBorderColor");

                textSelectColor = jo.getString("textSelectColor");
                tabIconColor = jo.getString("tabIconColor");

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

            colorPrimary = primaryColor;
            colorPrimaryDark = primaryDarkColor;
            colorTransparentDark = transparentDarkColor;
            colorTransparentLight = transparentLightColor;
            colorTextLight = textLightColor;
            colorTextDark = textDarkColor;
            colorLableText = lableTextColor;
            colorTextTitle = textTitleColor;
            colorClockTopBg = clockTopBgColor;
            colorGalleryImgBG = galleryImgBG;
            colorIcon = iconColor;
            colorBg = backgroundColor;
            imgBg = backgroundImg;
            colorIconDark = iconColorDark;
            colorIconLight = iconColorLight;
            colorRoundBorder = roundBorderColor;
            colorTextSelect = textSelectColor;
            colorTabIcon = tabIconColor;

        }
        else
        {
            Log.d("Like","Unsuccessful");
        }
    }
}
