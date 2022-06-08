package com.putrayelfihapp.mp3juicecc.tools;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.putrayelfihapp.mp3juicecc.R;
import com.putrayelfihapp.mp3juicecc.notifservice.CreateNotification;
import com.putrayelfihapp.mp3juicecc.ui.PlayerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import static com.putrayelfihapp.mp3juicecc.tools.Static.LOCALINTENTFILTER;
import static com.putrayelfihapp.mp3juicecc.tools.Static.listnewmusic;


public class MainFramework {


    public static void setBottomnav(Context context, BottomNavigationView nav, FragmentManager fm, PlayerFragment playerfragment , Fragment homefragment, Fragment searchfragment){
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            String currentview="PLAYER";
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.player:
                        if (currentview=="PLAYER"){

                            if (MusicService.PLAYERSTATUS.equals("PLAYING")){
                                Helper.musicControl(context,"pause");
                            }
                            else if (MusicService.PLAYERSTATUS.equals("STOP")){
                                Helper.Playmusic(context,0,listnewmusic);

                            }
                            else {
                                Helper.musicControl(context,"resume");
                            }
                            Log.e("playss", "onNavigationItemSelected: " );

                        }
                        else {
                            Route.gotofragmentbotnav(fm,playerfragment);
                            currentview="PLAYER";

                        }


                        return true;
                    case R.id.home_menu:
                        Route.gotofragmentbotnav(fm,homefragment);
                        currentview="HOME";
                        return true;
                    case R.id.search_menu:
                        Route.gotofragmentbotnav(fm, searchfragment);
                        currentview="SEARCH";
                        return true;
                   

                }
                return false;
            }
        });

    }

    public static void  setMiniPlayer(View miniPlayer, IndicatorSeekBar seekbar, Context context, ImageButton play, ImageButton next, ImageButton prev, TextView title,TextView artist, ImageView cover, Class playeractivity){

        miniPlayer.setVisibility(View.GONE);
        seekbar.setProgress(0);
        seekbar.setMax(MusicUtills.MAX_PROGRESS);
        seekbar.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                if(seekParams.fromUser){
                    seekbar.setProgress(seekParams.progress);
                    double currentseek = ((double) seekParams.progress/(double)MusicUtills.MAX_PROGRESS);
                    int totaldura= (int) MusicService.totalduration;
                    int seek= (int) (totaldura*currentseek);
                    Intent intent = new Intent(LOCALINTENTFILTER);
                    intent.putExtra("status", "seek");
                    intent.putExtra("seektime",seek);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                }
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });
        miniPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,playeractivity);
                context.startActivity(intent);
            }
        });


        Helper helper = new Helper();
        helper.getlocalbroadcaster(context);
        helper.setOnItemClickListener(new Helper.OnReceivedSignal() {
            @Override
            public void onPlaying() {
               play.setImageResource(R.drawable.btn_pause_1);
                title.setText(MusicService.currentsongModel.getTitle());
                Helper.displayImage(context,cover, MusicService.currentsongModel.getThumbNail(),R.drawable.defaultimage);

            }

            @Override
            public void onPrepare() {

              miniPlayer.setVisibility(View.VISIBLE  );


            }

            @Override
            public void onPause() {
                play.setImageResource(R.drawable.btn_play_2);

            }

            @Override
            public void onResume() {
              play.setImageResource(R.drawable.btn_pause_1);

            }

            @Override
            public void onGetduration(String currentduration, int progress) {
              play.setImageResource(R.drawable.btn_pause_1);
               seekbar.setProgress(progress);


            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MusicService.PLAYERSTATUS.equals("PLAYING")){
                    Helper.musicControl(context,"pause");
                }
                else {
                    Helper.musicControl(context,"resume");

                }
            }
        });

      next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.musicControl(context,"next");

            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.musicControl(context,"prev");

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CreateNotification.CHANNEL_ID,
                    "Music App", NotificationManager.IMPORTANCE_LOW);
            NotificationManager  notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }
        }


    }


    public static void setOnbackpress(FragmentManager fm,  Activity activity){
        if (fm.getBackStackEntryCount() > 1) {
            fm.popBackStack();
        } else {
//            Dialog.showExitDialog(context,activity);
        }
    }






}
