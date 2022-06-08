package com.putrayelfihapp.mp3juicecc.tools;

import android.content.Context;
import android.util.Log;

import com.putrayelfihapp.mp3juicecc.model.SongModel;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class RealmHelper {
    Realm realm;
    Context context;

    public interface MyRealmListener {
        void ondatachange(Realm realm);
    }

    private  MyRealmListener listener;

    public void setListener(MyRealmListener listener) {
        this.listener = listener;
    }

    public RealmHelper(Context context) {
        Realm.init(context);
        RealmConfiguration configuration = new RealmConfiguration.Builder().allowWritesOnUiThread(true).build();
        realm = Realm.getInstance(configuration);
        this.context = context;
    }



    public void addtorecent(SongModel songModel){
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        if (realm != null) {
                            RealmResults<SongModel> result = realm.where(SongModel.class)
                                    .equalTo("id", songModel.getId())
                                    .equalTo("playlistid", "recent")
                                    .findAll();
                            if (result.size() == 0) {
                                try {
                                    songModel.setPlaylistid("recent");
                                    realm.copyToRealm(songModel);//
                                    Log.e("sukess", "songid "+songModel.getId());
                                } catch (Exception e) {
                                    Log.e("onError", e.getMessage());

                                }
                            }
                        }
                    }
                });

    }


    public void addtoqueue(SongModel songModel){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (realm != null) {
                    RealmResults<SongModel> result = realm.where(SongModel.class)
                            .equalTo("id", songModel.getId())
                            .equalTo("playlistid", "queue")
                            .findAll();
                    if (result.size() == 0) {
                        try {
                            SongModel vm= videoModelclone(songModel);
                            vm.setPlaylistid("queue");
                            realm.copyToRealm(vm);//
                            Log.e("sukess", "songid "+vm.getId());
                        } catch (Exception e) {
                            Log.e("onError", e.getMessage());

                        }
                    }
                }
            }
        });

    }

    public List<SongModel> getRecent(){
        RealmResults<SongModel> results = realm.where(SongModel.class)
                .equalTo("playlistid", "recent")
                .findAll();
        return results;

    }
    public List<SongModel> getQueue(){
        RealmResults<SongModel> results = realm.where(SongModel.class)
                .equalTo("playlistid", "queue")
                .findAll();
        return results;

    }

    public void deleteQueue(SongModel videoModel) {
        final RealmResults<SongModel> model = realm.where(SongModel.class)
                .equalTo("id", videoModel.getId())
                .equalTo("playlistid", "queue")
                .findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                model.deleteFirstFromRealm();


            }
        });
    }


    public void check(){
        realm.addChangeListener(new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm realm) {
                listener.ondatachange(realm);

            }
        });
    }

    public SongModel videoModelclone(SongModel songModel){
        SongModel songModellclone= new SongModel();
        songModellclone.setDuration(songModel.getDuration());
        songModellclone.setId(songModel.getId());
        songModellclone.setTitle(songModel.getTitle());
        songModellclone.setPlaylistid(songModel.getPlaylistid());
        songModellclone.setThumbNail(songModel.getThumbNail());
        return songModellclone;

    }


}
