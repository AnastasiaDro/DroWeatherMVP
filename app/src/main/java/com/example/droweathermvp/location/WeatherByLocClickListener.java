package com.example.droweathermvp.location;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.os.CancellationSignal;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.example.droweathermvp.model.MyData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

public class WeatherByLocClickListener implements View.OnClickListener {

    private static final int PERMISSION_REQUEST_CODE = 10;

    private LocationManager locationManager;
    MyLocationListener myLocationListener;
    Activity activity;
    Location location;
    String latitude; //широта
    String longitude; //долгота
    int counter;
    MyData myData;




    public WeatherByLocClickListener(Activity activity, LocationManager locationManager, MyLocationListener myLocationListener){
        this.activity = activity;
        myData = MyData.getInstance();
        counter++;
        Log.d("LocationListener", "Создан "+counter + "раз");
        this.locationManager = locationManager;
        this.myLocationListener = myLocationListener;
    }

    @Override
    public void onClick(View v) {
        requestPermissions();
        requestLocation();
        Toast.makeText(activity, "Сработал OnClick", Toast.LENGTH_LONG);
    }

    private void requestPermissions(){
        // Проверим, есть ли Permission’ы, и если их нет, запрашиваем их у
        // пользователя
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            //запрашиваем координаты
            //свой метод
           // requestLocation();
        } else {
            // Permission’ов нет, запрашиваем их у пользователя
            //свой метод
            requestLocationPermissions();
        }
    }

    //Запрашиваем координаты
    private void requestLocation(){
        //Если Permisson-а всё-таки нет, просто выходим: приложение не имеет смысоа
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;
        // Получаем менеджер геолокаций
        //locationManager = (LocationManager) activity.getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        // Получаем наиболее подходящий провайдер геолокации по критериям.
        // Но определить, какой провайдер использовать, можно и самостоятельно.
        // В основном используются LocationManager.GPS_PROVIDER или
        // LocationManager.NETWORK_PROVIDER, но можно использовать и
        // LocationManager.PASSIVE_PROVIDER - для получения координат в
        // пассивном режиме
        String provider = locationManager.getBestProvider(criteria, true);
//        if (locationManager.getLastKnownLocation(provider) != null) {
//            location = locationManager.getLastKnownLocation(provider);
//        } else {
       // locationManager.requestLocationUpdates(provider, 60000, 1000, myLocationListener);
        locationManager.requestSingleUpdate(provider, myLocationListener, null);
        }


    // Запрашиваем Permission’ы для геолокации
    private void requestLocationPermissions() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CALL_PHONE)) {
            // Запрашиваем эти два Permission’а у пользователя
            ActivityCompat.requestPermissions(activity,
                    new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    PERMISSION_REQUEST_CODE);
        }
    }


}
