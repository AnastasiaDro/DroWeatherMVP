package com.example.droweathermvp.location;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.example.droweathermvp.model.MyData;

public class MyLocationListener implements LocationListener {
    MyData myData;



    String latitude;
    String longitude;

    public MyLocationListener(){
        myData = MyData.getInstance();
    }

    @Override
    public void onLocationChanged(Location location) {

        double lat = location.getLatitude(); // Широта
        latitude = Double.toString(lat);
        double lng = location.getLongitude(); // Долгота
        longitude = Double.toString(lng);
        myData.setLatitude(latitude);
        myData.setLongitude(longitude);
        String accuracy = Float.toString(location.getAccuracy());   // Точность
        Log.d("WeatherByLocClickListener", "Широта: " + latitude + "\n" + "долгота: " + longitude);
        myData.getWeatherLoader().loadWeatherByCoord();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

}
