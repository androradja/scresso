package com.putrayelfihapp.mp3juicecc.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.putrayelfihapp.mp3juicecc.adapter.SliderAdapter;
import com.putrayelfihapp.mp3juicecc.databinding.FragmentHomeBinding;
import com.putrayelfihapp.mp3juicecc.databinding.FragmentPlayerBinding;
import com.putrayelfihapp.mp3juicecc.model.SongModel;
import com.putrayelfihapp.mp3juicecc.tools.Helper;
import com.putrayelfihapp.mp3juicecc.tools.MusicService;
import com.putrayelfihapp.mp3juicecc.tools.MusicUtills;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.putrayelfihapp.mp3juicecc.tools.MusicService.currentlist;
import static com.putrayelfihapp.mp3juicecc.tools.Static.LOCALINTENTFILTER;
import static com.putrayelfihapp.mp3juicecc.tools.Static.listnewmusic;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    boolean scrolling=false;
    SliderAdapter sliderAdapter;
    int cstate=0;

    List<SongModel> listsongslider= new ArrayList<>();

    public PlayerFragment() {
        // Required empty public constructor
    }
    private FragmentPlayerBinding binding;
    Context context;
    KProgressHUD kProgressHUD;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment PlayerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayerFragment newInstance() {

        return new PlayerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPlayerBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.seekbar.setProgress(0);
        binding.seekbar.setMax(MusicUtills.MAX_PROGRESS);
        binding.seekbar.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                if(seekParams.fromUser){
                    binding.seekbar.setProgress(seekParams.progress);
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



        binding.vp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.e("onPageScrolled", String.valueOf(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                cstate=cstate+state;
               if (cstate==3){
                   scrolling=true;
               }
               else scrolling =false;
                Log.e("onPageSelected", String.valueOf(cstate));

            }

            @Override
            public void onPageSelected(int position) {

                if (scrolling){
                    Helper.Playmusic(context,position,listsongslider);
                    scrolling=false;
                }
                else {
                    sliderAdapter.notifyDataSetChanged();
                }

                cstate=0;


            }
        });

        listsongslider.clear();
        listsongslider.addAll(listnewmusic);
        sliderAdapter= new SliderAdapter(getActivity(), listsongslider);
        binding.vp.setAdapter(sliderAdapter);


        Helper helper= new Helper();
        helper.getlocalbroadcaster(context);
        helper.setOnItemClickListener(new Helper.OnReceivedSignal() {
            @Override
            public void onPlaying() {
                refresh();
                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onPrepare() {
                binding.progressBar.setVisibility(View.VISIBLE);
                sliderAdapter.notifyDataSetChanged();


            }

            @Override
            public void onPause() {

            }

            @Override
            public void onResume() {

            }

            @Override
            public void onGetduration(String currentduration, int progress) {
                refresh();
                binding.seekbar.setProgress(progress);

            }
        });






    }

    public void refresh(){
        Log.e("getCurrentItem", String.valueOf(binding.vp.getCurrentItem()));
        Log.e("currentpos", String.valueOf(MusicService.currentpos));
        if ((binding.vp.getCurrentItem() !=MusicService.currentpos)){
            listsongslider.clear();
            listsongslider.addAll(currentlist);
            sliderAdapter.notifyDataSetChanged();
            binding.vp.setCurrentItem(MusicService.currentpos,true);
            Log.e("currentpos", "refresh");

        }
    }


}