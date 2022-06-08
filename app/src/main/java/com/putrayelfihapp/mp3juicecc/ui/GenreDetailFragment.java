package com.putrayelfihapp.mp3juicecc.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.putrayelfihapp.mp3juicecc.adapter.SongAdapterList;
import com.putrayelfihapp.mp3juicecc.databinding.FragmentGenreDetailBinding;
import com.putrayelfihapp.mp3juicecc.databinding.FragmentHomeBinding;
import com.putrayelfihapp.mp3juicecc.inter.OnitemClickListener;
import com.putrayelfihapp.mp3juicecc.model.SongModel;
import com.putrayelfihapp.mp3juicecc.tools.Config;
import com.putrayelfihapp.mp3juicecc.tools.Helper;
import com.putrayelfihapp.mp3juicecc.tools.Route;
import com.putrayelfihapp.mp3juicecc.tools.VolleyHelper;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GenreDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GenreDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String genre;
    private String mParam2;
    private FragmentGenreDetailBinding binding;
    Context context;
    List<SongModel> list= new ArrayList<>();

    KProgressHUD kProgressHUD;

    public GenreDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment GenreDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GenreDetailFragment newInstance(String genre) {
        GenreDetailFragment fragment = new GenreDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, genre);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            genre = getArguments().getString(ARG_PARAM1);
        }
        context=getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentGenreDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @org.jetbrains.annotations.NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Route.backPress(getActivity());
            }
        });
        kProgressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Loading")
                .setCancellable(true)
                .setDetailsLabel("Please Wait")
                .setMaxProgress(100)
                .show();

        binding.genre.setText(genre);


        binding.rv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false));
        binding.rv.setHasFixedSize(false);
        SongAdapterList songAdapterList= new SongAdapterList(list,getActivity());
        songAdapterList.setmOnitemClickListener(new OnitemClickListener() {
            @Override
            public void onItemClick(int position) {
                Helper.Playmusic(context,position,list);

            }

            @Override
            public void onOptionClick(SongModel songModel) {

            }
        });
        binding.rv.setAdapter(songAdapterList);

        VolleyHelper volleyHelper= new VolleyHelper();
        volleyHelper.getSongSearch(context, Config.GENRE+genre);
        volleyHelper.setSonglistener(new VolleyHelper.Songlistener() {
            @Override
            public void onsuccess(List<SongModel> listsong) {
                list.clear();
                list.addAll(listsong);
                songAdapterList.notifyDataSetChanged();
                kProgressHUD.dismiss();

            }
        });





    }
}