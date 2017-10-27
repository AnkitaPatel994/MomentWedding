package com.ankita.momentwedding;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by pc-6 on 10/14/2017.
 */

class guestListAdapter extends RecyclerView.Adapter<guestListAdapter.ViewHolder> {

    View v;
    Context context;
    ArrayList<HashMap<String, String>> guestListArray;

    public guestListAdapter(Context context, ArrayList<HashMap<String, String>> guestListArray) {
        this.context = context;
        this.guestListArray = guestListArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.guest_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String GuestName = guestListArray.get(position).get("name");
        String Guesttime = guestListArray.get(position).get("time");
        String Guestdate = guestListArray.get(position).get("date");
        String Guestby = guestListArray.get(position).get("by");
        String Guest = guestListArray.get(position).get("guest");
        String image = guestListArray.get(position).get("image");

        holder.txtGlGuestName .setText(GuestName);
        String GuestDt = "Arriving at <b>"+ Guesttime + "</b> on <b>" + Guestdate + "</b> by <b>" + Guestby + "</b> with <b>" + Guest + " guests </b>";

        holder.txtGlGuestDate.setText(Html.fromHtml(GuestDt));

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

        imageLoader.displayImage(image,holder.ivGlGuestPic, options);
    }

    @Override
    public int getItemCount() {
        return guestListArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView ivGlGuestPic;
        TextView txtGlGuestName,txtGlGuestDate;
        LinearLayout llBGGuestList;

        public ViewHolder(View view) {
            super(view);
            ivGlGuestPic = (CircleImageView)v.findViewById(R.id.ivGlGuestPic);
            txtGlGuestName = (TextView)v.findViewById(R.id.txtGlGuestName);
            txtGlGuestDate = (TextView)v.findViewById(R.id.txtGlGuestDate);
            llBGGuestList = (LinearLayout) v.findViewById(R.id.llBGGuestList);
        }
    }
}
