package com.putrayelfihapp.mp3juicecc.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

public class SongModel extends RealmObject implements Parcelable {
    String title,thumbNail,src,playlistid;
    int id,likes,duration;

    public SongModel() {
    }

    public String getSongurl(){
        return "https://fando.id/soundcloud/get.php?id="+id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbNail() {
        return thumbNail;
    }

    public void setThumbNail(String thumbNail) {
        this.thumbNail = thumbNail;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getPlaylistid() {
        return playlistid;
    }

    public void setPlaylistid(String playlistid) {
        this.playlistid = playlistid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    protected SongModel(Parcel in) {
        title = in.readString();
        thumbNail = in.readString();
        src = in.readString();
        playlistid = in.readString();
        id = in.readInt();
        likes = in.readInt();
        duration = in.readInt();
    }

    public static final Creator<SongModel> CREATOR = new Creator<SongModel>() {
        @Override
        public SongModel createFromParcel(Parcel in) {
            return new SongModel(in);
        }

        @Override
        public SongModel[] newArray(int size) {
            return new SongModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(thumbNail);
        dest.writeString(src);
        dest.writeString(playlistid);
        dest.writeInt(id);
        dest.writeInt(likes);
        dest.writeInt(duration);
    }
}
