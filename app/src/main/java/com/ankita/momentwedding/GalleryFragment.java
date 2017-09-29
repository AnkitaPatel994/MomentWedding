package com.ankita.momentwedding;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

public class GalleryFragment extends Fragment {

    View view;
    RecyclerView rvGallery;
    RecyclerView.LayoutManager rvGalleryManager;
    RecyclerView.Adapter rvGalleryAdapter;
    ArrayList<HashMap<String,String>> galleryListArray=new ArrayList<>();

    public GalleryFragment() {
        // Required empty public constructor
    }

    public static GalleryFragment newInstance(String param1, String param2) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_gallery, container, false);

        rvGallery = (RecyclerView)view.findViewById(R.id.rvGallery);
        rvGallery.setHasFixedSize(true);

        rvGalleryManager = new GridLayoutManager(getActivity(),2);
        rvGallery.setLayoutManager(rvGalleryManager);

        GetGalleryList getGalleryList = new GetGalleryList();
        getGalleryList.execute();

        return view;
    }

    private class GetGalleryList extends AsyncTask<String,Void,String> {

        String status,message;

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
