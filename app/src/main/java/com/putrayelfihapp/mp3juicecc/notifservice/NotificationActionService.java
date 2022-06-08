package com.putrayelfihapp.mp3juicecc.notifservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static com.putrayelfihapp.mp3juicecc.tools.Static.ACTIONNAME;
import static com.putrayelfihapp.mp3juicecc.tools.Static.INTENTFILTERNOTIF;


public class NotificationActionService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.sendBroadcast(new Intent(INTENTFILTERNOTIF)
        .putExtra(ACTIONNAME, intent.getAction()));
    }


}
