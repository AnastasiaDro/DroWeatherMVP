package com.example.droweathermvp.location;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.example.droweathermvp.model.MyData;

import java.io.IOException;
import java.util.List;

public class MyLocationListener implements LocationListener {
    MyData myData;
    double lat;
    double lng;
    List<Address> addressList;

    String latitude;
    String longitude;

    Geocoder geocoder;

    public MyLocationListener(Context context){
        myData = MyData.getInstance();
        geocoder = new Geocoder(context);
    }

    @Override
    public void onLocationChanged(Location location) {

        lat = location.getLatitude(); // Широта
        latitude = Double.toString(lat);
        lng = location.getLongitude(); // Долгота
        longitude = Double.toString(lng);
        myData.setLatitude(latitude);
        myData.setLongitude(longitude);
        String accuracy = Float.toString(location.getAccuracy());   // Точность
        Log.d("WeatherByLocClickListener", "Широта: " + latitude + "\n" + "долгота: " + longitude);
        myData.getWeatherLoader().loadWeatherByCoord();
        takeCityByCoord();
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

    private void takeCityByCoord(){
        try {
            addressList = geocoder.getFromLocation(lat, lng, 1);
            String address = addressList.get(0).getLocality();
            myData.setCurrentCity(address);
            Log.d("takeCityByCoord", address);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
