package com.ankita.momentwedding;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by shyam group on 10/9/2017.
 */

class galleryGridListAdapter extends RecyclerView.Adapter<galleryGridListAdapter.ViewHolder> {

    View v;
    Context context;
    ArrayList<HashMap<String, String>> galleryGridListArray;

    public galleryGridListAdapter(Context context, ArrayList<HashMap<String, String>> galleryGridListArray) {

        this.context = context;
        this.galleryGridListArray = galleryGridListArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_grid, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final String gallery_pic = galleryGridListArray.get(position).get("gallery_pic");

        holder.llBGTGalleryGrid.setBackgroundColor(Color.parseColor(GetTheme.colorGalleryImgBG));

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();
        final ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);

        ImageLoader imageLoader = ImageLoader.getInstance();
        int fallback = 0;
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(fallback)
                .showImageOnFail(fallback)
                .showImageOnLoading(fallback).build();
        imageLoader.displayImage(gallery_pic,holder.ivBgImgGrid, options);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,ZoomGalleryImgActivity.class);
                i.putExtra("galleryPic",gallery_pic);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return galleryGridListArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivBgImgGrid;
        LinearLayout llBGTGalleryGrid;

        public ViewHolder(View v) {
            super(v);

            ivBgImgGrid = (ImageView)v.findViewById(R.id.ivBgImgGrid);
            llBGTGalleryGrid = (LinearLayout)v.findViewById(R.id.llBGTGalleryGrid);
        }
    }
}
