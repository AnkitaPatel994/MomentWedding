package com.ankita.momentwedding;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

        String sch_event = scheduleListArray.get(position).get("sch_event");
        String address = scheduleListArray.get(position).get("address");
        String sch_details = scheduleListArray.get(position).get("sch_details");
        String day = scheduleListArray.get(position).get("day");
        String month = scheduleListArray.get(position).get("month");
        String time = scheduleListArray.get(position).get("time");

        holder.txtScheduleEvent.setText(sch_event);
        holder.txtScheduleTimeAddress.setText(time+" | "+address);
        holder.txtScheduleDetails.setText(sch_details);
        holder.txtScheduleDate.setText(day);
        holder.txtScheduleMonth.setText(month);

        String sch_pic = scheduleListArray.get(position).get("sch_pic");

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
        imageLoader.displayImage(sch_pic,holder.ivSchedulePic, options);
    }

    @Override
    public int getItemCount() {
        return scheduleListArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivSchedulePic;
        TextView txtScheduleDate,txtScheduleMonth,txtScheduleEvent,txtScheduleTimeAddress,txtScheduleDetails;

        public ViewHolder(View v) {
            super(v);

            ivSchedulePic =(ImageView) v.findViewById(R.id.ivSchedulePic);
            txtScheduleDate =(TextView) v.findViewById(R.id.txtScheduleDate);
            txtScheduleMonth =(TextView) v.findViewById(R.id.txtScheduleMonth);
            txtScheduleEvent =(TextView) v.findViewById(R.id.txtScheduleEvent);
            txtScheduleTimeAddress =(TextView) v.findViewById(R.id.txtScheduleTimeAddress);
            txtScheduleDetails =(TextView) v.findViewById(R.id.txtScheduleDetails);
        }
    }

}
