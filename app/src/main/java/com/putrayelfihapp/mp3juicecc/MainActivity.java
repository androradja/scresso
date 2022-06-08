package com.putrayelfihapp.mp3juicecc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.putrayelfihapp.mp3juicecc.databinding.ActivityMainBinding;
import com.putrayelfihapp.mp3juicecc.tools.Config;
import com.putrayelfihapp.mp3juicecc.tools.Helper;
import com.putrayelfihapp.mp3juicecc.tools.MainFramework;
import com.putrayelfihapp.mp3juicecc.tools.MusicService;
import com.putrayelfihapp.mp3juicecc.tools.Route;
import com.putrayelfihapp.mp3juicecc.ui.HomeFragment;
import com.putrayelfihapp.mp3juicecc.ui.PlayerFragment;
import com.putrayelfihapp.mp3juicecc.ui.SearchFragment;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    String CURRENTVIEW="HOME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestConfiguration configuration = new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("your device id should go here")).build();
        MobileAds.setRequestConfiguration(configuration);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);
        Config.createChannel(MainActivity.this);
        Route.gotofragment(getSupportFragmentManager(), PlayerFragment.newInstance());

        MainFramework.setBottomnav(MainActivity.this,binding.nav, getSupportFragmentManager(),PlayerFragment.newInstance(), HomeFragment.newInstance(), SearchFragment.newInstance());







        Helper helper= new Helper();
        helper.getlocalbroadcaster(MainActivity.this);
        helper.setOnItemClickListener(new Helper.OnReceivedSignal() {
            @Override
            public void onPlaying() {
                Toast.makeText(MainActivity.this,"Playing "+ MusicService.currentsongModel.getTitle(),Toast.LENGTH_SHORT).show();
                binding.nav.getMenu().getItem(0).setIcon(R.drawable.ic_baseline_pause_24);


            }

            @Override
            public void onPrepare() {



            }

            @Override
            public void onPause() {
                Log.e("state", "onPause: " );
                binding.nav.getMenu().getItem(0).setIcon(R.drawable.ic_baseline_play_arrow_24);


            }

            @Override
            public void onResume() {
                binding.nav.getMenu().getItem(0).setIcon(R.drawable.ic_baseline_pause_24);

            }

            @Override
            public void onGetduration(String currentduration, int progress) {

            }
        });



    }

    @Override
    public void onBackPressed() {
        MainFramework.setOnbackpress(getSupportFragmentManager(), MainActivity.this);
    }
}