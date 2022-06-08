package com.putrayelfihapp.mp3juicecc.inter;


import com.putrayelfihapp.mp3juicecc.model.SongModel;

public interface OnitemClickListener {
        void onItemClick(int position);
        void onOptionClick(SongModel songModel);
}
