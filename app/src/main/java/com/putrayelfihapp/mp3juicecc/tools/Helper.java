package com.putrayelfihapp.mp3juicecc.tools;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.putrayelfihapp.mp3juicecc.R;
import com.putrayelfihapp.mp3juicecc.model.SongModel;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.putrayelfihapp.mp3juicecc.tools.Static.LOCALINTENTFILTER;


public class Helper {


    private OnReceivedSignal onReceivedSignal;
    private OnGetSeekbar onGetSeekbar;

    public interface OnReceivedSignal {
        void onPlaying();
        void onPrepare();
        void onPause();
        void onResume();
        void onGetduration(String currentduration,int progress);
    }


    public interface OnGetSeekbar{
        void onGet(String currentduration,int progress);
    }

    public void setGetSeekbar(final  OnGetSeekbar onGetSeekbar){
        this.onGetSeekbar=onGetSeekbar;

    }

    public void setOnItemClickListener(final OnReceivedSignal onReceivedSignal) {
        this.onReceivedSignal = onReceivedSignal;
    }


    public static   void downloadmusic(SongModel downloadModel,Context context){
        DownloadManager downloadmanager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(Config.DOWNLOAD+downloadModel.getId());
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(downloadModel.getTitle());
        request.setDescription("Downloading");//request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,downloadModel.getTitle()+".mp3");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);


        downloadmanager.enqueue(request);

        Toast.makeText(context,"Download "+downloadModel.getTitle()+" Started",Toast.LENGTH_LONG).show();

    }


    public static void Playmusic(Context context, int position, List<SongModel> currentlist) {

        MusicService.currentlist.clear();
        MusicService.currentlist.addAll(currentlist);
        Intent playerservice = new Intent(context, MusicService.class);
        playerservice.putExtra("pos", position);
        context.startService(playerservice);
    }


    public static void musicControl(Context context,String mode){
        Intent intent = new Intent(LOCALINTENTFILTER);
        intent.putExtra("status", mode);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }



    public void getlocalbroadcaster(Context context){
        LocalBroadcastManager.getInstance(context).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String status = intent.getStringExtra("status");
                if (status.equals("playing")){
                    onReceivedSignal.onPlaying();
                }
                else if (status.equals("prepare")){
                    onReceivedSignal.onPrepare();
                }
                else if (status.equals("pause")){
                    onReceivedSignal.onPause();
                }
                else if (status.equals("resume")){
                    onReceivedSignal.onResume();
                }
                 else if (status.equals("duration")){
                    MusicUtills musicUtills= new MusicUtills();
                    // Updating progress bar
                    int progress = (int) (musicUtills.getProgressSeekBar(MusicService.currentduraiton,MusicService.totalduration));
                    onReceivedSignal.onGetduration(musicUtills.milliSecondsToTimer(MusicService.currentduraiton),progress);
                }

            }
        }, new IntentFilter(LOCALINTENTFILTER));

    }



    public static String parsenumber(int number){

        if (number<10){

            String num="0"+number;
            return num;
        }
        else {

            return String.valueOf(number);
        }


    }



   public static String toProperCase(String s) {
        return s.substring(0, 1).toUpperCase() +
                s.substring(1).toLowerCase();
    }


    /**
     * Making notification bar transparent
     */
    public static void setSystemBarTransparent(Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = act.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public static boolean randomtruefalse(){
        Random rd = new Random(); // creating Rando

        return rd.nextBoolean();
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static void displayImage(Context ctx, ImageView img, String imgurl,int placeholder) {
        try {
            Glide.with(ctx).load(imgurl)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(placeholder)
                    .into(img);
        } catch (Exception e) {
        }
    }

    public static void displayImagedrawable(Context ctx, ImageView img, int imgurl,int placeholder) {
        try {
            Glide.with(ctx).load(imgurl)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(placeholder)
                    .into(img);
        } catch (Exception e) {
        }
    }

    public static void displayImageBlur(Context ctx, ImageView img, String imgurl,int placeholder) {
        try {
            Glide.with(ctx).load(imgurl)
                    .apply(RequestOptions.bitmapTransform(new BlurTransformation(10, 10)))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(placeholder)
                    .into(img);
        } catch (Exception e) {
        }
    }

    public static void displayImageOriginal(Context ctx, ImageView img, String url) {
        try {
            Glide.with(ctx).load(url)
                    .error(R.drawable.defaultimage)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(img);
            System.out.println("imageload "+url);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static String getFormattedDateSimple(Long dateTime) {
        SimpleDateFormat newFormat = new SimpleDateFormat("MMMM dd, yyyy");
        return newFormat.format(new Date(dateTime));
    }

    public static String getFormattedDateEvent(Long dateTime) {
        SimpleDateFormat newFormat = new SimpleDateFormat("EEE, MMM dd yyyy");
        return newFormat.format(new Date(dateTime));
    }

    public static String getFormattedTimeEvent(Long time) {
        SimpleDateFormat newFormat = new SimpleDateFormat("h:mm a");
        return newFormat.format(new Date(time));
    }

    public static String getEmailFromName(String name) {
        if (name != null && !name.equals("")) {
            String email = name.replaceAll(" ", ".").toLowerCase().concat("@mail.com");
            return email;
        }
        return name;
    }

    public static int dpToPx(Context c, int dp) {
        Resources r = c.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }



    public static void copyToClipboard(Context context, String data) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("clipboard", data);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(context, "Text copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    public static void nestedScrollTo(final NestedScrollView nested, final View targetView) {
        nested.post(new Runnable() {
            @Override
            public void run() {
                nested.scrollTo(500, targetView.getBottom());
            }
        });
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static boolean toggleArrow(View view) {
        if (view.getRotation() == 0) {
            view.animate().setDuration(200).rotation(180);
            return true;
        } else {
            view.animate().setDuration(200).rotation(0);
            return false;
        }
    }

    public static boolean toggleArrow(boolean show, View view) {
        return toggleArrow(show, view, true);
    }

    public static boolean toggleArrow(boolean show, View view, boolean delay) {
        if (show) {
            view.animate().setDuration(delay ? 200 : 0).rotation(180);
            return true;
        } else {
            view.animate().setDuration(delay ? 200 : 0).rotation(0);
            return false;
        }
    }

    public static void changeNavigateionIconColor(Toolbar toolbar, @ColorInt int color) {
        Drawable drawable = toolbar.getNavigationIcon();
        drawable.mutate();
        drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    public static void changeMenuIconColor(Menu menu, @ColorInt int color) {
        for (int i = 0; i < menu.size(); i++) {
            Drawable drawable = menu.getItem(i).getIcon();
            if (drawable == null) continue;
            drawable.mutate();
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
    }

    public static void changeOverflowMenuIconColor(Toolbar toolbar, @ColorInt int color) {
        try {
            Drawable drawable = toolbar.getOverflowIcon();
            drawable.mutate();
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        } catch (Exception e) {
        }
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static String toCamelCase(String input) {
        input = input.toLowerCase();
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }

    public static String insertPeriodically(String text, String insert, int period) {
        StringBuilder builder = new StringBuilder(text.length() + insert.length() * (text.length() / period) + 1);
        int index = 0;
        String prefix = "";
        while (index < text.length()) {
            builder.append(prefix);
            prefix = insert;
            builder.append(text.substring(index, Math.min(index + period, text.length())));
            index += period;
        }
        return builder.toString();
    }


    public static void rateAction(Activity activity) {
        Uri uri = Uri.parse("market://details?id=" + activity.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            activity.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + activity.getPackageName())));
        }
    }

}
