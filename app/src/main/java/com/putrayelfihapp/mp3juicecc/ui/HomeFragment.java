package com.putrayelfihapp.mp3juicecc.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.putrayelfihapp.mp3juicecc.adapter.GenreAdapterCircle;
import com.putrayelfihapp.mp3juicecc.adapter.SongAdapterGrid;
import com.putrayelfihapp.mp3juicecc.databinding.FragmentHomeBinding;
import com.putrayelfihapp.mp3juicecc.inter.OnitemClickListener;
import com.putrayelfihapp.mp3juicecc.model.SongModel;
import com.putrayelfihapp.mp3juicecc.tools.Helper;
import com.putrayelfihapp.mp3juicecc.tools.Route;

import static com.putrayelfihapp.mp3juicecc.tools.Static.listgenre;
import static com.putrayelfihapp.mp3juicecc.tools.Static.listnewmusic;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentHomeBinding binding;
    Context context;
    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
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
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initgenre();
        initsongs();



    }


    void initgenre(){
        binding.rvgenre.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false));
        binding.rvgenre.setHasFixedSize(true);
        GenreAdapterCircle genreAdapterCircle = new GenreAdapterCircle(listgenre,getActivity());
        genreAdapterCircle.setmOnitemClickListener(new OnitemClickListener() {
            @Override
            public void onItemClick(int position) {
                Route.gotofragment(getParentFragmentManager(),GenreDetailFragment.newInstance(listgenre.get(position)));

            }

            @Override
            public void onOptionClick(SongModel songModel) {

            }
        });
        binding.rvgenre.setAdapter(genreAdapterCircle);
        genreAdapterCircle.notifyDataSetChanged();
    }
    void initsongs(){
        binding.rvsongs.setLayoutManager(new GridLayoutManager(context, 4, GridLayoutManager.HORIZONTAL, false));
        binding.rvsongs.setHasFixedSize(false);
        SongAdapterGrid songAdapterGrid = new SongAdapterGrid(listnewmusic,getActivity());
        songAdapterGrid.setmOnitemClickListener(new OnitemClickListener() {
            @Override
            public void onItemClick(int position) {
            Helper.Playmusic(context,position,listnewmusic);
            }

            @Override
            public void onOptionClick(SongModel songModel) {

            }
        });
        binding.rvsongs.setAdapter(songAdapterGrid);
        songAdapterGrid.notifyDataSetChanged();
        binding.seeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Route.gotofragment(getParentFragmentManager(),GenreDetailFragment.newInstance("all-music"));
            }
        });
    }
}