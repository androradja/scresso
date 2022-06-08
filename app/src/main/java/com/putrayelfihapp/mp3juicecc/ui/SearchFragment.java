package com.putrayelfihapp.mp3juicecc.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.putrayelfihapp.mp3juicecc.adapter.SongAdapterList;
import com.putrayelfihapp.mp3juicecc.adapter.SuggestAdapter;
import com.putrayelfihapp.mp3juicecc.databinding.FragmentSearchBinding;
import com.putrayelfihapp.mp3juicecc.inter.OnitemClickListener;
import com.putrayelfihapp.mp3juicecc.model.SongModel;
import com.putrayelfihapp.mp3juicecc.tools.Ads;
import com.putrayelfihapp.mp3juicecc.tools.Config;
import com.putrayelfihapp.mp3juicecc.tools.Helper;
import com.putrayelfihapp.mp3juicecc.tools.VolleyHelper;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentSearchBinding binding;
    Context context;
    VolleyHelper volleyHelper = new VolleyHelper();
    SongAdapterList songAdapter;
    KProgressHUD hud;
    List<String> listsuggest= new ArrayList<>();
    List<SongModel> listresult= new ArrayList<>();

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance() {
        return new SearchFragment();
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
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        hud= KProgressHUD.create(context, KProgressHUD.Style.SPIN_INDETERMINATE);
        binding.rvresult.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.rvresult.setHasFixedSize(false);

        //set data and list adapter
        songAdapter = new SongAdapterList(listresult, getActivity());
        songAdapter.setmOnitemClickListener(new OnitemClickListener() {
            @Override
            public void onItemClick(int position) {
                Helper.Playmusic(getActivity(),position,listresult);

            }

            @Override
            public void onOptionClick(SongModel songModel) {

            }

        });

        binding.suggest.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.suggest.setHasFixedSize(false);
        SuggestAdapter suggestAdapter= new SuggestAdapter(listsuggest);
        suggestAdapter.setmOnitemClickListener(new SuggestAdapter.OnitemClickListener() {
            @Override
            public void onItemClick(String text) {
                binding.searchView.setQuery(text,true);
                listsuggest.clear();
                suggestAdapter.notifyDataSetChanged();
            }
        });
        binding.suggest.setAdapter(suggestAdapter);


        binding.rvresult.setAdapter(songAdapter);
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                hud.show();

                volleyHelper.getSongSearch(context, Config.SEARCH+query);
                volleyHelper.setSonglistener(new VolleyHelper.Songlistener() {
                    @Override
                    public void onsuccess(List<SongModel> listsong) {
                        listsuggest.clear();
                        suggestAdapter.notifyDataSetChanged();
                        hud.dismiss();
                        binding.searchView.clearFocus();
                        listresult.clear();
                        listresult.addAll(listsong);
                        if (listresult.size()<1){
                            Log.e("listsong", "onsuccess: "+query );

                            binding.nosongtext.setText("Oops!");
                            binding.nosongsub.setText("We couldn’t find results for your search:");
                            binding.nosongkw.setText(query);
                            binding.nosongkw.setVisibility(View.VISIBLE);
                            binding.result.setVisibility(View.GONE);
                        }

                        else {
                            binding.bg.setVisibility(View.INVISIBLE);
                            binding.nosongsub.setVisibility(View.INVISIBLE);
                            binding.result.setVisibility(View.VISIBLE);
                            binding.subresultkw.setText(query);
                            binding.subresultkw.setVisibility(View.VISIBLE);
                            binding.nosongtext.setVisibility(View.INVISIBLE);
                            Ads ads = new Ads(getActivity());
                            ads.showBanner(binding.banner);
                            songAdapter.notifyDataSetChanged();
                        }
                    }




                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")){
                    listsuggest.clear();

                }
                else {
                    VolleyHelper volleyHelper= new VolleyHelper();
                    volleyHelper.getSuggest(context,newText);
                    volleyHelper.setSuggestlistener(new VolleyHelper.Suggestlistener() {
                        @Override
                        public void onsuccess(List<String> listsong) {
                            listsuggest.clear();
                            listsuggest.addAll(listsong);
                        }
                    });

                }



                suggestAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }
}