package com.ankita.momentwedding;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

public class ZoomGalleryImgActivity extends AppCompatActivity {

    ViewPager vpZoomImg;
    ArrayList<String> galleryPicArray=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_zoom_gallery_img);

        setTitle("");

        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        if(Build.VERSION.SDK_INT >= 21)
        {
            getWindow().setStatusBarColor(Color.BLACK);
        }

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        galleryPicArray = getIntent().getExtras().getStringArrayList("galleryGridImgArray");
        int picPosition = getIntent().getExtras().getInt("picPosition");

        vpZoomImg = (ViewPager)findViewById(R.id.vpZoomImg);
        vpZoomImg.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(vpZoomImg != null)
                {
                    vpZoomImg.setCurrentItem(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setupViewPager(vpZoomImg,picPosition);
    }

    private void setupViewPager(ViewPager vpZoomImg, int picPosition) {

        FullScreenZoomImgAdapter fullScreenZoomImgAdapter = new FullScreenZoomImgAdapter(getApplicationContext(),galleryPicArray);
        vpZoomImg.setAdapter(fullScreenZoomImgAdapter);

        vpZoomImg.setCurrentItem(picPosition);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
