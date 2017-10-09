package com.ankita.momentwedding;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

class galleryListAdapter extends RecyclerView.Adapter<galleryListAdapter.ViewHolder> {

    View v;
    Context context;
    ArrayList<HashMap<String, String>> galleryListArray;

    public galleryListAdapter(Context context, ArrayList<HashMap<String, String>> galleryListArray) {

        this.context = context;
        this.galleryListArray = galleryListArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final String eventID = galleryListArray.get(position).get("eventID");
        final String name = galleryListArray.get(position).get("name");
        String photoCount = galleryListArray.get(position).get("photoCount");

        holder.txtEvent.setText(name);
        holder.txtImgCount.setText(photoCount + " Photos");

        String background = galleryListArray.get(position).get("background");

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
        imageLoader.displayImage(background,holder.ivBgImg, options);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context,GridGalleryActivity.class);
                i.putExtra("eventName",name);
                i.putExtra("eventID",eventID);
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return galleryListArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivBgImg;
        TextView txtEvent,txtImgCount;

        public ViewHolder(View v) {
            super(v);

            ivBgImg = (ImageView)v.findViewById(R.id.ivBgImg);
            txtEvent = (TextView)v.findViewById(R.id.txtEvent);
            txtImgCount = (TextView)v.findViewById(R.id.txtImgCount);

        }
    }
}
