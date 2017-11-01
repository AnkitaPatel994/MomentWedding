package com.ankita.momentwedding;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
 * Created by shyam group on 9/28/2017.
 */

class scheduleListAdapter extends RecyclerView.Adapter<scheduleListAdapter.ViewHolder> {

    View v;
    Context context;
    ArrayList<HashMap<String, String>> scheduleListArray;

    public scheduleListAdapter(Context context, ArrayList<HashMap<String, String>> scheduleListArray) {

        this.context = context ;
        this.scheduleListArray = scheduleListArray ;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedule, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final String name = scheduleListArray.get(position).get("name");
        final String time = scheduleListArray.get(position).get("time");
        final String location = scheduleListArray.get(position).get("location");
        String eventDay = scheduleListArray.get(position).get("eventDay");
        String eventMonth = scheduleListArray.get(position).get("eventMonth");
        String event_note = scheduleListArray.get(position).get("event_note");
        final String date = scheduleListArray.get(position).get("date");
        final String mapURL = scheduleListArray.get(position).get("mapURL");
        final String venueName = scheduleListArray.get(position).get("venueName");
        final String venuePhone = scheduleListArray.get(position).get("venuePhone");
        final String venueEmail = scheduleListArray.get(position).get("venueEmail");
        final String venueWeb = scheduleListArray.get(position).get("venueWeb");

        holder.txtScheduleEvent.setText(name);
        holder.txtScheduleEvent.setTextColor(ContextCompat.getColor(context,R.color.colorTextTitle));

        holder.txtScheduleTimeAddress.setText(time+" | "+location);
        holder.txtScheduleTimeAddress.setTextColor(ContextCompat.getColor(context,R.color.colorTextLight));

        holder.txtScheduleDetails.setText(event_note);
        holder.txtScheduleDetails.setTextColor(ContextCompat.getColor(context,R.color.colorTextLight));

        holder.txtScheduleDate.setText(eventDay);
        holder.txtScheduleDate.setTextColor(ContextCompat.getColor(context,R.color.colorTextLight));

        holder.txtScheduleMonth.setText(eventMonth);
        holder.txtScheduleMonth.setTextColor(ContextCompat.getColor(context,R.color.colorLableText));

        GradientDrawable shapeBg =  new GradientDrawable();
        shapeBg.setColor(ContextCompat.getColor(context,R.color.colorTransparentDark));
        holder.llScheduleBGT.setBackground(shapeBg);

        GradientDrawable shapeBgDe =  new GradientDrawable();
        shapeBgDe.setColor(ContextCompat.getColor(context,R.color.colorTransparentDark));
        holder.llScheduleDetailBGT.setBackground(shapeBgDe);

        GradientDrawable shapeRoundBg =  new GradientDrawable();
        shapeRoundBg.setShape(GradientDrawable.OVAL);
        shapeRoundBg.setStroke(3,ContextCompat.getColor(context,R.color.colorLableText));
        shapeRoundBg.setCornerRadius(10);
        shapeRoundBg.setColor(ContextCompat.getColor(context,R.color.colorPrimaryDark));
        holder.llRoungBG.setBackground(shapeRoundBg);

        final String image = scheduleListArray.get(position).get("image");

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
        imageLoader.displayImage(image,holder.ivSchedulePic, options);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,VenueActivity.class);
                i.putExtra("name",name);
                i.putExtra("time",time);
                i.putExtra("date",date);
                i.putExtra("location",location);
                i.putExtra("image",image);
                i.putExtra("mapURL",mapURL);
                i.putExtra("venueName",venueName);
                i.putExtra("venuePhone",venuePhone);
                i.putExtra("venueEmail",venueEmail);
                i.putExtra("venueWeb",venueWeb);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return scheduleListArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivSchedulePic;
        TextView txtScheduleDate,txtScheduleMonth,txtScheduleEvent,txtScheduleTimeAddress,txtScheduleDetails;
        LinearLayout llScheduleBGT,llScheduleDetailBGT,llRoungBG;

        public ViewHolder(View v) {
            super(v);

            ivSchedulePic =(ImageView) v.findViewById(R.id.ivSchedulePic);
            txtScheduleDate =(TextView) v.findViewById(R.id.txtScheduleDate);
            txtScheduleMonth =(TextView) v.findViewById(R.id.txtScheduleMonth);
            txtScheduleEvent =(TextView) v.findViewById(R.id.txtScheduleEvent);
            txtScheduleTimeAddress =(TextView) v.findViewById(R.id.txtScheduleTimeAddress);
            txtScheduleDetails =(TextView) v.findViewById(R.id.txtScheduleDetails);
            llScheduleBGT =(LinearLayout) v.findViewById(R.id.llScheduleBGT);
            llScheduleDetailBGT =(LinearLayout) v.findViewById(R.id.llScheduleDetailBGT);
            llRoungBG =(LinearLayout) v.findViewById(R.id.llRoungBG);
        }
    }

}
