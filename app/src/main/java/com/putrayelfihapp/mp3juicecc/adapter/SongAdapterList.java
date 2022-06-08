package com.putrayelfihapp.mp3juicecc.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.putrayelfihapp.mp3juicecc.R;
import com.putrayelfihapp.mp3juicecc.databinding.ItemListBinding;
import com.putrayelfihapp.mp3juicecc.inter.OnitemClickListener;
import com.putrayelfihapp.mp3juicecc.model.SongModel;
import com.putrayelfihapp.mp3juicecc.tools.Ads;
import com.putrayelfihapp.mp3juicecc.tools.Helper;

import java.util.ArrayList;
import java.util.List;

public class SongAdapterList extends  RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    List<SongModel> list= new ArrayList<>();
    Activity context;
    private OnitemClickListener mOnitemClickListener;

    public SongAdapterList(List<SongModel> list, Activity context) {
        this.list = list;
        this.context = context;
    }

    public void setmOnitemClickListener(OnitemClickListener mOnitemClickListener) {
        this.mOnitemClickListener = mOnitemClickListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        return new MyViewHolder(ItemListBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{
        private ItemListBinding item;
        public MyViewHolder(ItemListBinding item) {
            super(item.getRoot());
            this.item=item;
        }
    }


    @Override
    public void onBindViewHolder(@NonNull  RecyclerView.ViewHolder holder, int position) {
        MyViewHolder view = (MyViewHolder) holder;
        SongModel songModel= list.get(position);
        view.item.title.setText(songModel.getTitle());
        Helper.displayImage(context,view.item.cover,songModel.getThumbNail(), R.drawable.defaultimage);
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
