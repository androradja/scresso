package com.putrayelfihapp.mp3juicecc.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.putrayelfihapp.mp3juicecc.databinding.ItemSuggestBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SuggestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<String> listdata=new ArrayList<String>();
    private OnitemClickListener mOnitemClickListener;

    public interface OnitemClickListener {
        void onItemClick(String text);
    }


    public void setmOnitemClickListener(OnitemClickListener mOnitemClickListener) {
        this.mOnitemClickListener = mOnitemClickListener;
    }

    public SuggestAdapter(List<String> listdata) {
        this.listdata = listdata;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new MyViewHolder(ItemSuggestBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {
        private ItemSuggestBinding item;
        public MyViewHolder(ItemSuggestBinding item) {
            super(item.getRoot());
            this.item = item;
        }
    }



    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder view = (MyViewHolder) holder;
        view.item.suggest.setText(listdata.get(position));

        view.item.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnitemClickListener.onItemClick(listdata.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }
}
