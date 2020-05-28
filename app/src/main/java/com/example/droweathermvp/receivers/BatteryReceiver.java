package com.example.droweathermvp.receivers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.droweathermvp.R;

public class BatteryReceiver extends BroadcastReceiver {
    private static final String TAG = "MyBatteryReceiver";
    private int messageId = 2000;
    String channelId="2";
    String contentText = "Низкий уровень заряда батареи!";

    NotificationMaker notiMaker = new NotificationMaker();
    @Override
    public void onReceive(Context context, Intent intent) {
        notiMaker.buildNotification(context, channelId, contentText, messageId);
    }
}
