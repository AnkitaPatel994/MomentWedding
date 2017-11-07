package com.ankita.momentwedding;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.LinearLayout;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by kalpe on 11/7/2017.
 */

class GetImageFromServer extends AsyncTask<String,Void,String> {

    Bitmap image;
    LinearLayout llBgImg;
    public GetImageFromServer(LinearLayout llBgImg) {
        this.llBgImg = llBgImg;
    }

    @Override
    protected String doInBackground(String... strings) {

        try {

            URL urli = new URL(GetTheme.imgBg);
            URLConnection ucon = urli.openConnection();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;

            image = BitmapFactory.decodeStream(ucon.getInputStream(),null,options);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        Drawable dr = new BitmapDrawable(image);
        llBgImg.setBackgroundDrawable(dr);
    }
}
