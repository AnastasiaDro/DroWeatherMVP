package com.example.droweathermvp.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.example.droweathermvp.R;


public class BatteryReceiver extends BroadcastReceiver {
    private static final String TAG = "MyBatteryReceiver";
    private int messageId = 2000;
    String channelId="2";
    String contentText = null;

    NotificationMaker notiMaker = new NotificationMaker();
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Battery Receiver", "Сработал onReceive()");
        notiMaker.buildNotification(context, channelId, checkBatteryLevel(intent, context), messageId);
        messageId++;
    }

    private String checkBatteryLevel(Intent intent, Context context) {
        if((intent.getAction() == Intent.ACTION_BATTERY_LOW)) {
            contentText = context.getString(R.string.receiverTitleBatteryLow);
        }
        if ((intent.getAction() == Intent.ACTION_BATTERY_OKAY)){
            contentText = context.getString(R.string.receiverTitleOKBattery);
        }
        return contentText;
    }
}
