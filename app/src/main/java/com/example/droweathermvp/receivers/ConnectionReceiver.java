package com.example.droweathermvp.receivers;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.droweathermvp.R;

public class ConnectionReceiver extends android.content.BroadcastReceiver {
    private static final String TAG = "MyСonnectionReceiver";
    private String changedConnection = "Проверьте интернет-соединение";
    private int messageId = 1000;
    NotificationMaker notiMaker = new NotificationMaker();
    String channelId = "2";
    public ConnectionReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        notiMaker.buildNotification(context, channelId, changedConnection, messageId);
    }
}
