package com.ankita.momentwedding;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
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
 * Created by shyam group on 9/28/2017.
 */

class familyMemberListAdapter extends RecyclerView.Adapter<familyMemberListAdapter.ViewHolder> {

    View v;
    Context context;
    ArrayList<HashMap<String, String>> familyMemberListArray;

    public familyMemberListAdapter(Context context, ArrayList<HashMap<String, String>> familyMemberListArray) {

        this.context = context;
        this.familyMemberListArray = familyMemberListArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.membar, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String member_name = familyMemberListArray.get(position).get("member_name");
        String member_relation = familyMemberListArray.get(position).get("member_relation");
        String member_details = familyMemberListArray.get(position).get("member_details");

        String member_pic = familyMemberListArray.get(position).get("member_pic");

        holder.ivFMProfile.setBorderColor(Color.parseColor(HomeActivity.selectColor));

        GradientDrawable shapeBg =  new GradientDrawable();
        shapeBg.setCornerRadius(10);
        shapeBg.setColor(Color.parseColor(HomeActivity.transparentColor));
        holder.llMemberBgTransparent.setBackground(shapeBg);

        holder.txtFMName.setText(member_name);
        holder.txtFMName.setTextColor(Color.parseColor(HomeActivity.writeColor));

        holder.txtFMRelation.setText(member_relation);
        holder.txtFMRelation.setTextColor(Color.parseColor(HomeActivity.textColor));

        holder.txtFMDetails.setText(member_details);
        holder.txtFMDetails.setTextColor(Color.parseColor(HomeActivity.writeColor));

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
        imageLoader.displayImage(member_pic,holder.ivFMProfile, options);

    }

    @Override
    public int getItemCount() {
        return familyMemberListArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView ivFMProfile;
        TextView txtFMName,txtFMRelation,txtFMDetails;
        LinearLayout llMemberBgTransparent;

        public ViewHolder(View v) {
            super(v);

            ivFMProfile =(CircleImageView)v.findViewById(R.id.ivFMProfile);
            txtFMName =(TextView) v.findViewById(R.id.txtFMName);
            txtFMRelation =(TextView) v.findViewById(R.id.txtFMRelation);
            llMemberBgTransparent = (LinearLayout)v.findViewById(R.id.llMemberBgTransparent);
            txtFMDetails =(TextView) v.findViewById(R.id.txtFMDetails);
        }
    }
}
