package com.putrayelfihapp.mp3juicecc.tools;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import com.putrayelfihapp.mp3juicecc.R;
import com.putrayelfihapp.mp3juicecc.model.SongModel;
import com.putrayelfihapp.mp3juicecc.notifservice.CreateNotification;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static com.putrayelfihapp.mp3juicecc.tools.Static.ACTIONNAME;
import static com.putrayelfihapp.mp3juicecc.tools.Static.INTENTFILTERNOTIF;
import static com.putrayelfihapp.mp3juicecc.tools.Static.LOCALINTENTFILTER;


public class MusicService extends Service {
    public static String lirikurl;

    public  static String PLAYERSTATUS="STOP",REPEAT="OFF",SHUFFLE="OFF",CURRENTTYPE="OFF";
    public static int totalduration,currentduraiton,currentpos;
    String from;
    public static SongModel currentsongModel;
    Realm realm;
    public  static int sessionId;
    public static List<SongModel> currentlist = new ArrayList<>();

    private final Handler mHandler = new Handler();

    //player
    private MediaPlayer mp = new MediaPlayer();


    @Override
    public void onCreate() {
        super.onCreate();
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String status = intent.getStringExtra("status");
                if (status.equals("pause")){
                    CreateNotification.createNotification(getApplicationContext(), currentlist.get(currentpos),
                            R.drawable.ic_notif_play, currentpos, currentlist.size()-1);
                    mp.pause();
                    PLAYERSTATUS="PAUSE";
                }
                else if (status.equals("resume")){
                    CreateNotification.createNotification(getApplicationContext(), currentlist.get(currentpos),
                            R.drawable.ic_notif_pause, currentpos, currentlist.size()-1);
                    PLAYERSTATUS="PLAYING";
                    mHandler.post(mUpdateTimeTask);
                    mp.start();



                }


                else  if (status.equals("seek")){
                    int seek = intent.getIntExtra("seektime",0);
                    mp.pause();
                    mp.seekTo(seek);
                    mp.start();

                    PLAYERSTATUS="PLAYING";
                    Intent intent1 = new Intent(LOCALINTENTFILTER);
                    intent1.putExtra("status", "playing");
                    LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent1);
                    mHandler.post(mUpdateTimeTask);
                }
                else if (status.equals("stopmusic")){
                    PLAYERSTATUS="STOPING";
                    mp.release();
                }

                else if (status.equals("next")){
                    playsong(currentpos+1);
                }

                else if (status.equals("prev")){
                    playsong(currentpos-1);
                }
                else if (status.equals("settimer")){
                    Long end= intent.getLongExtra("end",0);
                    new CountDownTimer(end, 1000) {
                        public void onTick(long millisUntilFinished) {
                        }
                        public void onFinish() {
                            Intent intent1 = new Intent(LOCALINTENTFILTER);
                            intent1.putExtra("status", "pause");
                            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent1);
                        }
                    }.start();
                }
            }
        }, new IntentFilter(LOCALINTENTFILTER));

        registerReceiver(broadcastReceiver, new IntentFilter(INTENTFILTERNOTIF));



    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getExtras().getString(ACTIONNAME);
            switch (action){
                case CreateNotification.ACTION_PREVIUOS:
                    playsong(currentpos-1);
                    break;
                case CreateNotification.ACTION_PLAY:
                    if (PLAYERSTATUS.equals("PLAYING")){
                        Intent newintent = new Intent(LOCALINTENTFILTER);
                        newintent.putExtra("status", "pause");
                        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(newintent);
                        CreateNotification.createNotification(getApplicationContext(), currentlist.get(currentpos),
                                R.drawable.ic_notif_pause, currentpos, currentlist.size()-1);
                        mp.pause();
                        PLAYERSTATUS="PAUSE";
                    } else {
                        Intent newintent = new Intent(LOCALINTENTFILTER);
                        newintent.putExtra("status", "playing");
                        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(newintent);
                        CreateNotification.createNotification(getApplicationContext(), currentlist.get(currentpos),
                                R.drawable.ic_notif_pause, currentpos, currentlist.size()-1);
                        PLAYERSTATUS="PLAYING";
                        mp.start();
                    }
                    break;
                case CreateNotification.ACTION_CLOSE:
                    mp.stop();

                    break;
                case CreateNotification.ACTION_NEXT:
                    playsong(currentpos+1);
                    break;
            }
        }
    };



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initrealm();
        playsong(intent.getIntExtra("pos",0));
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void playsong(int pos){

        if (pos==currentlist.size()){
            pos=0;
        }
        else if (pos==-1){
            pos=currentlist.size()-1;
        }
        currentpos=pos;
        CreateNotification.createNotification(getApplicationContext(), currentlist.get(pos),
                R.drawable.ic_notif_pause, currentpos, currentlist.size()-1);

        try {
            currentsongModel =currentlist.get(pos);

            Intent intent = new Intent(LOCALINTENTFILTER);
            intent.putExtra("status", "prepare");
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
            mp.stop();
            mp.reset();
            mp.release();
            Uri myUri = Uri.parse(currentsongModel.getSongurl());
            mp = new MediaPlayer();
            mp.setDataSource(this, myUri);
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    return true;
                }
            });
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp1) {
                    if (REPEAT.equals("ON")){
                        playsong(currentpos);
                    }
                    else if (SHUFFLE.equals("ON")){
                        int pos= (int) (Math.random() * (currentlist.size()));
                        playsong(pos);
                    }
                    else {

                        playsong(currentpos+1);
                    }
                }
            });
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onPrepared(MediaPlayer mplayer) {

                    RealmHelper realmHelper = new RealmHelper(getApplication());
                    realmHelper.addtorecent(currentsongModel);
                    sessionId=mp.getAudioSessionId();
                    if (mplayer.isPlaying()) {
                        mp.pause();
                    } else {
                        mp.start();
                        PLAYERSTATUS="PLAYING";
                        Intent intent = new Intent(LOCALINTENTFILTER);
                        intent.putExtra("status", "playing");
                        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                        mHandler.post(mUpdateTimeTask);

                    }
                }
            });
            mp.prepareAsync();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    private final Runnable mUpdateTimeTask = new Runnable() {
        public void run() {

            // Running this thread after 10 milliseconds
            if (MusicService.PLAYERSTATUS.equals("PLAYING")) {
                try {
                    totalduration=mp.getDuration();
                    currentduraiton=mp.getCurrentPosition();
                    Intent intent = new Intent(LOCALINTENTFILTER);
                    intent.putExtra("status", "duration");
                    LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);


                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                mHandler.postDelayed(this, 100);
            }
        }
    };

    public void  initrealm(){
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().allowWritesOnUiThread(true).build();
        realm = Realm.getInstance(configuration);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}
