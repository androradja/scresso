package com.putrayelfihapp.mp3juicecc.tools;

import android.app.Activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.putrayelfihapp.mp3juicecc.R;


public class Route {
    public static boolean gotofragment(FragmentManager fragmentManager, Fragment fragment ) {

        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .add(R.id.frame, fragment)
                .addToBackStack("opsi")
                .commit();
        return false;
    }
    public static boolean gotofragmentwithTag(FragmentManager fragmentManager, Fragment fragment, String mytag) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .add(R.id.frame, fragment,mytag)
                .addToBackStack("opsi")
                .commit();
        return false;
    }
//  public static boolean gotofragmentlib(FragmentManager fragmentManager, Fragment fragment, String mytag) {
//        fragmentManager.beginTransaction()
//                .setCustomAnimations(R.anim.slide_up, R.anim.slide_down, R.anim.pop_enter, R.anim.pop_exit)
//                .replace(R.id.libframe, fragment,mytag)
//                .addToBackStack("opsi")
//                .commit();
//        return false;
//    }


    public static boolean gotofragmentbotnav(FragmentManager fragmentManager, Fragment fragment) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .replace(R.id.frame, fragment)
                .commit();
        return false;
    }
    public static boolean gotofragmentbotnav(FragmentManager fragmentManager, Fragment fragment, String tag) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .replace(R.id.frame, fragment,tag)
                .commit();
        return false;
    }


    public static boolean gotofragmentplayer(FragmentManager fragmentManager, Fragment fragment, String tag) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_up, R.anim.slide_down, R.anim.slide_up, R.anim.slide_down)
                .add(R.id.frame, fragment,tag)
                .addToBackStack("opsi")
                .commit();
        return false;
    }


//    public static void goToActivty(Activity asal,Activity tujuan){
//        Intent intent= new Intent(asal,tujuan);
//    }
//


    public static void backPress(Activity activity){
        activity.onBackPressed();

    }
}
