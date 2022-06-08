package com.putrayelfihapp.mp3juicecc.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.putrayelfihapp.mp3juicecc.databinding.ItemGenreBinding;
import com.putrayelfihapp.mp3juicecc.inter.OnitemClickListener;
import com.putrayelfihapp.mp3juicecc.tools.Ads;

import java.util.ArrayList;
import java.util.List;

public class GenreAdapterCircle extends  RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    List<String> list= new ArrayList<>();
    Activity context;
    private OnitemClickListener mOnitemClickListener;

    public GenreAdapterCircle(List<String> list, Activity context) {
        this.list = list;
        this.context = context;
    }

    public void setmOnitemClickListener(OnitemClickListener mOnitemClickListener) {
        this.mOnitemClickListener = mOnitemClickListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        return new MyViewHolder(ItemGenreBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{
        private ItemGenreBinding item;
        public MyViewHolder(ItemGenreBinding item) {
            super(item.getRoot());
            this.item=item;
        }
    }


    @Override
    public void onBindViewHolder(@NonNull  RecyclerView.ViewHolder holder, int position) {
        MyViewHolder view = (MyViewHolder) holder;
        String genre= list.get(position);
        view.item.title.setText(genre);
        view.item.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ads ads = new Ads(context);
                ads.showinters();
                ads.setadsListener(new Ads.adsListener() {
                    @Override
                    public void onAdsfinish() {
                        if (mOnitemClickListener!=null){
                            mOnitemClickListener.onItemClick(position);
                        }
                    }
                });
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
