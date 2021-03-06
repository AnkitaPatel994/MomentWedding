package com.ankita.momentwedding;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shyam group on 9/28/2017.
 */

class attProgramListAdapter extends RecyclerView.Adapter<attProgramListAdapter.ViewHolder> {

    View v;
    Context context;
    ArrayList<HashMap<String, String>> attProgramListArray;
    static ArrayList<String> positions = new ArrayList<String>();

    public attProgramListAdapter(Context context, ArrayList<HashMap<String, String>> attProgramListArray) {

        this.context = context ;
        this.attProgramListArray = attProgramListArray ;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.program, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final String id = attProgramListArray.get(position).get("id");
        String name = attProgramListArray.get(position).get("name");
        String time = attProgramListArray.get(position).get("time");
        String date = attProgramListArray.get(position).get("date");

        holder.txtProgramName.setText(name);
        holder.txtProgramName.setTextColor(Color.parseColor(GetTheme.colorTextDark));
        holder.txtProgramDateTime.setText(date+" "+time);
        holder.txtProgramDateTime.setTextColor(Color.parseColor(GetTheme.colorTextDark));

        positions.clear();

        if(Build.VERSION.SDK_INT >= 21) {
            holder.cbSelectProgram.setButtonTintList(ColorStateList.valueOf(Color.parseColor(GetTheme.colorPrimary)));
        }
        holder.cbSelectProgram.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b)
                {
                    positions.add(id);
                }
                else
                {
                    positions.remove(id);
                }

                /*for (String s : positions)
                {
                    if(listString == ""){
                        listString += s;
                    }else{
                        listString += "," + s;
                    }

                }

                Toast.makeText(context,""+listString,Toast.LENGTH_SHORT).show();*/

            }
        });

        String image = attProgramListArray.get(position).get("image");

        /*DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
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
        imageLoader.displayImage(image,holder.ivProgtamImg, options);*/

        Picasso.with(context).load(image).into(holder.ivProgtamImg);

        holder.ivProgtamImg.setBorderColor(Color.parseColor(GetTheme.colorPrimaryDark));
    }

    @Override
    public int getItemCount() {
        return attProgramListArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView ivProgtamImg;
        TextView txtProgramName,txtProgramDateTime;
        CheckBox cbSelectProgram;

        public ViewHolder(View v) {
            super(v);

            ivProgtamImg =(CircleImageView) v.findViewById(R.id.ivProgtamImg);
            txtProgramName =(TextView) v.findViewById(R.id.txtProgramName);
            txtProgramDateTime =(TextView) v.findViewById(R.id.txtProgramDateTime);
            cbSelectProgram =(CheckBox) v.findViewById(R.id.cbSelectProgram);
        }
    }

}
