package com.putrayelfihapp.mp3juicecc.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.putrayelfihapp.mp3juicecc.R;
import com.putrayelfihapp.mp3juicecc.databinding.ItemSliderSongBinding;
import com.putrayelfihapp.mp3juicecc.model.SongModel;
import com.putrayelfihapp.mp3juicecc.tools.Ads;
import com.putrayelfihapp.mp3juicecc.tools.Helper;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SongModel> musicItems;

    Activity activity;

    public SliderAdapter(Activity activity, List<SongModel> musicItems) {
        this.musicItems = musicItems;
        this.activity=activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(ItemSliderSongBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {

        MyViewHolder view = (MyViewHolder) holder;
        SongModel songModel= musicItems.get(position);
        Helper.displayImageBlur(activity,view.item.background,songModel.getThumbNail(), R.drawable.defaultimage);
        Helper.displayImage(activity,view.item.cover,songModel.getThumbNail(), R.drawable.defaultimage);
        view.item.title.setText(songModel.getTitle());
        view.item.desc.setText(songModel.getLikes()+" Likes");

        view.item.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ads ads = new Ads(activity);
                ads.showinters();
                ads.setadsListener(new Ads.adsListener() {
                    @Override
                    public void onAdsfinish() {
                        Toast.makeText(activity,"Try to Download "+songModel.getTitle(),Toast.LENGTH_LONG).show();
                        Helper.downloadmusic(songModel,activity);
                    }
                });

            }
        });






    }


    @Override
    public int getItemCount() {
        return musicItems.size();
    }




    public  class MyViewHolder extends RecyclerView.ViewHolder{
        private ItemSliderSongBinding item;
        public MyViewHolder(ItemSliderSongBinding item) {
            super(item.getRoot());
            this.item=item;
        }
    }




}
