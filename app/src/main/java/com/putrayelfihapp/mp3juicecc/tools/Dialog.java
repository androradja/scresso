package com.putrayelfihapp.mp3juicecc.tools;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class Dialog {

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public static void DialogSongMenu(VideoModel videoModel, Activity activity, FragmentManager fragmentManager,boolean remove) {
//        BottomSheetDialog dialog = new BottomSheetDialog(activity);
//        DialogSongMenuBinding binding = DialogSongMenuBinding
//                .inflate(LayoutInflater.from(activity));
//        dialog.setContentView(binding.getRoot());
//
//        binding.title.setText(videoModel.getTitle());
//        binding.artist.setText(videoModel.getChannelTitle());
//        binding.duration.setText(Helper.ConvertDuration(videoModel.getDuration()));
//        Helper.displayImage(activity,binding.cover,videoModel.getThumbnails(), R.drawable.defaultimage);
//
//        if (remove){
//            binding.add.setText("Remove from queue");
//            binding.add.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    RealmHelper realmHelper= new RealmHelper(activity);
//                    realmHelper.deleteQueue(videoModel);
//                    dialog.dismiss();
//                }
//            });
//        }
//
//        else {
//            binding.add.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    RealmHelper realmHelper= new RealmHelper(activity);
//                    realmHelper.addtoqueue(videoModel);
//                    showToastAdded(videoModel.getTitle(),activity);
//                    dialog.dismiss();
//                }
//            });
//        }
//
//
//        binding.download.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Route.gotofragment(fragmentManager, DownloadsFragment.newInstance(videoModel));
//                dialog.dismiss();
//
//
//            }
//        });
//        binding.info.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DialogInfo(videoModel,activity);
//                dialog.dismiss();
//
//
//            }
//        });
//        binding.share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sharedialog(activity);
//                dialog.dismiss();
//
//            }
//        });
//
//        binding.cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//
//        dialog.show();
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public static void DialogInfo(VideoModel videoModel, Activity activity) {
//        final android.app.Dialog dialog = new android.app.Dialog(activity);
//
//        DialogSongInfoBinding binding = DialogSongInfoBinding
//                .inflate(LayoutInflater.from(activity));
//        dialog.setContentView(binding.getRoot());
//
//
//
//
//        binding.title.setText(videoModel.getTitle());
//        binding.artist.setText(videoModel.getChannelTitle());
//        binding.duration.setText(Helper.ConvertDuration(videoModel.getDuration()));
//
//        binding.ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//
//        dialog.show();
//    }
//
//    public static void showToastAdded(String pesan, Activity activity) {
//        LayoutInflater inflater = activity.getLayoutInflater();
//        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) activity.findViewById(R.id.llToastBackground));
//        TextView toastText = layout.findViewById(R.id.pesan);
//        toastText.setTextColor(ContextCompat.getColor(activity, R.color.white));
//        ImageView toastImage = layout.findViewById(R.id.imgType);
//        toastText.setText(pesan);
//        toastImage.setImageResource(R.drawable.ic_added);
//        Toast toast = new Toast(activity);
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        toast.setDuration(LENGTH_LONG);
//        toast.setView(layout);
//        toast.show();
//    }

    public static void rateAction(Activity activity) {
        Uri uri = Uri.parse("market://details?id=" + activity.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            activity.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + activity.getPackageName())));
        }
    }
    ////
    public  static void sharedialog(Context context){
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String shareMessage= "\nLet me recommend you this application\n\n";
            //  shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            context.startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }

//    public static  void showExitDialog(Activity activity){
//        final android.app.Dialog dialog = new android.app.Dialog(activity);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
//        dialog.setContentView(R.layout.exit_dialog);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setCancelable(true);
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(dialog.getWindow().getAttributes());
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        dialog.findViewById(R.id.submit).setOnClickListener(v -> {
//            activity.finish();
//        });
//        dialog.findViewById(R.id.cancel_action).setOnClickListener(v -> {
//            dialog.dismiss();
//            Toast.makeText(activity, "Dismiss", Toast.LENGTH_SHORT).show();
//        });
//
//        dialog.show();
//
//
//        dialog.getWindow().setAttributes(lp);
//
//    }


}
