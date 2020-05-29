package com.example.droweathermvp.receivers;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.droweathermvp.R;


public class ConnectionReceiver extends android.content.BroadcastReceiver {
    private static final String TAG = "MyСonnectionReceiver";
    private String changedConnection;
    private int messageId = 1000;
    NotificationMaker notiMaker = new NotificationMaker();
    String channelId = "2";
    public ConnectionReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        notiMaker.buildNotification(context, channelId, checkConnectionStatus(context), messageId);
        messageId++;
    }


    //поменяем сообщение в зависимости от того, потеряно или найдено соединение
    private String checkConnectionStatus(Context context){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected){
            changedConnection = context.getString(R.string.receiverTitleNetworkOk);
        } else {
            changedConnection = context.getString(R.string.receiverTitleNetworkLost);
        }
        return changedConnection;
    }


}
