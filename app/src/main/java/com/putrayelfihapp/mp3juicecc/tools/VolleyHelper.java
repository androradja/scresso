package com.putrayelfihapp.mp3juicecc.tools;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.putrayelfihapp.mp3juicecc.model.SongModel;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VolleyHelper {



    public interface Suggestlistener {
        void onsuccess(List<String> listsong);
    }

    public interface Songlistener {
        void onsuccess(List<SongModel> listsong);
    }




    public void setSuggestlistener(Suggestlistener suggestlistener) {
        this.suggestlistener = suggestlistener;
    }

    public void setSonglistener(Songlistener songlistener) {
        this.songlistener = songlistener;
    }

    private Suggestlistener suggestlistener;
    private Songlistener songlistener;




    public  void getSongSearch(Context context, String url){
        Log.e("url",url);
        List<SongModel> list = new ArrayList<>();
        list.clear();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("songs");
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        JSONObject jsonObject= jsonArray.getJSONObject(i);
                        SongModel modelSong = new SongModel();
                        modelSong.setId(jsonObject.getInt("id"));
                        modelSong.setDuration(jsonObject.getInt("duration"));
                        modelSong.setTitle(jsonObject.getString("title"));
                        modelSong.setThumbNail(jsonObject.getString("image"));
                        modelSong.setLikes(jsonObject.getInt("likes_count"));
                        list.add(modelSong);
                    }
                    songlistener.onsuccess(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> Log.e("err","test"));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }





    public void getSuggest(Context context, String q){
        List<String> list = new ArrayList<>();
        list.clear();
        String url=Config.SUGGEST+q;
        Log.e("urlss", url );
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        list.add(jsonArray.getString(i));

                    }

                    suggestlistener.onsuccess(list);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, error -> Log.e("err", String.valueOf(error)));

        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }





}
