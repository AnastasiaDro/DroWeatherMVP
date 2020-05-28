package com.example.droweathermvp.receivers;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


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
        notiMaker.buildNotification(context, channelId, checkConnectionStatus(context), messageId);
    }


    //поменяем сообщение в зависимости от того, потеряно или найдено соединение
    private String checkConnectionStatus(Context context){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected == true){
            changedConnection = "Подключение восстановлено";
        } else {
            changedConnection = "Подключение потеряно";
        }
        return changedConnection;
    }


}
