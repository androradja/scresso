package com.putrayelfihapp.mp3juicecc;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.putrayelfihapp.mp3juicecc.model.SongModel;
import com.putrayelfihapp.mp3juicecc.tools.Ads;
import com.putrayelfihapp.mp3juicecc.tools.Config;
import com.putrayelfihapp.mp3juicecc.tools.Static;
import com.putrayelfihapp.mp3juicecc.tools.VolleyHelper;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.putrayelfihapp.mp3juicecc.tools.Static.listgenre;
import static com.putrayelfihapp.mp3juicecc.tools.Static.listnewmusic;

public class SplashActivity extends AppCompatActivity {
    int _launcherAnimationCounter = 0;
    Timer _launcherAnimationTimer;
    Context context;
    KProgressHUD hud;
    boolean ready=false;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context=SplashActivity.this;
        hud= KProgressHUD.create(context, KProgressHUD.Style.SPIN_INDETERMINATE);
        hud.show();

        getStatusApp(getString(R.string.json));
        getlistnewmusic();
        _launcherAnimationTimer = new Timer();
        _launcherAnimationTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("run", listnewmusic.size() +","+listgenre.size()+","+ready );
                        if (listnewmusic.size()!=0 && Static.listgenre.size()!=0 && ready) {
                            hud.dismiss();
                            Ads ads = new Ads(SplashActivity.this);
                            ads.showinters();
                            ads.setadsListener(new Ads.adsListener() {
                                @Override
                                public void onAdsfinish() {
                                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                            _launcherAnimationTimer.cancel();
                        }
                        _launcherAnimationCounter = 0;
                    }
                });
            }
        }, 0, 1500);


        Static.listgenre =Arrays.asList("Alternative Rock","Ambient","Audiobooks","Business","Classical","Comedy","Country","Dance & EDM","Dancehall","Deep House","Disco","Drum & Bass","Dubstep","Electronic","Entertainment","Folk & Singer-Songwriter","Hip Hop & Rap","House","Indie","Jazz & Blues","Latin","Learning","Metal","News & Politics","Piano","Pop","R&B & Soul","Reggae","Reggaeton","Religion & Spirituality","Rock","Science","Soundtrack","Sports","Storytelling","Techno","Technology","Trance","Trap","Trending Audio","Trending Music","Trip Hop","World");


    }

    private void getStatusApp(String url){
        Log.e("url", url );
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                Ads.statusapp=response.getString("statusapp");
                Ads.appupdate=response.getString("appupdate");
                Ads.admobads=response.getString("admobads");
                Ads.applovinbanner=response.getString("applovinbaner");
                Ads.applovininter=response.getString("applovininter");
                Ads.admobbanner=response.getString("admobbanner");
                Ads.admobinter=response.getString("admobinter");
                if (Ads.statusapp.equals("0")){
                    showDialog(Ads.appupdate);
                }
                else {
                  ready=true;
                }
            } catch (JSONException e) {
                Log.e("errorparsing",e.getMessage());
            }
        }, error -> {
            Log.e("errorparsing",error.getMessage());

        });
        Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest);
    }

    private void  showDialog(String appupdate){
        new SweetAlertDialog(SplashActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("App Was Discontinue")
                .setContentText("Please Install Our New Music App")
                .setConfirmText("Install")

                .setConfirmClickListener(sDialog -> {
                    sDialog
                            .setTitleText("Install From Playstore")
                            .setContentText("Please Wait, Open Playstore")
                            .setConfirmText("Go")


                            .changeAlertType(SweetAlertDialog.PROGRESS_TYPE);

                    final Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(
                                "https://play.google.com/store/apps/details?id="+appupdate));
                        intent.setPackage("com.android.vending");
                        startActivity(intent);
//                                Do something after 100ms
                    }, 3000);



                })
                .show();
    }

    void getlistnewmusic(){
        VolleyHelper volleyHelper= new VolleyHelper();
        volleyHelper.getSongSearch(context, Config.SEARCH+"dj");
        volleyHelper.setSonglistener(new VolleyHelper.Songlistener() {
            @Override
            public void onsuccess(List<SongModel> listsong) {
                listnewmusic.clear();
                listnewmusic.addAll(listsong);

            }
        });

    }

}